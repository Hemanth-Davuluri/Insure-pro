package com.insurePro.document_service.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class DocumentEntity {

    @Id
    @Column(nullable = false)
    private Long policyId;

    @Column(nullable = false)
    private String filePath;

//    @Column(nullable = false)
//    private String hash;
}
