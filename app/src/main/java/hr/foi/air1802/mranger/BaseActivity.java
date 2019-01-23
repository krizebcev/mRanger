package hr.foi.air1802.mranger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import hr.foi.air1802.fancymodule.FragmentFancyKontrole;
import hr.foi.air1802.sharedmodule.Controls;
import hr.foi.air1802.sharedmodule.IControls;

public class BaseActivity extends AppCompatActivity {

    IControls icontrols = new Controls();

    /**
     * Metoda koja čeka pritisak na gumb za povratak na prethodni zaslon.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        icontrols.disconnect(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Intent newint = getIntent();
        Controls.address = newint.getStringExtra(Bluetooth.extraAddress);
        String mode = newint.getStringExtra(Bluetooth.extraFancy);
        new ConnectBT(getApplicationContext(),this).execute();

        //ne radi operator ==, treba ici equals
        if (mode.equals("true")){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new FragmentFancyKontrole()).commit();
        }
        else{
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new FragmentBasicKontrole()).commit();
        }
    }
}
