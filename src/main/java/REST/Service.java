package REST;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class Service {
    public static SqlContextable context;

    public static void main(String[] args) throws Exception {
        context = new SqlContext();

        ServletContextHandler context = new
                ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        Server jettyServer = new Server(9998);
        jettyServer.setHandler(context);
        ServletHolder jerseyServlet =
                context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
                ApiService.class.getCanonicalName());
        try {
            jettyServer.start();
            jettyServer.join();
            System.out.println("Server started");
        } finally {
            jettyServer.destroy();
        }
    }
}