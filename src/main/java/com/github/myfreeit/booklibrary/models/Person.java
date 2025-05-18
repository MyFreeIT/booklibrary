package com.github.myfreeit.booklibrary.models;

/*
 * Copyright (c) 2025, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {
  private int id;

  @NotEmpty(message = "{person.name.empty}")
  @Size(min = 2, max = 100, message = "{person.name.size}")
  private String fullName;

  @Min(value = 1900, message = "{person.yearOfBirth.min}")
  private int yearOfBirth;

  public Person() {}

  public Person(String fullName, int yearOfBirth) {
    this.fullName = fullName;
    this.yearOfBirth = yearOfBirth;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public int getYearOfBirth() {
    return yearOfBirth;
  }

  public void setYearOfBirth(int yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
  }
}
