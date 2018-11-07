package hr.foi.air1802.mranger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                    myBluetoothAdapter.disable();
                    Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //isključi
                    startActivity(enableBTIntent);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
