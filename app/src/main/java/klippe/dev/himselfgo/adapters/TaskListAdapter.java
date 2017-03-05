package klippe.dev.himselfgo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import klippe.dev.himselfgo.R;
import klippe.dev.himselfgo.db.DbHelper;

/**
 * Created by user on 04.03.2017.
 */

public class TaskListAdapter extends CursorAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Cursor cursor;

    public TaskListAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
        this.cursor = cursor;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_my_task,null,true);

        ViewHolder holder = new ViewHolder();
        holder.name_task = (TextView) rowView.findViewById(R.id.name_task);
        holder.img_task = (ImageView) rowView.findViewById(R.id.img_task);
        rowView.setTag(holder);

        return rowView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        String s = cursor.getString(cursor.getColumnIndex(DbHelper.NAME_TASK));
        holder.name_task.setText(s);
        String path = cursor.getString(cursor.getColumnIndex(DbHelper.SRC));
        holder.img_task.setImageURI(Uri.fromFile(new File(path)));
    }

    static class ViewHolder {
        TextView name_task;
        ImageView img_task;
    }
}
