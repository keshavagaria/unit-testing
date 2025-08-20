package com.hcl.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hcl.demo.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer>{

}
