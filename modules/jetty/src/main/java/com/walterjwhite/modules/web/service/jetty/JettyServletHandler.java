package com.walterjwhite.modules.web.service.jetty;

import com.google.inject.servlet.GuiceFilter;
import com.walterjwhite.modules.web.service.core.handler.AbstractServletHandler;
import com.walterjwhite.modules.web.service.core.property.WebServerPort;
import com.walterjwhite.property.impl.annotation.Property;
import java.util.EnumSet;
import javax.inject.Inject;
import javax.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

// extend guice cli module ...
public class JettyServletHandler extends AbstractServletHandler {
  protected Server server;
  protected ServletContextHandler servletContextHandler;

  @Inject
  public JettyServletHandler(@Property(WebServerPort.class) int port /*,
      GuiceServletContextListener guiceServletContextListener*//*,
      ServletMappingConfiguration servletMappingConfiguration*/ ) {
    super(port /*, guiceServletContextListener*/ /*, servletMappingConfiguration*/);
  }

  @Override
  public void run() throws Exception {
    server = new Server(port);
    servletContextHandler = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);

    // min threads
    // max threads

    setupGuice();
    doConfigure();

    server.setStopAtShutdown(true);
    server.start();
    server.join();
  }

  protected void setupGuice() {
    servletContextHandler.addEventListener(guiceServletContextListener);
    servletContextHandler.addFilter(GuiceFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

    servletContextHandler.getInitParams().put("com.sun.jersey.api.json.POJOMappingFeature", "true");
    servletContextHandler
        .getInitParams()
        .put("com.sun.jersey.config.property.packages", "com.walterjwhite");
  }

  protected void doConfigure() {
    //    for (ServletMapping servletMapping : servletMappingConfiguration.getServletMappings()) {
    //      servletContextHandler.addServlet(
    //          servletMapping.getServletClass(), servletMapping.getUrlPattern());
    //    }
  }

  @Override
  public void shutdown() throws Exception {
    server.stop();
  }
}
