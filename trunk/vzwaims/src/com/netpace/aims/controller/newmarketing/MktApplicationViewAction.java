package com.netpace.aims.controller.newmarketing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.netpace.aims.bo.newmarketing.ApplicationManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.GifDecoder;
import com.netpace.aims.util.Utility;

/**
 * @struts.action path="/newMktApplicationView"
 *                scope="request"
 *                input="/newMktApplication.do"
 *                name="MktApplicationFilterForm"
 *                validate="false"
 * @struts.action-forward name="list" path="/newmarketing/applicationList.jsp"
 * @author Ahson Imtiaz
 */

public class MktApplicationViewAction extends BaseAction
{

    static Logger log = Logger.getLogger(MktApplicationViewAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            //Throw AimsSecurityException() if No Privileges
            checkAccess(request, AimsPrivilegesConstants.NEW_MARKETING);

            HttpSession session = request.getSession();
            //AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
            //int iPageLength = user.getRowsLength().intValue();
            //StringBuffer sbFilter = new StringBuffer();
            boolean bIsGif, bFirstRead;
            MktApplicationFilterForm frm = (MktApplicationFilterForm) form;
            GifDecoder gf = new GifDecoder();
            Image imgLoaded = null;

            if (frm.getTask() != null && frm.getTask().equals("view"))
            {

                log.debug("****** Enter in View Block");

                MarketApplicationExt mae = ApplicationManager.getContentDetail(frm.getApplicationId());
                //                MarketApplicationExt mae = ApplicationManager.getApplicationDetail(new Long("10205"));
                // Creating document and setting the temporay outputstream
                Document pdfDoc = new Document();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                // Creating header content of pdf document
                PdfWriter.getInstance(pdfDoc, baos);
                pdfDoc.addTitle("The ZON - Content Details");
                pdfDoc.addSubject("Conntent general details and screens");
                pdfDoc.addAuthor("Verizon Wireless Zon Team");
                pdfDoc.addCreator("Netpace Inc.");

                // Setting page header for pdf document
                Color border = new Color(0x00, 0x00, 0x00);
                Font font1 = new Font(Font.HELVETICA, 16, Font.NORMAL, border);
                Font font2 = new Font(Font.HELVETICA, 10, Font.NORMAL, border);
                Font fontbold = new Font(Font.HELVETICA, 10, Font.BOLD, border);
                Font fontsmall = new Font(Font.HELVETICA, 8, Font.BOLD, border);
                HeaderFooter header = new HeaderFooter(new Phrase("THE ZON - Content Detail View", font1), false);
                header.setAlignment(Element.ALIGN_LEFT);
                header.setBorder(Rectangle.BOTTOM);
                header.setBorderColor(border);
                pdfDoc.setHeader(header);
                //Adding a Footer
                HeaderFooter footer = new HeaderFooter(new Phrase("Verizon Wireless Confidential - Do not distribute.", fontsmall), false);
                footer.setAlignment(Element.ALIGN_LEFT);
                footer.setBorder(Rectangle.TOP);
                footer.setBorderColor(border);
                pdfDoc.setFooter(footer);

                // Generating page body for content
                pdfDoc.open();
                //adding table for general content
                Table detTable = new Table(2, 6);
                detTable.setBorderWidth(1);
                detTable.setBorderColor(new Color(177, 177, 177));
                detTable.setPadding(5);
                detTable.setSpacing(0);
                detTable.addCell(new Phrase("Content Title", fontbold));
                detTable.addCell(new Phrase(Utility.getObjectString(mae.getAppTitle()), font2));
                detTable.addCell(new Phrase("Content Description", fontbold));
                detTable.addCell(new Phrase(Utility.getObjectString(mae.getDeveloperName()), font2));
                detTable.addCell(new Phrase("Application Title", fontbold));
                detTable.addCell(new Phrase(Utility.getObjectString(mae.getDeckPlacement()), font2));
                detTable.addCell(new Phrase("Expiry Date", fontbold));
                detTable.addCell(new Phrase(Utility.convertToString(mae.getLaunchDate(), AimsConstants.DATE_FORMAT), font2));
                detTable.setAlignment(Element.ALIGN_LEFT);
                pdfDoc.add(detTable);
                // Adding heading for Images
                Paragraph name = new Paragraph("\n\nContent Screen Images", fontbold);
                name.setAlignment(Element.ALIGN_LEFT);
                pdfDoc.add(name);
                // Adding Images.
                Blob blobImage = null;
                Object[] objImages = mae.getBlobImages();

                Table imgTable = new Table(3);
                detTable.setBorderWidth(0);
                imgTable.setPadding(5);
                imgTable.setSpacing(0);
                imgTable.setAlignment(Element.ALIGN_LEFT);
//                imgTable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);

                log.debug(" ***** ImagesLength : " + objImages.length);

                for (int iCount = 0; iCount < objImages.length; iCount++)
                {

                    blobImage = (Blob) objImages[iCount];

                    bIsGif = false;
                    bFirstRead = true;

                    if (blobImage != null)
                    {
                        //JPEGImageDecoder jid = JPEGCodec.createJPEGDecoder(blobImage.getBinaryStream());
                        //java.awt.image.BufferedImage bImage = jid.decodeAsBufferedImage();

                        log.debug("Image size is : " + blobImage.length());
                        InputStream in = blobImage.getBinaryStream();
                        ByteArrayOutputStream boasImg = new ByteArrayOutputStream();

                        if (in != null)
                        {
                            int bytesRead = 0;
                            byte[] buffer = new byte[8192];
                            while ((bytesRead = in.read(buffer, 0, 8192)) != -1)
                            {
                                boasImg.write(buffer, 0, bytesRead);
                                if (bFirstRead)
                                    if (buffer[0] == 'G' && buffer[1] == 'I' && buffer[2] == 'F')
                                        bIsGif = true;
                            }
                            in.close();

                            if (!bIsGif)
                            {
                                imgLoaded = com.lowagie.text.Image.getInstance(boasImg.toByteArray());
                            }
                            else
                            {
                                gf.read(new ByteArrayInputStream(boasImg.toByteArray()));
                                BufferedImage bf = gf.getFrame(0);
                                imgLoaded = com.lowagie.text.Image.getInstance(bf, null);
                            }
                            imgLoaded.scalePercent(40);
                            Cell cellimg = new Cell(imgLoaded);
                            cellimg.setBackgroundColor(new Color(0xff, 0xff, 0xff));
                            cellimg.setHorizontalAlignment(Element.ALIGN_CENTER);
                            imgTable.addCell(cellimg);
                            log.debug("Image added to table iCount is : " + iCount);
                        }
                        else
                            imgTable.addCell("N/A");

                    }
                    else
                        imgTable.addCell("N/A");
                }

                pdfDoc.add(imgTable);
                pdfDoc.close();
                response.setContentType("application/pdf");
                response.setContentLength(baos.size());
                javax.servlet.ServletOutputStream out = response.getOutputStream();
                baos.writeTo(out);
                out.flush();
                out.close();
                return mapping.getInputForward();
            }

            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
        return mapping.getInputForward();
    }
}
