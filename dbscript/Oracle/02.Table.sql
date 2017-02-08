--==============================================================
-- NET_SEQUENCE 序列表
--==============================================================
CREATE TABLE NET_SEQUENCE
(
	TABLE_NAME         Varchar2(50)	NOT NULL,
	SERIAL_NUMBER      INTEGER		NOT NULL,
	CONSTRAINT P_SEQUENCE PRIMARY KEY (TABLE_NAME)
	USING INDEX TABLESPACE IDX_IUNET
) TABLESPACE TBS_IUNET;

--==============================================================
-- UM_USER 用户表
--==============================================================
CREATE TABLE UM_USER
(
	ID                 INTEGER			NOT NULL,
	LOGIN_NAME         NVarchar2(50)	NOT NULL,
	USER_NAME          NVarchar2(50),
	USER_CODE          NVarchar2(50),
	PASSWORD           NVarchar2(50),  
	DESCRIPTION        NVarchar2(200),
	STATE              INTEGER			NOT NULL,
	CREATE_TIME        TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
	UPDATE_TIME        TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
	USER_CARD          NVarchar2(50),	
	USER_TYPE          INTEGER,
	EMAIL			   VARCHAR(100),
	ACTIVATION_CODE    VARCHAR(50),
	SEX				   INTEGER,
	ADDRESS            NVarchar2(200),
	BIRTHDAY           TIMESTAMP,
    DUTY_TITLE         VARCHAR(50),
	OFFICE_PHONE       Varchar2(50),
	HOME_PHONE         Varchar2(50),
	MOBILE_PHONE       Varchar2(50),
	PERSON_ID          INTEGER,  
	RIGHT_LEVEL		   INTEGER,
	HOME_PATH          Varchar2(300),
    PHOTO_PATH		   Varchar2(300),
    PATH_SIZE          INTEGER DEFAULT 0,
	QUOTA_SIZE         INTEGER DEFAULT 0,  
	QUOTA_USED         INTEGER DEFAULT 0,
	APP_PARAM          Varchar2(1000),
	LOGIN_TIME         Varchar2(1000),
	FIELD_1            Varchar2(300),
	FIELD_2            Varchar2(200),
	FIELD_3            Varchar2(100),
	FIELD_4            Varchar2(100),
	FIELD_5            Varchar2(100),
	CONSTRAINT PK_UM_USER_ID PRIMARY KEY (ID)
	USING INDEX TABLESPACE IDX_IUNET
) TABLESPACE TBS_IUNET;
--==============================================================
-- INDEX: INDEX_LOGINNAME 登录名索引
--==============================================================
CREATE UNIQUE INDEX INDEX_LOGINNAME ON UM_USER (
   LOGIN_NAME	ASC
) TABLESPACE IDX_IUNET;

--==============================================================
-- UM_PERSON 人员表
--==============================================================
CREATE TABLE UM_PERSON
(
   ID                 INTEGER		NOT NULL,   
   NAME				  NVarchar2(50)	NOT NULL,  
   SEX                INTEGER,
   DUTY_TITLE         NVarchar2(50),
   ADDRESS            NVarchar2(300),
   EMAIL              Varchar2(50),
   OFFICE_PHONE       Varchar2(20),
   HOME_PHONE         Varchar2(20),
   MOBILE_PHONE       Varchar2(20), 
   JOIN_DATE          TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
   CREATE_TIME        TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,   
   BIRTHDAY           TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,   
   DESCRIPTION        NVarchar2(500),
   PHOTO_PATH		  Varchar2(300),
   RIGHT_LEVEL		  INTEGER,
   USER_CARD          Varchar2(50),
   USER_CODE    	  Varchar2(50),
   STATE              INTEGER	NOT NULL,
   FIELD_1            Varchar2(200),
   FIELD_2            Varchar2(200),
   CONSTRAINT PK_UM_PERSON_ID PRIMARY KEY (ID)
   USING INDEX TABLESPACE IDX_IUNET
) TABLESPACE TBS_IUNET;

--==============================================================
-- UM_ROLE 角色表
--==============================================================
CREATE TABLE UM_ROLE
(
   ID                 INTEGER		NOT NULL,
   NAME               NVarchar2(50)	NOT NULL,
   CREATE_TIME        TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
   DESCRIPTION        NVarchar2(200),
   TYPE               INTEGER	DEFAULT 0   NOT NULL,
   CONSTRAINT P_UM_ROLE PRIMARY KEY (ID)
   USING INDEX TABLESPACE IDX_IUNET
) TABLESPACE TBS_IUNET;
--==============================================================
-- INDEX: INDEX_ROLE 角色索引
--==============================================================
CREATE UNIQUE INDEX INDEX_UM_ROLE ON UM_ROLE (
   NAME	ASC
) TABLESPACE IDX_IUNET;

--==============================================================
-- UM_USERGROUP 用户组表
--==============================================================
CREATE TABLE UM_USERGROUP
(
   ID                 INTEGER		NOT NULL,
   PARENT_ID          INTEGER		NOT NULL,   
   NAME               NVarchar2(50)	NOT NULL,
   TYPE               INTEGER		NOT NULL,
   GROUP_CODE		  Varchar2(50),
   LAYER              Varchar2(30),
   DESCRIPTION        NVarchar2(200),  
   FOLDER_PATH        Varchar2(300),
   QUOTA_SIZE         INTEGER DEFAULT 0,
   QUOTA_USED         INTEGER DEFAULT 0,
   APP_PARAM          Varchar2(1000),
   FIELD_1            Varchar2(100),
   FIELD_2            Varchar2(100),
   CONSTRAINT PK_DEPARTMENT_ID PRIMARY KEY (ID)
   USING INDEX TABLESPACE IDX_IUNET
) TABLESPACE TBS_IUNET;

--==============================================================
-- UM_USERTOGROUP 用户&用户组关联表
--==============================================================
CREATE TABLE UM_USERTOGROUP
(
   USER_ID            INTEGER	NOT NULL,
   GROUP_ID           INTEGER	NOT NULL,
   USAGE_TYPE         INTEGER,
   CONSTRAINT P_UM_USERTOGROUP PRIMARY KEY (USER_ID, GROUP_ID)
   USING INDEX TABLESPACE IDX_IUNET
) TABLESPACE TBS_IUNET;

--==============================================================
-- INDEX_USERTOGROUP 用户&用户组关联 索引
--==============================================================
--CREATE UNIQUE INDEX INDEX_USERTOGROUP ON UM_USERTOGROUP (
--	USER_ID  	ASC,
--	GROUP_ID  	ASC,
--	USAGE_TYPE 	ASC
--) TABLESPACE IDX_IUNET;


--==============================================================
-- UM_USERTOROLE 用户&角色关联表
--==============================================================
CREATE TABLE UM_USERTOROLE
(
   ROLE_ID            INTEGER                NOT NULL,
   USER_ID            INTEGER                NOT NULL,
   CONSTRAINT P_UM_USERTOROLE PRIMARY KEY (ROLE_ID, USER_ID)
   USING INDEX TABLESPACE IDX_IUNET
) TABLESPACE TBS_IUNET;


--==============================================================
-- UM_PERSONTOGROUP 人员&用户组关联表
--==============================================================
CREATE TABLE UM_PERSONTOGROUP
(
   PERSON_ID          INTEGER                NOT NULL,
   GROUP_ID           INTEGER                NOT NULL,  
   CONSTRAINT P_UM_PTOGROUP PRIMARY KEY (PERSON_ID, GROUP_ID)
   USING INDEX TABLESPACE IDX_IUNET
) TABLESPACE TBS_IUNET;

--==============================================================
-- UM_FUNCTION 用户权限 表
--==============================================================
CREATE TABLE UM_FUNCTION
(
   ID                 INTEGER                NOT NULL,
   PARENT_ID          INTEGER                NOT NULL,
   NAME               NVarchar2(50)          NOT NULL,
   PARAM              Varchar2(200),
   ICON				  Varchar(100),
   STATE              INTEGER                NOT NULL,
   DESCRIPTION        NVarchar2(200),
   TYPE               INTEGER                NOT NULL,
   VIEW_POS	          INTEGER,
   CONSTRAINT P_UM_FUNCTION PRIMARY KEY (ID)  
   USING INDEX TABLESPACE IDX_IUNET
) TABLESPACE TBS_IUNET;

--==============================================================
-- UM_ROLEAUTHORITY 授权表
--==============================================================
CREATE TABLE UM_ROLEAUTHORITY
(
   ROLE_ID            INTEGER                NOT NULL,
   FUNCTION_ID        INTEGER                NOT NULL,
   CONSTRAINT P_UM_ROLEAUTH PRIMARY KEY (ROLE_ID, FUNCTION_ID)
   USING INDEX TABLESPACE IDX_IUNET
) TABLESPACE TBS_IUNET;

--==============================================================
-- UM_LOGINLOG
--==============================================================
CREATE TABLE UM_LOGINLOG
(
	ID			Varchar2(50)  NOT NULL,
	USER_ID		INTEGER,
	LOGIN_NAME	NVarchar2(50),
	LOGIN_TIME  TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
	LOGOUT_TIME	TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,    
	DEVICE_NAME	NVarchar2(50),
	HOST_NAME	NVarchar2(50),
	IP_ADDRESS	Varchar2(30),
	STATE		INTEGER,
	CONSTRAINT PK_UM_LOGINLOG_ID PRIMARY KEY (ID)
   USING INDEX TABLESPACE IDX_IUNET
) TABLESPACE TBS_IUNET;

--==============================================================
-- UM_ACTIVATION 账户激活表
--==============================================================
CREATE TABLE UM_ACTIVATION
(
	UUID        Varchar2(50)	NOT NULL,
	EMAIL       Varchar2(50)	NOT NULL,
	STATE      	INTEGER			NOT NULL,
	DATA		Varchar2(500) 	NOT NULL,
	CONSTRAINT P_ACTIVATION PRIMARY KEY (UUID)
	USING INDEX TABLESPACE IDX_IUNET
) TABLESPACE TBS_IUNET;

ALTER TABLE UM_FUNCTION
   ADD CONSTRAINT FK_UM_FUNCTION FOREIGN KEY (PARENT_ID)
      REFERENCES UM_FUNCTION (ID);
      

ALTER TABLE UM_ROLEAUTHORITY
   ADD CONSTRAINT FK_UM_ROLEAUTHORITY_R FOREIGN KEY (ROLE_ID)
      REFERENCES UM_ROLE (ID);
      
ALTER TABLE UM_ROLEAUTHORITY
   ADD CONSTRAINT FK_UM_ROLEAUTHORITY_F FOREIGN KEY (FUNCTION_ID)
      REFERENCES UM_FUNCTION (ID);
      

ALTER TABLE UM_USERGROUP
   ADD CONSTRAINT FK_DEPARTMENT FOREIGN KEY (PARENT_ID)
      REFERENCES UM_USERGROUP (ID);
      

ALTER TABLE UM_USERTOGROUP
   ADD CONSTRAINT FK_UM_USERTOGROUP FOREIGN KEY (GROUP_ID)
      REFERENCES UM_USERGROUP (ID);
      

ALTER TABLE UM_USERTOGROUP
   ADD CONSTRAINT FK_TOUSERID FOREIGN KEY (USER_ID)
      REFERENCES UM_USER (ID);
      

ALTER TABLE UM_USERTOROLE
   ADD CONSTRAINT FK_UM_USERTOROLE_UID FOREIGN KEY (USER_ID)
      REFERENCES UM_USER (ID);
      

ALTER TABLE UM_USERTOROLE
   ADD CONSTRAINT FK_UM_USERTOROLE_RID FOREIGN KEY (ROLE_ID)
      REFERENCES UM_ROLE (ID); 