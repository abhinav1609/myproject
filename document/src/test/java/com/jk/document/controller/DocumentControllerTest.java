package com.jk.document.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.jk.document.service.DocumentService;
import com.jk.documentqa.model.Document;

@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {

	@Autowired
	private MockMvc mockMvc; // MockMvc to simulate HTTP requests

	@MockBean
	private DocumentService documentService; // Mock the DocumentService

	@Test
	void testUploadDocument_Success() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test content".getBytes());

		Document document = new Document("Test Title", "Test Content", "Test Author", "pdf",
				List.of("keyword1", "keyword2"));

		when(documentService.uploadDocument(any(), any(), any(), any(), any()))
				.thenReturn(CompletableFuture.completedFuture(document));

		mockMvc.perform(multipart("/api/documents/upload").file(file).param("author", "Test Author")
				.param("type", "pdf").param("title", "Test Title").param("keywords", "keyword1", "keyword2"))
				.andDo(print()) // prints the response, helpful!
				.andExpect(status().isOk());
	}

	@Test
	void testFilterDocuments_Success() throws Exception {
		// Arrange
		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
		Page<Document> page = new PageImpl<>(List.of(new Document("Test Title", "Test Content")));
		when(documentService.filterDocuments(anyString(), anyString(), any(), any(), eq(pageable))).thenReturn(page);

		// Act and Assert
		mockMvc.perform(get("/api/documents/filter").param("author", "Test Author").param("type", "pdf")
				.param("page", "0").param("size", "10").param("sortBy", "createdAt").param("direction", "desc"))
				.andExpect(status().isOk()) // HTTP 200 OK
				.andExpect(jsonPath("$.content[0].title").value("Test Title"));
	}

	@Test
	void testFilterDocuments_NoResults() throws Exception {
		// Arrange
		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
		Page<Document> page = Page.empty(); // Empty page (no documents)
		when(documentService.filterDocuments(anyString(), anyString(), any(), any(), eq(pageable))).thenReturn(page);

		// Act and Assert
		mockMvc.perform(get("/api/documents/filter").param("author", "Non-Existent Author").param("type", "txt")
				.param("page", "0").param("size", "10").param("sortBy", "createdAt").param("direction", "desc"))
				.andExpect(status().isOk()) // HTTP 200 OK
				.andExpect(jsonPath("$.content").isEmpty()); // Expect empty content
	}

	@Test
	void testUploadDocument_Failure() throws Exception {
		// Arrange
		MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test content".getBytes());

		when(documentService.uploadDocument(any(MultipartFile.class), anyString(), anyString(), anyList(), anyString()))
				.thenReturn(CompletableFuture.failedFuture(new RuntimeException("Error")));

		// Act and Assert
		mockMvc.perform(
				multipart("/api/documents/upload").file(file).param("author", "Test Author").param("type", "pdf"))
				.andExpect(status().isInternalServerError()); // HTTP 500
	}

}
