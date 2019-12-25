/**
 * This is sample code provided by Wowza Media Systems, LLC.  All sample code is intended to be a reference for the
 * purpose of educating developers, and is not intended to be used in any production environment.
 * <p>
 * IN NO EVENT SHALL WOWZA MEDIA SYSTEMS, LLC BE LIABLE TO YOU OR ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL,
 * OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION,
 * EVEN IF WOWZA MEDIA SYSTEMS, LLC HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p>
 * WOWZA MEDIA SYSTEMS, LLC SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. ALL CODE PROVIDED HEREUNDER IS PROVIDED "AS IS".
 * WOWZA MEDIA SYSTEMS, LLC HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 * <p>
 * © 2015 – 2019 Wowza Media Systems, LLC. All rights reserved.
 */

package com.t4.androidclient;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;

import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.view.GestureDetectorCompat;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.On;
import com.github.nkzawa.socketio.client.Socket;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Authentication;
import com.t4.androidclient.contraints.Host;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.model.livestream.Comment;
import com.t4.androidclient.ui.AutoFocusListener;
import com.t4.androidclient.ui.MultiStateButton;
import com.t4.androidclient.ui.TimerView;
import com.wowza.gocoder.sdk.api.devices.WOWZCamera;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.Request;


public class CameraActivity extends CameraActivityBase {


    // UI controls
    protected MultiStateButton mBtnSwitchCamera = null;
    protected MultiStateButton mBtnTorch = null;
    protected TimerView mTimerView = null;

    // Gestures are used to toggle the focus modes
    protected GestureDetectorCompat mAutoFocusDetector = null;
    private View btnLoading = null;
    private View btnBroastCast = null;

    private RelativeLayout containerComments = null;
    private LinearLayout containerCommentPopup = null;
    private CommentBuffer commentBuffer;
    private final int ANIMATION_DURATION = 3000;
    private final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    private final int DX = (int) (16 * displayMetrics.density);
    private final int DY = (int) (64 * displayMetrics.density);
    private Socket mSocket;
    private AnimationThread animationThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        mRequiredPermissions = new String[]{


                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };
        mBtnTorch = findViewById(R.id.ic_torch);
        mBtnSwitchCamera = findViewById(R.id.ic_switch_camera);
        mTimerView = findViewById(R.id.txtTimer);
        btnLoading = findViewById(R.id.btn_spin_kit);
        btnBroastCast = findViewById(R.id.ic_broadcast);

        containerComments = (RelativeLayout) findViewById(R.id.container_comments);
        containerCommentPopup = findViewById(R.id.container_comments_popup);
    }

    /**
     * Android Activity lifecycle methods
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (this.hasDevicePermissionToAccess() && sGoCoderSDK != null && mWZCameraView != null) {
            // making starting calling here
            if (mAutoFocusDetector == null)
                mAutoFocusDetector = new GestureDetectorCompat(this, new AutoFocusListener(this, mWZCameraView));

            WOWZCamera activeCamera = mWZCameraView.getCamera();
            if (activeCamera != null && activeCamera.hasCapability(WOWZCamera.FOCUS_MODE_CONTINUOUS))
                activeCamera.setFocusMode(WOWZCamera.FOCUS_MODE_CONTINUOUS);

            new StartALiveStreamCaller(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    if (output != null) {
                        ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                        if (response.statusCode == 200) {
                            btnBroastCast.setVisibility(View.VISIBLE);
                            btnLoading.setVisibility(View.GONE);
                            try {
                                if (mSocket != null && mSocket.connected())
                                    mSocket.disconnect();
                                mSocket = IO.socket(Host.SOCKET_HOST);
                                mSocket.on("server-send-comment", onNewComment);
                                mSocket.connect();
                                mSocket.emit("client-send-id", JsonHelper.serialize(svm.getStreamId()));
                                commentBuffer = new CommentBuffer(displayMetrics.heightPixels,
                                        displayMetrics.widthPixels,
                                        ANIMATION_DURATION,
                                        DX,
                                        DY);
                                if (animationThread == null || !animationThread.stillRun) {
                                    animationThread = new AnimationThread(commentBuffer, containerComments, getBaseContext());
                                    animationThread.start();
                                }

                            } catch (URISyntaxException e) {
                                displayDialog(e.getMessage(), "Error");
                            }
                        }
                    } else {
                        displayDialog("Starting live stream is failed!", "Error");
                    }
                }
            }).execute(Api.URL_START_A_LIVE_STREAM.replace("{id}", svm.getStreamId() + ""));
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
                    Comment cmt;
                    try {
                        String ownerName = data.getString("owner_name");
                        String message = data.getString("message");
                        int commentId = data.getInt("comment_id");
                        int ownerId = data.getInt("owner_id");
                        cmt = new Comment();
                        cmt.setOwnerName(ownerName);
                        cmt.setMessage(message);
                        cmt.setCommentId(commentId);
                        cmt.setOwnerId(ownerId);
                        // add the message to view if not conflict id
                        onNewCommentArrive(cmt);
                        System.out.println(JsonHelper.serialize(cmt));
                    } catch (JSONException e) {
                        displayDialog(e.getMessage(), "Cmt Socketio");
                        return;
                    }


                }
            });
        }
    };

    class CommentBuffer {
        public Comment[] bufferOfComments; // buffer nay se nhan cmt tu socket IO ve
        private final int animationDuration;
        private final int width;
        private final int height;
        private final int dx;
        private final int dy;
        private int currentIndex = 0;

        public CommentBuffer(int height, int width, int animationDuration, int dx, int dy) {
            this.animationDuration = animationDuration;
            this.width = width;
            this.height = height;
            this.dx = dx;
            this.dy = dy;
            bufferOfComments = new Comment[calculateMaxBufferSize(height, width, dx, dy)];
        }

        private int calculateMaxBufferSize(int height, int width, int dx, int dy) {
            return (int) ((0.6 * height) / dy);
        }

        public void add(Comment cmt) {
            if (currentIndex + 1 > 2000000) {
                currentIndex %= bufferOfComments.length;
            }
            bufferOfComments[currentIndex % bufferOfComments.length] = cmt;
            currentIndex++;
        }

        public Comment get(int i) {
            return bufferOfComments[i];
        }

        public Comment remove(int i) {
            Comment ret = get(i);
            bufferOfComments[i] = null;
            return ret;
        }

    }

    private synchronized void onNewCommentArrive(Comment cmt) {
        if (cmt.getOwnerId() != MainScreenActivity.user.id) {
            commentBuffer.add(cmt);
            animationThread.interrupt();
        }
    }

    public Activity getActivity() {
        return this;
    }

    private class StartALiveStreamCaller extends AsyncTask<String, Void, String> {
        public AsyncResponse asyncResponse;

        public StartALiveStreamCaller(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... urls) {
            if (urls.length <= 0)
                return null;
            String url = urls[0];
            Request request = HttpClient.buildPostRequest(url, null, Authentication.TOKEN);
            String body = HttpClient.execute(request);
            return body;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (asyncResponse != null)
                asyncResponse.processFinish(s);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSocket != null)
            mSocket.disconnect();
        if (animationThread != null && animationThread.stillRun) {
            animationThread.down();
        }
        // making stop calling here
        new StopALiveStreamCaller(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                // turns all to default state
                btnLoading.setVisibility(View.VISIBLE);
                btnBroastCast.setVisibility(View.GONE);
            }
        }).execute(Api.URL_STOP_A_LIVE_STREAM.replace("{id}", svm.getStreamId() + ""));
    }

    private class StopALiveStreamCaller extends AsyncTask<String, Void, String> {
        public AsyncResponse asyncResponse;

        public StopALiveStreamCaller(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... urls) {
            if (urls.length <= 0)
                return null;
            String url = urls[0];
            Request request = HttpClient.buildPostRequest(url, null, Authentication.TOKEN);
            String body = HttpClient.execute(request);
            return body;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (asyncResponse != null)
                asyncResponse.processFinish(s);
        }
    }

    /**
     * Click handler for the switch camera button
     */
    public void onSwitchCamera(View v) {
        if (mWZCameraView == null) return;

        mBtnTorch.setState(false);
        mBtnTorch.setEnabled(false);

        // Set the new surface extension prior to camera switch such that
        // setting will take place with the new one.  So if it is currently the front
        // camera, then switch to default setting (not mirrored).  Otherwise show mirrored.
//        if(mWZCameraView.getCamera().getDirection() == WOWZCamera.DIRECTION_FRONT) {
//            mWZCameraView.setSurfaceExtension(mWZCameraView.EXTENSION_DEFAULT);
//        }
//        else{
//            mWZCameraView.setSurfaceExtension(mWZCameraView.EXTENSION_MIRROR);
//        }

        WOWZCamera newCamera = mWZCameraView.switchCamera();
        if (newCamera != null) {
            if (newCamera.hasCapability(WOWZCamera.FOCUS_MODE_CONTINUOUS))
                newCamera.setFocusMode(WOWZCamera.FOCUS_MODE_CONTINUOUS);

            boolean hasTorch = newCamera.hasCapability(WOWZCamera.TORCH);
            if (hasTorch) {
                mBtnTorch.setState(newCamera.isTorchOn());
                mBtnTorch.setEnabled(true);
            }
        }
    }

    /**
     * Click handler for the settings button
     */
    public void onSettings(View v) {
        super.onSettings(v);
    }

    /**
     * Click handler for the ToggleBroadcast button
     */
    public void onToggleBroadcast(View v) {
        super.onToggleBroadcast(v);
    }

    /**
     * Click handler for the torch/flashlight button
     */
    public void onToggleTorch(View v) {
        if (mWZCameraView == null) return;

        WOWZCamera activeCamera = mWZCameraView.getCamera();
        activeCamera.setTorchOn(mBtnTorch.toggleState());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mAutoFocusDetector != null)
            mAutoFocusDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    /**
     * Update the state of the UI controls
     */
    @Override
    protected boolean syncUIControlState() {
        boolean disableControls = super.syncUIControlState();

        if (disableControls) {
            mBtnSwitchCamera.setEnabled(false);
            mBtnTorch.setEnabled(false);
        } else {
            boolean isDisplayingVideo = (this.hasDevicePermissionToAccess(Manifest.permission.CAMERA) && getBroadcastConfig().isVideoEnabled() && mWZCameraView.getCameras().length > 0);
            boolean isStreaming = getBroadcast().getStatus().isRunning();

            if (isDisplayingVideo) {
                WOWZCamera activeCamera = mWZCameraView.getCamera();

                boolean hasTorch = (activeCamera != null && activeCamera.hasCapability(WOWZCamera.TORCH));
                mBtnTorch.setEnabled(hasTorch);
                if (hasTorch) {
                    mBtnTorch.setState(activeCamera.isTorchOn());
                }

                mBtnSwitchCamera.setEnabled(mWZCameraView.getCameras().length > 0);
            } else {
                mBtnSwitchCamera.setEnabled(false);
                mBtnTorch.setEnabled(false);
            }

            if (isStreaming && !mTimerView.isRunning()) {
                mTimerView.startTimer();
            } else if (getBroadcast().getStatus().isIdle() && mTimerView.isRunning()) {
                mTimerView.stopTimer();
            } else if (!isStreaming) {
                mTimerView.setVisibility(View.GONE);
            }
        }

        return disableControls;
    }

    protected void displayDialog(String msg, String title) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
            builder.setMessage(msg)
                    .setTitle(title);
            builder.setPositiveButton(R.string.dialog_button_close, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } catch (Exception ex) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSocket != null)
            mSocket.disconnect();
        if (animationThread != null && animationThread.stillRun) animationThread.down();
    }

    class AnimationThread extends Thread {
        private CommentBuffer commentBuffer;
        private boolean stillRun = true;
        private RelativeLayout animatedContainer;
        private Context context;

        public AnimationThread(CommentBuffer buffer, RelativeLayout container, Context context) {
            this.commentBuffer = buffer;
            this.animatedContainer = container;
            this.context = context;
        }

        public synchronized void down() {
            stillRun = false;
            this.notify();
        }

        @Override
        public void run() {
            while (stillRun) {
                try {
                    if (commentBuffer == null) Thread.sleep(2000);

                    for (int i = 0, realProcess = 0; i < commentBuffer.bufferOfComments.length; i++) {
                        Comment cmt = commentBuffer.remove(i);
                        if (cmt == null)
                            continue;
                        realProcess++;
                        animatedContainer.postDelayed(new Runnable() {
                            int counter;

                            public Runnable setCounter(int i) {
                                counter = i;
                                return this;
                            }

                            @Override
                            public void run() {
                                TextView t = new TextView(context);
                                String rawText = cmt.getOwnerName() + ": " + cmt.getMessage();
                                String displayText = rawText.length() > 25 ? (rawText.substring(0, 24) + "...") : rawText;
                                t.setText(displayText);
                                t.setWidth(DX * displayText.length());
                                t.setHeight(DY);
                                t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                // 8 - 9 ki tu cho 1 dong -> max ~ 27 ki tu cho 1 cmt, nhieu hon cat ra
                                t.setTextColor(Color.BLUE);
                                t.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        containerCommentPopup.setVisibility(View.VISIBLE);
                                        View sendBtn = containerCommentPopup.findViewById(R.id.comment_popup_btn_send);
                                        TextView popupMessage = containerCommentPopup.findViewById(R.id.comment_popup_message);
                                        EditText msgInput = (EditText) containerCommentPopup.findViewById(R.id.comment_popup_reply);
                                        popupMessage.setText(cmt.getOwnerName() + ": " + cmt.getMessage());

                                        sendBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                containerCommentPopup.setVisibility(View.GONE);
                                                String msg = msgInput.getText().toString().trim();
                                                popupMessage.setText("");
                                                msgInput.setText("");

                                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                                Comment pushComment = new Comment();
                                                if(msg.isEmpty()){
                                                    return;
                                                }

                                                pushComment.setMessage("@" + cmt.getOwnerName() + " " + msg);
                                                new PushCommentTask(new AsyncResponse() {
                                                    @Override
                                                    public void processFinish(String output) {
                                                    }
                                                }).execute(pushComment);
                                            }
                                        });
                                    }
                                });
                                RelativeLayout.LayoutParams params = new
                                        RelativeLayout.LayoutParams
                                        (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                                if (counter % 2 == 0) {
                                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                    params.rightMargin = (int) (4 * displayMetrics.densityDpi);
                                } else {
                                    params.addRule(RelativeLayout.ALIGN_LEFT);
                                    params.leftMargin = (int) (4 * displayMetrics.density);
                                }

                                animatedContainer.addView(t, params);
                                int fromY;
                                if (counter % 2 == 0)
                                    fromY = (int) (0.75f * Resources.getSystem().getDisplayMetrics().heightPixels) + DY * counter;
                                else
                                    fromY = (int) (0.85f * Resources.getSystem().getDisplayMetrics().heightPixels) + DY * counter;

                                ObjectAnimator move = ObjectAnimator.ofFloat(t, "translationY", fromY
                                        , 0f);
                                move.setDuration(ANIMATION_DURATION + counter * 200);

                                ObjectAnimator alpha2 = ObjectAnimator.ofFloat(t, "alpha", 1f, 0f);
                                alpha2.setDuration(ANIMATION_DURATION + counter * 200);
                                AnimatorSet animset = new AnimatorSet();
                                animset.play(alpha2).with(move);
                                animset.start();
                                animset.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {


                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        animation.cancel();
                                        animatedContainer.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                animatedContainer.removeView(t);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });
                            }
                        }.setCounter(realProcess), 1200);
                    }
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    continue;
                }

            }
        }
    }

    private class PushCommentTask extends AsyncTask<Comment, Void, String> {
        public AsyncResponse asyncResponse;

        public PushCommentTask(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(Comment... comments) {
            String url = Api.URL_POST_COMMENT + "/" + svm.getStreamId() + "/comment";
            Map<String, String> keyValues = new HashMap<>();
            Comment comment = comments[0];
            keyValues.put("message", comment.getMessage());
            keyValues.put("streamStatus", svm.getStatus().toString());
            keyValues.put("ownerName", MainScreenActivity.user.nickname);
            keyValues.put("videoTime", "200");
            Request request = HttpClient.buildPostRequest(url, keyValues, Authentication.TOKEN);
            return HttpClient.execute(request);
        }
    }
}
