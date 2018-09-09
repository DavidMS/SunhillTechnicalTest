package org.dms.sunhilltechnicaltest.factory;

import org.dms.sunhilltechnicaltest.Repository.BankAccountRepository;
import org.dms.sunhilltechnicaltest.model.AbstractBankAccount;
import org.dms.sunhilltechnicaltest.model.CheckingsAccount;
import org.dms.sunhilltechnicaltest.model.SavingsAccount;

public class BankAccountFactory {

	public static AbstractBankAccount getBankAccount(AccountType type, BankAccountRepository repository) {
		AbstractBankAccount result = null;
		switch (type) {
		case SAVINGS:
			result = new SavingsAccount();
			break;
		case CHECKING:
			result = new CheckingsAccount(repository);
			break;
		default:
			break;
		}
		return result;
	}
}
