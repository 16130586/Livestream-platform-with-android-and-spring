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


public class ChannelStreamAdapter extends RecyclerView.Adapter<ChannelStreamAdapter.ViewHolder>{
    private List<StreamViewModel> listStreamView;
    private Context context;

    byte[] imageBytes;
    Bitmap thumnailImage;

    public ChannelStreamAdapter(List<StreamViewModel> listStreamView, Context context) {
        this.listStreamView = listStreamView;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_channel_stream, parent, false);

        // Return a new holder instance
        ChannelStreamAdapter.ViewHolder viewHolder = new ChannelStreamAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StreamViewModel streamViewModel = listStreamView.get(position);

        if (streamViewModel.getThumbnailView() != null) {
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

        TextView streamPublishTimeView = holder.streamPublishTime;
        Date endTimeDate = streamViewModel.getEndTime();

        CompareDate compareDate = new CompareDate();
        long diffDayNumber = compareDate.compareDateToCurrent(endTimeDate);
        if(diffDayNumber > 364 ){
            long yearNumber = diffDayNumber/365;
            streamPublishTimeView.setText(yearNumber+" năm trước");
        }else  if(diffDayNumber > 30 ){
            long monthNumber = diffDayNumber/30;
            streamPublishTimeView.setText(monthNumber+" tháng trước");
        }else  if(diffDayNumber > 7 ){
            long weekNumber = diffDayNumber/7;
            streamPublishTimeView.setText(weekNumber+" tuần trước");
        }else{
            streamPublishTimeView.setText(diffDayNumber+" ngày trước");
        }

        TextView streamViewsView = holder.streamViews;
        streamViewsView.setText(streamViewModel.getTotalView()/1000+"N lượt xem");

    }

    @Override
    public int getItemCount() {
        return listStreamView.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumnailImage;
        public TextView streamName;
        public TextView streamPublishTime;
        public TextView streamViews;

        public ViewHolder(View itemView) {
            super(itemView);

            thumnailImage = (ImageView) itemView.findViewById(R.id.item_stream_thumbail_image);
            streamName = (TextView) itemView.findViewById(R.id.item_stream_name);
            streamPublishTime = (TextView) itemView.findViewById(R.id.item_stream_publish_time);
            streamViews = (TextView) itemView.findViewById(R.id.item_stream_views);

        }
    }

    // interface AsyncResponse
    public interface AsyncResponse {
        void processFinish(String output);
    }

}
