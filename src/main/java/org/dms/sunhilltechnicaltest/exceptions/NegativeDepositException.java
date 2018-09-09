package org.dms.sunhilltechnicaltest.exceptions;

public class NegativeDepositException extends BankApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NegativeDepositException(String message) {
		super(message);
	}

}
