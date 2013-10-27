package asu.edu.sbs.exception;

public class BankAccessException extends Exception {

	private static final long serialVersionUID = -6019469278891056730L;

	public BankAccessException() {
		super();
	}

	public BankAccessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BankAccessException(String arg0) {
		super(arg0);
	}

	public BankAccessException(Throwable arg0) {
		super(arg0);
	}

}
