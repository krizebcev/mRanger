package hr.foi.air1802.mranger;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.IOException;

import hr.foi.air1802.sharedmodule.Controls;

/**
 * Klasa koja služi za ostvarivanje i upravljanje Bluetooth konekcijom nad uređajem
 */
public class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
{
    Context context;
    Activity activity;

    /**
     * Konsturktor metode
     * @param context - kontekst klase KontroleActivity.java
     * @param activity - aktivnost klase KontroleActivity.java
     */
    public ConnectBT(Context context, Activity activity){
        this.context=context;
        this.activity=activity;
    }

    private boolean sonarConnectSuccess = true; //ako smo došli do ovdje, skoro smo se spojili

    /**
     * Stvaranje bluetooth veze između uređaja.
     * @param devices - parameetar kojim se prosljeđuje uređaj
     * @return - metoda uvijek vraća null
     */
    @Override
    protected Void doInBackground(Void... devices) //varijabilan broj parametara, dobro je to tako
    {
        try {
            if (Controls.bluetoothSocket == null || !Controls.isBluetoothConnected) //ako nismo spojeni
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH}, 1);
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 1);
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_PRIVILEGED}, 1);

                Controls.myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//dohvati naš bluetooth uređaj

                BluetoothDevice bluetoothRobot = Controls.myBluetoothAdapter.getRemoteDevice(Controls.address);

                //spaja se na adresu uređaja i provjerava da li je slobodna
                Controls.bluetoothSocket = bluetoothRobot.createInsecureRfcommSocketToServiceRecord(Controls.myUUID);//stvara RFCOMM (SPP) vezu
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                Controls.bluetoothSocket.connect();//počinje spajanje
            }
        } catch (IOException e) {
            sonarConnectSuccess = false;//ako se nismo uspjeli spojiti
        }
        return null;
    }

    /**
     * Status bluetooth veze. Ukoliko je veza ostvarena, ispisuje se "Povezivanje uspješno " inaće "Povezivanje neuspješno".
     * @param result
     */
    @Override
    protected void onPostExecute(Void result) //nakon pokušaja spajanja, provjeravamo da li je sve u redu
    {
        super.onPostExecute(result);

        if (!sonarConnectSuccess) {
            Toast.makeText(context, "Povezivanje neuspješno", Toast.LENGTH_SHORT).show();
            activity.finish();
        } else {
            Toast.makeText(context, "Povezivanje uspješno", Toast.LENGTH_SHORT).show();
            Controls.isBluetoothConnected = true;
        }

    }
}
