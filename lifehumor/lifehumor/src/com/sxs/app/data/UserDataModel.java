package com.sxs.app.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class UserDataModel {
	
	public DBManager mgr;
	private List<BraintwisterVO> alllistdata;
	/**
	 * @return the alllistdata
	 */
	public List<BraintwisterVO> getSearchlistdata(String key) {
		if(alllistdata == null){
			alllistdata = mgr.querySearchList("");
		}
		if(key.isEmpty()){
			return alllistdata;
		}else{
			List<BraintwisterVO> list= new ArrayList<BraintwisterVO>();
			for (BraintwisterVO item : alllistdata) {
				if(item.question.indexOf(key) > -1 || item.answer.indexOf(key) > -1){
					list.add(item);
				}
			}
			return list;
		}
	}
	
	public void sortList(List<BraintwisterVO> list){
		Comparator comp = new Comparator() {  
	        public int compare(Object o1, Object o2) {  
	        	BraintwisterVO p1 = (BraintwisterVO) o1;  
	        	BraintwisterVO p2 = (BraintwisterVO) o2;  
	        	if(p1==null||p1.date==null || p2==null || p2.date == null) return 1;
	        	return p2.date.compareTo(p1.date);
	        }  
	    };  
	    Collections.sort(list, comp);  
		
	}
	public void handleList(List<BraintwisterVO> list){
		sortList(list);
		String today = getDate();
	    if(!today.equals(list.get(0).date)){
	    	for(int i=0;i<20;i++){
	    		int position = (int) Math.round((list.size() - 1) * Math.random());
	    		BraintwisterVO item = list.get(position);
	    		if(item != null){
	    			item.date = today;
		    		mgr.updateDate(item.id + "", today);
	    		}	
	    	}
	    	sortList(list);
	    }
	}
	public String getDate() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	
	
	
	private static UserDataModel _instance;
	public static UserDataModel instance()
	{
		if(_instance == null)
		{
			_instance = new UserDataModel();
		}
		return _instance;
	}
}
