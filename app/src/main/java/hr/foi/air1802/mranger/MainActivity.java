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
import android.widget.Switch;

import java.util.ArrayList;

/**
 * Početni zaslon koji se otvara prilikom pokretanja aplikacjie.
 * MainActivity nasljeđuje klasu AppCompactActivity te njegove metode i klase.
 * Na zaslonu je omogućeno paljenje i gašenje Bluetooth-a te pronalazak uređaja na kojih se može povezat.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //definiranje varijabli
    private Button gumbBTOnOff;
    private Button gumbDiscover;

    //fancy switch zastavica
    private Switch prekidac;

    /**
     * Početna metoda koja se izvrši prilikom pokretanja zaslona.
     * Pronalazak i inicijalizacija elemenata sa zaslona.
     * Prilikom pritiska na gumbBTOnOff se uključuje ili isključuje Bluetooth
     * Prilikom pritiska na gumbDiscover se pronalaze i izlistaju svi mogući uređaji na koji se aplikacija možepovezat
     * @param savedInstanceState - parametar koji sadrži prošlo stanje zaslona kako se nebi izgubili podaci prilikom povratka na isti
     */
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

        //Paljenje gašenje Bluetooth-a
        gumbBTOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bluetooth.enableDisableBluetooth(getApplicationContext());
            }
        });

        //Pronalaženje Bluetooth uređaja
        gumbDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bluetooth.discoverBluetoothDevices(MainActivity.this, getApplicationContext(),myBroadcastReceiver);
            }
        });

        //fancy switch
        prekidac = findViewById(R.id.switchFancy);
        prekidac.setChecked(true);
    }//kraj OnCreate

    /**
     * Metoda koja se pokreće prolikom završetka izvođenja Zaslona tj.
     * u njoj se gase ili brišu razni podaci koji su se dosad koristili.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    /**
     *Inicijalizacija klase BroadcastReceiver koja se brine za odašiljanje infromacija, u ovom slučaju
     * preko Bluetooth signala
     */
    BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Bluetooth.myBluetoothDevices.add(device);
                Bluetooth.myDeviceListAdapter = new DeviceListAdapter(context, R.layout.discover, Bluetooth.myBluetoothDevices);
                Bluetooth.listaDiscoveredDevices.setAdapter(Bluetooth.myDeviceListAdapter);
            }
        }
    };

    /**
     * onItemClick je ugrađena metoda koja reagira na pritisak na element iz liste, u ovom slučaju naneki pronađeni Bluetooth uređaj.
     * Pritiskom na uređaj iz liste, aplikacija se automatski poveže na uređaj te se prelazi na idući zaslon.
     * @param parent - lista u kojoj se nalaze pronađeni uređaji
     * @param view - parametar u kojem se prosljeđuje View
     * @param position - pozicija elementa u listi, neki broj ovisno o duljini liste
     * @param id - dodatni parametar u kojem se može prosljediti neki dodatni id od kliknutog elementa
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bluetooth.startConnection( position, getApplicationContext(), prekidac.isChecked());
    }
}
