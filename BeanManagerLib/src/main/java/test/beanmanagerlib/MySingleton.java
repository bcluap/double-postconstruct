/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.beanmanagerlib;

import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Paul Carter-Brown
 */
@Singleton
public class MySingleton implements IMySingleton{
    
    private static final Logger log = LoggerFactory.getLogger(MySingleton.class);
    private static final String uuid = UUID.randomUUID().toString();
    private static boolean initDone = false;
    
    public MySingleton() {
        log.warn("In constructor [{}]", uuid);
    }
    
    @PostConstruct
    private void init() {
        if (initDone) {
            log.error("DOUBLE INIT !!! [{}]", uuid, new Exception());
            return;
        }
        initDone = true;
        log.warn("In first init [{}]", uuid, new Exception());
    }

    @Override
    public void go() {
         log.warn("In go [{}]", uuid);
    }
    
}
