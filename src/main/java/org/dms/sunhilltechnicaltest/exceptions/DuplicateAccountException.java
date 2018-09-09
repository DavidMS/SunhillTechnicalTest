package org.dms.sunhilltechnicaltest.exceptions;

public class DuplicateAccountException extends BankApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateAccountException() {
		super("This account already exists, please use update method for updating an existing account");
	}

}
