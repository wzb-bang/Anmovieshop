package com.gdou.movieshop;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_userid;
    private EditText et_psd;
    private ImageView iv_clear;
    private TextView register;
    private Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        ImageView iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);
        et_userid = (EditText) findViewById(R.id.et_userid);
        et_psd = (EditText) findViewById(R.id.et_psd);

        register=findViewById(R.id.register);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(this);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);

        et_userid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(et_userid.getText().toString().trim())) {
                    iv_clear.setVisibility(View.VISIBLE);
                } else {
                    iv_clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_clear:
                et_userid.setText("");
                et_psd.setText("");
                break;
            case R.id.register:
                Intent intent=new Intent(LoginActivity.this,registerActivity.class);
                startActivity(intent);
            case R.id.bt_login:
                /*
                 StringBuilder stringBuilder = new StringBuilder();
                BufferedReader buffer = null;
                HttpURLConnection connPOST = null;
                String str="http://192.168.1.104:8080/example6_war_exploded/LoginServlet";
                String param = "username=" + et_userid + "&" + "password=" + et_psd; //设置参数
                try{
                    URL url = new URL(str);
                    connPOST = (HttpURLConnection) url.openConnection();
                    connPOST.setConnectTimeout(5000);
                    connPOST.setRequestMethod("POST");
                    connPOST.setUseCaches(false);

                    // 发送POST请求必须设置如下两行
                    connPOST.setDoOutput(true);
                    connPOST.setDoInput(true);

                    //----------发送数据--------
                    PrintWriter printWriter = new PrintWriter(connPOST.getOutputStream());
                    printWriter.write(param); // 发送请求参数
                    printWriter.flush();
                    stringBuilder.setLength(0);
                    //----------接收数据--------
                    buffer = new BufferedReader(new InputStreamReader(connPOST.getInputStream()));
                    for (String s = buffer.readLine(); s != null; s = buffer.readLine()) {
                        stringBuilder.append(s);
                    }
                    Toast.makeText(getApplicationContext(),stringBuilder,Toast.LENGTH_LONG).show();
                    buffer.close();

                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "response err....." + e.toString(), Toast.LENGTH_LONG).show();
                }

                 */

                //-------------Volley链接------------
                //创建请求队列
                RequestQueue mQueue = Volley.newRequestQueue(LoginActivity.this);
                //创建请求
                StringRequest stringRequest=new StringRequest(Request.Method.POST,
                        "http://192.168.1.101:8080/movieshop_war_exploded/loginServlet",
                        new Response.Listener<String>() {      //volley监听器
                            @Override
                            public void onResponse(String response) {  //onResponse获取到服务器响应的值

                            }
                        },
                        new Response.ErrorListener() {   //接受错误信息
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.e("TAG", volleyError.getMessage(), volleyError);
                            }
                        }){
                    //传输参数
                    @Override
                    protected Map<String,String> getParams()throws AuthFailureError{
                        Map<String,String>map=new HashMap<>();
                        map.put("user_name",et_userid.toString());
                        map.put("user_password",et_psd.toString());

                        return map;
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        try {
                            String jsonString = new String(response.data, "UTF-8");
                            return Response.success(jsonString,
                                    HttpHeaderParser.parseCacheHeaders(response));
                        } catch (UnsupportedEncodingException e) {
                            return Response.error(new ParseError(e));
                        } catch (Exception je) {
                            Log.e("TAG",je.toString());
                            return Response.error(new ParseError(je));
                        }
                    }
                };
                //将创建的请求添加到请求队列中
                mQueue.add(stringRequest);
                    finish();
                    break;

        }
    }

}
