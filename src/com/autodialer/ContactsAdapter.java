package com.autodialer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactsAdapter extends ArrayAdapter<Contact> {

	Contact user;
	ArrayList<Contact> data = new ArrayList<Contact>();
	Context context;
	int layoutResID;
//	DailingMainActivity fragment;

public ContactsAdapter(Context context, int layoutResourceId,ArrayList<Contact> data) {
	super(context, layoutResourceId, data);
	
	// this.fragment = fragment;
	this.context=context;
	this.layoutResID=layoutResourceId;
	  this.data = data;
	    notifyDataSetChanged();

	// TODO Auto-generated constructor stub
}
 
@Override
public View getView(int position, View convertView, ViewGroup parent) {
	
	NewsHolder holder = null;
	   View row = convertView;
	    holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResID, parent, false);
            
            holder = new NewsHolder();
           
            holder.itemName = (TextView)row.findViewById(R.id.example_itemname);
            holder.itemPhone = (TextView)row.findViewById(R.id.example_itemphone);
            holder.itemEmail = (TextView)row.findViewById(R.id.example_itememail);
            holder.icon=(ImageView)row.findViewById(R.id.example_image);
            holder.button1=(Button)row.findViewById(R.id.swipe_button1);
           // holder.button2=(Button)row.findViewById(R.id.swipe_button2);
           // holder.button3=(Button)row.findViewById(R.id.swipe_button3);
            row.setTag(holder);
        }
        else
        {
            holder = (NewsHolder)row.getTag();
        }
        
        user = data.get(position);
        holder.itemName.setText(user.getName());
        holder.itemPhone.setText(user.getPhoneNumber());
        holder.itemEmail.setText(user.getEmail());
        holder.icon.setImageResource((R.drawable.contacts_icon));
      holder.button1.setTag(user.getID());
        holder.button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

			    AlertDialog.Builder adb = new AlertDialog.Builder(context);
			    adb.setTitle("Skip dialling?");
			    adb.setMessage("Are you sure you want to skip this contact from being auto dialled ?");
			    final int user_id = Integer.parseInt(v.getTag().toString());
			    adb.setNegativeButton("Cancel", null);
			    adb.setPositiveButton("Ok",
				    new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
						int which) {
					    // MyDataObject.remove(positionToRemove);
					    DatabaseHandler dBHandler = new DatabaseHandler(
						    context.getApplicationContext());
					    dBHandler.Delete_Contact(user_id);
					    Toast.makeText(context, "Contact removed from list to return it just refresh",Toast.LENGTH_SHORT).show();
					  //  fragment.onResume();
					    notifyDataSetChanged();

					}
				    });
			    adb.show();
			
				// TODO Auto-generated method stub
				
			}
		});
        
		// holder.button2.setOnClickListener(new View.OnClickListener() {
					
				//	@Override
				//	public void onClick(View v) {
						// TODO Auto-generated method stub
				//		Toast.makeText(context, "Button 2 Clicked",Toast.LENGTH_SHORT).show();
				//	}
				//});
		 
		// holder.button3.setOnClickListener(new View.OnClickListener() {
				
			//	@Override
			//	public void onClick(View v) {
					// TODO Auto-generated method stub
				//	Toast.makeText(context, "Button 3 Clicked",Toast.LENGTH_SHORT);
				//}
			//});
        
        
        return row;
	
}



static class NewsHolder{
	
	TextView itemName;
	TextView itemPhone;
	TextView itemEmail;
	ImageView icon;
	Button button1;
	Button button2;
	Button button3;
	}
	
	
}




