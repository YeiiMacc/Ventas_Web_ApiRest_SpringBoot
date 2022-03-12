package com.yeiimaccdev.springboot.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yeiimaccdev.springboot.apirest.models.services.IClientService;
import com.yeiimaccdev.springboot.apirest.models.entity.Client;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = {"http://localhost:4200/"})
public class ClientRestController {

	@Autowired
	private IClientService clientService;
	
	@GetMapping("") 
	public List<Client> index() {
		return clientService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Client client = null;
		Map<String, Object> response= new HashMap<>();
		
		try {
			client = clientService.findById(id);
		} catch (DataAccessException e) {
			response.put("messagge", "Query error.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(client == null) {
			response.put("messagge", "Error - Client with ID:".concat(id.toString().toString()).concat(" not found"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Client>(client, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	@ResponseStatus(HttpStatus.CREATED)
	public Client create(@RequestBody Client client) {
		return clientService.save(client);
	}
	
	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Client update(@RequestBody Client client) {
		return clientService.update(client);
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clientService.delete(id);
	}
}
