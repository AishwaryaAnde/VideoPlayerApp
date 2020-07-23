package com.videoplayerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    VideoAdapter adapter;
    public static int REQUEST_PERMISSION = 1;
    File directory;
    boolean boolean_permission;
    public static ArrayList<File> fileArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);


        //For phone memory and SD card videos
        directory = new File("/mnt/");

        //For phone memory
        //directory = new File("/storage/");

        GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(manager);

        permissionForVideos();
    }

    private void permissionForVideos() {

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        } else {

            boolean_permission = true;
            getFile(directory);
            adapter = new VideoAdapter(getApplicationContext(), fileArrayList);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                boolean_permission = true;
                getFile(directory);
                adapter = new VideoAdapter(getApplicationContext(), fileArrayList);
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "Please Allow permission", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public ArrayList<File> getFile(File directory) {
        File listFile[] = directory.listFiles();
        if (listFile != null && listFile.length > 0)
        {
            for (int i=0;i<listFile.length;i++)
            {
                if (listFile[i] .isDirectory())
                {
                    getFile(listFile[i]);
                }
                else
                {
                    boolean_permission = false;
                    if (listFile[i].getName().endsWith(".mp4"))
                    {
                        for (int j=0;j<fileArrayList.size();j++)
                        {
                            if (fileArrayList.get(j).getName().equals(listFile[i].getName()))
                            {
                                boolean_permission = true;
                            }
                            else {}
                        }
                        if (boolean_permission)
                        {
                            boolean_permission = false;
                        }
                        else
                        {
                            fileArrayList.add(listFile[i]);
                        }
                    }
                }
            }
        }
return fileArrayList;
    }
}

