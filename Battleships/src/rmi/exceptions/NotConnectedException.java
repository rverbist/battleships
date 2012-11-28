package rmi.exceptions;

@SuppressWarnings("serial")
public class NotConnectedException extends RuntimeException
{
    public NotConnectedException()
    {
        super("No connection to the server available.");
    }
}
