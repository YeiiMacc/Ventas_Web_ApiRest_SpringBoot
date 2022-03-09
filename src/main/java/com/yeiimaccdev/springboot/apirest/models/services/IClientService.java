package com.yeiimaccdev.springboot.apirest.models.services;

import java.util.List;

import com.yeiimaccdev.springboot.apirest.models.entity.Client;

public interface IClientService {
	public List<Client> findAll();
	
	public Client findById(Long id);
	
	public Client save(Client client);
	
	public Client update(Client client);
	
	public void delete(Long id);
}
