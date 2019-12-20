package com.t4.androidclient.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.t4.androidclient.R;
import com.t4.androidclient.model.livestream.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>{
    private List<User> userList;
    private Context context;
    private LinearLayout container;

    byte[] imageBytes;
    Bitmap avatarImage;

    public SubscriptionAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_subscription, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionAdapter.ViewHolder holder, int position) {
        User user = userList.get(position);
        container = holder.container;

        if (user.getAvatar() != null) {
            imageBytes = Base64.decode(user.getAvatar(), Base64.URL_SAFE);
            avatarImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }

        CircleImageView avatarView = holder.avatarView;
        if (avatarImage != null && avatarImage.getByteCount() > 0) {
            avatarView.setImageBitmap(avatarImage);
        } else {
            avatarView.setImageDrawable(context.getDrawable(R.drawable.place_holder));
        }

        TextView nameView = holder.nameView;
        nameView.setText(user.getNickname());

        LinearLayout linkButton = holder.container;
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // start new activity here
                nameView.setText("clickable");
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView avatarView;
        public TextView nameView;
        public LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);

            avatarView = (CircleImageView) itemView.findViewById(R.id.subscription_avatar);
            nameView = (TextView) itemView.findViewById(R.id.subscription_name);
            container = (LinearLayout) itemView.findViewById(R.id.subscription_container);
        }
    }
}
