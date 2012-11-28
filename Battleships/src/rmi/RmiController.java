package rmi;

import java.net.URL;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;

public abstract class RmiController<TInterface extends Remote>
{
    protected final String _host;
    protected final String _resource;
    protected final int _port;

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

    public String getHost()
    {
        return _host;
    }

    public String getResource()
    {
        return _resource;
    }

    public int getPort()
    {
        return _port;
    }

    public abstract TInterface getInterface();
    
    public String toString()
    {
        return String.format("%s:%s/%s", getHost(), getPort(), getResource());
    }
}
