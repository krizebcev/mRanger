package hr.foi.air1802.mranger;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Klasa DeviceListAdapter služi za prikupljanje svih pronađenih bluetooth uređaja te prikaz istih u Listi.
 * Lista se prikazuje na početnom zaslonu, a puni se na pritisak tipke OTKRIJ UREĐAJE.
 */
public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {

    private ArrayList<BluetoothDevice> myDevices;
    private LayoutInflater myLayoutInflater;
    private int myResourceId;

    /**
     * Konstruktor za klasu DeviceListAdapter
     * @param context - parametar u koji prosljeđujemo trenutni kontekst
     * @param resource - parametar u koji prosljeđujemo listu u kojoj se prikazuju uređaji
     * @param devices - parametar u koji se prosljeđuje lista pronađenih uređaja. Oni koji se prikazuju za odabir na zaslonu
     */
    public DeviceListAdapter(Context context, int resource, ArrayList<BluetoothDevice> devices) {
        super(context, resource, devices);

        this.myDevices = devices;
        myLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myResourceId = resource;
    }

    /**
     * Metoda koja prikazuje pronađene Bluetooth uređaje u listi, odnosno postavlja ih u listu.
     * @param position - pareametar koji iznačava poziciju uređaja u listi, tj. njegov index.
     * @param convertView - View u kojem se prikazuju pronađeni uređaji.
     * @param parent - grupa u kojoj se nalazi naš View.
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = myLayoutInflater.inflate(myResourceId, null);

        BluetoothDevice device = myDevices.get(position);

        if (device != null) {
            TextView deviceName = convertView.findViewById(R.id.labelDeviceName);
            TextView deviceAdress = convertView.findViewById(R.id.labelDeviceAddress);

            if (deviceName != null) {
                deviceName.setText(device.getName());
            }
            if (deviceAdress != null) {
                deviceAdress.setText(device.getAddress());
            }
        }
        return convertView;
    }
}
