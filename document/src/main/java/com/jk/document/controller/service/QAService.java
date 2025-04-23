package com.jk.document.controller.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jk.document.dao.DocumentRepository;
import com.jk.document.model.Document;

@Service
public class QAService {

	@Autowired
	private DocumentRepository documentRepository;

	// @Cacheable(value = "qaSearch", key = "#keyword")
	public List<String> searchDocumentsByKeyword(String keyword) {

		if (keyword == null || keyword.trim().isEmpty()) {
			return Collections.emptyList();
		}
		List<Document> docs = documentRepository.findAll();
		return docs.stream()
				.filter(doc -> doc.getKeywords() != null
						&& doc.getKeywords().stream().anyMatch(k -> k.equalsIgnoreCase(keyword)))
				.map(Document::getContent).collect(Collectors.toList());
	}
}
