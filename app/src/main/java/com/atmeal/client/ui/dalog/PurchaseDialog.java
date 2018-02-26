package com.atmeal.client.ui.dalog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.been.jsonbeen.CreatOrderBeen;
import com.atmeal.client.been.jsonbeen.ShopListBeen;
import com.atmeal.client.common.IntentCommon;
import com.atmeal.client.common.SPUtilsCommon;
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;
import com.atmeal.client.ui.order.OrderPayActivity;
import com.atmeal.client.utils.UtilTools;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/25.
 * 点击拼单
 */

public class PurchaseDialog extends Dialog implements View.OnClickListener,OkHttp_CallResponse{
    private TextView meal_purchase;
    private TextView alone_purchase;
    private TextView purchase_tip;

    private String shopId;
    private String shopType;
    private ShopListBeen shopListBeen;

    public PurchaseDialog(@NonNull Context context) {
        super(context);
    }

    public PurchaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PurchaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public PurchaseDialog(@NonNull Context context,String shopId,ShopListBeen shopListBeen) {
        super(context);
        this.shopId = shopId;
        this.shopListBeen = shopListBeen;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_meals);
        setCanceledOnTouchOutside(true);
        InitView();
    }

    private void InitView() {
        Resources resources = getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Window window = getWindow();
        WindowManager.LayoutParams  params = window.getAttributes();
        /**
         Dialog的width和height默认值为WRAP_CONTENT，正是因为如此，当屏幕中有足够的空间时，Dialog是不会被压缩的
         但是设置width和height为MATCH_PARENT的代价是无法设置gravity的值，这就无法调整Dialog中内容的位置，
         Dialog的内容会显示在屏幕左上角位置不过可以通过Padding来调节Dialog内容的位置。
         **/
        params.width = width- UtilTools.dip2px(getContext(),40);
        window.setAttributes(params);

        meal_purchase = findViewById(R.id.meal_purchase);
        alone_purchase = findViewById(R.id.alone_purchase);
        purchase_tip = findViewById(R.id.purchase_tip);

        purchase_tip.setText("选择购买的类型？");
        alone_purchase.setOnClickListener(this);
        meal_purchase.setOnClickListener(this);

    }



    private void creatOrder(){
        String purchase = UrlCommon.createOrder+"?shopId="+shopId
                +"&userToken="+ SPUtilsCommon.get(getContext(),"userToken","").toString()
                +"&orderType="+shopType;
        OkHttpMannager.getInstance().Post_Data(purchase,getContext(),
                this,true,"Purchase");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.meal_purchase:
                //拼单购买
                shopType = "1";
                creatOrder();
                break;
            case R.id.alone_purchase:
                //单独购买
                shopType = "2";
                creatOrder();
                break;
        }
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {
        dismiss();
        StringCommon.String_Toast(getContext(),"下单成功，去完成支付吧");
        try {
            if (tag.equals("Purchase")){
                String data = jsonObject.getString("data");
                JSONObject json = new JSONObject(data);
                CreatOrderBeen creatOrderBeen = new CreatOrderBeen();
                creatOrderBeen.setOrderId(UtilTools.json_GetKeyReturnValue(json,"OrderId"));
                creatOrderBeen.setIsFightAlone(UtilTools.json_GetKeyReturnValue(json,"IsFightAlone"));
                IntentCommon.getIstance().StartIntent(getContext(), OrderPayActivity.class,
                        "ShopPay",shopListBeen,"orderId",creatOrderBeen.getOrderId());
            }
        }catch (Exception e){

        }
    }

    @Override
    public void OkHttp_CallonFailure(Call call) {

    }

    @Override
    public void OkHttp_CallNoData(String tag) {

    }

    @Override
    public void OkHttp_CallError(String tag) {

    }

    @Override
    public void OkHttp_CallToastShow(String msg, String tag) {

    }
}
