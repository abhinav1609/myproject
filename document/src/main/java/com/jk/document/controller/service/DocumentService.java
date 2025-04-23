package com.jk.document.controller.service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.jk.document.model.Document;

public interface DocumentService {

	public CompletableFuture<Document> ingestDocument(MultipartFile file, String author, String type,
			List<String> keywords, String title);

	public Page<Document> filterDocuments(String author, String type, LocalDate fromDate, LocalDate toDate,
		Pageable pageable);
	

}
