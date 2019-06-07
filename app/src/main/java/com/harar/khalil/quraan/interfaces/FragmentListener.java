package com.harar.khalil.quraan.interfaces;


import com.harar.khalil.quraan.databases.DatabaseHelp;

public interface FragmentListener {
    void OnclickItem(DatabaseHelp databaseHelp, int id, String oromo, int position, String lang);
}
