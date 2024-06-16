package com.io.api.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class Utils {
  private static SecureRandom random = new SecureRandom();

  public static boolean validateAmountBigDecimal(BigDecimal amount) {
    if (amount == BigDecimal.ZERO || amount.compareTo(BigDecimal.ZERO) <= 0) {
      return false;
    }
    return true;
  }

  public static String generateRandomString(int numberCharacter) {
    return RandomStringUtils.randomAlphanumeric(numberCharacter);
  }

  public static int generateRandomInt(int n) {
    n = Math.abs(n);
    return (int) Math.pow(10.0D, (double) (n - 1))
        + random.nextInt(9 * (int) Math.pow(10.0D, (double) (n - 1)));
  }

  public static long getCurrentTimestamp(int timeZone) {
    /*
     * Use java.time.Instant to get a time stamp from the Java epoch. According to the JavaDoc,
     * “epoch-seconds are measured from the standard Java epoch of 1970-01-01T00:00:00Z, where
     * instants after the epoch have positive values:
     */
    Instant instant = Instant.now().plusSeconds(timeZone * 60 * 60);
    return instant.getEpochSecond();
  }

  public static boolean isValidEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@" + // part before @
        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);
    if (email == null)
      return false;
    return pat.matcher(email).matches();
  }

  public static String generateUUID() {
    return UUID.randomUUID().toString().toUpperCase();
  }


  public static String getMaskedEmail(String email) {
    if (StringUtils.isBlank(email))
      return "";
    return email.replaceAll("(^[^@]{3}|(?!^)\\G)[^@]", "$1*");
  }

  public static String getMaskedPhone(String s) {
    if (StringUtils.isBlank(s))
      return "";
    StringBuilder lastFour = new StringBuilder();
    int check = 0;
    for (int i = s.length() - 1; i >= 0; i--) {
      if (Character.isDigit(s.charAt(i))) {
        check++;
      }
      if (check <= 3)
        lastFour.append(s.charAt(i));
      else if (check >= s.length() - 2)
        lastFour.append(s.charAt(i));
      else
        lastFour.append(Character.isDigit(s.charAt(i)) ? "*" : s.charAt(i));
    }
    return lastFour.reverse().toString();
  }

  public static String[] mergerPath(String[] listPath, String[] listPathSwagger) {
    List<String> pathList = new ArrayList<String>(Arrays.asList(listPath));
    pathList.addAll(Arrays.asList(listPathSwagger));
    return pathList.toArray(new String[pathList.size()]);
  }

  public static String printLogStackTrace(Exception e) {
    StringWriter errors = new StringWriter();
    e.printStackTrace(new PrintWriter(errors));
    String msg = errors.toString();
    return msg;
  }

}
