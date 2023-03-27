package com.example.meituan.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meituan.Bean.Foodbean;
import com.example.meituan.R;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends BaseAdapter {
    private List<Foodbean> mList;
    private LayoutInflater layoutInflater;

    public FoodAdapter(Context context, List<Foodbean> list){
        mList = list;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size() ;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public View getView(int i, View view, ViewGroup viewGroup) {
        int position = i;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_food, null);
            viewHolder.textView = (TextView)view.findViewById(R.id.food_name);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.food_lei);
            viewHolder.textView2 = (TextView)view.findViewById(R.id.food_intro);
            viewHolder.textView3 = (TextView)view.findViewById(R.id.food_price);
            viewHolder.imageView = (ImageView)view.findViewById(R.id.food_img) ;
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText("名称:"+mList.get(position).getFood_name());
        viewHolder.textView1.setText("类别:"+mList.get(position).getFood_lei());
        viewHolder.textView2.setText("介绍:"+mList.get(position).getFood_intro());
        viewHolder.textView3.setText("价格:"+mList.get(position).getFood_price()+"元");
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeFile(mList.get(position).getFood_img());
        viewHolder.imageView.setImageBitmap(bitmap);
        return view;
    }
    public final class ViewHolder {
        public TextView textView, textView1, textView2, textView3, textView4;
        public ImageView imageView;
    }

}
