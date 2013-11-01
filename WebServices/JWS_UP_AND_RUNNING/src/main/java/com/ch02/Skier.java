package com.ch02;

import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Skier {
    
    @XmlElement(name = "player")
    private Person person;
    @XmlElement(name = "team")
    private String nationalTeam;
    @XmlElement(name = "achievements")
    private Collection<String> majorAchievements;

    public Skier() {
    }

    public Skier(Person person, String nationalTeam, Collection<String> majorAchievements) {
        this.person = person;
        this.nationalTeam = nationalTeam;
        this.majorAchievements = majorAchievements;
    }
    
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getNationalTeam() {
        return nationalTeam;
    }

    public void setNationalTeam(String nationalTeam) {
        this.nationalTeam = nationalTeam;
    }

    public Collection<String> getMajorAchievements() {
        return majorAchievements;
    }

    public void setMajorAchievements(Collection<String> majorAchievements) {
        this.majorAchievements = majorAchievements;
    }
    
}
