package com.autodialer;

import com.viewpagerindicator.UnderlinePageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Tutorial extends Fragment  {
	// Declare Variables
		ViewPager viewPager;
		PagerAdapter adapter;
		String[] rank;
		String[] country;
		String[] population;
		int[] flag;
		UnderlinePageIndicator mIndicator;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	    	setHasOptionsMenu(true);
	        if (container == null) {
	            // We have different layouts, and in one of them this
	            // fragment's containing frame doesn't exist.  The fragment
	            // may still be created from its saved state, but there is
	            // no reason to try to create its view hierarchy because it
	            // won't be displayed.  Note this is not needed -- we could
	            // just run the code below, where we would create and return
	            // the view hierarchy; it would just never be used.
	            return null;
	        }
	       // View mLinearLayout = mFadingHelper.createView(inflater);
	    //    if (mArguments != null){
	    ////        ImageView img = (ImageView) mLinearLayout.findViewById(R.id.image_header);
	      //      img.setImageResource(mArguments.getInt(ARG_IMAGE_RES));
	       // }
	        LinearLayout mLinearLayout =  (LinearLayout)inflater.inflate(R.layout.account,container, false);
	     // Generate sample data
			rank = new String[] { "1", "2", "3", "4", "5", "6", "7"  };

			country = new String[] { "China", "India", "United States",
					"Indonesia", "Brazil", "Pakistan", "Nigeria"};

			population = new String[] { "1,354,040,000", "1,210,193,422",
					"315,761,000", "237,641,326", "193,946,886", "182,912,000",
					"170,901,000"};

			flag = new int[] { R.drawable.tutorial_first, R.drawable.tutorial_second,
					R.drawable.tutorial_third, R.drawable.tutorial_fourth,
					R.drawable.tutorial_fifth, R.drawable.tutorial_sixth, R.drawable.tutorial_seventh
				 };

			// Locate the ViewPager in viewpager_main.xml
			viewPager = (ViewPager) mLinearLayout.findViewById(R.id.pager);
			// Pass results to ViewPagerAdapter Class
			adapter = new ViewPagerAdapter(getActivity(), rank, country,
					population, flag);
			// Binds the Adapter to the ViewPager
			viewPager.setAdapter(adapter);

			// ViewPager Indicator
			mIndicator = (UnderlinePageIndicator)mLinearLayout. findViewById(R.id.indicator);
			mIndicator.setFades(false);
			mIndicator.setViewPager(viewPager);
		      return mLinearLayout;

		}
}
