package com.carcaret.web;

import java.util.UUID;

public class ContextHolder {

  private static final ThreadLocal<String> UUID_LOCAL = new ThreadLocal<String>() {
    @Override
    protected String initialValue() {
      return "00000000-0000-0000-0000-000000000000";
    }
  };

  public static void init() {
    UUID_LOCAL.set(UUID.randomUUID().toString());
  }

  public static String getUUID() {
    return UUID_LOCAL.get();
  }

  public static void remove() {
    UUID_LOCAL.remove();
  }

}
