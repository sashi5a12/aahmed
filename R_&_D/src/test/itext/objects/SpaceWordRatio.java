
package test.itext.objects;

import java.io.FileOutputStream;

import test.itext.general.Utility;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Space Word Ratio.
 */
public class SpaceWordRatio {
    /**
     * Space Word Ratio.
     * @param args no arguments needed
     */
    public static void main(String[] args) {
    	System.out.println("Space Word Ratio");
    	// step 1
        Document document = new Document(PageSize.A4, 50, 350, 50, 50);
        try {
        	// step 2
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(Utility.createFile("spacewordratio.pdf")));
            // step 3
            document.open();
            // step 4
            String text = "Flanders International Filmfestival Ghent - Internationaal Filmfestival van Vlaanderen Gent";
            Paragraph p = new Paragraph(text);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(p);
            document.newPage();
            writer.setSpaceCharRatio(PdfWriter.NO_SPACE_CHAR_RATIO);
            document.add(p);
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        // step 5
        document.close();
    }
}