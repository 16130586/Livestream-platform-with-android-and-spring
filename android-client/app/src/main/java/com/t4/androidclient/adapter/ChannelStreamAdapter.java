package com.t4.androidclient.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.t4.androidclient.R;
import com.t4.androidclient.model.CompareDate;
import com.t4.androidclient.model.livestream.LiveStream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ChannelStreamAdapter extends RecyclerView.Adapter<ChannelStreamAdapter.ViewHolder>{
    private List<LiveStream> listStream;
    private Context context;

    byte[] imageBytes;
    Bitmap thumnailImage;

    public ChannelStreamAdapter(List<LiveStream> listStream, Context context) {
        this.listStream = listStream;
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
        LiveStream stream = listStream.get(position);

        if (stream.getThumbnail() != null) {
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

        TextView streamTitleView = holder.streamTitle;
        streamTitleView.setText(stream.getTitle());

        TextView streamPublishTimeView = holder.streamPublishTime;
        Date endTimeDate = stream.getEndTime();

        CompareDate compareDate = new CompareDate();
        long diffDayNumber = compareDate.compareDateToCurrent(endTimeDate);
        System.out.println(diffDayNumber);
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
        streamViewsView.setText(stream.getTotalView().toString()+" lượt xem");




    }

    @Override
    public int getItemCount() {
        return listStream.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumnailImage;
        public TextView streamTitle;
        public TextView streamPublishTime;
        public TextView streamViews;

        public ViewHolder(View itemView) {
            super(itemView);

            thumnailImage = (ImageView) itemView.findViewById(R.id.item_stream_thumbail_image);
            streamTitle = (TextView) itemView.findViewById(R.id.item_stream_title);
            streamPublishTime = (TextView) itemView.findViewById(R.id.item_stream_publish_time);
            streamViews = (TextView) itemView.findViewById(R.id.item_stream_views);

        }
    }

    // interface AsyncResponse
    public interface AsyncResponse {
        void processFinish(String output);
    }

    private class DeleteInbox extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public DeleteInbox(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... strings) {
//            String url = "link to server to get the inbox";
////
////            System.out.println("=============================================================");
////            System.out.println("The delete url: " + url);
////            System.out.println("=============================================================");
////
////            OkHttpClient client = new OkHttpClient();
////            Request request = new Request.Builder().url(url).build();
////
////            try (Response response = client.newCall(request).execute()) {
////                String rs = response.body().string();
////
////                return rs;
////            } catch (IOException e) {
////                e.printStackTrace();
////                return "false";
////            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
}
