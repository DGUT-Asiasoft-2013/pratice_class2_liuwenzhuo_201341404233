package com.example.helloworld;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import com.example.helloworld.entity.Article;
import com.example.helloworld.entity.Server;
import com.example.helloworld.fragments.AvatarView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FeedContentActivity extends Activity {

	private TextView tvTitle;
	private TextView tvTime;
	private TextView title;
	private TextView articleText;
	private AvatarView avatar;
	private TextView authorName;
	private Button btnComment;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private Button btnLike;
	private TextView tvCountLike;
	private Article article;
	private Integer countLike;
	boolean isLike = false;
	boolean flag = false;
	String TAG = "OK";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		article = (Article) getIntent().getSerializableExtra("article");
		setContentView(R.layout.activity_feeds_content);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTime = (TextView) findViewById(R.id.tvTime);
		title = (TextView) findViewById(R.id.title);
		articleText = (TextView) findViewById(R.id.articleText);
		avatar = (AvatarView) findViewById(R.id.avatar);
		authorName = (TextView) findViewById(R.id.tvAuthor);
		btnComment = (Button) findViewById(R.id.btn_comment);
		btnLike = (Button) findViewById(R.id.btn_like);
		tvCountLike = (TextView) findViewById(R.id.tvCountLike);

		tvTitle.setText(article.getTitle());
		tvTime.setText(sdf.format(article.getCreateDate()));
		avatar.load(article.getAuthorAvatar());
		title.setText(article.getTitle());
		articleText.setText(article.getText());
		authorName.setText(article.getAuthorName());
		articleText.setMovementMethod(ScrollingMovementMethod.getInstance());
		btnLike.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clickLike();
			}
		});
		btnComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent itnt = new Intent(FeedContentActivity.this, CommentActivity.class);
				itnt.putExtra("article", article);
				startActivity(itnt);
			}
		});

		checkLike();
		getCountLike();
	}

	private void getCountLike() {
		// TODO Auto-generated method stub
		OkHttpClient client = Server.getSharedClient();
		
		Request request = Server.requestBuilderWithApi("/article/"+article.getId()+"/likes").build();
		
		client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				String count = arg1.body().string();
				countLike = Integer.parseInt(count);
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						tvCountLike.setText(countLike+"");
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
						Toast.makeText(FeedContentActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	private void clickLike() {
		// TODO Auto-generated method stub
		if (isLike) {
			flag = false;
		} else {
			flag = true;
		}

		OkHttpClient client = Server.getSharedClient();

		MultipartBody requestBody = new MultipartBody.Builder().addFormDataPart("likes", flag + "").build();

		Request request = Server.requestBuilderWithApi("/article/" + article.getId() + "/likes").method("get", null)
				.post(requestBody).build();

		client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				String count = arg1.body().string();
				countLike = Integer.parseInt(count);
				
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						tvCountLike.setText(countLike+"");
						isLike = !isLike;
						if(isLike){
							btnLike.setText("已点赞");
						}else {
							btnLike.setText("点赞");
						}
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
						Toast.makeText(FeedContentActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		
	}

	private void checkLike() {
		// TODO Auto-generated method stub
		OkHttpClient client = Server.getSharedClient();

		Request request = Server.requestBuilderWithApi("/article/" + article.getId() + "/isliked").build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				String temp = arg1.body().string();
				Log.e(TAG,temp);
				isLike = Boolean.parseBoolean(temp);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (isLike) {
							btnLike.setText("已点赞");
						} else {
							btnLike.setText("点赞");
						}
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
						Toast.makeText(FeedContentActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
}
