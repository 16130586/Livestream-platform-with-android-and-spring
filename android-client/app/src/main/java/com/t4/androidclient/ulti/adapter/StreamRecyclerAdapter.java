package com.t4.androidclient.ulti.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.t4.androidclient.R;
import com.t4.androidclient.contraints.Host;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.ui.channel.ChannelActivity;
import com.t4.androidclient.ui.livestream.WatchLiveStreamActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import viewModel.StreamViewModel;

public class StreamRecyclerAdapter extends
        RecyclerView.Adapter<StreamRecyclerAdapter.ViewHolder> {
    private List<StreamViewModel> listStream;
    private final int STATUS_ENDED = 2;
    private final int STATUS_ON_LIVE = 1;
    private Context context;

    public StreamRecyclerAdapter(List<StreamViewModel> listStream, Context context) {
        this.listStream = listStream;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_stream, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        StreamViewModel streamModel = listStream.get(position);

//        byte[] imageBytes;

//        imageBytes = Base64.decode(streamModel.getOwner().avatar == null ? "" : streamModel.getOwner().avatar, Base64.DEFAULT);
//        Bitmap ownerAvatarImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        
        Glide.with(context).load(streamModel.getThumbnail() == null ? "" : streamModel.getThumbnail())
                    .placeholder(R.drawable.place_holder).centerCrop().into(holder.thumbnailView);
        ImageView ownerAvatarView = holder.avatarView;


//        Glide.with(context).load(ownerAvatarImage)
//                .placeholder(R.drawable.place_holder).centerCrop().into(holder.avatarView);
        String avatarURL = streamModel.getOwner().avatar;
        if (avatarURL != null && !avatarURL.isEmpty())
            Glide.with(context).load(avatarURL.startsWith("http") ? avatarURL : Host.API_HOST_IP + avatarURL) // plays as url
                    .placeholder(R.drawable.ic_fire).centerCrop().into(holder.avatarView);

        TextView titleView = holder.titleView;
        titleView.setText(streamModel.getTitle());

        holder.thumbnailView.setOnClickListener(e -> {
            Toast.makeText(context, "Click on video thumbnail " + position + " !", Toast.LENGTH_SHORT).show();
            navigateToWatchingActivity(listStream.get(position));
        });
        ownerAvatarView.setOnClickListener(e -> {
            Toast.makeText(context, "Click on owner avatar " + position + " !", Toast.LENGTH_SHORT).show();
            Class nextActivity = ChannelActivity.class;
            if (nextActivity != null) {
                Intent t = new Intent(context, nextActivity);
                t.putExtra("DATA", listStream.get(position).getOwner().getId());
                t.putExtra("DATA2", listStream.get(position).getOwner().getUsername());
                context.startActivity(t);
            }
        });
        titleView.setOnClickListener(e -> {
            Toast.makeText(context, "Click on streamName " + position + " !", Toast.LENGTH_SHORT).show();
            navigateToWatchingActivity(listStream.get(position));
        });

        TextView timeView = holder.timeView;
        String txtInfo = streamModel.getOwner().nickname + " - " + streamModel.getTotalView() + " views - " + streamModel.getDateStatus();
        timeView.setText(txtInfo);
        TextView txtLiveNOW = holder.liveNOW;
        if ("Live NOW".equals(streamModel.getDateStatus()))
            txtLiveNOW.setVisibility(View.VISIBLE);
        else
            txtLiveNOW.setVisibility(View.GONE);

    }

    private void navigateToWatchingActivity(StreamViewModel modelClicked) {
        if (modelClicked == null) {
            Toast.makeText(context, "Something is crashing !", Toast.LENGTH_SHORT).show();
            return;
        }
        ;
        Class nextActivity = null;
        switch (modelClicked.getStatus()) {
            case STATUS_ENDED:
                nextActivity = null;
                Toast.makeText(context, "Navigate to see video!", Toast.LENGTH_SHORT).show();
                break;
            case STATUS_ON_LIVE:
                nextActivity = WatchLiveStreamActivity.class;
                Toast.makeText(context, "Navigate to see live stream!", Toast.LENGTH_SHORT).show();
                break;
        }
        if (nextActivity != null) {
            Intent t = new Intent(context, nextActivity);
            t.putExtra("DATA", JsonHelper.serialize(modelClicked));
            context.startActivity(t);
        }
    }

    @Override
    public int getItemCount() {
        return listStream.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnailView;
        public CircleImageView avatarView;
        public TextView titleView, timeView, liveNOW;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnailView = (ImageView) itemView.findViewById(R.id.item_stream_thumbnail);
            avatarView = (CircleImageView) itemView.findViewById(R.id.item_stream_avatar);
            titleView = (TextView) itemView.findViewById(R.id.item_stream_name);
            timeView = (TextView) itemView.findViewById(R.id.item_stream_time_status);
            liveNOW = (TextView) itemView.findViewById(R.id.text_live_now);
        }
    }

    public void clear() {
        listStream.clear();
    }
}
