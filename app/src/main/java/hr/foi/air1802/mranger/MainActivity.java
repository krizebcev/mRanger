package hr.foi.air1802.mranger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button gumbPoveziBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gumbPoveziBluetooth = (Button) findViewById(R.id.gumbPoveziBluetooth);

        gumbPoveziBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent kontroleActivity = new Intent(getApplicationContext(), KontroleActivity.class);
                //startActivity(kontroleActivity);
            }
        }); //kraj gumbPoveziBluetooth


    }
}
