package com.mahato.covid_19pandemic.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mahato.covid_19pandemic.MainActivity;
import com.mahato.covid_19pandemic.R;
import com.mahato.covid_19pandemic.model.StateModel;
import com.mahato.covid_19pandemic.requests.CovidApi;
import com.mahato.covid_19pandemic.requests.NepalServiceGenerator;
import com.mahato.covid_19pandemic.requests.responses.StateWiseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mahato.covid_19pandemic.services.NotificationHelper.CHANNEL_1_ID;

public class StatusChangedJobService extends JobService {

    private static final String TAG = "StatusChangedJobService";
    private NotificationManagerCompat notificationManager;

    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: Start");
        notificationManager = NotificationManagerCompat.from(this);
        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        Log.d(TAG, "doBackgroundWork: Start");
        new Thread(new Runnable() {
            @Override
            public void run() {

                CovidApi covidApi = NepalServiceGenerator.getCovidApi();
                Call<StateWiseResponse> responseCall = covidApi.getNepalDataByState();

                responseCall.enqueue(new Callback<StateWiseResponse>() {
                    @Override
                    public void onResponse(Call<StateWiseResponse> call, Response<StateWiseResponse> response) {
                        if (response.code() == 200) {
                            for (StateModel state : response.body().getStates()) {

                                if (state.getState().equals("Total") || state.getStatecode().equals("TT")) {
                                    //save data here
                                    // show notification
                                    if (isFirstRun()) {
                                        sendNotification(state);
                                    } else {
                                        save(state);
                                    }

                                    jobFinished(params, false);
                                }
                            }
                        }

                        if (jobCancelled) {
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<StateWiseResponse> call, Throwable t) {

                    }
                });

            }
        }).start();
    }

    private void save(StateModel stateModel) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("covi", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("confirmed", stateModel.getConfirmed());
        editor.putLong("recovered", stateModel.getRecovered());
        editor.putLong("death", stateModel.getDeaths());
        editor.putBoolean("first", true);
        editor.apply();
    }

    private boolean isFirstRun() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("covi", MODE_PRIVATE);
        return preferences.getBoolean("first", false);
    }

    private void sendNotification(StateModel currentState) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("covi", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        long confirmed = preferences.getLong("confirmed", 0);
        long recovered = preferences.getLong("recovered", 0);
        long death = preferences.getLong("death", 0);

        long currentConfirmed = currentState.getConfirmed();
        long currentRecovered = currentState.getRecovered();
        long currentDeaths = currentState.getDeaths();

        if (currentConfirmed > confirmed) {
            Log.d(TAG, "sendNotification: " + confirmed);
            Log.d(TAG, "sendNotification: " + currentConfirmed);
            sendNotification("New Case Confirmed", "" + (currentConfirmed - confirmed) + " Case Found");
            editor.putLong("confirmed", currentConfirmed);
        }


        if (currentRecovered > recovered) {
            Log.d(TAG, "sendNotification: " + recovered);
            Log.d(TAG, "sendNotification: " + currentRecovered);
            sendNotification("Recovery Case", "Total " + (currentConfirmed - confirmed) + " Patient Recovered");
            editor.putLong("recovered", currentRecovered);
        }


        if (currentDeaths > death) {
            Log.d(TAG, "sendNotification: " + death);
            Log.d(TAG, "sendNotification: " + currentDeaths);
            sendNotification("New Death", "Total " + (currentDeaths - death) + " Patient Die");
            editor.putLong("death", currentDeaths);
        }

        editor.apply();
    }

    private void sendNotification(String title, String message) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(1, notification);
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        jobCancelled = true;
        Log.d(TAG, "onStopJob: Cancelled");
        return true;
    }

}
