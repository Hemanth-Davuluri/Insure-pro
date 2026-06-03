package com.insurePro.document_service.Controller;

import com.insurePro.document_service.Entity.DocumentEntity;
import com.insurePro.document_service.Repository.DocumentRepo;
import com.insurePro.document_service.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/document")
public class PdfController {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private DocumentRepo documentRepo;


    @GetMapping("getDocument/{policyId}")
    public ResponseEntity<byte[]> getDocument(@PathVariable Long policyId) throws Exception {
        System.out.println("getDocument policyId: " + policyId);
        DocumentEntity document = documentRepo.findByPolicyId(policyId)
                        .orElseThrow();
        byte[] pdf = s3Service.downloadFile(document.getS3Key());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=policy.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
