package org.dms.sunhilltechnialtest.factory;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.dms.sunhilltechnicaltest.Repository.BankAccountRepository;
import org.dms.sunhilltechnicaltest.factory.AccountType;
import org.dms.sunhilltechnicaltest.factory.BankAccountFactory;
import org.dms.sunhilltechnicaltest.model.AbstractBankAccount;
import org.dms.sunhilltechnicaltest.model.CheckingsAccount;
import org.dms.sunhilltechnicaltest.model.SavingsAccount;
import org.junit.Test;

public class BankAccountFactoryTests {

	@Test
	public void getBankAccountTest() {
		BankAccountRepository repository = new BankAccountRepository();
		AbstractBankAccount account1 = BankAccountFactory.getBankAccount(AccountType.CHECKING, repository);
		AbstractBankAccount account2 = BankAccountFactory.getBankAccount(AccountType.SAVINGS, repository);
		
		assertThat(account1, instanceOf(CheckingsAccount.class));
		assertThat(account2, instanceOf(SavingsAccount.class));
	}
}
