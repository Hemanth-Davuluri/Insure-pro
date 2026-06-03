package com.insurePro.document_service.Service;

import com.insurePro.document_service.DTO.PolicyEventDTO;
import com.insurePro.document_service.Entity.DocumentEntity;
import com.insurePro.document_service.Repository.DocumentRepo;
import com.insurePro.document_service.Utilites.HashUtil;
import com.insurePro.document_service.Utilites.PdfGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
@Slf4j
public class PDFService {

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private HashUtil hashUtil;

    @Autowired
    private S3Service s3Service;

    @KafkaListener(topics = "document" , groupId = "user-group")
    public void generateDoc(PolicyEventDTO policyEventDTO){
        if (documentRepo.existsByPolicyId(policyEventDTO.getPolicyId())) {
            return;
        }
        try {
            generatePolicyPdf(policyEventDTO);
        }
        catch (Exception e) {
            log.error("PDF generation failed for policy Id {}", policyEventDTO.getPolicyId(), e);
        }
    }

    @Transactional
    private void generatePolicyPdf(PolicyEventDTO policyEventDTO) throws Exception {
        File pdfFile = pdfGenerator.generatePolicyPdf(String.valueOf(policyEventDTO.getPolicyId()), policyEventDTO);
        String hash = hashUtil.generateSHA256(pdfFile);
        String S3Key = s3Service.uploadFile(pdfFile, policyEventDTO.getPolicyId());
        DocumentEntity savedInDb = saveToDb(policyEventDTO.getPolicyId(), hash, S3Key);
        if(savedInDb.getId() == null){
            throw new RuntimeException("Failed to save document metadata");
        }
        boolean delete = pdfFile.delete();
        if (!delete){
            log.warn("Failed to delete temp file {}", pdfFile.getAbsolutePath());
        }
    }

    private DocumentEntity saveToDb(Long policyId,String documentHash, String s3Key){
        LocalDateTime createdAt = LocalDateTime.now();
        DocumentEntity documentEntity = DocumentEntity.builder()
                .policyId(policyId)
                .documentHash(documentHash)
                .s3Key(s3Key)
                .createdAt(createdAt)
                .build();
        DocumentEntity save = documentRepo.save(documentEntity);
        return save;
    }
}
