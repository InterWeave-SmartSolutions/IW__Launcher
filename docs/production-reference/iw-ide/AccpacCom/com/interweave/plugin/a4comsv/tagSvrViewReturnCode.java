package com.interweave.plugin.a4comsv;

/**
 * Constants from tagSvrViewReturnCode' enum. Generated 12/10/2006 12:40:14 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\a4wcomsv.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 *
 * Generator Options:
 *   AwtForOcxs = False
 *   PromptForTypeLibraries = False
 *   RetryOnReject = False
 *   IDispatchOnly = False
 *   GenBeanInfo = False
 *   LowerCaseMemberNames = True
 *   TreatInStarAsIn = False
 *   ArraysAsObjects = False
 *   OmitRestrictedMethods = False
 *   ClashPrefix = zz_
 *   ImplementConflictingInterfaces = False
 *   DontRenameSameMethods = False
 *   RenameConflictingInterfaceMethods = False
 *   ReuseMethods = False
 *
 * Command Line Only Options:
 *   MakeClsidsPublic = False
 *   DontOverwrite = False
 */
public interface tagSvrViewReturnCode extends java.io.Serializable {
  static final int SVR_VC_SUCCESS = 0;
  static final int SVR_VC_LOAD_FAILED = 100;
  static final int SVR_VC_OPEN_FAILED = 101;
  static final int SVR_VC_COMPOSE_FAILED = 102;
  static final int SVR_VC_ROTOENTRY_FAILED = 103;
  static final int SVR_VC_OPEN_FAILED_PS = 500;
  static final int SVR_VC_OPEN_FAILED_PSCONNECT = 501;
  static final int SVR_VC_OLD_GENERAL = 1;
  static final int SVR_VC_OLD_WARNING = 10;
  static final int SVR_VC_OLD_OTHER = 2;
  static final int SVR_VC_OLD_RECORD_NOT_FOUND = 1;
  static final int SVR_VC_OLD_RECORD_NO_MORE_DATA = 1;
  static final int SVR_VC_OLD_RECORD_EXISTS = 1;
  static final int SVR_VC_OLD_RECORD_DUPLICATE = 1;
  static final int SVR_VC_OLD_TABLE_EXISTS = 1;
  static final int SVR_VC_WARNING = -1;
  static final int SVR_VC_GENERAL = 1000;
  static final int SVR_VC_RECORD_NOT_FOUND = 1020;
  static final int SVR_VC_RECORD_NO_MORE_DATA = 1021;
  static final int SVR_VC_RECORD_EXISTS = 1022;
  static final int SVR_VC_RECORD_DUPLICATE = 1023;
  static final int SVR_VC_RECORD_INVALID = 1024;
  static final int SVR_VC_RECORD_LOCKED = 1025;
  static final int SVR_VC_RECORD_CONFLICT = 1026;
  static final int SVR_VC_RECORD_NOT_LOCKED = 1027;
  static final int SVR_VC_TABLE_EXISTS = 1040;
  static final int SVR_VC_TABLE_NOT_FOUND = 1041;
  static final int SVR_VC_PERMISSION_NONE = 1060;
  static final int SVR_VC_MEMORY_NO_MORE = 1080;
  static final int SVR_VC_MEMORY_BUFFER_LIMIT = 1081;
  static final int SVR_VC_SVR_FILTER_SYNTAX = 1100;
  static final int SVR_VC_SVR_FILTER_OTHER = 1101;
  static final int SVR_VC_KEY_INVALID = 1120;
  static final int SVR_VC_KEY_NUMBER = 1121;
  static final int SVR_VC_KEY_CHANGED = 1122;
  static final int SVR_VC_SVR_FIELD_INVALID = 1140;
  static final int SVR_VC_SVR_FIELD_NUMBER = 1141;
  static final int SVR_VC_SVR_FIELD_INDEX = 1142;
  static final int SVR_VC_SVR_FIELD_DISABLED = 1143;
  static final int SVR_VC_SVR_FIELD_READONLY = 1144;
  static final int SVR_VC_TRANSACTION_NONE = 1160;
  static final int SVR_VC_TRANSACTION_OPEN = 1161;
  static final int SVR_VC_REVISION_PROTOCOL = 1180;
  static final int SVR_VC_SVR_DATABASE_PARAMETER = 1200;
  static final int SVR_VC_SVR_DATABASE_LIMIT = 1201;
  static final int SVR_VC_SVR_DATABASE_OTHER = 1202;
  static final int SVR_VC_SVR_DATABASE_DICTIONARY = 1203;
  static final int SVR_VC_RPC_FAILURE = 1220;
  static final int SVR_VC_OPERATION_CANCELLED = 2000;
  static final int SVR_VC_NON_SVR_VIEW_ERROR = 2001;
  static final int SVR_VC_APPLICATION_DEFINED_BASE = 9000;
  static final int SVR_VC_APPLICATION_DEFINED_END = 9999;
  static final int SVR_VC_WARNING_GENERAL = -1;
  static final int SVR_VC_WARNING_APPLICATION_DEFINED_BASE = -1999;
  static final int SVR_VC_WARNING_APPLICATION_DEFINED_END = -1000;
}
