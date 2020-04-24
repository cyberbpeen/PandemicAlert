package com.mahato.covid_19pandemic;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mahato.covid_19pandemic.adapter.DeviceListAdapter;

import java.util.ArrayList;

public class SocialDistancingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SocialDistancing";

    private TextView title;
    private TextView btStatus;
    private Button btOnOffBtn;
    private ListView deviceListView;
    private ImageButton closeBtn;

    private ArrayList<BluetoothDevice> mBTDevices;
    private DeviceListAdapter mDeviceListAdapter;

    private BluetoothAdapter bluetoothAdapter;
    private Handler mHandlerBtEnable = new Handler();
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_distancing);

        initViews();
        title.setText("Social Distancing");
        btOnOffBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mEnableRunnable.run();

        mPlayer = MediaPlayer.create(this, R.raw.alert);
    }

    private Runnable mEnableRunnable = new Runnable() {
        @Override
        public void run() {
            isBluetoothEnable();
            mHandlerBtEnable.postDelayed(this, 50000);
        }
    };

    private void initViews() {
        title = findViewById(R.id.social_title);
        btStatus = findViewById(R.id.social_bt_status);
        btOnOffBtn = findViewById(R.id.social_bt_on_off_btn);
        deviceListView = findViewById(R.id.social_device_list_view);
        closeBtn = findViewById(R.id.social_close_btn);

        mBTDevices = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        if (v == btOnOffBtn) {
            enableDisableBluetooth();
        } else if (v == closeBtn) {
            onBackPressed();
        }
    }

    private void isBluetoothEnable() {
        if (bluetoothAdapter.isEnabled()) {
            btStatus.setText("Bluetooth On");
            enableBluetoothButton();
            discoveryDevice();
        } else {
            btStatus.setText("Bluetooth Off");
            disableBluetoothButton();
        }
    }

    private void enableDisableBluetooth() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Does not have Bluetooth capabilities", Toast.LENGTH_SHORT).show();
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver, BTIntent);
            discoveryDevice();
        }
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver, BTIntent);
        }
    }

    private void discoveryDevice() {
        Log.d(TAG, "discoveryDevice: Looking for unpaired devices.");

        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "btnDiscover: Canceling discovery.");

            //check BT permissions in manifest
            checkBTPermissions();

            bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if (!bluetoothAdapter.isDiscovering()) {

            //check BT permissions in manifest
            checkBTPermissions();

            bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(bluetoothAdapter.ACTION_STATE_CHANGED)) {

                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, bluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE_OFF");
                        btStatus.setText("Bluetooth Off");
                        disableBluetoothButton();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "onReceive: STATE_TURNING_OFF");
                        btStatus.setText("Bluetooth Turning Off");
                        disableBluetoothButton();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "onReceive: STATE_ON");
                        btStatus.setText("Bluetooth On");
                        enableBluetoothButton();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "onReceive: STATE_TURNING_ON");
                        btStatus.setText("Bluetooth Turning On");
                        enableBluetoothButton();
                        break;
                }

            }
        }
    };

    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");
            mBTDevices.clear();

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);

                if (mPlayer.isPlaying()){
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = MediaPlayer.create(SocialDistancingActivity.this, R.raw.alert);
                }
                mPlayer.setLooping(false);
                mPlayer.start();

                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.layout_bt_device_item, mBTDevices);
                deviceListView.setAdapter(mDeviceListAdapter);
            }
        }
    };

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     * <p>
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    @SuppressLint("NewApi")
    private void checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    private void enableBluetoothButton() {
        btOnOffBtn.setText("ON");
        btOnOffBtn.setBackgroundResource(R.drawable.custom_on_button);
    }

    private void disableBluetoothButton() {
        btOnOffBtn.setText("OFF");
        btOnOffBtn.setBackgroundResource(R.drawable.custom_off_button);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            Log.d(TAG, "onDestroy: Try Unregister");
            unregisterReceiver(mBroadcastReceiver3);
            unregisterReceiver(mBroadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
