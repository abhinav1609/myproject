package com.jk.document.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jk.document.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

	@Query("SELECT d FROM Document d WHERE d.contentVector @@ plainto_tsquery(:keyword)")
	List<Document> searchDocuments(String keyword);

	List<Document> findByAuthorContainingIgnoreCase(String author);

	List<Document> findByAuthorAndType(String author, String type);

	List<Document> findByAuthor(String author);

	List<Document> findByType(String type);

	List<Document> findAll();

	Document save(Document doc);

}

