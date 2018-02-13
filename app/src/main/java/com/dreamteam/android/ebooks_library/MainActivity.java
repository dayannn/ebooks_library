package com.dreamteam.android.ebooks_library;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements EbooksAdapter.EbooksAdapterOnClickHandler{

    private RecyclerView mEbooksRecylerView;

    private EbooksAdapter mEbooksAdapter;

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
    }

    public void loadEbooksData(){
        String[] ebooks = {
                "Преступление и наказание",
                "Алиса в стране чудес",
                "Фауст",
                "Автостопом по галактике"};

        mEbooksAdapter.setEbooksData(ebooks);
    }

    @Override
    public void onClick(String ebookName) {
        Context context = this;
        Class destinationClass = EbookDetailActivity.class;
        Intent intentToStartActivity = new Intent(context, destinationClass);
        intentToStartActivity.putExtra(Intent.EXTRA_TEXT, ebookName);
        startActivity(intentToStartActivity);
    }
}
