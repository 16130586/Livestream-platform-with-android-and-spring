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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.session.MediaSession;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Authentication;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.ui.AutoFocusListener;
import com.t4.androidclient.ui.MultiStateButton;
import com.t4.androidclient.ui.TimerView;
import com.wowza.gocoder.sdk.api.broadcast.WOWZBroadcastConfig;
import com.wowza.gocoder.sdk.api.devices.WOWZCamera;
import com.wowza.gocoder.sdk.api.logging.WOWZLog;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

import okhttp3.Request;
import retrofit2.http.Url;
import viewModel.StreamViewModel;

public class CameraActivity extends CameraActivityBase {

    // UI controls
    protected MultiStateButton mBtnSwitchCamera = null;
    protected MultiStateButton mBtnTorch = null;
    protected TimerView mTimerView = null;

    // Gestures are used to toggle the focus modes
    protected GestureDetectorCompat mAutoFocusDetector = null;
    private View btnLoading = null;
    private View btnBroastCast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        mRequiredPermissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };

        // Initialize the UI controls
        mBtnTorch = findViewById(R.id.ic_torch);
        mBtnSwitchCamera = findViewById(R.id.ic_switch_camera);
        mTimerView = findViewById(R.id.txtTimer);
        btnLoading = findViewById(R.id.btn_spin_kit);
        btnBroastCast = findViewById(R.id.ic_broadcast);
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
                        }
                    } else {
                        displayDialog("An error is rose when try to start a live stream!", "Error");
                    }
                }
            }).execute(Api.URL_START_A_LIVE_STREAM.replace("{id}", svm.getStreamId() + ""));
        }
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


}
