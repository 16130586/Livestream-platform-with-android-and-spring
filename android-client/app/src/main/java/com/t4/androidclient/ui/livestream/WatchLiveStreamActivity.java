package com.t4.androidclient.ui.livestream;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.t4.androidclient.MainActivity;
import com.t4.androidclient.MainScreenActivity;
import com.t4.androidclient.R;
import com.t4.androidclient.adapter.CommentAdapter;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Authentication;
import com.t4.androidclient.contraints.Host;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.httpclient.SqliteAuthenticationHelper;
import com.t4.androidclient.model.helper.CommentHelper;
import com.t4.androidclient.model.helper.UserHelper;
import com.t4.androidclient.model.livestream.Comment;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;
import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.Option;
import tcking.github.com.giraffeplayer2.PlayerListener;
import tcking.github.com.giraffeplayer2.PlayerManager;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;
import tcking.github.com.giraffeplayer2.VideoView;
import viewModel.StreamViewModel;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;
import com.t4.androidclient.model.livestream.User;
import com.t4.androidclient.ui.channel.AboutFragment;
import com.t4.androidclient.ui.channel.ChannelActivity;
import com.t4.androidclient.ui.login.LoginRegisterActivity;
import com.t4.androidclient.ui.mychannel.MyChannelActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class WatchLiveStreamActivity extends AppCompatActivity {
    private StreamViewModel streamViewModel;
    private TextView tagView, titleView, viewsView, ownerNameView, ownerSubscribersView, timeView;
    private CircleImageView ownerAvatarView;
    private EditText commentInput;
    private ImageButton commentSendButton, showCommentButton;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout commentInputContainer;
    private CommentAdapter adapter;
    private RecyclerView recyclerView;
    private List<Comment> commentList;
    private List<Integer> commentIdList;
    private Socket mSocket;
    private boolean showComment = true;
    private User currentUser;
    {
        try {
            mSocket = IO.socket(Host.SOCKET_HOST);
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_live_stream);

        bindNavigateData(getIntent());
        setUp();
        funcSubscribeAndGoChannel();

        PlayerManager.getInstance().getDefaultVideoInfo().addOption(Option.create(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "multiple_requests", 1L));
        final VideoView videoView = findViewById(R.id.video_view);

        videoView.setPlayerListener(new PlayerListener() {
            @Override
            public void onPrepared(GiraffePlayer giraffePlayer) {

            }

            @Override
            public void onBufferingUpdate(GiraffePlayer giraffePlayer, int percent) {

            }

            @Override
            public boolean onInfo(GiraffePlayer giraffePlayer, int what, int extra) {
                return false;
            }

            @Override
            public void onCompletion(GiraffePlayer giraffePlayer) {

            }

            @Override
            public void onSeekComplete(GiraffePlayer giraffePlayer) {

            }

            @Override
            public boolean onError(GiraffePlayer giraffePlayer, int what, int extra) {
                return false;
            }

            @Override
            public void onPause(GiraffePlayer giraffePlayer) {

            }

            @Override
            public void onRelease(GiraffePlayer giraffePlayer) {
                Toast.makeText(getBaseContext(), "onRelease:" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart(GiraffePlayer giraffePlayer) {

                Toast.makeText(getBaseContext(), "onStart: old : " + giraffePlayer.getCurrentPosition() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTargetStateChange(int oldState, int newState) {
                Toast.makeText(getBaseContext(), "onTargetStateChange: currentPosition : " + videoView.getPlayer().getCurrentPosition() + "  -  new: " + newState , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCurrentStateChange(int oldState, int newState) {

            }

            @Override
            public void onDisplayModelChange(int oldModel, int newModel) {

            }

            @Override
            public void onPreparing(GiraffePlayer giraffePlayer) {
                Toast.makeText(getBaseContext(), "onPreparing: " , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTimedText(GiraffePlayer giraffePlayer, IjkTimedText text) {
                Toast.makeText(getBaseContext(), "onTimedText:" + text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLazyLoadProgress(GiraffePlayer giraffePlayer, int progress) {

            }

            @Override
            public void onLazyLoadError(GiraffePlayer giraffePlayer, String message) {

            }
        });



        Glide.with(videoView.getContext()).load(streamViewModel.getThumbnail())
                .centerCrop().into(videoView.getCoverView());
        if(streamViewModel.getHlsPlayBackUrl() != null)
            videoView.setVideoPath(streamViewModel.getHlsPlayBackUrl());
    }

    public void funcSubscribeAndGoChannel() {
        int ownerID = streamViewModel.getOwner().getId();
        Button subscribleButton = findViewById(R.id.watch_live_btn_subscrible);
        SqliteAuthenticationHelper db = new SqliteAuthenticationHelper(getActivity());
        Authentication.TOKEN = db.getToken();
        //Check login status
        if (Authentication.TOKEN == null || Authentication.TOKEN.isEmpty()) {
            subscribleButton.setText("Subscribe");
            subscribleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Please login to subscrible channel !",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginRegisterActivity.class);
                    startActivity(intent);
                }
            });
        }else{

            // Check status subscribe with channel owner , place before to call below
            CheckSubscribeTask checkSubscribeTask = new CheckSubscribeTask(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                        ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                        if (response != null && response.statusCode == 200) {
                            boolean result = (boolean) response.data;
                            if (result == true) { // True - Can subscrible
                                subscribleButton.setText("Subscribe");
                                subscribleButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (currentUser.getId() == ownerID) {
                                            Toast.makeText(getActivity(), "Do you want subscribe yourself ? LOL", Toast.LENGTH_SHORT).show();
                                        } else {
                                            SubscribeTask subscribeTask = new SubscribeTask(new AsyncResponse() {
                                                @Override
                                                public void processFinish(String output) {
                                                    ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                                                    if (response != null && response.statusCode == 200) {
                                                        boolean result = (boolean) response.data;
                                                        if (result == true) {
                                                            Toast.makeText(getActivity(), "Subscribe channel successfully !",
                                                                    Toast.LENGTH_SHORT).show();
                                                            // Reload current fragment
                                                            getActivity().finish();
                                                            getActivity().overridePendingTransition(0, 0);
                                                            startActivity(getActivity().getIntent());
                                                            getActivity().overridePendingTransition(0, 0);
                                                        }
                                                    }
                                                }
                                            });
                                            String[] subData = new String[2];
                                            subData[0] = currentUser.getId() + "";
                                            subData[1] = ownerID + "";
                                            subscribeTask.execute(subData);
                                        }
                                    }
                                });
                            } else { // False - Can't subscrible
                                subscribleButton.setText("Un Subscribe");
                                subscribleButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        UnSubscribeTask unSubscribeTask = new UnSubscribeTask(new AsyncResponse() {
                                            @Override
                                            public void processFinish(String output) {
                                                ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                                                if (response != null && response.statusCode == 200) {
                                                    boolean result = (boolean) response.data;
                                                    if (result == true) {
                                                        Toast.makeText(getActivity(), "Un Subscribe channel successfully !",
                                                                Toast.LENGTH_SHORT).show();
                                                        // Reload current fragment
                                                        getActivity().finish();
                                                        getActivity().overridePendingTransition(0, 0);
                                                        startActivity(getActivity().getIntent());
                                                        getActivity().overridePendingTransition(0, 0);
                                                    }
                                                }
                                            }
                                        });
                                        String[] subData = new String[2];
                                        subData[0] = currentUser.getId() + "";
                                        subData[1] = ownerID + "";
                                        unSubscribeTask.execute(subData);
                                        }
                                });
                            }
                        }
                    }
                });

            // If login already then check status subscrible
            // Get current user info to do task
            CurrentInfo currentInfo = new CurrentInfo(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    if (output != null && !output.isEmpty()) {
                        ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                        if (response != null && response.statusCode == 200) {
                            Map<String, Object> rawData = (Map<String, Object>) response.data;
                            currentUser = UserHelper.parseUserJson(rawData);
                        }
                    }
                    // Get currentUser successfully , do check Status Subscribe
                    String[] subData = new String[2];
                    subData[0]=currentUser.getId()+"";
                    subData[1]=ownerID+"";
                    checkSubscribeTask.execute(subData); //Call above method
                }

            });
            currentInfo.execute();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
    }

    private void bindNavigateData(Intent previousNavigationData) {
        this.streamViewModel = JsonHelper.deserialize(previousNavigationData.getStringExtra("DATA") , StreamViewModel.class);
    }

    public void setUp() {
        commentList = new ArrayList<>();
        commentIdList = new ArrayList<>();
        //addTen();

        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new CommentAdapter(commentList, this);

        recyclerView = (RecyclerView) findViewById(R.id.list_comment);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        tagView = findViewById(R.id.stream_watch_tag);
        tagView.setText(streamViewModel.getTag());

        titleView = findViewById(R.id.stream_watch_tittle);
        titleView.setText(streamViewModel.getTitle());

        viewsView = findViewById(R.id.stream_watch_views);
        viewsView.setText(streamViewModel.getTotalView() + " views");

        ownerNameView = findViewById(R.id.stream_watch_owner_name);
        ownerNameView.setText(streamViewModel.getOwner().getNickname());

        ownerSubscribersView = findViewById(R.id.stream_watch_owner_subscribers);
        ownerSubscribersView.setText(streamViewModel.getOwner().getSubscribeTotal() + " subscribers");

        timeView = findViewById(R.id.stream_watch_time);
        timeView.setText(streamViewModel.getDateStartString() + " | " + streamViewModel.getDateStatus());



        ownerAvatarView = (CircleImageView)  findViewById(R.id.stream_watch_owner_avatar);
        String avatarURL = streamViewModel.getOwner().getAvatar();
        if (avatarURL != null && !avatarURL.isEmpty())
            Glide.with(ownerAvatarView.getContext()).load(avatarURL.startsWith("http") ? avatarURL : Host.API_HOST_IP + avatarURL) // plays as url
                    .placeholder(R.drawable.ic_fire).centerCrop().into(ownerAvatarView);

//        Glide.with(ownerAvatarView.getContext()).load(streamViewModel.getOwner().getAvatar())
//                .placeholder(R.drawable.place_holder).centerCrop().into(ownerAvatarView);


        ownerAvatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class nextActivity = null;
                if(currentUser.getId()==streamViewModel.getOwner().getId()){
                     nextActivity = MyChannelActivity.class;
                }else{
                     nextActivity = ChannelActivity.class;
                }
                if (nextActivity != null) {
                    Intent t = new Intent(getActivity(), nextActivity);
                    t.putExtra("owner_id", streamViewModel.getOwner().getId());
                    t.putExtra("channel_name", streamViewModel.getOwner().getNickname());
                    startActivity(t);
                }
            }
        });

        commentInputContainer = findViewById(R.id.stream_watch_comment_container);
        commentInput = findViewById(R.id.stream_watch_comment);
        commentSendButton = findViewById(R.id.stream_watch_comment_button);
        commentSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment = new Comment();
                comment.setOwnerName(MainScreenActivity.user.nickname);
                comment.setMessage(commentInput.getText().toString());
                commentInput.setText("");
                //mSocket.emit("client-send-comment", JsonHelper.serialize(comment));

                PushCommentTask pushCommentTask = new PushCommentTask(new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        System.out.println(output);
                        Comment comment = CommentHelper.parseComment(output);
                        System.out.println("================================ " + comment.toString());
                    }
                });
                pushCommentTask.execute(comment);
            }
        });

        showCommentButton = findViewById(R.id.stream_watch_show_comment_button);
        showCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showComment) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    commentInputContainer.setVisibility(View.INVISIBLE);
                    showCommentButton.setImageResource(R.drawable.no_comment);
                    disconnectSocket();
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    commentInputContainer.setVisibility(View.VISIBLE);
                    showCommentButton.setImageResource(R.drawable.comment);
                    connectSocket();
                }
                showComment = !showComment;
            }
        });
        if (Authentication.ISLOGIN == false) {
            commentInputContainer.setVisibility(View.GONE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    37.0f
            );
            recyclerView.setLayoutParams(param);
        }

        mSocket.on("server-send-comment", onNewComment);
        connectSocket();
    }

    public void connectSocket() {
        mSocket.connect();
        mSocket.emit("client-send-id",JsonHelper.serialize(streamViewModel.getStreamId()));

    }

    public void disconnectSocket() {
        mSocket.disconnect();
    }


    private Emitter.Listener onNewComment = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    System.out.println(data.toString());
                    String ownerName;
                    String message;
                    int commentId;
                    try {
                        ownerName = data.getString("ownerName");
                        message = data.getString("message");
                        commentId = data.getInt("commentId");
                        System.out.println(ownerName + " ====== " + message + " ===== " + commentId);
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view if not conflict id
                        addMessage(ownerName, message);

                }
            });
        }
    };

    private class PushCommentTask extends AsyncTask<Comment, Void, String> {
        public AsyncResponse asyncResponse;

        public PushCommentTask(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(Comment... comments) {
            String url =Api.URL_POST_COMMENT + "/" + streamViewModel.getStreamId() + "/comment";
            Map<String, String> keyValues = new HashMap<>();
            Comment comment = comments[0];
            keyValues.put("message", comment.getMessage());
            keyValues.put("streamStatus", streamViewModel.getStatus().toString());
            keyValues.put("ownerName", MainScreenActivity.user.nickname);
            keyValues.put("videoTime", "200");
            Request request = HttpClient.buildPostRequest(url, keyValues, Authentication.TOKEN);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }

    public Activity getActivity() {
        return this;
    }

    public void addMessage(String username, String message) {
        commentList.add(new Comment(username, message));
        adapter.notifyItemRangeChanged(commentList.size() - 1 , 1);
        recyclerView.scrollToPosition(commentList.size() - 1);
    }

    private class CurrentInfo extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public CurrentInfo(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... data) {
            Request request = HttpClient.buildGetRequest(Api.URL_GET_INFO, Authentication.TOKEN);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }

    private class CheckSubscribeTask extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;
        public CheckSubscribeTask(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }
        @Override
        protected String doInBackground(String... data) {
            Map<String,String> dataMap = new HashMap<>();
            dataMap.put("subscriberID",data[0]);
            dataMap.put("publisherID",data[1]);
                Request request = HttpClient.buildPostRequest(Api.URL_CHANNEL_CHECK_SUBSCRIBE,dataMap);
                return HttpClient.execute(request);
        }
        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }

    private class SubscribeTask extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;
        public SubscribeTask(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }
        @Override
        protected String doInBackground(String... data) {
            Map<String,String> dataMap = new HashMap<>();
            dataMap.put("subscriberID",data[0]);
            dataMap.put("publisherID",data[1]);
            Request request = HttpClient.buildPostRequest(Api.URL_CHANNEL_SUBSCRIBE,dataMap);
            return HttpClient.execute(request);
        }
        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }

    private class UnSubscribeTask extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;
        public UnSubscribeTask(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }
        @Override
        protected String doInBackground(String... data) {
            Map<String,String> dataMap = new HashMap<>();
            dataMap.put("subscriberID",data[0]);
            dataMap.put("publisherID",data[1]);
            Request request = HttpClient.buildPostRequest(Api.URL_CHANNEL_UNSUBSCRIBE,dataMap);
            return HttpClient.execute(request);
        }
        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
}
