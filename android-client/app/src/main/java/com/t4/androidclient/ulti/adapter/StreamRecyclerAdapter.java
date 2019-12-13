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

import com.t4.androidclient.R;
import com.t4.androidclient.core.JsonHelper;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import viewModel.StreamViewModel;

public class StreamRecyclerAdapter extends
        RecyclerView.Adapter<StreamRecyclerAdapter.ViewHolder> {
    private List<StreamViewModel> listStream;
    private final int STATUS_ENDED = -1;
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
        byte[] imageBytes;
        imageBytes = Base64.decode(streamModel.getThumbnailView(), Base64.URL_SAFE);
        Bitmap thumbnailImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        imageBytes = Base64.decode(streamModel.getOwnerAvatar(), Base64.DEFAULT);
        Bitmap ownerAvatarImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);


        ImageView thumbnailView = holder.thumbnailView;
        if (thumbnailImage != null && thumbnailImage.getByteCount() > 0)
            thumbnailView.setImageBitmap(thumbnailImage);
        else {
            thumbnailView.setImageDrawable(context.getDrawable(R.drawable.place_holder));
        }
        ImageView ownerAvatarView = holder.avatarView;
        if (ownerAvatarImage != null && ownerAvatarImage.getByteCount() > 0)
            ownerAvatarView.setImageBitmap(ownerAvatarImage);
        else {
            ownerAvatarView.setImageDrawable(context.getDrawable(R.drawable.place_holder));
        }

        TextView titleView = holder.titleView;
        titleView.setText(streamModel.getTitle());

        thumbnailView.setOnClickListener(e -> {
            Toast.makeText(context, "Click on video thumbnail " + position + " !", Toast.LENGTH_SHORT).show();
            navigateToWatchingActivity(listStream.get(position));
        });
        ownerAvatarView.setOnClickListener(e -> {
            Toast.makeText(context, "Click on owner avatar " + position + " !", Toast.LENGTH_SHORT).show();
            Class nextActivity = null;
            if (nextActivity != null) {
                Intent t = new Intent(context, nextActivity);
                t.putExtra("DATA", JsonHelper.serialize(listStream.get(position)));
                context.startActivity(t);
            }
        });
        titleView.setOnClickListener(e -> {
            Toast.makeText(context, "Click on title " + position + " !", Toast.LENGTH_SHORT).show();
            navigateToWatchingActivity(listStream.get(position));
        });
    }

    private void navigateToWatchingActivity(StreamViewModel modelClicked) {
        if (modelClicked == null) {
            Toast.makeText(context, "Something is crashing !", Toast.LENGTH_SHORT).show();
            return;
        }
        ;
        Class nextActivity = null;
        switch (modelClicked.status) {
            case STATUS_ENDED:
                nextActivity = null;
                Toast.makeText(context, "Navigate to see video!", Toast.LENGTH_SHORT).show();
                break;
            case STATUS_ON_LIVE:
                nextActivity = null;
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
        public TextView titleView;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnailView = (ImageView) itemView.findViewById(R.id.item_stream_thumbnail);
            avatarView = (CircleImageView) itemView.findViewById(R.id.item_stream_avatar);
            titleView = (TextView) itemView.findViewById(R.id.item_stream_title);
        }
    }
}
