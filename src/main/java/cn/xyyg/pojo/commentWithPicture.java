package cn.xyyg.pojo;

import java.util.List;

/**
 * 评论扩展类，扩展图片
 * @author whc
 *
 */
public class commentWithPicture extends comment{
	private List<commentPicture> commentPictureList;
	private wechatUser wechatUser;
	private reply reply;
	public List<commentPicture> getCommentPictureList() {
		return commentPictureList;
	}

	public void setCommentPictureList(List<commentPicture> commentPictureList) {
		this.commentPictureList = commentPictureList;
	}

	public wechatUser getWechatUser() {
		return wechatUser;
	}

	public void setWechatUser(wechatUser wechatUser) {
		this.wechatUser = wechatUser;
	}

	public reply getReply() {
		return reply;
	}

	public void setReply(reply reply) {
		this.reply = reply;
	}
	
	
	
	
}
