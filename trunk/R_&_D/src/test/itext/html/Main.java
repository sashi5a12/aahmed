package test.itext.html;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;

import javax.jms.BytesMessage;

import org.w3c.tidy.Tidy;

import com.lowagie.text.Document;
import com.lowagie.text.html.HtmlParser;
import com.lowagie.text.pdf.PdfWriter;

public class Main {
	public static void main(String args[])throws Exception {
		Document document = new Document();
		StringBuffer sb=new StringBuffer();
		sb.append("<root><p><u><em><strong>Product Description</strong></em></u></p>");
		sb.append("<p style=\"text-align: justify\">Face the biggest test in soccer and come out on top with FIFA 08. Bringing home the silverware is harder than ever before as you make critical selection decisions and battle bookings, injuries and fatigue to produce a winning team. Defeat the toughest opposition with your in-form players overpower teams with quick movement off the ball and varying attacks. Select from fully-licensed teams, raise your game with the help of the crowd and start your quest for glory.</p>");
		sb.append("<p style=\"text-align: justify\"><strong>Starting the game</strong></p>");
		sb.append("<p style=\"text-align: justify\">Highlight EA SPORTS FIFA 08 in the games menu on your phone. Press Select (or OK on the D-Pad) to start the game. You are prompted to choose sound either on or off. After you make your selection, the EA Mobile splash screen appears followed by EA SPORTS FIFA 08 title sequence and the Main Menu.<br />");
		sb.append("</p>");
		sb.append("<p style=\"text-align: justify\"><strong>Main Menu</strong></p>");
		sb.append("<ul>");
		sb.append("<li>Play Start a game.</li>");
		sb.append("<li>Options View the available game options: sound, vibration, match length, difficulty, etc</li>");
		sb.append("<li>Help View detailed instructions and information on how to play the game.</li>");
		sb.append("<li>About Display copyright information and customer service information.</li>");
		sb.append("<li>More Games Display link to more EA Mobile games.</li>");
		sb.append("<li>Exit Exit the game.</li>");
		sb.append("</ul></root>");		
		
		/*Tidy tidy=new Tidy();
		tidy.setXmlOut(true);
		tidy.setXmlTags(true);		
		byte[] buff=sb.toString().getBytes("utf-8");
		ByteArrayOutputStream os=new ByteArrayOutputStream();		
		tidy.parse(new ByteArrayInputStream(buff), os);
		String temp=os.toString("utf-8");*/
		
		try {
			Reader reader=new StringReader(sb.toString());			
			PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream("html1.pdf"));
			document.open();
			writer.setPageEmpty(false);
			HtmlParser.parse(document, reader);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (document.isOpen())
			document.close();
	}
}
