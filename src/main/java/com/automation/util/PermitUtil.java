package com.automation.util;

import com.automation.core.commerce.model.BusinessRegistration;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PermitUtil {

    public static byte[] generateBusinessPremisesPermit(BusinessRegistration registration) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // Header - Ministry of Commerce
            Paragraph header = new Paragraph("ABIA STATE MINISTRY OF COMMERCE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph subHeader = new Paragraph("BUSINESS PREMISES PERMIT", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            subHeader.setAlignment(Element.ALIGN_CENTER);
            document.add(subHeader);

            document.add(new Paragraph("\n"));

            // Body - Permit Details
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            addCell(table, "Business Name:", registration.getBusinessName(), labelFont, valueFont);
            addCell(table, "Business Number:", registration.getBusinessNumber(), labelFont, valueFont);
            addCell(table, "Owner Name:", registration.getOwnerName(), labelFont, valueFont);
            addCell(table, "Address:", registration.getAddress(), labelFont, valueFont);
            addCell(table, "Phone:", registration.getPhone(), labelFont, valueFont);
            addCell(table, "Email:", registration.getEmail(), labelFont, valueFont);
            addCell(table, "Date Issued:", LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")), labelFont, valueFont);
            addCell(table, "Validity:", "1 Year", labelFont, valueFont);

            document.add(table);

            document.add(new Paragraph("\n\n"));

            // Certification text
            Paragraph certification = new Paragraph(
                "This is to certify that the business premises of " + registration.getBusinessName() +
                " located at " + registration.getAddress() + " has been inspected and approved " +
                "in accordance with the regulations of the Abia State Ministry of Commerce.",
                FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC)
            );
            certification.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(certification);

            document.add(new Paragraph("\n\n\n"));

            // Signature Area
            PdfPTable sigTable = new PdfPTable(2);
            sigTable.setWidthPercentage(100);

            PdfPCell cell1 = new PdfPCell(new Phrase("__________________________\nCommissioner for Commerce", FontFactory.getFont(FontFactory.HELVETICA, 12)));
            cell1.setBorder(Rectangle.NO_BORDER);
            sigTable.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Phrase("__________________________\nDate", FontFactory.getFont(FontFactory.HELVETICA, 12)));
            cell2.setBorder(Rectangle.NO_BORDER);
            sigTable.addCell(cell2);

            // Border removed to fix compilation error
            document.add(sigTable);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Business Premises Permit PDF", e);
        }
    }

    private static void addCell(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setPadding(5);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "N/A", valueFont));
        valueCell.setPadding(5);
        table.addCell(valueCell);
    }

    public static byte[] generateEnvironmentPermit(com.automation.core.basepa.model.EnvironmentApplication registration) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            Paragraph header = new Paragraph("ABIA STATE MINISTRY OF ENVIRONMENT", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph subHeader = new Paragraph("ENVIRONMENTAL OPERATIONAL PERMIT", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            subHeader.setAlignment(Element.ALIGN_CENTER);
            document.add(subHeader);

            document.add(new Paragraph("\n"));

            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            addCell(table, "Applicant Name:", registration.getApplicantName(), labelFont, valueFont);
            addCell(table, "Contact Person:", registration.getContactPerson(), labelFont, valueFont);
            addCell(table, "Address:", registration.getAddress(), labelFont, valueFont);
            addCell(table, "Industry Type:", registration.getIndustryType(), labelFont, valueFont);
            addCell(table, "Permit Type:", registration.getPermitType() != null ? registration.getPermitType().toString() : "N/A", labelFont, valueFont);
            addCell(table, "Waste Description:", registration.getWasteDescription(), labelFont, valueFont);
            addCell(table, "Operational License:", registration.getOperationalLicenseNumber(), labelFont, valueFont);
            addCell(table, "Date Issued:", LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")), labelFont, valueFont);

            document.add(table);
            document.add(new Paragraph("\n\n"));

            Paragraph certification = new Paragraph(
                "This is to certify that " + registration.getApplicantName() +
                " is authorized to operate in accordance with the environmental regulations of Abia State.",
                FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC)
            );
            certification.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(certification);

            document.add(new Paragraph("\n\n\n"));

            PdfPTable sigTable = new PdfPTable(2);
            sigTable.setWidthPercentage(100);

            PdfPCell cell1 = new PdfPCell(new Phrase("__________________________\nCommissioner for Environment", FontFactory.getFont(FontFactory.HELVETICA, 12)));
            cell1.setBorder(Rectangle.NO_BORDER);
            sigTable.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Phrase("__________________________\nDate", FontFactory.getFont(FontFactory.HELVETICA, 12)));
            cell2.setBorder(Rectangle.NO_BORDER);
            sigTable.addCell(cell2);

            document.add(sigTable);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Environment Permit PDF", e);
        }
    }
}
