package com.github.myfreeit.booklibrary.controllers;

import com.github.myfreeit.booklibrary.dao.BookDao;
import com.github.myfreeit.booklibrary.dao.PersonDao;
import com.github.myfreeit.booklibrary.models.Book;
import com.github.myfreeit.booklibrary.models.Person;
import jakarta.validation.Valid;
import java.util.Optional;
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
  private final BookDao bookDao;
  private final PersonDao personDao;

  @Autowired
  public BooksController(BookDao bookDao, PersonDao personDao) {
    this.bookDao = bookDao;
    this.personDao = personDao;
  }

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("books", bookDao.index());
    return "books/index";
  }

  @GetMapping("/{id}")
  public String show(
      @PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
    model.addAttribute("book", bookDao.show(id));

    Optional<Person> bookOwner = bookDao.getBookOwner(id);

    if (bookOwner.isPresent()) {
      model.addAttribute("owner", bookOwner.get());
    } else {
      model.addAttribute("people", personDao.index());
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
    bookDao.save(book);
    return "redirect:/books";
  }

  @GetMapping("/{id}/edit")
  public String edit(Model model, @PathVariable("id") int id) {
    model.addAttribute("book", bookDao.show(id));
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
    bookDao.update(id, book);
    return "redirect:/books";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    bookDao.delete(id);
    return "redirect:/books";
  }

  @PatchMapping("/{id}/release")
  public String release(@PathVariable("id") int id) {
    bookDao.release(id);
    return "redirect:/books/" + id;
  }

  @PatchMapping("/{id}/assign")
  public String assign(
      @PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
    bookDao.assign(id, selectedPerson);
    return "redirect:/books/" + id;
  }
}
