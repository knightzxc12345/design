package com.design.base.common;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Common {

    public final static ZoneId zoneId = ZoneId.of("Asia/Taipei");

    public final static DateTimeFormatter DATE_FORMAT_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public final static DateTimeFormatter DATE_FORMAT_2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###");

    public final static long DAYS = 24 * 60 * 60;

    public final static long HOURS = 60 * 60;

    public final static long MINUTES = 60;

}