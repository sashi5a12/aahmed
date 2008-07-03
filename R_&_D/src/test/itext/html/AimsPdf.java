package test.itext.html;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import test.itext.general.Utility;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class AimsPdf {
//	private static BaseFont arial = null;
	private static StyleSheet style=null;

	public static void addTitle(Document doc, String text)throws IOException, BadElementException, DocumentException{
		float[] widths = { 62f, 164f, 62f };
		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(100);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setSpacingAfter(10);

		Image image=Image.getInstance("app_title.gif");
		image.scalePercent(70);		
		PdfPCell cell = new PdfPCell(image);
		cell.setPadding(10);		
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		Paragraph title=new Paragraph(text,FontFactory.getFont("arial",12,Font.BOLD));
		cell=new PdfPCell(title);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);		
		table.addCell(cell);

		image=Image.getInstance("title_shot.gif");
		image.scalePercent(70);
		cell = new PdfPCell(image);
		cell.setPadding(10);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);		
		table.addCell(cell);
		
		doc.add(table);
		
		table = new PdfPTable(1);		
		table.setWidthPercentage(100);
		cell=new PdfPCell();
		cell.setBackgroundColor(Color.BLACK);
		cell.setFixedHeight(1);
		table.addCell(cell);
		
		doc.add(table);
	}
	public static void addAgreement(Document doc, String text)throws IOException,DocumentException{

		StringBuffer sb=new StringBuffer();
		sb.append("<p>Use of this Application is subject to the terms and conditions of the Customer Agreement, ");
		sb.append("your Calling Plan and Get It Now terms of use including the Get It Now License Agreement, ");
		sb.append("which can be found at <a href=\"http://www.vzwshop.com/popups/licenseagreement_popup.aspx\">");
		sb.append("www.vzwshop.com/popups/licenseagreement_popup.aspx</a>.</p>");
		sb.append("<p>&nbsp;</p>");
		
		style.loadTagStyle("p","align","center");
		writeData(doc, sb,null);
		style.loadTagStyle("p","align","justify");
	}
	public static void addProductionDescription(Document doc, String text)throws IOException,DocumentException{
		StringBuffer sb=new StringBuffer();		
		sb.append("<p>Face the biggest test in soccer and come out on top with FIFA 08. Bringing home the silverware");
		sb.append("is harder than ever before as you make critical selection decisions and battle bookings, injuries");
		sb.append("and fatigue to produce a winning team. Defeat the toughest opposition with your in-form players");
		sb.append("&ndash; overpower teams with quick movement off the ball and varying attacks. Select from ");
		sb.append("fully-licensed teams, raise your game with the help of the crowd and start your quest for glory.</p>");

		writeData(doc, sb, "Product Description");				
		
	}
	
	public static void addScreenShorts(Document doc, String text)throws BadElementException,IOException, DocumentException{
		
		Paragraph heading=new Paragraph("Screenshots",FontFactory.getFont("arial",10,Font.UNDERLINE|Font.BOLDITALIC));
		heading.setSpacingAfter(12f);
		doc.add(heading);
		
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(80);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);

		Image image=Image.getInstance("screen_shoot1.gif");
		image.scalePercent(70);		
		PdfPCell cell = new PdfPCell(image);
		cell.setPadding(1);		
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		image=Image.getInstance("screen_shoot2.gif");
		image.scalePercent(70);		
		cell = new PdfPCell(image);
		cell.setPadding(1);		
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		image=Image.getInstance("screen_shoot3.gif");
		image.scalePercent(70);		
		cell = new PdfPCell(image);
		cell.setPadding(1);		
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		image=Image.getInstance("screen_shoot4.gif");
		image.scalePercent(70);		
		cell = new PdfPCell(image);
		cell.setPadding(1);		
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		image=Image.getInstance("screen_shoot5.gif");
		image.scalePercent(70);		
		cell = new PdfPCell(image);
		cell.setPadding(1);		
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		image=Image.getInstance("screen_shoot6.gif");
		image.scalePercent(70);		
		cell = new PdfPCell(image);
		cell.setPadding(1);		
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
//		doc.add(new Paragraph("\n"));
		doc.add(table);
	}
	public static void addUsingApplication(Document doc, String text)throws IOException,DocumentException{
		StringBuffer sb=new StringBuffer();		
		sb.append("<p><strong>Game Objective</strong></p>");
		sb.append("<p style=\"text-align: justify\">Take your team to glory in the 07-08 soccer season. ");
		sb.append("Choose your favourite team and battle it out to win the league. Experience the highs and ");
		sb.append("lows of the soccer season in the all new Club Challenge mode. Or, choose your team and opponent from a ");
		sb.append("selection of over 100 teams and compete to win the match.</p>");
		sb.append("<p><strong>Starting the game</strong></p>");
		sb.append("<p style=\"text-align: justify\">Highlight &ldquo;EA SPORTS FIFA 08&rdquo; in the games menu on your phone.");
		sb.append("Press &ldquo;Select&rdquo; (or &ldquo;OK&rdquo; on the D-Pad) to start the game. You are prompted to choose ");
		sb.append("sound either on or off. After you make your selection, the EA Mobile splash screen appears followed by ");
		sb.append("EA SPORTS FIFA 08 title sequence and the Main Menu.</p>");
		sb.append("<p style=\"text-align: justify\"><strong>Main Menu</strong></p>");
		sb.append("<ul>");
		sb.append("<li>Play &ndash; Start a game.</li>");
		sb.append("<li>Options &ndash; View the available game options: sound, vibration, match length, difficulty, etc</li>");
		sb.append("<li>Help &ndash; View detailed instructions and information on how to play the game.</li>");
		sb.append("<li>About &ndash; Display copyright information and customer service information.</li>");
		sb.append("<li>More Games &ndash; Display link to more EA Mobile games.</li>");
		sb.append("<li>Exit &ndash; Exit the game.</li>");
		sb.append("</ul>");
		sb.append("<p><strong>How to Play</strong></p>");
		sb.append("<p>");
		sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"1\">");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<td valign=\"top\" width=\"198\"><p><font size=\"2\"><strong>Key</strong></font></p></td>");
		sb.append("<td valign=\"top\" width=\"188\"><p><font size=\"2\"><strong>Context</strong></font></p></td>");
		sb.append("<td valign=\"top\" width=\"214\"><p><strong><font size=\"2\">Functionality</font></strong></p></td>");
		sb.append("</tr>");
		sb.append("</thead>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td valign=\"top\" width=\"198\"><p><font size=\"2\">D-PAD</font></p></td>");
		sb.append("<td valign=\"top\" width=\"188\"><p><font size=\"2\">Menus</font></p></td>");
		sb.append("<td valign=\"top\" width=\"214\"><p><font size=\"2\">Navigates menus</font></p></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td valign=\"top\" width=\"198\"><p><font size=\"2\">D-PAD center, left soft key</font></p></td>");
		sb.append("<td valign=\"top\" width=\"188\"><p><font size=\"2\">Menus</font></p></td>");
		sb.append("<td valign=\"top\" width=\"214\"><p><font size=\"2\">Select menu item</font></p></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td valign=\"top\" width=\"198\"><p><font size=\"2\">Right soft key</font></p></td>");
		sb.append("<td valign=\"top\" width=\"188\"><p><font size=\"2\">Sub-menus</font></p></td>");
		sb.append("<td valign=\"top\" width=\"214\"><p><font size=\"2\">Back to previous menu page</font></p></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td valign=\"top\" width=\"198\"><p><font size=\"2\">D-PAD direction</font></p></td>");
		sb.append("<td valign=\"top\" width=\"188\"><p><font size=\"2\">in-game</font></p></td>");
		sb.append("<td valign=\"top\" width=\"214\"><p><font size=\"2\">Move player in the given direction</font></p></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td valign=\"top\" width=\"198\"><p><font size=\"2\">D-PAD center, 1</font></p></td>");
		sb.append("<td valign=\"top\" width=\"188\"><p><font size=\"2\">in-game</font></p></td>");
		sb.append("<td valign=\"top\" width=\"214\"><p><font size=\"2\">Pass ball when in possession of the ball</font></p>");
		sb.append("<p><font size=\"2\">Change player when not in possession of the ball</font></p></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td valign=\"top\" width=\"198\"><p><font size=\"2\">2</font></p></td>");
		sb.append("<td valign=\"top\" width=\"188\"><p><font size=\"2\">in-game</font></p></td>");
		sb.append("<td valign=\"top\" width=\"214\"><p><font size=\"2\">Shoot when in possession of the ball</font></p>");
		sb.append("<p><font size=\"2\">Slide Tackle when not in possession of the ball</font></p></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td valign=\"top\" width=\"198\"><p><font size=\"2\">3</font></p></td>");
		sb.append("<td valign=\"top\" width=\"188\"><p><font size=\"2\">in-game</font></p></td>");
		sb.append("<td valign=\"top\" width=\"214\"><p><font size=\"2\">Player sprints</font></p></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td valign=\"top\" width=\"198\"><p><font size=\"2\">Left soft key</font></p></td>");
		sb.append("<td valign=\"top\" width=\"188\"><p><font size=\"2\">in-game</font></p></td>");
		sb.append("<td valign=\"top\" width=\"214\"><p><font size=\"2\">Open pause menu</font></p></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td valign=\"top\" width=\"198\"><p><font size=\"2\">CLR Key</font></p></td>");
		sb.append("<td valign=\"top\" width=\"188\"><p><font size=\"2\">in-game</font></p></td>");
		sb.append("<td valign=\"top\" width=\"214\"><p><font size=\"2\">Open pause menu</font></p></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td valign=\"top\" width=\"198\"><p><font size=\"2\">CLR Key</font></p></td>");
		sb.append("<td valign=\"top\" width=\"188\"><p><font size=\"2\">Main menu</font></p></td>");
		sb.append("<td valign=\"top\" width=\"214\"><p><font size=\"2\">Exit application</font></p></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td valign=\"top\" width=\"198\"><p><font size=\"2\">CLR Key</font></p></td>");
		sb.append("<td valign=\"top\" width=\"188\"><p><font size=\"2\">Sub-menus</font></p></td>");
		sb.append("<td valign=\"top\" width=\"214\"><p><font size=\"2\">Back to previous screen</font></p></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</p>");
		writeData(doc, sb,"Using Application");				
		
	}
	public static void addTipsAndTricks(Document doc, String text)throws IOException,DocumentException{
		StringBuffer sb=new StringBuffer();		
		sb.append("<ul>");
	    sb.append("<li><b><span style=\"font-size: 10pt\">Know your team &ndash; </span></b><span style=\"font-size: 10pt\">Every player has strengths and weaknesses when it comes to passing, shooting and defending. You can view the player stats and make substitutions on the Team Line up screen. This can be accessed from the pre-match and pause menus</span></li>");
	    sb.append("<li><b><span style=\"font-size: 10pt\">Change your tactics</span></b><span style=\"font-size: 10pt\"> &ndash; If your team is not performing well, you can try changing their formation and tactics in the Strategy screen. This can be accessed from the pre-match and pause menus.</span></li>");
	    sb.append("<li><b><span style=\"font-size: 10pt\">Passing</span></b><span style=\"font-size: 10pt\"> &ndash; When in possession of the ball, the player you pass to is marked by an arrow. If it&rsquo;s not the player you want, try keeping the ball and waiting for another opening. Alternatively, try changing direction and see who else is available.</span></li>");
	    sb.append("<li><b><span style=\"font-size: 10pt\">Long and short passes</span></b><span style=\"font-size: 10pt\"> &ndash; Short passes are marked by a blue arrow. Tap the pass button to pass to these players. Long passes are marked with green arrows. Hold the pass button to make a long pass.</span></li>");
	    sb.append("<li><b><span style=\"font-size: 10pt\">See the entire pitch at a glance </span></b><span style=\"font-size: 10pt\">&ndash; The scanner provides a birds-eye view of the pitch and players and is a great way to see everybody&rsquo;s position at a glance. You can turn this on and off in the options menu, as well as placing it in the bottom-center and bottom-right of the screen.</span></li>");
	    sb.append("<li><b><span style=\"font-size: 10pt\">Avoid being caught offside </span></b><span style=\"font-size: 10pt\">&ndash; Be careful when making forward passes into the penalty box. If the receiving player is between the last defender and the goal, they will be caught offside. Keep an eye on the scanner.</span></li>");
	    sb.append("<li><b><span style=\"font-size: 10pt\">Use sprinting wisely</span></b><span style=\"font-size: 10pt\"> &ndash; Pressing the sprint button will give your player a burst of speed when running and holding it down will keep them sprinting. Beware, however, that this will make them become tired and reduce their ability to play well. Use the sprint function sparingly!</span></li>");
	    sb.append("<li><b><span style=\"font-size: 10pt\">Know your own strength &ndash; </span></b><span style=\"font-size: 10pt\">The strength of your shot is determined by how long you hold the shoot button down. If you put too much strength into your shot, you will overshoot the goal.</span></li>");
	    sb.append("<li><b><span style=\"font-size: 10pt\">Auto Tackling &ndash;</span></b><span style=\"font-size: 10pt\"> When defending, run your player towards the opponent with the ball to automatically tackle them. The success of the tackle will depend on the individual skills of the player you are controlling, so its best to learn the skills of your team</span></li>");
	    sb.append("<li><b><span style=\"font-size: 10pt\">Slide tackles &ndash;</span></b><span style=\"font-size: 10pt\"> Be careful when slide tackling as this may lead to a foul. Try to always tackle from the front or sides. This is more likely to succeed.</span></li>");
	    sb.append("</ul>");
		writeData(doc, sb,"Tips and Tricks");
	}
	public static void addFaq(Document doc, String text)throws IOException,DocumentException{
		StringBuffer sb=new StringBuffer();		
		sb.append("<p><strong>With FIFA08 for the mobile platform, how many teams can I choose from and how many leagues are available? </strong></p>");
		sb.append("<p style=\"text-align: justify\">There are five premier leagues available, including MLS, the English Premiership, Serie A, La Liga and the Mexican League as well as a collection of international teams from around the world. In total there are over 100 authentic licensed teams that you can choose to play with.</p>");
		sb.append("<p><strong>With so many teams available, how does the game work exactly? </strong></p>");
		sb.append("<p style=\"text-align: justify\">It&rsquo;s not as tricky as you may think! If you are unsure of how to play FIFA08 on your mobile phone, we have included a tutorial that will help you to get started quickly and play your first match.</p>");
		sb.append("<p style=\"text-align: justify\">You also have the option to play through a half season in one of the leagues with a team of your choice. As well as &lsquo;season mode&rsquo;, you can also take your chosen team through the &lsquo;club challenge&rsquo; mode, culminating in a match to win the title. Or if you simply prefer a quick match, you can pick any two teams from any of the leagues for a one-off friendly.</p>");
		sb.append("<p><strong>Soccer is all about the passion of winning and losing, and taking your team to glory &ndash; how is this replicated in FIFA08 on the mobile phone? </strong></p>");
		sb.append("<p style=\"text-align: justify\">Our aim for FIFA08 is for users to experience the passion and atmosphere that comes with watching or being a part of top-flight soccer. For users to experience that passion on their mobile phones we ensured that each game is preceded by a match intro with a famous soccer pundit (Andy Gray, for example) who introduces team lineups, etc. We have also included text commentary throughout matches, so all the crunching tackles, blinding goals and offside decisions are highlighted and brought to life. The match commentator will provide a half time comment in relation to your performance on the pitch.</p>");
		sb.append("<p><strong>What new features can we expect with FIFA08 for the mobile phone compared with FIFA07? </strong></p>");
		sb.append("<p style=\"text-align: justify\">FIFA08 offers great new features. In addition to &lsquo;game commentary&rsquo; and the &lsquo;club challenge&rsquo; mode, we have spent a lot of time ensuring that each and every player has individual statistics. You will find that players such as Wayne Rooney have a great shooting attribute, and Ronaldinho has great speed and passing ability. We have also included what we call &lsquo;in-form&rsquo; players. Randomly before each match, up to three players will be marked as in-form, which means these players will have heightened abilities for that match only. We have added a visual effect to these players, too, to highlight their skills.</p>");
		sb.append("<p style=\"text-align: justify\">We&rsquo;ve added a sprint key for an extra burst of speed &ndash; see the User&rsquo;s Guide above for details.</p>");
		sb.append("<p style=\"text-align: justify\">On capable handsets we have also added &lsquo;landscape mode&rsquo;, meaning that users can turn their phone on its side and play with a widescreen. We have also added more pitch textures and improved the stadium and crowd as well as allowing the user to customize the position of the radar.</p>");
		sb.append("<p><strong>Do you get the FIFA08 experience across all handsets; how does it differ? </strong></p>");
		sb.append("<p style=\"text-align: justify\">We work hard to provide similar experiences of the game no matter what handset it is played on, but there are natural limits within some handsets that we try and work around without losing any of the &lsquo;FIFA 08 experience&rsquo;. On lower-end handsets you can choose from international teams in a tournament or friendly match only and don&rsquo;t have access to the &lsquo;club challenge&rsquo; or &lsquo;season&rsquo; mode. You&rsquo;ll also find the &lsquo;in-form&rsquo; system primarily on higher end handsets. On some of the mid-end handsets we have had to reduce or remove small details like varying pitch textures, sound effects and music &ndash; but overall, players won&rsquo;t be disappointed with their FIFA 08 experience no matter which handset they own!</p>");
		sb.append("<p><strong>What is enhanced season mode? </strong></p>");
		sb.append("<p style=\"text-align: justify\">We have tried to make our season an emotive and fluid experience. If a player is injured in a match it will carry over into the next match and maybe even into future matches. If a player accumulates yellow cards or gets a straight red, then he will be suspended. Fatigue will also carry over into the next match, so your entire squad becomes important to you. The commentator will recognize your performance throughout the season and know when those crunch matches or local derby matches come up.</p>");
		sb.append("<p><strong>Are you able to use any of the data from the console game and transfer it to the mobile game? </strong></p>");
		sb.append("<p style=\"text-align: justify\">Yes, that is one of the great things about FIFA08 mobile. We have access to the same database that the FIFA08 console game uses. This gives us an incredible amount of information about player statistics and abilities as well as team information. We have managed to transfer player statistics to FIFA08 mobile so each player is an individual on the pitch.</p>");
		sb.append("<p><strong>You can now play FIFA08 in landscape? </strong></p>");
		sb.append("<p style=\"text-align: justify\">Yes, with an increasing number of handsets out there that cater for a landscape mode, it felt natural to integrate this capability into the game. It gives owners of compatible handsets additional choice on how they wish to experience the game &ndash; we&rsquo;ve heard that people find the wide lateral view of the pitch useful for passing and following movements. <br />");
		sb.append("&nbsp;</p>");
		writeData(doc, sb,"FAQs");		
	}
	public static void addTroubleshooting(Document doc, String text){
		
	}
	public static void addDisclaimer(Document doc, String text){
		
	}
	public static void temp(Document doc, String text)throws IOException,DocumentException{
		StringBuffer sb=new StringBuffer();		
		sb.append("<p>Top</p>");
		sb.append("<table width=\"200\" cellspacing=\"1\" cellpadding=\"1\" border=\"1\" align=\"left\">");
		sb.append("<tbody>");
		sb.append("<tr><td>Cell1</td><td>Cell2</td></tr>");
		sb.append("<tr><td>Cell3</td><td>Cell3</td></tr>");
		sb.append("<tr><td>Cell4</td><td>Cell6</td></tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p>Bottom</p>");
		writeData(doc, sb,"Temp");
	}
	private static void writeData(Document doc, StringBuffer sb, String headingText)throws IOException, DocumentException {
		String data = Utility.replace(sb.toString(), "<p>&nbsp;</p>", "",Utility.REPLACE_ALL);
		data = Utility.replace(data, "</p>", "</p><br/>", Utility.REPLACE_ALL);
		data = Utility.replace(data, "</ul>", "</ul><br/>", Utility.REPLACE_ALL);
		data = Utility.replace(data, "</p><br/></td>", "</p></td>", Utility.REPLACE_ALL);
		
		System.out.println(data);
		Reader reader = new StringReader("<root>" + data + "</root>");
		
		if (headingText !=null && headingText.length()>0){
			Paragraph heading=new Paragraph(headingText,FontFactory.getFont("arial",10,Font.UNDERLINE|Font.BOLDITALIC));
			heading.setSpacingAfter(12f);
			doc.add(heading);
		}
		
		ArrayList objects = HTMLWorker.parseToList(reader, style, null);
		for (int k = 0; k < objects.size(); ++k) {
			Element ele = (Element) objects.get(k);
			doc.add(ele);
		}

		reader.close();
	}	
	public static void main(String[] args)throws Exception{
		Document doc=new Document(PageSize.LETTER);		
		PdfWriter writer=PdfWriter.getInstance(doc, new FileOutputStream("aims.pdf"));
		doc.open();
		FontFactory.register("C:\\WINDOWS\\FONTS\\ARIAL.TTF","arial");		
//		AimsPdf.arial = BaseFont.createFont("C:\\WINDOWS\\FONTS\\ARIAL.TTF",BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
		
		AimsPdf.style = new StyleSheet();
		
		style.loadTagStyle("p","size","10px");
		style.loadTagStyle("p","align","justify");
		style.loadTagStyle("p","face","arial");
		style.loadTagStyle("p", "leading", "14f");

		style.loadTagStyle("a","color","blue");
	    style.loadTagStyle("a", "u", "");

	    style.loadTagStyle("ul", "face", "arial"); 
	    style.loadTagStyle("ul", "size", "10px"); 
	    style.loadTagStyle("ul", "leading", "14f");
	    
	    style.loadTagStyle("li","align","justify");
	    
		style.loadTagStyle("td", "cellpadding","2");
		
//		style.loadTagStyle("p", "spacingAfter", "35px"); 
//		style.loadTagStyle("p", "spacingBefore", "35px"); 
//		style.loadTagStyle("p", "style", "margin-top:20px;margin-bottom:20px"); 
 
		AimsPdf.addTitle(doc, "EA SPORTS FIFA 08™");
		AimsPdf.addAgreement(doc, "");
		AimsPdf.addProductionDescription(doc, "");
		AimsPdf.addScreenShorts(doc,"");
		AimsPdf.addUsingApplication(doc, "");
		AimsPdf.addTipsAndTricks(doc,"");
		AimsPdf.addFaq(doc, "");
		AimsPdf.temp(doc, "");
		

		/*StringBuffer sb=new StringBuffer();					
		sb.append("<p><h1><span style=\"font-size: 100px\"><span style=\"\"><span style=\"font-family: Courier New\"><ins>Hello World</ins></span></span></span></h1></p>");
		writeData(doc, sb);*/
		doc.close();
	}
}
