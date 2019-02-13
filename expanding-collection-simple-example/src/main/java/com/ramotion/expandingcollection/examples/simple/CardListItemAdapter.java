package com.ramotion.expandingcollection.examples.simple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramotion.expandingcollection.ECCardContentListItemAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class CardListItemAdapter extends ECCardContentListItemAdapter<String> {

    public CardListItemAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, R.layout.list_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.itemText = (TextView) rowView.findViewById(R.id.list_item_text);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        final String item = getItem(position);
        if (item != null) {
            viewHolder.itemText.setText(item);
        }
        // Example of changing/removing card list items
        viewHolder.itemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tapToRemoveText = "Tap again to remove!";
                TextView view = (TextView) v;
                if (view.getText().equals(tapToRemoveText)) {
                    v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorPrimary));
                    CardListItemAdapter.this.remove(item);
                    CardListItemAdapter.this.notifyDataSetChanged();
                } else {
                    view.setText(tapToRemoveText);
                    v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorAccent));
                }
            }
        });
        return rowView;
    }

    static class ViewHolder {
        TextView itemText;
    }

}
