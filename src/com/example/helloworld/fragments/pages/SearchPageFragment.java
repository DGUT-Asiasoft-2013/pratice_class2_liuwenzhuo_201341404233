package com.example.helloworld.fragments.pages;

import java.io.IOException;
import java.util.List;

import com.example.helloworld.FeedContentActivity;
import com.example.helloworld.R;
import com.example.helloworld.adapter.FeedListAdapter;
import com.example.helloworld.entity.Article;
import com.example.helloworld.entity.Page;
import com.example.helloworld.entity.Server;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchPageFragment extends Fragment {

	private View view;
	private EditText inputSearch;
	private Button btnSearch;
	private FeedListAdapter listAdapter;
	private ListView listView;
	private List<Article> data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_search_page, null);
			inputSearch = (EditText) view.findViewById(R.id.input_search);
			btnSearch = (Button) view.findViewById(R.id.btn_search);
			listView = (ListView) view.findViewById(R.id.list_search);
			listAdapter = new FeedListAdapter(getActivity(), data);
			listView.setAdapter(listAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					onItemSelected(position);
				}
			});
			inputSearch.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					goSearch();
				}
			});
			btnSearch.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goSearch();
				}
			});
		}
		return view;

	}

	private void goSearch() {
		// TODO Auto-generated method stub
		OkHttpClient client = Server.getSharedClient();

		Request request = Server.requestBuilderWithApi("/article/s/" + inputSearch.getText()).build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				final Page<Article> data = new ObjectMapper().readValue(arg1.body().string(),
						new TypeReference<Page<Article>>() {
						});
				SearchPageFragment.this.data = data.getContent();

				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						listAdapter.setData(SearchPageFragment.this.data);
						listAdapter.notifyDataSetChanged();
					}
				});
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "获取数据异常", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	private void onItemSelected(int position) {
		// TODO Auto-generated method stub
		Intent itnt = new Intent(getActivity(), FeedContentActivity.class);
		itnt.putExtra("article", (Article) listAdapter.getItem(position));
		startActivity(itnt);
	}
}
