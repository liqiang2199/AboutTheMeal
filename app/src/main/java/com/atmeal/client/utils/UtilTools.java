package com.atmeal.client.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.atmeal.client.common.StringCommon;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by empty cup on 2017/11/2.
 *
 */

public class UtilTools {
    public static String downloadDir = "app/download/";
    /**
     * 匹配请求成功返回的字段
     * @param str
     * @return
     */
    public static boolean isResultCodeMate(String str){

        Pattern pattern = Pattern.compile("200$");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
    /**
     * 匹配请求成功返回的字段
     * @param str
     * @return
     */
    public static boolean isResultCodeForBankMate(String str){

        Pattern pattern = Pattern.compile("026$");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 电子邮箱验证
     * @param email
     * @return
     */
    public static boolean isEamil(String email){
        if (!StringCommon.StringNull(email)){
            return false;
        }
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }
    /**
     * 正则表达式 判断电话号是否正确
     */
    public static boolean isPhoneHomeNum(String num) {
        Pattern p =
                Pattern.compile("^(((13[0-9])|(14[0-9])|(15([0-3]|[5-9])|17[0,5-9])|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{8})$");
        Matcher m = p.matcher(num);
        return m.matches();
    }

    /**
     * 正则表达式 判断纯电话 和 纯字母
     */
    public static boolean isNum(String num) {
        Pattern p =
                Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(num);
        return m.matches();
    }
    public static boolean isChar(String num) {
        Pattern p =
                Pattern.compile("^[A-Za-z]*$");
        Matcher m = p.matcher(num);
        return m.matches();
    }

    /**
     * 判断字符串 为空
     * @param s
     * @return
     */
    public static boolean isStringNull(String s){
        if (s == null||s.equals(null)||s.equals("null")||s.equals("")){
            return true;
        }
        return false;
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 验证 和 交易密码  6位数
     * @param st
     * @return
     */
    public static boolean Edit_Six_Length(String st){
        if (!StringCommon.StringNull(st)){
            return true;
        }
        if (st.length() == 6){
            return false;
        }
        return true;
    }
    /**
     * 注册密码  4-16位数
     * @param st
     * @return
     */
    public static boolean Edit_Four_Length(String st){
        if (!StringCommon.StringNull(st)){
            return true;
        }
        if (st.length() >= 4&&st.length()<=16){
            return false;
        }
        return true;
    }
    /**
     * 验证 和 交易密码  6位数
     * @param st
     * @return
     */
    public static boolean Edit_DYSix_Length(String st){
        if (!StringCommon.StringNull(st)){
            return true;
        }
        if (st.length() >= 6){
            return false;
        }
        return true;
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static SimpleDateFormat formatdata() {
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /***
     * 时间戳转换成时间
     *  yyyy-MM-dd HH:mm:ss
     * @param dt
     * @return
     */
    public static String DateGetTime(String dt,String styleType) {
        if (!StringCommon.StringNull(dt)) {
            return "";
        }
        if (dt.length()<=10){
            //如时间戳 为10 位数的时候后面添加0
            dt = dt+"000";
        }
        SimpleDateFormat ft = new SimpleDateFormat(styleType);
        String sd = ft.format(new Date(Long.parseLong(dt)));
        return sd;
    }

    /**
     * 将时间 转化成时间戳
     * @param time
     * @return
     * @throws ParseException
     */
    public static long DataLongTime(String time) throws ParseException {
        Date date = new SimpleDateFormat("yyyy年MM月dd").parse(time);
        long unixTimestamp = date.getTime()/1000;
        return unixTimestamp;
    }

    /**
     * 处理0 0.0 0.00
     * @param zero
     * @return
     */
    public static boolean Zero_Money(String zero){
        if (!StringCommon.StringNull(zero)){
            return true;
        }
        if (zero.equals("0")||zero.equals("0.0")||zero.equals("0.00")){
            return true;
        }
        return false;
    }

    /**
     *  计算ListView 高度
     * @param context
     * @param width
     * @param listView
     */
    public static void measureListViewHeight(Context context,int width,ListView listView,int listdp) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
//        int listViewWidth = width -UtilsTools.dip2px(context, listdp);//listView在布局时的宽度
        int listViewWidth = width ;//listView在布局时的宽度
        int widthSpec = View.MeasureSpec.makeMeasureSpec(listViewWidth, View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(widthSpec, 0);

            int itemHeight = listItem.getMeasuredHeight();
            totalHeight += itemHeight;
        }
        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 减掉底部分割线的高度
        params.height = totalHeight
                + (listView.getDividerHeight() * listAdapter.getCount() - 1);
        listView.setLayoutParams(params);
    }

//    public void setListViewHeightBasedOnChildren(ListView listView) {
//        // 获取ListView对应的Adapter
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//        int totalHeight = 0;
//        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0); // 计算子项View 的宽高
//            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight
//                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        // listView.getDividerHeight()获取子项间分隔符占用的高度
//        // params.height最后得到整个ListView完整显示需要的高度
//        listView.setLayoutParams(params);
//    }

    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }

    public static void setListViewHeight(ExpandableListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        int count = listAdapter.getCount();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 头像的删除
     */
    //删除图片
    public static void deleteLogoPhotoPath() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            String SDCarePath = Environment.getExternalStorageDirectory()
                    .toString();
            String filePath = SDCarePath + File.separator + "temp_kxheader.jpg";
            File file = new File(filePath);
            if (file.exists())
                file.delete();
        }
    }

    /**
     * bitmap 转化
     * @param uri
     * @param act
     * @return
     */
    public static Bitmap decodeUriAsBitmap(Uri uri, Activity act) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(act.getContentResolver()
                    .openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 获取设备权限检查
     * @param context
     * @param permission_Name
     * @return
     */
    public static boolean check_Permission(Context context,String permission_Name){
        //例：Manifest.permission.READ_CONTACTS = permission_Name
        if (ContextCompat.checkSelfPermission(context, permission_Name)
                != PackageManager.PERMISSION_GRANTED) {
            // 没有权限，可以在这里重新申请权限。
            return false;
        }
        return true;
    }
    /**
     * 请求权限
     * @param index
     */
    public static void Request_PerMission(Activity activity,String reper,int index){
        ActivityCompat.requestPermissions(
                activity,
                new String[]{reper},index);
    }
    /***
     * 保存图片文件到本地
     *
     * @param mBitmap
     * @param 、bitName
     */
    public static void saveMyBitmap(Bitmap mBitmap, String Url) {
        File f = new File(Url);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param imgurl
     * @return
     */
    public static String ImageBase64(String imgurl) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
//        String imgFile = imgurl;// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgurl);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        return Base64Helper.encode(data); // 返回Base64编码过的字节数组字符串
    }

    /**
     * json 判断是否为空  是否有值  是否存在key值
     */
    public static  String json_GetKeyReturnValue(JSONObject jsonObject,String key) throws JSONException {
        // 判断jsonObject 是否为空
        if (jsonObject == null){
            return "";
        }
        //key值为空时 也返回空
        if (!StringCommon.StringNull(key)){
            return "";
        }
        //不存在 是 返回
        if (!jsonObject.has(key)){
            return "";
        }
        return jsonObject.getString(key);
    }

    /**
     * 返回当前程序版本名
     */
//    public static GetPackAgeInfoBeen getAppVersionName(Context context) {
//        GetPackAgeInfoBeen getPackAgeInfoBeen = new GetPackAgeInfoBeen();
//        try {
//            // ---get the package info---
//            PackageManager pm = context.getPackageManager();
//            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
//            getPackAgeInfoBeen.setVersionName(pi.versionName);
//            getPackAgeInfoBeen.setVersionCode(pi.versionCode);
//
//            if (getPackAgeInfoBeen == null) {
//                getPackAgeInfoBeen.setVersionName("1.0.0");
//                getPackAgeInfoBeen.setVersionCode(1);
//                return getPackAgeInfoBeen;
//            }
//        } catch (Exception e) {
//        }
//        return getPackAgeInfoBeen;
//    }

    /**
     * 改变状态栏颜色
     */
    public static void BuildColor(Window window, Activity activity, int idresouce) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(window, true);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarAlpha(0.3f);
            tintManager.setStatusBarTintResource(idresouce);//通知栏所需颜色
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void setTranslucentStatus(Window window, boolean on) {
        WindowManager.LayoutParams winParams = window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        window.setAttributes(winParams);
    }

    @SuppressWarnings("deprecation")
    public static int[] getScreenDispaly(Context context){
        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int width=wm.getDefaultDisplay().getWidth();//手机屏幕的宽度
        int height=wm.getDefaultDisplay().getHeight();//手机屏幕的高度
        int result[] = {width,height};
        return result;
    }

}
