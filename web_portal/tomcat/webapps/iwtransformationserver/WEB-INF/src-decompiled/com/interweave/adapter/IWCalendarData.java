/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.core.IWRequest;
import java.util.Calendar;
import java.util.Hashtable;

public class IWCalendarData
implements IWIDataMap {
    private Access curAccess = null;
    private String mapName = null;
    private Datamap dataMap = null;
    public Hashtable accessList = new Hashtable();
    String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    int[] DaysInMonth = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int userMonth = 0;
    int userYear = 0;
    int numDays = 0;
    static final int FEBRUARY = 1;

    public String getDriver() {
        return null;
    }

    public String getPassword() {
        return null;
    }

    public String getUrl() {
        return null;
    }

    public String getUser() {
        return null;
    }

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    public void closeConnection() {
    }

    public StringBuffer go(IWRequest request) throws Exception {
        StringBuffer responseBuffer = new StringBuffer();
        Object strData = null;
        Access access = (Access)((Object)this.accessList.get("procedure"));
        Object values = null;
        Calendar now = Calendar.getInstance();
        this.userMonth = now.get(5);
        this.userYear = now.get(1) - 1900;
        this.numDays = this.DaysInMonth[this.userMonth] + (this.IsLeapYear(this.userYear) && this.userMonth == 1 ? 1 : 0);
        if (access != null) {
            responseBuffer.append("    <datamap ID=\"1\" name=\"calendar\" rowcount=\"1\">\n      <data rowcount=\"1\">\n        <row number=\"1\">\n");
            responseBuffer.append("            <col number=\"1\" name=\"year\">" + this.userYear + "</col>\n");
            responseBuffer.append("            <col number=\"2\" name=\"monthName\">" + this.months[this.userMonth] + "</col>\n");
            responseBuffer.append("            <col number=\"3\" name=\"month\">" + (this.userMonth + 1) + "</col>\n");
            responseBuffer.append("            <col number=\"4\" name=\"days\">" + this.numDays + "</col>\n");
            responseBuffer.append("            <col number=\"5\" name=\"today\">" + now.get(5) + "</col>\n");
            responseBuffer.append("        </row>\n      </data>\n    </datamap>\n");
            this.userMonth = 0;
            while (this.userMonth < 12) {
                this.genMonth(responseBuffer);
                ++this.userMonth;
            }
            ++this.userYear;
            this.userMonth = 0;
            while (this.userMonth < 12) {
                this.genMonth(responseBuffer);
                ++this.userMonth;
            }
        }
        return responseBuffer;
    }

    public void genMonth(StringBuffer responseBuffer) {
        int count = 1;
        this.numDays = this.DaysInMonth[this.userMonth] + (this.IsLeapYear(this.userYear) && this.userMonth == 1 ? 1 : 0);
        int firstOfMonth = this.CalcFirstOfMonth(this.userYear, this.userMonth);
        responseBuffer.append("    <datamap ID=\"" + (this.userMonth + 1) + "\" name=\"" + this.months[this.userMonth] + "\" rowcount=\"1\">\n      <data rowcount=\"1\">\n        <row number=\"1\">\n");
        int i = 0;
        while (i < firstOfMonth) {
            responseBuffer.append("            <col number=\"" + (i + 1) + "\" name=\"" + this.days[i] + "\">0</col>\n");
            ++i;
        }
        int day = 1;
        while (day <= this.numDays) {
            responseBuffer.append("            <col number=\"" + (firstOfMonth + 1) + "\" name=\"" + this.days[firstOfMonth] + "\">" + day + "</col>\n");
            if (++firstOfMonth == 7) {
                responseBuffer.append("\n        </row>\n        <row number=\"" + ++count + "\">\n");
                firstOfMonth = 0;
            }
            ++day;
        }
        if (firstOfMonth > 0) {
            i = firstOfMonth;
            while (i < 7) {
                responseBuffer.append("            <col number=\"" + (i + 1) + "\" name=\"" + this.days[i] + "\">0</col>\n");
                ++i;
            }
        }
        responseBuffer.append("        </row>\n      </data>\n    </datamap>\n");
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        this.mapName = map.getName();
        this.dataMap = map;
        for (Access access : map.getAccess()) {
            this.accessList.put(access.getType(), access);
        }
    }

    boolean IsLeapYear(int year) {
        if (year % 100 == 0) {
            return year % 400 == 0;
        }
        return year % 4 == 0;
    }

    int CalcFirstOfMonth(int year, int month) {
        if (year < 1582) {
            return -1;
        }
        if (month < 0 || month > 11) {
            return -1;
        }
        int firstDay = this.CalcJanuaryFirst(year);
        int i = 0;
        while (i < month) {
            firstDay += this.DaysInMonth[i];
            ++i;
        }
        if (month > 1 && this.IsLeapYear(year)) {
            ++firstDay;
        }
        return firstDay % 7;
    }

    int CalcJanuaryFirst(int year) {
        if (year < 1582) {
            return -1;
        }
        return (5 + (year - 1582) + this.CalcLeapYears(year)) % 7;
    }

    int CalcLeapYears(int year) {
        if (year < 1582) {
            return -1;
        }
        int leapYears = (year - 1581) / 4;
        int hundreds = (year - 1501) / 100;
        leapYears -= hundreds;
        int fourHundreds = (year - 1201) / 400;
        return leapYears += fourHundreds;
    }
}

