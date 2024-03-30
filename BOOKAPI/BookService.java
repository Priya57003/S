package com.APIpractice.APIpractice.BookService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIpractice.APIpractice.entity.Book;
import com.APIpractice.APIpractice.repo.BookRepo;

@Service
public class BookService {
	
	@Autowired
	private BookRepo bookRepo;

	public Optional<Book> getBook(int id) {
		return  bookRepo.findById(id);
		
	}

	public Book createBook(Book book) {
		
		return bookRepo.save(book);
	}

	public List<Book> getFindAllBook() {
		
		return bookRepo.findAll();
	}

	public Book updateBook(int id,Book bname) {
		Book book=  bookRepo.findById(id).orElseThrow(null);
		book.setBookname(bname.getBookname());
		book.setAuthor(bname.getAuthor());
		return bookRepo.save(book);
		
		
	}

	

}
