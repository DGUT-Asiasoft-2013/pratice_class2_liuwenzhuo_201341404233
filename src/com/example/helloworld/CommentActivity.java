package com.example.helloworld;

import java.io.IOException;
import java.util.List;

import com.example.helloworld.adapter.CommentListAdapter;
import com.example.helloworld.entity.Article;
import com.example.helloworld.entity.Comment;
import com.example.helloworld.entity.Page;
import com.example.helloworld.entity.Server;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentActivity extends Activity {

	private ListView listView;
	private Article article;
	private EditText inputComment;
	private Button btnAddComment;
	private CommentListAdapter listAdapter;
	private List<Comment> data;
	String TAG = "OK";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		// loadMore = view
		// loadMore.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// loadMore();
		// }
		// });
		inputComment = (EditText) findViewById(R.id.input_comment);
		btnAddComment = (Button) findViewById(R.id.btn_add_comment);
		article = (Article) getIntent().getSerializableExtra("article");
		listView = (ListView) findViewById(R.id.list_comment);
		listAdapter = new CommentListAdapter(this, data);
		listView.setAdapter(listAdapter);
		getListView();

		btnAddComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				addComment();
			}
		});
	}

	private void getListView() {
		// TODO Auto-generated method stub
		OkHttpClient client = Server.getSharedClient();

		Request request = Server.requestBuilderWithApi("article/" + article.getId() + "/comments/").get().build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub

				final Page<Comment> data = new ObjectMapper().readValue(arg1.body().string(),
						new TypeReference<Page<Comment>>() {
						});

				Log.e(TAG, data.getContent().size() + "ddd");
					CommentActivity.this.data = data.getContent();
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try {
							// articleList = parseArticleList(jsonString);
							listAdapter.setData(CommentActivity.this.data);
							listAdapter.notifyDataSetChanged();
							// listAdapter.notifyDataSetInvalidated();
							// loadMore.setText("加载更多");
						} catch (final Exception e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									new AlertDialog.Builder(CommentActivity.this).setMessage(e.getMessage()).show();
									// loadMore.setText("加载更多");
								}
							});
						}
					}
				});
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void addComment() {
		// TODO Auto-generated method stub

		String comment = inputComment.getText().toString();

		OkHttpClient client = Server.getSharedClient();

		MultipartBody requestBody = new MultipartBody.Builder().addFormDataPart("text", comment).build();

		Request request = Server.requestBuilderWithApi("/article/" + article.getId() + "/comments").method("get", null)
				.post(requestBody).build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(CommentActivity.this, "发表评论成功", Toast.LENGTH_SHORT).show();
						getListView();
					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(CommentActivity.this, "发表评论失败", Toast.LENGTH_SHORT).show();
					}
				});

			}
		});
	}
}
