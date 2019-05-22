package com.gdou.movieshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gdou.movieshop.adapter.DetailsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 电影信息页面，包括电影、场次、票价等信息
 * 及购票
 */
public class DetailsActivity extends AppCompatActivity {
    //类成员
    private DetailsAdapter adapter;
    private ImageView details_img;
    private TextView details_name;
    private TextView details_actor;
    private ExpandableTextView details_description;
    private Intent intent;
    private Context context;
    List<DetailsInfo> mList = new ArrayList<>();

    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        details_img = findViewById(R.id.details_img);
        details_name=findViewById(R.id.details_name);
        details_actor=findViewById(R.id.details_actor);
        details_description=findViewById(R.id.details_description);
        context=getApplicationContext();

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
        getNetInfo();
        //实例化MyAdapter并传入mList对象
        adapter = new DetailsAdapter(DetailsActivity.this,mList);
        //为RecyclerView对象mRecyclerView设置adapter
        mRecyclerView.setAdapter(adapter);

    }
    public void getNetInfo(){
        //-------------Volley链接,向后台获取电影详情信息------------
        //创建请求队列
        RequestQueue mQueue = Volley.newRequestQueue(DetailsActivity.this);
        //创建请求
        //设置参数
        Map<String,Object> params=new HashMap<>();
        intent=getIntent();
        String Movie_id=intent.getStringExtra("Movie_id");
        params.put("Movie_id",Movie_id);
        System.out.println("455434哈哈哈"+Movie_id);
        //params.put("status",sharedPreferences.getInt("status",300));
        JSONObject paramsJsonObject=new JSONObject(params);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "http://203.195.219.146:8080/movieshop/Movie/showMovieDetailByID.action", paramsJsonObject,
                new Response.Listener<JSONObject>() {       //volley监听器
                    @Override
                    public void onResponse(JSONObject response) {  //onResponse获取到服务器响应的值
                        try {
                            ExpandableTextView details_description = findViewById(R.id.details_description);
                            //登录成功
                            if (response.get("status").equals(200)) {
                                details_name.setText(response.getString("Movie_name"));
                                details_actor.setText(response.getString("Movie_actor"));
                                details_description.setText(response.getString("Movie_details_description"));
                                Glide.with(context).load("http://203.195.219.146:8080/movieshop/"+response.getString("Movie_image"))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .placeholder(R.drawable.loading)
                                        .into(details_img);
                                for(int i=0;i<(int)response.get("size");i++){
                                    mList.add(new DetailsInfo(response.getString("Movie_time"+i),response.getString("Movie_room"+i),
                                            response.getString("Movie_price"+i)));
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

    }
}
