package org.test.bookpub.repository;

import org.springframework.data.repository.CrudRepository;
import org.test.bookpub.entity.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

    Book findBookByIsbn(String isbn);

}
