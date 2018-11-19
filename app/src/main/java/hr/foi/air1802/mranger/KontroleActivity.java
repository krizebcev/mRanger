package hr.foi.air1802.mranger;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KontroleActivity extends AppCompatActivity {

    String address = null;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothAdapter myBluetoothAdapter = null;
    BluetoothSocket bluetoothSocket = null;

    private boolean isBluetoothConnected = false;

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

    InputStream inputStream = null;
    String incomingMessage;
    StringBuilder messages;

    //za kretanje podaci
    byte[] cmd = new byte[13];
    public static final int WRITEMODULE = 2;
    public static final int type = 5;

    private int DesniMotor = 180;
    private int LijeviMotor = 180;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Disconnect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontrole);
        Intent newint = getIntent();
        messages = new StringBuilder();
        address = newint.getStringExtra(MainActivity.EXTRA_ADDRESS);
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

        new ConnectBT().execute();

        gumbDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Disconnect();
            }
        });

        gumbForward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    Kretanje(-LijeviMotor,DesniMotor);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    Kretanje(0,0);
                }
                return true;
            }


        });

        gumbLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    Kretanje(-LijeviMotor,-DesniMotor);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    Kretanje(0,0);
                }
                return true;
            }
        });

        gumbRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    Kretanje(LijeviMotor,DesniMotor);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    Kretanje(0,0);
                }
                return true;
            }
        });

        gumbBackwards.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    Kretanje(LijeviMotor,-DesniMotor);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    Kretanje(0,0);
                }
                return true;
            }
        });

        gumbSporo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromjenaBrzine(120, 120);

                gumbSporo.setBackgroundColor(Color.parseColor("#4eae68"));
                gumbNormalno.setBackgroundResource(android.R.drawable.btn_default);
                gumbBrzo.setBackgroundResource(android.R.drawable.btn_default);
            }
        });

        gumbNormalno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromjenaBrzine(180,180);

                gumbSporo.setBackgroundResource(android.R.drawable.btn_default);
                gumbNormalno.setBackgroundColor(Color.parseColor("#fed63c"));
                gumbBrzo.setBackgroundResource(android.R.drawable.btn_default);
            }
        });

        gumbBrzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromjenaBrzine(240,240);

                gumbSporo.setBackgroundResource(android.R.drawable.btn_default);
                gumbNormalno.setBackgroundResource(android.R.drawable.btn_default);
                gumbBrzo.setBackgroundColor(Color.parseColor("#e23232"));
            }
        });

        //temperatura

        gumbTemperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gumbTemperatura.setText(DohvatiTemperaturu());
            }
        });

    }


    //metoda za kretanje
    private void Kretanje(int lijeviMotor,int desniMotor)
    {
        cmd[0]=(byte) 0xff;
        cmd[1]=(byte) 0x55;
        cmd[2]=(byte) 8;
        cmd[3]=(byte) 0;
        cmd[4]=(byte) WRITEMODULE;
        cmd[5]=(byte) type;
        final ByteBuffer buf = ByteBuffer.allocate(4);
        buf.putShort((short) lijeviMotor);
        buf.putShort((short) desniMotor);
        buf.position(0);
        // Read back bytes
        final byte b1 = buf.get();
        final byte b2 = buf.get();
        final byte b3 = buf.get();
        final byte b4 = buf.get();
        cmd[6] = b2;
        cmd[7] = b1;
        cmd[8] = b4;
        cmd[9] = b3;
        cmd[10]=(byte) '\n';

        try{
            bluetoothSocket.getOutputStream().write(cmd);
        }catch(IOException e){

        }
    }

    //metoda za promjenu brzine kretanja
    private void PromjenaBrzine(int desniMotor, int lijeviMotor){
        DesniMotor = desniMotor;
        LijeviMotor = lijeviMotor;
    }

    private String DohvatiTemperaturu(){
        String test ="";

        byte[] cmd = new byte[9];
        cmd[0]=(byte) 0xff; //0xff
        cmd[1]=(byte) 0x55; //0x55
        cmd[2]=(byte) 5; //5
        cmd[3]=(byte) 0; //3
        cmd[4]=(byte) 1; //1
        cmd[5]=(byte) 2; //2
        cmd[6]=(byte) (13); //6&0xff
        cmd[7]=(byte) (2); //7&0xff
        cmd[8]=(byte) '\n'; // '\n'

        try{
            bluetoothSocket.getOutputStream().write(cmd);
        }catch(IOException e){}

        try {
            int byteCount = bluetoothSocket.getInputStream().available();

            while(byteCount == 0){
                byteCount = bluetoothSocket.getInputStream().available();
            }

            if(byteCount > 0) {
                InputStream inputStream = bluetoothSocket.getInputStream();
                byte[] podaci = new byte[1024];
                int count = inputStream.read(podaci);

                if (podaci != null) {

                List<Byte> listaBajtova = new ArrayList<Byte>();

                if(count > 0){
                    for (int i=0;i<count;i++){
                        Byte b = podaci[i];
                        listaBajtova.add(b);
                        Byte[] novaLista = listaBajtova.toArray(new Byte[listaBajtova.size()]);
                        int[] buffer = new int[listaBajtova.size()];
                        for (int j=0; j<novaLista.length;j++){
                            test+=String.format("%02X ", novaLista[j]);
                            buffer[j] = novaLista[j];
                        }
                        listaBajtova.clear();
                    }
                } }
                }
        }catch (IOException e)
        {
        }

        /*String[] dijelovi = test.split(" ");
        test= dijelovi[8];
        int temp = Integer.parseInt(test, 16);
        test = ""+temp; */
        return test;
    }

    //metoda za odspajanje
    private void Disconnect(){
        if (bluetoothSocket!=null) //ako smo spojeni
        {
            try
            {
                bluetoothSocket.close(); //prekini vezu
            }
            catch (IOException e)
            { }
        }
        finish();
    }

    //OVO NAS ZANIMA -> VIDLI NA INTERNETU (KINEZ NA git/makeblock-official.cc) test temp
    Thread ListenInput=new Thread(){

    };


    public class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //ako smo došli do ovdje, skoro smo se spojili

        @Override
        protected void onPreExecute()
        {
            Toast.makeText(getApplicationContext(),"Povezivanje....",Toast.LENGTH_LONG).show(); //dok traje spajanje
        }

        @Override
        protected Void doInBackground(Void... devices) //varijabilan broj parametara, dobro je to tako
        {
            try
            {
                if (bluetoothSocket == null || !isBluetoothConnected) //ako nismo spojeni
                {
                    ActivityCompat.requestPermissions(KontroleActivity.this,new String[]{Manifest.permission.BLUETOOTH},1);
                    ActivityCompat.requestPermissions(KontroleActivity.this,new String[]{Manifest.permission.BLUETOOTH_ADMIN},1);
                    ActivityCompat.requestPermissions(KontroleActivity.this,new String[]{Manifest.permission.BLUETOOTH_PRIVILEGED},1);

                    myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//dohvati naš bluetooth uređaj

                    BluetoothDevice bluetoothRobot = myBluetoothAdapter.getRemoteDevice(address);

                    //spaja se na adresu uređaja i provjerava da li je slobodna
                    bluetoothSocket = bluetoothRobot.createInsecureRfcommSocketToServiceRecord(myUUID);//stvara RFCOMM (SPP) vezu
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    bluetoothSocket.connect();//počinje spajanje
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//ako se nismo uspjeli spojiti
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //nakon pokušaja spajanja, provjeravamo da li je sve u redu
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                Toast.makeText(getApplicationContext(),"Povezivanje neuspješno",Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Povezivanje uspješno",Toast.LENGTH_SHORT).show();
                isBluetoothConnected = true;
            }

        }
    }
}
