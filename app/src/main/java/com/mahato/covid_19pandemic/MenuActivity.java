package com.mahato.covid_19pandemic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mahato.covid_19pandemic.util.ConnectionDetector;
import com.mahato.covid_19pandemic.util.Constants;
import com.mahato.covid_19pandemic.util.CustomDialog;
import com.mahato.covid_19pandemic.util.SocialMedia;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MenuActivity1";

    private LinearLayout pandemicPatient;
    private LinearLayout socialDistancing;
    private LinearLayout feedback;
    private LinearLayout emergencyContact;
    private LinearLayout symptoms;
    private LinearLayout aboutCOVID;
    private LinearLayout searchByCountry;
    private LinearLayout about;

    private ImageButton closeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initViews();
        clickEvent();
    }

    private void initViews() {
        pandemicPatient = findViewById(R.id.menu_pandemic_patient);
        socialDistancing = findViewById(R.id.menu_social_distancing);
        feedback = findViewById(R.id.menu_feedback);
        emergencyContact = findViewById(R.id.menu_emergency_contact);
        symptoms = findViewById(R.id.menu_symptoms);
        aboutCOVID = findViewById(R.id.menu_about_covid);
        searchByCountry = findViewById(R.id.menu_search_by_country);
        closeBtn = findViewById(R.id.menu_close_btn);
        about = findViewById(R.id.menu_about_app);
    }

    private void clickEvent() {
        pandemicPatient.setOnClickListener(this);
        socialDistancing.setOnClickListener(this);
        feedback.setOnClickListener(this);
        emergencyContact.setOnClickListener(this);
        symptoms.setOnClickListener(this);
        aboutCOVID.setOnClickListener(this);
        searchByCountry.setOnClickListener(this);
        closeBtn.setOnClickListener(this);
        about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == pandemicPatient) {

            Intent intent = new Intent(this, PatientStatusActivity.class);
            startActivity(intent);

        } else if (v == socialDistancing) {
            Intent intent = new Intent(this, SocialDistancingActivity.class);
            startActivity(intent);

        } else if (v == feedback) {

            sendFeedback();

        } else if (v == emergencyContact) {
            Intent intent = new Intent(this, EmergencyContactActivity.class);
            startActivity(intent);

        } else if (v == symptoms) {
            Intent intent = new Intent(this, SymptomsPrecautionsActivity.class);
            startActivity(intent);

        } else if (v == aboutCOVID) {
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);

        } else if (v == searchByCountry) {
            Intent intent = new Intent(this, CountryActivity.class);
            startActivity(intent);

        } else if (v == closeBtn) {
            onBackPressed();
        }else if (v == about) {
           // showAboutDialog();
            showAboutDialog();
        }
    }

    private void sendFeedback() {
        final LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.layout_feedback, null);

        TextView cancelBtn = view.findViewById(R.id.feedback_cancel_btn);
        TextView submitBtn = view.findViewById(R.id.feedback_submit_btn);
        final EditText mMessage = view.findViewById(R.id.feedback_message);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] recipients = Constants.email_address.split(",");
                String message = mMessage.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, Constants.email_subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);

                intent.setType("message/rfc822");

                try {
                    startActivity(Intent.createChooser(intent, "Choose an Email Client"));
                }
                catch (ActivityNotFoundException ex){
                    Toast.makeText(MenuActivity.this, "There's no Email Clients", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showAboutDialog() {
        final LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.layout_about, null);

        ImageView closeBtn = view.findViewById(R.id.about_close_btn);
        ImageButton facebookBtn = view.findViewById(R.id.about_facebook);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SocialMedia.openFacebook(MenuActivity.this);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
