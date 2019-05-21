package com.gdou.movieshop;

public class GlobalParms {
    private static HomeFragment HomeFragment; //主页fragemnt
    private static MoviesFragment moviesFragment; //电影fragemnt
    private static MyFragment myFragment; //我的fragemnt
    public static ChangeFragment ChangeFragment;  //改变选中Frangment的接口

    /**
     * 获取主页Fragment
     *
     * @return
     */
    public static HomeFragment getHomeFragment() {
        if (HomeFragment == null) {
            HomeFragment = new HomeFragment();
        }
        return HomeFragment;
    }

    /**
     * 电影MoviesFragment
     *
     * @return
     */
    public static MoviesFragment getMoviesFragment() {
        if (moviesFragment == null) {
            moviesFragment = new MoviesFragment();
        }
        return moviesFragment;
    }

    /**
     * 我的MyFragment
     *
     * @return
     */
    public static MyFragment getMyFragment() {
        if (myFragment == null) {
            myFragment = new MyFragment();
        }
        return myFragment;
    }


    /**
     * 设置被选中的Fragment
     * @param changeFragment
     */
    public static void setFragmentSelected(ChangeFragment changeFragment) {
        ChangeFragment = changeFragment;

    }
}