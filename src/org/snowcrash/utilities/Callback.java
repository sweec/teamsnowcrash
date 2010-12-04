package org.snowcrash.utilities;


/**
 * 
 * @author Mike
 * 
 * Interface for allowing callback methods.  If a method makes this available as 
 * a parameter, it should be sure to execute the callback after it has finished 
 * executing.  Clients are responsible for implementing the interface as they see 
 * fit, usually by redirecting to the appropriate method in their classes.
 * 
 * 26 Oct - Created.
 * 
 */
public interface Callback
{
	public void callback( Object ... results );
}
