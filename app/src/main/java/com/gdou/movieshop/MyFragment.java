package com.gdou.movieshop;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class MyFragment extends Fragment {
    public static MyFragment newInstance(String param1) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);

        return fragment;
    }

    public MyFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);

        // ArrayAdapter的参数1是一个context，代表要生成ListView的上下文
        // 参数2要传入的是一个布局文件id值，这里使用google预置的布局文件
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);

        ListView lvDemo = (ListView)view.findViewById(R.id.listview);
        lvDemo.setAdapter(adapter);

        // 添加数据
        for(int i = 0; i < 1; i++){
            adapter.add("退出登陆 " + String.valueOf(i));
        }

        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");
        TextView tv = (TextView)view.findViewById(R.id.container);
        tv.setText(agrs1);

        return view;
    }
}

