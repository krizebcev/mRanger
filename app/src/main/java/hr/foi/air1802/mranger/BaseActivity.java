package hr.foi.air1802.mranger;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hr.foi.air1802.fancymodule.fFancyKontrole;
import hr.foi.air1802.sharedmodule.Controls;
import hr.foi.air1802.sharedmodule.IControls;

public class BaseActivity extends AppCompatActivity {

    IControls icontrols = new Controls();

    private String mode;
    private Fragment mFragment;

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
        mode = newint.getStringExtra(Bluetooth.extraFancy);
        new ConnectBT(getApplicationContext(),this).execute();

        //ne radi operator ==, treba ici equals
        if (mode.equals("true")){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new fFancyKontrole()).commit();
        }
        else{
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new fBasicKontrole()).commit();
        }

    }
}
