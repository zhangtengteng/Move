package com.xuanit.move.model;

public class LianXiRenInfo {
	public String UserId; //用户Id
	public String UserName;//用户名
	public String StudentCard;//头像
	public String PersonalDescription;//个人说明
	public String FriendCount;//好友指数
	public String FansCount;//粉丝数
	public String CommonFriends;//共同好友
	public String ToUserId;//好友或者粉丝ID
	public String AttentionType;//1好友2粉丝
	@Override
	public String toString() {
		return "LianXiRenInfo [UserId=" + UserId + ", UserName=" + UserName
				+ ", StudentCard=" + StudentCard + ", PersonalDescription="
				+ PersonalDescription + ", FriendCount=" + FriendCount
				+ ", FansCount=" + FansCount + ", CommonFriends="
				+ CommonFriends + ", ToUserId=" + ToUserId + ", AttentionType="
				+ AttentionType + "]";
	}
	


}
