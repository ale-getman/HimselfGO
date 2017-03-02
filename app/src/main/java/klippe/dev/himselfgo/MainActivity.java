package klippe.dev.himselfgo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
