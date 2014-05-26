package com.autodialer;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;


public class MainActivity extends Activity {

	// Declare Variables
	ViewPager viewPager;
	PagerAdapter adapter;
	String[] rank;
	String[] country;
	String[] population;
	int[] flag;
	UnderlinePageIndicator mIndicator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from viewpager_main.xml
		setContentView(R.layout.viewpager_main);

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
		viewPager = (ViewPager) findViewById(R.id.pager);
		// Pass results to ViewPagerAdapter Class
		adapter = new ViewPagerAdapter(MainActivity.this, rank, country,
				population, flag);
		// Binds the Adapter to the ViewPager
		viewPager.setAdapter(adapter);

		// ViewPager Indicator
	//	mIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
	//	mIndicator.setFades(false);
	//	mIndicator.setViewPager(viewPager);

       mIndicator = (UnderlinePageIndicator)findViewById(R.id.indicator);
     	mIndicator.setFades(false);
     //   mIndicator = indicator;
        mIndicator.setViewPager(viewPager);

      //  final float density = getResources().getDisplayMetrics().density;
     // / mIndicator.setBackgroundColor(0xFFCCCCCC);
      // mIndicator.setRadius(10 * density);
       // mIndicator.setPageColor(0x880000FF);
      //  mIndicator.setFillColor(0xFF888888);
      // mIndicator.setStrokeColor(0xFF000000);
       // mIndicator.setStrokeWidth(2 * density);
	}
}