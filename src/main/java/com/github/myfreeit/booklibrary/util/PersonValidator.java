package com.github.myfreeit.booklibrary.util;

import com.github.myfreeit.booklibrary.models.Person;
import com.github.myfreeit.booklibrary.services.PeopleService;
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
  private final PeopleService peopleService;

  @Autowired
  public PersonValidator(PeopleService peopleService) {
    this.peopleService = peopleService;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return Person.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    Person person = (Person) target;
    if (peopleService.getPersonByFullName(person.getFullName()).isPresent()) {
      errors.rejectValue("fullName", "person.validator.uniqueFullName");
    }
    if (!PersonNameValidator.isValid(person.getFullName())) {
      errors.rejectValue("fullName", "person.validator.caseFullName");
    }
  }
}
