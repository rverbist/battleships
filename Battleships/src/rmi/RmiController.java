package rmi;

import java.net.URL;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;

/**
 * a client/server controller that utilizes java rmi
 * @author rverbist
 * @param <TInterface> the interface that the controller will expose through rmi
 */
public abstract class RmiController<TInterface extends Remote>
{
    protected final String _host;
    protected final String _resource;
    protected final int _port;

    /**
     * creates a new java rmi controller on host:port/resource.
     * @param host the host name of the controller
     * @param port the port of the controller
     * @param resource the java rmi resource name of the controller
     */
    protected RmiController(final String host, final int port, final String resource)
    {
        _host = host;
        _port = port;
        _resource = resource;

        final URL codebase = getClass().getProtectionDomain().getCodeSource().getLocation();
        final URL policy = getClass().getResource("no.policy");

        System.setProperty("java.rmi.server.codebase", codebase.toString());
        System.setProperty("java.security.policy", policy.toString());

        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new RMISecurityManager());
        }
    }

    /**
     * gets the host name
     * @return the name of the host
     */
    public String getHost()
    {
        return _host;
    }

    /**
     * gets the resource name
     * @return the java rmi resource name
     */
    public String getResource()
    {
        return _resource;
    }

    /**
     * gets the port
     * @return the port
     */
    public int getPort()
    {
        return _port;
    }

    /**
     * gets the interface
     * @return the interface that this controller exposes
     */
    public abstract TInterface getInterface();
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return String.format("%s:%s/%s", getHost(), getPort(), getResource());
    }
}
