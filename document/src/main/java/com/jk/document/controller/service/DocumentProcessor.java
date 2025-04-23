package com.jk.document.controller.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jk.document.model.Document;

@Service
public class DocumentProcessor {

	// Processes a document and returns a Document object with extracted metadata
	// and content
	public Document processDocument(MultipartFile file, String author, String type, List<String> keywords, String title)
			throws Exception {
		Document document = new Document();
		document.setAuthor(author);
		document.setType(type);
		document.setKeywords(keywords);
		document.setTitle(title);

		// Extract content from the uploaded file (PDF, DOCX, TXT, etc.)
		String content = extractContent(file);

		document.setContent(content);
		document.setCreatedAt(LocalDateTime.now());

		return document;
	}

	// Extracts content based on file type (implementations for PDF, DOCX, etc. can
	// be added)
	private String extractContent(MultipartFile file) throws Exception {
		String content = "";

		if (file.getOriginalFilename().endsWith(".pdf")) {
			content = extractContentFromPdf(file);
		} else if (file.getOriginalFilename().endsWith(".docx")) {
			content = extractContentFromDocx(file);
		} else if (file.getOriginalFilename().endsWith(".txt")) {
			content = new String(file.getBytes(), StandardCharsets.UTF_8);
		}

		return content;
	}

	// Example method for PDF content extraction
	private String extractContentFromPdf(MultipartFile file) throws IOException {
		try (PDDocument document = PDDocument.load(file.getInputStream())) {
			PDFTextStripper stripper = new PDFTextStripper();
			return stripper.getText(document);
		}
	}

	// Example method for DOCX content extraction
	private String extractContentFromDocx(MultipartFile file) throws IOException {
		try (XWPFDocument doc = new XWPFDocument(file.getInputStream())) {
			StringBuilder content = new StringBuilder();
			for (XWPFParagraph para : doc.getParagraphs()) {
				content.append(para.getText()).append("\n");
			}
			return content.toString();
		}
	}
}
