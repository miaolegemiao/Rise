package com.example.rise.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rise.R;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengxi on 17/4/26.
 */
public class TabFragment1 extends Fragment{
    private ViewPager viewPager;
    private List<View> pageView = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_tab_1, null);
        x.view().inject(getActivity());
        viewPager = (ViewPager) view1.findViewById(R.id.viewpager);
        LayoutInflater inflater1 = getLayoutInflater(savedInstanceState);
        View view2 = inflater1.inflate(R.layout.item01, null);
        View view3 = inflater1.inflate(R.layout.item02, null);
        View view4 = inflater1.inflate(R.layout.item03, null);
        pageView.add(view2);
        pageView.add(view3);
        pageView.add(view4);
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            // 获取当前窗体界面数
            public int getCount() {
                //return pageView.size();
                return Integer.MAX_VALUE;
            }

            @Override
            // 判断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            // 是从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageView.get(arg1 % pageView.size()));
            }

            // 返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1) {
                ((ViewPager) arg0).addView(pageView.get(arg1 % pageView.size()), 0);
                return pageView.get(arg1 % pageView.size());
            }
        };
        viewPager.setAdapter(mPagerAdapter);
        return view1;
    }
}
