drop database if exists ladok;
create database ladok charset utf8 collate utf8_swedish_ci;
drop user if exists 'ladok'@'localhost';
create user 'ladok'@'localhost' identified by 'ladok';
grant all on ladok.* to 'ladok'@'localhost';
use ladok;
CREATE TABLE `qrtz_job_details` (
    `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
    `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `IS_DURABLE` varchar(1) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `JOB_DATA` blob,
    PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
    KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
    KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
);
CREATE TABLE `qrtz_locks` (
    `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `LOCK_NAME` varchar(40) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
);
CREATE TABLE `qrtz_triggers` (
    `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
    `NEXT_FIRE_TIME` bigint DEFAULT NULL,
    `PREV_FIRE_TIME` bigint DEFAULT NULL,
    `PRIORITY` int DEFAULT NULL,
    `TRIGGER_STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `START_TIME` bigint NOT NULL,
    `END_TIME` bigint DEFAULT NULL,
    `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
    `MISFIRE_INSTR` smallint DEFAULT NULL,
    `JOB_DATA` blob,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
    KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
    KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
    KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
    KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
    KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
    CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
);
CREATE TABLE `qrtz_paused_trigger_grps` (
    `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
);
CREATE TABLE `qrtz_simple_triggers` (
    `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `REPEAT_COUNT` bigint NOT NULL,
    `REPEAT_INTERVAL` bigint NOT NULL,
    `TIMES_TRIGGERED` bigint NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
);
CREATE TABLE `qrtz_fired_triggers` (
    `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `ENTRY_ID` varchar(95) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `FIRED_TIME` bigint NOT NULL,
    `SCHED_TIME` bigint NOT NULL,
    `PRIORITY` int NOT NULL,
    `STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
    `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
    `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
    `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
);
CREATE TABLE `qrtz_scheduler_state` (
    `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `LAST_CHECKIN_TIME` bigint NOT NULL,
    `CHECKIN_INTERVAL` bigint NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
);
CREATE TABLE `qrtz_simprop_triggers` (
    `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `STR_PROP_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
    `STR_PROP_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
    `STR_PROP_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
    `INT_PROP_1` int DEFAULT NULL,
    `INT_PROP_2` int DEFAULT NULL,
    `LONG_PROP_1` bigint DEFAULT NULL,
    `LONG_PROP_2` bigint DEFAULT NULL,
    `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
    `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
    `BOOL_PROP_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
    `BOOL_PROP_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
);
CREATE TABLE `qrtz_cron_triggers` (
    `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
);
CREATE TABLE `qrtz_blob_triggers` (
    `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `BLOB_DATA` blob,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
);
CREATE TABLE `qrtz_calendars` (
    `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL,
    `CALENDAR` blob NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
);
