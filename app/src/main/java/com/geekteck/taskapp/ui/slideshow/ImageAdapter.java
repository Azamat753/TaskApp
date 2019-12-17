package com.geekteck.taskapp.ui.slideshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekteck.taskapp.R;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Holder> {
    private ArrayList imagelist = new ArrayList();

    public ImageAdapter(ArrayList imageList) {
    }
    @NonNull
    @Override
    public ImageAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slideshow_holder, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.Holder holder, int position) {
        holder.bind(imagelist.get(position));
    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_hd);
        }

        public void bind(Object o) {


        }
    }


}





