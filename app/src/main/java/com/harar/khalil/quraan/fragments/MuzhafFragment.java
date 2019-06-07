package com.harar.khalil.quraan.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.harar.khalil.quraan.R;
import com.harar.khalil.quraan.adapters.QuranAdapter;
import com.harar.khalil.quraan.models.Quranim;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class MuzhafFragment extends Fragment {

    private StorageReference mStorageRef;
    private ProgressBar progressBar;
    private TextView textView;
    private int position;

    public MuzhafFragment() {

    }

    public static MuzhafFragment newInstance(int position) {
        MuzhafFragment muzhafFragment = new MuzhafFragment();
        muzhafFragment.position = position;
        return muzhafFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_muzhaf, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressBar5);
        textView = view.findViewById(R.id.textV);
        RecyclerView mrecyclerView = view.findViewById(R.id.mrecycler);
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://quraan-5d99a.appspot.com");

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        mrecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrecyclerView.getContext(),
                layoutManager.getOrientation());
        mrecyclerView.addItemDecoration(dividerItemDecoration);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mrecyclerView);

        checkDownload();
        ArrayList<Quranim> quran = getdata();

        Collections.sort(quran, new Comparator<Quranim>() {
            @Override
            public int compare(Quranim quranim, Quranim t1) {
                return quranim.getName().compareTo(t1.getName());
            }
        });

        mrecyclerView.setAdapter(new QuranAdapter(getActivity(), quran));
        mrecyclerView.scrollToPosition(position - 1);
        mrecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void checkDownload() {
        final File rootPath = new File(Environment.getExternalStorageDirectory(), "quranFile");
        String root_sd = Environment.getExternalStorageDirectory().toString();
        final File downloadsfolder = new File(root_sd + "/quranFile");
        if (!rootPath.exists()) {
            rootPath.mkdirs();
            final int[] yy = {1};
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Confirm dialog demo !");
            builder.setMessage("Download file required. Do you really want to proceed ?");
            builder.setCancelable(false);
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    int k = 1;
                    while (k <= 604) {
                        @SuppressLint("DefaultLocale") String str = String.format("%03d", k);
                        String page = "page" + str;
                        StorageReference riversRef = mStorageRef.child("quranFile/" + page + ".png");
                        File file = new File(rootPath, page + ".png");

                        riversRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                File[] files = downloadsfolder.listFiles();
                                yy[0] = files.length;
                                textView.setText("Downloading..." + (100 * yy[0]) / 604 + "%");
                                if (yy[0] >= 604) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    textView.setVisibility(View.INVISIBLE);

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();

                            }
                        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressBar.setProgress((int) progress);
                            }
                        });
                        k++;
                    }

                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    progressBar.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                }
            });
            builder.show();

        } else {
            final int[] yy = {1};
            File[] files = downloadsfolder.listFiles();
            final int xx = files.length;
            if (xx < 604) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("dialog !");
                builder.setMessage("Some file required to download. Do you really want to proceed ?");
                builder.setCancelable(false);
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int k = 1;
                        while (k <= 604) {
                            @SuppressLint("DefaultLocale") String str = String.format("%03d", k);
                            String page = "page" + str;
                            File chec = new File(downloadsfolder, page + ".png");
                            if (!chec.exists()) {
                                StorageReference riversRef = mStorageRef.child("quranFile/" + page + ".png");
                                File file = new File(rootPath, page + ".png");

                                riversRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        File[] files = downloadsfolder.listFiles();
                                        yy[0] = files.length;
                                        textView.setText("Downloading..." + (100 * yy[0]) / 604 + "%");
                                        if (yy[0] >= 604) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            textView.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                        progressBar.setProgress((int) progress);
                                    }
                                });
                            }
                            k++;

                        }
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressBar.setVisibility(View.INVISIBLE);
                        textView.setVisibility(View.INVISIBLE);
                    }
                });
                builder.show();
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private ArrayList<Quranim> getdata() {
        ArrayList<Quranim> quranims = new ArrayList<>();
        String root_sd = Environment.getExternalStorageDirectory().toString();
        File downloadsfolder = new File(root_sd + "/quranFile");
        Quranim quranim;
        if (downloadsfolder.exists()) {
            File[] files = downloadsfolder.listFiles();
            for (File file : files) {
                quranim = new Quranim();
                quranim.setName(file.getName());
                quranim.setUrl(Uri.fromFile(file));
                quranims.add(quranim);
            }
        }
        return quranims;
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

