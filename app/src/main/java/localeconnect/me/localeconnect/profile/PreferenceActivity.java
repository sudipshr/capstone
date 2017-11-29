package localeconnect.me.localeconnect.profile;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import localeconnect.me.localeconnect.LocaleApp;
import localeconnect.me.localeconnect.LocaleConnectBaseActivity;
import localeconnect.me.localeconnect.Preference;
import localeconnect.me.localeconnect.R;
import localeconnect.me.localeconnect.User;
import localeconnect.me.localeconnect.service.Service;

public class PreferenceActivity extends LocaleConnectBaseActivity {

    private ArrayAdapter listAdapter;
    AsyncTask<Void, Void, List<Preference>> execute;
    List<Preference> stubPrefList = new ArrayList<>();

    private static final int ACTION_TAKE_PHOTO_B = 1;
    private static final int ACTION_TAKE_PHOTO_S = 2;
    private static final int ACTION_TAKE_VIDEO = 3;


    private static final String VIDEO_STORAGE_KEY = "viewvideo";
    private static final String VIDEOVIEW_VISIBILITY_STORAGE_KEY = "videoviewvisibility";
    private VideoView mVideoView;
    private Uri mVideoUri;

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


        //http://www.informit.com/articles/article.aspx?p=2143148&seqNum=3


        //mImageView = (ImageView) findViewById(R.id.imageView1);
        mVideoView = (VideoView) findViewById(R.id.videoProfileView);
        mVideoView.setClickable(true);

        //mImageBitmap = null;
        mVideoUri = null;
        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVideoView.start();

            }
        });

        MediaController mediaController = new MediaController(this);
        mVideoView.setMediaController(mediaController);

        //Button picBtn = (Button) findViewById(R.id.btnIntend);
/*        setBtnListenerOrDisable(
                picBtn,
                mTakePicOnClickListener,
                MediaStore.ACTION_IMAGE_CAPTURE
        );*/


        Button vidBtn = (Button) findViewById(R.id.lc_uploadVideoBtn);
        setBtnListenerOrDisable(
                vidBtn,
                mTakeVidOnClickListener,
                MediaStore.ACTION_VIDEO_CAPTURE
        );


        //AWSMobileClient.getInstance().initialize(this).execute();


    }

    private void registerOnClick() {

        ListView list = (ListView) findViewById(R.id.myPreferenceListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String message = "You clicked " + position + " clicked " +
                        textView.getText().toString();
                Toast.makeText(PreferenceActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateListView() {


        ListView listView = (ListView) findViewById(R.id.myPreferenceListView);


        // Create and populate a List of planet names.
        String[] planets = new String[]{"Event1", "Event2", "Event3", "Event4",
                "Event5", "Event6", "Event7", "Event8"};
        ArrayList<String> planetList = new ArrayList<String>();
        planetList.addAll(Arrays.asList(planets));

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.activity_my_preference_list_row, planetList);


        // Set the ArrayAdapter as the ListView's adapter.
        listView.setAdapter(listAdapter);


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
            if (!mStubMode) {
                try {

                    LocaleApp appContext = (LocaleApp) getApplicationContext();
                    User user = appContext.getUser();

                    if (mPreference == null)
                        p = mSservice.getPreferences(user.getId());

                    else {
                        mPreference.setUserId(user.getId());
                        p = mSservice.createPreference(mPreference);
                        PreferenceActivity.this.stubPrefList.add(mPreference);


                    }

                    if (p != null){
                        PreferenceActivity.this.listAdapter.addAll(p.toArray());
                    }


                    Log.i("return msg:", p != null ? p.toString() : "no preferences");
                } catch (Exception e) {
                    Log.e("PreferenceListActivity", e.getMessage(), e);
                }
            } else {

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

                for (Preference evt : p) {
                    listAdapter.add(evt.getType() == null ? "invalid type" : evt.getType());

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

    private void addPreference() {
        String s = "some new preference  " + System.currentTimeMillis();

        Preference p = new Preference();
        TextView pTextView = (TextView) findViewById(R.id.lc_myPreferenceTextView);

        String intent = pTextView.getText().toString();

        if (intent == null){
            Toast.makeText(this, "Please enter a valid preference. See Hint", Toast.LENGTH_SHORT).show();
            return;
        }
        p.setType(intent);

        PreferenceListTask pListTask = new PreferenceListTask(p, new Service(), false);
        execute =
                pListTask.execute();

    }


    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
    }

    private void handleCameraVideo(Intent intent) {
        mVideoUri = intent.getData();
        mVideoView.setVideoURI(mVideoUri);
        //mImageBitmap = null;
        mVideoView.setVisibility(View.VISIBLE);
        mVideoView.start();

        uploadData(mVideoUri.getPath());


        //mImageView.setVisibility(View.INVISIBLE);
    }

    private void uploadToCloud() {
        //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        Uri file = this.mVideoUri;

    }

    Button.OnClickListener mTakeVidOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakeVideoIntent();
                }
            };

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void setBtnListenerOrDisable(
            Button btn,
            Button.OnClickListener onClickListener,
            String intentName
    ) {
        if (isIntentAvailable(this, intentName)) {
            btn.setOnClickListener(onClickListener);
        } else {
            btn.setText(
                    getText(R.string.lc_error).toString() + " " + btn.getText());
            btn.setClickable(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
/*
            case ACTION_TAKE_PHOTO_B: {
                if (resultCode == RESULT_OK) {
                    handleBigCameraPhoto();
                }
                break;
            } // ACTION_TAKE_PHOTO_B

            case ACTION_TAKE_PHOTO_S: {
                if (resultCode == RESULT_OK) {
                    handleSmallCameraPhoto(data);
                }
                break;
            } // ACTION_TAKE_PHOTO_S
*/

            case ACTION_TAKE_VIDEO: {
                if (resultCode == RESULT_OK) {
                    handleCameraVideo(data);
                }
                break;
            } // ACTION_TAKE_VIDEO
        } // switch
    }


    public void uploadData(String filePath) {

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-1:d547b5d9-45a4-4bc4-aae0-c11138f65fc7", // Identity Pool ID
        Regions.US_EAST_1 // Region
    );


        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);


        // Set the region of your S3 bucket
        s3.setRegion(Region.getRegion(Regions.US_EAST_1));

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext()).s3Client(s3)

                        .build();


        TransferObserver uploadObserver =
                null;
        try {

            uploadObserver = transferUtility.upload("lconnect",
                    String.valueOf(System.currentTimeMillis()),
                    new File(getRealPathFromURI(this, mVideoUri)));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    Toast.makeText(PreferenceActivity.this, "transfer complete", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgressChanged(
                    int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int) percentDonef;

                Log.d("MainActivity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

        // If your upload does not trigger the onStateChanged method inside your
        // TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }

    }


    public void downloadData() {

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .build();

        TransferObserver downloadObserver =
                transferUtility.download(
                        "s3Folder/s3Key.txt",
                        new File("/path/to/file/localFile.txt"));
        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("MainActivity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Video.Media.DATA };

            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


}
