package test.itext.general;

import java.io.File;

public class Utility {
	public static File createFile(String fileName){
		File file=new File(fileName);
		if(!file.exists()){
			file.canWrite();
		}
		return file;
	}
}
