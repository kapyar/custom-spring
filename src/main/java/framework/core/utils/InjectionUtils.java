package framework.core.utils;

import framework.core.GenericXmlApplicationContext;
import framework.core.annotations.Autowiring;

import javax.naming.ConfigurationException;
import java.lang.reflect.Field;
import java.util.List;

public class InjectionUtils {

    public static void autowireIfAnotated(Class<?> classObject) throws ConfigurationException {

        Field[] fields = classObject.getDeclaredFields();
        for (Field currentField : fields) {
            if (currentField.isAnnotationPresent(Autowiring.class)) {
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

                Class<?> currentFieldClass = currentField.getType();
                Class<?> match = null;

                if (!currentField.getAnnotation(Autowiring.class).value().isEmpty()) {
                    Class<?> classInAnnotation = null;
                    try {
                        classInAnnotation = Class.forName(currentField.getAnnotation(Autowiring.class)
                                .value());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (GenericXmlApplicationContext.canInstantiate(currentFieldClass).test(classInAnnotation)) {
                        match = classInAnnotation;
                    } else {
                        throw new ConfigurationException("Class specified in annotation is not compatible with "
                                + currentFieldClass.getName()    + ".");
                    }

                } else {
                    if (!classes.stream().anyMatch(GenericXmlApplicationContext.canInstantiate(currentFieldClass))) {
                        throw new ConfigurationException("No suitable implementation for "
                                + currentFieldClass.getName()    + " found. Please check your configuration file.");
                    }

                    match = classes.stream().filter(GenericXmlApplicationContext.canInstantiate(currentFieldClass)).findFirst().get();

                    if (classes.stream().anyMatch(GenericXmlApplicationContext.canInstantiate(currentFieldClass)
                            .and(GenericXmlApplicationContext.isTheSameClassAs(match).negate()))) {
                        throw new ConfigurationException("Ambiguous configuration for "
                                + currentFieldClass.getName() + ". Please check your configuration file.");
                    }
                }

                try {
                    currentField.setAccessible(true);
                    currentField.set(null, match.newInstance());
                } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
