package com.t4.androidclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.t4.androidclient.R;

import java.util.List;

public class CreateLiveGenreAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> genreList;

    public CreateLiveGenreAdapter(Context aContext, List<String> genreList) {
        this.context = aContext;
        this.genreList = genreList;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        if (genreList == null || genreList.isEmpty())
            return 0;
        return this.genreList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.genreList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
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
