package com.gdou.movieshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MyFragment extends Fragment {
    private SharedPreferences sharedPreferences;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        //创建sharedPreferences对象,并保存用户登录状态、用户名、用户id
        sharedPreferences =
                getActivity().getSharedPreferences("Login", Activity.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        String user_name = sharedPreferences.getString("user_name", "登录");

        TextView name = (TextView)view.findViewById(R.id.my_name);
        name.setText(user_name);
        System.out.println("out出来");
        Button out = (Button)view.findViewById(R.id.logout);
        out.setOnClickListener(new outClick());

        return view;
    }


    private class outClick implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            String a = sharedPreferences.getString("user_name", "登录");
            if (a=="登录"){
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("退出错误");
                dialog.setMessage("没有正在使用的用户!");
                dialog.setPositiveButton("确定", new okClick());
                dialog.create();
                dialog.show();
            }else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("退出登录");
                dialog.setMessage("退出当前用户!");
                dialog.setPositiveButton("确定", new exitClick());
                dialog.create();
                dialog.show();
            }
        }
    }

    private class exitClick implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("user_name");
            editor.remove("user_id");
            editor.remove("status");
            editor.commit();
            dialog.cancel();
        }
    }
}

