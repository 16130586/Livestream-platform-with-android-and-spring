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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.t4.androidclient.MainScreenActivity;
import com.t4.androidclient.R;
import com.t4.androidclient.adapter.CommentAdapter;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Authentication;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.model.helper.CommentHelper;
import com.t4.androidclient.model.livestream.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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


public class WatchVideoActivity extends AppCompatActivity {
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
    private VideoView videoView;
    private GetCommentWhenPlayingThread getCommentThread;


    private boolean showComment = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_live_stream);

        bindNavigateData(getIntent());
        setUp();

        PlayerManager.getInstance().getDefaultVideoInfo().addOption(Option.create(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "multiple_requests", 1L));
        videoView = findViewById(R.id.video_view);

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
                if (showComment && getCommentThread != null) {
                    getCommentThread.interrupt();
                }
            }

            @Override
            public void onRelease(GiraffePlayer giraffePlayer) {
                Toast.makeText(getBaseContext(), "onRelease:", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart(GiraffePlayer giraffePlayer) {

                if (showComment) {
                    if(getCommentThread != null){
                        getCommentThread.down();
                        getCommentThread.interrupt();
                        getCommentThread = null;
                        if(commentList != null && commentList.size() > 0){
                            commentList.clear();
                            adapter.notifyDataSetChanged();
                        }

                    }
                    getCommentThread = new GetCommentWhenPlayingThread();
                    getCommentThread.start();
                }

            }

            @Override
            public void onTargetStateChange(int oldState, int newState) {
                Toast.makeText(getBaseContext(), "onTargetStateChange: currentPosition : " + videoView.getPlayer().getCurrentPosition() + "  -  new: " + newState, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCurrentStateChange(int oldState, int newState) {

            }

            @Override
            public void onDisplayModelChange(int oldModel, int newModel) {

            }

            @Override
            public void onPreparing(GiraffePlayer giraffePlayer) {
                Toast.makeText(getBaseContext(), "onPreparing: ", Toast.LENGTH_SHORT).show();
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
        if (streamViewModel.getStoredUrl() != null)
            videoView.setVideoPath(streamViewModel.getStoredUrl());
    }

    private class GetCommentWhenPlayingThread extends Thread {
        private Map<Integer , Boolean> lastGet = new LinkedHashMap<>();
        private boolean stillRun = true;
        public synchronized  void down(){
            stillRun = false;
        }
        @Override
        public void run() {
            try{
                while (stillRun) {
                    if(!(videoView.isCurrentActivePlayer() && videoView.getPlayer().isPlaying())){
                        Thread.sleep(1000);
                    }
                    int currentTime = videoView.getPlayer().getCurrentPosition() / 1000;
                    if(lastGet.get(currentTime) != null && lastGet.get(currentTime)){
                        Thread.sleep(1000);
                        continue;
                    }
                    new GetCommentTask(new AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            ApiResponse response = JsonHelper.deserialize(output , ApiResponse.class);
                            if(response.statusCode == 200){
                                List<Comment> cmts = new LinkedList<>();
                                for(Map<String, Object> cmt : (List<Map<String,Object>>)response.data){
                                    cmts.add(JsonHelper.deserialize(cmt , Comment.class));
                                }
                                int oldSize = commentList.size();
                                commentList.addAll(cmts);
                                adapter.notifyItemRangeChanged(oldSize - 1 , cmts.size());
                            }

                        }
                    }).execute(currentTime);
                    lastGet.put(currentTime , true);
                    Thread.sleep(1000);
                }
            }catch (Exception e){

            }

        }
    }

    private class GetCommentTask extends AsyncTask<Integer, Void, String> {
        public AsyncResponse asyncResponse;

        public GetCommentTask(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(Integer... videoTimes) {
            String url = Api.URL_GET_COMMENT_BY_VIDEO_TIME;
            url = url.replace("{streamId}", streamViewModel.getStreamId() + "");
            url = url.replace("{videoTime}", videoTimes[0] + "");
            Request request = HttpClient.buildGetRequest(url, Authentication.TOKEN);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void bindNavigateData(Intent previousNavigationData) {
        this.streamViewModel = JsonHelper.deserialize(previousNavigationData.getStringExtra("DATA"), StreamViewModel.class);
    }

    public void setUp() {
        commentList = new ArrayList<>();

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

        commentInputContainer = findViewById(R.id.stream_watch_comment_container);
        commentInput = findViewById(R.id.stream_watch_comment);
        commentSendButton = findViewById(R.id.stream_watch_comment_button);
        commentSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment = new Comment();
                comment.setOwnerId(MainScreenActivity.user.getId());
                comment.setOwnerName(MainScreenActivity.user.nickname);
                comment.setMessage(commentInput.getText().toString().trim());
                if (comment.getMessage().isEmpty())
                    return;
                if (videoView.isCurrentActivePlayer() && videoView.getPlayer().isPlaying())
                    comment.setVideoTime(videoView.getPlayer().getCurrentPosition() / 1000);
                else if (videoView.isCurrentActivePlayer())
                    comment.setVideoTime(videoView.getPlayer().getDuration() / 1000);
                else
                    comment.setVideoTime(1);
                PushCommentTask pushCommentTask = new PushCommentTask(new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        ApiResponse response = JsonHelper.deserialize(output , ApiResponse.class);
                        if(response.statusCode < 400){
                            commentInput.setText("");
                        }
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
                    if(getCommentThread == null) getCommentThread = new GetCommentWhenPlayingThread();
                    if(videoView.isCurrentActivePlayer() && videoView.getPlayer().isPlaying())
                        getCommentThread.start();
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    commentInputContainer.setVisibility(View.VISIBLE);
                    showCommentButton.setImageResource(R.drawable.comment);
                    if(getCommentThread != null){
                        getCommentThread.down();
                        getCommentThread.interrupt();
                        getCommentThread = null;
                    }
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

    }


    private class PushCommentTask extends AsyncTask<Comment, Void, String> {
        public AsyncResponse asyncResponse;

        public PushCommentTask(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(Comment... comments) {
            String url = Api.URL_POST_COMMENT + "/" + streamViewModel.getStreamId() + "/comment";
            Map<String, Object> keyValues = new HashMap<>();
            Comment comment = comments[0];
            keyValues.put("message", comment.getMessage());
            keyValues.put("streamStatus", streamViewModel.getStatus());
            keyValues.put("ownerName", MainScreenActivity.user.nickname);
            keyValues.put("videoTime", comment.getVideoTime());
            Request request = HttpClient.buildPostRequest(url, keyValues, Authentication.TOKEN);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
}
