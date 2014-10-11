package com.sxs.app.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sxs.app.lifehumor.R;

public class PageControl extends LinearLayout implements OnClickListener,SeekBar.OnSeekBarChangeListener{  
	
    private Button leftBtn;  
    private Button rightBtn;  
    private SeekBar seekBar;  
    private TextView pageText;  

    public int numPerPage=10;  
    private int curPage=1;  
    private int count=0; 
    private int totalPage;
    private OnListPageChangeListener pageChangeListener;   
    public PageControl(Context context) {  
        super(context);  
        initComponent(context);  
    }  
    public PageControl(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        initComponent(context);  
    }  
    public PageControl(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        initComponent(context);  
    }  
    private void initComponent(Context context)
	{
		LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.pagecontrol_layout, this);
	    
	    // ��һҳ
        leftBtn = (Button)findViewById(R.id.page_left);
        leftBtn.setOnClickListener(this);
        rightBtn = (Button)findViewById(R.id.page_right);
        rightBtn.setOnClickListener(this);
        seekBar = (SeekBar) findViewById(R.id.page_seekbar);
        seekBar.setOnSeekBarChangeListener(this);
        pageText = (TextView) findViewById(R.id.page_text);
        
	}
    /** 
     * ��ʼ����ҳ�������ʾ״̬ 
     * @param newCount 
     */  
    public void initPageShow(int newCount){  
        count=newCount;  
        totalPage=count%numPerPage==0?count/numPerPage:count/numPerPage+1;  
        curPage=1;  
        seekBar.setProgress(0);
        leftBtn.setEnabled(false);  
        if(totalPage<=1){  
            rightBtn.setEnabled(false);    
        }else{  
        	rightBtn.setEnabled(true);  
        }  
        pageText.setText("��" + curPage + "/" + totalPage + "ҳ");
        if(pageChangeListener != null){
        	pageChangeListener.pageChanged(curPage,numPerPage); 
        }
    }  
    /** 
     * ��ҳ��ť�����ʱ����״̬,�÷���Ҫ��initPageShow����� 
     */  
    @Override  
    public void onClick(View view) {  
        if(pageChangeListener==null){  
            return;  
        }   
        switch(view.getId()){  
        case R.id.page_left:  
        	curPage--;  
        	if(curPage < 1)curPage = 1;
            if(curPage==1){  
                leftBtn.setEnabled(false);  
            }  
            if(totalPage>1){  
                rightBtn.setEnabled(true);  
            }  
            break;  
        case R.id.page_right:  
            curPage++;
            if(curPage > totalPage) curPage = totalPage;
            if(curPage==totalPage){  
            	rightBtn.setEnabled(false);  
            }  
            leftBtn.setEnabled(true);  
            break;  
        default:  
            break;  
        }  
        pageText.setText("��" + curPage + "/" + totalPage + "ҳ");
        pageChangeListener.pageChanged(curPage,numPerPage); 
        double pos = curPage * 0.1 / totalPage * 10;
        int value = (int)Math.round(pos * 100);
        if(value < 0)value = 0;
        if(value > 100)value = 100;
        seekBar.setProgress(value);
    }  
    public OnListPageChangeListener getPageChangeListener() {  
        return pageChangeListener;  
    }  
    /** 
     * ���÷�ҳ�����¼� 
     * @param pageChangeListener 
     */  
    public void setPageChangeListener(OnListPageChangeListener pageChangeListener) {  
        this.pageChangeListener = pageChangeListener;  
    }
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		double pos = progress * 0.01 * totalPage;  
		curPage = (int)Math.round(pos);
		if(curPage < 1){  
			curPage = 1;
            leftBtn.setEnabled(false);  
        }else{
        	leftBtn.setEnabled(true);  
        }
		if(curPage>=totalPage){ 
			curPage = totalPage;
        	rightBtn.setEnabled(false);  
        }else if(totalPage>1){
        	rightBtn.setEnabled(true);  
        }
//		Log.i("aaa", curPage + "");
		pageText.setText("��" + curPage + "/" + totalPage + "ҳ");
		if(pageChangeListener != null)
			pageChangeListener.pageChanged(curPage,numPerPage);  
	}
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}  
}  
