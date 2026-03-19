package com.erp.utils;


import com.erp.base.response.CustomResponse;
import com.erp.base.response.enums.UtilCode;
import com.erp.base.common.Common;
import com.erp.handler.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;

public class InstantUtil {

    public static String to(Instant instant){
        if(null == instant){
            return null;
        }
        DateTimeFormatter formatter = Common.DATE_FORMAT_1.withZone(Common.ZONE_ID);
        return formatter.format(instant);
    }

    public static Instant to(String time){
        try {
            if(StringUtils.isBlank(time)){
                return null;
            }
            LocalDateTime localDateTime = LocalDateTime.parse(time, Common.DATE_FORMAT_1.withZone(ZoneOffset.UTC));
            return localDateTime.atZone(Common.ZONE_ID).toInstant();
        } catch (DateTimeParseException e) {
            throw new BusinessException(new CustomResponse<>(UtilCode.INSTANT_ERROR));
        }
    }

    public static String to(Instant time, DateTimeFormatter dateTimeFormatter){
        try {
            if(null == time){
                return null;
            }
            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(time, Common.ZONE_ID);
            return dateTimeFormatter.format(zonedDateTime);
        } catch (DateTimeParseException e) {
            throw new BusinessException(new CustomResponse<>(UtilCode.INSTANT_ERROR));
        }
    }

    public static Instant to(String time, DateTimeFormatter dateTimeFormatter){
        try {
            if(StringUtils.isBlank(time)){
                return null;
            }
            if(Common.DATE_FORMAT_2.equals(dateTimeFormatter)){
                LocalDate localDate = LocalDate.parse(time, dateTimeFormatter);
                return localDate.atStartOfDay().atZone(Common.ZONE_ID).toInstant();
            }
            return to(time);
        } catch (DateTimeParseException e) {
            throw new BusinessException(new CustomResponse<>(UtilCode.INSTANT_ERROR));
        }
    }

    public static Instant addDays(Instant time, Integer count){
        if(null == time || null == count){
            return null;
        }
        if(0 == count){
            return time;
        }
        if(0 > count){
            return time.minusSeconds(count * Common.DAYS * -1);
        }
        return time.plusSeconds(count * Common.DAYS);
    }

    public static Instant addHours(Instant time, Integer count){
        if(null == time || null == count){
            return null;
        }
        if(0 == count){
            return time;
        }
        if(0 > count){
            return time.minusSeconds(count * Common.HOURS * -1);
        }
        return time.plusSeconds(count * Common.HOURS);
    }

    public static Instant addMinutes(Instant time, Integer count){
        if(null == time || null == count){
            return null;
        }
        if(0 == count){
            return time;
        }
        if(0 > count){
            return time.minusSeconds(count * Common.MINUTES * -1);
        }
        return time.plusSeconds(count * Common.MINUTES);
    }

    public static String startOfDay(String time){
        if(null == time){
            return null;
        }
        Instant date = to(time);
        date = startOfDay(date);
        return to(date);
    }

    public static Instant startOfDay(Instant time){
        if(null == time){
            return null;
        }
        ZonedDateTime zonedDateTime = time.atZone(Common.ZONE_ID);
        LocalDate date = zonedDateTime.toLocalDate();
        ZonedDateTime startOfDay = date.atStartOfDay(Common.ZONE_ID);
        return startOfDay.toInstant();
    }

    public static Instant startOfMonth(Instant time){
        if(null == time){
            return null;
        }
        ZonedDateTime zonedDateTime = time.atZone(Common.ZONE_ID);
        LocalDate date = zonedDateTime.toLocalDate().withDayOfMonth(1);
        ZonedDateTime startOfDay = date.atStartOfDay(Common.ZONE_ID);
        return startOfDay.toInstant();
    }

    public static Instant endOfDay(Instant time){
        if(null == time){
            return null;
        }
        ZonedDateTime zonedDateTime = time.atZone(Common.ZONE_ID);
        LocalDate date = zonedDateTime.toLocalDate();
        ZonedDateTime endOfDay = date.atTime(23, 59, 59).atZone(Common.ZONE_ID);
        return endOfDay.toInstant();
    }

    public static Instant endOfMonth(Instant time){
        if(null == time){
            return null;
        }
        ZonedDateTime zonedDateTime = time.atZone(Common.ZONE_ID);
        LocalDate date = zonedDateTime.toLocalDate().with(TemporalAdjusters.lastDayOfMonth());
        ZonedDateTime endOfDay = date.atTime(23, 59, 59).atZone(Common.ZONE_ID);
        return endOfDay.toInstant();
    }

    public static Instant withHour(Instant time, int hour){
        if(null == time){
            return null;
        }
        ZonedDateTime zonedDateTime = time.atZone(Common.ZONE_ID);
        ZonedDateTime adjustedDateTime = zonedDateTime.withHour(hour);
        return adjustedDateTime.toInstant();
    }

    public static String year(Instant instant){
        if(null == instant){
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy");
        return dateTimeFormatter.format(instant.atZone(Common.ZONE_ID));
    }

    public static String month(Instant instant){
        if(null == instant){
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM");
        return dateTimeFormatter.format(instant.atZone(Common.ZONE_ID));
    }

    public static String day(Instant instant){
        if(null == instant){
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd");
        return dateTimeFormatter.format(instant.atZone(Common.ZONE_ID));
    }

    public static String hour(Instant instant){
        if(null == instant){
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH");
        return dateTimeFormatter.format(instant.atZone(Common.ZONE_ID));
    }

}