package com.example.pulkit.boozingo.details;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pulkit.boozingo.helper.LocationHelper;
import com.example.pulkit.boozingo.helper.Permission;
import com.example.pulkit.boozingo.R;
import com.example.pulkit.boozingo.helper.SnackBarClass;
import com.example.pulkit.boozingo.helper.HttpHandler;
import com.example.pulkit.boozingo.model.detailsBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import static com.example.pulkit.boozingo.Boozingo.url;
import static com.example.pulkit.boozingo.helper.LocationHelper.REQUEST_CHECK_SETTINGS;
import static com.example.pulkit.boozingo.helper.LocationHelper.status;
import static com.example.pulkit.boozingo.helper.Permission.RequestPermissionCode;

public class detailsActivityBar extends AppCompatActivity implements OnMapReadyCallback, SnackBarClass.SnackbarMessage,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    ViewPager viewPager;
    //    ImageButton back;
    String TAG = "TAG", id = "2", text, geo_location, latitudeBar, longitudeBar, image, specs, cost, _type;
    List<String> images = new ArrayList<>();
    picPagerAdapter adapter;
    LinearLayout icons;
    TextView speciality, name, type, address, timing;
    ImageView transparent, dot1, dot2, dot3, dot4, dot5, dot6;
    ScrollView scroll;
    detailsBar details;
    ProgressDialog pDialog;
    SupportMapFragment mapFragment;

    RelativeLayout container;

    private Location mLastLocation;
    double latitude;
    double longitude;
    LocationHelper locationHelper;

    FrameLayout f1;
    LinearLayout l1;
    ImageView im1;
    HorizontalScrollView hsv;
    RelativeLayout r1;
    View frag;
    Button locator;
    int height, width;

    Permission permission;
    SnackBarClass snackBarClass;
    Snackbar snackbar;
    private boolean internetConnected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.temp5);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //      id = getIntent().getStringExtra("id");
        //    _type = getIntent().getStringExtra("type");

        id = "2";
        _type = "bar";


        // link views to objects
        init();

        // set height and width programatically
        setParams();


        permission = new Permission(this);
        locationHelper = new LocationHelper(this);
        snackBarClass = new SnackBarClass(this);
        snackBarClass.readySnackbarMessage(this);


        pDialog = new ProgressDialog(detailsActivityBar.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);
        pDialog.show();


        dot1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ring));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        dot1.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.ring));
                        dot2.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.dot));
                        break;

                    case 1:
                        dot1.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.dot));
                        dot2.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.ring));
                        dot3.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.dot));
                        break;
                    case 2:
                        dot2.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.dot));
                        dot3.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.ring));
                        //            dot4.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.dot));
                        break;
                   /* case 3:
                        dot3.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.dot));
                        dot4.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.ring));
                        dot5.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.dot));
                        break;
                    case 4:
                        dot4.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.dot));
                        dot5.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.ring));
                        dot6.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.dot));
                        break;
                    case 5:
                        dot5.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.dot));
                        dot6.setImageDrawable(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.ring));
                        break;*/
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        locator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLastLocation = locationHelper.getLocation();
                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                }

                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr=" + latitude + "," + longitude + "&daddr=" + latitudeBar + "," + longitudeBar;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(Intent.createChooser(intent, "Select an application"));

            }
        });

        //to get data from net
        new net().execute();

        String path="http://maps.google.com/maps/api/staticmap?center=37.4223662,-122.0839445&zoom=15&size=200x200&sensor=false";

        //     new setImageFromUrl().execute();
        Glide.with(this)
                .load(path)
                .into(im1);

        // for scrolling map fragment
        transparent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        scroll.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        scroll.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scroll.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });

    }


private void init(){
        viewPager=(ViewPager)findViewById(R.id.pager);

        dot1=(ImageView)findViewById(R.id.dot1);
        dot2=(ImageView)findViewById(R.id.dot2);
        dot3=(ImageView)findViewById(R.id.dot3);
        dot4=(ImageView)findViewById(R.id.dot4);
        dot5=(ImageView)findViewById(R.id.dot5);
        dot6=(ImageView)findViewById(R.id.dot6);

        speciality=(TextView)findViewById(R.id.speciality);
        name=(TextView)findViewById(R.id.name);
        type=(TextView)findViewById(R.id.type);
        timing=(TextView)findViewById(R.id.timings);
        address=(TextView)findViewById(R.id.address);
        icons=(LinearLayout)findViewById(R.id.icons);
        container=(RelativeLayout)findViewById(R.id.container);
        scroll=(ScrollView)findViewById(R.id.scroll);
        transparent=(ImageView)findViewById(R.id.imagetrans);

        f1=(FrameLayout)findViewById(R.id.layout);
        l1=(LinearLayout)findViewById(R.id.dots);
        im1=(ImageView)findViewById(R.id.booze);
        hsv=(HorizontalScrollView)findViewById(R.id.icon_holder);
        r1=(RelativeLayout)findViewById(R.id.ll4);
        frag=findViewById(R.id.map);
        locator=(Button)findViewById(R.id.locator);

        mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        }

private void setParams(){

        height=getWindowManager().getDefaultDisplay().getHeight();
        width=getWindowManager().getDefaultDisplay().getWidth();

        f1.getLayoutParams().height=(int)(height*0.30);
        l1.getLayoutParams().height=(int)(height*0.02);
        im1.getLayoutParams().height=(int)(height*0.54);
        hsv.getLayoutParams().height=(int)(height*0.10);
        r1.getLayoutParams().height=(int)(height*0.30);
        frag.getLayoutParams().height=(int)(height*0.30*0.85);
        locator.getLayoutParams().height=(int)(height*0.30*0.25);

        l1.getLayoutParams().width=(int)(width*0.25);
        im1.getLayoutParams().width=(int)(width*0.30);
        locator.getLayoutParams().width=(int)(width*0.15);
        }

@Override
public void onMapReady(GoogleMap map){
        map.addMarker(new MarkerOptions()
        .position(new LatLng(Float.parseFloat(latitudeBar),Float.parseFloat(longitudeBar)))
        .title(_type.toUpperCase()));

        CameraPosition cameraPosition=new CameraPosition.Builder()
        .target(new LatLng(Float.parseFloat(latitudeBar),Float.parseFloat(longitudeBar)))
        .zoom(15)
        .build();
        //Zoom in and animate the camera.
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }


private class net extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url + "/bar/" + id);


        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                final JSONObject object = new JSONObject(jsonStr);
                JSONArray array = object.getJSONArray("bar");

                JSONObject temp = array.getJSONObject(0);
                String userJson = temp.toString();

                Gson gson = new Gson();

                details = new detailsBar();
                details = gson.fromJson(userJson, detailsBar.class);

                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {

                        name.setText(details.getBar_name());
                        address.setText(details.getBar_address());
                        type.setText("(" + _type + ")");
                        timing.setText(details.getBar_time());
                        geo_location = details.getBar_geolocation();
                        specs = details.getBar_details();
                        cost = details.getBar_cost();


                        int comma = geo_location.indexOf('-');
                        latitudeBar = geo_location.substring(0, comma);
                        longitudeBar = geo_location.substring(comma + 1);

                        mapFragment.getMapAsync(detailsActivityBar.this);


                        try {
                            image = object.getJSONArray("bar_images").getJSONObject(0).getString("bar_images");
                            image = image.substring(2, image.length() - 2);
                            image = image.replaceAll("\\\\", "");

                            for (int i = 0; i < image.length(); ) {
                                int j = image.indexOf(',', i);
                                if (j == -1) {
                                    images.add(url + "/storage/" + image.substring(i, image.length()));
                                    break;
                                } else
                                    images.add(url + "/storage/" + image.substring(i, j - 1));
                                i = j + 2;

                            }

                            //to randomise pics
                            Collections.shuffle(images);

                            //to select only 3 pics
                            images = images.subList(0, 3);


                            adapter = new picPagerAdapter(detailsActivityBar.this, images);
                            viewPager.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        // for speciality
                        String y;
                        for (int i = 0; i < specs.length(); ) {
                            int x = specs.indexOf('/', i);
                            if (x < specs.length() && x != -1) {
                                y = speciality.getText() + "\u25CF " + specs.substring(i, x) + "\n";
                                speciality.setText(y);
                                i = x + 1;
                            } else {
                                y = speciality.getText() + "\u25CF " + specs.substring(i, specs.length());
                                speciality.setText(y);
                                break;
                            }

                        }


                        // for cost of 2 person
                        y = speciality.getText() + "\n\u25CF Average cost for 2 Boozinga: \u20B9" + cost;
                        speciality.setText(y);

                        facilities();

                        pDialog.dismiss();
                    }
                });

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Problem retrieving data. Restart application.",
                                Toast.LENGTH_LONG)
                                .show();
                        pDialog.dismiss();

                    }
                });

            }

        } else {
            Log.e(TAG, "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Network problem. Check network connection.",
                            Toast.LENGTH_LONG)
                            .show();
                    pDialog.dismiss();

                }
            });
        }

        return null;
    }

}

    @SuppressWarnings("ResourceType")
    private void facilities() {
        for (int i = 0; i < 8; i++) {
            View child = View.inflate(getBaseContext(), R.layout.smallpicrow, null);
            ImageView x = (ImageView) child.findViewById(R.id.pic);
            TextView text = (TextView) child.findViewById(R.id.text);

            switch (i) {
                case 0:
                    x.setBackground(ContextCompat.getDrawable(this, R.raw.boozingo));
                    text.setText("Boozingo");
                    icons.addView(child);
                    break;
                case 1:
                    if (details.getBar_food().equals("both") || details.getBar_food().equals("veg")) {
                        x.setBackground(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.veg));
                        text.setText("Veg");
                        icons.addView(child);
                    }
                    break;
                case 2:
                    if (details.getBar_food().equals("both") || details.getBar_food().equals("nonveg")) {
                        x.setBackground(ContextCompat.getDrawable(detailsActivityBar.this, R.drawable.non_veg));
                        text.setText("Non Veg");
                        icons.addView(child);
                    }
                    break;
                case 3:
                    if (details.getBar_sitting_facility().equals("yes")) {
                        x.setBackground(ContextCompat.getDrawable(detailsActivityBar.this, R.raw.table));
                        text.setText("Sitting");
                        icons.addView(child);
                    }
                    break;
                case 4:
                    if (details.getBar_music().equals("available")) {
                        x.setBackground(ContextCompat.getDrawable(detailsActivityBar.this, R.raw.music_player));
                        text.setText("Music");
                        icons.addView(child);
                    }
                    break;
                case 5:
                    if (details.getBar_ac().equals("ac")) {
                        x.setBackground(ContextCompat.getDrawable(detailsActivityBar.this, R.raw.minisplit1));
                        text.setText("Ac");
                        icons.addView(child);
                    }
                    break;
                case 6:
                    if (details.getBar_payment().equals("cash") || details.getBar_payment().equals("all")) {
                        x.setBackground(ContextCompat.getDrawable(detailsActivityBar.this, R.raw.notes));
                        text.setText("Cash");
                        icons.addView(child);
                    }
                    break;
                case 7:
                    if (details.getBar_payment().equals("credit/debit card") || details.getBar_payment().equals("all")) {
                        x.setBackground(ContextCompat.getDrawable(detailsActivityBar.this, R.raw.credit_card));
                        text.setText("Card");
                        icons.addView(child);
                    }
                    break;
            }

        }
    }


    // Permission check functions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {

            case RequestPermissionCode:
                if (grantResults.length > 0) {

                    boolean CoarseLocation = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean FineLocaion = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (CoarseLocation && FineLocaion) {

                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                        //                  setSnackbarMessage(status, false);

                    } else {

                        // permission was not granted
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                            permission.requestPermission();
                        } else {
                            Toast.makeText(this, "Permissions are necessary. Allow them from app settings.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;
        }

    }

    @Override
    public void setSnackbarMessage(String status, boolean showBar) {
        String internetStatus = "";
        if (status.equalsIgnoreCase("Wifi enabled") || status.equalsIgnoreCase("Mobile data enabled")) {
            internetStatus = "Internet Connected";
        } else {
            internetStatus = "Lost Internet Connection";
        }
        snackbar = Snackbar
                .make(scroll, internetStatus, Snackbar.LENGTH_LONG)
                .setAction("X", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();

        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        if (internetStatus.equalsIgnoreCase("Lost Internet Connection")) {
            if (internetConnected) {
                snackbar.show();
                internetConnected = false;
            }
        } else {
            if (!internetConnected) {
                internetConnected = true;
                snackbar.show();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //      locationHelper.checkPlayServices();
        snackBarClass.registerInternetCheckReceiver();

        if (!permission.checkPermission())
            permission.requestPermission();

    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(snackBarClass.broadcastReceiver);
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        mLastLocation = locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        locationHelper.connectApiClient();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        Log.e("d", "d");
                        // All required changes were successfully made
                        mLastLocation = locationHelper.getLocation();
                        break;
                    case RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        try {
                            status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                break;
        }
    }


}
