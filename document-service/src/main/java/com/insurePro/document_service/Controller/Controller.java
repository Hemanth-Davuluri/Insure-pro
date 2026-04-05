package com.insurePro.document_service.Controller;

import com.insurePro.document_service.Entity.DocumentEntity;
import com.insurePro.document_service.Service.PDFService;
import com.insurePro.document_service.Utilites.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private PDFService pdfService;

    private final String basePath = "C:\\Generated files";

/*
    *** sample document creation
 */
//    @GetMapping("/pdfGeneration")
//    public File pdfgenerartion() throws Exception {
//        Long PolicyId = 1L;
//        Map<String,Object> entry = new HashMap<>();
//        Map<String,Object> details = new HashMap<>();
//
//        details.put("propertyAddress", "GothamiNagar 4th line");
//        details.put("propertyAge", 3L);
//        details.put("propertyValue", 6000000);
//
//        entry.put("payload",details);
//        entry.put("coverageType", "Home_Policy");
//        entry.put("policyId",1L);
//        entry.put("customerId", 2L);
//
//        for (Map.Entry<String,Object> entry1 : entry.entrySet()){
//            System.out.println(entry1.getKey()+": "+ entry1.getValue());
//        }
//        return pdfGenerator.generatePolicyPdf(PolicyId,entry);
//    }

    @PostMapping("/generate/{policyId}")
    public ResponseEntity<FileSystemResource> generatePdf(
            @PathVariable("policyId") String policyId,
            @RequestBody Map<String,Object> policyDetails
            )throws Exception{

        File pdfFile = pdfGenerator.generatePolicyPdf(policyId, policyDetails);

        if(pdfGenerator.hasContent(pdfFile)){
            pdfService.saveToDb(Long.valueOf(policyId),pdfFile.getPath());
        }
        FileSystemResource resource = new FileSystemResource(pdfFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+pdfFile.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfFile.length())
                .body(resource);
    }

    @GetMapping("/retrieve/{policyId}")
    public ResponseEntity<FileSystemResource> retrievePdf(@PathVariable("policyId") Long policyId,
                                                          @RequestParam Map<String,Object> policyDetails
                                                          ) {
        String filePath =policyDetails.get("coverageType") +"_"+policyDetails.get("customerId")+".pdf";
        File pdfFile = new File(basePath + filePath);

        if (!pdfFile.exists()) {
            return ResponseEntity.notFound().build();
        }
        FileSystemResource resource = new FileSystemResource(pdfFile);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdfFile.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfFile.length())
                .body(resource);
    }


}
