package com.t4.androidclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.t4.androidclient.R;
import com.t4.androidclient.ui.channel.ChannelActivity;
import com.t4.androidclient.ui.channel.ChannelStreamsOfTypeActivity;

import java.util.ArrayList;
import java.util.List;

import viewModel.StreamViewModel;
import viewModel.UserModelView;


public class SubscribedChannelAdapter extends RecyclerView.Adapter<SubscribedChannelAdapter.ViewHolder>{
    private List<UserModelView> listUserView;
    private Context context;
    byte[] imageBytes;
    Bitmap thumnailImage;

    public SubscribedChannelAdapter(List<UserModelView> listUserView, Context context) {
        this.listUserView = listUserView;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_mychannel_subscribed_channel, parent, false);
        SubscribedChannelAdapter.ViewHolder viewHolder = new SubscribedChannelAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModelView userModelView = listUserView.get(position);

        if (userModelView.getAvatar() != null) {
            //get thumnail link - lay image thumnail tu do
            // imageBytes = Base64.decode(stream.getAvatar(), Base64.URL_SAFE);
            //thumnailImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }
        ImageView thumnailImgView = holder.thumnailImage;
        if (thumnailImage != null && thumnailImage.getByteCount() > 0) {
            thumnailImgView.setImageBitmap(thumnailImage);
        } else {
            thumnailImgView.setImageDrawable(context.getDrawable(R.drawable.background_channel));
        }

        TextView userName = holder.userName;
        userName.setText(userModelView.getNickName());

        TextView userSubTotal = holder.userSubTotal;
        int numberText = userModelView.getSubscriberTotal();
        if(numberText<1000){
            userSubTotal.setText(numberText+" subscribers");
        }else{
            userSubTotal.setText(numberText/1000+" N subscribers");
        }

    }

    @Override
    public int getItemCount() {
        return listUserView.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumnailImage;
        public TextView userName;
        public TextView userSubTotal;
        public ImageButton btnNotify;
        public Button btnSubscribe;

        public ViewHolder(View itemView) {
            super(itemView);
            thumnailImage = (ImageView) itemView.findViewById(R.id.item_subscribed_channel_image);
            userName = (TextView) itemView.findViewById(R.id.item_subscribed_channel_name);
            userSubTotal = (TextView) itemView.findViewById(R.id.item_subscribed_channel_subtotal);
            btnSubscribe = (Button) itemView.findViewById(R.id.item_subscribed_channel_btn_subscrible);
            btnNotify = (ImageButton) itemView.findViewById(R.id.item_subscribed_channel_btn_notify);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int userID = listUserView.get(getAdapterPosition()).getUserId();
                    String userName = listUserView.get(getAdapterPosition()).getNickName();
                    Intent intent=new Intent(context, ChannelActivity.class);
                    intent.putExtra("owner_id",userID);
                    intent.putExtra("channel_name",userName);
                    context.startActivity(intent);
                }
            });
        }
    }

    // interface AsyncResponse
    public interface AsyncResponse {
        void processFinish(String output);
    }

}
