package com.netpace.vic.util;

public class VICConstants {

	//public static final String VERIZON_MAIL_TO = "hmehmood@netpace.com,aahmed@netpace.com,aadil@netpace.com,vdc.netpace@gmail.com";
	public static final String VERIZON_MAIL_TO = "innovation@verizonwireless.com";
	public static final String VERIZON_MAIL_CC = "";
	public static final String VERIZON_MAIL_BCC = "";
	public static final String MAIL_FROM = "noreply@vdcmail.verizon.com";
	public static final String MAIL_SERVER = "vdcmail.verizon.com";
	public static final String VERIZON_EMAIL_SUBJECT = "Application to Verizon Innovation Program";
	public static final String DEV_EMAIL_SUBJECT = "Application to Verizon Innovation Program";
	
 	public static final String DEV_EMAIL_CONTENT_HTML = "<b>Thanks for your interest in the Verizon Innovation Program.</b><br/><br/> "+
 			"We know how much effort goes into submitting an application - and more importantly, how "+
 			"important your project is to you! A team of engineers and analysts within the Verizon Innovation"+
 			"Program will use the information provided to gain a directional understanding of your project"+
 			"and to extend select invitations for the next step in the evaluation process.<br/> "+
 			"<br/>"+
 			"If your company, project, and market opportunity align with the goals and resources available "+
 			"within the Verizon Innovation Program, we will reach out to you and set up some time to learn "+
 			"more and also give you a chance to learn more about our Program. If we don’t feel like there is "+
 			"a strong match at this time, we will work to let you know that as well.<br/>"+
 			"<br/>"+
 			"That being said, we receive a high volume of submissions and it takes time to go through all of "+
 			"them. We appreciate your understanding and patience.<br/>"+
 			"<br/>"+
 			"On behalf of the entire Verizon Innovation Program team, thanks for your interest, and best of "+
 			"luck with your innovation.<br/><br/>"+
 			"-The Verizon Innovation Program Team<br/>";

 	public static final String DEV_EMAIL_CONTENT_TEXT = "Thanks for your interest in the Verizon Innovation Program.\n "+
 			"We know how much effort goes into submitting an application - and more importantly, how \n"+
 			"important your project is to you! A team of engineers and analysts within the Verizon Innovation\n"+
 			"Program will use the information provided to gain a directional understanding of your project\n"+
 			"and to extend select invitations for the next step in the evaluation process.\n "+
 			"\r\n"+
 			"If your company, project, and market opportunity align with the goals and resources available\n "+
 			"within the Verizon Innovation Program, we will reach out to you and set up some time to learn\n "+
 			"more and also give you a chance to learn more about our Program. If we don’t feel like there is\n "+
 			"a strong match at this time, we will work to let you know that as well.\n"+
 			"\n"+
 			"That being said, we receive a high volume of submissions and it takes time to go through all of\n "+
 			"them. We appreciate your understanding and patience.\n"+
 			"\n"+
 			"On behalf of the entire Verizon Innovation Program team, thanks for your interest, and best of\n "+
 			"luck with your innovation.\n\n"+
 			"-The Verizon Innovation Program Team\n";
 	
 	public static final String VZWV_EMAIL_CONTENT_HTML = "<html>"+
 			"<body>"+
 			"<div>"+
 			"<p><span style='font-size:11.0pt'>Section"+
 			"1: Company Details</span></p>"+
 			"<table border=1 cellspacing=0 cellpadding=0"+
 			" style='border-collapse:collapse;border:none'>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Company"+
 			"  Name</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-left:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$companyName</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Website</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$website</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>What"+
 			"  best describes the stage of your company? Please specify your company stage</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$stageOfCompany</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>How"+
 			"  many employees do you have?</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$numberOfEmployees</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Are"+
 			"  you currently generating revenue?</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$revenueGeneration</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>What"+
 			"  is your primary reason for engaging with the Verizon Innovation Program?</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$reasonForEnagaging</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			"</table>"+
 			"<p><span style='font-size:11.0pt'>Section"+
 			"2: Idea Details</span></p>"+
 			"<table border=1 cellspacing=0 cellpadding=0"+
 			" style='border-collapse:collapse;border:none'>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>What"+
 			"  would you like to work on within the Verizon Innovation Program? In a few short"+
 			"  sentences, tell us about your product idea and the hardware, software, or"+
 			"  application you are developing</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-left:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$productIdea</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>What"+
 			"  development stage best reflects your project? </span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$projectDevelopmentStage</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>How"+
 			"  are you looking to utilize Verizon connectivity within your product or service?"+
 			"  </span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$connectivityUtilization</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>What"+
 			"  best characterizes the need for data and speed within your product? </span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$productDataSpeed</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Which"+
 			"  Verizon Innovation Program services address areas where you need immediate"+
 			"  help? We expect this will change over time</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$programImmediateHelp</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			"</table>"+
 			"<p><span style='font-size:11.0pt'>Section"+
 			"3: Market Opportunity Details</span></p>"+
 			"<table border=1 cellspacing=0 cellpadding=0"+
 			" style='border-collapse:collapse;border:none'>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>What"+
 			"  vertical/industry does your project address? If your solution applies to more"+
 			"  than one, please select the best fit (where youâ€™d sell to first)</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-left:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$verticalIndustry</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Do"+
 			"  you have any existing partnerships / alliances in place to support your"+
 			"  product development, manufacturing, or go-to-market initiatives? Briefly"+
 			"  explain</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$existingPartnerships</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>What"+
 			"  type of financing do you plan to use to fund the development and go-to-market"+
 			"  phases of your proposed Innovation Program engagement?</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$typeOfFiniancing</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>What"+
 			"  is your current thinking on the business model for your project? For example,"+
 			"  a connected refrigerator solution might look to warranty and service programs"+
 			"  as keys to creating revenue streams.</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$projectBusinessModel</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Target Customers</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$targetCustomers</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Where"+
 			"  will your product be used?</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$productUsage</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Do"+
 			"  you have existing sales and distributions channels in place to reach your"+
 			"  customers?</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$existingSalesChannels</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Are"+
 			"  other companies already working in the same space as your project?</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$companiesWorkingSameSpace</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>What"+
 			"  is your current thinking on the market opportunity for this product? Thinking"+
 			"  short term, where would you expect to see Year One sales:</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$oneYearMarketSales</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			"</table>"+
 			"<p><span style='font-size:11.0pt'>Section"+
 			"4: Contact Details</span></p>"+
 			"<table border=1 cellspacing=0 cellpadding=0"+
 			" style='border-collapse:collapse;border:none'>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Name&nbsp;</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-left:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$contactName</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Position</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$contactPosition</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Email"+
 			"  address&nbsp;</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$contactEmail</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Phone"+
 			"  Number&nbsp;</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$contactPhone</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Does"+
 			"  your company have a Salesforce.com identifier? Please enter&nbsp;</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>$contactSalesforceIdentifier</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			" <tr>"+
 			"  <td width=319 valign=top style='width:300.4pt;border:solid windowtext 1.0pt;"+
 			"  border-top:none;padding:0in 5.4pt 0in 5.4pt'>"+
 			"  <p><span style='font-size:11.0pt'>Have"+
 			"  you ever contacted a Verizon sales representative(s)? If so, please reference</span></p>"+
 			"  </td>"+
 			"  <td width=319 valign=top style='width:300.4pt;border-top:none;border-left:"+
 			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"+
 			"  padding:0in 5.4pt 0in 5.4pt'>" +
 			"  <p><span style='font-size:11.0pt'>$contactVerizonSalesRep</span></p>"+
 			"  </td>"+
 			" </tr>"+
 			"</table>"+
 			"</div>"+
 			"</body>"+
 			"</html>";
 	
 	public static final String VZWV_EMAIL_CONTENT_TEXT = "Section 1: Company Details\n"+
 			"\n"+
 			"Company Name: $companyName\n"+
 			"Website: $website\n"+
 			"What best describes the stage of your company? Please specify your company stage: $stageOfCompany\n"+
 			"How many employees do you have?: $numberOfEmployees\n"+
 			"Are you currently generating revenue?: $revenueGeneration\n"+
 			"What is your primary reason for engaging with the Verizon Innovation Program? $reasonForEnagaging\n"+
 			"\n"+
 			"Section 2: Idea Details\n"+
 			"\n"+
 			"What would you like to work on within the Verizon Innovation Program? In a few short sentences, tell us about your product idea and the hardware, software, or application you are developing.: $productIdea\n"+
 			"What development stage best reflects your project?: $projectDevelopmentStage\n"+
 			"How are you looking to utilize Verizon connectivity within your product or service?:  $connectivityUtilization\n"+
 			"What best characterizes the need for data and speed within your product?: $productDataSpeed \n"+
 			"Which Verizon Innovation Program services address areas where you need immediate help? We expect this will change over time: $programImmediateHelp\n"+
 			"\n"+
 			"Section 3: Market Opportunity Details\n"+
 			"\n"+
 			"What vertical/industry does your project address? If your solution applies to more than one, please select the best fit (where you'd sell to first): $verticalIndustry\n"+
 			"Do you have any existing partnerships / alliances in place to support your product development, manufacturing, or go-to-market initiatives? Briefly explain: $existingPartnerships\n"+
 			"What type of financing do you plan to use to fund the development and go-to-market phases of your proposed Innovation Program engagement?: $typeOfFiniancing\n"+
 			"What is your current thinking on the business model for your project? For example, a connected refrigerator solution might look to warranty and service programs as keys to creating revenue streams: $projectBusinessModel \n"+
 			"Which Verizon Innovation Program services address areas where you need immediate help? We expect this will change over time: $targetCustomers\n"+
 			"Where will your product be used?: $productUsage\n"+
 			"Do you have existing sales and distributions channels in place to reach your customers?: $existingSalesChannels \n"+
 			"Are other companies already working in the same space as your project?: $companiesWorkingSameSpace\n"+
 			"What is your current thinking on the market opportunity for this product? Thinking short term, where would you expect to see Year One sales: $oneYearMarketSales  \n"+
 			"\n"+
 			"Section 4: Contact Details\n"+
 			"Name: $contactName \n"+
 			"Position: $contactPosition\n"+
 			"Email address:  $contactEmail\n"+
 			"Phone Number: $contactPhone \n"+
 			"Does your company have a Salesforce.com identifier? Please enter: $contactSalesforceIdentifier  \n"+
 			"Have you ever contacted a Verizon sales representative(s)? If so, please reference: $contactVerizonSalesRep \n";
 				


 	
}
