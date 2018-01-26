package com.atmeal.client.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.atmeal.client.R;


/**
 * Created by admin on 2016/11/15.
 * 加载对话框
 */
public class DialogCommon {

    private Dialog mydialog;
    private static DialogCommon dialogCommon;
//    private ImageView dialog_pros;

    //IntentMange管理
    public DialogCommon(){

        dialogCommon = this;
    }

    public static DialogCommon getApplication(){
        return dialogCommon;
    }

    public static DialogCommon getIstance(){
        if(dialogCommon==null){
            dialogCommon = new DialogCommon();
        }
        return dialogCommon;
    }


    /***
     * 创建加载对话框
     *
     */
    public void CreateMyDialog(Context context) {
        mydialog = new Dialog(context, R.style.NobackDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_loading, null);
        ImageView dialog_pros = (ImageView) dialogView.findViewById(R.id.dialog_pros);

        dialog_pros.setImageResource(R.drawable.animal_dialog_loding);
        AnimationDrawable drawable = (AnimationDrawable) dialog_pros.getDrawable();
        drawable.start();
        mydialog.setCanceledOnTouchOutside(false);
        mydialog.setContentView(dialogView);
        mydialog.show();
    }
    /***
     * 关闭加载对话框
     *
     */
    public void MyDialogCanle() {
        if (mydialog != null){
            mydialog.dismiss();
        }else{
        }

    }

}
