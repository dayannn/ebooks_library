package com.dreamteam.android.ebooks_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EbookDetailActivity extends AppCompatActivity {

    private String mEbookInfo;
    private TextView mEbookInfoDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook_detail);

        mEbookInfoDisplay = findViewById(R.id.tv_display_ebook_info);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null){
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
                mEbookInfo = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                mEbookInfoDisplay.setText(mEbookInfo);
            }
        }

    }
}
