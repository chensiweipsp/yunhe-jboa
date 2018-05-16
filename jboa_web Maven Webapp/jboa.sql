prompt PL/SQL Developer import file
prompt Created on 2018年5月17日 by Administrator
set feedback off
set define off
prompt Creating BIZ_CHECK_RESULT...
create table BIZ_CHECK_RESULT
(
  id         NUMBER(10) not null,
  claim_id   NUMBER(10) not null,
  check_time TIMESTAMP(6) not null,
  checker_sn VARCHAR2(50) not null,
  result     VARCHAR2(50) not null,
  comm       VARCHAR2(500) not null,
  checker_cn VARCHAR2(50) not null
)
;

prompt Creating BIZ_CLAIM_VOUCHER...
create table BIZ_CLAIM_VOUCHER
(
  id            NUMBER(10) not null,
  next_deal_sn  VARCHAR2(50),
  create_sn     VARCHAR2(50) not null,
  create_time   TIMESTAMP(6) not null,
  event         VARCHAR2(255) not null,
  total_account NUMBER(20,2) not null,
  status        VARCHAR2(20) not null,
  modify_time   TIMESTAMP(6),
  schedule      NUMBER(6),
  taskid        VARCHAR2(20)
)
;
alter table BIZ_CLAIM_VOUCHER
  add constraint PK_BCV primary key (ID);

prompt Creating BIZ_CLAIM_VOUCHER_DETAIL...
create table BIZ_CLAIM_VOUCHER_DETAIL
(
  id    NUMBER(6) not null,
  state NUMBER(6) not null
)
;

prompt Creating BIZ_CLAIM_VOUCHER_STATISTICS...
create table BIZ_CLAIM_VOUCHER_STATISTICS
(
  id            NUMBER(10) not null,
  total_count   NUMBER(20,2) not null,
  year          NUMBER(4) not null,
  month         NUMBER(2) not null,
  department_id NUMBER(10) not null,
  modify_time   TIMESTAMP(6) not null
)
;

prompt Creating BIZ_CLAIM_VOUYEAR__STATISTICS...
create table BIZ_CLAIM_VOUYEAR__STATISTICS
(
  id          NUMBER(10) not null,
  total_count NUMBER(30,2) not null,
  year        NUMBER(4) not null,
  modify_time TIMESTAMP(6) not null,
  empid       VARCHAR2(50)
)
;

prompt Creating BIZ_LEAVE...
create table BIZ_LEAVE
(
  id              NUMBER(10) not null,
  employee_sn     VARCHAR2(50) not null,
  starttime       TIMESTAMP(6) not null,
  endtime         TIMESTAMP(6) not null,
  leaveday        NUMBER(5,1) not null,
  reason          VARCHAR2(500) not null,
  status          VARCHAR2(20),
  leavetype       VARCHAR2(50),
  next_deal_sn    VARCHAR2(50),
  approve_opinion VARCHAR2(100),
  createtime      TIMESTAMP(6),
  modifytime      TIMESTAMP(6)
)
;

prompt Creating SYS_DEPARTMENT...
create table SYS_DEPARTMENT
(
  id   NUMBER(10) not null,
  name VARCHAR2(50) not null
)
;
alter table SYS_DEPARTMENT
  add constraint PF_DEPTNO primary key (ID);

prompt Creating SYS_EMPLOYEE...
create table SYS_EMPLOYEE
(
  sn            NUMBER(6) not null,
  department_id NUMBER(10) not null,
  name          VARCHAR2(50) not null,
  password      VARCHAR2(50) not null,
  salt          VARCHAR2(50),
  email         VARCHAR2(50)
)
;
alter table SYS_EMPLOYEE
  add constraint PK_SYSEMP primary key (SN);
alter table SYS_EMPLOYEE
  add constraint FK_SYSDEPT foreign key (DEPARTMENT_ID)
  references SYS_DEPARTMENT (ID) on delete cascade;

prompt Creating SYS_ROLE...
create table SYS_ROLE
(
  id       NUMBER(6) not null,
  rolename VARCHAR2(50),
  describe VARCHAR2(50)
)
;
alter table SYS_ROLE
  add constraint ROLE_PK primary key (ID);

prompt Creating SYS_EMP_ROLE...
create table SYS_EMP_ROLE
(
  emp_id  NUMBER(6) not null,
  role_id NUMBER(6) not null
)
;
alter table SYS_EMP_ROLE
  add constraint R_E_PK_UNOIN primary key (EMP_ID, ROLE_ID);
alter table SYS_EMP_ROLE
  add constraint R_E_EMPID foreign key (EMP_ID)
  references SYS_EMPLOYEE (SN) on delete cascade;
alter table SYS_EMP_ROLE
  add constraint R_E_ROLID foreign key (ROLE_ID)
  references SYS_ROLE (ID) on delete cascade;

prompt Creating SYS_PERMISSION...
create table SYS_PERMISSION
(
  id             NUMBER(6) not null,
  permissionname VARCHAR2(50),
  describe       VARCHAR2(50)
)
;
alter table SYS_PERMISSION
  add constraint PMS_PK primary key (ID);

prompt Creating SYS_ROLE_PERMISSION...
create table SYS_ROLE_PERMISSION
(
  role_id       NUMBER(6) not null,
  permission_id NUMBER(6) not null
)
;
alter table SYS_ROLE_PERMISSION
  add constraint S_R_PK_UNOIN primary key (ROLE_ID, PERMISSION_ID);
alter table SYS_ROLE_PERMISSION
  add constraint S_R_PID foreign key (PERMISSION_ID)
  references SYS_PERMISSION (ID) on delete cascade;
alter table SYS_ROLE_PERMISSION
  add constraint S_R_ROLID foreign key (ROLE_ID)
  references SYS_ROLE (ID) on delete cascade;

prompt Disabling triggers for BIZ_CHECK_RESULT...
alter table BIZ_CHECK_RESULT disable all triggers;
prompt Disabling triggers for BIZ_CLAIM_VOUCHER...
alter table BIZ_CLAIM_VOUCHER disable all triggers;
prompt Disabling triggers for BIZ_CLAIM_VOUCHER_DETAIL...
alter table BIZ_CLAIM_VOUCHER_DETAIL disable all triggers;
prompt Disabling triggers for BIZ_CLAIM_VOUCHER_STATISTICS...
alter table BIZ_CLAIM_VOUCHER_STATISTICS disable all triggers;
prompt Disabling triggers for BIZ_CLAIM_VOUYEAR__STATISTICS...
alter table BIZ_CLAIM_VOUYEAR__STATISTICS disable all triggers;
prompt Disabling triggers for BIZ_LEAVE...
alter table BIZ_LEAVE disable all triggers;
prompt Disabling triggers for SYS_DEPARTMENT...
alter table SYS_DEPARTMENT disable all triggers;
prompt Disabling triggers for SYS_EMPLOYEE...
alter table SYS_EMPLOYEE disable all triggers;
prompt Disabling triggers for SYS_ROLE...
alter table SYS_ROLE disable all triggers;
prompt Disabling triggers for SYS_EMP_ROLE...
alter table SYS_EMP_ROLE disable all triggers;
prompt Disabling triggers for SYS_PERMISSION...
alter table SYS_PERMISSION disable all triggers;
prompt Disabling triggers for SYS_ROLE_PERMISSION...
alter table SYS_ROLE_PERMISSION disable all triggers;
prompt Deleting SYS_ROLE_PERMISSION...
delete from SYS_ROLE_PERMISSION;
commit;
prompt Deleting SYS_PERMISSION...
delete from SYS_PERMISSION;
commit;
prompt Deleting SYS_EMP_ROLE...
delete from SYS_EMP_ROLE;
commit;
prompt Deleting SYS_ROLE...
delete from SYS_ROLE;
commit;
prompt Deleting SYS_EMPLOYEE...
delete from SYS_EMPLOYEE;
commit;
prompt Deleting SYS_DEPARTMENT...
delete from SYS_DEPARTMENT;
commit;
prompt Deleting BIZ_LEAVE...
delete from BIZ_LEAVE;
commit;
prompt Deleting BIZ_CLAIM_VOUYEAR__STATISTICS...
delete from BIZ_CLAIM_VOUYEAR__STATISTICS;
commit;
prompt Deleting BIZ_CLAIM_VOUCHER_STATISTICS...
delete from BIZ_CLAIM_VOUCHER_STATISTICS;
commit;
prompt Deleting BIZ_CLAIM_VOUCHER_DETAIL...
delete from BIZ_CLAIM_VOUCHER_DETAIL;
commit;
prompt Deleting BIZ_CLAIM_VOUCHER...
delete from BIZ_CLAIM_VOUCHER;
commit;
prompt Deleting BIZ_CHECK_RESULT...
delete from BIZ_CHECK_RESULT;
commit;
prompt Loading BIZ_CHECK_RESULT...
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (22, 21, to_timestamp('21-04-2018 03:39:35.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死！', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (39, 37, to_timestamp('23-04-2018 04:27:01.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (40, 37, to_timestamp('23-04-2018 04:27:49.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (42, 37, to_timestamp('23-04-2018 04:33:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (43, 37, to_timestamp('23-04-2018 04:39:14.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (44, 37, to_timestamp('23-04-2018 04:41:45.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (45, 37, to_timestamp('23-04-2018 04:42:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (47, 46, to_timestamp('23-04-2018 04:44:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (48, 46, to_timestamp('23-04-2018 04:44:25.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (49, 46, to_timestamp('23-04-2018 04:45:20.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (60, 55, to_timestamp('23-04-2018 04:57:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (62, 55, to_timestamp('23-04-2018 05:01:10.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (63, 55, to_timestamp('23-04-2018 05:04:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (64, 55, to_timestamp('23-04-2018 05:04:51.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (67, 55, to_timestamp('23-04-2018 05:09:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (68, 55, to_timestamp('23-04-2018 05:09:33.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (69, 55, to_timestamp('23-04-2018 05:09:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (70, 55, to_timestamp('23-04-2018 05:10:51.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (169, 168, to_timestamp('29-04-2018 14:49:40.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (823, 285, to_timestamp('18-07-2017 17:38:35.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '6', '通过', '好吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (344, 322, to_timestamp('12-01-2017 14:19:40.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', '通过', '好的！！！', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (345, 284, to_timestamp('12-01-2017 14:19:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', '通过', '好的！！！', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (346, 128, to_timestamp('12-01-2017 14:19:48.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', '通过', '好的！！！', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (347, 126, to_timestamp('12-01-2017 14:52:33.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', '通过', 'OK', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (1, 1, to_timestamp('02-09-2013 09:02:02.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '2', '通过', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (2, 1, to_timestamp('02-09-2013 11:02:02.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '4', '通过', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (101, 4, to_timestamp('04-09-2013 17:12:27.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '4', '通过', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (142, 108, to_timestamp('18-09-2013 11:12:42.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '2', '通过', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (143, 7, to_timestamp('18-09-2013 11:13:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '2', '通过', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (144, 107, to_timestamp('18-09-2013 11:18:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '2', '通过', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (145, 3, to_timestamp('18-09-2013 11:18:54.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (146, 7, to_timestamp('18-09-2013 11:19:03.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '003', '通过', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (121, 100, to_timestamp('13-09-2013 15:29:06.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '002', '拒绝', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (100, 4, to_timestamp('04-09-2013 17:00:13.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '002', '通过', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (145, 3, to_timestamp('18-09-2013 11:18:54.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '003', '通过', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (146, 7, to_timestamp('18-09-2013 11:19:03.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '013', '通过', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (121, 100, to_timestamp('13-09-2013 15:29:06.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '002', '拒绝', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (100, 4, to_timestamp('04-09-2013 17:00:13.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '002', '通过', '同意', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (323, 322, to_timestamp('11-01-2017 10:56:57.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', '好啊', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (324, 173, to_timestamp('11-01-2017 10:58:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', '好吧', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (325, 124, to_timestamp('11-01-2017 10:59:14.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', '哈哈哈', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (336, 100, to_timestamp('12-01-2017 10:42:39.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '013', '通过', 'OKOK', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (338, 2, to_timestamp('12-01-2017 10:42:42.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '013', '通过', 'OKOK', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (340, 303, to_timestamp('12-01-2017 10:42:46.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '013', '通过', 'OKOK', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (342, 102, to_timestamp('12-01-2017 10:42:49.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '013', '通过', 'OKOK', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (348, 132, to_timestamp('13-01-2017 16:00:36.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', 'OKOK', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (349, 130, to_timestamp('13-01-2017 16:00:52.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', '好奥', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (350, 133, to_timestamp('13-01-2017 16:09:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '审核不通过', '不行', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (326, 130, to_timestamp('11-01-2017 11:13:45.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', '好吧', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (331, 285, to_timestamp('12-01-2017 10:39:03.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '010', '通过', '好的', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (332, 102, to_timestamp('12-01-2017 10:39:08.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '010', '通过', '好的', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (333, 303, to_timestamp('12-01-2017 10:39:11.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '010', '通过', '好的', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (334, 100, to_timestamp('12-01-2017 10:39:14.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '010', '通过', '好的', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (335, 2, to_timestamp('12-01-2017 10:39:17.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '010', '通过', '好的', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (541, 124, to_timestamp('20-04-2017 11:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', 'asdasdasdasdasd', '010');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (542, 126, to_timestamp('20-04-2017 11:12:20.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', 'asdasdasdasdasd', '010');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (543, 128, to_timestamp('20-04-2017 11:12:34.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', 'asdasdasddasd', '012');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (545, 102, to_timestamp('20-04-2017 11:15:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '013', '通过', '你老婆', '011');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (546, 102, to_timestamp('20-04-2017 11:17:13.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '013', '通过', '好的', '010');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (548, 302, to_timestamp('20-04-2017 11:33:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '012', '通过', '好的', '012');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (550, 198, to_timestamp('20-04-2017 11:34:19.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', '好的', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (551, 288, to_timestamp('20-04-2017 11:34:49.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', '好的', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (552, 526, to_timestamp('20-04-2017 11:37:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '010', '通过', '好的', '010');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (554, 526, to_timestamp('20-04-2017 11:41:13.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '010', '通过', '撒大声地', '010');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (502, 173, to_timestamp('12-04-2017 14:47:26.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', 'fuckof', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (509, 124, to_timestamp('12-04-2017 15:22:36.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '审核不通过', 'aaaaaaaaa', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (555, 285, to_timestamp('20-04-2017 11:42:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '006', '通过', 'fasdfasdfasdf', '006');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (556, 285, to_timestamp('20-04-2017 11:43:39.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '006', '通过', 'asdfsadfasdf', '006');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (557, 302, to_timestamp('20-04-2017 11:46:15.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '012', '通过', 'fsdasdf', '012');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (558, 6, to_timestamp('20-04-2017 11:46:47.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '012', '通过', '好吧沃日', '012');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (516, 180, to_timestamp('13-04-2017 10:44:46.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '003', '审核不通过', 'dsfasdfasdf', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (517, 303, to_timestamp('13-04-2017 10:45:08.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '002', '审核不通过', 'asdfasdfsdaf', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (518, 171, to_timestamp('13-04-2017 10:45:43.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '003', '审核不通过', 'asdfasdf', '001');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (519, 192, to_timestamp('13-04-2017 15:29:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', 'asdfasdfasdfasdfafasd', '010');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (520, 288, to_timestamp('13-04-2017 15:29:40.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', 'h好吧沃日', '010');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (822, 171, to_timestamp('18-07-2017 17:30:24.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '好的', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (521, 284, to_timestamp('14-04-2017 09:44:25.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', '好的', '010');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (522, 303, to_timestamp('14-04-2017 09:45:22.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '002', '通过', '好吧', '012');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (523, 173, to_timestamp('14-04-2017 09:48:51.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', 'gg', '010');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (524, 171, to_timestamp('14-04-2017 09:50:32.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '003', '通过', 'OKOKOKO', '006');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (525, 288, to_timestamp('14-04-2017 10:43:10.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001', '通过', 'dasdasdasd', '013');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (38, 37, to_timestamp('23-04-2018 04:22:19.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (41, 37, to_timestamp('23-04-2018 04:30:43.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (82, 81, to_timestamp('24-04-2018 02:12:02.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (84, 83, to_timestamp('24-04-2018 02:19:55.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (86, 85, to_timestamp('24-04-2018 02:27:45.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (94, 93, to_timestamp('24-04-2018 02:55:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (95, 93, to_timestamp('24-04-2018 02:57:32.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (97, 96, to_timestamp('24-04-2018 02:59:48.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (100, 96, to_timestamp('24-04-2018 03:01:54.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (101, 99, to_timestamp('24-04-2018 03:03:02.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (102, 96, to_timestamp('24-04-2018 03:03:26.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (103, 99, to_timestamp('24-04-2018 03:04:55.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '啊啊', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (104, 99, to_timestamp('24-04-2018 03:05:20.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '啊啊', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (122, 121, to_timestamp('29-04-2018 02:23:26.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '审核不通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (145, 144, to_timestamp('29-04-2018 03:38:49.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (151, 150, to_timestamp('29-04-2018 13:58:33.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '审核不通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (153, 152, to_timestamp('29-04-2018 14:01:59.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (155, 154, to_timestamp('29-04-2018 14:03:32.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (157, 156, to_timestamp('29-04-2018 14:05:01.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
commit;
prompt 100 records committed...
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (159, 158, to_timestamp('29-04-2018 14:24:50.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (163, 162, to_timestamp('29-04-2018 14:28:25.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (165, 164, to_timestamp('29-04-2018 14:31:25.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (166, 164, to_timestamp('29-04-2018 14:32:14.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (167, 164, to_timestamp('29-04-2018 14:34:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (80, 79, to_timestamp('24-04-2018 02:08:38.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (124, 123, to_timestamp('29-04-2018 03:00:56.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '审核不通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (126, 125, to_timestamp('29-04-2018 03:09:03.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '审核不通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (128, 127, to_timestamp('29-04-2018 03:14:57.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '审核不通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (130, 129, to_timestamp('29-04-2018 03:16:34.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (132, 131, to_timestamp('29-04-2018 03:18:36.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (134, 133, to_timestamp('29-04-2018 03:19:49.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (136, 135, to_timestamp('29-04-2018 03:23:04.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '审核不通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (138, 137, to_timestamp('29-04-2018 03:24:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (143, 142, to_timestamp('29-04-2018 03:36:11.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (147, 146, to_timestamp('29-04-2018 03:55:48.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (161, 160, to_timestamp('29-04-2018 14:27:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (51, 50, to_timestamp('23-04-2018 04:51:03.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (52, 50, to_timestamp('23-04-2018 04:51:27.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (53, 50, to_timestamp('23-04-2018 04:51:42.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (58, 55, to_timestamp('23-04-2018 04:52:37.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (59, 55, to_timestamp('23-04-2018 04:52:52.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (61, 55, to_timestamp('23-04-2018 04:59:37.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (65, 55, to_timestamp('23-04-2018 05:06:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (66, 55, to_timestamp('23-04-2018 05:07:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (141, 140, to_timestamp('29-04-2018 03:32:24.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (172, 171, to_timestamp('29-04-2018 14:54:57.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (174, 173, to_timestamp('29-04-2018 14:58:52.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (175, 173, to_timestamp('29-04-2018 14:59:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (177, 176, to_timestamp('29-04-2018 15:02:17.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '5', '审核不通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (179, 178, to_timestamp('29-04-2018 15:06:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (181, 180, to_timestamp('29-04-2018 15:07:18.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '审核不通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (182, 178, to_timestamp('29-04-2018 15:07:43.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (183, 178, to_timestamp('29-04-2018 15:08:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (185, 184, to_timestamp('29-04-2018 15:10:24.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (222, 221, to_timestamp('03-05-2018 20:18:26.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (223, 221, to_timestamp('03-05-2018 20:19:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (224, 221, to_timestamp('03-05-2018 20:19:48.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '审核不通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (226, 225, to_timestamp('03-05-2018 20:25:46.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (238, 237, to_timestamp('04-05-2018 03:33:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '审核不通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (240, 239, to_timestamp('04-05-2018 03:33:52.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (241, 239, to_timestamp('04-05-2018 03:36:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '通过', '去死吧', '12');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (242, 232, to_timestamp('04-05-2018 03:36:15.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '审核不通过', '去死吧', '12');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (244, 243, to_timestamp('04-05-2018 03:41:46.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (24, 23, to_timestamp('21-04-2018 04:07:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (73, 72, to_timestamp('24-04-2018 01:55:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (75, 74, to_timestamp('24-04-2018 01:57:56.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (77, 76, to_timestamp('24-04-2018 02:01:21.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (88, 87, to_timestamp('24-04-2018 02:39:15.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (90, 89, to_timestamp('24-04-2018 02:40:25.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (92, 91, to_timestamp('24-04-2018 02:43:14.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (110, 108, to_timestamp('27-04-2018 00:43:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (112, 106, to_timestamp('27-04-2018 00:44:24.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧！', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (113, 106, to_timestamp('27-04-2018 00:44:52.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '！！！！', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (114, 108, to_timestamp('27-04-2018 00:45:50.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '通过', '去死吧', '12');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (203, 202, to_timestamp('03-05-2018 03:33:14.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (204, 202, to_timestamp('03-05-2018 03:33:34.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (205, 202, to_timestamp('03-05-2018 03:33:49.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '3');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (207, 206, to_timestamp('03-05-2018 03:34:31.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (209, 208, to_timestamp('03-05-2018 03:35:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', '审核不通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (211, 210, to_timestamp('03-05-2018 03:43:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '5', '审核不通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (212, 210, to_timestamp('03-05-2018 03:43:31.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '5', '审核不通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (213, 208, to_timestamp('03-05-2018 03:44:21.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (215, 210, to_timestamp('03-05-2018 03:45:10.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '5', '通过', '去死吧', '5');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (228, 227, to_timestamp('03-05-2018 20:28:46.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '审核不通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (230, 229, to_timestamp('03-05-2018 20:29:38.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (231, 229, to_timestamp('03-05-2018 20:30:43.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '通过', '去死吧', '12');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (234, 233, to_timestamp('03-05-2018 20:45:40.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '通过', '去死吧', '4');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (235, 232, to_timestamp('03-05-2018 20:46:25.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12', '通过', '去死吧', '1');
insert into BIZ_CHECK_RESULT (id, claim_id, check_time, checker_sn, result, comm, checker_cn)
values (236, 233, to_timestamp('03-05-2018 20:46:31.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3', '审核不通过', '去死吧', '1');
commit;
prompt 170 records loaded
prompt Loading BIZ_CLAIM_VOUCHER...
insert into BIZ_CLAIM_VOUCHER (id, next_deal_sn, create_sn, create_time, event, total_account, status, modify_time, schedule, taskid)
values (262, '12', '3', to_timestamp('17-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '打架了', 800, '新创建', null, 1, '62521');
insert into BIZ_CLAIM_VOUCHER (id, next_deal_sn, create_sn, create_time, event, total_account, status, modify_time, schedule, taskid)
values (261, '12', '3', to_timestamp('17-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '打架了', 800, '新创建', null, 1, '62518');
insert into BIZ_CLAIM_VOUCHER (id, next_deal_sn, create_sn, create_time, event, total_account, status, modify_time, schedule, taskid)
values (239, '12', '12', to_timestamp('04-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '玩八神', 800, '已通过审核', null, 4, '60029');
insert into BIZ_CLAIM_VOUCHER (id, next_deal_sn, create_sn, create_time, event, total_account, status, modify_time, schedule, taskid)
values (237, '3', '3', to_timestamp('04-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '玩八神', 800, '部门经理回拒', null, 5, '60010');
insert into BIZ_CLAIM_VOUCHER (id, next_deal_sn, create_sn, create_time, event, total_account, status, modify_time, schedule, taskid)
values (243, '1', '1', to_timestamp('18-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '玩八神', 800, '财务回拒', null, 5, '60044');
insert into BIZ_CLAIM_VOUCHER (id, next_deal_sn, create_sn, create_time, event, total_account, status, modify_time, schedule, taskid)
values (227, '3', '3', to_timestamp('03-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '打架了', 800, '部门经理回拒', null, 5, '57510');
insert into BIZ_CLAIM_VOUCHER (id, next_deal_sn, create_sn, create_time, event, total_account, status, modify_time, schedule, taskid)
values (233, '3', '3', to_timestamp('03-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '玩八神', 800, '财务回拒', null, 5, '57560');
insert into BIZ_CLAIM_VOUCHER (id, next_deal_sn, create_sn, create_time, event, total_account, status, modify_time, schedule, taskid)
values (232, '12', '12', to_timestamp('03-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '玩八神', 800, '总经理回拒', null, 5, '57566');
commit;
prompt 8 records loaded
prompt Loading BIZ_CLAIM_VOUCHER_DETAIL...
prompt Table is empty
prompt Loading BIZ_CLAIM_VOUCHER_STATISTICS...
prompt Table is empty
prompt Loading BIZ_CLAIM_VOUYEAR__STATISTICS...
insert into BIZ_CLAIM_VOUYEAR__STATISTICS (id, total_count, year, modify_time, empid)
values (5, 1225771.56, 2013, to_timestamp('15-02-2014 16:40:36.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '3');
insert into BIZ_CLAIM_VOUYEAR__STATISTICS (id, total_count, year, modify_time, empid)
values (6, 2000.02, 2013, to_timestamp('15-02-2014 16:40:36.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '002');
insert into BIZ_CLAIM_VOUYEAR__STATISTICS (id, total_count, year, modify_time, empid)
values (327, 104, 117, to_timestamp('11-01-2017 15:52:21.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '004');
insert into BIZ_CLAIM_VOUYEAR__STATISTICS (id, total_count, year, modify_time, empid)
values (328, 112799, 117, to_timestamp('11-01-2017 15:56:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '5');
insert into BIZ_CLAIM_VOUYEAR__STATISTICS (id, total_count, year, modify_time, empid)
values (329, 11111, 117, to_timestamp('11-01-2017 16:21:01.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001');
insert into BIZ_CLAIM_VOUYEAR__STATISTICS (id, total_count, year, modify_time, empid)
values (330, 700, 117, to_timestamp('11-01-2017 16:43:42.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '001');
insert into BIZ_CLAIM_VOUYEAR__STATISTICS (id, total_count, year, modify_time, empid)
values (337, 600, 117, to_timestamp('12-01-2017 10:42:39.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '004');
insert into BIZ_CLAIM_VOUYEAR__STATISTICS (id, total_count, year, modify_time, empid)
values (339, 1200, 117, to_timestamp('12-01-2017 10:42:42.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '008');
insert into BIZ_CLAIM_VOUYEAR__STATISTICS (id, total_count, year, modify_time, empid)
values (341, 50000, 117, to_timestamp('12-01-2017 10:42:46.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '002');
insert into BIZ_CLAIM_VOUYEAR__STATISTICS (id, total_count, year, modify_time, empid)
values (343, 8889, 117, to_timestamp('12-01-2017 10:42:49.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '013');
insert into BIZ_CLAIM_VOUYEAR__STATISTICS (id, total_count, year, modify_time, empid)
values (547, 8889, 117, to_timestamp('20-04-2017 11:17:13.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '013');
insert into BIZ_CLAIM_VOUYEAR__STATISTICS (id, total_count, year, modify_time, empid)
values (549, 1218555, 117, to_timestamp('20-04-2017 11:33:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '12');
insert into BIZ_CLAIM_VOUYEAR__STATISTICS (id, total_count, year, modify_time, empid)
values (553, 105400, 117, to_timestamp('20-04-2017 11:37:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '010');
commit;
prompt 13 records loaded
prompt Loading BIZ_LEAVE...
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (105, '001', to_timestamp('09-09-2013 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('10-09-2013 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '请假', '待审批', '年假', '002', null, to_timestamp('05-09-2013 16:49:56.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (121, '001', to_timestamp('06-09-2013 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('07-09-2013 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '请假', '待审批', '事假', '002', null, to_timestamp('06-09-2013 09:02:40.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (136, '001', to_timestamp('10-09-2013 10:54:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('11-09-2013 10:54:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '2', '待审批', '年假', '002', null, to_timestamp('09-09-2013 10:54:20.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (139, '001', to_timestamp('23-09-2013 11:15:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('24-09-2013 11:15:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '1', '待审批', '年假', '002', null, to_timestamp('09-09-2013 11:15:11.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (143, '001', to_timestamp('30-09-2013 15:09:50.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('01-10-2013 15:09:50.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 'ttt', '待审批', '年假', '002', null, to_timestamp('09-09-2013 15:10:08.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (1, '001', to_timestamp('02-08-2013 08:30:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('02-08-2013 00:30:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), .5, '家里有事', '已审批', '事假', '002', '同意', to_timestamp('01-08-2013 10:02:58.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('02-08-2013 22:58:02.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (2, '001', to_timestamp('06-08-2013 08:30:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('07-08-2013 17:30:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '感冒', '已打回', '病假', null, '同意', to_timestamp('01-08-2013 10:02:58.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('02-08-2013 22:58:02.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (3, '001', to_timestamp('09-08-2013 08:30:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('09-08-2013 17:30:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '感冒', '已审批 ', '病假', '002', '同意', to_timestamp('01-08-2013 10:02:58.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('05-09-2013 15:49:04.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (4, '004', to_timestamp('13-08-2013 08:30:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('14-08-2013 17:30:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '事假', '已审批', '事假', null, null, null, null);
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (101, '001', to_timestamp('13-08-2013 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('13-08-2013 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '请假', '已审批 ', '年假', '002', '通过', to_timestamp('05-09-2013 14:31:19.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('05-09-2013 15:21:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (127, '001', to_timestamp('18-02-2014 09:55:21.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('20-02-2014 09:55:23.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '44', '待审批', '年假', '002', null, to_timestamp('18-02-2014 09:55:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (102, '001', to_timestamp('13-08-2013 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('13-08-2013 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '请假大', '待审批', '事假', '002', null, to_timestamp('05-09-2013 14:38:50.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (104, '001', to_timestamp('05-09-2013 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('06-09-2013 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '结婚', '已打回 ', '婚假', '002', '回吧', to_timestamp('05-09-2013 15:26:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('05-09-2013 15:42:31.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (122, '001', to_timestamp('06-09-2013 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('08-09-2013 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '田', '待审批', '年假', '002', null, to_timestamp('06-09-2013 09:15:18.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (124, '001', to_timestamp('05-09-2013 09:20:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('07-09-2013 09:20:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '年假', '待审批', '年假', '002', null, to_timestamp('06-09-2013 09:20:25.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (125, '001', to_timestamp('06-09-2013 14:19:47.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('07-09-2013 14:19:50.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '婚假', '待审批', '婚假', '002', null, to_timestamp('06-09-2013 14:20:10.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
insert into BIZ_LEAVE (id, employee_sn, starttime, endtime, leaveday, reason, status, leavetype, next_deal_sn, approve_opinion, createtime, modifytime)
values (140, '001', to_timestamp('11-09-2013 14:51:35.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('12-09-2013 14:51:35.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 'tt', '待审批', '年假', '002', null, to_timestamp('09-09-2013 14:51:48.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null);
commit;
prompt 17 records loaded
prompt Loading SYS_DEPARTMENT...
insert into SYS_DEPARTMENT (id, name)
values (1, '人事部');
insert into SYS_DEPARTMENT (id, name)
values (2, '平台研发部');
insert into SYS_DEPARTMENT (id, name)
values (3, '产品设计部');
insert into SYS_DEPARTMENT (id, name)
values (4, '财务部');
insert into SYS_DEPARTMENT (id, name)
values (5, '总裁办公室');
commit;
prompt 5 records loaded
prompt Loading SYS_EMPLOYEE...
insert into SYS_EMPLOYEE (sn, department_id, name, password, salt, email)
values (2, 3, '曹操', 'e99eba70ddba6b3c719c1ce81b94e3d0', 'sw', '295255658@qq.com');
insert into SYS_EMPLOYEE (sn, department_id, name, password, salt, email)
values (3, 3, '狄仁杰', 'e99eba70ddba6b3c719c1ce81b94e3d0', 'sw', '295255658@qq.com');
insert into SYS_EMPLOYEE (sn, department_id, name, password, salt, email)
values (4, 4, '小乔', 'e99eba70ddba6b3c719c1ce81b94e3d0', 'sw', '295255658@qq.com');
insert into SYS_EMPLOYEE (sn, department_id, name, password, salt, email)
values (5, 4, '大乔', 'e99eba70ddba6b3c719c1ce81b94e3d0', 'sw', '295255658@qq.com');
insert into SYS_EMPLOYEE (sn, department_id, name, password, salt, email)
values (6, 1, '妲己', 'e99eba70ddba6b3c719c1ce81b94e3d0', 'sw', '295255658@qq.com');
insert into SYS_EMPLOYEE (sn, department_id, name, password, salt, email)
values (7, 1, '亚瑟', 'e99eba70ddba6b3c719c1ce81b94e3d0', 'sw', '295255658@qq.com');
insert into SYS_EMPLOYEE (sn, department_id, name, password, salt, email)
values (8, 1, '关羽', 'e99eba70ddba6b3c719c1ce81b94e3d0', 'sw', '295255658@qq.com');
insert into SYS_EMPLOYEE (sn, department_id, name, password, salt, email)
values (9, 1, '张飞', 'e99eba70ddba6b3c719c1ce81b94e3d0', 'sw', '295255658@qq.com');
insert into SYS_EMPLOYEE (sn, department_id, name, password, salt, email)
values (10, 3, '李白', 'e99eba70ddba6b3c719c1ce81b94e3d0', 'sw', '295255658@qq.com');
insert into SYS_EMPLOYEE (sn, department_id, name, password, salt, email)
values (11, 3, '不知火舞', 'e99eba70ddba6b3c719c1ce81b94e3d0', 'sw', '295255658@qq.com');
insert into SYS_EMPLOYEE (sn, department_id, name, password, salt, email)
values (12, 3, '韩信', 'e99eba70ddba6b3c719c1ce81b94e3d0', 'sw', '295255658@qq.com');
insert into SYS_EMPLOYEE (sn, department_id, name, password, salt, email)
values (13, 3, '后羿', 'e99eba70ddba6b3c719c1ce81b94e3d0', 'sw', '295255658@qq.com');
insert into SYS_EMPLOYEE (sn, department_id, name, password, salt, email)
values (1, 5, 'siwei', 'e99eba70ddba6b3c719c1ce81b94e3d0', 'sw', '295255658@qq.com');
commit;
prompt 13 records loaded
prompt Loading SYS_ROLE...
insert into SYS_ROLE (id, rolename, describe)
values (1, 'staff', '员工');
insert into SYS_ROLE (id, rolename, describe)
values (2, 'manager', '部门经理');
insert into SYS_ROLE (id, rolename, describe)
values (3, 'cashier', '财务');
insert into SYS_ROLE (id, rolename, describe)
values (4, 'generalmanager', '总经理');
commit;
prompt 4 records loaded
prompt Loading SYS_EMP_ROLE...
insert into SYS_EMP_ROLE (emp_id, role_id)
values (1, 4);
insert into SYS_EMP_ROLE (emp_id, role_id)
values (2, 4);
insert into SYS_EMP_ROLE (emp_id, role_id)
values (3, 1);
insert into SYS_EMP_ROLE (emp_id, role_id)
values (4, 3);
insert into SYS_EMP_ROLE (emp_id, role_id)
values (5, 3);
insert into SYS_EMP_ROLE (emp_id, role_id)
values (6, 2);
insert into SYS_EMP_ROLE (emp_id, role_id)
values (7, 2);
insert into SYS_EMP_ROLE (emp_id, role_id)
values (8, 1);
insert into SYS_EMP_ROLE (emp_id, role_id)
values (9, 2);
insert into SYS_EMP_ROLE (emp_id, role_id)
values (10, 1);
insert into SYS_EMP_ROLE (emp_id, role_id)
values (11, 1);
insert into SYS_EMP_ROLE (emp_id, role_id)
values (12, 2);
insert into SYS_EMP_ROLE (emp_id, role_id)
values (13, 1);
commit;
prompt 13 records loaded
prompt Loading SYS_PERMISSION...
insert into SYS_PERMISSION (id, permissionname, describe)
values (7, 'all', '查看所有报销单');
insert into SYS_PERMISSION (id, permissionname, describe)
values (8, 'self', '查看自己的报销单');
insert into SYS_PERMISSION (id, permissionname, describe)
values (9, 'selfdept', '查看自己部门的报销单');
insert into SYS_PERMISSION (id, permissionname, describe)
values (11, 'check', '审核报销单');
insert into SYS_PERMISSION (id, permissionname, describe)
values (6, 'search', '搜索');
insert into SYS_PERMISSION (id, permissionname, describe)
values (1, 'query', '查询');
insert into SYS_PERMISSION (id, permissionname, describe)
values (2, 'add', '添加');
insert into SYS_PERMISSION (id, permissionname, describe)
values (3, 'update', '修改');
insert into SYS_PERMISSION (id, permissionname, describe)
values (4, 'delete', '删除');
insert into SYS_PERMISSION (id, permissionname, describe)
values (5, 'exeport', '报表导出');
insert into SYS_PERMISSION (id, permissionname, describe)
values (10, 'statistics', '查看月度年度统计');
commit;
prompt 11 records loaded
prompt Loading SYS_ROLE_PERMISSION...
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (1, 1);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (1, 3);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (1, 4);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (1, 8);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (2, 1);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (2, 2);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (2, 3);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (2, 4);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (2, 5);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (2, 6);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (2, 9);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (2, 10);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (2, 11);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (3, 1);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (3, 2);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (3, 3);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (3, 4);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (3, 5);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (3, 7);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (3, 10);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (3, 11);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (4, 1);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (4, 2);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (4, 3);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (4, 4);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (4, 5);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (4, 6);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (4, 7);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (4, 10);
insert into SYS_ROLE_PERMISSION (role_id, permission_id)
values (4, 11);
commit;
prompt 30 records loaded
prompt Enabling triggers for BIZ_CHECK_RESULT...
alter table BIZ_CHECK_RESULT enable all triggers;
prompt Enabling triggers for BIZ_CLAIM_VOUCHER...
alter table BIZ_CLAIM_VOUCHER enable all triggers;
prompt Enabling triggers for BIZ_CLAIM_VOUCHER_DETAIL...
alter table BIZ_CLAIM_VOUCHER_DETAIL enable all triggers;
prompt Enabling triggers for BIZ_CLAIM_VOUCHER_STATISTICS...
alter table BIZ_CLAIM_VOUCHER_STATISTICS enable all triggers;
prompt Enabling triggers for BIZ_CLAIM_VOUYEAR__STATISTICS...
alter table BIZ_CLAIM_VOUYEAR__STATISTICS enable all triggers;
prompt Enabling triggers for BIZ_LEAVE...
alter table BIZ_LEAVE enable all triggers;
prompt Enabling triggers for SYS_DEPARTMENT...
alter table SYS_DEPARTMENT enable all triggers;
prompt Enabling triggers for SYS_EMPLOYEE...
alter table SYS_EMPLOYEE enable all triggers;
prompt Enabling triggers for SYS_ROLE...
alter table SYS_ROLE enable all triggers;
prompt Enabling triggers for SYS_EMP_ROLE...
alter table SYS_EMP_ROLE enable all triggers;
prompt Enabling triggers for SYS_PERMISSION...
alter table SYS_PERMISSION enable all triggers;
prompt Enabling triggers for SYS_ROLE_PERMISSION...
alter table SYS_ROLE_PERMISSION enable all triggers;
set feedback on
set define on
prompt Done.
