package com.jk.document.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.jk.documentqa.model.Column;
import com.jk.documentqa.model.ElementCollection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "documents")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String author;
	private String type;
	@Column(columnDefinition = "TEXT")
	private String content;
	private LocalDateTime createdAt;

	@ElementCollection
	private List<String> keywords;

	public Document(Long id, String title, String author, String type, String content, LocalDateTime createdAt,
			List<String> keywords) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.type = type;
		this.content = content;
		this.createdAt = createdAt;
		this.keywords = keywords;
	}
	
	

	public Document(String title, String author, String type, String content, List<String> keywords) {
		super();
		this.title = title;
		this.author = author;
		this.type = type;
		this.content = content;
		this.keywords = keywords;
	}



	public Document(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}



	public Document() {
		super();
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
