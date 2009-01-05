package test.itext.general;

import java.io.File;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class HelloWorld {
	public static void main(String args[])throws Exception{
		
		//Step 1: Create a instance of Document
		Document doc=new Document();
		
		//Step 2: Create a Writer that listens to this document and writes 
		//the document to the OutputStream of your choice:
		File file=new File("HelloWorld.pdf");
		if(!file.exists()){
			file.createNewFile();
		}
		PdfWriter pdf=PdfWriter.getInstance(doc, new FileOutputStream(file));
		
		//Step 3: Open the Document
		doc.open();
		
		//Step 4: Add Content to the document
		doc.add(new Paragraph("Hello World"));
		
		//Step 5: Closes the document
		doc.close();
	}
}
