package com.example.rise.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rise.R;

import org.xutils.x;

/**
 * Created by chengxi on 17/4/26.
 */
public class TabFragment3 extends Fragment{
    private TextView tv1, search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_3, null);
        x.view().inject(getActivity());
        return view;
    }

}
