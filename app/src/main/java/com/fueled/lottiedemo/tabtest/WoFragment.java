package com.fueled.lottiedemo.tabtest;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fueled.lottiedemo.R;

/**
 * Created by ThinkPad on 2019/7/3.
 */

public class WoFragment extends Fragment {

    private TextView tv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.message, container, false);
        tv = (TextView) messageLayout.findViewById(R.id.message);
        tv.setText("我的");
        return messageLayout;
    }
}
