package com.t4.androidclient.ui.ranking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.t4.androidclient.R;
import com.t4.androidclient.contraints.Host;
import com.t4.androidclient.model.livestream.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankingUserAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<User> users;

    public RankingUserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
        layoutInflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        if (users == null)
            return 0;
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        User user = users.get(i);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.ranking_item, viewGroup, false);
            viewHolder.avatar = view.findViewById(R.id.ranking_avatar);
            viewHolder.nickname = view.findViewById(R.id.ranking_nickname);
            viewHolder.point = view.findViewById(R.id.ranking_point);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (user.avatar != null && !user.avatar.isEmpty())
            Glide.with(context).load(user.avatar.startsWith("http") ? user.avatar : Host.API_HOST_IP + user.avatar) // plays as url
                    .placeholder(R.drawable.ic_fire).centerCrop().into(viewHolder.avatar);
        viewHolder.nickname.setText(users.get(i).getNickname());
        viewHolder.point.setText(users.get(i).currentMonthRankingPoint()+"");
        return view;
    }

    public class ViewHolder {
        CircleImageView avatar;
        TextView nickname;
        TextView point;
    }
}
