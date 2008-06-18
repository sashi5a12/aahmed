package fileupload;



import java.io.File;

import com.opensymphony.xwork2.ActionSupport;

public class FileUpload extends ActionSupport{
	private File image;
	private String imageContentType;
	private String imageFileName;
	private String fileSystemPath;
	
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	public String getImageContentType() {
		return imageContentType;
	}
	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	public String getFileSystemPath() {
		return fileSystemPath;
	}
	public void setFileSystemPath(String fileSystemPath) {
		this.fileSystemPath = fileSystemPath;
	}
	public void validate(){
		if(image==null){
			addFieldError("file", "Required: File");
		}
	}
	public String execute(){
		System.out.println(imageFileName+" uploaded sucessfully at "+this.fileSystemPath);
		return SUCCESS;
	}
}
