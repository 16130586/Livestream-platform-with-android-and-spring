package com.t4.androidclient.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.t4.androidclient.R;
import com.t4.androidclient.model.CompareDate;

import java.util.Date;
import java.util.List;

import viewModel.StreamViewModel;


public class WatchedStreamAdapter extends RecyclerView.Adapter<WatchedStreamAdapter.ViewHolder>{
    private List<StreamViewModel> listStreamView;
    private Context context;

    byte[] imageBytes;
    Bitmap thumnailImage;

    public WatchedStreamAdapter(List<StreamViewModel> listStreamView, Context context) {
        this.listStreamView = listStreamView;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_mychannel_watched_stream, parent, false);
        WatchedStreamAdapter.ViewHolder viewHolder = new WatchedStreamAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StreamViewModel streamViewModel = listStreamView.get(position);

        if (streamViewModel.getThumbnail() != null) {
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

        TextView streamNameView = holder.streamName;
        streamNameView.setText(streamViewModel.getStreamName());

        TextView streamViewsView = holder.streamOwnerName;
        streamViewsView.setText(streamViewModel.getOwner().getNickname());

    }

    @Override
    public int getItemCount() {
        return listStreamView.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumnailImage;
        public TextView streamName;
        public TextView streamOwnerName;

        public ViewHolder(View itemView) {
            super(itemView);
            thumnailImage = (ImageView) itemView.findViewById(R.id.item_watched_stream_thumbail_image);
            streamName = (TextView) itemView.findViewById(R.id.item_watched_stream_name);
            streamOwnerName = (TextView) itemView.findViewById(R.id.item_watched_stream_owner_name);

        }
    }

    // interface AsyncResponse
    public interface AsyncResponse {
        void processFinish(String output);
    }

}
