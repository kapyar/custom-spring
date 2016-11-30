package framework.core.xmlbean;

import java.util.List;

import framework.parsers.entities.Bean;
import framework.parsers.SaxParser;

public class XmlBeanDefinitionReader {
    
    public static enum ParserTypes {DOM, SAX, StAX};
    
    private List<Bean> beanList;
    private List<Bean> interceptorList;
    private ParserTypes parserType;
    private boolean validating;
    private String packageName;
    
    public XmlBeanDefinitionReader() {
        parserType = ParserTypes.SAX;
        validating = false;
    }
    
    public List<Bean> getBeanList() {
        return beanList;
    }
    
    public List<Bean> getInterceptorList() {
        return interceptorList;
    }
    
    public boolean getValidating() {
        return validating;
    }    
    
    public void setValidating(boolean validating) {
        this.validating = validating;
    }
    
    public void setParserType(ParserTypes parserType) {
        this.parserType = parserType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void loadBeanDefinitions(String fileName) {
        
        switch (parserType) {
            case SAX:
                beanList        = new SaxParser(fileName).getBeanList();
                interceptorList = new SaxParser(fileName).getInterceptorList();
                packageName     = new SaxParser(fileName).getPackageToScan();

                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
