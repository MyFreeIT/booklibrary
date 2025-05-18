package com.github.myfreeit.booklibrary.controllers;

import com.github.myfreeit.booklibrary.dao.PersonDao;
import com.github.myfreeit.booklibrary.models.Person;
import com.github.myfreeit.booklibrary.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Copyright (c) 2025, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */

@Controller
@RequestMapping("/people")
public class PeopleController {
  private final PersonDao personDao;
  private final PersonValidator personValidator;

  @Autowired
  public PeopleController(PersonDao personDao, PersonValidator personValidator) {
    this.personDao = personDao;
    this.personValidator = personValidator;
  }

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("people", personDao.index());
    return "people/index";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable("id") int id, Model model) {
    model.addAttribute("person", personDao.show(id));
    return "people/show";
  }

  @GetMapping("/new")
  public String newPerson(@ModelAttribute("person") Person person) {
    return "people/new";
  }

  @PostMapping()
  public String create(
      @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {

    personValidator.validate(person, bindingResult);

    if (bindingResult.hasErrors()) {
      return "people/new";
    }
    personDao.save(person);
    return "redirect:/people";
  }
}
