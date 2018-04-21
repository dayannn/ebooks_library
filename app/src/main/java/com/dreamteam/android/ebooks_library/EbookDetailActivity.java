package com.dreamteam.android.ebooks_library;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;

public class EbookDetailActivity extends AppCompatActivity {

    private String mEbookInfo;
    private TextView mEbookInfoDisplay;

    private String mEbookAuthor;
    private TextView mEbookAuthorDisplay;

    private Button mPlayAudioButton;

    private boolean is_playing = false;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook_detail);

        player = MediaPlayer.create(EbookDetailActivity.this, R.raw.music);

        mEbookInfoDisplay = findViewById(R.id.tv_display_ebook_info);
        mEbookAuthorDisplay = findViewById(R.id.tv_display_ebook_author);

        mPlayAudioButton = findViewById(R.id.but_play_audio);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null){
            if (intentThatStartedThisActivity.hasExtra("book_name")){
                mEbookInfo = intentThatStartedThisActivity.getStringExtra("book_name");
                mEbookInfoDisplay.setText(mEbookInfo);
            }
            if (intentThatStartedThisActivity.hasExtra("book_author")){
                mEbookAuthor = intentThatStartedThisActivity.getStringExtra("book_author");
                mEbookAuthorDisplay.setText(mEbookAuthor);
            }
        }


        // TODO: убрать костыль
        mPlayAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (!is_playing) {
                player.start();
                mPlayAudioButton.setText("Пауза");
                is_playing = true;
            }
            else {
                player.pause();
                mPlayAudioButton.setText("Воспроизвести");
                is_playing = false;
            }
            }
        });
    }

}
