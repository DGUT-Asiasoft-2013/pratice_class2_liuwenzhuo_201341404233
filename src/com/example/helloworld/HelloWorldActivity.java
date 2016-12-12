package com.example.helloworld;

import com.example.helloworld.fragments.MainTabbarFragment;
import com.example.helloworld.fragments.MainTabbarFragment.OnTabSelectedListener;
import com.example.helloworld.fragments.pages.FeedsListFragment;
import com.example.helloworld.fragments.pages.MyProfileFragment;
import com.example.helloworld.fragments.pages.NoteListFragment;
import com.example.helloworld.fragments.pages.SearchPageFragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class HelloWorldActivity extends Activity {

	FeedsListFragment contentFeedList = new FeedsListFragment();
	NoteListFragment contentNoteList = new NoteListFragment();
	MyProfileFragment contentMyProfile = new MyProfileFragment();
	SearchPageFragment contentSearchPage = new SearchPageFragment();
	MainTabbarFragment tabbar = new MainTabbarFragment();
	Fragment frags[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_helloworld);
		frags = new Fragment[] { contentFeedList, contentNoteList, contentMyProfile, contentSearchPage };
		tabbar = (MainTabbarFragment) getFragmentManager().findFragmentById(R.id.frag_tabbar);
		tabbar.setOnTabSelectedListener(new OnTabSelectedListener() {

			@Override
			public void onTabSelected(int index) {
				// TODO Auto-generated method stub
				changeContentFragment(index);
			}
		});
		tabbar.setSelectedItem(0);
	}

	void changeContentFragment(int index) {
		Fragment newFrag = null;

		switch (index) {
		case 0:
			newFrag = contentFeedList;
			break;
		case 1:
			newFrag = contentNoteList;
			break;
		case 2:
			newFrag = contentSearchPage;
			break;
		case 3:
			newFrag = contentMyProfile;
			break;

		default:
			break;
		}

		if (newFrag == null) {
			return;
		}
		getFragmentManager().beginTransaction().replace(R.id.content, newFrag).commit();
	}

}
