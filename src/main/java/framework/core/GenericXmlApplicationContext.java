package framework.core;

import framework.core.XmlBeanDefinitionReader.ParserTypes;

public class GenericXmlApplicationContext {
	
	private static final String CONFIG_FILE_NAME = GenericXmlApplicationContext.class.getResource("/GS_SpringXMLConfig.xml").getPath();
	
	private final XmlBeanDefinitionReader reader;
	
	public XmlBeanDefinitionReader getReader() {
		return reader;
	}

	private String xmlFileLocation;	
	
	public GenericXmlApplicationContext() {
		this.xmlFileLocation = CONFIG_FILE_NAME;
		reader = new XmlBeanDefinitionReader();
	}
	
	public GenericXmlApplicationContext(String xmlFileLocation) {
		this.xmlFileLocation = xmlFileLocation;
		reader = new XmlBeanDefinitionReader();
	}
	
	public void setValidating(boolean validating){
		reader.setValidating(validating);
	}
	
	public void setParserType(ParserTypes parserType){
		reader.setParserType(parserType);
	}
	
	public void load(String xmlFileLocation){
		this.xmlFileLocation = xmlFileLocation;
	}
	
	public BeanFactory getBeanFactory(){
		return new XmlBeanFactory(xmlFileLocation, reader);
	}
}
