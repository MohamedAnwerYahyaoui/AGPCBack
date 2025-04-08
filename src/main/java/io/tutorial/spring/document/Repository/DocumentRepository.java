package io.tutorial.spring.document.Repository;

import io.tutorial.spring.document.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;



@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

}
