package com.yeiimaccdev.springboot.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
			response.put("message", "Error in data query.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(client == null) {
			response.put("message", "Error - Client with ID:".concat(id.toString().toString()).concat(" not found"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Client>(client, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> create(@Valid @RequestBody Client client, BindingResult result) {
		Client newClient = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "Field '"+ err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			newClient = clientService.save(client);
		} catch (DataAccessException e) {
			response.put("message", "Error inserting data");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		response.put("message", "New Client Created!");
		response.put("client", newClient);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Client client, BindingResult result , @PathVariable Long id) {
		Client dataClient = clientService.findById(id);
		Client UpdatedClient = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "Field '"+ err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(dataClient == null) {
			response.put("message", "Error - Cannot be edited, Client with ID: ".concat(id.toString().toString()).concat(" not found"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			dataClient.setFirstName(client.getFirstName());
			dataClient.setLastName(client.getLastName());
			dataClient.setEmail(client.getEmail());
			
			UpdatedClient = clientService.save(dataClient);
		} catch (DataAccessException e) {
			response.put("message", "Error updating data");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		response.put("message", "Client Updated!");
		response.put("client", UpdatedClient);
				
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			clientService.delete(id);
		} catch (DataAccessException e) {
			response.put("message", "Error deleting data");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
		response.put("message", "Client deleted!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
