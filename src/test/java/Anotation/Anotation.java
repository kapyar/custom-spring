package Anotation;

import static org.junit.Assert.*;
import application.beans.FarFarGalaxy.Galaxy;
import application.beans.darkside.LazerBazuka;
import framework.core.factory.BeanFactory;
import framework.core.GenericXmlApplicationContext;
import org.junit.Before;
import org.junit.Test;

public class Anotation {

    private GenericXmlApplicationContext context;
    private BeanFactory beanFactory;


    @Before
    public void init()
    {
        context = new GenericXmlApplicationContext();
        beanFactory = context.getBeanFactory();
    }


    @Test
    public void AutowiringTestPositive()
    {
        Galaxy galaxy = beanFactory.getBean("galaxy", Galaxy.class);
        System.out.println(galaxy);
    }

    @Test
    public void ComponentTestPositive()
    {
        final String expected = "SuperMassiveBazuka";
        LazerBazuka[] lazerBazukas = (LazerBazuka[])beanFactory.getComponents();

        boolean result = false;
        for(int i = 0; i <lazerBazukas.length;++i)
        {
            if(lazerBazukas[i].getName().equals(expected))
            {
                result = true;
            }
        }

        assertTrue(result);

    }



}