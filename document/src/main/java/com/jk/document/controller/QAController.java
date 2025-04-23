package com.jk.document.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jk.document.service.QAService;

@RestController
@RequestMapping("/api/qa")
public class QAController {

	@Autowired
	private QAService qaService;

	@GetMapping("/search")
	public ResponseEntity<List<String>> searchDocuments(@RequestParam("keyword") String keyword) {
		return ResponseEntity.ok(qaService.searchDocumentsByKeyword(keyword));
	}
}
