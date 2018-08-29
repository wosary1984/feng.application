--admin user
insert into t_user(userId, username, password,role,locked,email,granttype, firstname,lastname) values(3,'feng','$2a$10$trT3.R/Nfey62eczbKEnueTcIbJXW.u1ffAo/XfyLpofwNDbEB86O','ROLE_BASIC',false,'bruce.wang01@sap.com','PRIVILEGE','bruce','wang');
insert into t_user(userId, username, password,role,locked,email,granttype) values(1,'user','$2a$10$trT3.R/Nfey62eczbKEnueTcIbJXW.u1ffAo/XfyLpofwNDbEB86O','ADMIN',false,'273630150@qq.com','ROLE');
insert into t_user(userId, username, password,role,locked,email,granttype) values(2,'bruce','$2a$10$trT3.R/Nfey62eczbKEnueTcIbJXW.u1ffAo/XfyLpofwNDbEB86O','USER',true,'123@132.com','PRIVILEGE');

insert into t_util(id, categorykey, categoryname,shortname,fullname) values(1,'jobClass','jobClass','HelloJob','feng.sport.service.jobs.HelloJob');

--permission list discard
insert into t_permission(id,name, description, url,method,type) values(1,'P1','Permission#1','/api/users','GET','ACTION');
insert into t_permission(id,name, description, url,method,type) values(2,'P2','Permission#2','/api/roles','GET','ACTION');
insert into t_permission(id,name, description, url,method,type) values(3,'HOME','Access Home Function','','','VIEW');
insert into t_permission(id,name, description, url,method,type) values(4,'USER','Access USER Management Function','','','VIEW');
insert into t_permission(id,name, description, url,method,type) values(5,'SCHEDULER','Access SCHEDULER Function','','','VIEW');
insert into t_permission(id,name, description, url,method,type) values(6,'HOME1','Home WOC','','','VIEW');
insert into t_permission(id,name, description, url,method,type) values(7,'USER1','USER WOC','','','VIEW');
insert into t_permission(id,name, description, url,method,type) values(8,'SCHEDULER1','SCHEDULER WOC','','','VIEW');

--privilege
insert into t_privilege(fid,icon,link,name,subview,parentid) values(11,'fa fa-circle-o text-red','/starter','Home',false,null);
		insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(111,'/my/sesssion','GET',11);
		insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(112,'/my/privileges','GET',11);

insert into t_privilege(fid,icon,link,name,subview,parentid) values(12,'fa fa-dashboard','','Sales',true,null);
insert into t_privilege(fid,icon,link,name,subview,parentid) values(121,'','','获取远程数据列表',false,12);
insert into t_privilege(fid,icon,link,name,subview,parentid) values(122,'','','基础数据类别',false,12);
insert into t_privilege(fid,icon,link,name,subview,parentid) values(123,'','','类别详细页面',false,12);

insert into t_privilege(fid,icon,link,name,subview,parentid) values(13,'fa fa-files-o','','Customer',true,null);
insert into t_privilege(fid,icon,link,name,subview,parentid) values(131,'','','获取远程数据列表',false,13);
insert into t_privilege(fid,icon,link,name,subview,parentid) values(132,'','','基础数据类别',false,13);
insert into t_privilege(fid,icon,link,name,subview,parentid) values(133,'','','类别详细页面',false,13);

insert into t_privilege(fid,icon,link,name,subview,parentid) values(14,'fa fa-building','/org','Company',true,null);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(141,'/api/org/company','GET',14);
			
	insert into t_privilege(fid,icon,link,name,subview,parentid) values(141,'fa fa-cubes','/org/companylist','Setup Company',false,14);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(1411,'/api/org/company/*','GET',141);
			
		insert into t_privilege(fid,icon,link,name,subview,parentid) values(1411,'','/org/departmentlist','Get Departments',false,141);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(14111,'/api/org/department','GET',1411);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(14112,'/api/org/department/*','GET',1411);
			
		insert into t_privilege(fid,icon,link,name,subview,parentid) values(1412,'','/org/department','Create Department',false,141);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(14121,'/api/org/department','GET',1412);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(14122,'/api/org/department','POST',1412);
			
		insert into t_privilege(fid,icon,link,name,subview,parentid) values(1413,'','/org/company','Create Company',false,141);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(14131,'/api/org/company','POST',1413);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(14132,'/api/org/company/*','POST',1413);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(14133,'/api/org/company/logo/*','POST',1413);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(14134,'/api/org/company/logo/*','GET',1413);
			
	insert into t_privilege(fid,icon,link,name,subview,parentid) values(142,'fa fa-sitemap','/org','Org Map',false,14);
	
	insert into t_privilege(fid,icon,link,name,subview,parentid) values(143,'fa fa-id-card-o','/org/employees','Employee',false,14);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(1431,'/api/org/employees','GET',143,'get all employees');
		insert into t_privilege(fid,icon,link,name,subview,parentid) values(1431,'','/org/employee','New Employee',false,143);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(14311,'/api/org/employees/*','GET',1431,'get employee');
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(14312,'/api/org/employees','POST',1431,'create employee');
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(14313,'/api/org/employees/*','POST',1431,'update employee');

insert into t_privilege(fid,icon,link,name,subview,parentid) values(15,'fa fa-users','/system','Administration',true,null);
	insert into t_privilege(fid,icon,link,name,subview,parentid) values(151,'fa fa-users','/system/users','System User',true,15);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(1511,'/api/sys/users','GET',151);
		insert into t_privilege(fid,icon,link,name,subview,parentid) values(1511,'','/system/user/edituser','Edit and Create User',false,151);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(15111,'/api/sys/users','GET',1511,'get all users');
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(15112,'/api/sys/user/*','GET',1511,'get user');
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(15113,'/api/sys/user/*','POST',1511,'edit user');
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(15114,'/api/sys/roles','GET',1511,'get all roles');
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(15115,'/api/sys/privileges','GET',1511,'get all privileges');
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(15116,'/api/sys/user','POST',1511,'create user');
			
	insert into t_privilege(fid,icon,link,name,subview,parentid) values(152,'fa fa-user-secret','/system/roles','System Role',false,15);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(1521,'/api/sys/roles','GET',152,'get all roles');
		insert into t_privilege(fid,icon,link,name,subview,parentid) values(1521,'','/system/roles/editrole','Edit and Create Role',false,152);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(15211,'/api/sys/roles','GET',1521,'get all roles');
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(15212,'/api/sys/role/*','GET',1521,'get role');
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(15213,'/api/sys/role/*','POST',1521,'edit role');
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid,description) values(15214,'/api/sys/role','POST',1521,'create role');

	insert into t_privilege(fid,icon,link,name,subview,parentid) values(153,'fa fa-list-alt','/system/privileges','System Privilege',false,15);

insert into t_privilege(fid,icon,link,name,subview,parentid) values(16,'fa fa fa-tasks','/cookie','System Cookie',true,null);
insert into t_privilege(fid,icon,link,name,subview,parentid) values(161,'','/cookie/jobs','Schedulers',false,16);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(1611,'/api/cookie/jobs','GET',161);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(1612,'/api/cookie/addjob','POST',161);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(1613,'/api/cookie/resumejob','POST',161);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(1614,'/api/cookie/pausejob','POST',161);
			insert into t_privilege_action(actionid,actionpath,actionmethod,fid) values(1615,'/api/cookie/deletejob','POST',161);


--user mapped privilege
insert into t_user_ref_privilege(id,userid,fid) values(1,3,11);

insert into t_user_ref_privilege(id,userid,fid) values(2,3,12);
insert into t_user_ref_privilege(id,userid,fid) values(21,3,121);
insert into t_user_ref_privilege(id,userid,fid) values(22,3,122);
insert into t_user_ref_privilege(id,userid,fid) values(23,3,123);

insert into t_user_ref_privilege(id,userid,fid) values(3,3,13);
insert into t_user_ref_privilege(id,userid,fid) values(31,3,131);
insert into t_user_ref_privilege(id,userid,fid) values(32,3,132);
insert into t_user_ref_privilege(id,userid,fid) values(33,3,133);

insert into t_user_ref_privilege(id,userid,fid) values(4,3,14);
insert into t_user_ref_privilege(id,userid,fid) values(41,3,141);
insert into t_user_ref_privilege(id,userid,fid) values(411,3,1411);
insert into t_user_ref_privilege(id,userid,fid) values(412,3,1412);
insert into t_user_ref_privilege(id,userid,fid) values(413,3,1413);
insert into t_user_ref_privilege(id,userid,fid) values(42,3,142);
insert into t_user_ref_privilege(id,userid,fid) values(43,3,143);
insert into t_user_ref_privilege(id,userid,fid) values(431,3,1431);

insert into t_user_ref_privilege(id,userid,fid) values(5,3,15);
insert into t_user_ref_privilege(id,userid,fid) values(51,3,151);
insert into t_user_ref_privilege(id,userid,fid) values(511,3,1511);
insert into t_user_ref_privilege(id,userid,fid) values(52,3,152);
insert into t_user_ref_privilege(id,userid,fid) values(521,3,1521);
insert into t_user_ref_privilege(id,userid,fid) values(53,3,153);

insert into t_user_ref_privilege(id,userid,fid) values(6,3,16);
insert into t_user_ref_privilege(id,userid,fid) values(61,3,161);

--- id generator
INSERT INTO t_id_generator(id, pk_name, pk_value) VALUES (1,'REF_USER_PRIVILEGE_PK', 100); 
INSERT INTO t_id_generator(id, pk_name, pk_value) VALUES (2,'REF_USER_ROLE_PK', 100); 
INSERT INTO t_id_generator(id, pk_name, pk_value) VALUES (3,'USER_PK', 10000); 
INSERT INTO t_id_generator(id, pk_name, pk_value) VALUES (4,'REF_ROLE_PRIVILEGE_PK', 100); 
INSERT INTO t_id_generator(id, pk_name, pk_value) VALUES (5,'ROLE_PK', 100); 
INSERT INTO t_id_generator(id, pk_name, pk_value) VALUES (6,'COMPANY_PK', 1000); 
INSERT INTO t_id_generator(id, pk_name, pk_value) VALUES (7,'DEPARTMENT_PK', 1000); 
INSERT INTO t_id_generator(id, pk_name, pk_value) VALUES (8,'EMPLOYEE_PK', 1000); 
INSERT INTO t_id_generator(id, pk_name, pk_value) VALUES (9,'EMPLOYEESNAPSHOT_PK', 1000); 
INSERT INTO t_id_generator(id, pk_name, pk_value) VALUES (10,'REF_EMPLOYEE_DEPARTMENT_PK', 1); 
INSERT INTO t_id_generator(id, pk_name, pk_value) VALUES (11,'EMPLOYEE_SALARY_PK', 1); 


--user role
insert into t_role(id, name, description ) values(1,'ADMIN','Administrator');
insert into t_role(id, name, description ) values(2,'SUPER_ADMIN','System Administrator');

--role reference privilege
insert into t_role_ref_privilege(id, roleid, fid ) values(1,1,11);
insert into t_role_ref_privilege(id, roleid, fid ) values(2,1,12);
insert into t_role_ref_privilege(id, roleid, fid ) values(3,1,121);
insert into t_role_ref_privilege(id, roleid, fid ) values(4,1,15);
insert into t_role_ref_privilege(id, roleid, fid ) values(5,1,151);
insert into t_role_ref_privilege(id, roleid, fid ) values(6,1,1511);

--role reference user
insert into t_role_ref_user(id, roleid, userid ) values(1,1,3);

