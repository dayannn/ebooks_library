package com.dreamteam.android.ebooks_library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dayan on 14.02.2018.
 */

public class EbooksAdapter extends RecyclerView.Adapter<EbooksAdapter.EbooksAdapterViewHolder> {

    private String[] mEbooksData;
    private String[] mEbooksAuthorsData;

    private final EbooksAdapterOnClickHandler mClickHandler;

    // The interface that receives OnClick messages
    public interface EbooksAdapterOnClickHandler {
        void onClick(String ebookName, String ebookAuthor);
    }

    public EbooksAdapter(EbooksAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    // Cache of the children views for a forecast list item.
    public class EbooksAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mEbookTextView;
        public final TextView mEbookAuthorTextView;
        public final ImageView mEbookImageView;

        public EbooksAdapterViewHolder(View view){
            super(view);
            mEbookTextView = (TextView) view.findViewById(R.id.tv_ebook_list_item_data);
            mEbookAuthorTextView = (TextView) view.findViewById(R.id.tv_ebook_list_item_author);
            mEbookImageView = (ImageView) view.findViewById(R.id.book_icon);
            mEbookImageView.setImageResource(R.drawable.book_icon);
            view.setOnClickListener(this);
        }

        // The method is called by the child views during a click.
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String ebookName = mEbooksData[adapterPosition];
            String ebookAuthor = mEbooksAuthorsData[adapterPosition];
            mClickHandler.onClick(ebookName, ebookAuthor);
        }
    }

    // The method is called when each new ViewHolder is created. This happens when the RecyclerView
    // is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
    @Override
    public EbooksAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.ebook_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new EbooksAdapterViewHolder(view);
    }


    // onBindViewHolder is called by the RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(EbooksAdapterViewHolder holder, int position) {
        String ebookName = mEbooksData[position];
        String ebookAuthor = mEbooksAuthorsData[position];
        holder.mEbookTextView.setText(ebookName);
        holder.mEbookAuthorTextView.setText(ebookAuthor);
    }

    // returns number of items to display
    @Override
    public int getItemCount() {
        if (mEbooksData == null)
            return 0;
        return mEbooksData.length;
    }

    public void setEbooksData(String[] ebooksData, String[] ebooksAuthorsData){
        mEbooksData = ebooksData;
        mEbooksAuthorsData = ebooksAuthorsData;
        notifyDataSetChanged();
    }
}
