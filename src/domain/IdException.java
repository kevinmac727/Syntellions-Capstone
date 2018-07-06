package domain;

//Class derived from Exception
public class IdException extends Exception{
    /**
	 * 
	 */
    //Static constant long, used by JVM to check if a serialized class matches this one
	private static final long serialVersionUID = 2689695810932204301L;

        //Constructor calls base constructor
	public IdException(String msg){
        super(msg);
     }

}
