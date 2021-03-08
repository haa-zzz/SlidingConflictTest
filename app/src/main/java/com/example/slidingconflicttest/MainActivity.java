package com.example.slidingconflicttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HorizontalEx horizontalEx;
    private HorizontalEx2 horizontalEx2;
    private ViewGroup lastViewGroup = null;
    private boolean isClick_1 = false;
    private boolean isClick_2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        AppCompatButton button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            if(isClick_1){
                setVisibility(horizontalEx);
            }else{
                isClick_1 = true;
                initHorizontalEx1();
            }

        });

        AppCompatButton button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            if(isClick_2){
                setVisibility(horizontalEx2);
            }else{
                isClick_2 = true;
                initHorizontalEx2();
            }
        });

        AppCompatButton button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> {

        });

        AppCompatButton button4 = findViewById(R.id.button4);
        button4.setOnClickListener(v -> {

        });

    }

    private void initHorizontalEx1() {

        horizontalEx = findViewById(R.id.HorizontalEx);
        setVisibility(horizontalEx);
        ListView listView1 = getListView(1);
        ListView listView2 = getListView(2);
        ListView listView3 = getListView(3);
        ViewGroup.LayoutParams params
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        horizontalEx.addView(listView1,params);
        horizontalEx.addView(listView2,params);
        horizontalEx.addView(listView3,params);
        lastViewGroup = horizontalEx;
    }
    private void initHorizontalEx2() {

        horizontalEx2 = findViewById(R.id.HorizontalEx2);
        setVisibility(horizontalEx2);
        ListViewEx listView1 =  getListViewEx(1);
        ListViewEx listView2 =  getListViewEx(2);
        ListViewEx listView3 =  getListViewEx(3);
        ViewGroup.LayoutParams params
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        horizontalEx2.addView(listView1,params);
        horizontalEx2.addView(listView2,params);
        horizontalEx2.addView(listView3,params);
        lastViewGroup = horizontalEx2;
    }

    private void setVisibility(ViewGroup nowViewGroup) {

        if(lastViewGroup!=null ){
            lastViewGroup.setVisibility(View.GONE);
        }
        nowViewGroup.setVisibility(View.VISIBLE);

    }

    private ListView getListView(int index) {

        List<String> list = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            list.add("列表"+index+"__"+i);
        }
        ListView listView = new ListView(getApplicationContext());
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter1);
        return listView;
    }
    private ListViewEx getListViewEx(int index) {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            list.add("列表"+index+"__"+i);
        }
        ListViewEx listViewEx = new ListViewEx(getApplicationContext());
        listViewEx.setmHorizontalEx2(horizontalEx2);
        listViewEx.setBackgroundColor(Color.BLUE);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, list);
        listViewEx.setAdapter(adapter);
        return listViewEx;
    }

}