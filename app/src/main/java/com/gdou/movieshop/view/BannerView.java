package com.gdou.movieshop.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.gdou.movieshop.R;

import java.lang.reflect.Field;

import static com.gdou.movieshop.global.MovieApplication.dip2px;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class BannerView {
    private int[] images;
    private Context context;
    private View bannerView;
    private LinearLayout ll_point;
    private ViewPager viewPager;
    private View move_point;
    private int switchTime = 6000;
    private int scrollTime = 600;
    private int pointWidth = 7;
    private int pointDistance = 4;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 101) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                start();
            }
            return false;
        }
    });

    public BannerView(Context context, int[] images) {
        this.context = context;
        this.images = images;
        initView();
    }

    @SuppressLint("InflateParams")
    private void initView() {
        bannerView = LayoutInflater.from(context).inflate(R.layout.bannerview, null);
        ll_point = (LinearLayout) bannerView.findViewById(R.id.ll_point);
        viewPager = (ViewPager) bannerView.findViewById(R.id.viewpager);
        move_point = bannerView.findViewById(R.id.move_point);
        viewPager.setAdapter(new BannerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) move_point.getLayoutParams();
                position = position % images.length;
                params.leftMargin = (int) (dip2px(pointWidth + pointDistance) * position + dip2px(pointWidth + pointDistance) * positionOffset);
                move_point.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        initPoint();
        setViewPagerDuration();
    }

    private void initPoint() {
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(dip2px(pointWidth), dip2px(pointWidth));
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(dip2px(pointWidth), dip2px(pointWidth));
        params1.leftMargin = 0;
        params2.leftMargin = dip2px(pointDistance);
        for (int i = 0; i < images.length; i++) {
            ImageView point = new ImageView(context);
            if (i == 0) {
                point.setBackgroundResource(R.drawable.gray_point);
                point.setLayoutParams(params1);
            } else {
                point.setBackgroundResource(R.drawable.gray_point);
                point.setLayoutParams(params2);
            }
            ll_point.addView(point);
        }
    }

    public void start() {
        handler.sendEmptyMessageDelayed(101, switchTime);
    }

    public void stop() {
        handler.removeCallbacksAndMessages(null);
    }

    public View getBannerView() {
        return bannerView;
    }

    private void setViewPagerDuration() {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(viewPager, new Scroller(context, new AccelerateInterpolator()) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, scrollTime);
                }
            });
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % images.length;
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(images[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
