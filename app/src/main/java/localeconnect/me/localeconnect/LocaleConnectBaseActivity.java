package localeconnect.me.localeconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import localeconnect.me.localeconnect.event.CreateEventActivity;
import localeconnect.me.localeconnect.event.EventListActivity;
import localeconnect.me.localeconnect.profile.IDScanActivity;
import localeconnect.me.localeconnect.profile.PreferenceActivity;

/**
 * Created by admin on 11/14/2017.
 */

public abstract class LocaleConnectBaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initializeAds();
    }

    protected void initializeAds(){
        MobileAds.initialize(this, "ca-app-pub-2018232626764954~4512570322");

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.


        int addId = R.id.ad_view;

        if (this instanceof CreateEventActivity){
            addId = R.id.ad_view_createEvent;
        }
        else if (this instanceof EventListActivity){
            addId = R.id.ad_view_event_list;
        }
        else if (this instanceof PreferenceActivity){
            addId = R.id.ad_view_preference;

        }
        else if (this instanceof  IDScanActivity){
            addId = R.id.ad_view;

        }

        addId = R.id.ad_view_createEvent;

        AdView mAdView = (AdView) findViewById(R.id.ad_view_createEvent);
        mAdView.toString();

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();;


        adRequest.toString();
        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
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
            case R.id.idScanMenuItem:
                intent = new Intent(this, IDScanActivity.class);

                intent.putExtra("test", "Home Screen.....Under Construction");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
