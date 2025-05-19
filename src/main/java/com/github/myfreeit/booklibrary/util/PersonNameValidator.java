package com.github.myfreeit.booklibrary.util;

/*
 * Copyright (c) 2025, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */

import java.util.Arrays;
import java.util.Set;

public class PersonNameValidator {
  private PersonNameValidator() {}

  private static final Set<String> LOWERCASE_PREFIXES =
      Set.of("van", "von", "de", "di", "du", "del");

  public static boolean isValid(String fullName) {
    return Arrays.stream(fullName.split("[\\s-]")).allMatch(PersonNameValidator::isValidWord);
  }

  private static boolean isValidWord(String word) {
    if (word.isEmpty()) {
      return false;
    }
    if (LOWERCASE_PREFIXES.contains(word.toLowerCase())) {
      return true;
    }

    return startsWithUpperCase(word) && hasValidTail(word);
  }

  private static boolean startsWithUpperCase(String word) {
    return Character.isUpperCase(word.charAt(0));
  }

  private static boolean hasValidTail(String word) {
    return word.substring(1).chars().allMatch(c -> Character.isLowerCase(c) || c == '\'');
  }
}
