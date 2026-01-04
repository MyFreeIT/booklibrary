package com.github.myfreeit.booklibrary.controllers;

import com.github.myfreeit.booklibrary.models.Book;
import com.github.myfreeit.booklibrary.models.Person;
import com.github.myfreeit.booklibrary.services.BooksService;
import com.github.myfreeit.booklibrary.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/books")
public class BooksController {

  private final BooksService booksService;
  private final PeopleService peopleService;

  @Autowired
  public BooksController(BooksService booksService, PeopleService peopleService) {
    this.booksService = booksService;
    this.peopleService = peopleService;
  }

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("books", booksService.findAll());
    return "books/index";
  }

  @GetMapping("/{id}")
  public String show(
      @PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
    model.addAttribute("book", booksService.findOne(id));

    Person bookOwner = booksService.getBookOwner(id);

    if (bookOwner != null) {
      model.addAttribute("owner", bookOwner);
    } else {
      model.addAttribute("people", peopleService.findAll());
    }

    return "books/show";
  }

  @GetMapping("/new")
  public String newBook(@ModelAttribute("book") Book book) {
    return "books/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "books/new";
    }
    booksService.save(book);
    return "redirect:/books";
  }

  @GetMapping("/{id}/edit")
  public String edit(Model model, @PathVariable("id") int id) {
    model.addAttribute("book", booksService.findOne(id));
    return "books/edit";
  }

  @PatchMapping("/{id}")
  public String update(
      @ModelAttribute("book") @Valid Book book,
      BindingResult bindingResult,
      @PathVariable("id") int id) {

    if (bindingResult.hasErrors()) {
      return "books/edit";
    }
    booksService.update(id, book);
    return "redirect:/books";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    booksService.delete(id);
    return "redirect:/books";
  }

  @PatchMapping("/{id}/release")
  public String release(@PathVariable("id") int id) {
    booksService.release(id);
    return "redirect:/books/" + id;
  }

  @PatchMapping("/{id}/assign")
  public String assign(
      @PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
    booksService.assign(id, selectedPerson);
    return "redirect:/books/" + id;
  }
}
