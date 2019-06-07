package com.harar.khalil.quraan.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.harar.khalil.quraan.R;
import com.harar.khalil.quraan.models.Quranim;

import java.util.ArrayList;


public class QuranAdapter extends RecyclerView.Adapter<QuranAdapter.ViewHolder> {
    public ArrayList<Quranim> data;
    private Context c;

    public QuranAdapter(Context c, ArrayList<Quranim> data) {
        this.c = c;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View quranview = inflater.inflate(R.layout.quran_content, parent, false);
        return new ViewHolder(quranview, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Quranim quranim = data.get(position);

        Glide.with(c).load(quranim.getUrl()).into(holder.page);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        ImageView page;

        ViewHolder(final View itemView, final Context context) {
            super(itemView);
            this.context = context;
            page = itemView.findViewById(R.id.page);
        }
    }
}
