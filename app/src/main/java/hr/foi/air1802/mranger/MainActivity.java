package hr.foi.air1802.mranger;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    BluetoothAdapter mBluetoothAdapter;

    private final BroadcastReceiver mBroadcastReciverl = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state= intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,mBluetoothAdapter.ERROR);

                switch (state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG,"onRecive:STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG,"mBroadcastReciverl:STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG,"mBroadcastReciverl:STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG,"mBroadcastReciverl:STATE TURNING ON");
                        break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroyCaled");
        super.onDestroy();
        unregisterReceiver(mBroadcastReciverl);
    }

    private Button gumbPoveziBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gumbPoveziBluetooth = (Button) findViewById(R.id.gumbPoveziBluetooth);

        mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();

        enableDesableBT();
        Log.d(TAG,"onClck:enabled/desabled Bluetooth");

        gumbPoveziBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent kontroleActivity = new Intent(getApplicationContext(), KontroleActivity.class);
                //startActivity(kontroleActivity);
            }
        }); //kraj gumbPoveziBluetooth


    }

    public void enableDesableBT() {
        if (mBluetoothAdapter==null){
            Log.d(TAG,"Nema bluetootha");
        }
        if(!mBluetoothAdapter.isEnabled()){
            Log.d(TAG,"enableDesable:enabling BT");
            Intent enabledBTIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enabledBTIntent);

            IntentFilter BTIntent= new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReciverl,BTIntent);

        }
        if (mBluetoothAdapter.isEnabled()){
            Log.d(TAG,"enableDesable:desabling BT");
            mBluetoothAdapter.disable();

            IntentFilter BTIntent= new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReciverl,BTIntent);
        }
    }
}
