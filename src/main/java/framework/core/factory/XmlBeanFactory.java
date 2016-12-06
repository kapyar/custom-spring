package framework.core.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import framework.core.annotations.Component;
import framework.core.annotations.Controller;
import framework.core.annotations.Repository;
import framework.core.annotations.Service;
import framework.core.utils.InjectionUtils;
import framework.core.xmlbean.XmlBeanDefinitionReader;
import framework.parsers.entities.Bean;
import org.reflections.Reflections;

import javax.naming.ConfigurationException;

public class XmlBeanFactory implements BeanFactory {

    public static HashMap<String, Bean> beanTable   = new HashMap<>();
    HashMap<String, Object> interceptorTable        = new HashMap<>();


    HashMap<String, Object> componentsTable = new HashMap<>();
    HashMap<String, Object> controllerTable = new HashMap<>();
    HashMap<String, Object> serviceTable    = new HashMap<>();
    HashMap<String, Object> repositoryTable = new HashMap<>();


    public HashMap<String, Object> getComponentsTable() {
        return componentsTable;
    }


    public XmlBeanFactory(String xmlFilePath, XmlBeanDefinitionReader xbdr) {
        xbdr.loadBeanDefinitions(xmlFilePath);
        generateBeans(xbdr.getBeanList());
        setupInterceptors(xbdr.getInterceptorList());

        generateComponents(xbdr.getPackageName());
//        generateControllers(xbdr.getPackageName());
        generateServices(xbdr.getPackageName());
//        generateRepositories(xbdr.getPackageName());


    }

    private void generateComponents(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> clazz : annotated) {
            try {
                final Object newInstance = clazz.newInstance();
                componentsTable.put(clazz.getSimpleName().toLowerCase(), newInstance);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    private void generateControllers(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class<?> clazz : annotated) {
            try {
                final Object newInstance = clazz.newInstance();
                try {
                    InjectionUtils.autowireIfAnotated(newInstance.getClass());
                } catch (ConfigurationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                controllerTable.put(clazz.getSimpleName().toLowerCase(), newInstance);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    private void generateServices(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Service.class);
        for (Class<?> clazz : annotated) {
            try {
                final Object newInstance = clazz.newInstance();
                try {
                    InjectionUtils.autowireIfAnotated(newInstance.getClass());
                } catch (ConfigurationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                serviceTable.put(clazz.getSimpleName().toLowerCase(), newInstance);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    private void generateRepositories(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Repository.class);
        for (Class<?> clazz : annotated) {
            try {
                final Object newInstance = clazz.newInstance();
                try {
                    InjectionUtils.autowireIfAnotated(newInstance.getClass());
                } catch (ConfigurationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                repositoryTable.put(clazz.getSimpleName().toLowerCase(), newInstance);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private void generateBeans(List<Bean> beanList) {
        for (Bean b : beanList) {
            try {
                b.getBeanInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void setupInterceptors(List<Bean> interceptorList) {
        for (Bean b : interceptorList) {
            try {
                final Class<?> clazz = Class.forName(b.getClassName());
                Object interceptor = clazz.getConstructor().newInstance();
                interceptorTable.put(b.getName(), interceptor);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public Object getBean(String string) {
        return beanTable.get(string).getBeanInstance();
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String string, Class<T> type) {
        return (T) beanTable.get(string).getBeanInstance();
    }

    public Object[] getInterceptors() {
        return (Object[]) interceptorTable.values().toArray();
    }

    public Object[] getComponents() {
        return (Object[]) componentsTable.keySet().toArray();
    }

    public Object[] getComponentsValues() {
        return (Object[]) componentsTable.values().toArray();
    }

    @Override
    public Object[] getServiceNames() {
        return (Object[]) serviceTable.keySet().toArray();
    }

    @Override
    public Object[] getServiceInstances() {
        return (Object[]) serviceTable.values().toArray();
    }

    @Override
    public Object[] getRepositoriesNames() {
        return (Object[]) repositoryTable.keySet().toArray();
    }

    @Override
    public Object[] getRepositoriesInstances() {
        return (Object[]) repositoryTable.values().toArray();
    }

    @Override
    public Object[] getControllerNames() {
        return (Object[]) componentsTable.keySet().toArray();
    }

    @Override
    public Object[] getControllerInstancess() {
        return (Object[]) componentsTable.values().toArray();
    }

    public void setComponentsTable(HashMap<String, Object> componentsTable) {
        this.componentsTable = componentsTable;
    }

    public HashMap<String, Object> getControllerTable() {
        return controllerTable;
    }

    public void setControllerTable(HashMap<String, Object> controllerTable) {
        this.controllerTable = controllerTable;
    }

    public HashMap<String, Object> getServiceTable() {
        return serviceTable;
    }

    public void setServiceTable(HashMap<String, Object> serviceTable) {
        this.serviceTable = serviceTable;
    }

    public HashMap<String, Object> getRepositoryTable() {
        return repositoryTable;
    }

    public void setRepositoryTable(HashMap<String, Object> repositoryTable) {
        this.repositoryTable = repositoryTable;

    }
}
