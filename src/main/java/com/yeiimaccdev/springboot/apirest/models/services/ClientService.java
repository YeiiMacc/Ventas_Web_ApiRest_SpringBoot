package com.yeiimaccdev.springboot.apirest.models.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yeiimaccdev.springboot.apirest.models.dao.IClientDao;
import com.yeiimaccdev.springboot.apirest.models.entity.Client;

@Service
public class ClientService implements IClientService {

	@Autowired
	private IClientDao clientDao;

	@Override
	@Transactional(readOnly = true)
	public List<Client> findAll() {
		return (List<Client>) clientDao.findAll();
	}

	@Override
	public Client findById(Long id) {
		return clientDao.findById(id).orElse(null);
	}

	@Override
	public Client save(Client client) {
		client.setCreatedAt(new Date());
		client.setUpdatedAt(new Date());
		return clientDao.save(client);
	}

	@Override
	public Client update(Client client) {

		if (client.getId() != null) {
			Client newClient = this.findById(client.getId());

			if (newClient != null) {
				newClient.setFirstName(client.getFirstName());
				newClient.setLastName(client.getLastName());
				newClient.setEmail(client.getEmail());
				
				newClient.setUpdatedAt(new Date());

				return clientDao.save(newClient);
			}

			return client;
		}
		return client;
	}

	@Override
	public void delete(Long id) {
		clientDao.deleteById(id);
	}

}
