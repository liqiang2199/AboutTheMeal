package com.atmeal.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.atmeal.client.R;

/**
 * Created by Administrator on 2018/1/21.
 */

public class GridNearAdapter extends BaseAdapter {
    private Context context;
    public GridNearAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_adapter_gridview_content,null);
        }

        return view;
    }
}
