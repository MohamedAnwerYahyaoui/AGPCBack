package io.tutorial.spring.document.Service;

import io.tutorial.spring.document.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IDocumentService {
    Document addDocument(Document document);
    Document addDocumentWithFile(Document document, MultipartFile file) throws IOException;
    List<Document> getAllDocuments();
    Document updateDocument(Document document);
    void deleteDocument(Long idDocument);


}
