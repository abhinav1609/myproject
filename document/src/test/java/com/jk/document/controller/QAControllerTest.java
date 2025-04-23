package com.jk.document.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jk.document.service.QAService;

@WebMvcTest(QAController.class)
public class QAControllerTest {

	@Autowired
	private MockMvc mockMvc; // MockMvc to simulate HTTP requests

	@MockBean
	private QAService qaService; // Mock the QAService

	@Test
	void testSearchDocuments_Success() throws Exception {
		// Arrange
		when(qaService.searchDocumentsByKeyword("keyword")).thenReturn(List.of("Document 1", "Document 2"));

		// Act and Assert
		mockMvc.perform(get("/api/qa/search").param("keyword", "keyword")).andExpect(status().isOk()) // HTTP 200 OK
				.andExpect(jsonPath("$[0]").value("Document 1")).andExpect(jsonPath("$[1]").value("Document 2"));
	}

	@Test
	void testSearchDocuments_NoResults() throws Exception {
		// Arrange
		when(qaService.searchDocumentsByKeyword("nonexistent")).thenReturn(List.of());

		// Act and Assert
		mockMvc.perform(get("/api/qa/search").param("keyword", "nonexistent")).andExpect(status().isOk()) // HTTP 200 OK
				.andExpect(jsonPath("$").isEmpty()); // Expect empty result
	}
}
