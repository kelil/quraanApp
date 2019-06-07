package com.harar.khalil.quraan.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.harar.khalil.quraan.R;
import com.harar.khalil.quraan.databases.DatabaseHelp;
import com.harar.khalil.quraan.fragments.BookMarkFragment;
import com.harar.khalil.quraan.fragments.JuuzFragment;
import com.harar.khalil.quraan.fragments.MuzhafFragment;
import com.harar.khalil.quraan.fragments.QuranFragment;
import com.harar.khalil.quraan.fragments.SuraFragment;
import com.harar.khalil.quraan.globals.Global;
import com.harar.khalil.quraan.interfaces.FragmentListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private QuranFragment quranFragment;
    private SuraFragment suraFragment;
    private BookMarkFragment bookMarkFragment;
    private MuzhafFragment muzhafFragment;
    private JuuzFragment juuzFragment;
    private static final int REQ_PERMISSION = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        DatabaseHelp databaseHelp = new DatabaseHelp(this);
        File database = getApplicationContext().getDatabasePath(DatabaseHelp.DBName);
        if (!database.exists()) {
            databaseHelp.getReadableDatabase();
            copyDatabase(this);
        }


        suraFragment = SuraFragment.newInstance(databaseHelp);
        juuzFragment = JuuzFragment.newInstance(databaseHelp);
        bookMarkFragment = BookMarkFragment.newInstance(databaseHelp);
        gotoFragment(suraFragment, true);
        suraFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void OnclickItem(DatabaseHelp databaseHelp, int id, String oromo, int position, String lang) {
                if (!String.valueOf(lang).equals(String.valueOf(R.id.muzhaf))) {
                    quranFragment = QuranFragment.newInstance(databaseHelp, id, oromo);
                    gotoFragment(quranFragment, false);
                } else {
                    muzhafFragment = MuzhafFragment.newInstance(position);
                    gotoFragment(muzhafFragment, false);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            requestPermission();
        }


    }

    public void gotoFragment(Fragment fragment, boolean b) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.executePendingTransactions();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        if (!b)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    gotoFragment(suraFragment, false);
                    return true;
                case R.id.navigation_dashboard:
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    gotoFragment(juuzFragment, false);
                case R.id.navigation_notifications:
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    gotoFragment(bookMarkFragment, false);
                    return true;
            }
            return false;
        }
    };

    private void copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DatabaseHelp.DBName);
            String outfilename = DatabaseHelp.DBLocation + DatabaseHelp.DBName;
            OutputStream outputStream = new FileOutputStream(outfilename);
            byte[] buff = new byte[1024];
            int leng;
            while ((leng = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, leng);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void requestPermission() {
        int reqP = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int reqPw = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int inter = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (reqP != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PERMISSION);
        }
        if (reqPw != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_PERMISSION);
        }
        if (inter != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQ_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        String id = Global.getState(this);
        if (id != null)
            onOptionsItemSelected(menu.findItem(Integer.valueOf(id)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.oromo) {
            String prev = Global.getState(this);
            Global.saveState(this, "lang", String.valueOf(id));
            if (quranFragment != null) {
                quranFragment.resetData(String.valueOf(id));
            }
            if (String.valueOf(prev).equals(String.valueOf(R.id.muzhaf))) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                gotoFragment(suraFragment, false);
            }
        } else if (id == R.id.english) {
            String prev = Global.getState(this);
            Global.saveState(this, "lang", String.valueOf(id));
            if (quranFragment != null) {
                quranFragment.resetData(String.valueOf(id));
            }
            if (String.valueOf(prev).equals(String.valueOf(R.id.muzhaf))) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                gotoFragment(suraFragment, false);
            }
        } else if (id == R.id.amharic) {
            String prev = Global.getState(this);
            Global.saveState(this, "lang", String.valueOf(id));
            if (quranFragment != null) {
                quranFragment.resetData(String.valueOf(id));
            }
            if (String.valueOf(prev).equals(String.valueOf(R.id.muzhaf))) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                gotoFragment(suraFragment, false);
            }
        } else if (id == R.id.muzhaf) {
            Global.saveState(this, "lang", String.valueOf(id));
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            gotoFragment(suraFragment, false);

        } else if (id == R.id.exit) {
            finish();
        }

        if (item.isChecked()) item.setChecked(false);
        else item.setChecked(true);
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBackPressed() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        if (getSupportFragmentManager().getBackStackEntryCount() < 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
