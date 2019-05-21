package com.gdou.movieshop;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
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
        adapter = new MoviesAdapter(mList);
        //为RecyclerView对象mRecyclerView设置adapter
        mRecyclerView.setAdapter(adapter);
        return view;
    }
    private void initInfo() {
//
////        测试数据
//        MovieInfo element1 = new MovieInfo("大侦探皮卡丘", "6.7", "尼科尔·帕尔曼 / 亚历克斯·赫什 / 田尻智",getResources().getDrawable(R.drawable.hot1));
//        mList.add(element1);
//        MovieInfo element2 = new MovieInfo("一条狗的使命2", "6.9", " 丹尼斯·奎德 / 凯瑟琳·普雷斯科特 / 刘宪华",getResources().getDrawable(R.drawable.hot2));
//        mList.add(element2);
//        MovieInfo element3 = new MovieInfo("复仇者联盟4：终局之战", "8.6", "小罗伯特·唐尼 / 克里斯·埃文斯 ",getResources().getDrawable(R.drawable.hot3));
//        mList.add(element3);
//        MovieInfo element4 = new MovieInfo("何以为家", "9.0", " 赞恩·阿尔·拉菲亚 / 约丹诺斯·希费罗",getResources().getDrawable(R.drawable.hot4));
//        mList.add(element4);
//        MovieInfo element5 = new MovieInfo("一个母亲的复仇", "6.7", " 希里黛玉 / 阿克夏耶·坎纳 / 萨佳·阿里",getResources().getDrawable(R.drawable.hot5));
//        mList.add(element5);
//        MovieInfo element6 = new MovieInfo("双生", "3.6", "刘昊然 / 陈都灵 / 赵芮",getResources().getDrawable(R.drawable.hot6));
//        mList.add(element6);
////        MovieInfo element7 = new MovieInfo("小明", "6.3", "feverdg@icloud.com");
////        mList.add(element7);

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
            sharedPreferences.edit().remove("findMovie");
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "http://192.168.1.103:8080/movieshop_war_exploded/Movie/findMovieByName.action", paramsJsonObject,
                new Response.Listener<JSONObject>() {       //volley监听器
                    @Override
                    public void onResponse(JSONObject response) {  //onResponse获取到服务器响应的值
                        try {
                            //登录成功
                            if (response.get("status").equals(200)) {
                                urlMap = new HashMap<>();
                                for(int i=0;i<(int)response.get("size");i++){
                                    mList.add(new MovieInfo(response.getString("Movie_name"+i),
                                            response.getString("Movie_score"+i),
                                            response.getString("Actor"+i)));
                                    urlMap.put("iv_url"+i, (String) response.get("Image_url")+i);
                                }
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


//        //使用图片缓存工具类
//        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
//        if (urlMap != null) {
//            //加载图片
//
//            String prex = "http://192.168.1.103:8080/movieshop_war_exploded/";
//
//            for (int i=0;i<mList.size();i++){
//                String url = prex + urlMap.get("iv_url"+i);
//                mList.get(i).setImageloader(new ImageLoader.getImageListener());
//
//                        ImageLoader.getImageListener(card_movie_img1,R.drawable.loading, R.drawable.loadfailure);
//                mList.get(i).getImageloader().
//            }
//
//            imageLoader.get(url0, imageListener1);
//            //加载图片
//            String url1 = prex + urlMap.get("iv_hot1");
//            ImageLoader.ImageListener imageListener2 = ImageLoader.getImageListener(iv_hot1, R.drawable.loading, R.drawable.loadfailure);
//            imageLoader.get(url1, imageListener2);
//            //加载图片
//            String url2 = prex + urlMap.get("iv_hot2");
//            ImageLoader.ImageListener imageListener3 = ImageLoader.getImageListener(iv_hot2, R.drawable.loading, R.drawable.loadfailure);
//            imageLoader.get(url2, imageListener3);
//            //加载图片
//            String url3 = prex + urlMap.get("iv_hot3");
//            ImageLoader.ImageListener imageListener4 = ImageLoader.getImageListener(iv_hot3, R.drawable.loading, R.drawable.loadfailure);
//            imageLoader.get(url3, imageListener4);
//        }
    }
}

