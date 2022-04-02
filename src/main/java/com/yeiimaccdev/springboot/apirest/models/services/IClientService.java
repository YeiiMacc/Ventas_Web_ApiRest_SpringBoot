package com.yeiimaccdev.springboot.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yeiimaccdev.springboot.apirest.models.entity.Client;

public interface IClientService {
	public List<Client> findAll();

	public Page<Client> findAll(Pageable pageable);
	
	public Client findById(Long id);
	
	public Client save(Client client);
	
	public Client update(Client client);
	
	public void delete(Long id);
}
