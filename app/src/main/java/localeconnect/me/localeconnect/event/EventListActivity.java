package localeconnect.me.localeconnect.event;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import localeconnect.me.localeconnect.Event;
import localeconnect.me.localeconnect.LocaleApp;
import localeconnect.me.localeconnect.LocaleConnectBaseActivity;
import localeconnect.me.localeconnect.R;
import localeconnect.me.localeconnect.User;
import localeconnect.me.localeconnect.profile.PreferenceActivity;
import localeconnect.me.localeconnect.service.Service;

public class EventListActivity extends LocaleConnectBaseActivity {

    private ArrayAdapter listAdapter;
    AsyncTask<Void, Void, List<Event>> execute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        populateListView();


        registerOnClick();
    }

    private void populateListView(){



        ListView listView = (ListView) findViewById(R.id.eventListView);


        // Create and populate a List of planet names.
        String[] planets = new String[] { "Event1", "Event2", "Event3", "Event4",
                "Event5", "Event6", "Event7", "Event8"};
        ArrayList<String> planetList = new ArrayList<String>();
        planetList.addAll( Arrays.asList(planets) );

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.activity_event_list_row, planetList);



        // Add more planets. If you passed a String[] instead of a List<String>
        // into the ArrayAdapter constructor, you must not add more items.
        // Otherwise an exception will occur.
        listAdapter.add( "Ceres" );
        listAdapter.add( "Pluto" );
        listAdapter.add( "Haumea" );
        listAdapter.add( "Makemake" );
        listAdapter.add( "Eris" );

        // Set the ArrayAdapter as the ListView's adapter.
        listView.setAdapter( listAdapter );


        EventListTask eventListTask = new EventListTask(null, new Service(), false);
        execute =
                eventListTask.execute();





    }


    private class EventListTask extends AsyncTask<Void, Void, List<Event>> {

        private final Event mEvent;
        private boolean mStubMode;
        private Service mSservice;


        EventListTask(Event event, Service service, boolean stubMode) {
            mEvent = event;
            mStubMode = stubMode;
            mSservice = service;
        }

        @Override
        protected List<Event> doInBackground(Void... params) {

            List<Event> evt = null;
            if (!mStubMode){
                try {

                    LocaleApp appContext = (LocaleApp) getApplicationContext();
                    User user = appContext.getUser();
                    Log.i("ERRORRRR",user != null ? user.toString():"USER CANT B RETRIEVED");
                    evt = mSservice.getEvents(user.getId());


                    Log.i("return msg:", evt != null?evt.toString():"no events");
                } catch (Exception e) {
                    Log.e("CreateEventsActivity", e.getMessage(), e);
                }
            }
            else {

                Event dummyEvent = new Event();
                dummyEvent.setId("AAAAAAAAAAAAAAAAAAAAAAAAA");
                dummyEvent.setAcceptingUserId("ramos");
                dummyEvent.setPrefId("hangout");
                evt = new ArrayList<>();
                evt.add(dummyEvent);
                evt.add(dummyEvent);
                evt.add(dummyEvent);


            }
            return evt;
        }

        @Override
        protected void onPostExecute(final List<Event> events) {

            listAdapter.clear();


            if (events != null) {

                //listAdapter.addAll(events);

                for (Event evt: events){
                    listAdapter.add(evt.getInitiatingUserId()+" is looking to meet for "+ evt.getPrefId()
                    + " near "+evt.getAddress()+", "+evt.getCity());

                }

                //Toast.makeText(EventListActivity.this, "Create Event Results: "+events.toString(), Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        protected void onCancelled() {

        }
    }

    private void registerOnClick(){

        ListView list = (ListView) findViewById(R.id.eventListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String message = "You clicked "+ position + " clicked "+
                        textView.getText().toString();
                Toast.makeText(EventListActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }


}
