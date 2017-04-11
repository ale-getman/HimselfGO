package klippe.dev.himselfgo;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import klippe.dev.himselfgo.db.DbHelper;
import klippe.dev.himselfgo.singletons.MyPosition;

import static klippe.dev.himselfgo.InitDbHelper.sdb;

/**
 * Created by user on 17.01.2017.
 */

public class AddToMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public double LAT = 0;
    public double LNG = 0;
    public FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddToMapsActivity.this, TasksActivity.class);
                intent.putExtra("name_quest", TasksActivity.task.getQuest());
                if (LAT != 0 && LNG != 0) {
                    insertDB();
                    startActivity(intent);
                }
            }
        });
    }

    public void insertDB() {
        ContentValues newValues = new ContentValues();
        newValues.put(DbHelper.QUEST, TasksActivity.task.getQuest());
        newValues.put(DbHelper.NAME_TASK, TasksActivity.task.getName());
        newValues.put(DbHelper.LONGTITUDE, TasksActivity.task.getLongtitude());
        newValues.put(DbHelper.LATITUDE, TasksActivity.task.getLatitude());
        newValues.put(DbHelper.STEP, TasksActivity.task.getStep());
        newValues.put(DbHelper.SRC, TasksActivity.task.getSrc());
        sdb.insert(DbHelper.TABLE_TASKS, null, newValues);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("LOGI", "LatLng: " + latLng);
                mMap.clear();
                LAT = latLng.latitude;
                LNG = latLng.longitude;

                LatLng myPosition2 = new LatLng(LAT, LNG);
                mMap.addMarker(new MarkerOptions()
                        .position(myPosition2)
                        .title("Latitude = " + LAT + " Longtitude = " + LNG));
                TasksActivity.task.setLatitude(LAT);
                TasksActivity.task.setLongtitude(LNG);
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        LatLng myPosition = new LatLng(MyPosition.getInstance().getLatitude(), MyPosition.getInstance().getLongtitude());

        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 10));

    }
}