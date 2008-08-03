package test.itext.webapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

/**
 * If you want to avoid that your Servlet times out, you should use this ProgressServlet.
 * 
 * @author blowagie
 */
public class ProgressServlet extends HttpServlet {
	
	private static final long serialVersionUID = 6272312661092621179L;

	/**
	 * This class will keep a Pdf file
	 * @author blowagie
	 */
	public class MyPdf implements Runnable {

		/** the ByteArrayOutputStream that holds the PDF data. */
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		/** the percentage of the PDF file that is finished */
		int p = 0;
		
		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			// step 1
			Document doc = new Document();
			try {
				// step 2
				PdfWriter.getInstance(doc, baos);
				// step 3
				doc.open();
				// step 4
				while (p < 99) {
		            doc.add(new Paragraph(new Date().toString()));
		            // we slow the process down deliberately
					Thread.sleep(500);
					p++;
				}
			} catch (DocumentException e) {
				p = -1;
				e.printStackTrace();
			} catch (InterruptedException e) {
				p = -1;
				e.printStackTrace();
			}
			// step 5
			doc.close();
			p = 100;
		}
		/**
		 * Gets the complete PDF data
		 * @return the PDF as an array of bytes
		 * @throws DocumentException when the document isn't ready yet
		 */
		public ByteArrayOutputStream getPdf() throws DocumentException {
			if (p < 100) {
				throw new DocumentException("The document isn't finished yet!");
			}
			return baos;
		}
		/**
		 * Gets the current percentage of the file that is done.
		 * @return a percentage or -1 if something went wrong.
		 */
		public int getPercentage() {
			return p;
		}
	}
	
    /**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void doGet (HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    	// We get a Session object
        HttpSession session = request.getSession(true);
        Object o = session.getAttribute("myPdf");
        MyPdf pdf;
        if (o == null) {
        	pdf = new MyPdf();
        	session.setAttribute("myPdf", pdf);
        	Thread t = new Thread(pdf);
        	t.start();
        }
        else {
        	pdf = (MyPdf)o;
        }
        response.setContentType("text/html");
        switch (pdf.getPercentage()) {
        	case -1:
        		isError(response.getOutputStream());
        		return;
        	case 100:
        		isFinished(response.getOutputStream());
        		return;
        	default:
        		isBusy(pdf, response.getOutputStream());
        		return;
        }
    }
	/**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void doPost (HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    	// We get a Session object
        HttpSession session = request.getSession(false);
        try {
        	MyPdf pdf = (MyPdf) session.getAttribute("myPdf");
        	session.removeAttribute("myPdf");
			ByteArrayOutputStream baos = pdf.getPdf();
			//setting some response headers
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			//setting the content type
			response.setContentType("application/pdf");
			// the contentlength is needed for MSIE!!!
			response.setContentLength(baos.size());
			// write ByteArrayOutputStream to the ServletOutputStream
			ServletOutputStream out = response.getOutputStream();
			baos.writeTo(out);
			out.flush();
        }
        catch(Exception e) {
        	isError(response.getOutputStream());
        }
    }

	/**
	 * Sends an HTML page to the browser saying how many percent of the document is finished.
	 * @param pdf 		the class that holds the PDF
	 * @param stream	the outputstream of the servlet
	 * @throws IOException
	 */
	private void isBusy(MyPdf pdf, ServletOutputStream stream) throws IOException {
		stream.print("<html>\n\t<head>\n\t\t<title>Please wait...</title>\n\t\t<meta http-equiv=\"Refresh\" content=\"5\">\n\t</head>\n\t<body>");
		stream.print(String.valueOf(pdf.getPercentage()));
		stream.print("% of the document is done.<br>\nPlease Wait while this page refreshes automatically (every 5 seconds)\n\t</body>\n</html>");
	}

	/**
	 * Sends an HTML form to the browser to get the PDF
	 * @param stream	the outputstream of the servlet
	 * @throws IOException
	 */
	private void isFinished(ServletOutputStream stream) throws IOException {
		stream.print("<html>\n\t<head>\n\t\t<title>Finished!</title>\n\t</head>\n\t<body>");
		stream.print("The document is finished:<form method=\"POST\"><input type=\"Submit\" value=\"Get PDF\"></form>\n\t</body>\n</html>");
	}

	/**
	 * Sends an error message in HTML to the browser
	 * @param stream	the outputstream of the servlet
	 * @throws IOException
	 */
	private void isError(ServletOutputStream stream) throws IOException {
		stream.print("<html>\n\t<head>\n\t\t<title>Error</title>\n\t</head>\n\t<body>");
		stream.print("An error occured.\n\t</body>\n</html>");
	}
}