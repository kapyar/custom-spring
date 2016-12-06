package Hibernate;

import framework.core.GenericXmlApplicationContext;
import framework.core.annotations.Autowiring;
import framework.core.factory.BeanFactory;
import framework.core.inspectors.Interceptor;
import framework.core.xmlbean.XmlBeanDefinitionReader;
import framework.hibernate.HibernateLoader;
import org.junit.Before;

/**
 * Created by yaroslav on 12/6/16.
 */
public class HibernateTest {

    private GenericXmlApplicationContext    context;
    private BeanFactory                     factory;
    private HibernateLoader                 hibernateLoader;


    @Before
    public void init() {
        context = new GenericXmlApplicationContext(application.main.MainApp.class);

        context.load(application.main.MainApp.class.getResource("/GS_SpringXMLConfig.xml").getPath());
        context.setValidating(true);
        context.setParserType(XmlBeanDefinitionReader.ParserTypes.SAX);

        factory         = context.getBeanFactory();
        hibernateLoader = new HibernateLoader(application.main.HibernateApp.class.getResource("/hibernate.cfg.xml").getPath(), "application.hibernate.entities");


    }





}
