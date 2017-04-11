package klippe.dev.himselfgo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import klippe.dev.himselfgo.adapters.TaskListAdapter;
import klippe.dev.himselfgo.db.DbHelper;
import klippe.dev.himselfgo.pojo.Task;

import static android.view.View.GONE;
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
    public Context context = this;
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
                showAlert();
            }
        });
    }

    public void showAlert() {
        View view = getLayoutInflater().inflate(R.layout.alertdialog, null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(
                context);
        final EditText name = (EditText) view.findViewById(R.id.field_name_quest);
        final EditText complexity = (EditText) view.findViewById(R.id.field_complexity);
        name.setHint("Имя задания");
        complexity.setVisibility(GONE);
        alert.setMessage("Ваше задание:");
        alert.setView(view);
        alert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        task.setName(name.getText().toString());
                        task.setStep(task_list.getCount());
                        Intent intent = new Intent(TasksActivity.this, SetImagesActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog build = alert.create();
        build.show();
    }

    private void CreateList() {
        cursor = sdb.query(DbHelper.TABLE_TASKS,
                new String[]{DbHelper._ID, DbHelper.QUEST,
                        DbHelper.NAME_TASK, DbHelper.LONGTITUDE,
                        DbHelper.LATITUDE, DbHelper.STEP, DbHelper.SRC},
                DbHelper.QUEST + " = ?",
                new String[]{task.getQuest()},
                null,
                null,
                null);

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

    @Override
    protected void onResume() {
        super.onResume();
        CreateList();
    }
}
