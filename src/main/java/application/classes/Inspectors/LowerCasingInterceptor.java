package application.classes.Inspectors;

import framework.core.inspectors.Interceptor;

public class LowerCasingInterceptor implements Interceptor {

    @Override
    public String interceptOutputString(String interceptedString) {        
        return interceptedString.toLowerCase();
    }

}
