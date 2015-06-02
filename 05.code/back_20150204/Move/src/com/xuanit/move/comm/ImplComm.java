package com.xuanit.move.comm;

public class ImplComm {
	
	//2.1用户登陆接口
	public static final String PhoneUser = "PhoneUser";
	//2.2用户注册接口
	public static final String PhoneUser_Create = "PhoneUser/Create";
	//2.3获取联系人接口
	public static final String GetContactPerson = "PhoneFriendsFans/GetContactPerson";
	//2.4动弹一下周边接口
	public static final String GetPeriphery = "PhoneNewsFeed/GetPeriphery";
	//2.5 动弹一下同班接口
	public static final String GetSameClassNews = "PhoneNewsFeed/GetSameClassNews";
	//2.6 动弹一下学校接口
	public static final String GetSchoolNews = "PhoneNewsFeed/GetSchoolNews";
	//2.7 动弹一下头条接口
	public static final String GetTopLineNews = "PhoneNewsFeed/GetTopLineNews";
	//2.8 动弹一下发布动态消息接口
	public static final String PhoneNewsFeed_Create = "PhoneNewsFeed/Create";
	//2.9 学校接口
	public static final String PhoneCollege_GetList = "PhoneCollege/GetList";
	//2.10 上传头像接口
	public static final String PhoneUser_UpHeadImg = "PhoneUser/UpHeadImg";
	//2.11 上传学生证接口
	public static final String PhoneUser_UpCardImg = "PhoneUser/UpCardImg";
	//2.12 获取消息详情接口
	public static final String PhoneNewsFeed_Get = "PhoneNewsFeed/Get";
	//2.13 申请组织活动接口
	public static final String Hudong_Creat = "PhoneActivity/Create";
	//2.14 根据活动ID获取活动详情接口
	public static final String Hudong_GetByActId = "PhoneActivity/GetByActId";
	//2.15 查看自己提交的活动和状态接口
	public static final String Hudong_GetListByUserId = "PhoneActivity/GetListByUserId";
    //2.16 查看自己参加和发布的活动
	public static final String Hudong_GetJoinListByUserId = "PhoneActivity/GetJoinListByUserId";
	//2.17 获取好友共同消息接口
	public static final String GetNewsByUserIds = "PhoneNewsFeed/GetNewsByUserIds";
	//2.18 根据活动类型查询活动接口
	public static final String Hudong_GetByActType = "PhoneActivity/GetByActType";
	//2.19 报名参加活动接口
	public static final String Hudong_Join = "PhoneActivityDetail/Create";
	//2.20 外卖联系方式接口
	public static final String PhoneTakeAway_GetList = "PhoneTakeAway/GetList";
	//2.21 快递服务接口
	public static final String honeLogisticsCompany_GetList = "honeLogisticsCompany/GetList";
	//2.22 二手资料接口
	public static final String PhoneHandInformation_GetList = "PhoneHandInformation/GetList";	
	//2.23 兼职中心接口
	public static final String PhonePartTimeJob_GetList = "PhonePartTimeJob/GetList";
	//2.24 游戏列表接口
	public static final String PhoneGame_GetList = "PhoneGame/GetList";
	//2.25 聊天Token接口
	public static final String PhoneChat_GetToken = "PhoneChat/GetToken";
	//2.26 获取我的好友接口
	public static final String GetFriends = "PhoneFriendsFans/GetFriends";
	//2.27 关注接口（添加为好友或者为粉丝点击确定时调用）
	public static final String GetAttentionFriends = "PhoneFriendsFans/Attention";
	//2.28 上传活动图片接口
	public static final String Huodong_UpCardImg = "PhoneActivity/UpCardImg";
	//2.29 获取推荐人接口
	public static final String PhoneUser_GetByUserIsReferre = "PhoneUser/GetByUserIsReferre";
	//2.30 上传消息图片接口
	public static final String PhoneNewsFeed_UpNewsFeedImg = "PhoneNewsFeed/UpNewsFeedImg";
	//2.31 爱心捐赠接口
	public static final String PhoneLoveDonation_GetList = "PhoneLoveDonation/GetList";
	//2.32 动弹列表接口
	public static final String PhoneMove_GetList = "PhoneMove/GetList";
	//2.33 人物接口
	public static final String GetPhoneNews = "PhoneNewsFeed/GetPhoneNews";
	//2.34 消息阅读接口
	public static final String PhoneNewsFeed_Reaed = "PhoneNewsFeed/Reaed";
	//2.35 消息点赞接口
	public static final String PhoneNewsFeed_Praise = "PhoneNewsFeed/Praise";
	//2.36 消息回复接口
	public static final String PhoneNewComment_Create = "PhoneNewComment/Create";
	//2.37 动弹回复接口
	public static final String PhoneMoveReply_Create = "PhoneMoveReply/Create";
	//2.38 动弹阅读接口
	public static final String PhoneMove_Reaed = "PhoneMove/Reaed";
	//2.39 我的动弹列表接口
	public static final String PhoneMove_GetMyList = "PhoneMove/GetMyList";	
	//2.40 选择用户（添加为好友或者为粉丝）接口
	public static final String PhoneUser_GetUserList = "PhoneUser/GetUserList";
	//2.41 更新头像接口
	public static final String PhoneUser_UpdateHead = "PhoneUser/UpdateHead";
	//2.42 更新个人中心
	public static final String PhoneUser_UpdatePersonCenterUser = "PhoneUser/UpdatePersonCenterUser";
	//2.43 判断上传相册按钮是否显示接口
	public static final String PhoneActivity_GetIsUpload = "PhoneActivity/GetIsUpload";	
	//2.44 获取自己发布的消息接口
	public static final String PhoneNewsFeed_GetPersonalCenter = "PhoneNewsFeed/GetPersonalCenter";
	//2.45 个人中心接口
	public static final String PhoneUser_Get = "PhoneUser/Get";
	//2.46 游戏列表(最新)接口
	public static final String PhoneGame_GetListByNew = "PhoneGame/GetListByNew";
	//2.47 游戏列表(最热)接口
	public static final String PhoneGame_GetListByTop = "PhoneGame/GetListByTop";
	//2.48 取消报名接口
	public static final String PhoneActivityDetail_Cancel = "PhoneActivityDetail/Cancel";
	//2.49 消息取消点赞接口
	public static final String PhoneNewsFeed_PraiseCancle = "PhoneNewsFeed/PraiseCancle";
	//2.50 相册上传图片保存接口
	public static final String PhoneActivityImg_Create = "PhoneActivityImg/Create";
	//2.51 根据活动获取相册（照片）接口
	public static final String PhoneActivityImg_GetList = "PhoneActivityImg/GetList";
	//2.52 根据学校查询班级接口
	public static final String PhoneClassGrade_GetListBySchoolId = "PhoneClassGrade/GetListBySchoolId";
	//2.53 人物周边接口
	public static final String PhoneUser_GetPeriphery = "PhoneUser/GetPeriphery";
	//2.54 人物同班接口
	public static final String PhoneUser_GetSameUser = "PhoneUser/GetSameUser";
	//2.55 人物学校接口
	public static final String PhoneUser_GetSchoolList = "PhoneUser/GetSchoolList";
	//2.56 人物推荐人接口
	public static final String PhoneUser_GetReferreList = "PhoneUser/GetReferreList";
}
