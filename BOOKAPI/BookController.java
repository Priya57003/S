package com.APIpractice.APIpractice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.APIpractice.APIpractice.BookService.BookService;
import com.APIpractice.APIpractice.entity.Book;

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@PostMapping
	public Book createBook(@RequestBody Book book)
	{
		return bookService.createBook(book);
		
	}

	@GetMapping("/{id}")
	public Optional<Book> getBook(@PathVariable int id)
	{
		return bookService.getBook(id);
		
	}
	
	@GetMapping("/get")
	public List<Book> getFindAllBook()
	{
		return bookService.getFindAllBook();
		
	}
	
	@PutMapping("/put/{id}")
	public Book updateBook(@PathVariable int id, @RequestBody Book name)
	{
		return bookService.updateBook(id,name);
		
	}
	
	
	
	
}
