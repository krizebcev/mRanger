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

/**
 * Klasa koja služi za uključivanje/isključivanje Bluetootha, otkirvanje uređaja i stvaranje konekcije
 */
public class Bluetooth {

    private Bluetooth(){ }

    protected static BluetoothAdapter myBluetoothAdapter;
    protected static ArrayList<BluetoothDevice> myBluetoothDevices;
    protected static ListView listaDiscoveredDevices;
    protected static DeviceListAdapter myDeviceListAdapter;
    protected static String deviceAddress;
    protected static String extraAddress = "device_address";

    /**
     * Metoda koji služi za stvaranje novog Bluetooth adaptera
     */
    public static void createBluetoothAdapter(){
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * Metoda koja služi za uključivanje/isključivanje Bluetootha
     * @param context - parametar u kojem prosljeđujemo trenutni kontekst
     */
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

    /**
     * Metoda koja služi za otkrivanje uređaja koji su vidiljivi putem Bluetooth
     * @param activity - parametar u kojem prosljeđujemo activity
     * @param context - parametar u kojem se prosljeđuje kontekst
     * @param myBroadcastReceiver - parametar u kojem prosljeđujemo BroadcastReceiver
     */
    public  static void discoverBluetoothDevices(Activity activity, Context context, BroadcastReceiver myBroadcastReceiver){
        myBluetoothDevices.clear(); //ukoliko smo već otkrili neke, čistimo
        listaDiscoveredDevices.setAdapter(null);//ukoliko smo već otkrili neke, čistimo
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

    /**
     * Metoda koja započinje konekciju sa odabranim uređajem iz liste otkrivenih uređaja
     * @param position - parametar u kojem se prosljeđuje pozicija uređaja u listi otkirvenih uređaja
     * @param context - parametar u kojem se prosljeđuje kontekst
     */
    public static void startConnection( int position, Context context, boolean prekidac){
        myBluetoothAdapter.cancelDiscovery();

        deviceAddress = myBluetoothDevices.get(position).getAddress();
        myBluetoothDevices.get(position).createBond();

        Intent connection=null;
        if (!prekidac)
        {
            connection = new Intent(context, KontroleActivity.class);
        }
        else
        {
            connection = new Intent(context, FancyKontroleActivity.class);
        }
        connection.putExtra(extraAddress, deviceAddress);
        context.startActivity(connection);
        Controls.changeSpeed(180, 180);
    }
}
