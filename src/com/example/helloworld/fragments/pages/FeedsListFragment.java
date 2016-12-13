package com.example.helloworld.fragments.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.helloworld.R;
import com.example.helloworld.adapter.FeedListAdapter;
import com.example.helloworld.entity.Article;
import com.example.helloworld.entity.Page;
import com.example.helloworld.entity.Server;
import com.example.helloworld.fragments.AvatarView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FeedsListFragment extends Fragment {
	private View view;
	private ListView listView;
	private FeedListAdapter listAdapter;
	private Integer page;
	private List<Article> data;
	private AvatarView avatar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_feed_list, null);
			listView = (ListView) view.findViewById(R.id.list);
			listAdapter = new FeedListAdapter(getActivity(), data);
			listView.setAdapter(listAdapter);
			avatar = (AvatarView) view.findViewById(R.id.avatar);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					onItemSelected(position);
				}

			});
		}
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		OkHttpClient client = Server.getSharedClient();

		Request request = Server.requestBuilderWithApi("feeds").get().build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				// final String jsonString = arg1.body().string();
				Page<Article> data = new ObjectMapper().readValue(arg1.body().string(),
						new TypeReference<Page<Article>>() {
						});
				page = data.getNumber();
				FeedsListFragment.this.data = data.getContent();
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try {
							// articleList = parseArticleList(jsonString);
							 listAdapter.setData(FeedsListFragment.this.data);
							// listAdapter.notifyDataSetChanged();
							listAdapter.notifyDataSetInvalidated();
						} catch (final Exception e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							getActivity().runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									new AlertDialog.Builder(getActivity()).setMessage(e.getMessage()).show();
								}
							});
						}
					}
				});
			}

			@Override
			public void onFailure(Call arg0, final IOException e) {
				// TODO Auto-generated method stub
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						new AlertDialog.Builder(getActivity()).setMessage(e.getMessage()).show();
					}
				});

			}
		});
	}

	// public List<Article> parseArticleList(String jsonString) throws
	// Exception, IOException {
	// ObjectMapper mapper = new ObjectMapper();
	// Page<Article>page=mapper.readValue(jsonString, new TypeReference() {
	// });
	// JavaType javaType =
	// mapper.getTypeFactory().constructParametricType(Page.class,
	// Article.class);
	// Page<Article> page = (Page<Article>) mapper.readValue(jsonString,
	// javaType);
	// return page.getContent();
	// }

	protected void onItemSelected(int position) {
		// TODO Auto-generated method stub

	}
}
