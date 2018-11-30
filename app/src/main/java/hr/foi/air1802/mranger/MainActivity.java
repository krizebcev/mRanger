package hr.foi.air1802.mranger;


import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //definiranje varijabli
    private Button gumbBTOnOff;
    private Button gumbDiscover;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gumbBTOnOff = findViewById(R.id.gumbBTOnOff);
        gumbDiscover = findViewById(R.id.gumbDiscover);
        Bluetooth.listaDiscoveredDevices = findViewById(R.id.listaDiscDevices);
        Bluetooth.listaDiscoveredDevices.setOnItemClickListener(MainActivity.this);
        Bluetooth.myBluetoothDevices = new ArrayList<>();
        Bluetooth.createBluetoothAdapter();

        //event za gumb ON/OFF
        gumbBTOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bluetooth.enableDisableBluetooth(getApplicationContext());
            }
        });

        gumbDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bluetooth.discoverBluetoothDevices(MainActivity.this, getApplicationContext(),myBroadcastReceiver);
            }
        });
    }//kraj OnCreate

    BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Bluetooth.myBluetoothDevices.add(device);
                Bluetooth.myDeviceListAdapter = new DeviceListAdapter(context, R.layout.activity_discover, Bluetooth.myBluetoothDevices);
                Bluetooth.listaDiscoveredDevices.setAdapter(Bluetooth.myDeviceListAdapter);
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bluetooth.startConnection( position, getApplicationContext());
    }
}
