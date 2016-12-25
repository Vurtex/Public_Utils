package com.puhua.crm.util;

import com.puhua.crm.entity.CommentConfig;

import android.view.View;

/**
 * @author acer 评论点赞区的刷新接口
 */
public interface ICircleView {

	public void update2DeleteCircle(int flag, String circleId);

	public void update2DeleteFavort(int circlePosition, String favortId);

	public void update2DeleteComment(int circlePosition, String commentId);

	public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);

	public void update2AddComment(CommentConfig config, Object addItem);

	public void update2AddFavorite(int circlePosition, Object item);

	public void showDetail(int position);

	public void updateEditTextBodyVisible(int visible, CommentConfig commentConfig, View parent);
}
