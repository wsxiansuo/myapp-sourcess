package com.sxs.app.common;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxs.app.data.BraintwisterVO;
import com.sxs.app.lifehumor.R;


public class BraintwisterAdapter extends BaseAdapter {
	
	public boolean isOffLine = false;
	private LayoutInflater mInflater;
	private List<BraintwisterVO> listData = null; 
	public List<BraintwisterVO> getListData() {
		return listData;
	}

	public void setListData(List<BraintwisterVO> listData) {
		this.listData = listData;
//		this.notifyDataSetChanged();
	}

	public BraintwisterAdapter(Context context){
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		if (listData != null)
		{
			return listData.size();
		}
		return 0;
	}

	@Override
	public BraintwisterVO getItem(int position) {
		if( listData == null || listData.size() <= position)
		{
			return null;
		}
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
        if (convertView == null) {
            holder=new ViewHolder(); 
            convertView = mInflater.inflate(R.layout.bt_list_item, null);
            holder.questionTV = (TextView) convertView.findViewById(R.id.bt_question_tv);
            holder.answerTV = (TextView) convertView.findViewById(R.id.bt_answer_tv);
            holder.tagTV = (ImageView) convertView.findViewById(R.id.bt_tag_iv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        BraintwisterVO item = getItem(position);
        if( null != item ){
        	holder.questionTV.setText(Html.fromHtml("<font color=\"#68aca5\">" + (position + 1) + "��" + "</font>" + item.question) );
        	holder.answerTV.setText(Html.fromHtml(item.answer));
        	holder.tagTV.setImageResource(item.isTag ? R.drawable.star_full:R.drawable.star_empty);
        }
        return convertView;
	}
	final class ViewHolder{
	   public TextView questionTV;  //����
	   public TextView answerTV;  //��
	   public ImageView tagTV;   //�ղ�
   }
}
