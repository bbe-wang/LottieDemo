package com.fueled.lottiedemo.tabtest;

import android.animation.Animator;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.fueled.lottiedemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ThinkPad on 2019/7/3.
 */

public class TabTest extends Activity implements  Animator.AnimatorListener {

    /**
     * 双手同时点击的时候 怎么更新ui
     * 现在的问题是：当两个同时点击 那么 则两个都同时显示蓝色了 怎么能正确捕捉一个点击动作 然后去执行
     */
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.lottie_first)
    LottieAnimationView lottieFirst;
    @BindView(R.id.first_image)
    ImageView firstImage;
    @BindView(R.id.first_layout)
    RelativeLayout firstLayout;
    @BindView(R.id.lottie_finet)
    LottieAnimationView lottieFinet;
    @BindView(R.id.image_finet)
    ImageView imageFinet;
    @BindView(R.id.finet_layout)
    RelativeLayout finetLayout;
    @BindView(R.id.lottie_stock)
    LottieAnimationView lottieStock;
    @BindView(R.id.image_stock)
    ImageView imageStock;
    @BindView(R.id.stock_layout)
    RelativeLayout stockLayout;
    @BindView(R.id.lottie_brocast)
    LottieAnimationView lottieBrocast;
    @BindView(R.id.image_brocast)
    ImageView imageBrocast;
    @BindView(R.id.brocast_layout)
    RelativeLayout brocastLayout;
    @BindView(R.id.lottie_wo)
    LottieAnimationView lottieWo;
    @BindView(R.id.image_wo)
    ImageView imageWo;
    @BindView(R.id.wo_layout)
    RelativeLayout woLayout;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.tv_finet)
    TextView tvFinet;
    @BindView(R.id.tv_stock)
    TextView tvStock;
    @BindView(R.id.tv_brocast)
    TextView tvBrocast;
    @BindView(R.id.tv_wo)
    TextView tvWo;
    /**
     * 用于首页消息的Fragment
     */
    private FirstFragment firstFragment;

    /**
     * 用于财华号的Fragment
     */
    private FinetFragment finetFragment;
    /**
     * 用于展示股吧Fragment
     */
    private StockFragment stockFragment;
    /**
     * 用于展示财华播报的Fragment
     */
    private BrocastFragment brocastFragment;

    /**
     * 用于展示我的Fragment
     */
    private WoFragment woFragment;

    private FragmentManager fragmentManager;
    private int mPosition, exPosition = -1;
    Unbinder unbinder;
    private List<LottieAnimationView> lottieList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_main);
        ButterKnife.bind(this);
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        mPosition = 0;
        lottieFirst.addAnimatorListener(this);
        lottieFinet.addAnimatorListener(this);
        lottieStock.addAnimatorListener(this);
        lottieBrocast.addAnimatorListener(this);
        lottieWo.addAnimatorListener(this);
        lottieList.add(lottieFirst);
        lottieList.add(lottieFinet);
        lottieList.add(lottieStock);
        lottieList.add(lottieBrocast);
        lottieList.add(lottieWo);
        setTabSelection(0);
    }




    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (firstFragment != null) {
            transaction.hide(firstFragment);
        }
        if (finetFragment != null) {
            transaction.hide(finetFragment);
        }
        if (stockFragment != null) {
            transaction.hide(stockFragment);
        }
        if (brocastFragment != null) {
            transaction.hide(brocastFragment);
        }
        if (woFragment != null) {
            transaction.hide(woFragment);
        }
    }


    @Override
    public void onAnimationStart(Animator animation) {
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        //如果这个动画完成 则需要判断需要执行反动画的position是哪个
        //需要规避的问题：
        if (mPosition != exPosition) {
           //执行完当前点击的位置的json后  需要将progress设置成100  mPosition
            //如果点击的是其他的view  那么就要将前一个view执行反动画
            switch (exPosition) {
                case -1:
                    lottieFirst.setProgress(1f);
                    exPosition=mPosition;
                    break;
                default:
                    lottieList.get(mPosition).setProgress(1f);
                    break;
            }

        }

    }

    @Override
    public void onAnimationCancel(Animator animation) {
        //如果被取消了的话 那么就将此view复原
        lottieList.get(exPosition).setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }

    /**
     * 如果在这个地方加同步代码块  则动画完成的监听 监听不到
     * @param view
     */

    @OnClick({R.id.first_layout, R.id.finet_layout, R.id.stock_layout, R.id.brocast_layout, R.id.wo_layout})
    public synchronized void onViewClicked(View view) {
       switch (view.getId()) {
            case R.id.first_layout:
                // 当点击了消息tab时，选中第1个tab
                mPosition = 0;
                changeTvColor(0);
                setTabSelection(0);
                break;
            case R.id.finet_layout:
                mPosition = 1;
                changeTvColor(1);
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.stock_layout:
                mPosition = 2;
                // 当点击了动态tab时，选中第3个tab
                changeTvColor(2);
                setTabSelection(2);
                break;
            case R.id.brocast_layout:
                mPosition = 3;
                changeTvColor(3);
                // 当点击了设置tab时，选中第4个tab
                setTabSelection(3);
                break;
            case R.id.wo_layout:
                mPosition = 4;
                changeTvColor(4);
                // 当点击了设置tab时，选中第4个tab
                setTabSelection(4);
                break;
        }
    }


    private  void changeTvColor(int position){
        switch (position){
            case 0:
                tvFirst.setTextColor(Color.parseColor("#333333"));
                tvFinet.setTextColor(Color.parseColor("#999999"));
                tvBrocast.setTextColor(Color.parseColor("#999999"));
                tvStock.setTextColor(Color.parseColor("#999999"));
                tvWo.setTextColor(Color.parseColor("#999999"));
                break;
            case 1:
                tvFirst.setTextColor(Color.parseColor("#999999"));
                tvFinet.setTextColor(Color.parseColor("#333333"));
                tvBrocast.setTextColor(Color.parseColor("#999999"));
                tvStock.setTextColor(Color.parseColor("#999999"));
                tvWo.setTextColor(Color.parseColor("#999999"));
                break;
            case 2:
                tvFirst.setTextColor(Color.parseColor("#999999"));
                tvFinet.setTextColor(Color.parseColor("#999999"));
                tvStock.setTextColor(Color.parseColor("#333333"));
                tvBrocast.setTextColor(Color.parseColor("#999999"));
                tvWo.setTextColor(Color.parseColor("#999999"));
                break;
            case 3:
                tvFirst.setTextColor(Color.parseColor("#999999"));
                tvFinet.setTextColor(Color.parseColor("#999999"));
                tvStock.setTextColor(Color.parseColor("#999999"));
                tvBrocast.setTextColor(Color.parseColor("#333333"));
                tvWo.setTextColor(Color.parseColor("#999999"));
                break;
            case 4:
                tvFirst.setTextColor(Color.parseColor("#999999"));
                tvFinet.setTextColor(Color.parseColor("#999999"));
                tvBrocast.setTextColor(Color.parseColor("#999999"));
                tvStock.setTextColor(Color.parseColor("#999999"));
                tvWo.setTextColor(Color.parseColor("#333333"));
                break;
        }
        changeLottie(position);
    }


    private  void changeLottie(int mPosition){
        for (int i=0;i<lottieList.size();i++){
            if (i == mPosition){
                if (lottieList.get(i).isAnimating()) {
                    lottieList.get(i).cancelAnimation();
                }
                lottieList.get(i).setVisibility(View.VISIBLE);
                lottieList.get(i).playAnimation();
            }else if (i == exPosition){
                lottieList.get(i).setVisibility(View.VISIBLE);
                lottieList.get(i).reverseAnimation();
                exPosition=mPosition;
            }else {
                lottieList.get(i).setVisibility(View.GONE);
            }
        }

    }



    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index 每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
     */
    private  void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (firstFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    firstFragment = new FirstFragment();
                    transaction.add(R.id.content, firstFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(firstFragment);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                if (finetFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    finetFragment = new FinetFragment();
                    transaction.add(R.id.content, finetFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(finetFragment);
                }
                break;
            case 2:

                // 当点击了动态tab时，改变控件的图片和文字颜色
                if (stockFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    stockFragment = new StockFragment();
                    transaction.add(R.id.content, stockFragment);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(stockFragment);
                }
                break;
            case 3:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                if (brocastFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    brocastFragment = new BrocastFragment();
                    transaction.add(R.id.content, brocastFragment);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(brocastFragment);
                }
                break;
            default:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                if (woFragment == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    woFragment = new WoFragment();
                    transaction.add(R.id.content, woFragment);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(woFragment);
                }
                break;
        }
        transaction.commit();
    }


    private void Cancellottie(LottieAnimationView lottieAnimationView) {
        if (lottieAnimationView.isAnimating()) {
            lottieAnimationView.cancelAnimation();
        }

    }
}
