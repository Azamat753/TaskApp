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
import androidx.recyclerview.widget.GridLayoutManager;
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

public class SlideshowFragment extends Fragment {

    private List<String> urls;
    private SlideshowViewModel slideshowViewModel;
    ImageAdapter imageAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);



        urls = new ArrayList<>();
        urls.add("https://s.hi-news.ru/wp-content/uploads/2016/04/p03phwdt-750x300.jpg");
        urls.add("https://s.hi-news.ru/wp-content/uploads/2019/10/tirannosaur_image_one-750x422.jpg/s.hi-news.ru/wp-content/uploads/2016/04/p03phwdt-750x300.jpg");
        urls = new ArrayList<>();
        RecyclerView recView = root.findViewById(R.id.slideshow_recyclerView);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        recView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        imageAdapter = new ImageAdapter(urls);
        recView.setAdapter(imageAdapter);

        return root;
    }




}