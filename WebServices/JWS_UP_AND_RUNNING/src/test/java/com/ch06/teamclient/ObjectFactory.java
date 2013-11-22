
package com.ch06.teamclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ch06.teamclient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetTeam_QNAME = new QName("http://ch06.com/", "getTeam");
    private final static QName _GetTeams_QNAME = new QName("http://ch06.com/", "getTeams");
    private final static QName _GetTeamsResponse_QNAME = new QName("http://ch06.com/", "getTeamsResponse");
    private final static QName _GetTeamResponse_QNAME = new QName("http://ch06.com/", "getTeamResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ch06.teamclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTeamResponse }
     * 
     */
    public GetTeamResponse createGetTeamResponse() {
        return new GetTeamResponse();
    }

    /**
     * Create an instance of {@link GetTeams }
     * 
     */
    public GetTeams createGetTeams() {
        return new GetTeams();
    }

    /**
     * Create an instance of {@link GetTeam }
     * 
     */
    public GetTeam createGetTeam() {
        return new GetTeam();
    }

    /**
     * Create an instance of {@link GetTeamsResponse }
     * 
     */
    public GetTeamsResponse createGetTeamsResponse() {
        return new GetTeamsResponse();
    }

    /**
     * Create an instance of {@link Player }
     * 
     */
    public Player createPlayer() {
        return new Player();
    }

    /**
     * Create an instance of {@link Team }
     * 
     */
    public Team createTeam() {
        return new Team();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTeam }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ch06.com/", name = "getTeam")
    public JAXBElement<GetTeam> createGetTeam(GetTeam value) {
        return new JAXBElement<GetTeam>(_GetTeam_QNAME, GetTeam.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTeams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ch06.com/", name = "getTeams")
    public JAXBElement<GetTeams> createGetTeams(GetTeams value) {
        return new JAXBElement<GetTeams>(_GetTeams_QNAME, GetTeams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTeamsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ch06.com/", name = "getTeamsResponse")
    public JAXBElement<GetTeamsResponse> createGetTeamsResponse(GetTeamsResponse value) {
        return new JAXBElement<GetTeamsResponse>(_GetTeamsResponse_QNAME, GetTeamsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTeamResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ch06.com/", name = "getTeamResponse")
    public JAXBElement<GetTeamResponse> createGetTeamResponse(GetTeamResponse value) {
        return new JAXBElement<GetTeamResponse>(_GetTeamResponse_QNAME, GetTeamResponse.class, null, value);
    }

}
