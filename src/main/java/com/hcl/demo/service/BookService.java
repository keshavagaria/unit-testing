package com.hcl.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hcl.demo.dao.BookRepository;
import com.hcl.demo.entity.Book;

@Component
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	public List<Book> getAllBook() {
		
		List<Book> bookList=(List)bookRepository.findAll();
		System.out.println("Getting data from DB "+bookList);
		return bookList;
	}
	
	public Optional<Book> getBookById(int id) {
		
		Optional<Book> book= bookRepository.findById(id);
		System.out.println("Getting BookBy ID from DB "+book.get());
		return book;
	}
	
	public Book addBook(Book book) {
		Book book2 = bookRepository.save(book);
		System.out.println("Artificially Saving data to DB "+book2);
		return book2;
	}

	public void deleteBook(Book book) {
		
		System.out.println("Artificially Deleting data to DB "+book);
		bookRepository.delete(book);
		
	}

	

	public void updateBook(Book book, int id) {
		
		bookRepository.save(book);
	}
}
