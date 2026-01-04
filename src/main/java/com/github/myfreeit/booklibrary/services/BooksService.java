package com.github.myfreeit.booklibrary.services;

import com.github.myfreeit.booklibrary.models.Book;
import com.github.myfreeit.booklibrary.models.Person;
import com.github.myfreeit.booklibrary.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
public class BooksService {
  private final BooksRepository booksRepository;

  @Autowired
  public BooksService(BooksRepository booksRepository) {
    this.booksRepository = booksRepository;
  }

  public List<Book> findAll(boolean sortByYear) {
    if (sortByYear) {
      return booksRepository.findAll(Sort.by("year"));
    } else {
      return booksRepository.findAll();
    }
  }

  public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
    if (sortByYear) {
      return booksRepository
          .findAll(PageRequest.of(page, booksPerPage, Sort.by("year")))
          .getContent();
    } else {
      return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }
  }

  public Book findOne(int id) {
    Optional<Book> foundBook = booksRepository.findById(id);
    return foundBook.orElse(null);
  }

  public List<Book> searchByTitle(String query) {
    return booksRepository.searchByTitleStartingWith(query);
  }

  @Transactional
  public void save(Book book) {
    booksRepository.save(book);
  }

  @Transactional
  public void update(int id, Book updatedBook) {
    Book bookToBeUpdated = booksRepository.findById(id).get();

    // Added a new book (which is not in the Persistence context), so save() is needed
    updatedBook.setId(id);

    // So that the relations is not lost during the update
    updatedBook.setOwner(bookToBeUpdated.getOwner());

    booksRepository.save(updatedBook);
  }

  @Transactional
  public void delete(int id) {
    booksRepository.deleteById(id);
  }

  // Returns null if book has no owner
  public Person getBookOwner(int id) {
    return booksRepository.findById(id).map(Book::getOwner).orElse(null);
  }

  // Releases a book (this method is called when a person returns a book into the library)
  @Transactional
  public void release(int id) {
    booksRepository.findById(id).ifPresent(book -> book.setOwner(null));
  }

  // Assigns book to a person (this method is called when person checks out a book from the library)
  @Transactional
  public void assign(int id, Person selectedPerson) {
    booksRepository.findById(id).ifPresent(book -> book.setOwner(selectedPerson));
  }
}
