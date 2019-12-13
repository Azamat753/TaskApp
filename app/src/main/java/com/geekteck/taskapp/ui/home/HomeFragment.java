package com.geekteck.taskapp.ui.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;
import com.geekteck.taskapp.App;
import com.geekteck.taskapp.FormActivity;
import com.geekteck.taskapp.OnItemClickListener;
import com.geekteck.taskapp.R;
import com.geekteck.taskapp.Task;
import com.geekteck.taskapp.room.TaskDao;
import com.geekteck.taskapp.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private TaskAdapter adapter;

private Task task;


    private List<Task> list;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        list = new ArrayList<>();
        list = App.getDatabase().taskDao().getAll();
        App.getDatabase().taskDao().getAllLive().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                list.clear();
                list.addAll(tasks);
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new TaskAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                task=list.get(position);
               Intent intent = new Intent(getContext(),FormActivity.class);
               intent.putExtra("task",task);
               startActivityForResult(intent,1);

            }
            @Override
            public void onItemLongClick(final int position) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                dialog.setTitle("Вы хотите удалить?")
                        .setMessage("Удалить задачу")
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }
                        }).setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        App.getDatabase().taskDao().delete(list.get(position));
                    }
                }).show();
            }
        });
        return root;
    }


}
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
//            String title = data.getStringExtra("title");
//            String desc = data.getStringExtra("description");
//            list.add(0, new Task(title, desc));
//            list = App.getDatabase().taskDao().getAll();
//            adapter.notifyDataSetChanged();
//        }
//}

