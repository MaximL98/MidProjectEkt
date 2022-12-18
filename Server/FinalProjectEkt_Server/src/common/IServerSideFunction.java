package common;

/*
 * This interface needs to be implemented according to the devised protocol:
 * For every client-side action that requires server communication, 
 * one must fit a proper function (that does what the server needs to do for that action)
 * inside the dedicated server-side static hash-map. 
 * 
 * 
 */

public interface IServerSideFunction {
	SCCP handleMessage(SCCP message);	
}
