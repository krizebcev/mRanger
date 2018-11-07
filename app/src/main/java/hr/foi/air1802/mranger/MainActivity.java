package hr.foi.air1802.mranger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    //definiranje varijabli
    private Button gumbBTOnOff;
    private Button gumbDiscover;
    private ListView listaDiscoveredDevices;


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
