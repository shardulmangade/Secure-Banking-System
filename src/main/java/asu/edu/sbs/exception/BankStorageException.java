package asu.edu.sbs.exception;


public class BankStorageException extends Exception {

	
	private static final long serialVersionUID = -3850218568287768164L;
	
	/**
	 * default storage exception
	 */
	public BankStorageException() {
		super();
	}
	
	/**
	 * Custom message in the exception
	 * @param customMsg
	 */
	public BankStorageException(String customMsg) {
		super(customMsg);
	}


	
	public BankStorageException(Exception e)
	{
		super(e);
	}


	public BankStorageException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	

}
