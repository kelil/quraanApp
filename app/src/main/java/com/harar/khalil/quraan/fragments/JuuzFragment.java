package com.harar.khalil.quraan.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harar.khalil.quraan.R;
import com.harar.khalil.quraan.databases.DatabaseHelp;

public class JuuzFragment extends Fragment {

    private DatabaseHelp databaseHelp;

    public JuuzFragment() {
    }

    public static JuuzFragment newInstance(DatabaseHelp databaseHelp) {
        JuuzFragment fragment = new JuuzFragment();
        fragment.databaseHelp = databaseHelp;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_juuz, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
