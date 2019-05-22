package com.gdou.movieshop;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gdou.movieshop.adapter.MoviesAdapter;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.BitmapCache;


public class MoviesFragment extends Fragment {
    //类成员
    private MoviesAdapter adapter;
    List<MovieInfo> mList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    HashMap urlMap;


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
    public void onAttach(Context context) {
        super.onAttach(context);
        //获取sharedPreferences对象
        sharedPreferences = getActivity().getSharedPreferences("Login", Activity.MODE_PRIVATE);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_fragment, container, false);
//        Button card_btn_buy1 = getView().findViewById(R.id.card_btn_buy1);
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
        getNetInfo();
        //实例化MyAdapter并传入mList对象
        adapter = new MoviesAdapter(getActivity(),mList);
        //为RecyclerView对象mRecyclerView设置adapter
        mRecyclerView.setAdapter(adapter);

        return view;
    }


    public void getNetInfo(){
        //-------------Volley链接,向后台获取主界面信息------------
        //创建请求队列
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        //创建请求
        //设置参数
        Map<String,Object> params=new HashMap<>();

        params.put("findMovie",sharedPreferences.getString("findMovie",""));
        params.put("status",sharedPreferences.getInt("status",300));
        JSONObject paramsJsonObject=new JSONObject(params);
        if (sharedPreferences.contains("findMovie")){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.remove("findMovie");
            editor.apply();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "http://203.195.219.146:8080/movieshop/Movie/findMovieByName.action", paramsJsonObject,
                new Response.Listener<JSONObject>() {       //volley监听器
                    @Override
                    public void onResponse(JSONObject response) {  //onResponse获取到服务器响应的值
                        try {
                            //查询成功
                            if (response.get("status").equals(200)) {
                                urlMap = new HashMap<>();
                                for(int i=0;i<(int)response.get("size");i++){
                                    mList.add(new MovieInfo(response.getString("Movie_id"+i),response.getString("Movie_name"+i),
                                            response.getString("Movie_score"+i),
                                            response.getString("Actor"+i),response.getString("Image_url"+i)));
                                }
                            }
                            //找不到数据
                            if(response.get("status").equals(300)){
                                Toast.makeText(getActivity(),response.getString("msg"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {   //接受错误信息
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("TAG", volleyError.getMessage(), volleyError);
                    }
                }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(new String(response.data, "UTF-8"));
                    return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (Exception je) {
                    Log.e("TAG", je.toString());
                    return Response.error(new ParseError(je));
                }
            }
        };
        //将创建的请求添加到请求队列中
        mQueue.add(jsonObjectRequest);

    }
}

