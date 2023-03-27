package com.example.meituan.Tool;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class GetBitmap extends AppCompatImageView {

    public GetBitmap(Context context) {
        super(context);
    }

    public GetBitmap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GetBitmap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}


