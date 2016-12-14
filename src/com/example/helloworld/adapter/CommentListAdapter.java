package com.example.helloworld.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.example.helloworld.R;
import com.example.helloworld.entity.Comment;
import com.example.helloworld.fragments.AvatarView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentListAdapter extends BaseAdapter {

	private Context context;
	private List<Comment> commentList;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public CommentListAdapter(Context context, List<Comment> commentList) {
		this.context = context;
		this.commentList = commentList;
	}

	@Override
	public int getCount() {
		return commentList == null ? 0 : commentList.size();
	}

	@Override
	public Object getItem(int position) {
		return commentList == null ? null : commentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.listview_item_comment, null);
			holder = new ViewHolder();
			holder.tvText = (TextView) convertView.findViewById(R.id.tvText);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			holder.avatar = (AvatarView) convertView.findViewById(R.id.avatar);
			holder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Comment comment = commentList.get(position);
		holder.tvText.setText(comment.getText());
		holder.tvTime.setText(sdf.format(comment.getCreateDate()));
		holder.avatar.load(comment.getAuthorAvatar());
		holder.tvAuthor.setText(comment.getAuthorName());
		convertView.setTag(holder);
		return convertView;
	}

	private static class ViewHolder {
		TextView tvText;
		TextView tvTime;
		AvatarView avatar;
		TextView tvAuthor;
	}

	public void setData(List<Comment> commentList) {
		this.commentList = commentList;
	}
}
