-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sort` tinyint(4) NULL DEFAULT NULL COMMENT '排序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片路径',
  `created_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除（0:未删除;1:已删除;）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '轮播图表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '企业名称',
  `set_time` date NULL DEFAULT NULL COMMENT '企业创立时间',
  `emp_num` int(11) NULL DEFAULT NULL COMMENT '企业人数',
  `core_business` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主营业务',
  `contact_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `is_read` tinyint(4) NULL DEFAULT 0 COMMENT '是否已读（0:未读;1:已读;）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注信息',
  `created_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除（0:未删除;1:已删除;）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '入孵申请信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company
-- ----------------------------

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `pid` bigint(11) NULL DEFAULT NULL COMMENT '父级字典',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `tips` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '提示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES (16, 0, 0, '状态', NULL);
INSERT INTO `dict` VALUES (17, 1, 16, '启用', NULL);
INSERT INTO `dict` VALUES (18, 2, 16, '禁用', NULL);
INSERT INTO `dict` VALUES (29, 0, 0, '性别', NULL);
INSERT INTO `dict` VALUES (30, 1, 29, '男', NULL);
INSERT INTO `dict` VALUES (31, 2, 29, '女', NULL);
INSERT INTO `dict` VALUES (35, 0, 0, '账号状态', NULL);
INSERT INTO `dict` VALUES (36, 1, 35, '启用', NULL);
INSERT INTO `dict` VALUES (37, 2, 35, '冻结', NULL);
INSERT INTO `dict` VALUES (38, 3, 35, '已删除', NULL);
INSERT INTO `dict` VALUES (39, 0, 0, '留言来源', NULL);
INSERT INTO `dict` VALUES (40, 1, 39, '底部导航', NULL);
INSERT INTO `dict` VALUES (41, 0, 0, '新闻栏目', NULL);
INSERT INTO `dict` VALUES (42, 1, 41, '新闻资讯', NULL);
INSERT INTO `dict` VALUES (43, 2, 41, '中心动态', NULL);
INSERT INTO `dict` VALUES (44, 3, 41, '重要通知', NULL);
INSERT INTO `dict` VALUES (45, 0, 0, '政策级别', NULL);
INSERT INTO `dict` VALUES (46, 1, 45, '国家', NULL);
INSERT INTO `dict` VALUES (47, 2, 45, '省级', NULL);
INSERT INTO `dict` VALUES (48, 3, 45, '市级', NULL);
INSERT INTO `dict` VALUES (49, 4, 45, '区县级', NULL);

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `id` bigint(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '日志名称',
  `userid` bigint(65) NULL DEFAULT NULL COMMENT '管理员id',
  `createtime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否执行成功',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '具体消息',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1065 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '登录记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_log
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(65) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'url地址',
  `num` int(65) NULL DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(65) NULL DEFAULT NULL COMMENT '角色层级',
  `ismenu` int(11) NULL DEFAULT NULL COMMENT '是否是菜单（1：是  0：不是）',
  `tips` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int(65) NULL DEFAULT NULL COMMENT '菜单状态 :  1:启用   0:不启用',
  `isopen` int(11) NULL DEFAULT NULL COMMENT '是否打开:    1:打开   0:不打开',
  `is_admin` int(11) NULL DEFAULT 0 COMMENT '是否管理员专用:    1:是   0:不是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 189 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (106, 'mgr', '0', '[0],', '用户管理', '', '/mgr', 5, 1, 1, NULL, 1, 0, 1);
INSERT INTO `menu` VALUES (107, 'mgr_add', 'mgr', '[0],[mgr],', '添加用户', '', '/mgr/add', 1, 2, 0, NULL, 1, 0, 1);
INSERT INTO `menu` VALUES (108, 'mgr_list', 'mgr', '[0],[mgr],', '用户列表', '', '/mgr/list', 1, 2, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (109, 'mgr_reset', 'mgr', '[0],[mgr],', '密码重置', '', '/mgr/reset', 1, 2, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (110, 'to_mgr_user_edit', 'mgr', '[0],[mgr],', '跳转编辑', '', '/mgr/user_edit', 1, 2, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (111, 'mgr_freeze', 'mgr', '[0],[mgr],', '冻结用户', '', '/mgr/freeze', 1, 2, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (112, 'mgr_unfreeze', 'mgr', '[0],[mgr],', '解冻用户', '', '/mgr/unfreeze', 1, 2, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (114, 'role', 'dev', '[0],[dev],', '角色管理', '', '/role', 2, 2, 1, NULL, 1, 0, 1);
INSERT INTO `menu` VALUES (115, 'role_add', 'role', '[0],[dev],[role],', '添加角色', '', '/role/add', 1, 3, 0, NULL, 1, 0, 1);
INSERT INTO `menu` VALUES (116, 'role_edit', 'role', '[0],[dev],[role],', '修改角色', '', '/role/edit', 2, 3, 0, NULL, 1, 0, 1);
INSERT INTO `menu` VALUES (117, 'role_remove', 'role', '[0],[dev],[role],', '删除角色', '', '/role/remove', 3, 3, 0, NULL, 1, 0, 1);
INSERT INTO `menu` VALUES (118, 'role_setAuthority', 'role', '[0],[dev],[role],', '配置权限', '', '/role/setAuthority', 4, 3, 0, NULL, 1, 0, 1);
INSERT INTO `menu` VALUES (119, 'menu', 'dev', '[0],[dev],', '菜单管理', '', '/menu', 4, 2, 1, NULL, 1, 0, 1);
INSERT INTO `menu` VALUES (120, 'menu_add', 'menu', '[0],[dev],[menu],', '添加菜单', '', '/menu/add', 1, 3, 0, NULL, 1, 0, 1);
INSERT INTO `menu` VALUES (121, 'menu_edit', 'menu', '[0],[dev],[menu],', '修改菜单', '', '/menu/edit', 2, 3, 0, NULL, 1, 0, 1);
INSERT INTO `menu` VALUES (122, 'menu_remove', 'menu', '[0],[dev],[menu],', '删除菜单', '', '/menu/remove', 3, 3, 0, NULL, 1, 0, 1);
INSERT INTO `menu` VALUES (128, 'log', 'dev', '[0],[dev],', '业务日志', '', '/log', 6, 2, 1, NULL, 1, 0, 1);
INSERT INTO `menu` VALUES (132, 'dict', 'dev', '[0],[dev],', '字典管理', '', '/dict', 4, 2, 1, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (133, 'loginLog', 'dev', '[0],[dev],', '登录日志', '', '/loginLog', 6, 2, 1, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (134, 'log_clean', 'log', '[0],[dev],[log],', '清空日志', '', '/log/delLog', 3, 3, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (141, 'notice', 'dev', '[0],[dev],', '通知管理', '', '/notice', 9, 2, 1, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (150, 'to_menu_edit', 'menu', '[0],[dev],[menu],', '菜单编辑跳转', '', '/menu/menu_edit', 4, 3, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (151, 'menu_list', 'menu', '[0],[dev],[menu],', '菜单列表', '', '/menu/list', 5, 3, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (158, 'log_list', 'log', '[0],[dev],[log],', '日志列表', '', '/log/list', 2, 3, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (159, 'log_detail', 'log', '[0],[dev],[log],', '日志详情', '', '/log/detail', 3, 3, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (160, 'del_login_log', 'loginLog', '[0],[dev],[loginLog],', '清空登录日志', '', '/loginLog/delLoginLog', 1, 3, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (161, 'login_log_list', 'loginLog', '[0],[dev],[loginLog],', '登录日志列表', '', '/loginLog/list', 2, 3, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (162, 'to_role_edit', 'role', '[0],[dev],[role],', '修改角色跳转', '', '/role/role_edit', 5, 3, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (163, 'to_role_assign', 'role', '[0],[dev],[role],', '角色分配跳转', '', '/role/role_assign', 6, 3, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (164, 'role_list', 'role', '[0],[dev],[role],', '角色列表', '', '/role/list', 7, 3, 0, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (168, 'user_info', '0', '[0],', '个人中心', '', '/mgr/user_info', 5, 1, 1, NULL, 1, NULL, 0);
INSERT INTO `menu` VALUES (169, 'material', '0', '[0],', '素材管理', '', '/', 1, 1, 1, NULL, 1, NULL, 0);
INSERT INTO `menu` VALUES (170, 'news', 'material', '[0],[material],', '新闻动态', '', '/news', 1, 2, 1, NULL, 1, NULL, 0);
INSERT INTO `menu` VALUES (180, 'company', '0', '[0],', '入孵管理', '', '/company', 4, 1, 1, NULL, 1, NULL, 0);
INSERT INTO `menu` VALUES (181, 'dev', '0', '[0],', '开发管理', '', '/', 7, 1, 1, NULL, 1, NULL, 1);
INSERT INTO `menu` VALUES (182, 'policy', 'material', '[0],[material],', '招商政策', '', '/policy', 2, 2, 1, NULL, 1, NULL, 0);
INSERT INTO `menu` VALUES (183, 'recruit', 'material', '[0],[material],', '人才招聘', '', '/recruit', 3, 2, 1, NULL, 1, NULL, 0);
INSERT INTO `menu` VALUES (189, 'banner', 'material', '[0],[material],', '轮播图', '', '/banner', 4, 2, 1, NULL, 1, NULL, 0);

-- ----------------------------
-- Table structure for news_info
-- ----------------------------
DROP TABLE IF EXISTS `news_info`;
CREATE TABLE `news_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `news_type` tinyint(4) NOT NULL COMMENT '栏目类型(1:新闻动态;2:媒体报道;3:合作交流;)',
  `image_news` tinyint(1) NOT NULL COMMENT '是否图片新闻（0:不是;1:是;）',
  `main_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '正文内容',
  `extra_file` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '附件文件',
  `file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '附件文件名',
  `show_time` datetime(0) NOT NULL COMMENT '展示时间',
  `cover_image` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '封面图片',
  `is_publish` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否发布（0:未发布;1:已发布;）',
  `created_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除（0:未删除;1:已删除;）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '新闻资讯表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news_info
-- ----------------------------

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `createtime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creater` bigint(11) NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '通知表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (6, '世界', 10, '欢迎使用\n\n<!--StartFragment-->上饶市创新创业产业孵化中心后台管理系统<!--EndFragment-->', '2017-01-11 08:53:20', 1);
INSERT INTO `notice` VALUES (8, '你好', NULL, '你好<p><br></p>', '2017-05-10 19:28:57', 1);

-- ----------------------------
-- Table structure for operation_log
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log`  (
  `id` bigint(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logtype` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '日志类型',
  `logname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '日志名称',
  `userid` bigint(65) NULL DEFAULT NULL COMMENT '用户id',
  `classname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类名称',
  `method` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '方法名称',
  `createtime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否成功',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1138 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for policy
-- ----------------------------
DROP TABLE IF EXISTS `policy`;
CREATE TABLE `policy`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `level` smallint(6) NOT NULL COMMENT '级别',
  `title` varchar(240) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `source` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '来源',
  `publish_date` date NOT NULL COMMENT '发布时间',
  `attachment_name` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '附件名称',
  `attachment` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '附件',
  `status` tinyint(1) NOT NULL DEFAULT 0,
  `created_user` bigint(20) NOT NULL,
  `created_time` datetime(0) NOT NULL,
  `modified_user` bigint(20) NULL DEFAULT NULL,
  `modified_time` datetime(0) NULL DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '政策' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of policy
-- ----------------------------

-- ----------------------------
-- Table structure for policy_content
-- ----------------------------
DROP TABLE IF EXISTS `policy_content`;
CREATE TABLE `policy_content`  (
  `id` bigint(20) NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '政策内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '政策内容' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of policy_content
-- ----------------------------

-- ----------------------------
-- Table structure for recruit
-- ----------------------------
DROP TABLE IF EXISTS `recruit`;
CREATE TABLE `recruit`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(240) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `company` varchar(240) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `publish_datetime` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `attachment_name` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '附件名称',
  `attachment` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `show_datetime` datetime(0) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT 0,
  `created_user` bigint(20) NOT NULL,
  `created_time` datetime(0) NOT NULL,
  `modified_user` bigint(20) NULL DEFAULT NULL,
  `modified_time` datetime(0) NULL DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '招聘' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recruit
-- ----------------------------

-- ----------------------------
-- Table structure for relation
-- ----------------------------
DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menuid` bigint(11) NULL DEFAULT NULL COMMENT '菜单id',
  `roleid` bigint(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4392 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of relation
-- ----------------------------
INSERT INTO `relation` VALUES (3967, 106, 1);
INSERT INTO `relation` VALUES (3968, 107, 1);
INSERT INTO `relation` VALUES (3978, 114, 1);
INSERT INTO `relation` VALUES (3979, 115, 1);
INSERT INTO `relation` VALUES (3980, 116, 1);
INSERT INTO `relation` VALUES (3981, 117, 1);
INSERT INTO `relation` VALUES (3982, 118, 1);
INSERT INTO `relation` VALUES (3983, 162, 1);
INSERT INTO `relation` VALUES (3984, 163, 1);
INSERT INTO `relation` VALUES (3985, 164, 1);
INSERT INTO `relation` VALUES (3986, 119, 1);
INSERT INTO `relation` VALUES (3987, 120, 1);
INSERT INTO `relation` VALUES (3988, 121, 1);
INSERT INTO `relation` VALUES (3989, 122, 1);
INSERT INTO `relation` VALUES (3990, 150, 1);
INSERT INTO `relation` VALUES (3991, 151, 1);
INSERT INTO `relation` VALUES (3992, 128, 1);
INSERT INTO `relation` VALUES (3993, 134, 1);
INSERT INTO `relation` VALUES (3994, 158, 1);
INSERT INTO `relation` VALUES (3995, 159, 1);
INSERT INTO `relation` VALUES (4004, 132, 1);
INSERT INTO `relation` VALUES (4011, 133, 1);
INSERT INTO `relation` VALUES (4012, 160, 1);
INSERT INTO `relation` VALUES (4013, 161, 1);
INSERT INTO `relation` VALUES (4014, 141, 1);
INSERT INTO `relation` VALUES (4019, 168, 1);
INSERT INTO `relation` VALUES (4020, 169, 1);
INSERT INTO `relation` VALUES (4021, 170, 1);
INSERT INTO `relation` VALUES (4031, 180, 1);
INSERT INTO `relation` VALUES (4078, 181, 1);
INSERT INTO `relation` VALUES (4343, 182, 1);
INSERT INTO `relation` VALUES (4344, 183, 1);
INSERT INTO `relation` VALUES (4345, 108, 1);
INSERT INTO `relation` VALUES (4347, 109, 1);
INSERT INTO `relation` VALUES (4348, 110, 1);
INSERT INTO `relation` VALUES (4355, 111, 1);
INSERT INTO `relation` VALUES (4356, 112, 1);
INSERT INTO `relation` VALUES (4378, 106, 5);
INSERT INTO `relation` VALUES (4379, 107, 5);
INSERT INTO `relation` VALUES (4380, 108, 5);
INSERT INTO `relation` VALUES (4381, 109, 5);
INSERT INTO `relation` VALUES (4382, 110, 5);
INSERT INTO `relation` VALUES (4383, 111, 5);
INSERT INTO `relation` VALUES (4384, 112, 5);
INSERT INTO `relation` VALUES (4385, 168, 5);
INSERT INTO `relation` VALUES (4386, 169, 5);
INSERT INTO `relation` VALUES (4387, 170, 5);
INSERT INTO `relation` VALUES (4388, 180, 5);
INSERT INTO `relation` VALUES (4389, 182, 5);
INSERT INTO `relation` VALUES (4390, 183, 5);
INSERT INTO `relation` VALUES (4392, 189, 1);
INSERT INTO `relation` VALUES (4393, 189, 5);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) NULL DEFAULT NULL COMMENT '序号',
  `pid` bigint(11) NULL DEFAULT NULL COMMENT '父角色id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色名称',
  `tips` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '提示',
  `status` int(11) NULL DEFAULT 1 COMMENT '角色的状态 :  1:启用   2:禁用\',',
  `version` int(11) NULL DEFAULT NULL COMMENT '保留字段(暂时没用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 1, 0, '超级管理员', 'administrator', 1, NULL);
INSERT INTO `role` VALUES (5, NULL, NULL, 'subAdmin', '管理员', 1, NULL);

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_user
-- ----------------------------
INSERT INTO `role_user` VALUES (1, 1, 1);
INSERT INTO `role_user` VALUES (4, 5, 4);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `account` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'md5密码盐',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名字',
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',
  `createtime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `version` int(11) NULL DEFAULT NULL COMMENT '保留字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '91da7f95-29d8-4ad0-a206-04eed55f6add.jpg', 'admin', '6c0359f2fc2565ebf1ba7934a5004bfb', '8pgby', '开发者', 'sn93@qq.com', '18272001234', 1, '2016-01-29 08:49:53', 25);
INSERT INTO `user` VALUES (4, NULL, 'subadmin', '81653222b396169243a7e18f6a6fda0f', 'e3lda', '管理员', '', '', 1, '2019-08-22 17:59:40', NULL);