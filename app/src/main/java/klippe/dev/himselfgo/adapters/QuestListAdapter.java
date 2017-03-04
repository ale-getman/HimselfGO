package klippe.dev.himselfgo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import klippe.dev.himselfgo.R;

/**
 * Created by user on 04.03.2017.
 */

public class QuestListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public QuestListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_my_quest, null);
            holder = new ViewHolder();

            holder.name_quest = (TextView) convertView.findViewById(R.id.name_quest);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {
        TextView name_quest;
    }
}
