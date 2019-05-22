package com.gdou.movieshop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gdou.movieshop.DetailsInfo;
import com.gdou.movieshop.R;

import java.util.List;


public class DetailsAdapter extends RecyclerView.Adapter
        <DetailsAdapter.ContactViewHolder> { //MyAdapter类 开始

    //DetailsAdapter的成员变量detailsInfoList, 这里被我们用作数据的来源
    private List<DetailsInfo> detailsInfoList;
    private AdapterView.OnItemClickListener onItemClickListener;
    //上下文
    private Context context;
    //DetailsAdapter的构造器
    public DetailsAdapter(Context context,List<DetailsInfo> detailsInfoList) {
        this.detailsInfoList = detailsInfoList;
        this.context=context;
    }
    //重写3个抽象方法
    //onCreateViewHolder()方法 返回我们自定义的 ContactViewHolder对象
    @Override
    public ContactViewHolder onCreateViewHolder
    (ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.movies_details,parent,false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder
            (ContactViewHolder holder, int position) {

        //contactInfoList中包含的都是ContactInfo类的对象
        //通过其get()方法可以获得其中的对象
        DetailsInfo ci = detailsInfoList.get(position);

        //将viewholder中hold住的各个view与数据源进行绑定(bind)
        holder.vTime.setText(ci.getTime());
        holder.vRoom.setText(ci.getRoom());
        holder.vPrice.setText(ci.getPrice());

        int adapterPosition = holder.getAdapterPosition();
        if (onItemClickListener == null) {
            holder.vButton.setOnClickListener(new MyOnClickListener(position,detailsInfoList.get(adapterPosition)));
        }
    }


    /**
     * 设置点击事件
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private class MyOnClickListener implements View.OnClickListener {
        private int position;
        private DetailsInfo data;

        public MyOnClickListener(int position, DetailsInfo data) {
            this.position = position;
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            showNormalDialog();
        }

        private void showNormalDialog(){
            /* @setIcon 设置对话框图标
             * @setTitle 设置对话框标题
             * @setMessage 设置对话框消息提示
             * setXXX方法返回Dialog对象，因此可以链式设置属性
             */
            final AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(context);
            normalDialog.setTitle("确认购买");
            normalDialog.setMessage("是否确定购买?");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context,"你已成功购买", Toast.LENGTH_LONG).show();
                        }
                    });
            normalDialog.setNegativeButton("关闭",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //...To-do
                        }
                    });
            // 显示
            normalDialog.show();
        }
    }





    //此方法返回列表项的数目
    @Override
    public int getItemCount() {
        return detailsInfoList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        //create the viewHolder class

        protected TextView vTime;
        protected TextView vRoom;
        protected TextView vPrice;
        protected Button vButton;
        public ContactViewHolder(View itemView) {
            super(itemView);
            vTime = itemView.findViewById(R.id.time);
            vRoom = itemView.findViewById(R.id.room);
            vPrice = itemView.findViewById(R.id.price);
            vButton=itemView.findViewById(R.id.Movie_detail_buy);
        }

    }
} //结束 类MyAdapter