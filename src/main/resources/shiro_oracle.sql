DROP TABLE sys_menu;
CREATE TABLE sys_menu (
  menu_id NUMBER NOT NULL,
  parent_id NUMBER DEFAULT NULL ,
  NAME VARCHAR2(50) DEFAULT NULL,
  URL VARCHAR2(200) DEFAULT NULL,
  perms VARCHAR2(500) DEFAULT NULL,
  TYPE NUMBER(11) DEFAULT NULL,
  icon varchar(50) DEFAULT NULL,
  order_num NUMBER(11) DEFAULT NULL,
  PRIMARY KEY (menu_id)
) ;
COMMENT ON TABLE sys_menu IS 
'菜单管理';
COMMENT ON COLUMN  sys_menu.menu_id IS
'id';
COMMENT ON COLUMN sys_menu.parent_id IS
'父菜单ID，一级菜单为0';
COMMENT ON COLUMN sys_menu.NAME IS
'菜单名称';
COMMENT ON COLUMN sys_menu.URL IS
'菜单URL';
COMMENT ON COLUMN sys_menu.perms IS
'授权(多个用逗号分隔，如：user:list,user:create)';
COMMENT ON COLUMN sys_menu.type IS
'类型   0：目录   1：菜单   2：按钮';
COMMENT ON COLUMN sys_menu.icon IS
'菜单图标';
COMMENT ON COLUMN sys_menu.order_num IS
'排序';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO sys_menu VALUES ('1', '0', '权限菜单', 'menu/list', 'sys:user:shiro', '0', 'system', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE sys_role;
CREATE TABLE sys_role (
  role_id NUMBER NOT NULL,
  role_name VARCHAR2(100) DEFAULT NULL,
  remark VARCHAR2(100) DEFAULT NULL,
  create_user_id NUMBER(20) DEFAULT NULL,
  create_time DATE DEFAULT NULL,
  PRIMARY KEY (role_id)
);

COMMENT ON TABLE sys_role IS
'角色';
COMMENT ON COLUMN sys_role.role_id IS
'id';
COMMENT ON COLUMN sys_role.role_name IS
'角色名称';
COMMENT ON COLUMN sys_role.remark IS
'备注';
COMMENT ON COLUMN sys_role.create_user_id IS
'创建者ID';
COMMENT ON COLUMN sys_role.create_time IS
'创建时间';

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE  sys_role_menu;
CREATE TABLE sys_role_menu (
  ID NUMBER(20) NOT NULL,
  role_id NUMBER(20) DEFAULT NULL,
  menu_id NUMBER(20) DEFAULT NULL,
  PRIMARY KEY (ID)
) ;
COMMENT ON TABLE sys_role_menu IS 
'角色与菜单对应关系';
COMMENT ON COLUMN sys_role_menu.ID
IS 'id';
COMMENT ON COLUMN sys_role_menu.role_id
IS '角色ID';
COMMENT ON COLUMN sys_role_menu.menu_id
IS '菜单ID';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
  user_id NUMBER(20) NOT NULL,
  username VARCHAR2(50) NOT NULL,
  PASSWORD VARCHAR2(100) DEFAULT NULL,
  SALT VARCHAR2(20) DEFAULT NULL,
  email VARCHAR2(100) DEFAULT NULL,
  mobile VARCHAR2(100) DEFAULT NULL,
  status NUMBER DEFAULT NULL,
  create_user_id NUMBER(20) DEFAULT NULL,
  create_time DATE DEFAULT NULL,
  PRIMARY KEY (user_id)
) ;

COMMENT ON TABLE sys_user IS 
'系统用户';
COMMENT ON COLUMN sys_user.user_id IS
'id';
COMMENT ON COLUMN sys_user.username IS
'用户名';
COMMENT ON COLUMN sys_user.PASSWORD IS
'密码';
COMMENT ON COLUMN sys_user.SALT IS
'盐';
COMMENT ON COLUMN sys_user.email IS
'邮箱';
COMMENT ON COLUMN sys_user.mobile IS
'手机号';
COMMENT ON COLUMN sys_user.status IS
'状态  0：禁用   1：正常';
COMMENT ON COLUMN sys_user.create_user_id IS
'创建者ID';
COMMENT ON COLUMN sys_user.create_time IS
'创建时间';
-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO sys_user VALUES ('1', 'admin', '3743a4c09a17e6f2829febd09ca54e627810001cf255ddcae9dabd288a949c4a', '123', 'cicada@163.com', '18967835678', '1', '1', DATE'2021-01-18');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role (
  ID NUMBER(20) NOT NULL,
  user_id NUMBER(20) DEFAULT NULL ,
  role_id NUMBER(20) DEFAULT NULL,
  PRIMARY KEY (ID)
);
COMMENT ON TABLE sys_user_role IS
'用户与角色对应关系';
COMMENT ON COLUMN sys_user_role.ID  IS
'id';
COMMENT ON COLUMN sys_user_role.user_id  IS
'用户ID';
COMMENT ON COLUMN sys_user_role.role_id  IS
'角色ID';
