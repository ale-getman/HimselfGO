package klippe.dev.himselfgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by user on 15.01.2017.
 */

public class SettingsActivity extends AppCompatActivity {

    private ImageButton img_load, add_to_map, add_img, delete;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        img_load = (ImageButton) findViewById(R.id.img_load);
        add_to_map = (ImageButton) findViewById(R.id.add_to_map);
        add_img = (ImageButton) findViewById(R.id.add_img);
        delete = (ImageButton) findViewById(R.id.delete);

        img_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), SetImagesActivity.class);
                startActivity(intent);
            }
        });
    }
}
