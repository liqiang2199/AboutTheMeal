package com.atmeal.client.common;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.atmeal.client.application.MealApplication;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2016/11/7.
 * 所有的字符串 判断
 */
public class StringCommon {


    /**
     * Toast
     *
     * @param context
     * @param messge
     */
    public static void String_Toast(Context context, String messge) {
        if (context != null)
            Toast.makeText(context, messge, Toast.LENGTH_SHORT).show();
    }

    public static void String_Toast(Context context, int messge) {
        if (context != null)
            Toast.makeText(context, messge, Toast.LENGTH_SHORT).show();
    }

    //判断是否有网络连接
    public static boolean String_ToastFlag(Context context, int messge) {
        if (MealApplication.getIstance().isNetworkConnected())
            return true;
        else {
            String_Toast(context, messge);
//            DialogCommon.getIstance().MyDialogCanle();
            return false;
        }
    }

    //字符串为空判断long    n   = 10000000;
    public static boolean StringNull(String s) {

//        for (long i = 0; i < 10000000; i++) {
        if (s == null || s.isEmpty() || s.equals("")||s.equals("null")) {
            return false;
        }

//        }
        return true;

    }

    //判断字符串相等 相等就返回TRUE
    public static boolean StringEqules(String s1, String s2) {
        if (s1.equals(s2)) {
            return true;
        }

        return false;
    }

    //判断密码长度至少6-20位
    public static boolean StringLongSIX(String s1) {
        if (s1.length() >= 6 && s1.length() <= 20) {
            return true;
        }
        return false;
    }

    //设置交易密码
    public static boolean StringLongSixLength(String s1) {
        if (s1.length() == 6) {
            return true;
        }
        return false;
    }

    /**
     * 特殊字符处理
     *
     * @return
     */
//    public static String replaceBlank(String str) {
//        String dest = "";
//        if (str!=null) {
//
//            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//            Matcher m = p.matcher(str);
//            dest = m.replaceAll("");
//        }
//        return dest;
//    }

    // 判断一个字符串是否都为数字
    public static boolean isDigit(String strNum) {
        if (strNum == null && strNum.equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) strNum);
        return matcher.matches();
    }


    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        if (mobiles.length() > 0 && mobiles.length() <= 11) {
            String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
//            if (TextUtils.isEmpty(mobiles))
//
//            else
//                return mobiles.matches(telRegex);
            return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
        } else {
            return false;
        }

    }

    /***
     * 电话号码安全处理
     *
     * @param Mobile
     * @return
     */
    public static String MobileSafe(String Mobile) {
        if (Mobile.equals("")) {
            return Mobile;
        }
        String str1 = Mobile.substring(3, 8);// 13568270989
        return Mobile.replace(str1, "*****");
    }

    /**
     * 身份证安全处理
     *
     * @param IDcard
     * @return
     */
    public static String IDCardSafe(String IDcard) {
        if (IDcard.equals("")) {
            return IDcard;
        }
        String str1 = IDcard.substring(3, (IDcard.length() - 4));
        return IDcard.replace(str1, "***********");
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = formatter.format(dateDate);
        return formatter.format(dateDate);
    }

    /***
     * 时间戳转换成时间
     *
     * @param dt
     * @return
     */
    public static String DateGet(String dt) {
        if (dt.equals("0") || dt.equals("") || dt == null) {
            return "";
        }
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//        String sd = ft.format(Long.valueOf(dt)*1000);
        return ft.format(Long.valueOf(dt) * 1000);
    }

//    /**
//     * 获取年月
//     * @param dt
//     * @return
//     */
//    public static  String DateGetSubYM(String dt) {
//        if (dt.equals("0")||dt.equals("")||dt == null) {
//            return "";
//        }
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM");
////        String sd = ft.format(Long.valueOf(dt)*1000);
//        return ft.format(Long.valueOf(dt)*1000);
//    }

    /**
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime() / 1000;
//        res = String.valueOf(ts);
        return ts;
    }

    /***
     * 时间戳转换成时间
     *
     * @param dt
     * @return
     */
    public static String DateGetTime(String dt) {
        if (dt.equals("0") || dt.equals("") || dt == null) {
            return "";
        }
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String sd = ft.format(Long.valueOf(dt)*1000);
        return ft.format(Long.valueOf(dt) * 1000);
    }

    /* 将字符串转为时间戳 */
    public static long ToDate(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    /**
     * 返回对应时间错
     *
     * @param clander
     * @return
     */
    public static long ToMonth(String clander) {
        SimpleDateFormat sf = new SimpleDateFormat("yy-MM");
        Date date = new Date();
        try {
            date = sf.parse(clander);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    //身份证验证是否正确
    public static boolean IDCrad(String IDname) {
        if (null == IDname || "".equals(IDname))
            return false;
//			Pattern p = Pattern.compile("[\\d]{6}(19|20)*[\\d]{2}((0[1-9])|(11|12))([012][\\d]|(30|31))[\\d]{3}[xX\\d]*");
//			Pattern p = Pattern.compile("\\d{17}[0-9Xx]");
        Pattern p = Pattern.compile("^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$");

        Matcher m = p.matcher(IDname);
        return m.find();


    }

    /**
     * 银行卡验证
     *
     * @param IDname
     * @return
     */
    public static boolean BankCrad(String IDname) {
        if (null == IDname || "".equals(IDname))
            return false;
//			Pattern p = Pattern.compile("[\\d]{6}(19|20)*[\\d]{2}((0[1-9])|(11|12))([012][\\d]|(30|31))[\\d]{3}[xX\\d]*");
//			Pattern p = Pattern.compile("\\d{17}[0-9Xx]");
//        Pattern p = Pattern.compile("^\\d{19}$");
//        Pattern p = Pattern.compile("/^(\\d{16}|\\d{19})$/");

//        Matcher m = p.matcher(IDname);
        if (IDname.length() <= 19 && IDname.length() >= 16) {
            return true;
        }
        return false;


    }


//    /**
//     * 校验银行卡卡号
//     * @param cardId
//     * @return
//     */
//    public static boolean checkBankCard(String cardId) {
//        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
//        if(bit == 'N'){
//            return false;
//        }
//        return cardId.charAt(cardId.length() - 1) == bit;
//    }

//    /**
//     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
//     * @param nonCheckCodeCardId
//     * @return
//     */
//    public static char getBankCardCheckCode(String nonCheckCodeCardId){
//        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
//                || !nonCheckCodeCardId.matches("\\d+")) {
//            //如果传的不是数据返回N
//            return 'N';
//        }
//        char[] chs = nonCheckCodeCardId.trim().toCharArray();
//        int luhmSum = 0;
//        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
//            int k = chs[i] - '0';
//            if(j % 2 == 0) {
//                k *= 2;
//                k = k / 10 + k % 10;
//            }
//            luhmSum += k;
//        }
//        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
//    }

    /***
     * 双精度数据
     *
     * @param v
     * @return
     */
    public static String doubleformat(String v) {
        // 格式化计算总价
        final DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(Double.valueOf(v));
    }

    /**
     * 金钱的格式化 显示
     *
     * @param num
     * @return
     */
    public static String Double_Zero(String num) {
        if (num.equals("") || num == null) {
            return "";
        }
        Double d = Double.valueOf(num);
        NumberFormat nf = new DecimalFormat("###,##0.00");
        return nf.format(d);
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static SimpleDateFormat formatdata() {
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date s1 = sdf.parse(smdate);
        Date s2 = sdf.parse(bdate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(s1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(s2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 截取字符姓名  隐藏姓
     *
     * @param name
     * @return
     */
    public static String Sub_Name(String name) {
        if (name.equals("")) {
            return name;
        }
        String subname = null;
        if (name.length() > 4) {
            subname = name.substring(2, name.length());
            subname = "**" + subname;
        } else {
            subname = name.substring(1, name.length());
            subname = "*" + subname;
        }

        return subname;

    }

    /**
     * 判断字符串是否是纯数字
     *
     * @param string_nub
     * @return
     */
    public static boolean Nub_Char(String string_nub) {
        if (string_nub == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) string_nub);
        return matcher.matches();
    }

    /**
     * 获取 保存的Key 值
     * @param context
     * @param key
     * @return
     */
    public static String Read_SpUtil(Context context,String key){
        if (!StringNull(key)){
            return "";
        }
        if (!SPUtilsCommon.contains(context,key)){
            return "";
        }
        return SPUtilsCommon.get(context,key,"").toString();
    }

}
