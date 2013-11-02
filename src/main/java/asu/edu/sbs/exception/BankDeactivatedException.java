package asu.edu.sbs.exception;

public class BankDeactivatedException extends Exception {

	private static final long serialVersionUID = -6019469278891056730L;

	public BankDeactivatedException() {
		super();
	}

	public BankDeactivatedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BankDeactivatedException(String arg0) {
		super(arg0);
	}

	public BankDeactivatedException(Throwable arg0) {
		super(arg0);
	}

}
