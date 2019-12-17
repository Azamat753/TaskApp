package com.geekteck.taskapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;

import com.geekteck.taskapp.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT*FROM task")
    LiveData<List<Task>> getAllLive();

    @Query("SELECT*FROM task ORDER BY title ASC")
    List<Task> sort();

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);
@Delete
    void deleteall(List<Task> list);
}
