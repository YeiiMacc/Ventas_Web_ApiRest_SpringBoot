package com.yeiimaccdev.springboot.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yeiimaccdev.springboot.apirest.models.entity.Client;

public interface IClientDao extends JpaRepository<Client, Long> {

}
