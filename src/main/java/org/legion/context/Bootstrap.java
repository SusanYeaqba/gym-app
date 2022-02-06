package org.legion.context;

import lombok.extern.slf4j.Slf4j;
import org.legion.util.MainDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Slf4j
@WebListener
public class Bootstrap implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            log.info("<<<<<< initializing system context >>>>>>");

            String env = System.getenv("GYM_APP_ENV");
            log.info("RUNNING ENVIRONMENT: " + env);

            if (env == null || env.equals("null")) {
                throw new Exception("GYM_APP_ENV environment variable not defined, should be ['DEVELOPMENT','PRODUCTION']");
            } else if (env.equals("PRODUCTION")) {
            }


        } catch (Exception e) {
            log.error("Error during system context bootstrap", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            log.info(">>>>>> destroying system context <<<<<<");
            closeDataSources();
        } catch (Exception e) {
            log.error("Error during system context destroy", e);
        }
    }

    private void closeDataSources() throws Exception {
        if (MainDataSource.getDs() != null)
            MainDataSource.getDs().close();
    }
}
