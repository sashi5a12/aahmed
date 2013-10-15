
package sample.ch01.team.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getTeamResponse", namespace = "http://team.ch01.sample/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTeamResponse", namespace = "http://team.ch01.sample/")
public class GetTeamResponse {

    @XmlElement(name = "return", namespace = "")
    private sample.ch01.team.Team _return;

    /**
     * 
     * @return
     *     returns Team
     */
    public sample.ch01.team.Team getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(sample.ch01.team.Team _return) {
        this._return = _return;
    }

}
