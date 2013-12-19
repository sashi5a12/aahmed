package faststring;

import javax.xml.bind.DatatypeConverter;


public class StringInterner {

    public static String parseStringToString(String value) {
        return DatatypeConverter.parseString(value).intern();
    }
}
