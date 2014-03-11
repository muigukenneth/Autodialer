package com.autodialer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;










import com.actionbarsherlock.view.Menu;
import com.cuubonandroid.sugaredlistanimations.GNowListAdapter;
import com.cuubonandroid.sugaredlistanimations.GPlusListAdapter;
import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;






import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
public class DailingNumbersAdapter extends GPlusListAdapter {

private PopupMenu popupMenu;

       private final static int ONE = 1;

       private final static int TWO = 2;

       private final static int THREE = 3;
       AlertDialog alert;

	
	 private  Context context;
	private static final long NOW = new Date().getTime();
	private Activity activity;
	private ArrayList<ItemParse> items;
	
	private ItemParse objBean;
	
//	ArrayList<HashMap<String, String>> data;
	//HashMap<String, String> resultp = new HashMap<String, String>();
	//ArrayList<HashMap<String, String>> data2;
	//HashMap<String, String> resultp2 = new HashMap<String, String>();
	//ArrayList<HashMap<String, String>> data3;
	//HashMap<String, String> resultp3 = new HashMap<String, String>();
	private int row;
	
	// private int mLastPosition = -1;
	 private int lastPosition = -1;
		ImageView comment;
		//ImageButton kulike;
		 
		  private Animation btnAnim;
		  private Animation layoutAnim;
		  private Animation kulike;
	 //private final List<String> urls = new ArrayList<String>();
	public DailingNumbersAdapter(Context context, SpeedScrollListener scrollListener, ArrayList<ItemParse> arrayList) {
		super(context,scrollListener,arrayList);
		//this.activity = act;
		this.context = context;
		//this.fragment = sizzlingEvents;
	//	this.listener = listener;
		//this.row = resource;
		this.items = arrayList;
	
		//this.itemstwo = arrayListtwo;
		//,List<ItemParsetest> arrayListtwo
		// Collections.addAll(urls, MainActivity.CITY);
		

	}
	

	


	@Override
	public View getRowView(final int position, View convertView, ViewGroup parent) {
		//View view = super.getView(position, convertView, parent);
		View view = convertView;
		ViewHolder holder=null;
		//LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
			
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

		if ((items == null) || ((position + 1) > items.size()) )
			return view;
		objBean = items.get(position);
		
		//  View viewcali = view;
	      
	//	objBeantwo = itemstwo.get(position);
//	String url =  "http://globegokartshows.co.ke/images/portfolio/"+objBean.getCity();
		// String url = getItem(position);
		holder.profilepic=(ImageView) view.findViewById(R.id.imagelogo);
		holder.email = (TextView) view.findViewById(R.id.textviewemail);
		
		holder.options=(ImageView) view.findViewById(R.id.imagethreedot);
	
		
	
		holder.name = (TextView) view.findViewById(R.id.name);
		holder.secondname = (TextView) view.findViewById(R.id.textviewsecondname);
		holder.phone = (TextView) view.findViewById(R.id.textviewphone);
		
		
		if (holder.name != null && null != objBean.getName()
				&& objBean.getName().trim().length() > 0) {
			holder.name.setText(Html.fromHtml(objBean.getName()));
		}
		if (holder.secondname != null && null != objBean.getSecondName()
				&& objBean.getSecondName().trim().length() > 0) {
			holder.name.setText(Html.fromHtml(objBean.getSecondName()));
			//holder.tvCity.setText(Html.fromHtml(objBean.getCity()));//(Html.fromHtml(objBean.getCity()));
		}
	
		if (holder.email != null && null != objBean.getEmail()
				&& objBean.getEmail().trim().length() > 0) {
			holder.email.setText(Html.fromHtml(objBean.getEmail()));
		}
		
		if (holder.phone != null && objBean.getPhone() > 0) { 
			holder.phone.setText(Html.fromHtml("" + objBean.getPhone()));
			
		}
		
	//	if (holder.image != null  
		//		) { 
			Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
	                R.drawable.ic_launcher);
	        Bitmap resized = Bitmap.createScaledBitmap(bm, 100, 100, true);
	        Bitmap conv_bm = getCircleBitmap(resized, 100);
	        holder.profilepic.setImageBitmap(conv_bm);
			//Picasso.with(context).load(MainActivity.CITY)
			//  .into(holder.image);
			//Picasso.with(context).load(objBean.getCity())
			//.placeholder(R.drawable.events_icon)
			//.error(R.drawable.helpicon)
			//.resize(403, 403)
           // .fit()
	       //    .centerCrop()
			//.placeholder(R.drawable.events_icon)
			//.into(holder.image);
	//	}
		
		view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
				//	Intent intent = new Intent(context, DemoActivity.class);
				//	objBean = items.get(position);
					//int =("" + objBean.getAge());
				//	intent.putExtra("eventname", (objBean.getName()));
	                // Pass all data country
	             //   intent.putExtra("eventdescription", (objBean.getBirthdate()));
	                // Pass all data population
	             //  intent.putExtra("eventcash",(objBean.getGender()));
	                // Pass all data flag
	              //  intent.putExtra("eventvenue",( objBean.getAge()));
	              //  intent.putExtra("eventpic", objBean.getCity());
				//context.startActivity(intent);
				}
		
	});
		

		
		
		
		return view;
	}
	
	public class ViewHolder {
		public TextView id;TextView name, phone, secondname, email;
		 ImageView profilepic; ImageView options;
	}
	
	 
//	
	public Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

	    final int color = 0xffff0000;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    final RectF rectF = new RectF(rect);

	    paint.setAntiAlias(true);
	    paint.setDither(true);
	    paint.setFilterBitmap(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawOval(rectF, paint);

	    paint.setColor(Color.BLUE);
	    paint.setStyle(Paint.Style.STROKE);
	    paint.setStrokeWidth((float) 4);
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);

	    return output;
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
