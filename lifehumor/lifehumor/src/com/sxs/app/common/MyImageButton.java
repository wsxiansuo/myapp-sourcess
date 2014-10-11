package com.sxs.app.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxs.app.lifehumor.R;



public class MyImageButton extends LinearLayout {
	@Override
	protected void onLayout(boolean arg0, int l, int t, int r, int b) {
		boolean changed = arg0;
		for(int i = 0 ; i < getChildCount() ; i++){
            getChildAt(i).layout(l, t, r, b);
        }
	}
	@Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    } 

	public MyImageButton(Context context)
	{
		super(context);
	}
	public MyImageButton(Context context,AttributeSet attrs)
	{
		super(context,attrs);
		this.initComponent(context, attrs);
	}
	private void initComponent(Context context,AttributeSet attrs)
	{
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.common_attrs);
		LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.include_image_button, this);
        TextView titleTv = (TextView)findViewById(R.id.tv_image_button_title);
        String title = a.getString(R.styleable.common_attrs_title);
        titleTv.setText(title);
        titleTv.setVisibility(View.VISIBLE);
        
        ImageView iconIv = (ImageView)findViewById(R.id.iv_image_button_icon);
        int icon = a.getResourceId(R.styleable.common_attrs_icon, 0);
        iconIv.setBackgroundResource(icon);
        iconIv.setVisibility(View.VISIBLE);
        
        a.recycle();
	}
	
	protected void onFinishInflate ()
	{
		super.onFinishInflate();
		if (isInEditMode()) {
            return;
		}
	}
}
