package com.dreamteam.android.ebooks_library;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements EbooksAdapter.EbooksAdapterOnClickHandler{

    private RecyclerView mEbooksRecylerView;

    private EbooksAdapter mEbooksAdapter;

    private ImageView mImageView;

    private boolean isSignedIn = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEbooksRecylerView = (RecyclerView) findViewById(R.id.recyclerview_ebooks);

        LinearLayoutManager linLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mEbooksRecylerView.setLayoutManager(linLayoutManager);
        mEbooksRecylerView.setHasFixedSize(true);

        mEbooksAdapter = new EbooksAdapter(this);
        mEbooksRecylerView.setAdapter(mEbooksAdapter);

        loadEbooksData();

        // check if user is signed in
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        isSignedIn = getResources().getBoolean(R.bool.pref_is_loginned);
        if(!isSignedIn) {
            SharedPreferences.Editor edit = prefs.edit();
           // edit.putBoolean(getString(R.string.pref_is_signed_in), Boolean.TRUE);

            edit.commit();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void loadEbooksData(){
        String[] ebooks = {
                "Преступление и наказание",
                "Алиса в стране чудес",
                "Фауст",
                "Автостопом по галактике"};

        String[] authors = {
                "Ф.М. Достоевский",
                "Л. Кэррол",
                "И.В. Гёте",
                "Д. Адамс"};
        mEbooksAdapter.setEbooksData(ebooks, authors);
    }

    @Override
    public void onClick(String ebookName, String ebookAuthor) {
        Context context = this;
        Class destinationClass = EbookDetailActivity.class;

        Intent intentToStartActivity = new Intent(context, destinationClass);
        intentToStartActivity.putExtra("book_name", ebookName);
        intentToStartActivity.putExtra("book_author", ebookAuthor);
        startActivity(intentToStartActivity);

        // opening animation
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
