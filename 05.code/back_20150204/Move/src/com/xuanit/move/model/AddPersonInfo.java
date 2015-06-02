package com.xuanit.move.model;

import java.io.Serializable;

public class AddPersonInfo implements Serializable {

	public String UserId;//用户ID
	public String UserName;//用户昵称
	public String Pwd;//用户密码
	public String Salt;
	public String StudentNum;//学号
	public String SchoolId;//学校id
	public String SchoolName;//学校名
	public String Head;//头像
	public String StudentCard;//学生证照片
	public String AuditStatus;//审核状态
	public String StarLevel;//星级
	public String LevelMax;
	public String GameIntegral;//游戏积分
	public String StarLevelSum;
	public String MoveCount;//动弹数
	public String Status;
	public String CreateTime;
	public String CreateUser;
	public String LastChangeTime;
	public String LastChangeUser;
	public String ActId;
	public boolean IsAssociated;//是否为好友
	public String NumberStars;//星星数目
	public String PersonalCenter;
	public String Description;	//用户签名
	public String Distance; //距离

	public String FansCount;//粉丝数量
	public String Hobby;//兴趣爱好
	public String Birthday;//生日
	public String Dream;//梦想
	
}
