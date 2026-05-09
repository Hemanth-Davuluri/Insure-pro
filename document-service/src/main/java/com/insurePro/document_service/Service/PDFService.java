package com.insurePro.document_service.Service;

import com.insurePro.document_service.DTO.PolicyEventDTO;
import com.insurePro.document_service.Entity.DocumentEntity;
import com.insurePro.document_service.Repository.DocumentRepo;
import com.insurePro.document_service.Utilites.PdfGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class PDFService {

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private PdfGenerator pdfGenerator;

    @KafkaListener(topics = "document" , groupId = "user-group")
    public void generateDoc(PolicyEventDTO policyEventDTO){
        if (documentRepo.existsByPolicyId(policyEventDTO.getPolicyId())) {
            return;
        }
        try {
            File file = generatePolicyPdf(policyEventDTO);
            saveToDb(policyEventDTO.getPolicyId(), file.getPath());
            System.out.println(file.getPath());
        }
        catch (Exception e) {
            log.error("PDF generation failed for policy Id {}", policyEventDTO.getPolicyId(), e);
        }
    }

    private File generatePolicyPdf(PolicyEventDTO policyEventDTO) throws Exception {
        File pdfFile = pdfGenerator.generatePolicyPdf(String.valueOf(policyEventDTO.getPolicyId()), policyEventDTO);

        if(pdfGenerator.hasContent(pdfFile)){
            Long policyId = policyEventDTO.getPolicyId();
            saveToDb(policyId,pdfFile.getPath());
        }
        System.out.println(pdfFile.getPath());
        return pdfFile;
    }
    private void saveToDb(Long policyId,String path) {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setPolicyId(policyId);
        documentEntity.setFilePath(path);
        documentRepo.save(documentEntity);
    }
}
