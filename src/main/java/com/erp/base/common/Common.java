package com.erp.base.common;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Common {

    public final static String TOKEN_HEADER = "Volvo-CV-Token";

    public final static String TOKEN_PREFIX = "Bearer ";

    public final static String CLAIM_ROLE = "role";

    public final static String CLAIM_PERMISSION = "permissions";

    public final static ZoneId ZONE_ID = ZoneId.of("Asia/Taipei");

    public final static DateTimeFormatter DATE_FORMAT_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public final static DateTimeFormatter DATE_FORMAT_2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public final static DateTimeFormatter DATE_FORMAT_3 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public final static DecimalFormat NUMBER_FORMAT = new DecimalFormat("#,###");

    public final static long JWT_REFRESH_TOKEN_VALIDITY = 24 * 60 * 60 * 1000;

    public final static long JWT_ACCESS_TOKEN_VALIDITY = 15 * 60 * 1000;

    public final static String COOKIE_REFRESH_TOKEN_KEY = "refreshToken";

    public final static int COOKIE_REFRESH_TOKEN_AGE = 60 * 60 * 24;

    public final static String CLAIM_USER = "userId";

    public final static String CLAIM_VERSION = "version";

    public final static long DAYS = 24 * 60 * 60;

    public final static long HOURS = 60 * 60;

    public final static long MINUTES = 60;

    public final static String ENCODING = "utf-8";

    public final static String CONTENT_TYPE = "application/json;charset=utf-8";

    public final static Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(.+?)}}");


}