package com.gdou.movieshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gdou.movieshop.DetailsActivity;
import com.gdou.movieshop.LoginActivity;
import com.gdou.movieshop.MainActivity;
import com.gdou.movieshop.MovieInfo;
import com.gdou.movieshop.R;

import java.net.URI;
import java.util.List;

public class MoviesAdapter  extends RecyclerView.Adapter<MoviesAdapter.ContactViewHolder> {
        //MoviesAdapter类 开始
        //MoviesAdapter的成员变量MovieInfoList, 这里被我们用作数据的来源
        private List<MovieInfo> MovieInfoList;
        private AdapterView.OnItemClickListener onItemClickListener;
        private Context context;

        //MoviesAdapter的构造器
        public MoviesAdapter(Context context,List<MovieInfo> MovieInfoList) {
            this.context=context;
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
        public void onBindViewHolder(final ContactViewHolder holder, int position) {
            //MovieInfoList中包含的都是MovieInfo类的对象
            //通过其get()方法可以获
            MovieInfo ci = MovieInfoList.get(position);
            //将viewholder中hold住的各个view与数据源进行绑定(bind)

            holder.vName.setText(ci.getNamePrefix() + ci.getMovie_name());
            holder.vScore.setText(ci.getScorePrefix() +ci.getMovie_score());
            holder.vActor.setText(ci.getActorPrefix() + ci.getActor());

            Glide.with(context).load(ci.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.loading)
                    .into(holder.vImage);

            int adapterPosition = holder.getAdapterPosition();
            if (onItemClickListener == null) {
                holder.vButton.setOnClickListener(new MyOnClickListener(position,MovieInfoList.get(adapterPosition)));
            }

//            holder.vImage.setImageDrawable(ci.getImage());
//            holder.vButton.setId(ci.getBtIdPrefix()+ci.getBt_id());
        }


    /**
     * 设置点击事件
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private class MyOnClickListener implements View.OnClickListener {
        private int position;
        private MovieInfo data;

        public MyOnClickListener(int position, MovieInfo data) {
            this.position = position;
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,DetailsActivity.class);
            intent.putExtra("Movie_id",data.getMovie_id());
            v.getContext().startActivity(intent);

        }
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
        protected Button vButton;

        public ContactViewHolder(View itemView) {
            super(itemView);
            vName = itemView.findViewById(R.id.movie_name);
            vScore = itemView.findViewById(R.id.movie_score);
            vActor = itemView.findViewById(R.id.actor);
            vImage=itemView.findViewById(R.id.card_movie_img1);
            vButton=itemView.findViewById(R.id.card_btn_buy1);
        }
    }
}
