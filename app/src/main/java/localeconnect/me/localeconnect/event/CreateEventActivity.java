package localeconnect.me.localeconnect.event;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import org.w3c.dom.Text;

import localeconnect.me.localeconnect.Event;
import localeconnect.me.localeconnect.R;

public class CreateEventActivity extends AppCompatActivity implements LocationListener {

    double longitute;
    double lattitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);


        Button mCreateEventButton = (Button) findViewById(R.id.post_event_button);
        mCreateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEvent();
            }
        });


    }


    private void createEvent(){

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location lastKnownLocation = null;
        try {
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            //for address https://developer.android.com/training/location/display-address.html

            Event event = new Event();
            event.setLattiude(lastKnownLocation.getLatitude());
            event.setLongtude(lastKnownLocation.getLongitude());

            TextView pTextView = (TextView) findViewById(R.id.lc_preferenceTextView);

            TextView pDescTextView = (TextView) findViewById(R.id.lc_preferenceDescTextViewDesc);

            String intent = pTextView.getText().toString();
            String intentDesc = pDescTextView.getText().toString();
            event.setPrefId(intent);


            Toast.makeText(CreateEventActivity.this, event.toString(), Toast.LENGTH_LONG).show();





        }
        catch (SecurityException e){
            Toast.makeText(CreateEventActivity.this, "Location Service Not Enabled", Toast.LENGTH_SHORT).show();
        }



    }

    public void onLocationChanged(Location location) {
        longitute = location.getLongitude();
        lattitude = location.getLatitude();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //longitudeValueNetwork.setText(longitudeNetwork + "");
                //latitudeValueNetwork.setText(latitudeNetwork + "");
                Toast.makeText(CreateEventActivity.this, "Network Provider update", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }
    @Override
    public void onProviderEnabled(String s) {
    }
    @Override
    public void onProviderDisabled(String s) {
    }
}
