import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

class NativeName implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1846211453819501110L;
	String common;
	String official;	
}
public class Name extends NativeName{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6676817997883107402L;
	HashMap<String, NativeName> nativeName;
}