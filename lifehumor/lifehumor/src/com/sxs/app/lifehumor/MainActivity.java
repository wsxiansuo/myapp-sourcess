package com.sxs.app.lifehumor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pop.doujin.android.DPManager;
import pop.doujin.android.pop.PopManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sxs.app.data.DBManager;
import com.sxs.app.data.MenuItemVO;
import com.sxs.app.data.UserDataModel;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import drop.doujin.android.DDManager;
import drop.doujin.android.rp.DropManager;

public class MainActivity extends Activity {
 
	@ViewInject(R.id.help_btn)   Button helpBtn;
	
    private boolean mode = false;//true:测试 false发布
	private int dbVersion = 1;
	private GridView menuGrid;
	private DetailPopupWindow popupWindow;
	
	private int[] menu_image_array = { R.drawable.menu1,
			R.drawable.menu2, R.drawable.menu3,R.drawable.menu4,R.drawable.menu5,R.drawable.menu6,R.drawable.menu7};
	private List<MenuItemVO> listData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UmengUpdateAgent.update(this);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this); 
        UserDataModel.instance().mgr = new DBManager(MainActivity.this);
        if(UserDataModel.instance().mgr.getDbVersion() < dbVersion){
    		UserDataModel.instance().mgr.upDatabase();  
    		Log.i("update", "数据库执行了更新---------------");
    	}
        initCompent();
        DDManager.getInstance(this).init("5afdec9a25c44ce32fb2851bf5642e7c", mode);
        DDManager.getInstance(this).initLayout("#41cec6", 0);
        DropManager.getInstance(this).start(this);
        DPManager.getInstance(this).init("5afdec9a25c44ce32fb2851bf5642e7c", mode);
        PopManager.getInstance(this).start(this,1);
        
        helpBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popWithUrl("file:///android_asset/about.html","应用说明");
			}
		});
	}
	private void popWithUrl(String url,String title){
    	if(popupWindow == null){
    		popupWindow = new DetailPopupWindow(MainActivity.this);  
    	}
    	popupWindow.loadUrl(url,title);//file:///android_asset/www/index.html
        //显示窗口  
    	popupWindow.showAtLocation(helpBtn, Gravity.CENTER, 0, 0); //设置layout在PopupWindow中显示的位置  
    }
	private void initCompent(){
		
		
		
		menuGrid = (GridView)findViewById(R.id.gridview);
		menuGrid.setAdapter(getMenuAdapter(menu_image_array));
		/** 监听menu选项 **/
		menuGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MenuItemVO item = listData.get(arg2);
				if(item != null){
					Intent intent = new Intent();
					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					intent.putExtra("type", item.id + "");
					intent.putExtra("title", item.type);
					intent.setClass(MainActivity.this, HumorListActivity.class);
					startActivity(intent);
				}
			}
		});
	}
	private SimpleAdapter getMenuAdapter(int[] imageResourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		listData = UserDataModel.instance().mgr.queryTypeList();
		
		for (int i = 0; i < listData.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageResourceArray[i]);
			map.put("itemText", listData.get(i).type);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(this, data,
				R.layout.item_menu, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });
		return simperAdapter;
	}

		@Override
	    public boolean onKeyDown(int keyCode, KeyEvent event)  {
	        switch (keyCode) {
	        case KeyEvent.KEYCODE_BACK:     
	            DropManager.getInstance(MainActivity.this).show(MainActivity.this);
	            break;
	        default:
	            break;
	        }
	        return false;    
	    }

		
		@Override
		public void onSaveInstanceState(Bundle savedInstanceState){
			super.onSaveInstanceState(savedInstanceState);
//			Log.v(TAG, "MainAcitivity 将要被销毁");
			// 这里什么都不做，不要调用父类，不然重新启动会报错
		}
		@Override
		public void onResume() {
	    	super.onResume();
	    	MobclickAgent.onResume(this);
	    }
		@Override
		public void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
		}
		@Override  
	    protected void onDestroy() {  
	        super.onDestroy();  
	        //应用的最后一个Activity关闭时应释放DB  
	        UserDataModel.instance().mgr.closeDB();  
	    }
}
