package com.github.myfreeit.booklibrary.util;

import com.github.myfreeit.booklibrary.dao.PersonDao;
import com.github.myfreeit.booklibrary.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/*
 * Copyright (c) 2025, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */

@Component
public class PersonValidator implements Validator {
  private final PersonDao personDao;

  @Autowired
  public PersonValidator(PersonDao personDao) {
    this.personDao = personDao;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return Person.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    Person person = (Person) target;
    if (personDao.getPersonByFullName(person.getFullName()).isPresent()) {
      errors.rejectValue("fullName", "", "person.validator.msg");
    }
  }
}
