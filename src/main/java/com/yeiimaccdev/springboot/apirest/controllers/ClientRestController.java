package com.yeiimaccdev.springboot.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
