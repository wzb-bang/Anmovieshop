package com.gdou.movieshop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdou.movieshop.DetailsInfo;
import com.gdou.movieshop.R;

import java.util.List;


public class DetailsAdapter extends RecyclerView.Adapter
        <DetailsAdapter.ContactViewHolder> { //MyAdapter类 开始

    //MyAdapter的成员变量contactInfoList, 这里被我们用作数据的来源
    private List<DetailsInfo> detailsInfoList;

    //MyAdapter的构造器
    public DetailsAdapter(List<DetailsInfo> detailsInfoList) {
        this.detailsInfoList = detailsInfoList;
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

        public ContactViewHolder(View itemView) {
            super(itemView);
            vTime = itemView.findViewById(R.id.time);
            vRoom = itemView.findViewById(R.id.room);
            vPrice = itemView.findViewById(R.id.price);
        }

    }
} //结束 类MyAdapter