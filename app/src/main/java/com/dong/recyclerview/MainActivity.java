package com.dong.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private ArrayList<String> mDatas;
    private MyAdapter mAdapter;
    //123
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new MyAdapter(this));
        mAdapter.setData(mDatas);
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView tv = (TextView) view.findViewById(R.id.tv);
                Toast.makeText(MainActivity.this,tv.getText().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Log.i(TAG, "onItemLongClick: "+position+" "+view);
                Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for(int i='A';i<'z';i++){
            mDatas.add(""+(char)i);
        }
    }
}
