package klippe.dev.himselfgo;

import android.Manifest;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import klippe.dev.himselfgo.db.DbHelper;

public class MainActivity extends AppCompatActivity {

    public static final int PERMISSION_REQUEST_CODE = 200;
    private Button start_btn, settings_btn;
    private Intent intent;
    private DbHelper mDatabaseHelper;
    public SQLiteDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_btn = (Button) findViewById(R.id.start);
        settings_btn = (Button) findViewById(R.id.settings);

        mDatabaseHelper = new DbHelper(this, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
        sdb = mDatabaseHelper.getWritableDatabase();

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), MyQuestActivity.class);
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestMultiplePermissions();
        }
    }

    public void requestMultiplePermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                },
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CODE) {
            requestMultiplePermissions();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
