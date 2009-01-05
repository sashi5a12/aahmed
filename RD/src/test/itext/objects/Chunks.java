
package test.itext.objects;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import test.itext.general.Utility;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Demonstrates some Chunk functionality.
 * 
 * @author blowagie
 */

public class Chunks {

	/**
	 * Demonstrates some Chunk functionality.
	 * 
	 * @param args no arguments needed here
	 */
	public static void main(String[] args) {

		System.out.println("the Chunk object");

		// step 1: creation of a document-object
		Document document = new Document();
		try {
			// step 2:
			// we create a writer that listens to the document
			PdfWriter.getInstance(document,new FileOutputStream(Utility.createFile("Chunks.pdf")));

			// step 3: we open the document
			document.open();
			// step 4:
			Chunk fox = new Chunk("quick brown fox");
			float superscript = 8.0f;
			fox.setTextRise(superscript);
			fox.setBackground(new Color(0xFF, 0xDE, 0xAD));
			Chunk jumps = new Chunk(" jumps over ");
			Chunk dog = new Chunk("the lazy dog");
			float subscript = -8.0f;
			dog.setTextRise(subscript);
//			dog.setUnderline(new Color(0xFF, 0x00, 0x00), 3.0f, 0.0f, -5.0f + subscript, 0.0f, PdfContentByte.LINE_CAP_ROUND);
			dog.setUnderline(1.0f, -5.0f + subscript);
			document.add(fox);
			document.add(jumps);
			document.add(dog);
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// step 5: we close the document
		document.close();
	}
}