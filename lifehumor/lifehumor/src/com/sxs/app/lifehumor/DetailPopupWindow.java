package com.sxs.app.lifehumor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.PopupWindow;

import com.sxs.app.common.BaseActionBar;
  
public class DetailPopupWindow extends PopupWindow {  
  

    private View mMenuView; 
    private WebView webView;  
    private BaseActionBar actionBar;
    
    public DetailPopupWindow(Activity context) {  
        super(context);  
        LayoutInflater inflater = (LayoutInflater) context  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        mMenuView = inflater.inflate(R.layout.popup_layout, null);  
        webView = (WebView) mMenuView.findViewById(R.id.wv_shuoming_webview);  
        actionBar = (BaseActionBar)mMenuView.findViewById(R.id.ab_detail_activity);  
        actionBar.setRightBtnOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();  
			}
		});
       
        //����SelectPicPopupWindow��View  
        this.setContentView(mMenuView);  
        //����SelectPicPopupWindow��������Ŀ�  
        this.setWidth(LayoutParams.MATCH_PARENT);  
        //����SelectPicPopupWindow��������ĸ�  
        this.setHeight(LayoutParams.MATCH_PARENT);  
        //����SelectPicPopupWindow��������ɵ��  
        this.setFocusable(true);  
        //����SelectPicPopupWindow�������嶯��Ч��  
        this.setAnimationStyle(R.style.mystyle);  
        //ʵ����һ��ColorDrawable��ɫΪ��͸��  
//        ColorDrawable dw = new ColorDrawable(0xb0000000);  
        //����SelectPicPopupWindow��������ı���  
//        this.setBackgroundDrawable(dw);  
        //mMenuView���OnTouchListener�����жϻ�ȡ����λ�������ѡ������������ٵ�����  
//        mMenuView.setOnTouchListener(new OnTouchListener() {  
//              
//            public boolean onTouch(View v, MotionEvent event) {  
//                return true;  
//            }  
//        });  
//  
    }  
    
    public void loadUrl(String url,String title){
    	actionBar.setTitle(title);
    	webView.loadUrl(url);
    }
  
}  