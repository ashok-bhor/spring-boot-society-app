package com.abpoint.service.reciept;

import com.abpoint.SpringBootSocietyApplication;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.layout.property.TextAlignment;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GenerateReceiptService {

	private static final Logger logger = LoggerFactory.getLogger(GenerateReceiptService.class);

	void getDetailsForReciept(Long id){
		
	}
	
	
	public byte[] pdfGenerator() throws IOException {
    	
		
		//String dest = "D:\\tempr\\filled_Receipt_format.pdf"; // Path for the new filled PDF
		 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
			
        	PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
        	/*
			 * PdfWriter writer = new PdfWriter(dest); PdfDocument pdf = new
			 * PdfDocument(writer); Document document = new Document(pdf, PageSize.A4);
			 */
            // Adding title
            Paragraph title = new Paragraph("Amrutsiddhi Society")
                    .setFont(PdfFontFactory.createFont("Helvetica-Bold"))
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Bill Information Table
            float[] billInfoColumnWidths = {150, 150, 150};
            Table billInfoTable = new Table(billInfoColumnWidths);
            
            // Adding Bill Information Rows
            billInfoTable.addCell(new Cell().add(new Paragraph("Bill Number").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
            billInfoTable.addCell(new Cell().add(new Paragraph("12345")));
            billInfoTable.addCell(new Cell().add(new Paragraph("QR ID: " + generateUniqueId()).setFont(PdfFontFactory.createFont("Helvetica-Bold")).setTextAlignment(TextAlignment.CENTER)));
            
            billInfoTable.addCell(new Cell().add(new Paragraph("Bill Date").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
            billInfoTable.addCell(new Cell().add(new Paragraph("2023-08-05")));
            billInfoTable.addCell(new Cell(4, 1).add(generateQRCodeImage("QR ID: " + generateUniqueId() + ", Bill Number: 12345, Flat number: A-101, Customer Name: John Doe, Paid for: Maintenance Charges, Bill Date: 2023-08-05")).setTextAlignment(TextAlignment.CENTER));

            billInfoTable.addCell(new Cell().add(new Paragraph("Flat number").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
            billInfoTable.addCell(new Cell().add(new Paragraph("A-101")));
            
            billInfoTable.addCell(new Cell().add(new Paragraph("Customer Name").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
            billInfoTable.addCell(new Cell().add(new Paragraph("John Doe")));
            
            billInfoTable.addCell(new Cell().add(new Paragraph("Paid for").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
            billInfoTable.addCell(new Cell().add(new Paragraph("Maintenance Charges")));
            
            document.add(billInfoTable);

            // Add a gap between tables
            document.add(new Paragraph("\n"));

            // Adding Table Header
            float[] columnWidths = {40, 300, 100};
            Table table = new Table(columnWidths);

            table.addCell(new Cell().add(new Paragraph("ID").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
            table.addCell(new Cell().add(new Paragraph("Description").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
            table.addCell(new Cell().add(new Paragraph("Amount").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));

            // Adding Table Content
            double total = 0;
            for (int i = 1; i <= 12; i++) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(i))));
                table.addCell(new Cell().add(new Paragraph(getDescription(i))));
                double amount = getAmount(i);
                total += amount;
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", amount))));
            }

            // Adding Total Row
            table.addCell(new Cell(1, 2).add(new Paragraph("Total").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
            table.addCell(new Cell().add(new Paragraph(String.format("%.2f", total))));

            document.add(table);

            // Adding Payment Details
            Paragraph paymentDetailsHeader = new Paragraph("Payment Details")
                    .setFont(PdfFontFactory.createFont("Helvetica-Bold"))
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginTop(20);
            document.add(paymentDetailsHeader);

            float[] paymentColumnWidths = {200, 200};
            Table paymentTable = new Table(paymentColumnWidths);

            paymentTable.addCell(new Cell().add(new Paragraph("Amount in words").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
            paymentTable.addCell(new Cell().add(new Paragraph("Five Thousand Only")));

            paymentTable.addCell(new Cell().add(new Paragraph("Payment ref number").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
            paymentTable.addCell(new Cell().add(new Paragraph("PAY-12345")));

            paymentTable.addCell(new Cell().add(new Paragraph("Paid by").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
            paymentTable.addCell(new Cell().add(new Paragraph("John Doe")));

            paymentTable.addCell(new Cell().add(new Paragraph("Verified By").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
            paymentTable.addCell(new Cell().add(new Paragraph("Jane Smith")));

            document.add(paymentTable);

            document.close();

            logger.info("PDF created successfully.");
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
     // Return the PDF content as a byte array
        return byteArrayOutputStream.toByteArray();
	}
    
    
    private static String getDescription(int id) {
        switch (id) {
            case 1: return "Maintenance";
            case 2: return "None occupancy Charges";
            case 3: return "Repairs Fund";
            case 4: return "Sinking Fund";
            case 5: return "Parking";
            case 6: return "Donation";
            case 7: return "Transfer fee";
            case 8: return "Previous Dues";
            case 9: return "Late fee";
            case 10: return "Entrance fees";
            case 11: return "Other";
            default: return "";
        }
    }

    private static double getAmount(int id) {
        switch (id) {
            case 1: return 1500.00;
            case 2: return 500.00;
            case 3: return 200.00;
            case 4: return 300.00;
            case 5: return 100.00;
            case 6: return 50.00;
            case 7: return 250.00;
            case 8: return 800.00;
            case 9: return 100.00;
            case 10: return 50.00;
            case 11: return 150.00;
            case 12: return 0.00;
            default: return 0.00;
        }
    }

    private static Image generateQRCodeImage(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int width = 80;
        int height = 80;
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        ImageData imageData = ImageDataFactory.create(pngData);
        return new Image(imageData);
    }

    private static String generateUniqueId() {
        Random random = new Random();
        int uniqueId = 10000000 + random.nextInt(90000000);
        return String.valueOf(uniqueId);
    }
    
	/*
	 * void fromPdfCaligraph(){ final String[] sources = {"english.xml",
	 * "arabic.xml", "hindi.xml", "tamil.xml"}; final PdfWriter writer = new
	 * PdfWriter(DEST); final PdfDocument pdfDocument = new PdfDocument(writer);
	 * final Document document = new Document(pdfDocument); final FontSet set = new
	 * FontSet(); set.addFont("fonts/NotoNaskhArabic-Regular.ttf");
	 * set.addFont("fonts/NotoSansTamil-Regular.ttf");
	 * set.addFont("fonts/FreeSans.ttf"); document.setFontProvider(new
	 * FontProvider(set)); document.setProperty(Property.FONT, new
	 * String[]{"MyFontFamilyName"}); for (final String source : sources) { final
	 * File xmlFile = new File(source); final DocumentBuilderFactory factory =
	 * DocumentBuilderFactory.newInstance(); final DocumentBuilder builder =
	 * factory.newDocumentBuilder(); final org.w3c.dom.Document doc =
	 * builder.parse(xmlFile); final Node element =
	 * doc.getElementsByTagName("text").item(0); final Paragraph paragraph = new
	 * Paragraph(); final Node textDirectionElement =
	 * element.getAttributes().getNamedItem("direction"); boolean rtl =
	 * textDirectionElement != null && textDirectionElement.getTextContent()
	 * .equalsIgnoreCase("rtl"); if (rtl) {
	 * paragraph.setTextAlignment(TextAlignment.RIGHT); }
	 * paragraph.add(element.getTextContent()); document.add(paragraph); }
	 * document.close(); pdfDocument.close(); writer.close(); }
	 */
}
