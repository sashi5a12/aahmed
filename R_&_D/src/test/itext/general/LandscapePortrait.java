package test.itext.general;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class LandscapePortrait {
    /**
     * Creates a PDF document with pages in portrait/landscape.
     */
    public static void main(String[] args) {
        
        System.out.println("Documents in Landscape and Portrait format");
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4.rotate());
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            File file=new File("LandscapePortrait.pdf");
            if(!file.exists()){
            	file.createNewFile();
            }
            PdfWriter.getInstance(document, new FileOutputStream(file));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add some content
            document.add(new Paragraph("To create a document in landscape format, just make the height smaller than the width. For instance by rotating the PageSize Rectangle: PageSize.A4.rotate()"));
            document.setPageSize(PageSize.A4);
            document.newPage();
            document.add(new Paragraph("This is portrait again"));
            
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
