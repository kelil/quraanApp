package com.harar.khalil.quraan.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.harar.khalil.quraan.R;
import com.harar.khalil.quraan.databases.DatabaseHelp;
import com.harar.khalil.quraan.models.SuraContent;

import java.util.List;

public class ContentAdapter extends BaseAdapter {

    private List<SuraContent> data;
    private Context context;
    private String language;
    private DatabaseHelp databaseHelp;
    private String table;

    public ContentAdapter(Context context, List<SuraContent> data, String oromo, DatabaseHelp databaseHelp) {
        this.context = context;
        this.data = data;
        this.language = oromo;
        this.databaseHelp = databaseHelp;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.sura_content, parent, false);
            viewHolder.arabict = convertView.findViewById(R.id.arabict);
            viewHolder.transt = convertView.findViewById(R.id.transt);
            viewHolder.bookmark = convertView.findViewById(R.id.bookmark);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SuraContent suraList = data.get(position);
        viewHolder.arabict.setText(suraList.getArabic());
        if (language.equalsIgnoreCase(String.valueOf(R.id.oromo))) {
            table = "Oromic";
            if (suraList.getId() != 0) {
                viewHolder.transt.setText(suraList.getId() + ". " + suraList.getOromo());
            } else {
                viewHolder.transt.setText(suraList.getOromo());
            }
        } else if (language.equalsIgnoreCase(String.valueOf(R.id.amharic))) {
            table = "Amharic";
            if (suraList.getId() != 0) {
                viewHolder.transt.setText(suraList.getId() + ". " + suraList.getAmharic());
            } else {
                viewHolder.transt.setText(suraList.getAmharic());
            }
        } else {
            table = "English";
            if (suraList.getId() != 0) {
                viewHolder.transt.setText(suraList.getId() + ". " + suraList.getEnglish());
            } else {
                viewHolder.transt.setText(suraList.getEnglish());
            }
        }

        boolean isBook = databaseHelp.isBookMark(data.get(position).getRowId(), table);

        if (isBook) {
            viewHolder.bookmark.setTag(1);
            viewHolder.bookmark.setImageResource(R.drawable.ic_bookmark_fill);
        } else {
            viewHolder.bookmark.setTag(0);
            viewHolder.bookmark.setImageResource(R.drawable.ic_bookmark);
        }
        viewHolder.bookmark.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                int i = (int) viewHolder.bookmark.getTag();
                if (i == 0) {
                    viewHolder.bookmark.setImageResource(R.drawable.ic_bookmark_fill);
                    viewHolder.bookmark.setTag(1);
                    databaseHelp.updateBookmark(data.get(position).getRowId(), 1, table);
                } else {
                    viewHolder.bookmark.setImageResource(R.drawable.ic_bookmark);
                    viewHolder.bookmark.setTag(0);
                    databaseHelp.updateBookmark(data.get(position).getRowId(), 0, table);
                }
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_left);
        convertView.startAnimation(animation);

        return convertView;
    }

    class ViewHolder {
        TextView arabict, transt;
        ImageView bookmark;
    }
}
