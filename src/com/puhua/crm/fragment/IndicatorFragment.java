package com.puhua.crm.fragment;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.puhua.crm.R;
import com.puhua.crm.base.BaseFragment;
import com.puhua.crm.util.CommonUtils;
import com.puhua.crm.view.TitleIndicator;

import android.R.mipmap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

@SuppressLint("NewApi") public abstract class IndicatorFragment extends BaseFragment implements OnPageChangeListener {
	private static final String TAG = "DxFragmentActivity";
	public static final String EXTRA_TAB = "tab";
	public static final String EXTRA_QUIT = "extra.quit";
	protected int mCurrentTab = 0;
	protected int mLastTab = -1;

	// 存放选项卡信息的列表
	protected ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

	// viewpager adapter
	protected MyAdapter myAdapter = null;

	// viewpager
	protected ViewPager mPager;

	// 选项卡控件
	protected TitleIndicator mIndicator;

	public TitleIndicator getIndicator() {
		return mIndicator;
	}
	public class MyAdapter extends FragmentPagerAdapter {
		ArrayList<TabInfo> tabs = null;
		Context context = null;

		public MyAdapter(Context context, FragmentManager fm, ArrayList<TabInfo> tabs) {
			super(fm);
			this.tabs = tabs;
			this.context = context;
		}

		@Override
		public Fragment getItem(int pos) {
			Fragment fragment = null;
			if (tabs != null && pos < tabs.size()) {
				TabInfo tab = tabs.get(pos);
				if (tab == null)
					return null;
				fragment = tab.createFragment();
			}
			return fragment;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public int getCount() {
			if (tabs != null && tabs.size() > 0)
				return tabs.size();
			return 0;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabInfo tab = tabs.get(position);
			Fragment fragment = (Fragment) super.instantiateItem(container, position);
			tab.fragment = fragment;
			return fragment;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(getMainViewResId(), null);
		initViews(view);
		// 设置viewpager内部页面之间的间距
	//	mPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin_width));
		// 设置viewpager内部页面间距的drawable
	//	mPager.setPageMarginDrawable(R.color.page_viewer_margin_color);
		return view;
	}

	private final void initViews(View view) {
		// 这里初始化界面
		mCurrentTab = supplyTabs(mTabs);
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			mCurrentTab = intent.getIntExtra(EXTRA_TAB, mCurrentTab);
		}
		Log.d(TAG, "mTabs.size() == " + mTabs.size() + ", cur: " + mCurrentTab);
		myAdapter = new MyAdapter(getActivity(), getChildFragmentManager(), mTabs);

		mPager = (ViewPager) view.findViewById(R.id.pager);
		mPager.setAdapter(myAdapter);
		mPager.setOnPageChangeListener(this);
		mPager.setOffscreenPageLimit(mTabs.size());
		
		mIndicator = (TitleIndicator) view.findViewById(R.id.pagerindicator);
		InitRes(mIndicator);
			
			mIndicator.init(mCurrentTab, mTabs, mPager);
		
		
		mPager.setCurrentItem(mCurrentTab);
		mLastTab = mCurrentTab;
	}

	public void InitRes(TitleIndicator mIndicator2) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 添加一个选项卡
	 * 
	 * @param tab
	 */
	public void addTabInfo(TabInfo tab) {
		mTabs.add(tab);
		myAdapter.notifyDataSetChanged();
	}


	/**
	 * 从列表添加选项卡
	 * 
	 * @param tabs
	 */
	public void addTabInfos(ArrayList<TabInfo> tabs) {
		mTabs.addAll(tabs);
		myAdapter.notifyDataSetChanged();
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		mIndicator.onScrolled((mPager.getWidth() + mPager.getPageMargin()) * position + positionOffsetPixels);
	}

	@Override
	public void onPageSelected(int position) {
		mIndicator.onSwitched(position);
		mCurrentTab = position;
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		CommonUtils.hideSoftInput(context);
		if (state == ViewPager.SCROLL_STATE_IDLE) {
			mLastTab = mCurrentTab;
		}
	}

	protected TabInfo getFragmentById(int tabId) {
		if (mTabs == null)
			return null;
		for (int index = 0, count = mTabs.size(); index < count; index++) {
			TabInfo tab = mTabs.get(index);
			if (tab.getId() == tabId) {
				return tab;
			}
		}
		return null;
	}

	/**
	 * 跳转到任意选项卡
	 * 
	 * @param tabId
	 *            选项卡下标
	 */
	public void navigate(int tabId) {
		for (int index = 0, count = mTabs.size(); index < count; index++) {
			if (mTabs.get(index).getId() == tabId) {
				mPager.setCurrentItem(index);
			}
		}
	}


	/**
	 * 返回layout id
	 * 
	 * @return layout id
	 */
	protected int getMainViewResId() {
		return R.layout.activity_home;
	}

	/**
	 * 在这里提供要显示的选项卡数据
	 */
	protected abstract int supplyTabs(List<TabInfo> tabs);


	/**
	 * 单个选项卡类，每个选项卡包含名字，图标以及提示（可选，默认不显示）
	 */
	public static class TabInfo implements Parcelable {

		private int id;
		private int icon;
		private String name = null;
		public boolean hasTips = false;
		public Fragment fragment = null;
		public boolean notifyChange = false;
		@SuppressWarnings("rawtypes")
		public Class fragmentClass = null;

		@SuppressWarnings("rawtypes")
		public TabInfo(int id, String name, Class clazz) {
			this(id, name, 0, clazz);
		}

		@SuppressWarnings("rawtypes")
		public TabInfo(int id, String name, boolean hasTips, Class clazz) {
			this(id, name, 0, clazz);
			this.hasTips = hasTips;
		}

		@SuppressWarnings("rawtypes")
		public TabInfo(int id, String name, int iconid, Class clazz) {
			super();

			this.name = name;
			this.id = id;
			icon = iconid;
			fragmentClass = clazz;
		}

		public TabInfo(Parcel p) {
			this.id = p.readInt();
			this.name = p.readString();
			this.icon = p.readInt();
			this.notifyChange = p.readInt() == 1;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setIcon(int iconid) {
			icon = iconid;
		}

		public int getIcon() {
			return icon;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Fragment createFragment() {
			if (fragment == null) {
				Constructor constructor;
				try {
					constructor = fragmentClass.getConstructor(new Class[0]);
					fragment = (Fragment) constructor.newInstance(new Object[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return fragment;
		}

		public static final Parcelable.Creator<TabInfo> CREATOR = new Parcelable.Creator<TabInfo>() {
			public TabInfo createFromParcel(Parcel p) {
				return new TabInfo(p);
			}

			public TabInfo[] newArray(int size) {
				return new TabInfo[size];
			}
		};

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel p, int flags) {
			p.writeInt(id);
			p.writeString(name);
			p.writeInt(icon);
			p.writeInt(notifyChange ? 1 : 0);
		}

	}

}
