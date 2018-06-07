CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `seq` int(5) DEFAULT NULL COMMENT '显示顺序',
  `status` int(5) DEFAULT NULL COMMENT '状态：0 启用 1停用',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单地址',
  `levele` int(5) DEFAULT NULL COMMENT '层级',
  `pid` int(11) DEFAULT NULL COMMENT '父级id',
  `description` varchar(255) DEFAULT NULL COMMENT '菜单描述',
  `add_user` varchar(50) DEFAULT NULL COMMENT '新增人',
  `add_date` datetime DEFAULT NULL COMMENT '新增时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '最后修改人',
  `update_date` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;