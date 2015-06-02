/*
Navicat MySQL Data Transfer

Source Server         : localhost_3308
Source Server Version : 50096
Source Host           : localhost:3308
Source Database       : moveonetime

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2015-01-07 09:46:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `ActId` int(11) NOT NULL auto_increment COMMENT '活动Id',
  `UserId` int(11) default NULL COMMENT '用户ID',
  `Title` varchar(300) default '' COMMENT '标题',
  `CapPeople` int(11) default '0' COMMENT '封顶人数',
  `ActivityLocate` varchar(500) default '' COMMENT '活动地点\r\n',
  `ActType` int(11) default '1' COMMENT '活动类型1官方发起2个人发起',
  `Detail` text COMMENT '活动内容',
  `PreviewImg` varchar(300) default '' COMMENT '活动宣传图片',
  `IsAudit` int(11) default '1' COMMENT '是否审核',
  `ApplyTime` datetime default '1900-01-01 00:00:00' COMMENT '申请时间',
  `EndTime` datetime default '1900-01-01 00:00:00' COMMENT '截止时间',
  `ParticipateCount` int(11) default '0' COMMENT '参与数',
  `ActivityPrice` decimal(10,2) default '0.00' COMMENT '活动费用',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建人',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '上次更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '上次更改人员',
  PRIMARY KEY  (`ActId`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='活动表';

-- ----------------------------
-- Table structure for activitydetail
-- ----------------------------
DROP TABLE IF EXISTS `activitydetail`;
CREATE TABLE `activitydetail` (
  `DetailId` int(11) NOT NULL auto_increment COMMENT '活动详情ID',
  `UserId` int(11) default NULL COMMENT '用户ID',
  `Detail` text COMMENT '活动详情内容',
  `ActId` int(11) default NULL COMMENT '活动ID',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建人',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '上次更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '上次更改人员',
  PRIMARY KEY  (`DetailId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for activityimg
-- ----------------------------
DROP TABLE IF EXISTS `activityimg`;
CREATE TABLE `activityimg` (
  `ImgId` int(11) NOT NULL auto_increment COMMENT '相册Id',
  `ActId` int(11) default '-1' COMMENT '活动ID',
  `Name` varchar(200) default '' COMMENT '图片原名',
  `IsAudit` bit(1) default NULL,
  `Src` varchar(200) default '' COMMENT '图片地址',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建人',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '上次更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '上次更改人员',
  PRIMARY KEY  (`ImgId`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='活动相册表';

-- ----------------------------
-- Table structure for classgrade
-- ----------------------------
DROP TABLE IF EXISTS `classgrade`;
CREATE TABLE `classgrade` (
  `ClassID` int(11) NOT NULL auto_increment COMMENT '班级Id',
  `Name` varchar(300) default '' COMMENT '班级名称',
  `SchoolId` int(11) default NULL COMMENT '学校ID',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建人',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '上次更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '上次更改人员',
  PRIMARY KEY  (`ClassID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college` (
  `Id` int(11) NOT NULL auto_increment,
  `CollegeId` int(11) default '-1' COMMENT '学校编号',
  `CollegeName` varchar(50) default '' COMMENT '学校名称',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建人',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '上次更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '上次更改人员',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for friendsfans
-- ----------------------------
DROP TABLE IF EXISTS `friendsfans`;
CREATE TABLE `friendsfans` (
  `FriendsFansId` int(11) NOT NULL auto_increment COMMENT '好友粉丝Id',
  `UserId` int(11) default '0' COMMENT '用户ID',
  `ToUserId` int(11) default '0' COMMENT '好友或者粉丝ID',
  `AttentionType` int(11) default '1' COMMENT '1好友2粉丝',
  `IsAttentioned` bit(1) default b'0' COMMENT '是否已经关注',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建用户',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '最后更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '最后更改用户',
  PRIMARY KEY  (`FriendsFansId`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='好友粉丝表';

-- ----------------------------
-- Table structure for game
-- ----------------------------
DROP TABLE IF EXISTS `game`;
CREATE TABLE `game` (
  `GameId` int(11) NOT NULL auto_increment COMMENT '游戏Id',
  `Name` varchar(200) default '' COMMENT '游戏名',
  `Src` varchar(200) default '' COMMENT '游戏图片地址',
  `GameSize` decimal(10,2) default NULL COMMENT '游戏大小M',
  `GameUrl` varchar(200) default '' COMMENT '游戏地址',
  `AllDownTimes` int(11) default NULL COMMENT '设置一共可以下载都是次',
  `WeeKDownTimes` int(10) default NULL COMMENT '设置一周可以下载但是此次',
  `GameStar` int(10) default NULL,
  `GameType` varchar(200) default NULL COMMENT '游戏类型',
  `GameIntegral` decimal(10,0) default '0' COMMENT '游戏积分',
  `Status` int(11) NOT NULL default '1' COMMENT '记录状态',
  `CreateTime` datetime NOT NULL default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) NOT NULL default '-1' COMMENT '创建人',
  `LastChangeTime` datetime NOT NULL default '1900-01-01 00:00:00' COMMENT '上次更改时间',
  `LastChangeUser` int(11) NOT NULL default '-1' COMMENT '上次更改人员',
  PRIMARY KEY  (`GameId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='游戏表';

-- ----------------------------
-- Table structure for gamecomment
-- ----------------------------
DROP TABLE IF EXISTS `gamecomment`;
CREATE TABLE `gamecomment` (
  `CommentId` int(10) NOT NULL auto_increment,
  `GameId` int(10) NOT NULL,
  `CommentUserId` int(10) NOT NULL,
  `Commenttxt` text NOT NULL,
  `CommentTime` datetime NOT NULL,
  PRIMARY KEY  (`CommentId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gamedown
-- ----------------------------
DROP TABLE IF EXISTS `gamedown`;
CREATE TABLE `gamedown` (
  `GameDownId` int(10) NOT NULL auto_increment COMMENT '主键',
  `GameId` int(10) NOT NULL,
  `DownUserId` int(10) NOT NULL,
  `DownTime` datetime NOT NULL,
  PRIMARY KEY  (`GameDownId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for handinformation
-- ----------------------------
DROP TABLE IF EXISTS `handinformation`;
CREATE TABLE `handinformation` (
  `InformationId` int(11) NOT NULL auto_increment COMMENT '二手信息资料Id',
  `Name` varchar(4000) default '' COMMENT '外卖名称',
  `LinkMan` varchar(20) default NULL,
  `Img` varchar(100) default NULL,
  `Phone` varchar(20) default NULL,
  `Contents` text COMMENT '外卖内容',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建用户',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '最后更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '最后更改用户',
  PRIMARY KEY  (`InformationId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='二手信息资料表';

-- ----------------------------
-- Table structure for logisticscompany
-- ----------------------------
DROP TABLE IF EXISTS `logisticscompany`;
CREATE TABLE `logisticscompany` (
  `CompanyId` int(11) NOT NULL auto_increment COMMENT '公司id',
  `Prefix` varchar(10) default '' COMMENT '前缀',
  `CompanyCode` varchar(50) default '' COMMENT '物流公司code',
  `LinkMan` varchar(20) default NULL,
  `Phone` varchar(20) default NULL,
  `CompanyName` varchar(200) default '' COMMENT '物流公司名',
  `Img` varchar(100) default NULL,
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建用户',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '最后更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '最后更改用户',
  PRIMARY KEY  (`CompanyId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='物流公司表';

-- ----------------------------
-- Table structure for lovedonation
-- ----------------------------
DROP TABLE IF EXISTS `lovedonation`;
CREATE TABLE `lovedonation` (
  `DonationId` int(11) NOT NULL auto_increment COMMENT '捐款Id',
  `Name` varchar(200) default '' COMMENT '捐款名',
  `Src` varchar(200) default '' COMMENT '捐款地址',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建人',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '上次更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '上次更改人员',
  PRIMARY KEY  (`DonationId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='爱心捐款表';

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `MenuId` int(11) NOT NULL auto_increment COMMENT '菜单ID',
  `Name` varchar(200) default '' COMMENT '菜单名',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建用户',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '最后更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '最后更改用户',
  PRIMARY KEY  (`MenuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单';

-- ----------------------------
-- Table structure for move
-- ----------------------------
DROP TABLE IF EXISTS `move`;
CREATE TABLE `move` (
  `MoveId` int(11) NOT NULL auto_increment COMMENT '动弹Id',
  `Contents` text NOT NULL COMMENT '动弹内容',
  `UserId` int(11) NOT NULL COMMENT '用户ID',
  `Latitude` varchar(40) default NULL COMMENT '纬度',
  `Longitude` varchar(40) default NULL COMMENT '经度',
  `ImgSrc` varchar(100) default NULL,
  `ReadCount` int(11) default '0' COMMENT '阅读数',
  `ReplyCount` int(11) default '0' COMMENT '回复数',
  `Status` int(11) NOT NULL default '1' COMMENT '记录状态',
  `CreateTime` datetime NOT NULL default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) NOT NULL default '-1' COMMENT '创建用户',
  `LastChangeTime` datetime NOT NULL default '1900-01-01 00:00:00' COMMENT '最后更改时间',
  `LastChangeUser` int(11) NOT NULL default '-1' COMMENT '最后更改用户',
  PRIMARY KEY  (`MoveId`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for movereply
-- ----------------------------
DROP TABLE IF EXISTS `movereply`;
CREATE TABLE `movereply` (
  `ReplyId` int(11) NOT NULL auto_increment COMMENT '回复Id',
  `MoveId` int(11) default '0' COMMENT '动弹ID',
  `ReplyContents` text COMMENT '回复内容',
  `UserId` int(11) default NULL COMMENT '用户ID',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建用户',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '最后更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '最后更改用户',
  PRIMARY KEY  (`ReplyId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for newcomment
-- ----------------------------
DROP TABLE IF EXISTS `newcomment`;
CREATE TABLE `newcomment` (
  `CommentId` int(11) NOT NULL auto_increment COMMENT '评论Id',
  `Contents` text COMMENT '回复内容',
  `UserId` int(11) default NULL COMMENT '当前用户ID',
  `Img` varchar(100) default NULL,
  `NewsId` int(11) default '0' COMMENT '消息ID',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建用户',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '最后更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '最后更改用户',
  PRIMARY KEY  (`CommentId`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='消息评论表';

-- ----------------------------
-- Table structure for newsfeed
-- ----------------------------
DROP TABLE IF EXISTS `newsfeed`;
CREATE TABLE `newsfeed` (
  `NewsId` int(11) NOT NULL auto_increment COMMENT '消息Id',
  `UserId` int(11) default NULL COMMENT '用户ID',
  `Contents` text COMMENT '消息内容',
  `IsPublished` bit(1) default b'0' COMMENT '是否发布',
  `ReadCount` int(11) default '0' COMMENT '阅读数',
  `ReplyCount` int(11) default '0' COMMENT '回复数',
  `NewType` int(11) default '1' COMMENT '消息类型1普通用户2管理员',
  `IsPraised` bit(1) default b'0' COMMENT '是否点赞',
  `PraiseCount` int(11) default '0' COMMENT '点赞数',
  `IsStick` bit(1) default b'0' COMMENT '是否置顶',
  `Rules` varchar(300) default '' COMMENT '同班规则',
  `Longitude` int(11) default '0' COMMENT '经度',
  `Latitude` int(11) default '0' COMMENT '纬度',
  `PublishTime` datetime default '1900-01-01 00:00:00' COMMENT '消息发布时间',
  `ImgSrc` varchar(300) default '' COMMENT '消息图片地址',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建用户',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '最后更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '最后更改用户',
  PRIMARY KEY  (`NewsId`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='消息表';

-- ----------------------------
-- Table structure for parttimejob
-- ----------------------------
DROP TABLE IF EXISTS `parttimejob`;
CREATE TABLE `parttimejob` (
  `PartTimeId` int(11) NOT NULL auto_increment COMMENT '兼职Id',
  `Name` varchar(100) default '' COMMENT '外卖名称',
  `Contents` text COMMENT '外卖内容',
  `WorkPeriod` int(10) default NULL COMMENT '1小时2天3周4月5年',
  `WorkArea` varchar(100) default NULL COMMENT '工作地点',
  `WorkTime` varchar(100) NOT NULL COMMENT '工作时间',
  `WorkPay` decimal(10,2) default NULL COMMENT '薪酬',
  `Img` varchar(100) default NULL,
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建用户',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '最后更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '最后更改用户',
  `ContactName` varchar(4000) default '' COMMENT '联系人名称',
  `ContactPhone` int(11) default '0' COMMENT '联系人电话',
  PRIMARY KEY  (`PartTimeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='兼职资料表';

-- ----------------------------
-- Table structure for praise
-- ----------------------------
DROP TABLE IF EXISTS `praise`;
CREATE TABLE `praise` (
  `PraiseId` int(11) NOT NULL auto_increment COMMENT '点赞记录ID',
  `NewsId` int(11) default NULL COMMENT '消息ID',
  `UserId` int(11) default NULL COMMENT '点赞用户ID',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateUser` int(11) default '-1' COMMENT '创建人id',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `LastChangeUser` int(11) default '-1' COMMENT '上次修改人id',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '上次修改时间',
  PRIMARY KEY  (`PraiseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞记录表';

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `RoleId` int(11) NOT NULL auto_increment COMMENT '角色ID',
  `RoleName` varchar(50) default '' COMMENT '角色名称',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建用户',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '最后更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '最后更改用户',
  PRIMARY KEY  (`RoleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息';

-- ----------------------------
-- Table structure for rolemenumap
-- ----------------------------
DROP TABLE IF EXISTS `rolemenumap`;
CREATE TABLE `rolemenumap` (
  `Id` int(11) NOT NULL auto_increment,
  `RoleId` int(11) default NULL COMMENT '角色ID',
  `MenuId` int(11) default NULL COMMENT '菜单ID',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单映射';

-- ----------------------------
-- Table structure for systemparameters
-- ----------------------------
DROP TABLE IF EXISTS `systemparameters`;
CREATE TABLE `systemparameters` (
  `SystemParameterId` int(11) NOT NULL auto_increment COMMENT '主键',
  `ParameterName` varchar(255) NOT NULL COMMENT '参数名',
  `ParameterCode` varchar(255) NOT NULL,
  `ParameterValule` varchar(255) NOT NULL COMMENT '参数值',
  `Status` int(11) NOT NULL,
  `CreateTime` datetime default NULL,
  `CreateUserId` int(11) default NULL,
  `LastChangeTime` datetime default NULL,
  `LastChangeUser` int(11) default NULL,
  PRIMARY KEY  (`SystemParameterId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for takeaway
-- ----------------------------
DROP TABLE IF EXISTS `takeaway`;
CREATE TABLE `takeaway` (
  `TakeAwayId` int(11) NOT NULL auto_increment COMMENT '外卖Id',
  `Name` varchar(100) default '' COMMENT '外卖名称',
  `Contents` text COMMENT '外卖内容',
  `Img` varchar(100) default NULL,
  `Speed` double(10,1) default NULL,
  `Distance` double(10,2) default NULL,
  `Phone` varchar(12) default NULL,
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建用户',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '最后更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '最后更改用户',
  PRIMARY KEY  (`TakeAwayId`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='外卖资料表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `UserId` int(11) NOT NULL auto_increment COMMENT '用户Id',
  `UserName` varchar(20) default '' COMMENT '用户昵称',
  `Pwd` varchar(200) default '' COMMENT '密码',
  `Salt` varchar(50) default '' COMMENT '加盐',
  `StudentNum` varchar(200) default '' COMMENT '学号',
  `SchoolId` int(11) default '0' COMMENT '学校Id',
  `Head` varchar(300) default '' COMMENT '学生证',
  `StudentCard` varchar(300) default '' COMMENT '头像',
  `AuditStatus` int(11) default '1' COMMENT '审核状态',
  `StarLevelSum` decimal(10,2) default '0.00' COMMENT '星级=好友数+游戏积分+动弹数',
  `StarLevel` decimal(10,2) default '0.00' COMMENT '好友数',
  `LevelMax` decimal(10,2) default '0.00' COMMENT '等级条件(多少分一个星星)',
  `GameIntegral` decimal(10,2) default '0.00' COMMENT '游戏积分',
  `MoveCount` int(11) default '0' COMMENT '动弹数',
  `Longitude` varchar(300) default '' COMMENT '经度',
  `IsReferre` bit(1) default NULL COMMENT '是否是推荐人',
  `Latitude` varchar(300) default '' COMMENT '纬度',
  `Description` varchar(500) default '' COMMENT '个人说明',
  `ClassId` int(11) default '0' COMMENT '班级Id',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建用户',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '最后更改时间',
  `LastChangeUser` int(11) default NULL COMMENT '最后更改用户',
  PRIMARY KEY  (`UserId`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COMMENT='用户账户表';

-- ----------------------------
-- Table structure for useractivitymap
-- ----------------------------
DROP TABLE IF EXISTS `useractivitymap`;
CREATE TABLE `useractivitymap` (
  `Id` int(11) NOT NULL auto_increment,
  `ActId` int(11) default NULL COMMENT '活动ID',
  `UserId` int(11) default NULL COMMENT '用户ID',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户活动映射表';

-- ----------------------------
-- Table structure for userrecommendmap
-- ----------------------------
DROP TABLE IF EXISTS `userrecommendmap`;
CREATE TABLE `userrecommendmap` (
  `Id` int(11) NOT NULL auto_increment,
  `RecommendId` int(11) default NULL COMMENT '推荐人ID',
  `UserId` int(11) default NULL COMMENT '用户ID',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户推荐人映射表';

-- ----------------------------
-- Table structure for userrolemap
-- ----------------------------
DROP TABLE IF EXISTS `userrolemap`;
CREATE TABLE `userrolemap` (
  `Id` int(11) NOT NULL auto_increment,
  `RoleId` int(11) default NULL COMMENT '角色ID',
  `UserId` int(11) default NULL COMMENT '用户ID',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户、角色映射';
