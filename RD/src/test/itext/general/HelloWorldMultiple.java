package test.itext.general;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Anchor;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;

/**
 * Generates simple 'Hello World' PDF, RTF and HTML files.
 * 
 * @author blowagie
 */

public class HelloWorldMultiple {

	/**
	 * Generates simple PDF, RTF and HTML files using only one Document object.
	 * 
	 * @param args no arguments needed here
	 */
	public static void main(String[] args) {

		System.out.println("Hello World in PDF, RTF and HTML");

		// step 1: creation of a document-object
		Document document = new Document();
		try {
			// step 2:
			// we create 3 different writers that listen to the document
			File file1=new File("HelloWorldPdf.pdf");
			File file2=new File("HelloWorldRtf.rtf");
			File file3=new File("HelloWorldHtml.html");
			
			if(!file1.exists()){
				file1.canWrite();
			}
			if(!file2.exists()){
				file2.canWrite();
			}
			if(!file3.exists()){
				file3.canWrite();
			}
			
			PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(file1));
			RtfWriter2 rtf = RtfWriter2.getInstance(document,new FileOutputStream(file2));
			HtmlWriter.getInstance(document, new FileOutputStream(file3));

			// step 3: we open the document
			document.open();
			// step 4: we add a paragraph to the document
			document.add(new Paragraph("Hello World"));
			
			// we make references
			Anchor pdfRef = new Anchor("see Hello World in PDF.");
			pdfRef.setReference("./HelloWorldPdf.pdf");
			
			Anchor rtfRef = new Anchor("see Hello World in RTF.");
			rtfRef.setReference("./HelloWorldRtf.rtf");
			
			// we add the references, but only to the HTML page:
			
			pdf.pause();
			rtf.pause();
			document.add(pdfRef);
			document.add(Chunk.NEWLINE);
			document.add(rtfRef);
			pdf.resume();
			rtf.resume();
			
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// step 5: we close the document
		document.close();
	}
}