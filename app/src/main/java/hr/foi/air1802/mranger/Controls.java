package hr.foi.air1802.mranger;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Klasa koja služi za upravljanje kontrolama na robotu
 */
public final class Controls {

    public static String address = null;
    public static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static BluetoothAdapter myBluetoothAdapter = null;
    public static BluetoothSocket bluetoothSocket = null;
    public static boolean isBluetoothConnected = false;
    private static float globTemp;
    private static boolean isTemperatureRead=false;
    private static float proslaTemp;

    //podaci za kretanje
    private static byte[] cmd = new byte[13];
    public static final int WRITEMODULE = 2;
    public static final int type = 5;
    private static int DesniMotor = 180;
    private static int LijeviMotor = 180;

    /**
     * Metoda u kojoj se šalju podaci za kretanje robota
     * @param lijeviMotor brzina lijevog motora
     * @param desniMotor brzina desnog motora
     */
    private static void move(int lijeviMotor, int desniMotor) {
        cmd[0] = (byte) 0xff;
        cmd[1] = (byte) 0x55;
        cmd[2] = (byte) 8;
        cmd[3] = (byte) 0;
        cmd[4] = (byte) WRITEMODULE;
        cmd[5] = (byte) type;
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
        cmd[10] = (byte) '\n';

        try {
            bluetoothSocket.getOutputStream().write(cmd);
        } catch (IOException e) {
        }
    }

    /**
     * Metoda koja služi za kretanje robota prema ravno
     * @param event objekt u kojem se prosljeđuje događaj
     * @return
     */
    public static boolean moveForward(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            move(-LijeviMotor, DesniMotor);

        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            move(0, 0);
        }
        return true;
    }

    /**
     * Metoda koja služi za skretanje robota ulijevo
     * @param event objekt u kojem se prosljeđuje događaj
     * @return
     */
    public static boolean moveLeft(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            move(-LijeviMotor, -DesniMotor);

        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            move(0, 0);
        }
        return true;
    }

    /**
     * Metoda koja služi za skretanje robota udesno
     * @param event objekt u kojem se prosljeđuje događaj
     * @return
     */
    public static boolean moveRight(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            move(LijeviMotor, DesniMotor);

        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            move(0, 0);
        }
        return true;
    }

    /**
     * Metoda koja služi za kretanje robota unatrag
     * @param event objekt u kojem se prosljeđuje događaj
     * @return
     */
    public  static  boolean moveBackwards(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            move(LijeviMotor, -DesniMotor);

        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            move(0, 0);
        }
        return true;
    }

    /**
     * Metoda koja služi za promjenu brzine kretanja robota
     * @param desniMotor brzina desnog motora
     * @param lijeviMotor brzina lijevog motora
     */
    public static void changeSpeed(int desniMotor, int lijeviMotor) {
        DesniMotor = desniMotor;
        LijeviMotor = lijeviMotor;
    }

    /**
     * Metoda koja služi za dohvaćanje temperature s robota
     * @return
     */
    public static String getTemperature() {
        String test = "";

        byte[] cmd = new byte[9];
        cmd[0] = (byte) 0xff; //0xff
        cmd[1] = (byte) 0x55; //0x55
        cmd[2] = (byte) 5; //5
        cmd[3] = (byte) 7; //7 indeks na kojem se nalazi senzor
        cmd[4] = (byte) 1; //1
        cmd[5] = (byte) 27; //27 tip uređaja
        cmd[6] = (byte) (13 & 0xff); //13&0xff- mora biti port 13 za senzor
        cmd[7] = (byte) (2 & 0xff); //2&0xff- slot senzora čije uzimamo vrijednosti
        cmd[8] = (byte) '\n'; // '\n'

        try {
            bluetoothSocket.getOutputStream().write(cmd);//uključivanje moda mjerenja temperature
            int byteCount = bluetoothSocket.getInputStream().available();
            while (byteCount == 0) {
                byteCount = bluetoothSocket.getInputStream().available();
            }

            if (byteCount > 0) {
                InputStream inputStream = bluetoothSocket.getInputStream();//uzimanje podataka od robota
                byte[] podaci = new byte[1024];
                int count = inputStream.read(podaci);

                if (podaci != null) {

                    List<Byte> listaBajtova = new ArrayList<>();

                    if (count > 0) {
                        for (int i = 0; i < count; i++) {
                            Byte b = podaci[i];
                            listaBajtova.add(b);
                            Byte[] novaLista = listaBajtova.toArray(new Byte[listaBajtova.size()]);
                            int[] buffer = new int[listaBajtova.size()];
                            for (int j = 0; j < novaLista.length; j++) {
                                test += String.format("%02X ", novaLista[j]);
                                buffer[j] = novaLista[j];
                            }
                            listaBajtova.clear();
                        }
                    }
                }
            }
        } catch (IOException e) {        }

        String[] dijelovi = test.split(" ");
        test = dijelovi[6]; // dohvaćamo sa 6. indeksa buffera
        int temp = Integer.parseInt(test, 16);
        float temperatura = (float) temp / 10;
        globTemp=temperatura;
        isTemperatureRead=true;
        test = String.valueOf(temperatura) + " °C";
        return test;
    }

    /**
     * Metoda koja služi za prekid Bluetooth veze s robotom
     * @param activity aktivnost koja se završava ovom metodom
     */
    public static void Disconnect(Activity activity) {
        if (bluetoothSocket != null) //ako smo spojeni
        {
            try {
                bluetoothSocket.close(); //prekini vezu
            } catch (IOException e) {}
        }
        activity.finish();
    }

    /**
     * Metoda koja služi za slanje temperature u bazu podataka
     * @param context objekt u kojem se prosljeđuje trenutni kontekst
     */
    public static void insertTemperatueToDB(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://mranger.foi.hr/unos.php?temp="+globTemp;

        if (isTemperatureRead) {
            if (proslaTemp == globTemp){
                Toast.makeText(context, "Temperatura se nije promijenila.", Toast.LENGTH_LONG).show();
            }
            else {
                proslaTemp = globTemp;
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,null,null);

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
                Toast.makeText(context, "Temperatura upisana u bazu..", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(context, "Prvo je potrebno očitati temperaturu.", Toast.LENGTH_LONG).show();

        }
    }
}
