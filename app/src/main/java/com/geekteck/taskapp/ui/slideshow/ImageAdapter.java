package com.geekteck.taskapp.ui.slideshow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.UrlQuerySanitizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.geekteck.taskapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Holder> {
    private List<String >list;
private Context context;
    public ImageAdapter(List<String > list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ImageAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slideshow_holder, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.Holder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_hd);
        }

        public void bind(String url) {
            Glide.with(context).load(url).into(imageView);
        }
    }


}





