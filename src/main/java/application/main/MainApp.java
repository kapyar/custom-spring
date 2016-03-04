package application.main;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import application.classes.Bus;
import application.classes.Car;
import application.classes.GreetingService;
import application.classes.Transport;

import framework.core.BeanFactory;
import framework.core.GenericXmlApplicationContext;
import framework.core.XmlBeanDefinitionReader.ParserTypes;

public class MainApp {
	public static void inspectObjectViaReflection(Object o, String name) {
		System.out.println("Inspecting object " + name + ":");
		System.out.println("Class name: " + o.getClass().getName());
		System.out.println("Methods:");
		
		for (Method m : o.getClass().getMethods()) {
			int methodModifiers = m.getModifiers();
			StringBuilder methodInfo = new StringBuilder();
			methodInfo.append("\t* ");
			
			if (Modifier.isPublic(methodModifiers)) {
				methodInfo.append("public ");
			}
			if (Modifier.isProtected(methodModifiers)) {
				methodInfo.append("protected ");
			}
			if (Modifier.isPrivate(methodModifiers)) {
				methodInfo.append("private ");
			}
			if (Modifier.isFinal(methodModifiers)) {
				methodInfo.append("final ");
			}
			if (Modifier.isAbstract(methodModifiers)) {
				methodInfo.append("abstract ");
			}
			if (Modifier.isStatic(methodModifiers)) {
				methodInfo.append("static ");
			}
			if (Modifier.isSynchronized(methodModifiers)) {
				methodInfo.append("synchronized ");
			}
			
			//In order to get formal method parameters names via reflection, they must be present in .class file.
			//By default Java compiler discards this information, 
			//so you'll get arg0, arg1... instead of real informative names.
			//In order to have the method parameters names included in the .class files,
			//you must compile .java files with -parameters compile option.
			//To do this in Eclipse, go to Window > Preferences > Java > Compiler and
			//check the box 'Store information about method parameters (usable via reflection)'
			methodInfo.append(m.getReturnType().getSimpleName() + " " + m.getName() + "(");
			if (m.getParameterCount() > 0) {
				for (Parameter p : m.getParameters()) {
					if (Modifier.isFinal(p.getModifiers())) {
						methodInfo.append("final ");
					}
					methodInfo.append(p.getType().getSimpleName());
					
					if (p.isVarArgs()) {
						methodInfo.delete(methodInfo.length() - 2, methodInfo.length()).append("...");
					}
					methodInfo.append(" " + p.getName() + ", ");
				}
				methodInfo.delete(methodInfo.length() - 2, methodInfo.length());
			}
			methodInfo.append(")");
			
			System.out.println(methodInfo.toString());
		}
	}
	
	public static void main(String[] args) {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.load(MainApp.class.getResource("/GS_SpringXMLConfig.xml").getPath());
		context.setValidating(true);
		context.setParserType(ParserTypes.SAX);

		BeanFactory factory = context.getBeanFactory();

		GreetingService greetingService = 
				(GreetingService) factory.getBean("greetingService", GreetingService.class);
		System.out.println(greetingService.getMessage());
		
		Transport bus = 
				(Bus) factory.getBean("bus", Transport.class);
		bus.getTransport();
		
		Transport bus2 = 
				(Bus) factory.getBean("bus2", Transport.class);
		bus2.getTransport();
		
		Transport car = 
				(Car) factory.getBean("car", Transport.class);
		System.out.println(car.toString());
		
		//================
		//================ REFLECTION API DEMO
		//================
		System.out.println();
		System.out.println("================ REFLECTION API DEMO ================");
		System.out.println();
		
		inspectObjectViaReflection(bus, "bus");
		System.out.println("==========");
		inspectObjectViaReflection(bus.toString(), "String representation of the 'bus' object");
	}
}