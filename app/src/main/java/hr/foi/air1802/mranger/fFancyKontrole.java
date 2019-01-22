package hr.foi.air1802.mranger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class fFancyKontrole extends Fragment {

    ImageButton gumbDisconnect;
    ImageButton gumbForward;
    ImageButton gumbLeft;
    ImageButton gumbRight;
    ImageButton gumbBackwards;
    ImageButton gumbSporo;
    ImageButton gumbNormalno;
    ImageButton gumbBrzo;

    //temperatura
    ImageButton gumbTemperatura;
    ImageButton gumbPohraniTemperaturu;
    TextView textViewTemperatura;

    StringBuilder messages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fancy, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        messages = new StringBuilder();
        gumbDisconnect = getView().findViewById(R.id.imageButtonOdspoji);

        //kretanje
        gumbForward = getView().findViewById(R.id.imageButtonGore);
        gumbLeft = getView().findViewById(R.id.imageButtonLijevo);
        gumbRight = getView().findViewById(R.id.imageButtonDesno);
        gumbBackwards = getView().findViewById(R.id.imageButtonDolje);

        //brzine
        gumbSporo = getView().findViewById(R.id.imageButtonSporo);
        gumbNormalno = getView().findViewById(R.id.imageButtonNormalno);
        gumbBrzo = getView().findViewById(R.id.imageButtonBrzo);

        //boje gumbova brzina
        gumbNormalno.setImageResource(R.drawable.normalno_stisnuto);

        //temperatura
        gumbTemperatura = getView().findViewById(R.id.imageButtonTemperatura);
        gumbPohraniTemperaturu=getView().findViewById(R.id.imageButtonDodajTemp);
        textViewTemperatura = getView().findViewById(R.id.textViewTemperatura);
        textViewTemperatura.setTextColor(Color.WHITE);

        /**
         * Metoda koja se aktivira na pritisak gumba za prekidanje bluetooth veze.
         */
        gumbDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controls.disconnect(getActivity());
            }
        });
        /**
         *  Metoda koja se aktivira na pritisak gumba za kretanje unaprijed.
         */
        gumbForward.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                    Controls.moveForward(event);
                    gumbForward.setImageResource(R.drawable.gore_stisnuto);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP ) {
                    Controls.moveForward(event);
                    gumbForward.setImageResource(R.drawable.gore);
                }
                return false;
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za kretanje ulijevo.
         */
        gumbLeft.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                    Controls.moveLeft(event);
                    gumbLeft.setImageResource(R.drawable.lijevo_stisnuto);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP ) {
                    Controls.moveLeft(event);
                    gumbLeft.setImageResource(R.drawable.lijevo);
                }
                return false;
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za kretanje udesno.
         */
        gumbRight.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                    Controls.moveRight(event);
                    gumbRight.setImageResource(R.drawable.desno_stisnuto);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP ) {
                    Controls.moveRight(event);
                    gumbRight.setImageResource(R.drawable.desno);
                }
                return false;
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za kretanje unazad.
         */
        gumbBackwards.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                    Controls.moveBackwards(event);
                    gumbBackwards.setImageResource(R.drawable.dolje_stisnuto);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP ) {
                    Controls.moveBackwards(event);
                    gumbBackwards.setImageResource(R.drawable.dolje);
                }
                return false;
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za promijenu brzine robota na sporo.
         */
        gumbSporo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.changeSpeed(120, 120);

                gumbSporo.setImageResource(R.drawable.sporo_stisnuto);
                gumbNormalno.setImageResource(R.drawable.normalno);
                gumbBrzo.setImageResource(R.drawable.brzo);

            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za promijenu brzine robota na normalno.
         */
        gumbNormalno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.changeSpeed(180, 180);

                gumbSporo.setImageResource(R.drawable.sporo);
                gumbNormalno.setImageResource(R.drawable.normalno_stisnuto);
                gumbBrzo.setImageResource(R.drawable.brzo);
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za promijenu brzine robota na brzo.
         */
        gumbBrzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.changeSpeed(240, 240);

                gumbSporo.setImageResource(R.drawable.sporo);
                gumbNormalno.setImageResource(R.drawable.normalno);
                gumbBrzo.setImageResource(R.drawable.brzo_stisnuto);
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za očitanje temperature.
         */
        gumbTemperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewTemperatura.setText(Controls.getTemperature());
            }
        });

        /**
         * Metoda koja se aktivira na pritisak gumba za pohranjivanje temperature u bazu.
         */
        gumbPohraniTemperaturu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.insertTemperatueToDB(getContext());
            }
        });
    }
}
