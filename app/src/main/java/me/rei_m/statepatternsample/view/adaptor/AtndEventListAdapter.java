package me.rei_m.statepatternsample.view.adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import me.rei_m.statepatternsample.R;
import me.rei_m.statepatternsample.entity.AtndEventEntity;

public class AtndEventListAdapter extends ArrayAdapter<AtndEventEntity> {

    public AtndEventListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.list_item_atnd_event, null);
            holder = new ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.text_atnd_event_id);
            holder.title = (TextView) convertView.findViewById(R.id.text_atnd_event_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AtndEventEntity item = getItem(position);
        holder.id.setText(String.valueOf(item.getId()));
        holder.title.setText(item.getTitle());

        return convertView;
    }

    private static class ViewHolder {
        private TextView id;
        private TextView title;
    }
}
