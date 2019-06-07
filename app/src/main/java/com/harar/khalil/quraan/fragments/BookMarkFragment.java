package com.harar.khalil.quraan.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.harar.khalil.quraan.R;
import com.harar.khalil.quraan.adapters.BookMarkAdapter;
import com.harar.khalil.quraan.databases.DatabaseHelp;
import com.harar.khalil.quraan.globals.Global;
import com.harar.khalil.quraan.models.BookMark;

import java.util.List;
import java.util.Objects;

public class BookMarkFragment extends Fragment {


    private DatabaseHelp databaseHelp;
    private ListView bookmarklist;
    private List<BookMark> bookMarks;

    public BookMarkFragment() {
        // Required empty public constructor
    }

    public static BookMarkFragment newInstance(DatabaseHelp databaseHelp) {
        BookMarkFragment fragment = new BookMarkFragment();
        fragment.databaseHelp = databaseHelp;
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_book_mark, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookmarklist = view.findViewById(R.id.bookmarks);
        String lang = Global.getState(Objects.requireNonNull(getActivity()));
        if (lang.equals(String.valueOf(R.id.oromo))) {
            bookMarks = databaseHelp.getBookMarks("Oromic");
        } else if (lang.equals(String.valueOf(R.id.amharic))) {
            bookMarks = databaseHelp.getBookMarks("Amharic");
        } else if (lang.equals(String.valueOf(R.id.english))) {
            bookMarks = databaseHelp.getBookMarks("English");
        }

        BookMarkAdapter bookMarkAdapter = new BookMarkAdapter(bookMarks, getContext(), databaseHelp);
        bookmarklist.setAdapter(bookMarkAdapter);
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

}
