package io.tutorial.spring.document.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity

public class Notification {
    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Integer version;

    private String message;
    @OneToOne
    @JoinColumn(name = "document_id")
    @JsonBackReference

    private Document document;

    @Column(nullable = false)
    private Boolean lue = false;

    public Notification() {
    }

    public Notification(String message, Document document) {
        this.message = message;
        this.document = document;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
    public Boolean getLue() {
        return lue;
    }

    public void setLue(Boolean lue) {
        this.lue = lue;
    }

}
