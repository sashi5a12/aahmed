
package test.itext.general;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

public class CustomPageSize {
    /**
     * Creates a PDF document with a certain pagesize
     */
    public static void main(String[] args) {
        
        System.out.println("Custom PageSize and backgroundcolor");
        
        // step 1: creation of a document-object
        Rectangle pageSize = new Rectangle(216, 720);
        pageSize.setBackgroundColor(new java.awt.Color(0xFF, 0xFF, 0xDE));
        Document document = new Document(pageSize);
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            
        	File file=new File("CustomPageSize.pdf");
        	if(!file.exists()){
        		file.createNewFile();
        	}
            PdfWriter.getInstance(document, new FileOutputStream(file));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add some paragraphs to the document
            document.add(new Paragraph("The size of this page is 216x720 points."));
            document.add(new Paragraph("216pt / 72 points per inch = 3 inch"));
            document.add(new Paragraph("720pt / 72 points per inch = 10 inch"));
            document.add(new Paragraph("The size of this page is 3x10 inch."));
            document.add(new Paragraph("3 inch x 2.54 = 7.62 cm"));
            document.add(new Paragraph("10 inch x 2.54 = 25.4 cm"));
            document.add(new Paragraph("The size of this page is 7.62x25.4 cm."));
            document.add(new Paragraph("The backgroundcolor of the Rectangle used for this PageSize, is #FFFFDE."));
            document.add(new Paragraph("That's why the background of this document is yellowish..."));
            
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        
        // step 5: we close the document
        document.close();
    }
}
