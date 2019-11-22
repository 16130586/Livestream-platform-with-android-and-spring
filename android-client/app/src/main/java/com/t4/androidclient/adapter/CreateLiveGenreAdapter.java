package com.t4.androidclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.t4.androidclient.R;

import java.util.List;

public class CreateLiveGenreAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private List<String> genreList;

    public CreateLiveGenreAdapter(Context context, int resource, List<String> genreList) {
        super(context, resource, genreList);
        this.context = context;
        this.resource = resource;
        this.genreList = genreList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_create_live_genre, parent, false);
            viewHolder.genreCheckBox = (CheckBox) convertView.findViewById(R.id.tv_create_live_genre_checkbox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.genreCheckBox.setText(genreList.get(position));
        viewHolder.genreCheckBox.setId(View.generateViewId());
        System.out.println(viewHolder.genreCheckBox.getId() + "new ID" + viewHolder.genreCheckBox.getText().toString());
        return convertView;
    }

    public class ViewHolder {
        CheckBox genreCheckBox;
    }

}
