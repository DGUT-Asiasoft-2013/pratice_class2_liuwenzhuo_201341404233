package com.example.helloworld.fragments.pages;

import com.example.helloworld.FeedContentActivity;
import com.example.helloworld.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FeedsListFragment extends Fragment {

	private View view;
	private ListView listView;
	private String[] arrList = new String[34];
	private int i;

	private void addArrList() {
		for (i = 0; i < arrList.length; i++) {
			arrList[i] = new String(i + "");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_feed_list, null);

			listView = (ListView) view.findViewById(R.id.list);
			listView.setAdapter(listAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					onItemSelected(position);
				}
			});
			addArrList();
		}

		return view;
	}
	
	private void onItemSelected(int position){
		String text = arrList[position];
		Intent itnt = new Intent(getActivity(),FeedContentActivity.class);
		itnt.putExtra("text", text);
		startActivity(itnt);
	}

	BaseAdapter listAdapter = new BaseAdapter() {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = null;

			if (convertView == null) {

				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(android.R.layout.simple_list_item_1, null);
			} else {
				view = convertView;
			}
			TextView text1 = (TextView) view.findViewById(android.R.id.text1);
			text1.setText("THIS IS ROW" + arrList[position]);
			return view;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arrList[position];
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrList == null ? 0 : arrList.length;
		}
	};
}
