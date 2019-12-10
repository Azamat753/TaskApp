package com.geekteck.taskapp.onboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.geekteck.taskapp.MainActivity;
import com.geekteck.taskapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {

    public BoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        int pos = getArguments().getInt("pos");
        TextView textView = view.findViewById(R.id.textView);
        ImageView imageView = view.findViewById(R.id.imageView);
        Button button = view.findViewById(R.id.button_start);
        LinearLayout frg = view.findViewById(R.id.frg_board);

        switch (pos) {
            case 0:
                button.setVisibility(View.INVISIBLE);
                textView.setText("Привет");
                imageView.setImageResource(R.drawable.onboard_page1);
                frg.setBackgroundColor(getResources().getColor(R.color.colorRed));
                break;
            case 1:

                button.setVisibility(View.INVISIBLE);
                textView.setText("Как дела?");
                imageView.setImageResource(R.drawable.onboard_page2);
                frg.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                break;
            case 2:
                button.setVisibility(View.VISIBLE);
                textView.setText("Что делаешь?");
                imageView.setImageResource(R.drawable.onboard_page3);
                frg.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                break;
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
                preferences.edit().putBoolean("isShown", true).apply();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }
}
