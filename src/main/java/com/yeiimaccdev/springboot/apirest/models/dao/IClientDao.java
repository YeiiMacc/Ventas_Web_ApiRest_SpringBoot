package com.yeiimaccdev.springboot.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.yeiimaccdev.springboot.apirest.models.entity.Client;

public interface IClientDao extends CrudRepository<Client, Long> {

}
