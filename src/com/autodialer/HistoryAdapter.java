package com.autodialer;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autodialer.DailingNumbersAdapter.ViewHolder;
import com.cuubonandroid.sugaredlistanimations.GPlusListAdapter;
import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;

public class HistoryAdapter extends GPlusListAdapter {
	private List<HistoryParse> items;
	private HistoryParse objBean;
	
	public HistoryAdapter(Context context, SpeedScrollListener scrollListener, List<HistoryParse>arrayList) {
		super(context,scrollListener,arrayList);
		//this.activity = act;
		this.context = context;
		//this.fragment = sizzlingEvents;
	//	this.listener = listener;
		//this.row = resource;
		this.items = arrayList;
	}
	@Override
	public View getRowView(final int position, View convertView, ViewGroup parent) {
		//View view = super.getView(position, convertView, parent);
		View view = convertView;
		ViewHolder holder=null;
		//LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.row_comment, parent, false);
			
			/* TranslateAnimation animation = null;
		        if (position > mLastPosition) {
		            animation = new TranslateAnimation(
		                Animation.RELATIVE_TO_SELF,
		                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
		                Animation.RELATIVE_TO_SELF, 1.0f,
		                Animation.RELATIVE_TO_SELF, 0.0f);

		            animation.setDuration(600);
		            view.startAnimation(animation);
		            mLastPosition = position;
		        }*/
			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
	//	return view;
		objBean = items.get(position);
		
		holder.Name = (TextView) view.findViewById(R.id.namehistory);
		holder.Note = (TextView) view.findViewById(R.id.notehistory);
		holder.Time = (TextView) view.findViewById(R.id.timehistory);
		if (holder.Name != null && null != objBean.getName()
				&& objBean.getName().trim().length() > 0) {
			holder.Name.setText(Html.fromHtml(objBean.getName()));
		}
		if (holder.Note != null && null != objBean.getNote()
				&& objBean.getNote().trim().length() > 0) {
			holder.Note.setText(Html.fromHtml(objBean.getNote()));
		}
		if (holder.Time != null && null != objBean.getTime()
				&& objBean.getTime().trim().length() > 0) {
			holder.Time.setText(Html.fromHtml(objBean.getTime()));
		}
		//  }
		return view;
	}
	public class ViewHolder {
		public TextView Name;TextView Note;TextView Time;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return 4;
	return items.size();
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
