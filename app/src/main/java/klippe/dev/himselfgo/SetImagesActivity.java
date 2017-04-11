package klippe.dev.himselfgo;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import gun0912.tedbottompicker.TedBottomPicker;

/**
 * Created by user on 17.01.2017.
 */

public class SetImagesActivity extends AppCompatActivity {

    public ImageView img_setted;
    private ViewGroup mSelectedImagesContainer;
    public TextView path_to_img;
    public Button set_img_btn;
    public FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_img);

        img_setted = (ImageView) findViewById(R.id.img_setted);
        path_to_img = (TextView) findViewById(R.id.path_to_img);
        set_img_btn = (Button) findViewById(R.id.set_img_btn);
        mSelectedImagesContainer = (ViewGroup) findViewById(R.id.selected_photos_container);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetImagesActivity.this, AddToMapsActivity.class);
                if(!path_to_img.getText().equals("путь к изображению"))
                    startActivity(intent);
            }
        });

        set_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PermissionListener permissionlistener = new PermissionListener() {

                    @Override
                    public void onPermissionGranted() {
                        TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(SetImagesActivity.this)
                                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                                    @Override
                                    public void onImageSelected(Uri uri) {
                                        Log.d("ted", "uri: " + uri);
                                        Log.d("ted", "uri.getPath(): " + uri.getPath());
                                        TasksActivity.task.setSrc(uri.getPath());

                                        img_setted.setVisibility(View.VISIBLE);
                                        mSelectedImagesContainer.setVisibility(View.GONE);
                                        Glide.with(SetImagesActivity.this)
                                                //.load(uri.toString())
                                                .load(uri)
                                                .into(img_setted);
                                        path_to_img.setText(uri.getPath());

                                    }
                                })
                                //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                                .setPeekHeight(1200)
                                .create();

                        bottomSheetDialogFragment.show(getSupportFragmentManager());
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(SetImagesActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                    }


                };

                new TedPermission(SetImagesActivity.this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .check();
            }
        });
    }
}
