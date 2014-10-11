package com.sxs.app.lifehumor;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sxs.app.common.BaseActionBar;
import com.sxs.app.common.BraintwisterAdapter;
import com.sxs.app.common.OnListPageChangeListener;
import com.sxs.app.common.PageControl;
import com.sxs.app.data.BraintwisterVO;
import com.sxs.app.data.UserDataModel;

import drop.doujin.android.rp.DropManager;

@SuppressLint("ShowToast")
public class HumorListActivity extends Activity implements OnListPageChangeListener{
	
	
	@ViewInject(R.id.lv_bt_list)         ListView listView;
	@ViewInject(R.id.page_control)       PageControl pageControl;
	@ViewInject(R.id.ab_index_activity)  BaseActionBar actionBar;
	@ViewInject(R.id.tv_no_data) 	     TextView  noDataView;
	private String startTitle = "";
	private String startType = "";
	private String currType = "1";
	private String currTitle = "生活小幽默";
	private BraintwisterAdapter adapter;
	private List<BraintwisterVO> alllistdata;
	private List<BraintwisterVO> listdata;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_media);
		ViewUtils.inject(this); 
		adapter = new BraintwisterAdapter(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				BraintwisterVO item = (BraintwisterVO) listView.getItemAtPosition(arg2);
				ImageView img = (ImageView) arg1.findViewById(R.id.bt_tag_iv);
				if(img != null && item != null){
					item.isTag = !item.isTag;
					UserDataModel.instance().mgr.updateQuestionState(item.id +"",item.isTag);
					img.setImageResource(item.isTag ? R.drawable.star_full:R.drawable.star_empty);
					Toast.makeText(getApplicationContext(), item.isTag?"成功添加收藏！":"成功去除收藏！", Toast.LENGTH_SHORT).show();
				}
			}
        });
		actionBar.setRightBtnOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!"tag".equals(currType)){
					currTitle = "我的收藏夹";
					currType = "tag";
					actionBar.setTitle(currTitle);
					actionBar.setRightBtnVisible(View.INVISIBLE);
					initdata();
				}
			}
		});
		actionBar.setReturnBtnOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				handlerBack();
			}
		});
	}
	
	private void initdata(){
		if("tag".equals(currType)){
			alllistdata = UserDataModel.instance().mgr.queryQuestionList("",true);
		}else{
			alllistdata = UserDataModel.instance().mgr.queryQuestionList(currType,false);
		}
		if(alllistdata == null || alllistdata.size() == 0){
			noDataView.setVisibility(View.VISIBLE);
			listView.setVisibility(View.INVISIBLE);
			pageControl.setVisibility(View.INVISIBLE);
		}else{
			if(!"tag".equals(currType)){
				UserDataModel.instance().handleList(alllistdata);
			}
			noDataView.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			pageControl.setVisibility(View.VISIBLE);
			pageControl.setPageChangeListener(this);  
	        pageControl.initPageShow(alllistdata.size());  
		}
	}
	
	
	
	
	private void handlerBack(){
		if("tag".equals(currType)){
			currTitle = startTitle;
			currType = startType;
			actionBar.setTitle(currTitle);
			actionBar.setRightBtnVisible(View.VISIBLE);
			initdata();
		}else{
			this.onBackPressed();
		}
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        switch (keyCode) {
        case KeyEvent.KEYCODE_BACK:     
        	handlerBack();
            break;
        default:
            break;
        }
        return false;    
    }
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = getIntent();
		if(intent.hasExtra("type") && intent.hasExtra("title")){
			currTitle = intent.getStringExtra("title");
			currType = intent.getStringExtra("type");
			startType = currType;
			startTitle = currTitle;
			actionBar.setTitle(currTitle);
			initdata();
			intent.removeExtra("type");
			intent.removeExtra("title");
		}
		
	}
	
	
	@Override
	public void pageChanged(int curPage, int numPerPage) {
		if(alllistdata != null && alllistdata.size() > 0){
			int last = curPage * numPerPage;
			if(last > alllistdata.size())last = alllistdata.size();
			listdata = alllistdata.subList((curPage - 1) * numPerPage, last);
			adapter.setListData(listdata);
			adapter.notifyDataSetInvalidated();
			listView.scrollTo(0, 0);
		}
	}
}
