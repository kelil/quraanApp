package com.harar.khalil.quraan.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.harar.khalil.quraan.R;
import com.harar.khalil.quraan.databases.DatabaseHelp;
import com.harar.khalil.quraan.models.BookMark;

import java.util.List;

public class BookMarkAdapter extends BaseAdapter {

    private List<BookMark> data;
    private Context context;
    private DatabaseHelp databaseHelp;

    public BookMarkAdapter(List<BookMark> data, Context context, DatabaseHelp databaseHelp) {
        this.data = data;
        this.context = context;
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

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.bookmark_content, parent, false);
            viewHolder.arabict = convertView.findViewById(R.id.arabicb);
            viewHolder.transt = convertView.findViewById(R.id.translation);
            viewHolder.sura = convertView.findViewById(R.id.suraname);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BookMark bookMark = data.get(position);
        viewHolder.arabict.setText(bookMark.getArabic());
        viewHolder.transt.setText(bookMark.getId() + ". " + bookMark.getTranslation());
        String name = databaseHelp.getSuraName(bookMark.getSura());
        viewHolder.sura.setText("Suratul " + name);
        Log.i("wow", String.valueOf(bookMark.getSura()));

        return convertView;
    }

    class ViewHolder {
        TextView arabict, transt, sura;
    }
}
