package com.xuanit.move.model;

import java.io.Serializable;

public class HuoDongInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String ActId;//活动ID	
	public String UserId;//用户ID
	
	public String Title;//标题
	public String CapPeople;//活动限制人数
	public String ActivityLocate;//活动地点
	public String ActType;//活动类型1官方发起2个人发起
	public String Detail;//活动内容 
	public String PreviewImg;//活动宣传图片
	public String UserName;//活动组织者
	public String ApplyTime;//活动开始时间
	public String EndTime;//活动结束时间
	public String ParticipateCount;//已参与人数
	public String ActivityPrice;//活动（消费）要求
	public boolean IsApply;//当前用户是否报名
	public boolean IsShow;//当前活动是否过期	
	public String ExecutorID;//已参与人数
	
}
