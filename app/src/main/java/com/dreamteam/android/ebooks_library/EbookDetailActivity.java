package com.dreamteam.android.ebooks_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EbookDetailActivity extends AppCompatActivity {

    private String mEbookInfo;
    private TextView mEbookInfoDisplay;

    private String mEbookAuthor;
    private TextView mEbookAuthorDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook_detail);

        mEbookInfoDisplay = findViewById(R.id.tv_display_ebook_info);
        mEbookAuthorDisplay = findViewById(R.id.tv_display_ebook_author);

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

    }
}
