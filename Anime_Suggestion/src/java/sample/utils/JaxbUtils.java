/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author DELL
 */
public class JaxbUtils implements Serializable{
    public static Object bindingXMLToObject(Class object,String filePath) 
        throws JAXBException,IOException{
        JAXBContext jc = JAXBContext.newInstance(object);
        Unmarshaller u = jc.createUnmarshaller();
        File f = new File(filePath);
        return u.unmarshal(f);
    }
    
    public static Object bindingXMLToObject(Class object,String filePath,String filePathSchema)
        throws JAXBException,IOException, SAXException{
        JAXBContext jc = JAXBContext.newInstance(object);
        Unmarshaller u = jc.createUnmarshaller();
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(filePathSchema));
        
        u.setSchema(schema);
        File f = new File(filePath);
        return u.unmarshal(f);
    }
    
    public static Object bindingXMLToObject(Class object,InputStream is,String filePathSchema)
        throws JAXBException,IOException, SAXException{
        JAXBContext jc = JAXBContext.newInstance(object);
        Unmarshaller u = jc.createUnmarshaller();
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(filePathSchema));
        
        u.setSchema(schema);
        return u.unmarshal(is);
    }
    
    public static void marshall(Class clazz,Object object,String filePathOutput) 
            throws JAXBException{
        JAXBContext ctx = JAXBContext.newInstance(clazz);
        Marshaller mar = ctx.createMarshaller();
        mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(object, new File(filePathOutput));
    }
}
