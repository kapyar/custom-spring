package framework.parsers.entities;

import framework.core.factory.BeanFactory;
import framework.core.exceptions.DIExceprion;
import framework.core.factory.XmlBeanFactory;
import framework.core.utils.CopyingUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Bean {
    String name;
    String className;
    String scope;
    String beanType;
    private Object beanInstance;
    public static String SINGLETON = "singleton";
    public static String PROTOTYPE = "prototype";


    public Object getBeanInstance() {
        if (isSingleton()||beanType==null) {
            if (beanInstance == null) {
                synchronized (this) {
                    if (beanInstance == null) {
                        initialize();
                    }
                }
            }
        } else {
            if (beanInstance == null) {
                initialize();
            } else try {
                beanInstance = CopyingUtil.copy(beanInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return beanInstance;

    }


    private void initialize() {
        try {
            final Class<?> clazz = Class.forName(this.getClassName());
            Constructor<?> ctor;

            List<BeanConstuctorParam> ca = this.getConstuctorParams();

            if (!ca.isEmpty()) {

                Class<?>[] consClasses = new Class[ca.size()];
                int k = 0;
                for (BeanConstuctorParam beanConstuctorParam : ca) {
                    if (beanConstuctorParam.getRef() == null) {
                        if (beanConstuctorParam.getType() == null || beanConstuctorParam.getType().contentEquals("String")) {
                            consClasses[k] = String.class;
                        } else if (XmlBeanFactory.classLibrary.containsKey(beanConstuctorParam.getType())) {
                            consClasses[k] = BeanFactory.getPrimitiveClassForName(beanConstuctorParam.getType());
                        } else {
                            consClasses[k] = Class.forName(beanConstuctorParam.getType());
                        }
                    } else {
                        if (XmlBeanFactory.beanTable.get(beanConstuctorParam.getRef()).getBeanInstance() == null)
                            throw new DIExceprion("Bean not found");
                        consClasses[k] = XmlBeanFactory.beanTable.get(beanConstuctorParam.getRef()).getBeanInstance().getClass();
                    }
                    k++;
                }


                ctor = clazz.getConstructor(consClasses);
                Object[] consArgs = new Object[consClasses.length];
                int j = 0;
                for (BeanConstuctorParam beanConstuctorParam : ca) {
                    if (beanConstuctorParam.getRef() != null) {
                        consArgs[j] = XmlBeanFactory.beanTable.get(beanConstuctorParam.getRef()).getBeanInstance();
                    } else if (consClasses[j].isPrimitive()) {
                        consArgs[j] =
                                BeanFactory.getWrapperClassValueForPrimitiveType(consClasses[j], beanConstuctorParam.getValue());
                    } else {
                        consArgs[j] = consClasses[j].cast(beanConstuctorParam.getValue());
                    }
                    j++;
                }
                beanInstance = ctor.newInstance(consArgs);
            } else {
                ctor = clazz.getConstructor();
                beanInstance = ctor.newInstance();
            }

            List<BeanProperties> props = this.getBeanPropertieses();

            if (!props.isEmpty()) {

                for (BeanProperties beanProperty : props) {
                    char first = Character.toUpperCase(beanProperty.getName().charAt(0));
                    String methodName = "set" + first + beanProperty.getName().substring(1);
                    if (beanProperty.getValue() == null) {
                        Object o = XmlBeanFactory.beanTable.get(beanProperty.getRef()).getBeanInstance();
                        if (o == null)
                            throw new DIExceprion("Bean not found");
                        Method method = beanInstance.getClass().getMethod(methodName,
                                new Class[]{XmlBeanFactory.beanTable.get(beanProperty.getRef()).getBeanInstance().getClass()});
                        method.invoke(beanInstance, XmlBeanFactory.beanTable.get(beanProperty.getRef()).getBeanInstance());
                    } else {
                        Method method = beanInstance.getClass().getMethod(methodName,
                                new Class[]{beanProperty.getValue().getClass()});
                        method.invoke(beanInstance, beanProperty.getValue());
                    }
                }

            }

            XmlBeanFactory.beanTable.put(this.getName(), this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    ArrayList<BeanConstuctorParam> constuctorParams = new ArrayList<BeanConstuctorParam>();
    ArrayList<BeanProperties> beanPropertieses = new ArrayList<BeanProperties>();

    public ArrayList<BeanConstuctorParam> getConstuctorParams() {
        return constuctorParams;
    }

    public void setConstuctorParams(ArrayList<BeanConstuctorParam> constuctorParams) {
        this.constuctorParams = constuctorParams;
    }

    public ArrayList<BeanProperties> getBeanPropertieses() {
        return beanPropertieses;
    }

    public void setBeanPropertieses(ArrayList<BeanProperties> beanPropertieses) {
        this.beanPropertieses = beanPropertieses;
    }


    public String getBeanType() {
        return beanType;
    }

    public void setBeanType(String beanType) {
        this.beanType = beanType;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", scope='" + scope + '\'' +
                ", beanType='" + beanType + '\'' +
                ", constuctorParams=" + constuctorParams +
                ", beanPropertieses=" + beanPropertieses +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isSingleton() {
        return SINGLETON.equals(beanType);
    }

    public boolean isPrototype() {
        return PROTOTYPE.equals(beanType);
    }
}
