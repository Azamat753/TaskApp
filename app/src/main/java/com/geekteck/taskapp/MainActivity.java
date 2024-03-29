package com.geekteck.taskapp;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.geekteck.taskapp.onboard.OnBoardActivity;
import com.geekteck.taskapp.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.CountDownTimer;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    private int counter = 0;
    private AppBarConfiguration mAppBarConfiguration;
    private List<Task> list;

    private TextView textName;
    private TextView textEmail;
    private String name;
    private String email;
    final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        name=preferences.getString("name","no name");
        email=preferences.getString("email","no email");
        list = App.getDatabase().taskDao().getAll();
        App.getDatabase().taskDao().getAllLive().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                list.clear();
                list.addAll(tasks);
            }


        });

        boolean isShown = preferences.getBoolean("isShown", false);

        if (!isShown) {
            startActivity(new Intent(this, OnBoardActivity.class));
            finish();
            return;
        }
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, PhoneActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, FormActivity.class), 1);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ProfileActivity.class),200);
            }

        });
        textName = header.findViewById(R.id.name_textView);
        textEmail = header.findViewById(R.id.email_textView);
        textName.setText(name);
        textEmail.setText(email);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort:
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                assert fragment != null;
                ((HomeFragment) fragment.getChildFragmentManager().getFragments().get(0)).sortList();
            case R.id.clear:
                App.getDatabase().taskDao().deleteall(list);
            case R.id.signOut:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Вы хотите выйти?")
                        .setMessage("Выйти из приложения").setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                }).show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @AfterPermissionGranted(101)
    private void initFile() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            File folder = new File(Environment.getExternalStorageDirectory(), "TaskApp");
            folder.mkdirs();
            File file = new File(folder, "note.txt");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            File imageFile = new File(folder, "image.png");
            File imageFile2 = new File(folder, "image2.png");
            File imageFile3 = new File(folder, "image3.png");
            File imageFile4 = new File(folder, "image4.png");
            File imageFile5 = new File(folder, "image5.png");
            downloadFile(imageFile, imageFile2, imageFile3, imageFile4, imageFile5);
        } else {
            EasyPermissions.requestPermissions(this, "Разрешить?", 101,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void downloadFile(final File imageFile5, final File imageFile2, final File imageFile3, final File imageFile4, final File imageFile) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://square.github.io/picasso/static/sample.png");
                    FileUtils.copyURLToFile(url, imageFile);

                    URL url2 = new URL("https://s.hi-news.ru/wp-content/uploads/2016/04/p03phwdt-750x300.jpg");
                    FileUtils.copyURLToFile(url2, imageFile2);

                    URL url3 = new URL("https://s.hi-news.ru/wp-content/uploads/2019/10/tirannosaur_image_one-750x422.jpg");
                    FileUtils.copyURLToFile(url3, imageFile3);

                    URL url4 = new URL("https://cdn-st2.rtr-vesti.ru/vh/pictures/hd/200/691/3.jpg");
                    FileUtils.copyURLToFile(url4, imageFile4);

                    URL url5 = new URL("https://ichef.bbci.co.uk/news/ws/410/amz/worldservice/live/assets/images/2015/07/28/150728212130__84509634_trex-nhm-976.jpg");
                    FileUtils.copyURLToFile(url5, imageFile5);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void onDownload(View view) {
        initFile();
        progressBar = findViewById(R.id.gal_pb);
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                counter += 2;
                progressBar.setProgress(counter);
                if (counter == 100) {
                    timer.cancel();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        };
        timer.schedule(timerTask, 0, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     if (resultCode == RESULT_OK && requestCode == 200 && data != null){
//         textEmail=findViewById(R.id.email_textView);
//         textName=findViewById(R.id.name_textView);

         textName.setText(data.getStringExtra("name"));
         textEmail.setText(data.getStringExtra("email"));
     }
}

//
//textName.setText(editName.getText());
//textEmail.setText(editEmail.getText());


}
