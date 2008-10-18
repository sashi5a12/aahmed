package strutsTutorial;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

public class MyDownloadAction extends DownloadAction {

	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		File file=new File("D:\\Downloads\\prototype-160-api.pdf");
		FileInputStream ins=new FileInputStream(file);
		byte[] data = new byte[(int)file.length()];
		ins.read(data);
		ins.close();
		response.setHeader("Content-disposition", "attachment; filename=" + "prototype.pdf");

		String contentType = "application/pdf";
		return new ByteArrayStreamInfo(contentType, data);
	}

	protected class ByteArrayStreamInfo implements StreamInfo {

		protected String contentType;
		protected byte[] bytes;

		public ByteArrayStreamInfo(String contentType, byte[] bytes) {
			this.contentType = contentType;
			this.bytes = bytes;
		}

		public String getContentType() {
			return contentType;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(bytes);
		}
	}
}
