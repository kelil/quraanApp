package com.harar.khalil.quraan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.harar.khalil.quraan.R;
import com.harar.khalil.quraan.interfaces.ItemClickListener;
import com.harar.khalil.quraan.models.SuraList;

import java.util.List;


public class SuraAdapter extends BaseAdapter {

    private List<SuraList> suraListList;
    private Context context;
    private ItemClickListener itemClickListener;

    public SuraAdapter(Context context, List<SuraList> suraLists) {
        this.context = context;
        this.suraListList = suraLists;

    }

    @Override
    public int getCount() {
        return suraListList.size();
    }

    @Override
    public Object getItem(int position) {
        return suraListList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return suraListList.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.sura_name, parent, false);
            viewHolder.arabic = convertView.findViewById(R.id.arabic);
            viewHolder.trans = convertView.findViewById(R.id.trans);
            viewHolder.juz = convertView.findViewById(R.id.juz);
            viewHolder.linearLayout = convertView.findViewById(R.id.linear);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SuraList suraList = suraListList.get(position);
        viewHolder.arabic.setText(suraList.getArabic());
        viewHolder.trans.setText(suraList.getOromo());
        viewHolder.juz.setText(suraList.getJuz());
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) itemClickListener.itemclick(position);
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_left);
        convertView.startAnimation(animation);

        return convertView;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    class ViewHolder {
        TextView arabic, trans, juz;
        LinearLayout linearLayout;
    }
}
