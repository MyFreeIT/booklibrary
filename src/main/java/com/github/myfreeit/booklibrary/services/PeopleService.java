package com.github.myfreeit.booklibrary.services;

import com.github.myfreeit.booklibrary.models.Book;
import com.github.myfreeit.booklibrary.models.Person;
import com.github.myfreeit.booklibrary.repositories.PeopleRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * Copyright (c) 2026, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */
@Service
@Transactional(readOnly = true)
public class PeopleService {
  private final PeopleRepository peopleRepository;

  @Autowired
  public PeopleService(PeopleRepository peopleRepository) {
    this.peopleRepository = peopleRepository;
  }

  public List<Person> findAll() {
    return peopleRepository.findAll();
  }

  public Person findOne(int id) {
    Optional<Person> foundPerson = peopleRepository.findById(id);
    return foundPerson.orElse(null);
  }

  @Transactional
  public void save(Person person) {
    peopleRepository.save(person);
  }

  @Transactional
  public void update(int id, Person updatedPerson) {
    updatedPerson.setId(id);
    peopleRepository.save(updatedPerson);
  }

  @Transactional
  public void delete(int id) {
    peopleRepository.deleteById(id);
  }

  public Optional<Person> getPersonByFullName(String fullName) {
    return peopleRepository.findByFullName(fullName);
  }

  public List<Book> getBooksByPersonId(int id) {
    Optional<Person> person = peopleRepository.findById(id);
    if (person.isPresent()) {

      // Since OneToMany communication has lazy loading
      Hibernate.initialize(person.get().getBooks());

      return person.get().getBooks();

    } else {
      return Collections.emptyList();
    }
  }
}
