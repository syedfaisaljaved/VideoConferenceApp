package com.videoconferenceapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    //Ui components
    private EditText mRoomId;
    private Button mButton;
    private SwitchCompat mAudioVideoSwitch, mVideoOnOffSwitch, mMicOnOffSwitch;

    //vars
    private Boolean bool1, bool2, bool3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRoomId = findViewById(R.id.room_etv);
        mButton = findViewById(R.id.button);
        mAudioVideoSwitch = findViewById(R.id.videoaudio_switch);
        mVideoOnOffSwitch = findViewById(R.id.video_switch);
        mMicOnOffSwitch = findViewById(R.id.mic_switch);


        init();
        setClickListeners();
//        checkPermissions();
    }

    private void init() {
        JitsiMeetConferenceOptions defaultOptions
                = null;
        try {
            defaultOptions = new JitsiMeetConferenceOptions.Builder()
            .setServerURL(new URL("https://meet.jit.si"))
            .setWelcomePageEnabled(false)
            .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JitsiMeet.setDefaultConferenceOptions(defaultOptions);
    }

    private void setClickListeners() {
        mButton.setOnClickListener(this);
        mRoomId.setOnEditorActionListener(this);
    }
//
//    public void checkPermissions(){
//        String[] PermissionsList = {
//                Manifest.permission.CAPTURE_AUDIO_OUTPUT,
//                Manifest.permission.RECORD_AUDIO,
//                Manifest.permission.MODIFY_AUDIO_SETTINGS,
//                Manifest.permission.CAMERA
//        };
//
//        if(!hasPermissions(this, PermissionsList)){
//            ActivityCompat.requestPermissions(this, PermissionsList, 1);
//        }
//    }
//
//    public static boolean hasPermissions(Context context, String... permissions) {
//        if (context != null && permissions != null) {
//            for (String permission : permissions) {
//                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.button:
                startVideoActivity();
                break;
        }
    }

    private void startVideoActivity() {
        String roomId = mRoomId.getText().toString().trim();
        configureSettings();

        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL("https://meet.jit.si"))
                    .setRoom(roomId)
                    .setAudioMuted(bool1)
                    .setVideoMuted(bool2)
                    .setAudioOnly(bool3)
                    .setWelcomePageEnabled(false)
                    .build();

            if (!roomId.isEmpty()){
                JitsiMeetActivity.launch(this, options);
            }else {
                Toast.makeText(this, "Enter a room ID", Toast.LENGTH_SHORT).show();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void configureSettings() {
        if (mAudioVideoSwitch.isChecked()){
            bool3 = true;
        }
        else {
            bool3 = false;
        }
        if (mVideoOnOffSwitch.isChecked()){
            bool2 = true;
        }
        else {
            bool2 = false;
        }
        if (mMicOnOffSwitch.isChecked()){
            bool1 = true;
        }
        else {
            bool1 = false;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            startVideoActivity();
        }
        return true;
    }

}
