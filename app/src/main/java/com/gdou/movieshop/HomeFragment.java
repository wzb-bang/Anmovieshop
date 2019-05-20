package com.gdou.movieshop;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {
    //Button
    TextView textView;
    EditText findMovie;
    ImageView iv_find;
    SharedPreferences sharedPreferences;
    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        //获取sharedPreferences对象
        sharedPreferences=getActivity().getSharedPreferences("Login", Activity.MODE_PRIVATE);

    }
    public HomeFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //intiView();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        Bundle bundle = getArguments();
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        initView();
    }
    private void initView() {

        textView =getView().findViewById(R.id.tv_login);
        findMovie=getView().findViewById(R.id.findMovie);
        iv_find=getView().findViewById(R.id.iv_find);

        String user_name=sharedPreferences.getString("user_name","登录");
        textView.setText(user_name);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 给登录按钮添加点击响应事件
                Intent intent =new Intent(getActivity(),LoginActivity.class);
                //启动
                startActivity(intent);
            }
        });

        //搜索图标添加监听
        iv_find.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }

}

