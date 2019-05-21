package com.gdou.movieshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gdou.movieshop.adapter.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

public class findmovie extends AppCompatActivity {
    private MoviesAdapter adapter;
    List<MovieInfo> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findmovie);


        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.card_list);
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
        adapter = new MoviesAdapter(mList);
        //为RecyclerView对象mRecyclerView设置adapter
        mRecyclerView.setAdapter(adapter);
    }
    private void initInfo() {

//        测试数据
        MovieInfo element1 = new MovieInfo("大侦探皮卡丘", "6.7", "尼科尔·帕尔曼 / 亚历克斯·赫什 / 田尻智",getResources().getDrawable(R.drawable.hot1));
        mList.add(element1);
        MovieInfo element2 = new MovieInfo("一条狗的使命2", "6.9", " 丹尼斯·奎德 / 凯瑟琳·普雷斯科特 / 刘宪华",getResources().getDrawable(R.drawable.hot2));
        mList.add(element2);
        MovieInfo element3 = new MovieInfo("复仇者联盟4：终局之战", "8.6", "小罗伯特·唐尼 / 克里斯·埃文斯 ",getResources().getDrawable(R.drawable.hot3));
        mList.add(element3);
        MovieInfo element4 = new MovieInfo("何以为家", "9.0", " 赞恩·阿尔·拉菲亚 / 约丹诺斯·希费罗",getResources().getDrawable(R.drawable.hot4));
        mList.add(element4);
        MovieInfo element5 = new MovieInfo("一个母亲的复仇", "6.7", " 希里黛玉 / 阿克夏耶·坎纳 / 萨佳·阿里",getResources().getDrawable(R.drawable.hot5));
        mList.add(element5);
        MovieInfo element6 = new MovieInfo("双生", "3.6", "刘昊然 / 陈都灵 / 赵芮",getResources().getDrawable(R.drawable.hot6));
        mList.add(element6);
//        MovieInfo element7 = new MovieInfo("小明", "6.3", "feverdg@icloud.com");
//        mList.add(element7);

    }
}
