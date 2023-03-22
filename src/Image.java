import java.io.Serializable;

public class Image implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7120417568051829953L;
	String png;
	String svg;
}
class FlagImage extends Image{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3651246021392196063L;
	String alt;
}