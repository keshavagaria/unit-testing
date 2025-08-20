package com.hcl.demo;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.hcl.demo.dao.BookRepository;
import com.hcl.demo.entity.Book;
import com.hcl.demo.service.BookService;

@RunWith(SpringRunner.class)
@SpringBootTest
class Sb4SpringBootRestWithDbApplicationTests {

	@Autowired
	private BookService bookService;
	@MockBean
	private BookRepository bookRepository;
	
	@Test
	public void getBooksTest() {
		
		when(bookRepository.findAll()).thenReturn(Stream.of(new Book(101, "Let Us C", "Yashwant Kanetkar"),
									new Book(102, "C in Depth", "Vishal")).collect(Collectors.toList()));
		
		assertEquals(2, bookService.getAllBook().size());
	}
	@Test
	public void getBookByIdTest() {
		int id = 101;
		when(bookRepository.findById(id)).thenReturn(Optional.of(new Book(101, "Let Us C", "Yashwant Kanetkar")));
		assertEquals(true, bookService.getBookById(id).isPresent());
	}

	@Test
	public void addBookTest() {
		Book book = new Book(102, "C in Depth", "Vishal");
		when(bookRepository.save(book)).thenReturn(book);
		assertEquals(book, bookService.addBook(book));
	}
	/*
	public void updateBook() {
		Book book = new Book(102, "The Java Black Book", "Vishal");
		int id = 102;
		when(bookRepository.save(book)).thenReturn(book);
		assertEquals(book, bookService.updateBook(book,id));
		
	}*/
	@Test
	public void deleteBookTest() {
		Book book = new Book(102, "C in Depth", "Vishal");
		bookService.deleteBook(book);
		verify(bookRepository, times(1)).delete(book);
		
	}
}
