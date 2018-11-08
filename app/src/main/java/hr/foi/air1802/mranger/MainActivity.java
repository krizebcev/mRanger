package hr.foi.air1802.mranger;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //definiranje varijabli
    private Button gumbBTOnOff;
    private Button gumbDiscover;
    private ListView listaDiscoveredDevices;

    private BluetoothAdapter myBluetoothAdapter;
    private ArrayList<BluetoothDevice> myBluetoothDevices;

    public DeviceListAdapter myDeviceListAdapter;

    private String deviceAddress;
    public static String EXTRA_ADDRESS = "device_address";

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
        listaDiscoveredDevices = findViewById(R.id.listaDiscDevices);
        listaDiscoveredDevices.setOnItemClickListener(MainActivity.this);

        myBluetoothDevices = new ArrayList<>();
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //event za gumb ON/OFF
        gumbBTOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myBluetoothAdapter == null) { //ne podržava Bluetooth
                    Toast.makeText(getApplicationContext(),"This device does not support Bluetooth connection.",Toast.LENGTH_LONG).show();
                }
                if (!myBluetoothAdapter.isEnabled()) { //Bluetooth nije uključen
                    Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //uključi
                    startActivity(enableBTIntent);
                }
                if (myBluetoothAdapter.isEnabled()) { //Bluetooth uključen
                    myBluetoothAdapter.disable(); //isključi

                    myBluetoothDevices.clear(); //ukoliko smo već otkrili neke, čistimo
                    listaDiscoveredDevices.setAdapter(null);

                    Toast.makeText(getApplicationContext(),"Bluetooth isključen.",Toast.LENGTH_LONG).show();
                }
            }
        });

        gumbDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myBluetoothDevices.clear(); //ukoliko smo već otkrili neke, čistimo

                if (myBluetoothAdapter.isDiscovering()) {

                    myBluetoothAdapter.cancelDiscovery(); //poništi otkrivanje
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    //provjera BT dopuštenja u manifestu

                    myBluetoothAdapter.startDiscovery();
                }
                if (!myBluetoothAdapter.isDiscovering()) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    //provjera BT dopuštenja u manifestu

                    myBluetoothAdapter.startDiscovery();
                }
                IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(myBroadcastReceiver, discoverDevicesIntent);

                if (!myBluetoothAdapter.isEnabled()) { //Bluetooth nije uključen
                    Toast.makeText(getApplicationContext(),"Potrebno je uključiti Bluetooth!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }//kraj OnCreate

    BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                myBluetoothDevices.add(device);

                myDeviceListAdapter = new DeviceListAdapter(context, R.layout.activity_discover, myBluetoothDevices);
                listaDiscoveredDevices.setAdapter(myDeviceListAdapter);
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        myBluetoothAdapter.cancelDiscovery();

        deviceAddress = myBluetoothDevices.get(position).getAddress();
        myBluetoothDevices.get(position).createBond();

        Intent connection = new Intent(MainActivity.this, KontroleActivity.class);
        connection.putExtra(EXTRA_ADDRESS, deviceAddress);
        startActivity(connection);
    }
}
