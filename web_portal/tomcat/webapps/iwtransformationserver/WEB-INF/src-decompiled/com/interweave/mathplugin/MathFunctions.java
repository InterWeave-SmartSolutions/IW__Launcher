/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.mathplugin;

import java.util.Calendar;

public class MathFunctions {
    public static String mul(String a, String b) {
        return new Long(Long.valueOf(a) * Long.valueOf(b)).toString();
    }

    public static String mul1(String a, String b) {
        return new Long((Long.valueOf(a) + 1L) * Long.valueOf(b)).toString();
    }

    public static String cmp(String a, String b) {
        return new Boolean(Long.valueOf(a) > Long.valueOf(b)).toString();
    }

    public static String cmpe(String a, String b) {
        return new Boolean(Long.valueOf(a) >= Long.valueOf(b)).toString();
    }

    public static String day() {
        Calendar cln = Calendar.getInstance();
        int day = cln.get(5);
        return String.valueOf(day < 10 ? "0" : "") + String.valueOf(day);
    }

    public static String dayPlus(String days) {
        Calendar cln = Calendar.getInstance();
        cln.add(5, Integer.valueOf(days));
        int day = cln.get(5);
        return String.valueOf(day < 10 ? "0" : "") + String.valueOf(day);
    }

    public static String date() {
        Calendar cln = Calendar.getInstance();
        int day = cln.get(5);
        int month = cln.get(2) + 1;
        int year = cln.get(1);
        return String.valueOf(String.valueOf(year)) + "-" + (month < 10 ? "0" : "") + String.valueOf(month) + "-" + (day < 10 ? "0" : "") + String.valueOf(day);
    }

    public static String datePlus(String days) {
        Calendar cln = Calendar.getInstance();
        cln.add(5, Integer.valueOf(days));
        int day = cln.get(5);
        int month = cln.get(2) + 1;
        int year = cln.get(1);
        return String.valueOf(String.valueOf(year)) + "-" + (month < 10 ? "0" : "") + String.valueOf(month) + "-" + (day < 10 ? "0" : "") + String.valueOf(day);
    }

    public static String daysPrevMonth() {
        Calendar cln = Calendar.getInstance();
        cln.add(2, -1);
        return String.valueOf(cln.getActualMaximum(5));
    }
}

