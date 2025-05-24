package com.automation.util;

import com.automation.core.lands.model.CertificateOfOccupancy;
import com.automation.core.lands.model.GroundRent;
import com.automation.core.lands.model.StatutoryAllocation;
import com.automation.util.enums.ReportType;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ReportUtil {
    public static byte[] generatePdfReportFromCofO(List<CertificateOfOccupancy> cofoList, ReportType reportType) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.addTitle("Certificate of Occupancy Report - " + reportType);
            document.add(new Paragraph("CofO Report - " + reportType));
            document.add(new Paragraph("Generated On: " + new java.util.Date()));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 3, 3, 4});

            table.addCell("S/N");
            table.addCell("Applicant Name");
            table.addCell("Date Submitted");
            table.addCell("Status");

            int i = 1;
            for (CertificateOfOccupancy c : cofoList) {
                table.addCell(String.valueOf(i++));
                table.addCell(c.getApplicantName());
                table.addCell(c.getCreatedAt().toString());
                table.addCell(c.getStatus());
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    public static byte[] generatePdfReportFromStatutory(List<StatutoryAllocation> cofoList, ReportType reportType) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.addTitle("Certificate of Occupancy Report - " + reportType);
            document.add(new Paragraph("CofO Report - " + reportType));
            document.add(new Paragraph("Generated On: " + new java.util.Date()));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 3, 3, 4});

            table.addCell("S/N");
            table.addCell("Applicant Name");
            table.addCell("Date Submitted");
            table.addCell("Status");

            int i = 1;
            for (StatutoryAllocation c : cofoList) {
                table.addCell(String.valueOf(i++));
                table.addCell(c.getApplicantName());
                table.addCell(c.getCreatedAt().toString());
                table.addCell(c.getStatus());
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    public static byte[] generatePdfReportFromGroundRent(List<GroundRent> cofoList, ReportType reportType) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.addTitle("Certificate of Occupancy Report - " + reportType);
            document.add(new Paragraph("CofO Report - " + reportType));
            document.add(new Paragraph("Generated On: " + new java.util.Date()));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 3, 3, 4});

            table.addCell("S/N");
            table.addCell("baNo");
            table.addCell("Date Submitted");
            table.addCell("Status");

            int i = 1;
            for (GroundRent c : cofoList) {
                table.addCell(String.valueOf(i++));
                table.addCell(c.getBaNo());
                table.addCell(c.getCreatedAt().toString());
                table.addCell(c.getStatus());
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
