package org.dms.sunhilltechnicaltest.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dms.sunhilltechnicaltest.exceptions.AccountNotExistsException;
import org.dms.sunhilltechnicaltest.exceptions.DuplicateAccountException;
import org.dms.sunhilltechnicaltest.model.AbstractBankAccount;
import org.springframework.stereotype.Repository;

public class BankAccountRepository {
	
	private Map<Integer, AbstractBankAccount> accounts;
	
	public BankAccountRepository() {
		accounts = new ConcurrentHashMap<>();
	}
	
	public void addAccount(Integer id, AbstractBankAccount account) throws DuplicateAccountException {
		if(accounts.containsKey(id)) {
			throw new DuplicateAccountException();
		}
		accounts.put(id, account);
	}
	
	public void updateAccount(Integer id, AbstractBankAccount account) throws AccountNotExistsException {
		if(!accounts.containsKey(id)) {
			throw new AccountNotExistsException();
			
		}
		accounts.put(id, account);
	}
	
	public AbstractBankAccount getAccount(Integer id) throws AccountNotExistsException {
		if(!accounts.containsKey(id)) {
			throw new AccountNotExistsException();
			
		}
		return accounts.get(id);
	}
	
	public void deleteAccount(Integer id) throws AccountNotExistsException {
		if(!accounts.containsKey(id)) {
			throw new AccountNotExistsException();
		}
		accounts.remove(id);
	}
	
	public int size() {
		return accounts.size();
	}

}
