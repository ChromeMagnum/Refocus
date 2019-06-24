package com.example.refocus;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<App> {
    Context context;
    private ArrayList<App> apps;


    public MyAdapter(Context context, ArrayList<App> apps) {
        super(context, R.layout.list_item, apps);
        this.apps = apps;
        this.context = context;

    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtName;
        TextView txtCategory;
        ImageView imageView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        App app = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.title);
            viewHolder.txtCategory = (TextView) convertView.findViewById(R.id.subtitle);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText(app.getName());
        viewHolder.txtCategory.setText(app.getCategory());

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public App getItem(int position) {
        return apps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return apps.indexOf(getItem(position));
    }
}
