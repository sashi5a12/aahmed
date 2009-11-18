
package com.netpace.aims.ws.appinfo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContentType_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ContentType_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="GAME"/>
 *     &lt;enumeration value="APPLICATION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ContentType_Type")
@XmlEnum
public enum ContentTypeType {

    GAME,
    APPLICATN;

    public String value() {
        return name();
    }

    public static ContentTypeType fromValue(String v) {
        return valueOf(v);
    }

}
