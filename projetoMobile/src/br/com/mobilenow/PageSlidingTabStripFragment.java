package br.com.mobilenow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.mobilenow.fragment.CaronaMapFragment;

import com.actionbarsherlock.app.SherlockFragment;
import com.astuetz.PagerSlidingTabStrip;

public class PageSlidingTabStripFragment extends Fragment{
	
	public static final String TAG = PageSlidingTabStripFragment.class.getSimpleName();

	public static PageSlidingTabStripFragment newInstance() {
		return new PageSlidingTabStripFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.pager, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view
				.findViewById(R.id.tabs);
		ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
		MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
		
		pager.setAdapter(adapter);
		
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		
		tabs.setViewPager(pager);

	}
	
	private static String makeFragmentName(int viewId, int index) {
	     return "android:switcher:" + viewId + ":" + index;
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
     	}		
		

	       private final String[] TITULO = { 	"Carona", "Estacionamento"};


			@Override
			public CharSequence getPageTitle(int position) {
				return TITULO[position];
			}

			@Override
			public int getCount() {
				return TITULO.length;
			}

		@Override
		public Fragment getItem(int position) {
             
			if (position==0) {
				
				//Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.pager, position));
				//System.out.println("*********** fragmentByTag = " + fragmentByTag);
				return CaronaMapFragment.newInstance(position, "Fragment with menu");
				
			} else 
				return null; //SimpleCardFragment.newInstance(position);
			}
	 } // fim classe interna

}

