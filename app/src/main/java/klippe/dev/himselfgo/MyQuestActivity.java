package klippe.dev.himselfgo;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import klippe.dev.himselfgo.db.DbHelper;

import static klippe.dev.himselfgo.InitDbHelper.sdb;

public class MyQuestActivity extends AppCompatActivity {

    public ListView quest_list;
    public InitDbHelper iDbHelper;
    public ListAdapter adapter;
    public Cursor cursor;
    public final Context context = this;
    public String name_quest;
    public String complexity_quest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        quest_list = (ListView) findViewById(R.id.list);
        iDbHelper = new InitDbHelper(this, DbHelper.DATABASE_NAME, DbHelper.DATABASE_VERSION);

        CreateList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        alert.setMessage("Your quest:");
        alert.setView(view);
        alert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        name_quest = name.getText().toString();
                        complexity_quest = complexity.getText().toString();
                        insertDB();
                        CreateList();
                    }
                });
        AlertDialog build = alert.create();
        build.show();
    }

    public void insertDB() {
        ContentValues newValues = new ContentValues();
        newValues.put(DbHelper.NAME_QUEST, name_quest);
        newValues.put(DbHelper.COMPLEXITY, complexity_quest);
        newValues.put(DbHelper.COUNT_TASKS, 0);
        sdb.insert(DbHelper.TABLE_QUESTS, null, newValues);
    }

    public void CreateList() {
        cursor = sdb.query(DbHelper.TABLE_QUESTS, new String[]{DbHelper._ID, DbHelper.NAME_QUEST,
                        DbHelper.COMPLEXITY, DbHelper.COUNT_TASKS},
                null, null, null, null, null);

        adapter = new SimpleCursorAdapter(this, // Связь.
                R.layout.item_my_quest, // Определения шаблона элемента
                cursor, // Переход к курсору, который надо запомнить.
                // Массив курсоров, которые надо запомнить.
                new String[]{DbHelper.NAME_QUEST, DbHelper.COMPLEXITY, DbHelper.COUNT_TASKS},
                // Массив, связывающий запомненные курсоры и шаблоны с ними связанные
                new int[]{R.id.name_quest, R.id.complexity, R.id.count});
        quest_list.setAdapter(adapter);
        quest_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ColorDrawable divcolor = new ColorDrawable(Color.parseColor("#FF12212f"));
        quest_list.setDivider(divcolor);
        quest_list.setDividerHeight(2);
        quest_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyQuestActivity.this, TasksActivity.class);
                TextView name_quest = (TextView) view.findViewById(R.id.name_quest);
                intent.putExtra("name_quest", name_quest.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        CreateList();
    }
}
