package localeconnect.me.localeconnect.profile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import localeconnect.me.localeconnect.Event;
import localeconnect.me.localeconnect.LocaleApp;
import localeconnect.me.localeconnect.LocaleConnectBaseActivity;
import localeconnect.me.localeconnect.Preference;
import localeconnect.me.localeconnect.R;
import localeconnect.me.localeconnect.User;
import localeconnect.me.localeconnect.event.CreateEventActivity;
import localeconnect.me.localeconnect.event.EventListActivity;
import localeconnect.me.localeconnect.service.Service;

public class PreferenceActivity extends LocaleConnectBaseActivity {

    private ArrayAdapter listAdapter;
    AsyncTask<Void, Void, List<Preference>> execute;
    List<Preference> stubPrefList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        populateListView();

        registerOnClick();

        Button mAddPrefButton = (Button) findViewById(R.id.add_my_preference_button);
        mAddPrefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPreference();
            }
        });

    }

    private void registerOnClick(){

        ListView list = (ListView) findViewById(R.id.myPreferenceListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String message = "You clicked "+ position + " clicked "+
                        textView.getText().toString();
                Toast.makeText(PreferenceActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateListView(){



        ListView listView = (ListView) findViewById(R.id.myPreferenceListView);


        // Create and populate a List of planet names.
        String[] planets = new String[] { "Event1", "Event2", "Event3", "Event4",
                "Event5", "Event6", "Event7", "Event8"};
        ArrayList<String> planetList = new ArrayList<String>();
        planetList.addAll( Arrays.asList(planets) );

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.activity_my_preference_list_row, planetList);



        // Set the ArrayAdapter as the ListView's adapter.
        listView.setAdapter( listAdapter );


        PreferenceListTask pListTask = new PreferenceListTask(null, new Service(), false);
        execute =
                pListTask.execute();





    }

    private class PreferenceListTask extends AsyncTask<Void, Void, List<Preference>> {

        private final Preference mPreference;
        private boolean mStubMode;
        private Service mSservice;


        PreferenceListTask(Preference preference, Service service, boolean stubMode) {
            mPreference = preference;
            mStubMode = stubMode;
            mSservice = service;
        }

        @Override
        protected List<Preference> doInBackground(Void... params) {

            List<Preference> p = null;
            if (!mStubMode){
                try {

                    LocaleApp appContext = (LocaleApp) getApplicationContext();
                    User user = appContext.getUser();

                    if (mPreference == null)
                        p = mSservice.getPreferences(user.getId());
                        //p = PreferenceActivity.this.stubPrefList;
                    else {
                        mPreference.setUserId(user.getId());
                        p = mSservice.createPreference(mPreference);
                        //PreferenceActivity.this.stubPrefList.add(mPreference);
                        //p = PreferenceActivity.this.stubPrefList;

                    }


                   Log.i("return msg:", p != null?p.toString():"no preferences");
                } catch (Exception e) {
                    Log.e("PreferenceListActivity", e.getMessage(), e);
                }
            }
            else {

                Preference dummyPref = new Preference();
                p = new ArrayList<>();
                p.add(dummyPref);
                p.add(dummyPref);
                p.add(dummyPref);


            }
            return p;
        }

        @Override
        protected void onPostExecute(final List<Preference> p) {

            listAdapter.clear();


            if (p != null) {

                //listAdapter.addAll(events);

                for (Preference evt: p){
                    listAdapter.add(evt.getType());

                }

                EditText pTextView = (EditText) findViewById(R.id.lc_myPreferenceTextView);
                pTextView.setText(null);

                //Toast.makeText(EventListActivity.this, "Create Event Results: "+events.toString(), Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        protected void onCancelled() {

        }
    }

    private void addPreference(){
        String s = "some new preference  "+ System.currentTimeMillis();

        Preference p = new Preference();
        TextView pTextView = (TextView) findViewById(R.id.lc_myPreferenceTextView);

        String intent = pTextView.getText().toString();
        p.setType(intent);

        PreferenceListTask pListTask = new PreferenceListTask(p, new Service(), false);
        execute =
                pListTask.execute();

    }


}
