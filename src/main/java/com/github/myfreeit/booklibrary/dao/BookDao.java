package com.github.myfreeit.booklibrary.dao;

import com.github.myfreeit.booklibrary.models.Book;
import com.github.myfreeit.booklibrary.models.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/*
 * Copyright (c) 2025, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */
@Component
public class BookDao {
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public BookDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Book> index() {
    return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
  }

  public Book show(int id) {
    return jdbcTemplate
        .query(
            "SELECT * FROM Book WHERE id=?",
            new BeanPropertyRowMapper<>(Book.class),
            new Object[] {id})
        .stream()
        .findAny()
        .orElse(null);
  }

  public void save(Book book) {
    jdbcTemplate.update(
        "INSERT INTO Book(title, author, year) VALUES (?, ?, ?)",
        book.getTitle(),
        book.getAuthor(),
        book.getYear());
  }

  public void update(int id, Book updatedBook) {
    jdbcTemplate.update(
        "UPDATE Book SET title=?, author=?, year=? WHERE id=?",
        updatedBook.getTitle(),
        updatedBook.getAuthor(),
        updatedBook.getYear(),
        id);
  }

  public void delete(int id) {
    jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
  }

  public Optional<Person> getBookOwner(int id) {
    return jdbcTemplate
        .query(
            "SELECT Person.* FROM Book JOIN Person ON Book.person_id=Person.id"
                + " WHERE Book.id=?",
            new BeanPropertyRowMapper<>(Person.class),
            new Object[] {id})
        .stream()
        .findAny();
  }

  public void release(int id) {
    jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE id=?", id);
  }

  public void assign(int id, Person selectPerson) {
    jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", selectPerson.getId(), id);
  }
}
