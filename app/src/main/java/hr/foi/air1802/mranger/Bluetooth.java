package hr.foi.air1802.mranger;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.ActivityCompat;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public final class Bluetooth {
    public static BluetoothAdapter myBluetoothAdapter;
    public static ArrayList<BluetoothDevice> myBluetoothDevices;
    public static ListView listaDiscoveredDevices;
    public static DeviceListAdapter myDeviceListAdapter;
    private static String deviceAddress;
    public static String EXTRA_ADDRESS = "device_address";

    public static void createBluetoothAdapter(){
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }



    public static void enableDisableBluetooth(Context context){

        if (myBluetoothAdapter == null) { //ne podržava hr.foi.air1802.mranger.Bluetooth
            Toast.makeText(context,"Ovaj uređaj ne podržava Bluetooth konekciju.",Toast.LENGTH_LONG).show();
        }
        if (!myBluetoothAdapter.isEnabled()) { //hr.foi.air1802.mranger.Bluetooth nije uključen
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //uključi
            context.startActivity(enableBTIntent);
        }
        if (myBluetoothAdapter.isEnabled()) { //hr.foi.air1802.mranger.Bluetooth uključen
            myBluetoothAdapter.disable(); //isključi

            myBluetoothDevices.clear(); //ukoliko smo već otkrili neke, čistimo
            listaDiscoveredDevices.setAdapter(null);

            Toast.makeText(context,"Bluetooth isključen.",Toast.LENGTH_LONG).show();
        }
    }

    public  static void discoverBluetoothDevices(Activity activity, Context context, BroadcastReceiver myBroadcastReceiver){

        myBluetoothDevices.clear(); //ukoliko smo već otkrili neke, čistimo

        if (myBluetoothAdapter.isDiscovering()) {

            myBluetoothAdapter.cancelDiscovery(); //poništi otkrivanje
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            //provjera BT dopuštenja u manifestu

            myBluetoothAdapter.startDiscovery();
        }
        if (!myBluetoothAdapter.isDiscovering()) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            //provjera BT dopuštenja u manifestu

            myBluetoothAdapter.startDiscovery();
        }
        IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(myBroadcastReceiver, discoverDevicesIntent);

        if (!myBluetoothAdapter.isEnabled()) { //hr.foi.air1802.mranger.Bluetooth nije uključen
            Toast.makeText(context,"Potrebno je uključiti Bluetooth!",Toast.LENGTH_LONG).show();
        }
    }

    public static void startConnection( int position, Context context){
        myBluetoothAdapter.cancelDiscovery();

        deviceAddress = myBluetoothDevices.get(position).getAddress();
        myBluetoothDevices.get(position).createBond();

        Intent connection = new Intent(context, KontroleActivity.class);
        connection.putExtra(EXTRA_ADDRESS, deviceAddress);
        context.startActivity(connection);
    }
}
