package com.gdou.movieshop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdou.movieshop.MovieInfo;
import com.gdou.movieshop.R;

import java.util.List;

public class MoviesAdapter  extends RecyclerView.Adapter<MoviesAdapter.ContactViewHolder> {
        //MoviesAdapter类 开始
        //MoviesAdapter的成员变量MovieInfoList, 这里被我们用作数据的来源
        private List<MovieInfo> MovieInfoList;

        //MoviesAdapter的构造器
        public MoviesAdapter(List<MovieInfo> MovieInfoList) {
            this.MovieInfoList = MovieInfoList;
        }

        //重写3个抽象方法
//onCreateViewHolder()方法 返回我们自定义的 ContactViewHolder对象
        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.card_view, parent, false);
            return new ContactViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ContactViewHolder holder, int position) {
//MovieInfoList中包含的都是MovieInfo类的对象
//通过其get()方法可以获
            MovieInfo ci = MovieInfoList.get(position);
//将viewholder中hold住的各个view与数据源进行绑定(bind)

            holder.vName.setText(ci.getNamePrefix() + ci.getName());
            holder.vSurname.setText(ci.getSurname() +ci.getSurname());
            holder.vEmail.setText(ci.getEmail() + ci.getEmail());
            holder.vTitle.setText(ci.getEmail() + "" + ci.getName());
        }

        //此方法返回列表项的数目
        @Override
        public int getItemCount() {
            return MovieInfoList.size();
        }
    class ContactViewHolder extends RecyclerView.ViewHolder {
        //create the viewHolder class
        protected TextView vName;
        protected TextView vSurname;
        protected TextView vEmail;
        protected TextView vTitle;

        public ContactViewHolder(View itemView) {
            super(itemView);
            vName = itemView.findViewById(R.id.text_name);
            vSurname = itemView.findViewById(R.id.text_surname);
            vEmail = itemView.findViewById(R.id.text_email);
            vTitle = itemView.findViewById(R.id.title);
        }
    }
}
