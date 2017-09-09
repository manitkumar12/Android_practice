package com.grademojo.android_practice;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

public class MainActivity extends AppCompatActivity {


    private static  final String TAG = MainActivity.class.getSimpleName();

    private CheckBox checkBox;

    private EmojiconEditText emojiconEditText;

    private EmojiconTextView emojiconTextView;

    private ImageView emoji_Image_view ,submit_button;

    private View rootview;

    private EmojIconActions emojIconActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        rootview = findViewById(R.id.rootview);
        emoji_Image_view = (ImageView) findViewById(R.id.emoji_btn);
        submit_button = (ImageView) findViewById(R.id.submit_btn);
        checkBox = (CheckBox) findViewById(R.id.use_system_default);
        emojiconEditText = (EmojiconEditText) findViewById(R.id.emojicon_edit_text);
        emojiconTextView = (EmojiconTextView) findViewById(R.id.textView);
        emojIconActions = new EmojIconActions(this, rootview, emojiconEditText, emoji_Image_view);


        emojIconActions.ShowEmojIcon();
        emojIconActions.setIconsIds(R.drawable.ic_action_keyboard,R.drawable.smiley);

        emojIconActions.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e(TAG, "Keyboard opened!");

            }

            @Override
            public void onKeyboardClose() {
                Log.e(TAG, "Keyboard closed!");

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                emojIconActions.setUseSystemEmoji(isChecked);
                emojiconTextView.setUseSystemDefault(isChecked);
            }
        });


        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_text = emojiconEditText.getText().toString();

                emojiconTextView.setText(new_text);
            }
        });


    }
}
