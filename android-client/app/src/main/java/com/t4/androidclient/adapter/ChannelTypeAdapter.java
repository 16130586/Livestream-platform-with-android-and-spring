package com.t4.androidclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.t4.androidclient.R;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Host;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.model.helper.LiveStreamHelper;
import com.t4.androidclient.model.helper.StreamTypeHelper;
import com.t4.androidclient.model.livestream.LiveStream;
import com.t4.androidclient.ui.channel.AllStreamsFragment;
import com.t4.androidclient.ui.channel.ChannelStreamsOfTypeActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import viewModel.StreamTypeViewModel;
import viewModel.StreamViewModel;


public class ChannelTypeAdapter extends RecyclerView.Adapter<ChannelTypeAdapter.ViewHolder>{
    private List<StreamTypeViewModel> listTypeView;
    private Context context;
    private ArrayList<String> dataList;
    byte[] imageBytes;
    Bitmap thumnailImage;

    public ChannelTypeAdapter(List<StreamTypeViewModel> listTypeView, Context context, ArrayList<String> dataList) {
        this.listTypeView = listTypeView;
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_channel_type_stream, parent, false);
        // Return a new holder instance
        ChannelTypeAdapter.ViewHolder viewHolder = new ChannelTypeAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StreamTypeViewModel typeViewModel = listTypeView.get(position);



        TextView typeNameView = holder.typeName;
        typeNameView.setText(typeViewModel.getTypeName());

        TextView numberOfTypeView = holder.numberOfType;
        numberOfTypeView.setText(typeViewModel.getNumberOfType()+" streams");

        TextView numberOfTypeTextView = holder.numberOfTypeText;
        int numberText = typeViewModel.getNumberOfType();
        if(numberText<1000){
            numberOfTypeTextView.setText(numberText+"");
        }else{
            numberOfTypeTextView.setText(numberText/1000+" N");
        }
    }

    @Override
    public int getItemCount() {
        return listTypeView.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumnailImage;
        public TextView typeName;
        public TextView numberOfType;
        public TextView numberOfTypeText;

        public ViewHolder(View itemView) {
            super(itemView);
            thumnailImage = (ImageView) itemView.findViewById(R.id.item_type_thumbail_image);
            typeName = (TextView) itemView.findViewById(R.id.item_type_name);
            numberOfType = (TextView) itemView.findViewById(R.id.item_type_number_stream);
            numberOfTypeText = (TextView) itemView.findViewById(R.id.item_type_number_stream_text);

            GetListStream getListStream = new GetListStream(new com.t4.androidclient.core.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    if (output == null) return;
                    try {
                        ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                        if (response != null && response.statusCode == 200) {
                            List<Map<String, Object>> streams = (List<Map<String, Object>>) response.data;
                            if (streams != null && streams.size() > 0) {
                                for (Map<String, Object> obj : streams) {
                                    LiveStream liveStream = LiveStreamHelper.parse(obj);
                                    if (liveStream != null) {
                                        StreamViewModel streamView = new StreamViewModel();
                                        streamView.setStreamId(liveStream.getStreamId());
                                        streamView.setTitle(liveStream.getName());
                                        streamView.setEndTime(liveStream.getEndTime() != null ? liveStream.getEndTime() : new Date(1573837200)); //16/11/2019
                                        streamView.setTotalView(liveStream.getTotalView() != null ? liveStream.getTotalView() : 69069);
                                        streamView.setThumbnail(liveStream.getThumbnail() != null ? liveStream.getThumbnail() : "");
                                        String avatarURL = streamView.getThumbnail();
                                        if (avatarURL != null && !avatarURL.isEmpty())
                                            Glide.with(thumnailImage.getContext()).load(avatarURL.startsWith("http") ? avatarURL : Host.API_HOST_IP + avatarURL) // plays as url
                                                    .placeholder(R.drawable.ic_fire).centerCrop().into(thumnailImage);
                                    } else  {
                                        continue;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            int ownerID = 1;
            String[] values = new String[4];
            values[0] = String.valueOf(ownerID);
            values[1] = "1"; // type ID
            values[2] = "1" ;
            values[3] = "1" ;

            getListStream.execute(values);




            // Handle item click and set the selection
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int typeID = listTypeView.get(getAdapterPosition()).getId();
                    int numberOfType= listTypeView.get(getAdapterPosition()).getNumberOfType();
                    String typeName= listTypeView.get(getAdapterPosition()).getTypeName();
                    Intent intent=new Intent(context, ChannelStreamsOfTypeActivity.class);
                    intent.putExtra("type_id",typeID);
                    intent.putExtra("owner_id",Integer.valueOf(dataList.get(0)));
                    intent.putExtra("channel_name",dataList.get(1));
                    intent.putExtra("type_name",typeName);
                    intent.putExtra("stream_number",numberOfType);

                    context.startActivity(intent);
                }
            });
        }
    }

    // interface AsyncResponse
    public interface AsyncResponse {
        void processFinish(String output);
    }


    // get image of type example
    private class GetListStream extends AsyncTask<String, Integer, String> {
        public com.t4.androidclient.core.AsyncResponse asyncResponse;

        public GetListStream(com.t4.androidclient.core.AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... token) {
            Request request = HttpClient.buildGetRequest(Api.URL_CHANNEL_GET_STREAMS_BY_TYPE+"/"+token[0]+"/"+token[1]+"/"+token[2]+"/"+token[3]);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (asyncResponse != null)
                asyncResponse.processFinish(s);
        }
    }
}
