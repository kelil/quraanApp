package com.harar.khalil.quraan.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.harar.khalil.quraan.R;
import com.harar.khalil.quraan.adapters.SuraAdapter;
import com.harar.khalil.quraan.databases.DatabaseHelp;
import com.harar.khalil.quraan.globals.Global;
import com.harar.khalil.quraan.interfaces.FragmentListener;
import com.harar.khalil.quraan.interfaces.ItemClickListener;
import com.harar.khalil.quraan.models.SuraList;

import java.util.List;


public class SuraFragment extends Fragment {

    private FragmentListener fragmentListener;
    private ListView mlistView;
    private DatabaseHelp databaseHelp;
    private List<SuraList> suraListList;
    private SuraList suraList;

    public SuraFragment() {

    }

    public static SuraFragment newInstance(DatabaseHelp databaseHelp) {
        SuraFragment fragment = new SuraFragment();
        fragment.databaseHelp = databaseHelp;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sura, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mlistView = view.findViewById(R.id.dictlist);
        suraListList = databaseHelp.getSuraList("");

        final SuraAdapter suraAdapter = new SuraAdapter(getContext(), suraListList);
        mlistView.setAdapter(suraAdapter);
        suraAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void itemclick(int position) {
                suraList = suraListList.get(position);
                if (fragmentListener != null) {
                    String lang = Global.getState(getActivity());
                    fragmentListener.OnclickItem(databaseHelp, suraList.getId(), suraList.getOromo(), suraList.getPosition(), lang);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setOnFragmentListener(FragmentListener listener) {
        this.fragmentListener = listener;

    }

}
