CREATE TABLE `t_role` (
  `id` int(16) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `status` int(11) DEFAULT NULL COMMENT '状态：0 启用; 1 禁用',
  `add_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

