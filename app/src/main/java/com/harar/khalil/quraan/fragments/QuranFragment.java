package com.harar.khalil.quraan.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.harar.khalil.quraan.R;
import com.harar.khalil.quraan.adapters.ContentAdapter;
import com.harar.khalil.quraan.databases.DatabaseHelp;
import com.harar.khalil.quraan.globals.Global;
import com.harar.khalil.quraan.models.SuraContent;

import java.util.List;
import java.util.Objects;

public class QuranFragment extends Fragment {

    private int id;
    private String name;
    private DatabaseHelp databaseHelp;
    private List<SuraContent> contents;
    private ListView contentlist;
    protected SuraContent suraContent;
    private Toolbar toolbar;

    public QuranFragment() {

    }

    public static QuranFragment newInstance(DatabaseHelp databaseHelp, int id, String name) {
        QuranFragment fragment = new QuranFragment();
        fragment.id = id;
        fragment.databaseHelp = databaseHelp;
        fragment.name = name;
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_quran, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        contentlist = view.findViewById(R.id.content);
        contents = databaseHelp.getSuraContent(id);
        String lang = Global.getState(Objects.requireNonNull(getActivity()));
        ContentAdapter contentAdapter = new ContentAdapter(getContext(), contents, lang, databaseHelp);
        contentlist.setAdapter(contentAdapter);
    }

    public void filterValue(String value) {
        //listview.getFilter().filter(value);
        int size = contents.size();
        for (int i = 0; i < size; i++) {
            suraContent = contents.get(i);
            if (suraContent.getOromo().startsWith(value)) {
                contentlist.setSelection(i);
                break;
            }
        }
    }

    public void resetData(String oromo) {
        ContentAdapter contentAdapter = new ContentAdapter(getContext(), contents, oromo, databaseHelp);
        contentlist.setAdapter(contentAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Objects.requireNonNull(getActivity()).onBackPressed();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        toolbar.setTitle("Quraan");
    }
}
