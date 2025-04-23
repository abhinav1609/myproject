package com.jk.document.controller.service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jk.document.dao.DocumentRepository;
import com.jk.document.model.Document;
import com.jk.documentqa.service.DocumentProcessor;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private DocumentProcessor documentProcessor;

	@Async
	public CompletableFuture<Document> ingestDocument(MultipartFile file, String author, String type,
			List<String> keywords, String title) {
		try {

			// Process the document (e.g., extract text, generate metadata)
			Document document = documentProcessor.processDocument(file, author, type, keywords, title);

			return CompletableFuture.completedFuture(documentRepository.save(document));
		} catch (Exception e) {
			throw new RuntimeException("Failed to upload document", e);
		}
	}

	@Override
	public Page<Document> filterDocuments(String author, String type, LocalDate fromDate, LocalDate toDate,
			org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable pageable) {
		List<Document> docs;

		if (author != null && type != null) {
			docs = documentRepository.findByAuthorAndType(author, type);
		} else if (author != null) {
			docs = documentRepository.findByAuthor(author);
		} else if (type != null) {
			docs = documentRepository.findByType(type);
		} else {
			docs = documentRepository.findAll();
		}

		// Apply date filter manually
		if (fromDate != null || toDate != null) {
			docs = docs.stream().filter(doc -> {
				LocalDate createdAt = doc.getCreatedAt().toLocalDate(); // Assuming getCreatedAt() returns LocalDateTime
				boolean afterFrom = (fromDate == null || !createdAt.isBefore(fromDate));
				boolean beforeTo = (toDate == null || !createdAt.isAfter(toDate));
				return afterFrom && beforeTo;
			}).collect(Collectors.toList());
		}

		// Pagination manually
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), docs.size());
		List<Document> pagedDocs = docs.subList(start, end);

		return new PageImpl<>(pagedDocs, pageable, docs.size());

	}

}

