package com.example.helloworld.entity;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

	int id;
	User author;
	Date createDate;
	Date editDate;
	String text;
	String comment;


	public String getAuthorName() {
		return author.getName();
	}

	public void setAuthorName(String authorName) {
		author.setName(authorName);;
	}

	public String getAuthorAvatar() {
		return author.getAvatar();
	}

	public void setAuthorAvatar(String authorAvatar) {
		author.setAvatar(authorAvatar);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}