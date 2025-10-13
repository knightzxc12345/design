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

    public final static String ENCODING = "utf-8";

    public final static String CONTENT_TYPE = "application/json;charset=utf-8";

    public final static String SYSTEM_UUID = "23da4d7d-2f53-4fd0-abfd-9549357939eb";

    public final static String IMAGE_PATH_CUSTOMER = "/uploads/customer/";

    public final static String IMAGE_PATH_SUPPLIER = "/uploads/supplier/";

    public final static String IMAGE_PATH_ITEM = "/uploads/item/";

    public final static String IMAGE_PATH_PRODUCT = "/uploads/product/";

}