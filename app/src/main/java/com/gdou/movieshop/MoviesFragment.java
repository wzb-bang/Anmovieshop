package com.gdou.movieshop;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdou.movieshop.adapter.MoviesAdapter;


import java.util.ArrayList;
import java.util.List;


public class MoviesFragment extends Fragment {
    //类成员
    private MoviesAdapter adapter;
    List<MovieInfo> mList = new ArrayList<>();

    public static MoviesFragment newInstance(String param1) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MoviesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_fragment, container, false);
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");
        TextView tv = (TextView)view.findViewById(R.id.container);
        tv.setText(agrs1);


        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.card_list);
        mRecyclerView.setHasFixedSize(true);

        //RecyclerView 需要一个layoutManager，也就是布局管理器
        //布局管理器能确定RecyclerView内各个子视图（项目视图）的位置
        //并能决定何时重新使用对用户已不可见的项目视图
        //安卓为我们预先准备好了三种视图管理器：LinearLayoutManager、
        //GridLayoutManager、StaggeredGridLayoutManager（详见文档）

        //这里我们选择创建一个LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //为RecyclerView对象指定我们创建得到的layoutManager
        mRecyclerView.setLayoutManager(layoutManager);

        //初始化mList
        initInfo();
        //实例化MyAdapter并传入mList对象
        adapter = new MoviesAdapter(mList);
        //为RecyclerView对象mRecyclerView设置adapter
        mRecyclerView.setAdapter(adapter);
        return view;
    }
    private void initInfo() {

//        测试数据
        MovieInfo element1 = new MovieInfo("小明", "西门", "feverdg@icloud.com");
        mList.add(element1);
        MovieInfo element2 = new MovieInfo("小红", "南宫", "146793455@icloud.com");
        mList.add(element2);
        MovieInfo element3 = new MovieInfo("小九九", "欧阳", "17987453@icloud.com");
        mList.add(element3);
        MovieInfo element4 = new MovieInfo("小九九", "欧阳", "17987453@icloud.com");
        mList.add(element4);
        MovieInfo element5 = new MovieInfo("小九九", "欧阳", "17987453@icloud.com");
        mList.add(element5);
        MovieInfo element6 = new MovieInfo("小九九", "欧阳", "17987453@icloud.com");
        mList.add(element6);
        MovieInfo element7 = new MovieInfo("小明", "西门", "feverdg@icloud.com");
        mList.add(element7);
        MovieInfo element8 = new MovieInfo("小红", "南宫", "146793455@icloud.com");
        mList.add(element8);
        MovieInfo element9 = new MovieInfo("小九九", "欧阳", "17987453@icloud.com");
        mList.add(element9);
        MovieInfo element10 = new MovieInfo("小九九", "欧阳", "17987453@icloud.com");
        mList.add(element10);
        MovieInfo element11 = new MovieInfo("小九九", "欧阳", "17987453@icloud.com");
        mList.add(element11);
        MovieInfo element12 = new MovieInfo("小九九", "欧阳", "17987453@icloud.com");
        mList.add(element12);
    }
}

