package Anotation;

import static org.junit.Assert.*;
import application.beans.FarFarGalaxy.Galaxy;
import application.beans.darkside.LazerBazuka;
import application.beans.darkside.SupplyTroopService;
import framework.core.factory.BeanFactory;
import framework.core.GenericXmlApplicationContext;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

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
        assertTrue("FarFarGalaxy".equals(galaxy.getName()));
    }

    @Test
    public void ComponentLazerBazukaTestPositive()
    {
        final String expected = "SuperMassiveBazuka";
        Object [] obj = beanFactory.getComponentsValues();

        boolean result = false;
        for(int i = 0; i <obj.length;++i)
        {
            if(obj[i] instanceof LazerBazuka)
            {
                LazerBazuka bazuka = (LazerBazuka)obj[i];
                if(expected.equals(bazuka.getName()))
                {
                    result = true;
                }

            }
        }

        assertTrue(result);
    }

    @Test
    public void ComponentNameOldStormTrooperWithInterceptorTestPositive() {
        final String expected = "oldstormtrooper";

        Object[] obj = beanFactory.getComponents();
        boolean result = false;
        for (int i = 0; i < obj.length; ++i) {

            if (expected.equals(obj[i])) {
                result = true;
            }
        }
        assertTrue(result);
    }

    @Test
    public void ControllerTest()
    {
        beanFactory.getControllerNames();
    }


    @Test
    public void ServiceTest()
    {
        Object [] services = beanFactory.getServiceInstances();

        System.out.println((SupplyTroopService)services[0]);
    }





}