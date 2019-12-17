package com.geekteck.taskapp.ui.slideshow;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.geekteck.taskapp.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment implements View.OnClickListener {

    private List<String> urls;
    private SlideshowViewModel slideshowViewModel;
    private ProgressBar progressBar;

    ArrayList imageList = new ArrayList();
    ImageAdapter imageAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        root.findViewById(R.id.bt_dwn_slid).setOnClickListener(this);

        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        urls = new ArrayList<>();
        urls.add("https://s.hi-news.ru/wp-content/uploads/2016/04/p03phwdt-750x300.jpg");
        urls.add("https://s.hi-news.ru/wp-content/uploads/2019/10/tirannosaur_image_one-750x422.jpg/s.hi-news.ru/wp-content/uploads/2016/04/p03phwdt-750x300.jpg");

        imageList= new ArrayList();

        RecyclerView recView= root.findViewById(R.id.slideshow_recyclerView);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        recView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        imageAdapter = new ImageAdapter(imageList);
        recView.setAdapter(imageAdapter);

        return root;
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        File folder = new File(Environment.getExternalStorageDirectory(), "TaskApp/Images");
        folder.mkdirs();
        downloadFiles(folder);

    }

    private void downloadFiles(final File folder) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < urls.size(); i++) {
                        String url = urls.get(i);
                        String fileName = url.substring(url.lastIndexOf("/") + 1);
                        File file = new File(folder, fileName);
                        FileUtils.copyURLToFile(new URL(url), file);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
        thread.start();
    }
}