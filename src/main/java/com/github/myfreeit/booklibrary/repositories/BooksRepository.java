package com.github.myfreeit.booklibrary.repositories;

import com.github.myfreeit.booklibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Copyright (c) 2026, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */
@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
  List<Book> searchByTitleStartingWith(String title);
}
