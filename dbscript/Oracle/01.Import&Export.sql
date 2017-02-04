导入
sqlplus sys/iunet@iunet as sysdba;

set define OFF;
spool C:\Oracledb\iunet\log\db_tbs_user.log;
set echo on;
set serveroutput on;
--表空间地址
create tablespace TBS_IUNET datafile 'C:\Oracledb\iunet\TBS_IUNET.dbf' size 200m reuse
	AUTOEXTEND ON NEXT  50M MAXSIZE UNLIMITED EXTENT MANAGEMENT LOCAL 
	SEGMENT SPACE MANAGEMENT AUTO;
create tablespace IDX_IUNET datafile 'C:\Oracledb\iunet\IDX_IUNET.dbf' size 50m reuse
	AUTOEXTEND ON NEXT  20M MAXSIZE UNLIMITED EXTENT MANAGEMENT LOCAL 
	SEGMENT SPACE MANAGEMENT AUTO;
====================================
	删除用户重新导入
====================================
--sqlplus sys/123456@orcl as sysdba;
--drop user iunet cascade;
====================================

create user iunet identified by 123456 default tablespace TBS_IUNET;
grant all privilege to iunet;
GRANT "DBA" TO iunet;
ALTER USER iunet DEFAULT ROLE  ALL;
conn iunet/123456@orcl;
spool C:\Oracledb\iunet\log\tablesetup.log;
set echo on;
commit;
exit;

imp iunet/iunet@iunet file=C:\DMP\iunet.dmp full=y;

导出

exp iunet/iunet@iunet file=C:\iunet.dmp full=y;