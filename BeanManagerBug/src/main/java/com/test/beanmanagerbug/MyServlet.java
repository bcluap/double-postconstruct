
package com.test.beanmanagerbug;

import test.beanmanagerlib.IMySingleton;
import java.io.IOException;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "MyServlet", urlPatterns = {"/MyServlet"}, loadOnStartup = 1)
public class MyServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MyServlet.class);

    @Inject
    private IMySingleton mySingletonInjected;
    
    @Override
    public void init() {
        mySingletonInjected.go();
        IMySingleton mySingletonNonInjected = getBean(IMySingleton.class);
        mySingletonNonInjected.go();
    }

    public <T> T getBean(Class<T> beanClass) {
        try {
            BeanManager bm = CDI.current().getBeanManager();
            Set<Bean<?>> beans = bm.getBeans(beanClass);
            if (beans != null && !beans.isEmpty()) {
                Bean<T> bean = (Bean<T>) beans.iterator().next();
                CreationalContext<T> ctx = bm.createCreationalContext(bean);
                return (T) bm.getContext(bean.getScope()).get(bean, ctx);
            } else {
                throw new Exception("Unknown bean class " + beanClass.getName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

