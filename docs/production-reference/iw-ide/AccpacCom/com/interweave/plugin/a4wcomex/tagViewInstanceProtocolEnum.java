package com.interweave.plugin.a4wcomex;

/**
 * Constants from tagViewInstanceProtocolEnum' enum. Generated 02/10/2006 12:21:33 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
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
public interface tagViewInstanceProtocolEnum extends java.io.Serializable {
  static final int VIEW_INSTANCE_PROTOCOL_BASIC_FLAT = 0;
  static final int VIEW_INSTANCE_PROTOCOL_BASIC_BATCH = 16;
  static final int VIEW_INSTANCE_PROTOCOL_BASIC_HEADER = 32;
  static final int VIEW_INSTANCE_PROTOCOL_BASIC_DETAIL = 48;
  static final int VIEW_INSTANCE_PROTOCOL_BASIC_SUPER = 64;
  static final int VIEW_INSTANCE_PROTOCOL_REVISION_NONE = 0;
  static final int VIEW_INSTANCE_PROTOCOL_REVISION_SEQUENCED = 128;
  static final int VIEW_INSTANCE_PROTOCOL_REVISION_ORDERED = 256;
  static final int VIEW_INSTANCE_PROTOCOL_GENERATE_KEY = 1024;
  static final int VIEW_INSTANCE_PROTOCOL_HEADER_MUST_EXIST = 2048;
  static final int VIEW_INSTANCE_PROTOCOL_SUBCLASS_NONE = 0;
  static final int VIEW_INSTANCE_PROTOCOL_SUBCLASS_OVERRIDE = 4096;
  static final int VIEW_INSTANCE_PROTOCOL_SUBCLASS_ALTER = 8192;
  static final int VIEW_INSTANCE_PROTOCOL_SUBCLASS_JOIN = 12288;
  static final int VIEW_INSTANCE_PROTOCOL_MASK_BASIC = 112;
  static final int VIEW_INSTANCE_PROTOCOL_MASK_REVISION = 896;
  static final int VIEW_INSTANCE_PROTOCOL_MASK_SUBCLASS = 28672;
  static final int VIEW_INSTANCE_PROTOCOL_MASK_SEGMENTS_ADDED = 15;
}
