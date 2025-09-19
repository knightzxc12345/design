package com.design.base.regex;

public interface BaseRegex {

    String BANNER_DATE_FORMAT = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]$";

    String VEHICLE_PRICE_DATE_FORMAT = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]$";

    String SALES_PLAN_DATE_FORMAT = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";

    String PAYMENT_DATE_FORMAT = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";

    String DELIVERY_DATE_FORMAT = "^[0-9]{4}-(0[1-9]|1[0-2])$";

    String API_SYNC_STOCK = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";

    String API_FIND_ORDER = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";

    String ORDER_FIND = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";

    String CONTRACT_FIND = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";

    String MOBILE = "^09[0-9]{8}$";

    String ID_CARD = "^[A-Z]{1}[12]{1}[0-9]{8}$";

    String VAT_NUMBER = "^\\d{8}";

    String PASSWORD = "^(?!.*\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";

    String CITY_ID = "^\\d{2}";

    String TOWN_ID = "^[A-Z]\\d{2}";

    String ZIP_CODE = "^\\d{3}(\\d{2})?$";

    String SYMBOL_ID = "^.{1,10}$";

    String EQUIPMENT = "^.{1,12}$";

}