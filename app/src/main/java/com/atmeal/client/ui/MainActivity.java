package com.atmeal.client.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.fragment.MainPageFragment;
import com.atmeal.client.fragment.MePageFragment;
import com.atmeal.client.fragment.NearPageFragment;
import com.atmeal.client.fragment.OrderPageFragment;

import java.util.ArrayList;

public class MainActivity extends BaseFragmentActivity {

    private FrameLayout frame_layout;
    private RadioButton home_page;
    private RadioGroup group;

    /** 首页tab索引 **/
    final int TAB_INDEX = 0;
    /** 附近tab索引 **/
    final int TAB_NEARBY = 1;
    /** 我的tab索引 **/
    final int TAB_ME = 2;

    final int TAB_NEAR = 3;

    private MainPageFragment mainPageFragment;
    private OrderPageFragment orderPageFragment;
    private MePageFragment mePageFragment;
    private NearPageFragment nearPageFragment;

    private android.support.v4.app.FragmentTransaction transaction;
    private ArrayList<Integer> listradio = new ArrayList<>();//添加对应的Radio 修改字体颜色

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitView();
    }

    @Override
    public void InitView() {
        frame_layout = (FrameLayout) findViewById(R.id.frame_layout);

        home_page = (RadioButton) findViewById(R.id.home_page);
        group = (RadioGroup) findViewById(R.id.group);

        listradio.add(R.id.home_page);
        listradio.add(R.id.nearby_page);
        listradio.add(R.id.me_page);
        listradio.add(R.id.page_near);

        home_page.setChecked(true);
        ChangeTab(TAB_INDEX);
        RadioButtonOnclick(group);
    }

    /**
     * 添加 tab页面
     * @param tab
     */
    public void ChangeTab(int tab){
        transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);

        switch (tab){
            case TAB_INDEX:
                Init_RadioText(R.id.home_page);
                if(mainPageFragment==null){
                    mainPageFragment = new MainPageFragment();
                    transaction.add(frame_layout.getId(),mainPageFragment);
                }else{
                    transaction.show(mainPageFragment);

                }
                break;
            case TAB_NEAR:
                Init_RadioText(R.id.page_near);
                if(nearPageFragment==null){
                    nearPageFragment = new NearPageFragment();
                    transaction.add(frame_layout.getId(),nearPageFragment);
                }else{
                    transaction.show(nearPageFragment);
                }
                break;
            case TAB_NEARBY:
                Init_RadioText(R.id.nearby_page);
                if(orderPageFragment==null){
                    orderPageFragment = new OrderPageFragment();
                    transaction.add(frame_layout.getId(),orderPageFragment);
                }else{
                    transaction.show(orderPageFragment);

                }
                break;
            case TAB_ME:
                Init_RadioText(R.id.me_page);
                if(mePageFragment==null){
                    mePageFragment = new MePageFragment();
                    transaction.add(frame_layout.getId(),mePageFragment);
                }else{
                    transaction.show(mePageFragment);
                }
                break;
        }
        transaction.commit();
    }

    //隐藏所有fragment
    public void hideAllFragment(android.support.v4.app.FragmentTransaction transaction) {

        if (mainPageFragment != null && mainPageFragment.isVisible()) {
            transaction.hide(mainPageFragment);
        }
        if (orderPageFragment != null && orderPageFragment.isVisible()) {
            transaction.hide(orderPageFragment);
        }
        if (mePageFragment != null && mePageFragment.isVisible()) {
            transaction.hide(mePageFragment);
        }
        if (nearPageFragment != null && nearPageFragment.isVisible()) {
            transaction.hide(nearPageFragment);
        }
    }

    /**
     * 重置 点击颜色
     * @param raid
     */
    private void Init_RadioText(int raid){
        for (int i = 0;i<listradio.size();i++){
            RadioButton viewById = (RadioButton) findViewById(listradio.get(i));
            viewById.setTextColor(Color.parseColor("#767676"));
        }
        RadioButton viewById = (RadioButton) findViewById(raid);
        viewById.setTextColor(Color.parseColor("#38bafe"));
    }

    /**
     * 点击 选择tab
     * @param radio
     */
    private void RadioButtonOnclick(RadioGroup radio){

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

//                UtilsCommon.BuildColor(getWindow(),KeXinMain_Activity.this,R.color.baseblu);
                if(checkedId == R.id.home_page){
                    ChangeTab(TAB_INDEX);
                }if(checkedId == R.id.nearby_page){
//                    IS_Login(1);
                    ChangeTab(TAB_NEARBY);
                }if(checkedId == R.id.me_page){
                    ChangeTab(TAB_ME);
                }
                if (checkedId == R.id.page_near){
                    ChangeTab(TAB_NEAR);
                }
//                if (checkedId == R.id.prowalletfooter){
//                    IS_Login(2);
//                }
            }
        });

    }

}
