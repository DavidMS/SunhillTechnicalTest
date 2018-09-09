package org.dms.sunhilltechnicaltest.exceptions;

public class AccountNotExistsException extends BankApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountNotExistsException() {
		super("The account you are trying to update does not exists.");
	}

}
