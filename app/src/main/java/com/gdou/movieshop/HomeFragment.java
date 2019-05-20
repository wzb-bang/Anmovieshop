package com.gdou.movieshop;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class HomeFragment extends Fragment {
    //Button
    TextView textView;
    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        Bundle bundle = getArguments();

//        String agrs1 = bundle.getString("agrs1");
////        TextView tv = (TextView)view.findViewById(R.id.container);
////        tv.setText(agrs1);
        textView =view.findViewById(R.id.tv_login);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                // 给登录按钮添加点击响应事件
                 Intent intent =new Intent(getActivity(),LoginActivity.class);
                 //启动
                startActivity(intent);
                }
         });
        return view;
    }


}

