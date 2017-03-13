package com.ramotion.expandingcollection.examples.simple;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ramotion.com.expandingcollection.examples.simple.R;

public class CommentsArrayAdapter extends ArrayAdapter<CommentPOJO> {

    private final Context context;
    private final CommentPOJO[] values;

    public CommentsArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull CommentPOJO[] values) {
        super(context, resource, values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.list_element, null);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.line1 = (TextView) rowView.findViewById(R.id.firstLine);
            viewHolder.line2 = (TextView) rowView.findViewById(R.id.secondLine);
            viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        CommentPOJO objectItem = values[position];

        if (objectItem != null) {
            viewHolder.line1.setText(objectItem.getCommentPersonName());
            viewHolder.line2.setText(objectItem.getCommentText());
            viewHolder.icon.setImageDrawable(objectItem.getCommentPersonPicture());
        }

        return rowView;
    }

    static class ViewHolder {
        TextView line1;
        TextView line2;
        ImageView icon;
    }

    @Override
    public int getCount() {
        return values.length;
    }
}
