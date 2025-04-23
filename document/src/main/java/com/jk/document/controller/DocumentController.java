package com.jk.document.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jk.document.controller.service.DocumentService;
import com.jk.document.model.Document;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

  @Autowired
  private DocumentService service;

  @PostMapping("/upload")
	public CompletableFuture<ResponseEntity<Document>> ingestDocument(@RequestParam("file") MultipartFile file,
			@RequestParam(name = "author", required = false) String author,
			@RequestParam(name = "type", required = false) String type,
			@RequestParam(name = "title", required = false) String title,
			@RequestParam(name = "keywords", required = false) List<String> keywords) {
		return service.ingestDocument(file, author, type, keywords, title).thenApply(ResponseEntity::ok);
	}

 
  @GetMapping("/filter")
	public ResponseEntity<Page<Document>> filterDocuments(
			@RequestParam(name = "author", required = false) String author,
			@RequestParam(name = "type", required = false) String type,
			@RequestParam(name = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@RequestParam(name = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
			@RequestParam(name = "direction", defaultValue = "desc") String direction) {

		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return ResponseEntity.ok(service.filterDocuments(author, type, fromDate, toDate, pageable));
	}
}

