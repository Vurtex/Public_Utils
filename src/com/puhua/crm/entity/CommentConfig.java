package com.puhua.crm.entity;

/**
 * @author acer 数据请求的部分参数
 */
public class CommentConfig {
	public static enum Type {
		PUBLIC("public"), REPLY("reply");
		@SuppressWarnings("unused")
		private String value;
		private Type(String value) {
			this.value = value;
		}
	}

	public int circlePosition;// 子项的位置
	public int commentPosition;// 评论的位置
	public Type commentType;// 评论的类型：回复/评论
	private String workId;
	private String commentId;
	private String praise;
	private ReplyUser replyuser;

	public String getWorkId() {
		if (workId != null)
			return workId;
		else
			return "";
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public ReplyUser getReplyuser() {
		return replyuser;

	}

	public void setReplyuser(ReplyUser replyuser) {
		this.replyuser = replyuser;
	}

	public int getCirclePosition() {
		return circlePosition;
	}

	public void setCirclePosition(int circlePosition) {
		this.circlePosition = circlePosition;
	}

	public int getCommentPosition() {
		return commentPosition;
	}

	public void setCommentPosition(int commentPosition) {
		this.commentPosition = commentPosition;
	}

	public Type getCommentType() {
		return commentType;
	}

	public void setCommentType(Type commentType) {
		this.commentType = commentType;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getPraise() {
		return praise;
	}

	public void setPraise(String praise) {
		this.praise = praise;
	}

}
