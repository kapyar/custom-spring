package Creation;

import static org.junit.Assert.*;

import application.classes.Transport.Bus;
import application.classes.Transport.Car;
import application.classes.Greeting.GreetingService;
import application.classes.Transport.Transport;
import framework.core.factory.BeanFactory;
import framework.core.GenericXmlApplicationContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by yaroslav on 11/29/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class CreationTests {


    private GenericXmlApplicationContext context;
    private BeanFactory beanFactory;

    @Before
    public void init()
    {
        context = new GenericXmlApplicationContext();
        beanFactory = context.getBeanFactory();
    }


    //          CONSTRUCTOR INJECTION   //

    @Test
    public void CreateObjectWithConstructorTestPositive()
    {
        String expected = "Hello World!";
        GreetingService greetingService = beanFactory.getBean("greetingService", GreetingService.class);
        String actual = greetingService.getMessage();

        assertEquals("Create bean with constructor param positive: ", actual, expected);
    }


    @Test
    public void CreateObjectWithConstructorTestNegative()
    {
        String expected = "Dummy string!";
        GreetingService greetingService = beanFactory.getBean("greetingService", GreetingService.class);
        String actual = greetingService.getMessage();

        assertFalse("Create bean with constructor param negative: ", actual.equals(expected));
    }

    @Test
    public void MultipleConstructorParamTestPositive()
    {

        String expected = "Ferrari";
        Car ferrari   = (Car)beanFactory.getBean("car", Transport.class);
        String actual = ferrari.getName();

        assertEquals("Name of car: ", actual, expected);
    }

    @Test
    public void MultipleConstructorParamTestNegative()
    {

        int expected = 6;
        Car ferrari   = (Car)beanFactory.getBean("car", Transport.class);
        int actual = ferrari.getWheelCount();

        assertFalse("Name of car: ", actual == expected);
    }


    //      SETTERS INJECTIONS      //

    @Test
    public void CreateObjectWithSetterTestPositive()
    {
        String expected = "Transport type is Bus";
        Bus bus = (Bus) beanFactory.getBean("bus", Transport.class);
        String actual = bus.getMessage();

        assertEquals("Create bean with setter param positive: ", expected, actual);
    }


    @Test
    public void CreateObjectWithSetterTestNegative()
    {
        String expected = "I am the bicycle!";
        Bus bus = beanFactory.getBean("bus", Bus.class);
        String actual = bus.getMessage();

        assertFalse("Create bean with setter param negative: ", actual.equals(expected));
    }





}
