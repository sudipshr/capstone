package localeconnect.me.localeconnect.event;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import localeconnect.me.localeconnect.Event;
import localeconnect.me.localeconnect.LocaleApp;
import localeconnect.me.localeconnect.Preference;
import localeconnect.me.localeconnect.R;
import localeconnect.me.localeconnect.User;
import localeconnect.me.localeconnect.profile.PreferenceActivity;
import localeconnect.me.localeconnect.service.Service;

public class CreateEventActivity extends AppCompatActivity implements LocationListener {

    private double longitute;
    private double lattitude;
    private Service service;

    private AsyncTask evtTask;

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

        this.service = new Service();


    }


    private void createEvent(){

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location lastKnownLocation = null;
        try {
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            //for address https://developer.android.com/training/location/display-address.html


            LocaleApp appContext = (LocaleApp) getApplicationContext();
            User user = appContext.getUser();

            Event event = new Event();
            event.setLatitude(lastKnownLocation.getLatitude());
            event.setLongitude(lastKnownLocation.getLongitude());
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> address = geocoder.getFromLocation(event.getLatitude(), event.getLongitude(), 1);
            //lastKnownLocation.distanceTo(lastKnownLocation);
            if (address != null && address.size() > 0){
                event.setAddress(address.get(0).getAddressLine(0));
                event.setCity(address.get(0).getLocality());
            }
            event.setEventTime(new Date());
            event.setInitiatingUserId(user.getId());
            TextView pTextView = (TextView) findViewById(R.id.lc_preferenceTextView);

            String intent = pTextView.getText().toString();

            event.setPrefId(intent);

            Toast.makeText(CreateEventActivity.this, event.toString(), Toast.LENGTH_SHORT).show();
            CreateEventTask task = new CreateEventTask(event, this.service, false);
            task.execute();






        }
        catch (SecurityException e){
            e.printStackTrace();

            Toast.makeText(CreateEventActivity.this, "Location Occurred: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();

            Toast.makeText(CreateEventActivity.this, "Error Occurred: "+e.getMessage(), Toast.LENGTH_SHORT).show();
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


    private class CreateEventTask extends AsyncTask<Void, Void, Event> {

        private final Event mEvent;
        private boolean mStubMode;
        private Service mSservice;


        CreateEventTask(Event event, Service service, boolean stubMode) {
            mEvent = event;
            mStubMode = stubMode;
            mSservice = service;
        }

        @Override
        protected Event doInBackground(Void... params) {

            Event evt = null;
            if (!mStubMode){
                try {

                    evt = mSservice.createEvent(this.mEvent);

                    Log.i("return msg:",evt.toString());
                } catch (Exception e) {
                    Log.e("CreateEventsActivity", e.getMessage(), e);
                }
            }
            else {

                Event dummyEvent = new Event();
                dummyEvent.setId("AAAAAAAAAAAAAAAAAAAAAAAAA");
                dummyEvent.setAcceptingUserId("ramos");
                dummyEvent.setPrefId("hangout");
                evt = dummyEvent;


            }
            return evt;
        }

        @Override
        protected void onPostExecute(final Event event) {

            if (event != null) {

                Toast.makeText(CreateEventActivity.this, "Create Event Results: "+event.toString(), Toast.LENGTH_SHORT).show();

            }

            TextView pTextView = (TextView) findViewById(R.id.lc_preferenceTextView);
            pTextView.setText(null);

        }

        @Override
        protected void onCancelled() {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.createEventMenuItem:
                intent = new Intent(this, CreateEventActivity.class);

                intent.putExtra("test", "Home Screen.....Under Construction");
                startActivity(intent);
                return true;
            case R.id.eventListMenuItem:
                intent = new Intent(this, EventListActivity.class);

                intent.putExtra("test", "Home Screen.....Under Construction");
                startActivity(intent);
                return true;
            case R.id.myPreferenceMenuItem:
                intent = new Intent(this, PreferenceActivity.class);

                intent.putExtra("test", "Home Screen.....Under Construction");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
