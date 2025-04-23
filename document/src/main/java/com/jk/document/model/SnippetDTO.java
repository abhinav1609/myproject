package com.jk.document.model;
public class SnippetDTO {

    private Long documentId;  // Unique identifier for the document
    private String title;     // The document's title
    private String snippet;   // A small excerpt of the document's content

    // Constructor
    public SnippetDTO(Long documentId, String title, String snippet) {
        this.documentId = documentId;
        this.title = title;
        this.snippet = snippet;
    }

    // Getters and Setters
    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
