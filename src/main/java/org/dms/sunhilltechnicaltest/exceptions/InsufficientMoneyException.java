package org.dms.sunhilltechnicaltest.exceptions;

public class InsufficientMoneyException extends BankApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InsufficientMoneyException(String message) {
		super(message);
	}

}
