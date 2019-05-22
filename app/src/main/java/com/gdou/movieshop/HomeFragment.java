package com.gdou.movieshop;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import utils.BitmapCache;

/**
 * 主页面
 */
public class HomeFragment extends Fragment {
    //Button
    TextView login;
    EditText findMovie;
    ImageView iv_find;
    ImageView iv_AD;
    ImageView iv_hot1;
    ImageView iv_hot2;
    ImageView iv_hot3;
    Button btn_buy1;
    Button btn_buy2;
    Button btn_buy3;
    TextView tv_all;
    TextView tv_hotMovie;
    TextView hot_text1;
    TextView hot_text2;
    TextView hot_text3;
    String AD_id,hot1_id,hot2_id,hot3_id;
    HashMap<String, String> urlMap;
    HomeFragment fragment;
    SharedPreferences sharedPreferences;

    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获取sharedPreferences对象
        sharedPreferences = getActivity().getSharedPreferences("Login", Activity.MODE_PRIVATE);

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
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        //初始化组件
        login = getView().findViewById(R.id.tv_login);
        findMovie = getView().findViewById(R.id.findMovie);
        iv_find = getView().findViewById(R.id.iv_find);
        iv_AD = getView().findViewById(R.id.iv_AD);
        iv_hot1 = getView().findViewById(R.id.iv_hot1);
        iv_hot2 = getView().findViewById(R.id.iv_hot2);
        iv_hot3 = getView().findViewById(R.id.iv_hot3);
        btn_buy1 = getView().findViewById(R.id.btn_buy1);
        btn_buy2 = getView().findViewById(R.id.btn_buy2);
        btn_buy3 = getView().findViewById(R.id.btn_buy3);
        tv_all=getView().findViewById(R.id.tv_all);
        tv_hotMovie = getView().findViewById(R.id.tv_hotMovie);
        hot_text1 = getView().findViewById(R.id.hot_text1);
        hot_text2 = getView().findViewById(R.id.hot_text2);
        hot_text3 = getView().findViewById(R.id.hot_text3);


        String user_name = sharedPreferences.getString("user_name", "登录");
        login.setText(user_name);

        //-------------Volley链接,向后台获取主界面信息------------
        //创建请求队列
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        //创建请求
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "http://203.195.219.146:8080/movieshop/User/LoadHomeView.action", null,
                new Response.Listener<JSONObject>() {       //volley监听器
                    @Override
                    public void onResponse(JSONObject response) {  //onResponse获取到服务器响应的值
                        try {
                            //登录成功
                            if (response.get("status").equals(200)) {
                                urlMap = new HashMap<>();

                                urlMap.put("iv_AD",  response.getString("Image_url0"));
                                urlMap.put("iv_hot1",  response.getString("Image_url1"));
                                urlMap.put("iv_hot2",  response.getString("Image_url2"));
                                urlMap.put("iv_hot3",  response.getString("Image_url3"));
                                hot_text1.setText( response.getString("Movie_name1"));
                                hot_text2.setText( response.getString("Movie_name2"));
                                hot_text3.setText( response.getString("Movie_name3"));
                                AD_id=response.getString("Movie_id0");
                                hot1_id=response.getString("Movie_id1");
                                hot2_id=response.getString("Movie_id2");
                                hot3_id=response.getString("Movie_id3");

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


        //使用图片缓存工具类
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        if (urlMap != null) {
            //加载图片
            String prex = "http://203.195.219.146:8080/movieshop/";
            String url0 = prex + urlMap.get("iv_AD");
            ImageLoader.ImageListener imageListener1 = ImageLoader.getImageListener(iv_AD, R.drawable.loading, R.drawable.loadfailure);
            imageLoader.get(url0, imageListener1);
            //加载图片
            String url1 = prex + urlMap.get("iv_hot1");
            ImageLoader.ImageListener imageListener2 = ImageLoader.getImageListener(iv_hot1, R.drawable.loading, R.drawable.loadfailure);
            imageLoader.get(url1, imageListener2);
            //加载图片
            String url2 = prex + urlMap.get("iv_hot2");
            ImageLoader.ImageListener imageListener3 = ImageLoader.getImageListener(iv_hot2, R.drawable.loading, R.drawable.loadfailure);
            imageLoader.get(url2, imageListener3);
            //加载图片
            String url3 = prex + urlMap.get("iv_hot3");
            ImageLoader.ImageListener imageListener4 = ImageLoader.getImageListener(iv_hot3, R.drawable.loading, R.drawable.loadfailure);
            imageLoader.get(url3, imageListener4);
        }


        //---------------绑定监听器----------------
        //登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 给登录按钮添加点击响应事件
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                //启动
                startActivity(intent);
            }
        });
        //-------------以下均是添加监听, 可做简化
        //搜索图标添加监听
        iv_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存搜索名
                String name = findMovie.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("findMovie", name);
                editor.apply();
                //跳转至电影列表
                MainActivity activity = (MainActivity) getActivity();
                activity.setBottomBarSelection(1);
            }
        });

        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至电影列表
                MainActivity activity = (MainActivity) getActivity();
                activity.setBottomBarSelection(1);
            }
        });

        iv_AD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至电影列表
                Intent intent =new Intent(getActivity(),DetailsActivity.class);
                intent.putExtra("Movie_id",AD_id);
                startActivity(intent);
            }
        });

        btn_buy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至电影列表
                Intent intent =new Intent(getActivity(),DetailsActivity.class);
                intent.putExtra("Movie_id",hot1_id);
                startActivity(intent);
            }
        });
        btn_buy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至电影列表
                Intent intent =new Intent(getActivity(),DetailsActivity.class);
                intent.putExtra("Movie_id",hot2_id);
                startActivity(intent);
            }
        });
        btn_buy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至电影列表
                Intent intent =new Intent(getActivity(),DetailsActivity.class);
                intent.putExtra("Movie_id",hot3_id);
                startActivity(intent);
            }
        });
    }


}

