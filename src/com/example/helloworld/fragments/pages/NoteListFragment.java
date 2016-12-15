package com.example.helloworld.fragments.pages;

import java.io.IOException;
import java.util.List;

import com.example.helloworld.R;
import com.example.helloworld.adapter.CommentListAdapter;
import com.example.helloworld.entity.Article;
import com.example.helloworld.entity.Comment;
import com.example.helloworld.entity.Page;
import com.example.helloworld.entity.Server;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NoteListFragment extends Fragment {

	private View view;
	private Button btnRecevied;
	private Button btnSent;
	private ListView listView;
	private CommentListAdapter listAdapter;
	private List<Comment> data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_note_list, null);
			btnRecevied = (Button) view.findViewById(R.id.btn_recevied);
			btnSent = (Button) view.findViewById(R.id.btn_sent);
			listView = (ListView) view.findViewById(R.id.list_comments);
			listAdapter = new CommentListAdapter(getActivity(), data);
			listView.setAdapter(listAdapter);
			btnRecevied.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getCommentOfMe();
				}
			});
			btnSent.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getCommentByMe();
				}
			});
		}
		return view;

	}

	protected void getCommentByMe() {
		// TODO Auto-generated method stub
OkHttpClient client = Server.getSharedClient();
		
		Request request = Server.requestBuilderWithApi("me/comments").build();
		
		client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				final String jsonString = arg1.body().string();
				final List<Comment> data = new ObjectMapper().readValue(jsonString, new TypeReference<List<Comment>>() {
				});
				NoteListFragment.this.data = data;
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						listAdapter.setData(NoteListFragment.this.data);
						listAdapter.notifyDataSetChanged();
					}
				});
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void getCommentOfMe() {
		// TODO Auto-generated method stub
		OkHttpClient client = Server.getSharedClient();
		
		Request request = Server.requestBuilderWithApi("me/article/comments").build();
		
		client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				final String jsonString = arg1.body().string();
				final List<Comment> data = new ObjectMapper().readValue(jsonString, new TypeReference<List<Comment>>() {
				});
				NoteListFragment.this.data = data;
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						listAdapter.setData(NoteListFragment.this.data);
						listAdapter.notifyDataSetChanged();
					}
				});
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
