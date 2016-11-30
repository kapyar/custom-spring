package Scope;


import static org.junit.Assert.*;

import application.classes.Transport.Bus;
import framework.core.factory.BeanFactory;
import framework.core.GenericXmlApplicationContext;
import org.junit.Before;
import org.junit.Test;

public class ScopeTests {


    private GenericXmlApplicationContext context;
    private BeanFactory beanFactory;


    @Before
    public void init()
    {
        context = new GenericXmlApplicationContext();
        beanFactory = context.getBeanFactory();
    }


    //      SINGLETONE      //

    @Test
    public void defaultScopeSingletoneTestPositive()
    {
        String expected = "I am bus 1";
        Bus bus = beanFactory.getBean("bus2", Bus.class);
        bus.setMessage(expected);

        Bus bus2 = beanFactory.getBean("bus2", Bus.class);
        String actual = bus2.getMessage();

        assertEquals("default scope is singletone: ", actual, expected);

    }


    @Test
    public void defaultScopeSingletoneTestNegative()
    {

        String expected = "I am bus 1";
        Bus bus = beanFactory.getBean("bus2", Bus.class);
        bus.setMessage(expected);

        Bus bus2 = beanFactory.getBean("bus2", Bus.class);
        bus2.setMessage("I am bus 2");
        String actual = bus2.getMessage();

        assertNotSame("default scope is singletone: ", expected, actual);

    }

    //          PROTOTYPE       //


    @Test
    public void scopePrototypeTestPositive()
    {
        String expected = "I am bus 1";
        Bus bus = beanFactory.getBean("busPrototype", Bus.class);
        bus.setMessage(expected);

        Bus bus2 = beanFactory.getBean("busPrototype", Bus.class);
        bus2.setMessage("I am bus 2");
        String actual = bus2.getMessage();

        assertNotSame("default scope is singletone: ", expected, actual);
    }

    @Test
    public void scopePrototypeTestNegative()
    {
        String expected = "I am bus 1";
        Bus bus = beanFactory.getBean("busPrototype", Bus.class);
        bus.setMessage(expected);

        Bus bus2 = beanFactory.getBean("busPrototype", Bus.class);
        bus2.setMessage("I am bus 2");
        String actual = bus2.getMessage();

        assertNotSame("default scope is singletone: ", expected, actual);
    }

}
