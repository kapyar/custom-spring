package framework.core;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import framework.core.XmlBeanDefinitionReader.ParserTypes;

public class GenericXmlApplicationContext {
	
	private static final String CONFIG_FILE_NAME = GenericXmlApplicationContext.class.getResource("/GS_SpringXMLConfig.xml").getPath();
	
	private final XmlBeanDefinitionReader reader;
	private final BeanFactory beanFactory;
	
	public XmlBeanDefinitionReader getReader() {
		return reader;
	}

	private String xmlFileLocation;	
	
	public GenericXmlApplicationContext() {
		this(CONFIG_FILE_NAME);		
	}
	
	@SuppressWarnings("unchecked")
	public GenericXmlApplicationContext(Class<?> classObject) {
		this(CONFIG_FILE_NAME);
		
		Field[] fields = classObject.getDeclaredFields();
		for (Field f : fields) {
			if (f.isAnnotationPresent(Autowiring.class)) {
				ClassLoader myCL = Thread.currentThread().getContextClassLoader();
				Field classLoaderClassesField = null;
				Class<?> myCLClass = myCL.getClass();
		        while (myCLClass != java.lang.ClassLoader.class) {
		        	myCLClass = myCLClass.getSuperclass();
		        }
				try {
					classLoaderClassesField = myCLClass.getDeclaredField("classes");
				} catch (NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}
				classLoaderClassesField.setAccessible(true);
				
				List<Class<?>> classes = null;
				try {
					classes = (List<Class<?>>) classLoaderClassesField.get(myCL);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				Iterator<Class<?>> it = classes.iterator();
				while (it.hasNext()) {
					Class<?> nextClass = it.next();
					if (!nextClass.isInterface() && f.getType().isAssignableFrom(nextClass)) {
						try {
							f.setAccessible(true);
							f.set(null, nextClass.newInstance());
						} catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}
	}
	
	public GenericXmlApplicationContext(String xmlFileLocation) {
		this.xmlFileLocation = xmlFileLocation;
		reader = new XmlBeanDefinitionReader();
		beanFactory = new XmlBeanFactory(this.xmlFileLocation, reader);
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
		return beanFactory;
	}
}
