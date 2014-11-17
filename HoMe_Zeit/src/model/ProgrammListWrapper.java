package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of programms. This is used for saving the
 * list of programms to XML.
 */
@XmlRootElement(name = "programms")
public class ProgrammListWrapper {
	
	private List<Programm> programms;

    @XmlElement(name = "programm")
    public List<Programm> getProgramm() {
        return programms;
    }

    public void setProgramm(List<Programm> programms) {
        this.programms = programms;
    }

}
