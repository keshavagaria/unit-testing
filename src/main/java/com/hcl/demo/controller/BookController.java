package com.hcl.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.demo.entity.Book;
import com.hcl.demo.service.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getBooks() {
		
		List<Book> bookList=bookService.getAllBook();
		if(bookList.size()==0) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(bookList));
	}
	
	@GetMapping("/books/{id}")
	public ResponseEntity<?> getBookById(@PathVariable int id) {
		
		Optional<Book> book=bookService.getBookById(id);
		if(book.isPresent()) {
			//Optional<String> author=Optional.of(book.get().getAuthor());
			Optional<String> author=Optional.ofNullable(book.get().getAuthor().toUpperCase());
/*			if(author.isPresent()) {
			return new ResponseEntity<>("Author is null gor given book",HttpStatus.NOT_FOUND);
		}
*/		
			//author.ifPresent(n -> System.out.println("Name is Present - "+n));
			author.ifPresentOrElse((n) -> System.out.println("Name is Present - "+n),
									() -> System.out.println("Name is not present"));
		}else {
			return new ResponseEntity<>("Book with given id is not found!!!",HttpStatus.NOT_FOUND);
		}
		return null;
	}
	
	@PostMapping("/books")
	public ResponseEntity<Book> addNewBook(@RequestBody  Book book) {
		try {
			bookService.addBook(book);
			return ResponseEntity.status(HttpStatus.CREATED).body(book);
		}catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}finally {
			System.out.println("Finally block executed");
		}
		
	}
	
	
	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<Void> deleteBookById(@PathVariable("bookId")  int id) {
		try {
			bookService.deleteBookById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}finally {
			System.out.println("FInally BLock Executed");
		}
		
	}
	
	@PutMapping("/books/{bookId}")
	public ResponseEntity<Book> updateTheBook(@RequestBody Book book,
							  @PathVariable("bookId")  int id) {
		try {
			bookService.updateBook(book,id);
			return ResponseEntity.ok().body(book);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}finally {
			System.out.println("FInally BLock Executed");
		}
	}
}
