package com.github.myfreeit.booklibrary.dao;

/*
 * Copyright (c) 2025, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */
import com.github.myfreeit.booklibrary.models.Person;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PersonDao {
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public PersonDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Person> index() {
    return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
  }

  public Person show(int id) {
    return jdbcTemplate
        .query(
            "SELECT * FROM Person WHERE id=?",
            new BeanPropertyRowMapper<>(Person.class),
            new Object[] {id})
        .stream()
        .findAny()
        .orElse(null);
  }

  public void save(Person person) {
    jdbcTemplate.update(
        "INSERT INTO Person(full_name, year_of_birth) VALUES(?, ?)",
        person.getFullName(),
        person.getYearOfBirth());
  }

  public void update(int id, Person updatedPerson) {
    jdbcTemplate.update(
        "UPDATE Person SET full_name=?, year_of_birth=? WHERE id=?",
        updatedPerson.getFullName(),
        updatedPerson.getYearOfBirth(),
        id);
  }

  public void delete(int id) {
    jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
  }
}
