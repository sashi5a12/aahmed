package test.itext.xml2pdf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.lowagie.text.Document;
import com.lowagie.text.ElementTags;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.Markup;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.xml.TagMap;
import com.lowagie.text.xml.XmlParser;
import com.lowagie.text.xml.XmlPeer;

public class Main {
	
	static class MyTagMap extends TagMap {

		private static final long serialVersionUID = 1024517625414654121L;

		/**
		 * Constructs a TagMap based on an XML file
		 * and/or on XmlPeer objects that are added.
		 * @throws IOException
		 */
		public  MyTagMap() throws IOException {
			super(new FileInputStream("mytagmap.xml"));
			XmlPeer peer = new XmlPeer(ElementTags.CHUNK, "SPEAKER");
//			peer.addValue(Markup.CSS_KEY_FONTSIZE, "10");
//			peer.addValue(Markup.CSS_KEY_FONTWEIGHT, Markup.CSS_VALUE_BOLD);
//			peer.addValue(ElementTags.GENERICTAG, "");
			put(peer.getAlias(), peer);
		}
	}
	public static void main(String args[])throws Exception{
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("sample.pdf"));
//		writer.setPageEvent(new SimpleLetter());
		StringBuffer sb=new StringBuffer();
		sb.append("<p><u><em><strong>Product Description</strong></em></u></p>");
		sb.append("<p style=\"text-align: justify\">Face the biggest test in soccer and come out on top with FIFA 08. Bringing home the silverware is harder than ever before as you make critical selection decisions and battle bookings, injuries and fatigue to produce a winning team. Defeat the toughest opposition with your in-form players &ndash; overpower teams with quick movement off the ball and varying attacks. Select from fully-licensed teams, raise your game with the help of the crowd and start your quest for glory.</p>");
		sb.append("<p style=\"text-align: justify\"><strong>Starting the game</strong></p>");
		sb.append("<p style=\"text-align: justify\">Highlight &ldquo;EA SPORTS FIFA 08&rdquo; in the games menu on your phone. Press &ldquo;Select&rdquo; (or &ldquo;OK&rdquo; on the D-Pad) to start the game. You are prompted to choose sound either on or off. After you make your selection, the EA Mobile splash screen appears followed by EA SPORTS FIFA 08 title sequence and the Main Menu.<br />");
		sb.append("&nbsp;</p>");
		sb.append("<p style=\"text-align: justify\"><strong>Main Menu</strong></p>");
		sb.append("<ul>");
		sb.append("<li>Play &ndash; Start a game.</li>");
		sb.append("<li>Options &ndash; View the available game options: sound, vibration, match length, difficulty, etc</li>");
		sb.append("<li>Help &ndash; View detailed instructions and information on how to play the game.</li>");
		sb.append("<li>About &ndash; Display copyright information and customer service information.</li>");
		sb.append("<li>More Games &ndash; Display link to more EA Mobile games.</li>");
		sb.append("<li>Exit &ndash; Exit the game.</li>");
		sb.append("</ul>");
		Reader reader=new StringReader(sb.toString());
		XmlParser.parse(document, reader,new Main.MyTagMap());
	}
}
