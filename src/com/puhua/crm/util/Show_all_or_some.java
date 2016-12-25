package com.puhua.crm.util;

import java.io.Serializable;

import com.puhua.crm.common.Constants;

public class Show_all_or_some implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String show_praise_num=Constants.SHOW_SOME_PRAISE;
	private String show_comment_num=Constants.SHOW_SOME_COMMENT;
	/**
	 * 用于记录当前用户的点赞ID,取消点赞时需要
	 */
	private String LocalUser_praiseId="";
	public String getShow_praise_num() {
		return show_praise_num;
	}
	public void setShow_praise_num(String show_praise_num) {
		this.show_praise_num = show_praise_num;
	}
	public String getShow_comment_num() {
		return show_comment_num;
	}
	public void setShow_comment_num(String show_comment_num) {
		this.show_comment_num = show_comment_num;
	}
	public String getLocalUser_praiseId() {
		return LocalUser_praiseId;
	}
	public void setLocalUser_praiseId(String localUser_praiseId) {
		LocalUser_praiseId = localUser_praiseId;
	}
	
}
