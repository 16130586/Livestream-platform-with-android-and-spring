package com.t4.androidclient.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.t4.androidclient.R;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.httpclient.SqliteAuthenticationHelper;
import com.t4.androidclient.model.livestream.Notification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private List<Notification> listNotification;
    private Context context;
    private LinearLayout container;

    public NotificationAdapter(List<Notification> listNotification, Context context) {
        this.listNotification = listNotification;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_inbox, parent, false);

        // Return a new holder instance
        NotificationAdapter.ViewHolder viewHolder = new NotificationAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = listNotification.get(position);
        container = holder.container;

        if (notification.getStream().getThumbnail() != null) {
            Glide.with(context).load(notification.getStream().getThumbnail())
                    .placeholder(R.drawable.place_holder).centerCrop().into(holder.avatarView);
        }

        TextView messageView = holder.messageView;
        messageView.setText(notification.getMessage());

        TextView nameView = holder.nameView;
        nameView.setText(notification.getStream().getStreamName());

        ImageButton deleteButton = holder.deleteButton;
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteInbox(position, notification.getId());
            }
        });

        LinearLayout linkButton = holder.linkButton;
        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // start new activity here
                messageView.setText("asd");
            }
        });


    }

    @Override
    public int getItemCount() {
        return listNotification.size();
    }

    public void deleteInbox(int position, long id) {
        listNotification.remove(position);
        DeleteInbox deleteInbox = new DeleteInbox(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                notifyDataSetChanged();
            }
        });
        deleteInbox.execute(Long.toString(id));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView avatarView;
        public TextView messageView;
        public TextView nameView;
        public ImageButton deleteButton;
        public LinearLayout linkButton;
        public LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);

            avatarView = (CircleImageView) itemView.findViewById(R.id.inbox_avatar);
            messageView = (TextView) itemView.findViewById(R.id.inbox_message);
            nameView = (TextView) itemView.findViewById(R.id.inbox_name);
            linkButton = (LinearLayout) itemView.findViewById(R.id.inbox_link);
            deleteButton = (ImageButton) itemView.findViewById(R.id.inbox_delete);
            container = (LinearLayout) itemView.findViewById(R.id.inbox_container);
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
        protected String doInBackground(String... input) {
            SqliteAuthenticationHelper db = new SqliteAuthenticationHelper(context);
            Map<String, String> keyValues = new HashMap<>();
            keyValues.put("id", input[0]);
            Request request = HttpClient.buildPostRequest(Api.URL_DELETE_NOTIFICATION, keyValues, db.getToken());
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
}
