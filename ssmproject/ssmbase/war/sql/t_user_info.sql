/** 用户信息表  */
CREATE TABLE `t_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `realname` varchar(50) DEFAULT NULL COMMENT '真实名',
  `status` int(11) DEFAULT NULL COMMENT '状态0：启用；1：禁用',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `add_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

INSERT INTO `t_user_info` (`password`, `realname`, `status`, `username`, `phone`, `add_user`, `add_date`, `update_user`, `update_date`) 
VALUES ('e10adc3949ba59abbe56e057f20f883e', '管理员', 0, 'ADMIN', NULL, NULL, NULL, NULL, NULL);




