package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.entity.Article;
import com.example.helloworld.entity.Server;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentActivity extends Activity {

	private EditText inputComment;
	private Button btnAddComment;
	private Article article;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		inputComment = (EditText) findViewById(R.id.input_comment);
		btnAddComment = (Button) findViewById(R.id.btn_add_comment);
		article = (Article) getIntent().getSerializableExtra("article");
		btnAddComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				addComment();
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
						finish();
						Toast.makeText(CommentActivity.this, "发表评论成功", Toast.LENGTH_SHORT).show();
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
						finish();
						Toast.makeText(CommentActivity.this, "发表评论失败", Toast.LENGTH_SHORT).show();
					}
				});

			}
		});
	}
}
