package rmi;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public final class RmiClient<TInterface extends Remote> extends RmiController<TInterface>
{
    private TInterface _proxy;

    public RmiClient(final String host, final int port, final String resource) throws RemoteException, NotBoundException
    {
        super(host, port, resource);
    }

    @SuppressWarnings("unchecked")
    public void bind() throws RemoteException, NotBoundException
    {
        final Registry registry = LocateRegistry.getRegistry(getHost(), getPort());
        _proxy = (TInterface) registry.lookup(getResource());
    }
    
    public void unbind()
    {
    }

    @Override
    public TInterface getInterface()
    {
        return _proxy;
    }
}
