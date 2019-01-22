package hr.foi.air1802.mranger;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {

    private String mode;
    private Fragment mFragment;

    /**
     * Metoda koja čeka pritisak na gumb za povratak na prethodni zaslon.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Controls.disconnect(this);
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
