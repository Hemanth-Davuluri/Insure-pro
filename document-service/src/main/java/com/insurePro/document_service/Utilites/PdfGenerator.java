package com.insurePro.document_service.Utilites;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class PdfGenerator {

    private final String basePath="C:\\Users\\hemanth.sai.davuluri\\seshu's work\\Generated Documents\\";

    public File generatePolicyPdf(String policyId, Map<String,Object> policyDetails) throws Exception {

//      Defining Filepath
//        =policyDetails.get("coverageType") +"_"+policyDetails.get("customerName")+"_"+policyDetails.get("customerId")+".pdf";
        String filePath =policyDetails.get("coverageType") +"_"+policyDetails.get("customerId")+".pdf";
        File pdfFile= Paths.get(basePath,filePath).toFile();

//        creating a document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document,page);

//      writing the data to document...
        contentStream.setFont(PDType1Font.TIMES_BOLD,12);
        contentStream.beginText();
        contentStream.newLineAtOffset(50,700);
        contentStream.showText("Policy Details");
        contentStream.newLineAtOffset(0, -20);
        for(Map.Entry<String,Object> entry:policyDetails.entrySet()){
            contentStream.showText(entry.getKey()+": "+entry.getValue());
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -15);
        }
        contentStream.endText();
        contentStream.close();

        document.save(pdfFile);
        document.close();
        System.out.println("Created a document");

        return pdfFile;
    }

    public Boolean hasContent(File pdfFile) throws Exception {
        try(PDDocument document = PDDocument.load(pdfFile)){

            if(document.getNumberOfPages()==0){
                return false;
            }

            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(document).trim();
            return !text.isEmpty();
        }
    }
}
