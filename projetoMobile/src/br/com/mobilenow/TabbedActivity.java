package br.com.mobilenow;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import br.com.mobilenow.adapter.MenuListAdapter;
import br.com.mobilenow.fragment.CaronaMapFragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.astuetz.PagerSlidingTabStrip;

public class TabbedActivity extends SherlockFragmentActivity {
	
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;		
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_tabs);
        setProgressBarIndeterminate(false);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		
       pager.setAdapter(adapter);
		
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setViewPager(pager);
		
		

	} // fim metodo onCreate
	
	
	
	private static String makeFragmentName(int viewId, int index) {
	     return "android:switcher:" + viewId + ":" + index;
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITULO = { 	"Carona", 
											};

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

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
				
				Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.pager, position));
				System.out.println("*********** fragmentByTag = " + fragmentByTag);
				return CaronaMapFragment.newInstance(position, "Fragment with menu");
				
			} else 
				return null; //SimpleCardFragment.newInstance(position);
			}
		}

	}


