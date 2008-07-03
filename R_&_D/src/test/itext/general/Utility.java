package test.itext.general;

import java.io.File;

public class Utility {
    public static final byte REPLACE_ALL = 0;
    public static final byte REPLACE_FIRST_ONLY = 1;
    public static final byte REPLACE_LAST_ONLY = 2;
	
	public static File createFile(String fileName){
		File file=new File(fileName);
		if(!file.exists()){
			file.canWrite();
		}
		return file;
	}
	
	public static String replace(String source, String stringToReplace,
			String replaceWith, int option) {
		replaceWith = (replaceWith == null) ? "" : replaceWith;
		StringBuffer buffer = new StringBuffer(source);

		if (option == REPLACE_FIRST_ONLY) {
			int start = source.indexOf(stringToReplace);
			int end = start + stringToReplace.length();
			buffer.replace(start, end, replaceWith);
		} else if (option == REPLACE_LAST_ONLY) {
			int start = source.lastIndexOf(stringToReplace);
			int end = start + stringToReplace.length();
			buffer.replace(start, end, replaceWith);
		} else if (option == REPLACE_ALL) {
			int start = 0;

			while ((start = buffer.toString().indexOf(stringToReplace, start)) > -1) {
				int end = start + stringToReplace.length();
				buffer.replace(start, end, replaceWith);
				start = start + replaceWith.length();
			}
		}

		return buffer.toString();
	}   	
}
