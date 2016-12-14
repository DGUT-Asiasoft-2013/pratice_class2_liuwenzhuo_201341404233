package com.example.helloworld;

import java.text.SimpleDateFormat;

import com.example.helloworld.entity.Article;
import com.example.helloworld.fragments.AvatarView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FeedContentActivity extends Activity {

	private TextView tvTitle;
	private TextView tvTime;
	private TextView title;
	private TextView articleText;
	private AvatarView avatar;
	private TextView authorName;
	private Button btnComment;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final Article article = (Article) getIntent().getSerializableExtra("article");
		setContentView(R.layout.activity_feeds_content);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTime = (TextView) findViewById(R.id.tvTime);
		title = (TextView) findViewById(R.id.title);
		articleText = (TextView) findViewById(R.id.articleText);
		avatar = (AvatarView) findViewById(R.id.avatar);
		authorName = (TextView) findViewById(R.id.tvAuthor);
		btnComment = (Button) findViewById(R.id.btn_comment);

		tvTitle.setText(article.getTitle());
		tvTime.setText(sdf.format(article.getCreateDate()));
		avatar.load(article.getAuthorAvatar());
		title.setText(article.getTitle());
		articleText.setText(article.getText());
		authorName.setText(article.getAuthorName());
		articleText.setMovementMethod(ScrollingMovementMethod.getInstance());
		btnComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent itnt = new Intent(FeedContentActivity.this,CommentActivity.class);
				itnt.putExtra("article", article);
				startActivity(itnt);
			}
		});
	}

}
