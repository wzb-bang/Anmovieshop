package com.gdou.movieshop.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdou.movieshop.MovieInfo;
import com.gdou.movieshop.R;

import java.net.URI;
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

            holder.vName.setText(ci.getNamePrefix() + ci.getMovie_name());
            holder.vScore.setText(ci.getScorePrefix() +ci.getMovie_score());
            holder.vActor.setText(ci.getActorPrefix() + ci.getActor());
            holder.vImage.setImageDrawable(ci.getImage());
//            holder.vButton.setId(ci.getBtIdPrefix()+ci.getBt_id());
        }

        //此方法返回列表项的数目
        @Override
        public int getItemCount() {
            return MovieInfoList.size();
        }
    class ContactViewHolder extends RecyclerView.ViewHolder {
        //create the viewHolder class
        protected TextView vName;
        protected TextView vScore;
        protected TextView vActor;
        protected ImageView vImage;
//        protected Button vButton;

        public ContactViewHolder(View itemView) {
            super(itemView);
            vName = itemView.findViewById(R.id.movie_name);
            vScore = itemView.findViewById(R.id.movie_score);
            vActor = itemView.findViewById(R.id.actor);
            vImage=itemView.findViewById(R.id.card_movie_img1);
//            vButton=itemView.findViewById(R.id.btn_buy1);
        }
    }
}
