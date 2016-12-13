package com.example.helloworld.adapter;

import java.util.List;

import com.example.helloworld.R;
import com.example.helloworld.entity.Article;
import com.example.helloworld.fragments.AvatarView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FeedListAdapter extends BaseAdapter {

	private Context context;
	private List<Article> articleList;
	

	public FeedListAdapter(Context context, List<Article> articleList) {
		this.context = context;
		this.articleList = articleList;
	}

	@Override
	public int getCount() {
		return articleList == null ? 0 : articleList.size();
	}

	@Override
	public Object getItem(int position) {
		return articleList == null ? null : articleList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.listview_item_feed, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			holder.avatar = (AvatarView) convertView.findViewById(R.id.avatar);
			holder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Article article = articleList.get(position);
		holder.tvTitle.setText(article.getTitle());
		holder.tvTime.setText(article.getCreateDate().toString());
		holder.avatar.load(article.getAuthorAvatar());
		holder.tvAuthor.setText(article.getAuthorName());
		convertView.setTag(holder);
		return convertView;
	}

	private static class ViewHolder {
		TextView tvTitle;
		TextView tvTime;
		AvatarView avatar;
		TextView tvAuthor;
	}

	public void setData(List<Article> articleList) {
		this.articleList = articleList;
	}
}
