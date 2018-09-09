package org.dms.sunhilltechnicaltest.Repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.dms.sunhilltechnicaltest.exceptions.AccountNotExistsException;
import org.dms.sunhilltechnicaltest.exceptions.DuplicateAccountException;
import org.dms.sunhilltechnicaltest.model.AbstractBankAccount;
import org.dms.sunhilltechnicaltest.model.SavingsAccount;
import org.junit.Test;

public class BankAccountRepositoryTest {

	@Test(expected = DuplicateAccountException.class)
	public void addAccountDuplicateAccountExceptionTest() throws DuplicateAccountException {
		BankAccountRepository repository = new BankAccountRepository();
		repository.addAccount(1, new SavingsAccount());
		repository.addAccount(1, new SavingsAccount());
	}
	
	@Test
	public void addAccountOKTest() throws DuplicateAccountException, AccountNotExistsException {
		BankAccountRepository repository = new BankAccountRepository();
		repository.addAccount(1, new SavingsAccount());
		assertNotNull(repository.getAccount(1));
	}
	
	@Test
	public void updateAccountOKTest() throws AccountNotExistsException, DuplicateAccountException {
		BankAccountRepository repository = new BankAccountRepository();
		
		SavingsAccount account1 = new SavingsAccount();
		account1.setOwner("owner1");
		SavingsAccount account2 = new SavingsAccount();
		account2.setOwner("owner2");		
		repository.addAccount(1, account1);
		repository.updateAccount(1, account2);
		assertEquals(repository.getAccount(1).getOwner(), "owner2");
	}
	
	@Test(expected = AccountNotExistsException.class)
	public void updateAccountAccountNotExistsTest() throws AccountNotExistsException, DuplicateAccountException {
		BankAccountRepository repository = new BankAccountRepository();
		
		SavingsAccount account1 = new SavingsAccount();
		account1.setOwner("owner1");
		SavingsAccount account2 = new SavingsAccount();
		account2.setOwner("owner2");		
		repository.addAccount(1, account1);
		repository.updateAccount(2, account2);
		assertEquals(repository.getAccount(1).getOwner(), "owner2");
	}
	
	@Test
	public void deleteAccountOKTest() throws DuplicateAccountException, AccountNotExistsException {
		BankAccountRepository repository = new BankAccountRepository();
		
		SavingsAccount account1 = new SavingsAccount();
		account1.setOwner("owner1");
		SavingsAccount account2 = new SavingsAccount();
		account2.setOwner("owner2");		
		repository.addAccount(1, account1);
		repository.addAccount(2, account2);
		repository.deleteAccount(2);
		assertEquals(repository.getAccount(1).getOwner(), "owner1");
		assertEquals(repository.size(), 1);
	}
	
	@Test(expected = AccountNotExistsException.class)
	public void deleteAccountAccountNotExistsExceptionTest() throws DuplicateAccountException, AccountNotExistsException {
		BankAccountRepository repository = new BankAccountRepository();
		
		SavingsAccount account1 = new SavingsAccount();
		account1.setOwner("owner1");
		SavingsAccount account2 = new SavingsAccount();
		account2.setOwner("owner2");		
		repository.addAccount(1, account1);
		repository.addAccount(2, account2);
		repository.deleteAccount(3);
	}
	
	
	@Test
	public void sizeTest() throws DuplicateAccountException {
		BankAccountRepository repository = new BankAccountRepository();
		
		SavingsAccount account1 = new SavingsAccount();
		account1.setOwner("owner1");
		SavingsAccount account2 = new SavingsAccount();
		account2.setOwner("owner2");		
		repository.addAccount(1, account1);
		repository.addAccount(2, account2);
		assertEquals(repository.size(), 2);
	}
	
}
