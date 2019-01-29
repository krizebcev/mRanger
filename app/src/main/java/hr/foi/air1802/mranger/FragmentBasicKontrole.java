package hr.foi.air1802.mranger;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hr.foi.air1802.sharedmodule.Controls;
import hr.foi.air1802.sharedmodule.IControls;

/**
 * Fragment za kontrolu robota putem jednostavnog izgleda, koristi se kod BaseActivity-a
 */
public class FragmentBasicKontrole extends Fragment{

    IControls icontrols = new Controls();

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

    /**
     * Metoda koja omogućuje prikaz fragmenta na određenom mjestu, u ovom slučaju kod BaseActivity-a zaslona
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_f_basic_kontrole, container, false);
    }

    /**
     * Metoda koja se poziva prilikom prikaza fragmenta, inicijalizacija elementata sa fragmenta te njihovih funkcionalnosti
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gumbDisconnect = getView().findViewById(R.id.gumbDisconnect);

        //kretanje
        gumbForward = getView().findViewById(R.id.gumbForward);
        gumbLeft = getView().findViewById(R.id.gumbLeft);
        gumbRight = getView().findViewById(R.id.gumbRight);
        gumbBackwards = getView().findViewById(R.id.gumbBackwards);

        //brzine
        gumbSporo = getView().findViewById(R.id.gumbSporo);
        gumbNormalno = getView().findViewById(R.id.gumbNormalno);
        gumbBrzo = getView().findViewById(R.id.gumbBrzo);

        //boje gumbova brzina
        gumbSporo.setBackgroundResource(android.R.drawable.btn_default);
        gumbNormalno.setBackgroundColor(Color.parseColor("#fed63c"));
        gumbBrzo.setBackgroundResource(android.R.drawable.btn_default);

        //temperatura
        gumbTemperatura = getView().findViewById(R.id.gumbTemperatura);
        gumbPohraniTemperaturu=getView().findViewById(R.id.gumbPohraniTemperaturu);

        /**
         * Metoda koja se aktivira na pritisak gumba za prekidanje bluetooth veze.
         */
        gumbDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icontrols.disconnect(getActivity());
            }
        });

        /**
         *  Metoda koja se aktivira na pritisak gumba za kretanje unaprijed.
         */
        gumbForward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return icontrols.moveForward(event);
            }


        });

        /**
         * Metoda koja se aktivira na pritisak gumba za kretanje ulijevo.
         */
        gumbLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return icontrols.moveLeft(event);
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za kretanje udesno.
         */
        gumbRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return icontrols.moveRight(event);
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za kretanje unazad.
         */
        gumbBackwards.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return  icontrols.moveBackwards(event);
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za promijenu brzine robota na sporo.
         */
        gumbSporo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icontrols.changeSpeed(120, 120);

                gumbSporo.setBackgroundColor(Color.parseColor("#4eae68"));
                gumbNormalno.setBackgroundResource(android.R.drawable.btn_default);
                gumbBrzo.setBackgroundResource(android.R.drawable.btn_default);
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za promijenu brzine robota na normalno.
         */
        gumbNormalno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icontrols.changeSpeed(180, 180);

                gumbSporo.setBackgroundResource(android.R.drawable.btn_default);
                gumbNormalno.setBackgroundColor(Color.parseColor("#fed63c"));
                gumbBrzo.setBackgroundResource(android.R.drawable.btn_default);
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za promijenu brzine robota na brzo.
         */
        gumbBrzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icontrols.changeSpeed(240, 240);

                gumbSporo.setBackgroundResource(android.R.drawable.btn_default);
                gumbNormalno.setBackgroundResource(android.R.drawable.btn_default);
                gumbBrzo.setBackgroundColor(Color.parseColor("#e23232"));
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za očitanje temperature.
         */
        gumbTemperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gumbTemperatura.setText(icontrols.getTemperature());
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za pohranjivanje temperature u bazu.
         */
        gumbPohraniTemperaturu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icontrols.insertTemperatueToDB(getContext());
            }
        });
    }
}
