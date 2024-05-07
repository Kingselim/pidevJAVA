package com.esprit.controllers;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

public class pdfgenerator {

    public static final Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
    public static final Font NORMAL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 12);

    public static void addTitle(Document document, String text) throws DocumentException {
        Paragraph title = new Paragraph(text, TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER); // Aligner le titre au centre
        document.add(title);
    }

    public static void addNormalText(Document document, String text) throws DocumentException {
        Paragraph normalText = new Paragraph(text, NORMAL_FONT);
        document.add(normalText);
    }

    public static void addAgencyMessage(Document document, String message) throws DocumentException {
        Paragraph agencyMessage = new Paragraph(message, NORMAL_FONT);
        agencyMessage.setAlignment(Element.ALIGN_CENTER); // Aligner le message au centre
        document.add(agencyMessage);
    }

    public static void generatePdf(String name,String last_name, int idseminar_id, int phone) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Reçu de participation.pdf"));
            document.open();

            // Ajouter l'image au document
            Image image = Image.getInstance("C://Users//HP//Desktop//workshopjdbc//src//main//resources//images//ivest.png");
            image.scaleToFit(150, 150); // Redimensionner l'image à 100x100 pixels
            document.add(image);

            // Ajouter le titre "GIVEST" en haut du PDF
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
            Paragraph title = new Paragraph("GIVEST", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Ajouter le titre "Reçu de participation"
            Font subTitleFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
            Paragraph subTitle = new Paragraph("Reçu de participation", subTitleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);

            addEmptyLine(document);
            addEmptyLine(document);

            // Ajouter le message de l'association en bas des détails
            addAgencyMessage(document, "Voici les détails de votre séminaire. Veuillez les conserver");
            addEmptyLine(document);
            addEmptyLine(document);
            // Ajouter les détails du séminaire

            addNormalText(document, "Bonjour ! Mmme/Mr ");
            addBoldRedText(document, last_name);
            addBoldRedText(document, name);
            addNormalText(document, "Vous etes inscrit au seminaire : ");
            addBoldRedText(document, String.valueOf(idseminar_id));
            addNormalText(document, "avec le numéro du téléphone ");
            addBoldRedText(document, String.valueOf(phone));
            addEmptyLine(document);
            addEmptyLine(document);

            // Ajouter le message de l'association en bas des détails
            addAgencyMessage(document, "Merci d'avoir choisi Givest ! ");
            addAgencyMessage(document, "Merci encore pour votre confiance et profitez-en pleinement !");

            addEmptyLine(document);
            addEmptyLine(document);

            // Ajouter les coordonnées de l'association
            addNormalText(document, "Adresse: Ariana 2083");
            addNormalText(document, "Tel: +216 71 969 969");
            addNormalText(document, "E-mail: Givest@esprit.tn");

            addEmptyLine(document);
            addEmptyLine(document);

            // Ajouter la date d'impression
            addDate(document);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addBoldRedText(Document document, String text) throws DocumentException {
        Font boldRedFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED);
        Chunk chunk = new Chunk(text, boldRedFont);
        Paragraph paragraph = new Paragraph(chunk);
        document.add(paragraph);
    }
    // Méthode pour ajouter la date d'impression
    private static void addDate(Document document) throws DocumentException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateStr = sdf.format(new Date());
        Paragraph dateParagraph = new Paragraph("Date d'impression : " + dateStr);
        dateParagraph.setAlignment(Element.ALIGN_BASELINE);
        document.add(dateParagraph);
    }
    public static void addEmptyLine(Document document) throws DocumentException {
        document.add(new Paragraph(" "));
    }
}
