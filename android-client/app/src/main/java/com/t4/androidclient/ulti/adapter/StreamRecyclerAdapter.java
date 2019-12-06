package com.t4.androidclient.ulti.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.t4.androidclient.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import viewModel.StreamViewModel;


public class StreamRecyclerAdapter extends
        RecyclerView.Adapter<StreamRecyclerAdapter.ViewHolder> {
    private List<StreamViewModel> listStream;
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

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_stream, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        StreamViewModel streamModel = listStream.get(position);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes;
        imageBytes = Base64.decode(streamModel.getThumbnailView(), Base64.URL_SAFE);
        Bitmap thumbnailImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        imageBytes = Base64.decode(streamModel.getOwnerAvatar(), Base64.DEFAULT);
        Bitmap ownerAvatarImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        // Set item views based on your views and data model

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
    }

    @Override
    public int getItemCount() {
        return listStream.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
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
