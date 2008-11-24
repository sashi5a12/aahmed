
package test.itext.images;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.awt.image.ByteInterleavedRaster;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

/**
 * General Images example.
 */
public class Images {
    
    /**
     * General Images example
     * @param args	no arguments needed
     */
    public static void main(String[] args) {
        
        System.out.println("Images");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document, new FileOutputStream("Images.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4:
            document.add(new Paragraph("A picture of my dog: otsoe.jpg"));                        
            Image jpg = Image.getInstance("otsoe.jpg");
            
            document.add(jpg);
            document.add(new Paragraph("getacro.gif"));
            Image gif= Image.getInstance("getacro.gif");
            document.add(gif);
            document.add(new Paragraph("pngnow.png"));
            Image png = Image.getInstance("pngnow.png");
            document.add(png);
            document.add(new Paragraph("iText.bmp"));
            Image bmp = Image.getInstance("iText.bmp");
            document.add(bmp);
            document.add(new Paragraph("iText.wmf"));
            Image wmf = Image.getInstance("iText.wmf");
            document.add(wmf);
            document.add(new Paragraph("iText.tif"));
            Image tiff = Image.getInstance("iText.tif");
            document.add(tiff);
            
            InputStream ins =  Images.class.getResourceAsStream("lowagie_3d.jpg");
            byte[] buff=new byte[ins.available()];
            ins.read(buff);
            
            Image jpgStream=Image.getInstance(buff);            
            document.add(jpgStream);
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