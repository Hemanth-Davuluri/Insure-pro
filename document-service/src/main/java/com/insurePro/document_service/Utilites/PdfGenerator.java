package com.insurePro.document_service.Utilites;

import com.insurePro.document_service.DTO.PolicyEventDTO;
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

    private final String basePath="C:\\Generated files\\";

    public File generatePolicyPdf(String policyId, PolicyEventDTO dto) throws Exception {

        String filePath =dto.getCoverageType() +"_"+ dto.getCustomerId()+".pdf";
        File pdfFile= Paths.get(basePath,filePath).toFile();

//        creating a document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document,page);

        float startX = 50;
        float startY = 750;
        float lineHeight = 18;

//      writing the data to document...
        contentStream.setFont(PDType1Font.TIMES_BOLD,12);
        contentStream.beginText();
        contentStream.newLineAtOffset(startX,startY);
        contentStream.showText("Policy Document");
        contentStream.endText();

        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        float yPosition = startY - 40;
        yPosition = writeLine(contentStream, "Policy ID", dto.getPolicyId(), startX, yPosition);
        yPosition = writeLine(contentStream, "Customer ID", dto.getCustomerId(), startX, yPosition);
        yPosition = writeLine(contentStream, "Agent ID", dto.getAgentId(), startX, yPosition);
        yPosition = writeLine(contentStream, "Coverage Type", dto.getCoverageType(), startX, yPosition);
        yPosition = writeLine(contentStream, "Premium", dto.getPremium(), startX, yPosition);
        yPosition = writeLine(contentStream, "Tax", dto.getTax(), startX, yPosition);
        yPosition = writeLine(contentStream, "Status", dto.getStatus(), startX, yPosition);

        if (dto.getPayLoad() != null && !dto.getPayLoad().isEmpty()) {
            yPosition -= 20;
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, yPosition);
            contentStream.showText("Additional Details:");
            contentStream.endText();

            yPosition -= 20;
            for (Map.Entry<String, String> entry : dto.getPayLoad().entrySet()) {
                yPosition = writeLine(contentStream, entry.getKey(), entry.getValue(), startX, yPosition);
            }
        }

        contentStream.close();

        document.save(pdfFile);
        document.close();
        System.out.println("Created a document");

        return pdfFile;
    }

    private float writeLine(PDPageContentStream contentStream,
                            String label,
                            Object value,
                            float x,
                            float y) throws IOException {

        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(label + ": " + String.valueOf(value));
        contentStream.endText();
        return y - 18;
    }
}
