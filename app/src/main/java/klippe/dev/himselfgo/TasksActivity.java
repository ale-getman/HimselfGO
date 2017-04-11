package klippe.dev.himselfgo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import klippe.dev.himselfgo.adapters.TaskListAdapter;
import klippe.dev.himselfgo.db.DbHelper;
import klippe.dev.himselfgo.pojo.Task;

import static klippe.dev.himselfgo.InitDbHelper.sdb;

/**
 * Created by user on 05.03.2017.
 */

public class TasksActivity extends AppCompatActivity {

    public ListView task_list;
    public InitDbHelper iDbHelper;
    public FloatingActionButton fab;
    public TaskListAdapter adapter;
    public Cursor cursor;
    public static Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        task = new Task();
        task.setQuest(new String(getIntent().getStringExtra("name_quest")));
        task_list = (ListView) findViewById(R.id.list);
        iDbHelper = new InitDbHelper(this, DbHelper.DATABASE_NAME, DbHelper.DATABASE_VERSION);

        CreateList();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TasksActivity.this, SetImagesActivity.class);
                task.setStep(task_list.getCount());
                startActivity(intent);
            }
        });
    }

    private void CreateList() {
        cursor = sdb.query(DbHelper.TABLE_TASKS, new String[]{DbHelper._ID, DbHelper.QUEST,
                    DbHelper.NAME_TASK, DbHelper.LONGTITUDE,
                    DbHelper.LATITUDE, DbHelper.STEP, DbHelper.SRC },
                    null, null, null, null, null);

        adapter = new TaskListAdapter(TasksActivity.this, cursor);
        task_list.setAdapter(adapter);
        task_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ColorDrawable divcolor = new ColorDrawable(Color.parseColor("#FF12212f"));
        task_list.setDivider(divcolor);
        task_list.setDividerHeight(2);
        task_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
