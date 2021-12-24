package com.msfinancial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.msfinancial.converter.AccountFinancialInstituitionConverter;
import com.msfinancial.model.AccountFinancialInstituition;
import com.msfinancial.model.FinancialInstituition;
import com.msfinancial.repository.AccountFinancialInstituitionRepository;
import com.msschemas.dto.AccountFinancialInstituitionDTO;
import com.msschemas.exception.EntityInUseException;
import com.msschemas.exception.EntityNotFoundException;

@Service
public class AccountFinancialInstituitionService {

	@Autowired
	private AccountFinancialInstituitionRepository accountRepository;
	
	@Autowired
	private FinancialInstituitionService financialInstituitionService;
	
	@Autowired
	private AccountFinancialInstituitionConverter accountConverter;
	
	public List<AccountFinancialInstituition> getAll() {
		return accountRepository.findAll();
	}
	
	public AccountFinancialInstituition findById(Long idAccount) {
		return getOrThrowException(idAccount);
	}
	
	public List<AccountFinancialInstituition> getAllByIdUser(Long idUser) {
		return accountRepository.findAllByIdUser(idUser);
	}

	public AccountFinancialInstituition create(AccountFinancialInstituition account) {
//		User user = userService.getOrThrowException(account.getClient().getId());
		//TODO: Fazer a validacao do client que está em outro microservico
		
		FinancialInstituition financialInstituition = financialInstituitionService.getOrThrowException(account.getFinancialInstituition().getId());
		
		account.setFinancialInstituition(financialInstituition);
		
		return accountRepository.save(account);
	}
	
	public AccountFinancialInstituition update(Long idAccount, AccountFinancialInstituitionDTO accountDTO) {
		AccountFinancialInstituition storedAccount = getOrThrowException(idAccount);
		accountDTO.setId(idAccount);
		accountDTO.setCreatedAt(storedAccount.getCreatedAt());
		accountConverter.copyToEntity(accountDTO, storedAccount);

		return create(storedAccount);
	}
	
	public void remove(Long idAccount) {
		try {
			accountRepository.deleteById(idAccount);
		
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Account not found");
			
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException("Account can't be removed.");
		}
	}
	
	public AccountFinancialInstituition getOrThrowException(Long idAccount) {
		return accountRepository.findById(idAccount).orElseThrow(() -> new EntityNotFoundException("Account not found!"));
	}
}
