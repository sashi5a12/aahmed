package	com.netpace.aims.util;

public class TempFile {

		private java.lang.Long fileId;
		private java.lang.String fileName;
		private java.lang.String fileContentType;
        private java.lang.Long fileSize;

        public java.lang.Long getFileId() {
		    return this.fileId;
		}
		
		public void setFileId(java.lang.Long fileId) {
		    this.fileId = fileId;
		}
		
		public java.lang.String getFileName() {
		    return this.fileName;
		}
		
		public void setFileName(java.lang.String fileName) {
		    this.fileName = fileName;
		}
		
		public java.lang.String getFileContentType() {
		    return this.fileContentType;
		}
		
		public void setFileContentType(java.lang.String fileContentType) {
		    this.fileContentType = fileContentType;
		}

        public Long getFileSize() {
            return fileSize;
        }

        public void setFileSize(Long fileSize) {
            this.fileSize = fileSize;
        }

}

