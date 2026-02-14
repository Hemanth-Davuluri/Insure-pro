package com.insurePro.document_service.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurePro.document_service.Entity.DocumentEntity;
import com.insurePro.document_service.Repository.DocumentRepo;
import com.insurePro.document_service.Utilites.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
public class PDFService {

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private PdfGenerator pdfGenerator;

    @KafkaListener(topics = "document" , groupId = "user-group")
    private void GenerateDoc(String message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> policyDetails = mapper.readValue(message, new TypeReference<>() {});
        generatePolicyPdf(policyDetails);
    }

    private File generatePolicyPdf(Map<String, Object> policyDetails) throws Exception {
        File pdfFile = pdfGenerator.generatePolicyPdf(policyDetails.get("policyId").toString(), policyDetails);

        if(pdfGenerator.hasContent(pdfFile)){

            Object policyIdObj = policyDetails.get("policyId");
            Long policyId = null;
            if (policyIdObj instanceof Integer) {
                policyId = ((Integer) policyIdObj).longValue();
            } else if (policyIdObj instanceof Long) {
                policyId = (Long) policyIdObj;
            } else if (policyIdObj != null) {
                policyId = Long.valueOf(policyIdObj.toString());
            }

            saveToDb(policyId,pdfFile.getPath());
        }
        System.out.println(pdfFile.getPath());
        return pdfFile;
    }
    public void saveToDb(Long policyId,String path) {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setPolicyId(policyId);
        documentEntity.setFilePath(path);
        documentRepo.save(documentEntity);
    }

}
