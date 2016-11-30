package application.classes.Inspectors;

import framework.core.inspectors.Interceptor;

public class CapitalizingInterceptor implements Interceptor {

    @Override
    public String interceptOutputString(String interceptedString) {
        return interceptedString.toUpperCase();
    }
}
