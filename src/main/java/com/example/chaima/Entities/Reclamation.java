package com.example.chaima.Entities;

import com.example.chaima.Entities.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id") // Ensure this annotation is present
    private Integer id;

    @JsonProperty("comment")
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    @JsonProperty("employee")
    private Employee employee;

    @Lob
    @Column(name = "file", columnDefinition = "LONGBLOB")
    @JsonProperty("file")
    private byte[] file;

    @JsonProperty("fileName")
    private String fileName;

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getComment() {
        return comment;
    }
}
