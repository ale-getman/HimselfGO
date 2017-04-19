package klippe.dev.himselfgo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import klippe.dev.himselfgo.db.DbHelper;
import klippe.dev.himselfgo.pojo.Task;
import klippe.dev.himselfgo.singletons.MyPosition;

import static klippe.dev.himselfgo.InitDbHelper.sdb;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String name_quest;
    public ArrayList<Task> tasks = new ArrayList<>();
    public Task task;
    public LatLng posTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        name_quest = getIntent().getStringExtra("name_quest");

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
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                this, R.raw.map_style));
        mMap.getUiSettings().setCompassEnabled(true);


        LatLng myPosition = new LatLng(MyPosition.getInstance().getLatitude(), MyPosition.getInstance().getLongtitude());

        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 10));

        getTaskFromBD();

        for (Task t : tasks) {
            posTasks = new LatLng(t.getLatitude(), t.getLongtitude());
            mMap.addMarker(new MarkerOptions()
                    .position(posTasks)
                    .title(t.getName())
                    .snippet(t.getQuest() + " / " + t.getStep()));
        }
    }

    public void getTaskFromBD() {
        Cursor cursorSelect = sdb.query(DbHelper.TABLE_TASKS,
                new String[]{DbHelper._ID, DbHelper.QUEST,
                        DbHelper.NAME_TASK, DbHelper.LONGTITUDE,
                        DbHelper.LATITUDE, DbHelper.STEP, DbHelper.SRC},
                DbHelper.QUEST + " = ?",
                new String[]{name_quest},
                null,
                null,
                null);

        while (cursorSelect.moveToNext()) {
            task = new Task();
            task.setName(cursorSelect.getString(cursorSelect.getColumnIndex(DbHelper.NAME_TASK)));
            task.setQuest(cursorSelect.getString(cursorSelect.getColumnIndex(DbHelper.QUEST)));
            task.setLongtitude(cursorSelect.getDouble(cursorSelect.getColumnIndex(DbHelper.LONGTITUDE)));
            task.setLatitude(cursorSelect.getDouble(cursorSelect.getColumnIndex(DbHelper.LATITUDE)));
            task.setStep(cursorSelect.getInt(cursorSelect.getColumnIndex(DbHelper.STEP)));
            task.setSrc(cursorSelect.getString(cursorSelect.getColumnIndex(DbHelper.SRC)));
            tasks.add(task);
        }
        cursorSelect.close();
        Log.d("LOGI", "stop");
    }
}
