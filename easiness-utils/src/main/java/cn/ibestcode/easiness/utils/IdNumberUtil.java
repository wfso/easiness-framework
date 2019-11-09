package cn.ibestcode.easiness.utils;

import cn.ibestcode.easiness.utils.exception.IdNumberException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Slf4j
public class IdNumberUtil {

  private static final List<Integer> integers = Arrays.asList(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
  private static final List<Character> characters = Arrays.asList('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');

  private String gender;
  private Date birthday;
  private int age;
  private int ageMonth;
  private int ageMonthDay;

  public IdNumberUtil(String idNumber) {
    if (idNumber.length() != 18) {
      throw new IdNumberException("身份证号长度应为18位长度的字符串");
    }
    char[] ids = idNumber.toCharArray();
    int sum = 0;
    for (int i = 0; i < 17; i++) {
      if (ids[i] > '9' || ids[i] < '0') {
        throw new IdNumberException("身份证号前17位字符只能是0-9的数字");
      }
      sum += Integer.parseInt(Character.toString(ids[i])) * integers.get(i);
    }
    sum = sum % 11;
    if (characters.get(sum) != ids[17]) {
      throw new IdNumberException("非法身份证号");
    }

    // 性别解析
    this.gender = (Integer.parseInt(Character.toString(ids[16])) % 2) == 0 ? "女" : "男";

    // 生日解析

    try {
      this.birthday = DateUtils.parseDate(idNumber.substring(6, 14), "yyyyMMdd");
    } catch (ParseException e) {
      log.error(e.getMessage(), e);
    }

    // 年龄解析
    if (this.birthday != null) {
      Calendar birthdayCalendar = Calendar.getInstance();
      birthdayCalendar.setTime(birthday);
      int birthdayYear = birthdayCalendar.get(Calendar.YEAR);
      int birthdayMonth = birthdayCalendar.get(Calendar.MONTH);
      int birthdayDay = birthdayCalendar.get(Calendar.DAY_OF_MONTH);

      Date now = new Date();
      Calendar nowCalendar = Calendar.getInstance();
      nowCalendar.setTime(now);
      int nowYear = nowCalendar.get(Calendar.YEAR);
      int nowMonth = nowCalendar.get(Calendar.MONTH);
      int nowDay = nowCalendar.get(Calendar.DAY_OF_MONTH);

      this.age = nowYear - birthdayYear;
      this.ageMonth = nowMonth - birthdayMonth;
      this.ageMonthDay = nowDay - birthdayDay;

      if (this.ageMonthDay < 0) {
        Calendar tmp = Calendar.getInstance();
        tmp.set(nowYear, nowMonth - 1, 0);
        this.ageMonthDay += tmp.get(Calendar.DAY_OF_MONTH);
        this.ageMonth--;
      }

      if (this.ageMonth < 0) {
        this.ageMonth += 12;
        this.age--;
      }

    }

  }

  public static IdNumberUtil getInstance(String idNumber) {
    return new IdNumberUtil(idNumber);
  }

}
