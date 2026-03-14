package com.interweave.plugin.A4WCOM;

public class JIntegraInit {
  static boolean initialised = false;
  public static void init() {
    if(initialised) return;
    initialised = true;
  }
}
