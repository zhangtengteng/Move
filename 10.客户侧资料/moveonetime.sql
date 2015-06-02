/*
Navicat MySQL Data Transfer

Source Server         : localhost_3308
Source Server Version : 50096
Source Host           : localhost:3308
Source Database       : moveonetime

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2015-01-09 19:33:43
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
  `SchoolId` int(11) default '0' COMMENT 'ѧУId',
  PRIMARY KEY  (`ActId`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='活动表';

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES ('26', '57', '校园版电信正式登陆苏州各大高校校园版电信正式登陆苏州各大高校，加个优惠 的', '100', '苏州金鸡湖', null, '校园版电信正式登陆苏州各大高校，加个优惠 的校园版电信正式登陆苏州各大高校，加个优惠 的校园版电信正式登陆苏州各大高校，加个优惠 的校园版电信正式登陆苏州各大高校，加个优惠 的', '/UpLoadFile/PreviewImg/2ae888f3-2783-4ebf-a0da-fec9f129ae83.jpg', '0', '2015-12-25 00:00:00', '2015-12-25 00:00:00', '2', null, '1', '2015-01-03 21:45:44', '57', '2015-01-07 11:04:33', '57', '13');
INSERT INTO `activity` VALUES ('27', '57', 'dd', '22', '33', '1', '34', '/UpLoadFile/PreviewImg/ff2aaf22-d866-4fc8-a0cb-df233722d372.jpg', '1', '2015-01-07 00:00:00', '2015-01-07 00:00:00', null, null, '1', '2015-01-07 09:16:47', '57', '2015-01-07 09:16:47', null, '13');
INSERT INTO `activity` VALUES ('28', '57', '2134', '43', '34', null, '4545', '/UpLoadFile/PreviewImg/a90ab74a-6d4d-4931-840a-10b5b5e781e6.jpg', '1', '2015-01-07 00:00:00', '2015-01-07 00:00:00', null, '500.00', '1', '2015-01-07 09:17:41', '57', '2015-01-08 16:28:34', '57', '13');

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
-- Records of activitydetail
-- ----------------------------
INSERT INTO `activitydetail` VALUES ('1', '60', '我要参加，这么便宜，还可以拍些照片', '27', '2', '2015-01-03 21:51:43', '60', '2015-01-07 10:31:01', '57');
INSERT INTO `activitydetail` VALUES ('2', '61', '我也要参加，这么便宜，还可以拍些照片', '27', '1', '2015-01-03 21:52:51', '61', '2015-01-03 21:52:51', '61');

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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='活动相册表';

-- ----------------------------
-- Records of activityimg
-- ----------------------------
INSERT INTO `activityimg` VALUES ('33', '20', null, '\0', '~/UpLoadFile/ActImgs/cfc0150c-cda3-4e4e-822c-b0ec3c9f93cd.jpg', '1', '2014-12-30 14:51:30', '37', '2014-12-30 14:52:14', null);
INSERT INTO `activityimg` VALUES ('34', '20', null, '', '~/UpLoadFile/ActImgs/0a7b290a-4520-4679-af30-85779493d23d.jpg', '1', '2014-12-30 14:51:40', '37', '2014-12-30 14:51:52', null);
INSERT INTO `activityimg` VALUES ('35', '20', null, '', '~/UpLoadFile/ActImgs/dc9b7c9c-a42c-406f-a0a3-21da079682ea.jpg', '1', '2014-12-30 14:52:07', '37', '2014-12-30 14:52:08', null);
INSERT INTO `activityimg` VALUES ('36', '23', null, '', '~/UpLoadFile/ActImgs/f415955f-9f0a-4056-9730-9fc5047bbeac.jpg', '1', '2014-12-30 14:53:04', '37', '2014-12-30 14:53:08', null);
INSERT INTO `activityimg` VALUES ('37', '23', null, '\0', '~/UpLoadFile/ActImgs/2d241ed7-c201-4719-8882-70fcca83bdc8.jpg', '1', '2014-12-30 14:53:16', '37', '2014-12-30 14:53:16', null);
INSERT INTO `activityimg` VALUES ('38', '26', null, '', '~/UpLoadFile/ActImgs/c17eb7c1-9bd2-4af0-8ab5-0b6868a6b932.jpg', '1', '2015-01-06 10:28:55', '72', '2015-01-06 10:33:37', null);
INSERT INTO `activityimg` VALUES ('39', '26', null, '\0', '~/UpLoadFile/ActImgs/91fe9a70-be8e-47e6-ad26-001f600b9f45.jpg', '1', '2015-01-06 10:29:02', '72', '2015-01-06 10:36:10', null);
INSERT INTO `activityimg` VALUES ('40', '26', null, '\0', '~/UpLoadFile/ActImgs/182ae0df-9972-4b6c-adda-881608820908.jpg', '1', '2015-01-06 10:32:42', '72', '2015-01-06 10:32:42', null);
INSERT INTO `activityimg` VALUES ('41', '26', null, '', '~/UpLoadFile/ActImgs/78239d2f-fdc4-4b95-86d8-6169bf5a6bb5.jpg', '1', '2015-01-06 10:33:11', '72', '2015-01-06 10:33:24', null);
INSERT INTO `activityimg` VALUES ('42', '26', null, '\0', '123.jpg', '1', '2015-01-07 10:54:57', '60', '2015-01-07 10:54:57', '60');
INSERT INTO `activityimg` VALUES ('43', '26', null, '\0', '123.jpg', '1', '2015-01-07 10:54:57', '60', '2015-01-07 10:54:57', '60');
INSERT INTO `activityimg` VALUES ('44', '26', null, '\0', '123.jpg', '1', '2015-01-07 10:54:57', '60', '2015-01-07 10:54:57', '60');
INSERT INTO `activityimg` VALUES ('45', '28', null, '\0', '/UpLoadFile/ActImgs/a74684f9-c834-4724-af36-34ee4772cee4.jpg', '1', '2015-01-08 14:53:00', '57', '2015-01-08 14:53:00', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classgrade
-- ----------------------------
INSERT INTO `classgrade` VALUES ('1', '软件08C2', '1', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `classgrade` VALUES ('2', 'ffasd', '1', '1', '2015-01-06 15:27:37', '72', '2015-01-06 15:34:55', '72');
INSERT INTO `classgrade` VALUES ('3', 'fffffff', '2', '1', '2015-01-06 15:34:59', '72', '2015-01-06 15:35:05', '72');
INSERT INTO `classgrade` VALUES ('4', 'gggggg', '2', '1', '2015-01-06 15:35:36', '72', '2015-01-06 15:35:36', '72');
INSERT INTO `classgrade` VALUES ('5', 'adadasd', '1', '2', '2015-01-06 15:39:59', '72', '2015-01-06 15:40:02', '72');
INSERT INTO `classgrade` VALUES ('6', 'asdasd', '18', '1', '2015-01-07 16:57:13', '57', '2015-01-07 16:57:13', '57');

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college` (
  `Id` int(11) NOT NULL auto_increment,
  `CollegeId` int(11) default '-1' COMMENT '学校编号',
  `CollegeName` varchar(50) default '' COMMENT '学校名称',
  `StudentNumLength` int(10) default NULL,
  `ClassStart` int(10) default NULL,
  `ClassEnd` int(10) default NULL,
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建人',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '上次更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '上次更改人员',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES ('1', '1001', '清华大学', '10', '8', '10', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `college` VALUES ('2', '1002', '北京大学', '10', '8', '10', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `college` VALUES ('3', '1003', '中国人民大学', '10', '8', '10', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `college` VALUES ('4', '1004', '北京航空航天大学', '10', '8', '10', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `college` VALUES ('5', '1005', '北京邮电大学', '10', '8', '10', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `college` VALUES ('6', '1006', '北京师范大学', '10', '8', '10', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `college` VALUES ('7', '1007', '中国传媒大学', '10', '8', '10', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `college` VALUES ('8', '1008', '北京语言大学', '10', '8', '10', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `college` VALUES ('9', '1009', '北京科技大学', '10', '8', '10', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `college` VALUES ('10', '1010', '中国农业大学', '10', '8', '10', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `college` VALUES ('11', '1011', '北京理工大学', '10', '8', '10', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `college` VALUES ('12', '1012', '北京林业大学', '10', '8', '10', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `college` VALUES ('13', '1013', '北京交通大学', '10', '8', '10', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `college` VALUES ('18', null, '333', '10', '8', '10', '1', '2015-01-06 15:40:09', '72', '2015-01-07 16:57:06', '57');

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
  `SchoolId` int(11) default '0' COMMENT 'ѧУId',
  PRIMARY KEY  (`FriendsFansId`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='好友粉丝表';

-- ----------------------------
-- Records of friendsfans
-- ----------------------------
INSERT INTO `friendsfans` VALUES ('13', '57', '58', '1', '\0', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1', '0');
INSERT INTO `friendsfans` VALUES ('17', '57', '59', '2', '\0', '1', '2015-01-04 13:45:36', '57', '2015-01-04 13:45:36', '57', '0');
INSERT INTO `friendsfans` VALUES ('18', '57', '60', '2', '\0', '1', '2015-01-04 13:45:41', '57', '2015-01-04 13:45:41', '57', '0');
INSERT INTO `friendsfans` VALUES ('19', '57', '61', '2', '\0', '1', '2015-01-04 13:45:42', '57', '2015-01-04 13:45:42', '57', '0');

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
  `SchoolId` int(11) default '0' COMMENT 'ѧУId',
  PRIMARY KEY  (`GameId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='游戏表';

-- ----------------------------
-- Records of game
-- ----------------------------
INSERT INTO `game` VALUES ('1', '传奇6', '~/UpLoadFile/Game/4f8ee46d-4526-446e-a69d-d5bd57ba70de.jpg', '4.00', 'http://download.3g.fang.com/soufun_android_30001_7.2.2.apk', '4', '4', null, '射击', '45', '2', '1900-01-01 00:00:00', '-1', '2015-01-06 16:36:22', '57', '13');
INSERT INTO `game` VALUES ('2', 'QQ小游戏', 'http://www.baidu.com', '100.00', '~/UpLoadFile/Game/50caa3bb-e7d8-4284-a024-796dba03a84b.jpg', '4', '4', null, '休闲', '2', '1', '2014-12-24 23:26:32', '0', '2015-01-07 09:19:30', '57', '0');
INSERT INTO `game` VALUES ('3', null, null, null, '~/UpLoadFile/Game/b4dfa3b6-4320-4b5d-b0bb-6e431e8d70c3.jpg', null, null, null, null, null, '1', '2014-12-27 13:46:12', '42', '2015-01-06 16:32:21', '0', '0');
INSERT INTO `game` VALUES ('4', '123', 'www.badu.com', '123.00', '~/UpLoadFile/Game/7669ef81-5e0f-4376-9aef-a6e079e225fc.jpg', '123', '213', null, '123', '123', '1', '2015-01-07 09:20:14', '57', '2015-01-07 09:21:47', '57', '13');
INSERT INTO `game` VALUES ('5', 'r', '/UpLoadFile/Game/64f6bd5a-837b-4d73-ade8-11728fef575c.jpg', '234.00', '23', '234', '234', null, '34', '234', '1', '2015-01-07 09:24:23', '57', '2015-01-08 14:11:24', '57', '0');
INSERT INTO `game` VALUES ('6', '123', '/UpLoadFile/Game/bebb5782-b842-423a-af77-2e9eacb0d671.jpg', '123.00', '23', '123', '123', null, '123', '123', '1', '2015-01-07 09:26:05', '57', '2015-01-09 10:14:21', '57', '13');
INSERT INTO `game` VALUES ('7', '324', '/UpLoadFile/Game/33786741-1f26-4d7f-898f-bb3efcc581c3.jpg', '234.00', '234222', '234', '234', null, '234', '234', '2', '2015-01-07 09:29:49', '57', '2015-01-07 09:29:57', '57', '0');

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
-- Records of gamecomment
-- ----------------------------
INSERT INTO `gamecomment` VALUES ('1', '0', '2', '2', '2014-12-23 15:26:23');

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
-- Records of gamedown
-- ----------------------------
INSERT INTO `gamedown` VALUES ('1', '2', '3', '2014-12-23 15:26:41');
INSERT INTO `gamedown` VALUES ('2', '1', '1', '2014-12-23 16:05:51');
INSERT INTO `gamedown` VALUES ('3', '1', '1', '2014-12-23 16:06:10');
INSERT INTO `gamedown` VALUES ('4', '1', '1', '2014-12-23 16:06:39');

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
  `SchoolId` int(11) default '0' COMMENT 'ѧУId',
  PRIMARY KEY  (`InformationId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='二手信息资料表';

-- ----------------------------
-- Records of handinformation
-- ----------------------------
INSERT INTO `handinformation` VALUES ('5', '1', '赵金龙', '/UpLoadFile/SecondHand/94e02327-5060-4409-bd62-b60c1533468a.jpg', '15151509313', '阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬阿斯顿发斯蒂芬', '1', '2015-01-05 09:25:39', '68', '2015-01-08 16:29:56', '57', '13');

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
  `SchoolId` int(11) default '0' COMMENT 'ѧУId',
  PRIMARY KEY  (`CompanyId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='物流公司表';

-- ----------------------------
-- Records of logisticscompany
-- ----------------------------
INSERT INTO `logisticscompany` VALUES ('16', null, null, 'test-001', '15151509313', '圆通', '/UpLoadFile/TakeOut/b9235c65-9812-4434-9ff6-d7f771b74ce3.jpg', '1', '2015-01-05 09:54:21', '68', '2015-01-08 16:31:52', '57', '0');
INSERT INTO `logisticscompany` VALUES ('17', null, null, 'test-002', '15151509313', '顺丰', '/UpLoadFile/TakeOut/73b67613-b6ea-432d-b449-5c6eb9771d6d.jpg', '1', '2015-01-05 10:02:26', '68', '2015-01-08 16:31:47', '57', '13');

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
-- Records of lovedonation
-- ----------------------------
INSERT INTO `lovedonation` VALUES ('1', 'g', 'g', null, null, null, null, null);
INSERT INTO `lovedonation` VALUES ('2', 'g', 'g', null, null, null, null, null);
INSERT INTO `lovedonation` VALUES ('3', 'yty', 'yy', null, null, null, null, null);
INSERT INTO `lovedonation` VALUES ('4', 'df', 'dfdf', null, null, null, null, null);
INSERT INTO `lovedonation` VALUES ('5', '8800', '似得', '1', '2014-12-27 13:03:50', '42', '2014-12-27 13:03:50', '42');
INSERT INTO `lovedonation` VALUES ('6', '帮助小孩学', 'www.bao.copm', '1', '2014-12-28 13:25:15', '41', '2014-12-28 13:25:15', '41');

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
-- Records of menu
-- ----------------------------

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
-- Records of move
-- ----------------------------
INSERT INTO `move` VALUES ('1', 'd', '2', null, null, null, '0', '0', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `move` VALUES ('2', 'xxxxxxx', '0', null, null, null, '0', '0', '0', '0001-01-01 00:00:00', '0', '0001-01-01 00:00:00', '0');
INSERT INTO `move` VALUES ('3', 'xxxxxxx', '0', null, null, null, '0', '0', '0', '0001-01-01 00:00:00', '0', '0001-01-01 00:00:00', '0');
INSERT INTO `move` VALUES ('4', 'xxxxxxx', '0', null, null, null, '0', '0', '0', '0001-01-01 00:00:00', '0', '0001-01-01 00:00:00', '0');
INSERT INTO `move` VALUES ('5', 'xxxxx', '0', null, null, null, '0', '0', '1', '2014-12-23 13:24:20', '1', '2014-12-23 13:24:20', '0');
INSERT INTO `move` VALUES ('6', 'xxxxx', '0', null, null, null, '0', '0', '1', '2014-12-23 13:24:29', '1', '2014-12-23 13:24:29', '0');
INSERT INTO `move` VALUES ('7', 'xxxxxc', '0', null, null, null, '0', '0', '1', '2014-12-23 13:24:32', '1', '2014-12-23 13:24:32', '0');
INSERT INTO `move` VALUES ('8', 'xxxxxcv', '0', null, null, null, '0', '0', '1', '2014-12-23 13:24:34', '1', '2014-12-23 13:24:34', '0');
INSERT INTO `move` VALUES ('9', 'xxxxxcvc', '0', null, null, null, '0', '0', '1', '2014-12-23 13:24:37', '1', '2014-12-23 13:24:37', '0');
INSERT INTO `move` VALUES ('10', 'xxxxxcvc6676', '0', null, null, null, '0', '0', '1', '2014-12-23 13:24:39', '1', '2014-12-23 13:24:39', '0');
INSERT INTO `move` VALUES ('11', 'xxxxxcvc6676mmmmm', '0', null, null, null, '0', '0', '1', '2014-12-23 13:24:42', '1', '2014-12-23 13:24:42', '0');
INSERT INTO `move` VALUES ('12', 'xxxxxcvc6676mmmmmkkk', '0', null, null, null, '0', '0', '1', '2014-12-23 13:24:45', '1', '2014-12-23 13:24:45', '0');
INSERT INTO `move` VALUES ('13', 'xxx', '1', null, null, null, '0', '0', '1', '2014-12-23 14:51:17', '1', '2014-12-23 14:51:17', '0');
INSERT INTO `move` VALUES ('14', 'xxx', '1', null, null, null, '0', '0', '1', '2014-12-23 14:52:34', '1', '2014-12-23 14:52:34', '0');
INSERT INTO `move` VALUES ('15', 'xxx', '1', null, null, null, '0', '2', '1', '2014-12-23 14:52:58', '1', '2014-12-30 13:25:13', '40');
INSERT INTO `move` VALUES ('16', 'xxx', '1', null, null, null, '0', '0', '1', '2014-12-23 14:53:24', '1', '2014-12-23 14:53:24', '2');
INSERT INTO `move` VALUES ('17', 'xxx', '1', null, null, null, '0', '0', '1', '2014-12-23 14:54:45', '1', '2014-12-23 14:54:45', '2');
INSERT INTO `move` VALUES ('18', 'acaaasdasd', '1', null, null, null, '0', '0', '1', '2014-12-23 15:00:30', '1', '2014-12-23 15:00:30', '0');
INSERT INTO `move` VALUES ('19', 'acaaasdasd', '1', null, null, null, '0', '0', '1', '2014-12-23 15:00:48', '1', '2014-12-23 15:00:48', '0');
INSERT INTO `move` VALUES ('20', '1', '1', null, null, null, '0', '0', '1', '2014-12-23 16:13:30', '1', '2014-12-23 16:13:30', '0');
INSERT INTO `move` VALUES ('21', '发', '1', null, null, null, '0', '0', '1', '2014-12-24 21:55:09', '1', '2014-12-24 21:55:09', '0');
INSERT INTO `move` VALUES ('22', '人', '1', null, null, null, '0', '0', '1', '2014-12-24 21:56:15', '1', '2014-12-24 21:56:15', '0');
INSERT INTO `move` VALUES ('23', '方法', '1', null, null, null, '0', '0', '1', '2014-12-24 21:57:09', '1', '2014-12-24 21:57:09', '0');
INSERT INTO `move` VALUES ('24', '方法', '35', null, null, null, '0', '0', '1', '2014-12-24 21:58:35', '1', '2014-12-24 21:58:35', '0');

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
-- Records of movereply
-- ----------------------------
INSERT INTO `movereply` VALUES ('1', '15', null, '43', '0', '2014-12-30 13:24:25', '40', '2014-12-30 13:24:26', '40');
INSERT INTO `movereply` VALUES ('2', '15', 'tttttasdfs33333dfsdttt', '43', '0', '2014-12-30 13:25:13', '40', '2014-12-30 13:25:13', '40');

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
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COMMENT='消息评论表';

-- ----------------------------
-- Records of newcomment
-- ----------------------------
INSERT INTO `newcomment` VALUES ('44', '恭喜你，早日结婚', '59', null, '16', '2', '2015-01-03 20:12:20', '59', '2015-01-08 17:06:34', '57');
INSERT INTO `newcomment` VALUES ('45', null, null, null, null, null, null, null, null, '57');
INSERT INTO `newcomment` VALUES ('46', '必须当回事', '60', null, '17', '2', '2015-01-03 20:14:27', '60', '2015-01-08 17:08:36', '57');
INSERT INTO `newcomment` VALUES ('47', '那能当回事吗？', '61', null, '17', '1', '2015-01-03 20:14:53', '61', '2015-01-03 20:14:53', '61');
INSERT INTO `newcomment` VALUES ('57', '凤飞飞', '68', '/UpLoadFile/News/60dc358c-6cf5-4dfa-92fe-2b77ce84b754.jpg', '28', '1', '2015-01-05 15:02:02', null, '2015-01-05 15:02:02', null);
INSERT INTO `newcomment` VALUES ('58', '方法', '68', '/UpLoadFile/News/baa970c5-486d-4751-b27c-7d6d18fbbc7a.jpg', '28', '1', '2015-01-05 15:04:32', null, '2015-01-05 15:04:32', null);
INSERT INTO `newcomment` VALUES ('59', '方法', '68', '/UpLoadFile/News/baa970c5-486d-4751-b27c-7d6d18fbbc7a.jpg', '26', '1', '2015-01-05 15:04:51', null, '2015-01-05 15:04:51', null);
INSERT INTO `newcomment` VALUES ('60', '方法日日日', '68', '/UpLoadFile/News/91559c93-9081-4a27-94c9-7715456f2710.jpg', '26', '1', '2015-01-05 15:04:57', null, '2015-01-05 15:04:57', null);
INSERT INTO `newcomment` VALUES ('61', '飞', '68', '/UpLoadFile/News/35673a06-3e48-4db4-affe-84f0911aece7.jpg', '28', '1', '2015-01-05 15:09:58', null, '2015-01-05 15:09:58', null);
INSERT INTO `newcomment` VALUES ('62', '最后一个测试回复', '68', '/UpLoadFile/News/8205393e-d57e-4a88-9f19-03b6ca9501f4.jpg', '29', '1', '2015-01-05 15:13:56', '68', '2015-01-05 15:13:56', '68');
INSERT INTO `newcomment` VALUES ('63', '带图测试', '68', '/UpLoadFile/News/411a2d56-854f-4231-aae9-dfbc2133cd51.jpg', '31', '2', '2015-01-05 15:17:06', '68', '2015-01-08 17:06:23', '57');
INSERT INTO `newcomment` VALUES ('64', 'fdsfsdfsd', '57', null, '17', '1', '2015-01-07 16:54:59', '57', '2015-01-07 16:54:59', '57');
INSERT INTO `newcomment` VALUES ('65', 'sdfsdf', '57', null, '17', '1', '2015-01-07 17:25:51', '57', '2015-01-07 17:25:51', '57');
INSERT INTO `newcomment` VALUES ('66', 'afdfsdfsdf', '57', null, '36', '1', '2015-01-08 17:09:08', '57', '2015-01-08 17:09:08', '57');

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
  `SchoolId` int(11) default '0' COMMENT 'ѧУId',
  `DateTimeNow` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  PRIMARY KEY  (`NewsId`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='消息表';

-- ----------------------------
-- Records of newsfeed
-- ----------------------------
INSERT INTO `newsfeed` VALUES ('16', '57', '我准备在生日之前结婚大事给完成！', '\0', '2', '2', '1', '', '2', '\0', '03', '1', '1', '2015-01-03 19:59:35', '/UpLoadFile/News/39f569ad-f2d5-4c7e-9aea-71bce3155edf.jpg', '1', '2015-01-03 19:59:35', '57', '2015-01-08 14:26:51', '57', '13', '2015-01-09 10:18:23');
INSERT INTO `newsfeed` VALUES ('17', '58', '几个国国信办的主人请我吃饭，我会当回事？', '\0', '2', '2', '1', '', '2', '', '03', '1', '1', '2015-01-03 20:03:16', '/UpLoadFile/News/39f569ad-f2d5-4c7e-9aea-71bce3155edf.jpg', '1', '2015-01-03 20:03:16', '58', '2015-01-09 10:58:50', '57', '13', '1900-01-01 00:00:00');
INSERT INTO `newsfeed` VALUES ('30', '68', '最后测试', null, null, null, null, null, null, null, null, null, null, '2015-01-05 15:16:26', '/UpLoadFile/News/39f569ad-f2d5-4c7e-9aea-71bce3155edf.jpg', '1', null, '68', null, '68', '14', '1900-01-01 00:00:00');
INSERT INTO `newsfeed` VALUES ('31', '68', '最后带图测试', '', null, null, null, null, null, '', null, null, null, '2015-01-05 15:16:56', '/UpLoadFile/News/39f569ad-f2d5-4c7e-9aea-71bce3155edf.jpg', '1', null, '68', '2015-01-08 14:28:53', '57', '14', '1900-01-01 00:00:00');
INSERT INTO `newsfeed` VALUES ('32', '57', '凤飞飞凤飞飞', '', '0', '0', '1', '\0', '0', '\0', null, '1', '1', null, null, '1', '2015-01-07 14:34:12', '57', '2015-01-09 10:03:24', '57', '13', '2015-01-09 10:18:23');
INSERT INTO `newsfeed` VALUES ('33', '57', '发大发', '', '0', '0', '1', '\0', '0', '\0', null, '1', '1', null, null, '1', '2015-01-07 14:39:06', '57', '2015-01-08 14:26:23', '57', '15', '2015-01-09 10:18:23');
INSERT INTO `newsfeed` VALUES ('34', '57', 'dsfsdfsdfsdf', '', '0', '0', '1', '\0', '0', '\0', null, '1', '1', '2015-01-08 09:19:36', '/UpLoadFile/News/4715db46-91a5-486c-ab4d-ebd0381c2f86.jpg', '1', '2015-01-08 09:19:36', '57', '2015-01-09 10:02:23', '57', '13', '2015-01-09 10:18:23');
INSERT INTO `newsfeed` VALUES ('35', '57', '3434343', null, '0', '0', '1', '\0', '0', '', null, '1', '1', '2015-01-08 10:11:56', '/UpLoadFile/News/3d9b4e9c-5797-4eb4-abcf-f4b378884214.jpg', '1', '2015-01-08 10:11:56', '57', '2015-01-08 14:28:57', '57', '13', '2015-01-09 10:18:23');
INSERT INTO `newsfeed` VALUES ('36', '57', 'asdadasd', null, '0', '0', '1', '\0', '0', '\0', null, '1', '1', '2015-01-08 17:08:58', '/UpLoadFile/News/3b1f6eb5-f4b2-4fb4-98ad-4e338a0ab648.jpg', '1', '2015-01-08 17:08:58', '57', '2015-01-08 17:08:58', '57', '13', '2015-01-09 10:18:23');

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
  `SchoolId` int(11) default '0' COMMENT 'ѧУId',
  PRIMARY KEY  (`PartTimeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='兼职资料表';

-- ----------------------------
-- Records of parttimejob
-- ----------------------------
INSERT INTO `parttimejob` VALUES ('1', '兼职外卖', '工资日结', '4', '2', '3', '4.00', '/UpLoadFile/Job/cf33079d-2afb-4ab6-adb9-f8aa7060e6eb.jpg', '2', '2015-01-05 09:37:00', '68', '2015-01-05 09:37:18', '68', '赵金龙', '1515150931', '0');
INSERT INTO `parttimejob` VALUES ('2', '兼职搬运工', '工资日结', '1', 'JIALI', '3444', '4.00', '/UpLoadFile/Job/dd51c3a1-09e0-4590-b8f4-415abd5a81f5.jpg', '1', '2015-01-05 10:02:58', '68', '2015-01-08 16:37:37', '57', '赵金龙', '1515150931', '13');

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
  `SchoolId` int(11) default '0' COMMENT '学校Id',
  PRIMARY KEY  (`PraiseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞记录表';

-- ----------------------------
-- Records of praise
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='角色信息';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '大经理', '2', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', '-1');
INSERT INTO `role` VALUES ('2', '小主管de ', '2', '1900-01-01 00:00:00', '-1', '2015-01-09 14:51:25', '-1');
INSERT INTO `role` VALUES ('3', '大经理', null, null, '57', null, '57');
INSERT INTO `role` VALUES ('4', '大总裁', '1', '2015-01-09 14:51:12', '57', '2015-01-09 14:51:43', '57');
INSERT INTO `role` VALUES ('5', '大经理', '1', '2015-01-09 14:51:54', '57', '2015-01-09 14:51:54', '57');

-- ----------------------------
-- Table structure for rolemenumap
-- ----------------------------
DROP TABLE IF EXISTS `rolemenumap`;
CREATE TABLE `rolemenumap` (
  `Id` int(11) NOT NULL auto_increment,
  `RoleId` int(11) default NULL COMMENT '角色ID',
  `MenuId` int(11) default NULL COMMENT '菜单ID',
  `Status` int(11) default '1' COMMENT '记录状态',
  `CreateTime` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  `CreateUser` int(11) default '-1' COMMENT '创建用户',
  `LastChangeTime` datetime default '1900-01-01 00:00:00' COMMENT '最后更改时间',
  `LastChangeUser` int(11) default '-1' COMMENT '最后更改用户',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='角色菜单映射';

-- ----------------------------
-- Records of rolemenumap
-- ----------------------------
INSERT INTO `rolemenumap` VALUES ('9', '5', '11', '1', '2015-01-09 17:50:45', '57', '2015-01-09 17:50:58', '57');
INSERT INTO `rolemenumap` VALUES ('10', '5', '12', '1', '2015-01-09 17:50:49', '57', '2015-01-09 17:50:59', '57');

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
-- Records of systemparameters
-- ----------------------------
INSERT INTO `systemparameters` VALUES ('1', '推荐人-A(可设置多少人为推荐见)', '001', '5', '1', null, null, '2014-12-24 23:19:57', '35');
INSERT INTO `systemparameters` VALUES ('2', '推荐人-B(每次随机SHOW出多少推荐人)', '002', '10', '1', null, null, null, null);
INSERT INTO `systemparameters` VALUES ('3', '动弹分数-A(每天发几条动弹)', '003', '5', '1', null, null, null, null);
INSERT INTO `systemparameters` VALUES ('4', '动弹分数-B(如果达到A的条件就积分的分值)', '004', '10', '1', null, null, null, null);
INSERT INTO `systemparameters` VALUES ('5', '星星级别', '005', '5', '1', null, null, null, null);

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
  `SchoolId` int(11) default '0' COMMENT 'ѧУId',
  PRIMARY KEY  (`TakeAwayId`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='外卖资料表';

-- ----------------------------
-- Records of takeaway
-- ----------------------------
INSERT INTO `takeaway` VALUES ('32', '肯德基', '价格优惠', '/UpLoadFile/TakeOut/64c3d1ac-4360-40d6-8d61-db238ab15206.jpg', '2.0', '2.00', '2', '2', '2015-01-05 09:31:37', '68', '2015-01-05 09:31:56', '68', '0');
INSERT INTO `takeaway` VALUES ('33', '麦当劳', '价格便宜', '/UpLoadFile/TakeOut/9ca8069d-7642-4f21-a525-b1d42e123574.jpg', '4.0', '5.00', '123123', '1', '2015-01-05 10:02:38', '68', '2015-01-08 16:29:30', '57', '13');

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
  `DateTimeNow` datetime default '1900-01-01 00:00:00' COMMENT '创建时间',
  PRIMARY KEY  (`UserId`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COMMENT='用户账户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('57', 'test-001', 'e10adc3949ba59abbe56e057f20f883e', null, '1002100141', '13', '/UpLoadFile/Face/d30c42ce-b27b-4ade-989c-45ed431625ef.jpg', '456.jpg', '1', '3.00', '1.00', '0.00', '1.00', '1', '1', '', '1', '', '1', '1', '2015-01-03 19:41:56', '1', '2015-01-03 19:41:56', '1', '2015-01-09 10:20:02');
INSERT INTO `user` VALUES ('58', 'test-002', 'e10adc3949ba59abbe56e057f20f883e', null, '1002100142', '13', '123.jpg', '456.jpg', '1', '0.00', '0.00', '0.00', '0.00', '0', '1', '', '1', '大家都觉得本人比照片好看', '1', '1', '2015-01-03 19:43:04', '1', '2015-01-03 19:43:04', '1', '2015-01-09 10:20:15');
INSERT INTO `user` VALUES ('59', 'test-003', 'e10adc3949ba59abbe56e057f20f883e', null, '1002100143', '13', '/UpLoadFile/Face/d30c42ce-b27b-4ade-989c-45ed431625ef.jpg', '456.jpg', '1', '0.00', '0.00', '0.00', '0.00', '0', '0', '\0', '0', '', '1', '1', '2015-01-03 19:44:19', '1', '2015-01-03 19:44:19', '1', '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('60', 'test-004', 'e10adc3949ba59abbe56e057f20f883e', null, '1002100144', '13', '123.jpg', '456.jpg', '1', '0.00', '0.00', '0.00', '0.00', '0', '0', '\0', '0', '', '1', '1', '2015-01-03 19:44:26', '1', '2015-01-03 19:44:26', '1', '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('61', 'test-005', 'e10adc3949ba59abbe56e057f20f883e', null, '1002100145', '13', '123.jpg', '456.jpg', '1', '0.00', '0.00', '0.00', '0.00', '0', '0', '\0', '0', '', '1', '1', '2015-01-03 19:44:31', '1', '2015-01-03 19:44:31', '1', '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('62', 'test-006', 'e10adc3949ba59abbe56e057f20f883e', null, '1002100146', '13', '123.jpg', '456.jpg', '1', '0.00', '0.00', '0.00', '0.00', '0', '0', '', '0', '', '1', '1', '2015-01-03 19:44:34', '1', '2015-01-03 19:44:34', '1', '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('63', 'test-007', 'e10adc3949ba59abbe56e057f20f883e', null, '1002100147', '13', '123.jpg', '456.jpg', '1', '0.00', '0.00', '0.00', '0.00', '0', '0', '\0', '0', '', '1', '1', '2015-01-03 19:44:38', '1', '2015-01-03 19:44:38', '1', '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('64', 'test-008', 'e10adc3949ba59abbe56e057f20f883e', null, '1002100148', '13', '123.jpg', '456.jpg', '1', '0.00', '0.00', '0.00', '0.00', '0', '0', '\0', '0', '', '1', '1', '2015-01-03 19:44:42', '1', '2015-01-03 19:44:42', '1', '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('65', 'test-009', 'e10adc3949ba59abbe56e057f20f883e', null, '1002100149', '13', '123.jpg', '456.jpg', '1', '0.00', '0.00', '0.00', '0.00', '0', '0', '', '0', '', '1', '1', '2015-01-03 19:44:47', '1', '2015-01-03 19:44:47', '1', '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('66', 'test-010', 'e10adc3949ba59abbe56e057f20f883e', null, '1002100150', '13', '123.jpg', '456.jpg', '1', '0.00', '0.00', '0.00', '0.00', '0', '0', '\0', '0', '', '1', '1', '2015-01-03 19:44:53', '1', '2015-01-03 19:44:53', '1', '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('67', 'test-011', 'e10adc3949ba59abbe56e057f20f883e', null, '1002100146', '13', '123.jpg', '456.jpg', '1', '0.00', '0.00', '0.00', '0.00', '0', '0', '\0', '0', '', '1', '1', '2015-01-03 19:44:57', '1', '2015-01-03 19:44:57', '1', '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('68', '123', '123', '', '1002100146', '0', '', '', '1', '0.00', '0.00', '0.00', '0.00', '0', '', '\0', '', '', '0', '1', '1900-01-01 00:00:00', '-1', '1900-01-01 00:00:00', null, '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('69', '111', '111', null, '1002100146', '1', '~/UpLoadFile/Face/d30c42ce-b27b-4ade-989c-45ed431625ef.jpg', '~/UpLoadFile/Card/e084886e-55e1-49dc-b516-a3c53c30ff09.jpg', '1', null, null, null, null, null, null, '\0', null, null, null, null, '2015-01-05 15:53:50', null, null, null, '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('70', '1111', 'b59c67bf196a4758191e42f76670ceba', null, '1002100146', '1', '~/UpLoadFile/Face/ebdfbb70-a199-4ffc-a674-087fa8fc1c7a.jpg', '~/UpLoadFile/Card/14433e7f-e7da-4204-9a97-5b7bf69e29c0.jpg', '1', null, null, null, null, null, null, '\0', null, null, null, null, '2015-01-05 15:58:54', null, null, null, '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('71', '22', 'b6d767d2f8ed5d21a44b0e5886680cb9', null, '1002100146', '1', '~/UpLoadFile/Face/a50b6213-2036-4163-b76e-bbfbea685303.jpg', '~/UpLoadFile/Card/e10b523b-41db-4467-b7e2-e46a760659b6.jpg', '1', null, null, null, null, null, null, '\0', null, null, null, '1', '2015-01-05 16:01:18', null, null, null, '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('72', '4444444444444444', '550a141f12de6341fba65b0ad0433500', null, '1002100146', '1', '~/UpLoadFile/Face/90d16606-8c9e-4be0-9d3b-78b88bed0d16.jpg', '~/UpLoadFile/Card/5e6bbbc7-ae7a-4310-89a0-9cbfa2f2c716.jpg', '2', null, null, null, null, null, null, '\0', null, null, '2', '1', '2015-01-05 16:04:05', '68', '2015-01-06 16:24:44', '57', '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('73', 'ddd', '202cb962ac59075b964b07152d234b70', null, '1002100146', '2', '~/UpLoadFile/Face/db608745-935c-4715-a7df-ad412c300ffa.jpg', '~/UpLoadFile/Card/fc6d3630-f2ca-48c0-bf03-3fae319c52f5.jpg', '1', null, null, null, null, null, null, '', null, null, '4', '1', '2015-01-06 16:27:00', '57', '2015-01-06 16:27:00', '57', '1900-01-01 00:00:00');
INSERT INTO `user` VALUES ('74', '123', '202cb962ac59075b964b07152d234b70', null, '1002100146', '13', '~/UpLoadFile/Face/46ef23c2-bca3-42c8-989c-435ba08e5d35.jpg', '~/UpLoadFile/Card/e41f29d7-9041-46f7-9510-1ee028a3ad9d.jpg', '1', '0.00', '0.00', '0.00', '0.00', '0', '0', '', '0', null, '1', '1', '2015-01-06 16:59:06', '57', '2015-01-06 16:59:06', '57', '1900-01-01 00:00:00');

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
-- Records of useractivitymap
-- ----------------------------
INSERT INTO `useractivitymap` VALUES ('1', '2', '1');
INSERT INTO `useractivitymap` VALUES ('2', '18', '43');
INSERT INTO `useractivitymap` VALUES ('3', '19', '5');

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
-- Records of userrecommendmap
-- ----------------------------

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

-- ----------------------------
-- Records of userrolemap
-- ----------------------------
