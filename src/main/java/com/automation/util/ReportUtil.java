package com.automation.util;

import com.automation.core.commerce.model.BusinessRegistration;
import com.automation.core.lands.model.CertificateOfOccupancy;
import com.automation.core.lands.model.GroundRent;
import com.automation.core.lands.model.StatutoryAllocation;
import com.automation.core.wardactivity.model.ActivityReport;
import com.automation.util.enums.ReportType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class ReportUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static byte[] generateSingleCofOCertificate(CertificateOfOccupancy cofo) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Paragraph title = new Paragraph("CERTIFICATE OF OCCUPANCY", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("This is to certify that: " + cofo.getApplicantName()));
            document.add(new Paragraph("Date of Issuance: " + cofo.getApprovalDate()));
            document.add(new Paragraph("Certificate ID: " + cofo.getId()));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("This certificate is issued under the authority of the Land Bureau."));
            document.add(new Paragraph("Status: " + cofo.getStatus()));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Authorized Signature: ____________________"));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate CofO certificate", e);
        }
    }

    public static byte[] generatePdfReportFromCofO(List<CertificateOfOccupancy> cofoList, ReportType reportType) {
        return generateGenericPdfReport("Certificate of Occupancy Report", reportType.toString(), cofoList,
            (c) -> new String[]{ "S/N", "Applicant Name", "Date Submitted", "Status" },
            (c, i) -> new String[]{ String.valueOf(i), c.getApplicantName(), c.getCreatedAt().toString(), String.valueOf(c.getStatus()) });
    }

    public static byte[] generatePdfReportFromStatutory(List<StatutoryAllocation> cofoList, ReportType reportType) {
        return generateGenericPdfReport("Statutory Allocation Report", reportType.toString(), cofoList,
            (c) -> new String[]{ "S/N", "Applicant Name", "Date Submitted", "Status" },
            (c, i) -> new String[]{ String.valueOf(i), c.getApplicantName(), c.getCreatedAt().toString(), String.valueOf(c.getStatus()) });
    }

    public static byte[] generatePdfReportFromGroundRent(List<GroundRent> cofoList, ReportType reportType) {
        return generateGenericPdfReport("Ground Rent Report", reportType.toString(), cofoList,
            (c) -> new String[]{ "S/N", "BA No", "Date Submitted", "Status" },
            (c, i) -> new String[]{ String.valueOf(i), c.getBaNo(), c.getCreatedAt().toString(), String.valueOf(c.getStatus()) });
    }

    // New Generic PDF method for any entity
    public static <T> byte[] generateGenericPdfReport(String title, String subtitle, List<T> dataList,
                                                   Function<T, String[]> headerProvider,
                                                   BiFunction<T, Integer, String[]> rowProvider) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.addTitle(title);
            document.add(new Paragraph(subtitle));
            document.add(new Paragraph("Generated On: " + new java.util.Date()));
            document.add(Chunk.NEWLINE);

            String[] headers = headerProvider.apply(null); // Simplified header logic
            PdfPTable table = new PdfPTable(headers.length);
            table.setWidthPercentage(100);

            for (String header : headers) {
                table.addCell(header);
            }

            int i = 1;
            for (T item : dataList) {
                String[] row = rowProvider.apply(item, i++);
                for (String cell : row) {
                    table.addCell(cell != null ? cell : "");
                }
            }

            document.add(table);
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    // Specialized PDF for Business Registration
    public static byte[] generatePdfReportFromBusiness(List<BusinessRegistration> list, String title) {
        return generateGenericPdfReport(title, "Commerce Report", list,
            (b) -> new String[]{ "S/N", "Business Name", "Email", "Status" },
            (b, i) -> new String[]{ String.valueOf(i), b.getBusinessName(), b.getEmail(), String.valueOf(b.getStatus()) });
    }

    // Specialized PDF for Activity Reports
    public static byte[] generatePdfReportFromActivity(List<ActivityReport> list, String title) {
        return generateGenericPdfReport(title, "Ward Activity Report", list,
            (a) -> new String[]{ "S/N", "Title", "Date", "Status" },
            (a, i) -> new String[]{ String.valueOf(i), a.getTitle(), a.getDate().toString(), String.valueOf(a.getStatus()) });
    }

    // CSV Report
    public static byte[] generateCsvReportFromCofO(List<CertificateOfOccupancy> cofoList, ReportType reportType) {
        StringBuilder sb = new StringBuilder();
        sb.append("S/N,Applicant Name,Date Submitted,Status\n");

        int sn = 1;
        for (CertificateOfOccupancy c : cofoList) {
            sb.append(sn++).append(",");
            sb.append(escapeCsv(c.getApplicantName())).append(",");
            sb.append(c.getCreatedAt().toString()).append(",");
            sb.append(escapeCsv(String.valueOf(c.getStatus()))).append("\n");
        }

        return sb.toString().getBytes();
    }

    // Generic CSV generator
    public static <T> byte[] generateGenericCsvReport(List<T> dataList, String[] headers, Function<T, String[]> rowProvider) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", headers)).append("\n");

        int sn = 1;
        for (T item : dataList) {
            String[] row = rowProvider.apply(item);
            for (int i = 0; i < row.length; i++) {
                sb.append(escapeCsv(row[i]));
                if (i < row.length - 1) sb.append(",");
            }
            sb.append("\n");
        }
        return sb.toString().getBytes();
    }

    // Generic JSON generator
    public static <T> byte[] generateJsonReport(List<T> dataList) {
        try {
            return objectMapper.writeValueAsBytes(dataList);
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate JSON report", e);
        }
    }

    private static String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    @FunctionalInterface
    public interface BiFunction<T, U, R> {
        R apply(T t, U u);
    }
}
