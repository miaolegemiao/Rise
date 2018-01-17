package com.example.rise.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.example.rise.R;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengxi on 17/4/26.
 */
public class TabFragment11 extends Fragment{
    private ViewPager viewPager;
    private ImageAdapter mPagerAdapter;
    private ImageView imageView1, imageView2, imageView3;
    private ArrayList<ImageView> pageView = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_tab_1, null);
        x.view().inject(getActivity());
        viewPager = (ViewPager) view1.findViewById(R.id.viewpager);
        LayoutInflater inflater1 = getLayoutInflater(savedInstanceState);
        View view2 = inflater1.inflate(R.layout.item04, null);
        imageView1 = (ImageView) view2.findViewById(R.id.imageview1);
        imageView2 = (ImageView) view2.findViewById(R.id.imageview2);
        imageView3 = (ImageView) view2.findViewById(R.id.imageview3);
        pageView.add(imageView1);
        pageView.add(imageView2);
        pageView.add(imageView3);
        mPagerAdapter = new ImageAdapter(pageView);
        viewPager.setAdapter(mPagerAdapter);
        return view1;
    }

    class ImageAdapter extends PagerAdapter{

        private ArrayList<ImageView> viewlist;

        public ImageAdapter(ArrayList<ImageView> viewlist) {
            this.viewlist = viewlist;
        }

        @Override
        public int getCount() {
            //设置成最大，使用户看不到边界
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //Warning：不要在这里调用removeView
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //对ViewPager页号求模取出View列表中要显示的项
            position %= viewlist.size();
            if (position < 0){
                position = viewlist.size() + position;
            }
            ImageView view = viewlist.get(position);
            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = view.getParent();
            if (vp != null){
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
            container.addView(view);
            //add listeners here if necessary
            return view;
        }
    }
}
