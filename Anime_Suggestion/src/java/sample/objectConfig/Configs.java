/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.objectConfig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author DELL
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "configs", propOrder = {
    "config"
})
@XmlRootElement(name = "configs",namespace = "http://xml.netbeans.org/schema/configs")
public class Configs {
    @XmlElement(required = true)
    protected List<Config> config;

    public List<Config> getConfig() {
        if(config == null){
            config = new ArrayList<Config>();
        }
        return config;
    }

    public void setConfig(List<Config> config) {
        this.config = config;
    }
    
    
}
