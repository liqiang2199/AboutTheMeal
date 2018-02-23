package com.atmeal.client.loginactivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.been.busbeen.UpHeaderBus;
import com.atmeal.client.been.httpbeen.HttpBeen;
import com.atmeal.client.common.DialogCommon;
import com.atmeal.client.common.LogCommon;
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;
import com.atmeal.client.meactivity.setactivity.ChangeBindPhoneActivity;
import com.atmeal.client.utils.UtilTools;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/20.
 * 跟换头像
 */

public class HeaderSetActivity extends BaseFragmentActivity implements OkHttp_CallResponse{

    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 0;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private Bitmap bitmap;
    /* 头像名称 */
    private String PHOTO_FILE_NAME = "temp_kxheader.jpg";
    /* 剪切后头像文件路径 */
    private static final String CUTPHOTO_FILE_URL =
            Environment.getExternalStorageDirectory().getPath() + "/temp_kxheader.jpg";
    private Uri imageUri;
    private File tempFile;
    private boolean isCameraModel;

    private final int PAGE_INDEX = 1000;
    private final int PAGE_INDEX_READ = 2000;

    private int camareIndexPage = 1;//点击相机还是选择的相册

    private TextView photo_text;
    private TextView camera_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_set);
        InitView();
    }

    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("我的");

        photo_text = (TextView)findViewById(R.id.photo_text);
        camera_text = (TextView)findViewById(R.id.camera_text);

        OnClick(photo_text);
        OnClick(camera_text);

    }
    private void OnClick(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.photo_text:
                        camareIndexPage = 2;
                        if (UtilTools.check_Permission(context, Manifest.permission.CAMERA)) {
                            if (UtilTools.check_Permission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                intent_Image();
                            } else {
                                UtilTools.Request_PerMission(HeaderSetActivity.this,
                                        Manifest.permission.READ_EXTERNAL_STORAGE, PAGE_INDEX_READ);
                            }

                        } else {
                            UtilTools.Request_PerMission(HeaderSetActivity.this, Manifest.permission.CAMERA, PAGE_INDEX);
                        }
                        break;
                    case R.id.camera_text:
                        //先判断相机权限  在判断是否具有读写 内存的权限
                        camareIndexPage = 1;
                        if (UtilTools.check_Permission(context, Manifest.permission.CAMERA)) {

                            if (UtilTools.check_Permission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                useCamera();
                            } else {
                                UtilTools.Request_PerMission(HeaderSetActivity.this,
                                        Manifest.permission.READ_EXTERNAL_STORAGE, PAGE_INDEX_READ);
                            }
                        } else {
                            UtilTools.Request_PerMission(HeaderSetActivity.this, Manifest.permission.CAMERA, PAGE_INDEX);
                        }
                        break;
                }
            }
        });
    }

    private void intent_Image() {

        isCameraModel = false;
        Intent intentFromGallery = new Intent(Intent.ACTION_SEND);
        intentFromGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        intentFromGallery.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);    //这一步很重要。给目标应用一个临时的授权。
        startActivityForResult(intentFromGallery, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 使用相机
     */
    private void useCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        tempFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"
                + PHOTO_FILE_NAME);
        tempFile.getParentFile().mkdirs();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
            //改变Uri  com.xykj.customview.fileprovider注意和xml中的一致
            imageUri = FileProvider.getUriForFile(this, "com.atmeal.client.provider", tempFile);
            //添加权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        } else {
            intent.setAction("android.media.action.IMAGE_CAPTURE");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    /**
     * 剪切图片
     *
     * @param uri
     * @function:
     * @author:Jerry
     * @date:2013-12-30
     */
    private void crop(Uri uri) {
        // 裁剪图片意图

        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String url = getPath(this, uri);
//                intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
                intent.setDataAndType(getImageContentUri(context, new File(url)), "image/*");
            } else {
                intent.setDataAndType(uri, "image/*");
            }
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", !isCameraModel);
            if (isCameraModel) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                intent.putExtra("noFaceDetection", true);
            }
            startActivityForResult(intent, PHOTO_REQUEST_CUT);
        } catch (Exception e) {
            // TODO: handle exception
//            ToastLogTools.LogTip_V("  裁剪异常      " + e);
        }
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
//                ToastLogTools.LogTip_V("    本地  图片的全路径      " + data.getData());
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
//            ToastLogTools.LogTip_V("    本地  图片的全路径    ceeeee  111  " + resultCode);
            if (resultCode != RESULT_OK) {
                return;
            }
//            ToastLogTools.LogTip_V("    本地  图片的全路径    ceeeee  222  ");
            if (UtilTools.hasSdcard()) {
//                tempFile = new File(Environment.getExternalStorageDirectory(),
//                        PHOTO_FILE_NAME);
                if (tempFile != null) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        crop(getImageContentUri(context, tempFile));
                    } else {
                        crop(Uri.fromFile(tempFile));
                    }

                }
            } else {
                StringCommon.String_Toast(context, "未找到存储卡，无法存储照片！");
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                if (data == null) {
                    return;
                }
                if (!isCameraModel)
                    bitmap = data.getParcelableExtra("data");
                else
                    bitmap = UtilTools.decodeUriAsBitmap(imageUri, this);
//                BitmapUtil.saveBitmap(photo, IMAGE_FILE_NAME);
//                bitmap = data.getParcelableExtra("data");
                // this.mFace.setImageBitmap(bitmap);

//                GlideApp.with(this).load(CUTPHOTO_FILE_URL).error(R.drawable.imgview_background)
//                        .into(iv_head_me);
                // 保存剪切图片到本地
                UtilTools.saveMyBitmap(bitmap, CUTPHOTO_FILE_URL);
                // boolean delete = tempFile.delete();
                //上传头像
//                String loginurl = UrlCommon.Uploadheader+"?usertoken="+
//                        StringCommon.Read_SpUtil(context,"userToken")
//                        +"&uploadFile="+CUTPHOTO_FILE_URL;
//                OkHttpMannager.getInstance().Post_Data(loginurl,HeaderSetActivity.this,
//                        HeaderSetActivity.this,false,"changePhone");
                DialogCommon.getIstance().CreateMyDialog(context);
                uploadImg();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private final OkHttpClient client = new OkHttpClient();

    private void uploadImg() {
        String loginurl = UrlCommon.URL+UrlCommon.Uploadheader+"?userToken="+
                StringCommon.Read_SpUtil(context,"userToken");
        LogCommon.LogShowPrint("     上传 头像       "+loginurl);
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        for (int i = 0; i <mImgUrls.size() ; i++) {
//            File f=new File(mImgUrls.get(i));
//            if (f!=null) {
//
//            }
//        }
        File f=new File(CUTPHOTO_FILE_URL);
        builder.addFormDataPart("uploadFile", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));

        MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(loginurl)//地址
                .post(requestBody)//添加请求体
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        // 需要在主线程的操作。
                        DialogCommon.getIstance().MyDialogCanle();
                        StringCommon.String_Toast(context,"上传失败");

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                final String msg = response.body().string();
                System.out.println("上传照片成功：response = " +msg);
                final HttpBeen httpBeen = JSON.parseObject(msg,HttpBeen.class);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        // 需要在主线程的操作。
                        DialogCommon.getIstance().MyDialogCanle();
                        StringCommon.String_Toast(context,httpBeen.getTipMessge());
                        if (httpBeen .getCode() == 200){
                            try {
                                JSONObject jsonObject = new JSONObject(msg);
                                String data = jsonObject.getString("data");
                                //
                                LogCommon.LogShowPrint("     获取头像地址 data      "+data);
                                EventBus.getDefault().post(new UpHeaderBus(data));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                LogCommon.LogShowPrint("     获取头像地址 异常      "+e);
                            }

                            finish();
                        }
                    }
                });

            }
        });

    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {

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
