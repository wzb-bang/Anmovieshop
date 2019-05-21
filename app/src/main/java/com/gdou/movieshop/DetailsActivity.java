package com.gdou.movieshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;

import com.gdou.movieshop.adapter.DetailsAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    //类成员
    private DetailsAdapter adapter;
    List<DetailsInfo> mList = new ArrayList<>();
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //获取RecyclerView的引用，并对其进行设置
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.card_list);
        mRecyclerView.setHasFixedSize(true);

        //RecyclerView 需要一个layoutManager，也就是布局管理器
        //布局管理器能确定RecyclerView内各个子视图（项目视图）的位置
        //并能决定何时重新使用对用户已不可见的项目视图
        //安卓为我们预先准备好了三种视图管理器：LinearLayoutManager、
        //GridLayoutManager、StaggeredGridLayoutManager（详见文档）

        //这里我们选择创建一个LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //为RecyclerView对象指定我们创建得到的layoutManager
        mRecyclerView.setLayoutManager(layoutManager);
        //初始化mList
        initInfo();
        //实例化MyAdapter并传入mList对象
        adapter = new DetailsAdapter(mList);
        //为RecyclerView对象mRecyclerView设置adapter
        mRecyclerView.setAdapter(adapter);
    }

    private void initInfo() {

//        测试数据
        DetailsInfo element1 = new DetailsInfo("17:20", "3号放映厅", "26.9元");
        mList.add(element1);
        DetailsInfo element2 = new DetailsInfo("17:20", "1号放映厅", "26.9元");
        mList.add(element2);
        DetailsInfo element3 = new DetailsInfo("18:20", "2号放映厅", "26.9元");
        mList.add(element3);
        DetailsInfo element4 = new DetailsInfo("19:20", "5号放映厅", "26.9元");
        mList.add(element4);
        DetailsInfo element5 = new DetailsInfo("20:20", "4号放映厅", "26.9元");
        mList.add(element5);
        DetailsInfo element6 = new DetailsInfo("21:20", "2号放映厅", "26.9元");
        mList.add(element6);

    }
}
