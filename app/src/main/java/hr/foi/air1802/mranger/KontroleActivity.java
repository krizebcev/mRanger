package hr.foi.air1802.mranger;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class KontroleActivity extends AppCompatActivity {

    Button gumbDisconnect;
    Button gumbForward;
    Button gumbLeft;
    Button gumbRight;
    Button gumbBackwards;
    Button gumbSporo;
    Button gumbNormalno;
    Button gumbBrzo;

    //temperatura
    Button gumbTemperatura;
    Button gumbPohraniTemperaturu;

    StringBuilder messages;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Controls.Disconnect(KontroleActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontrole);
        Intent newint = getIntent();
        messages = new StringBuilder();
        Controls.address = newint.getStringExtra(Bluetooth.EXTRA_ADDRESS);
        new ConnectBT(getApplicationContext(),KontroleActivity.this).execute(); //konekcija
        gumbDisconnect = findViewById(R.id.gumbDisconnect);

        //kretanje
        gumbForward = findViewById(R.id.gumbForward);
        gumbLeft = findViewById(R.id.gumbLeft);
        gumbRight = findViewById(R.id.gumbRight);
        gumbBackwards = findViewById(R.id.gumbBackwards);

        //brzine
        gumbSporo = findViewById(R.id.gumbSporo);
        gumbNormalno = findViewById(R.id.gumbNormalno);
        gumbBrzo = findViewById(R.id.gumbBrzo);

        //boje gumbova brzina
        gumbSporo.setBackgroundResource(android.R.drawable.btn_default);
        gumbNormalno.setBackgroundColor(Color.parseColor("#fed63c"));
        gumbBrzo.setBackgroundResource(android.R.drawable.btn_default);

        //temperatura
        gumbTemperatura = findViewById(R.id.gumbTemperatura);
        gumbPohraniTemperaturu=findViewById(R.id.gumbPohraniTemperaturu);

        gumbDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controls.Disconnect(KontroleActivity.this);
            }
        });

        gumbForward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               return Controls.moveForward(event);
            }


        });

        gumbLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return Controls.moveLeft(event);
            }
        });

        gumbRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return Controls.moveRight(event);
            }
        });

        gumbBackwards.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               return  Controls.moveBackwards(event);
            }
        });

        gumbSporo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.changeSpeed(120, 120);

                gumbSporo.setBackgroundColor(Color.parseColor("#4eae68"));
                gumbNormalno.setBackgroundResource(android.R.drawable.btn_default);
                gumbBrzo.setBackgroundResource(android.R.drawable.btn_default);
            }
        });

        gumbNormalno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.changeSpeed(180, 180);

                gumbSporo.setBackgroundResource(android.R.drawable.btn_default);
                gumbNormalno.setBackgroundColor(Color.parseColor("#fed63c"));
                gumbBrzo.setBackgroundResource(android.R.drawable.btn_default);
            }
        });

        gumbBrzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.changeSpeed(240, 240);

                gumbSporo.setBackgroundResource(android.R.drawable.btn_default);
                gumbNormalno.setBackgroundResource(android.R.drawable.btn_default);
                gumbBrzo.setBackgroundColor(Color.parseColor("#e23232"));
            }
        });

        //temperatura
        gumbTemperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gumbTemperatura.setText(Controls.getTemperature());
            }
        });

        gumbPohraniTemperaturu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.insertTemperatueToDB(getApplicationContext());
            }
        });
    }

}
