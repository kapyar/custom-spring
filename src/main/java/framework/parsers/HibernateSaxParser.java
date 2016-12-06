package framework.parsers;

import framework.parsers.entities.Bean;
import framework.parsers.entities.DBProperties;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class HibernateSaxParser extends DefaultHandler implements Parser {

    DBProperties dbProperties = new DBProperties();
    String packageToScan;
    String xmlFileName;


    public HibernateSaxParser(String xmlFileName) {
        this.xmlFileName = xmlFileName;
        parseDocument();
    }

    private void parseDocument() {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(xmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }

    public DBProperties getDbProperties() {
        return dbProperties;
    }

    public void setDbProperties(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    public String getPackageToScan() {
        return packageToScan;
    }

    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {


        if (elementName.equalsIgnoreCase("property")) {
            String propertyName = attributes.getValue("name");
            switch (propertyName) {
                case "driver_class":
                    dbProperties.setDriverClass(attributes.getValue("value"));
                    break;
                case "url":
                    dbProperties.setConnectionURL(attributes.getValue("value"));
                    break;
                case "username":
                    dbProperties.setUserName(attributes.getValue("value"));
                    break;
                case "password":
                    dbProperties.setPassword(attributes.getValue("value"));
                    break;
                default:
                    throw new SAXException();



            }
        }

        if (elementName.equalsIgnoreCase("component-scan")) {
            packageToScan = attributes.getValue("base-package");
        }
    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
    }


    @Override
    public List<Bean> getBeanList() {
        return null;
    }
}




