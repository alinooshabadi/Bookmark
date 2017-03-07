package com.novler.quotes.util;

public class Util {

  private static String[] persianNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};


  public static String clearText(String text) {
    return text.replace("&171;", "«").replace("&187;", "»");
  }

  public static String toPersianNumber(String text) {
    if (text.isEmpty())
      return "";
    String out = "";
    int length = text.length();
    for (int i = 0; i < length; i++) {
      char c = text.charAt(i);
      if ('0' <= c && c <= '9') {
        int number = Integer.parseInt(String.valueOf(c));
        out += persianNumbers[number];
      } else if (c == '٫') {
        out += '،';
      } else {
        out += c;
      }
    }
    return out;
  }
}
