package com.msfinancial.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msfinancial.model.AccountFinancialInstituition;
import com.msfinancial.model.FinancialInstituition;
import com.msschemas.dto.AccountFinancialInstituitionDTO;

@Component
public class AccountFinancialInstituitionConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public List<AccountFinancialInstituitionDTO> toCollectionDto(List<AccountFinancialInstituition> accounts) {
		return accounts.stream()
				.map(account -> toDto(account))
				.collect(Collectors.toList());
	}
	
	public AccountFinancialInstituitionDTO toDto(AccountFinancialInstituition account) {
		return modelMapper.map(account, AccountFinancialInstituitionDTO.class);
	}
	
	public AccountFinancialInstituition toEntity(AccountFinancialInstituitionDTO accountDTO) {
		return modelMapper.map(accountDTO, AccountFinancialInstituition.class);
	}
	
	public void copyToEntity(AccountFinancialInstituitionDTO accountDTO, AccountFinancialInstituition account) {
		account.setFinancialInstituition(new FinancialInstituition());

		modelMapper.map(accountDTO, account);
	}
}
