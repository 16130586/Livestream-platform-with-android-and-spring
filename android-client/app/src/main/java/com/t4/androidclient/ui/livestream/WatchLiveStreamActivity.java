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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.t4.androidclient.MainActivity;
import com.t4.androidclient.MainScreenActivity;
import com.t4.androidclient.R;
import com.t4.androidclient.adapter.CommentAdapter;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Authentication;
import com.t4.androidclient.contraints.Host;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.model.helper.CommentHelper;
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

import org.json.JSONException;
import org.json.JSONObject;

public class WatchLiveStreamActivity extends AppCompatActivity {
    private StreamViewModel streamViewModel;
    private TextView tagView, titleView, viewsView, ownerNameView, ownerSubscribersView, timeView;
    private CircleImageView ownerAvatarView;
    private EditText commentInput;
    private ImageButton commentSendButton;
    private LinearLayoutManager linearLayoutManager;
    private CommentAdapter adapter;
    private RecyclerView recyclerView;
    private List<Comment> commentList;
    private List<Integer> commentIdList;
    private Socket mSocket;
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

        ownerAvatarView = findViewById(R.id.stream_watch_owner_avatar);
        Glide.with(ownerAvatarView.getContext()).load(streamViewModel.getOwner().getAvatar())
                .centerCrop().into(ownerAvatarView);

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
                        //commentIdList.add(comment.getCommentId());
                    }
                });
                pushCommentTask.execute(comment);
            }
        });

        mSocket.on("server-send-comment", onNewComment);
        mSocket.connect();
        mSocket.emit("client-send-id",JsonHelper.serialize(streamViewModel.getStreamId()) );
    }

    public void addTen() {
        int n = 0;
        while(n < 5){
            commentList.add(new Comment("jack", "messssss"));
            n++;
        }
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
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(commentList.size() - 1);
    }


}
