package com.dong.recyclerview;

import android.app.Service;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private ArrayList<String> mDatas;
    private MyAdapter mAdapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new MyAdapter(this));

        itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

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

            @Override
            public void onItemLongDrag(RecyclerView.ViewHolder viewHolder, View view, int position) {
                Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                vibrator.vibrate(70);
                itemTouchHelper.startDrag(viewHolder);
            }


        });
    }

    ItemTouchHelper.Callback touchHelperCallback = new ItemTouchHelper.Callback() {

        //设置是否处理拖拽和滑动事件
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags;
            int swipeFlags;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                dragFlags = ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT;
                swipeFlags = 0;
            } else {
                dragFlags = ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN;
                swipeFlags = 0;
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        //拖拽过程不断回调此方法
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if(fromPosition<toPosition){
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mDatas,i,i+1);
                }
            }else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mDatas,i,i-1);
                }
            }
            recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(0);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }
    };

    private void initData() {
        mDatas = new ArrayList<>();
        for(int i='A';i<'z';i++){
            mDatas.add(""+(char)i);
        }
    }
}
