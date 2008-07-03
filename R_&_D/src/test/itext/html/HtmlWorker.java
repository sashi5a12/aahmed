package test.itext.html;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfWriter;

public class HtmlWorker {
	public static void main(String args[]) {
		Document document = new Document();
		StyleSheet st = new StyleSheet();
		st.loadTagStyle("body", "leading", "16,0");
		
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
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream("html2.pdf"));
			Reader reader=new StringReader(sb.toString());
			document.open();
			ArrayList p = HTMLWorker.parseToList(reader, st);
			for (int k = 0; k < p.size(); ++k)
				document.add((Element) p.get(k));
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.close();
	}
}
