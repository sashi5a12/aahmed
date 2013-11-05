
package com.ch03.jaxws;

import java.awt.Image;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getImageResponse", namespace = "http://ch03.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getImageResponse", namespace = "http://ch03.com/")
public class GetImageResponse {

    @XmlElement(name = "return", namespace = "")
    private Image _return;

    /**
     * 
     * @return
     *     returns Image
     */
    public Image getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(Image _return) {
        this._return = _return;
    }

}
