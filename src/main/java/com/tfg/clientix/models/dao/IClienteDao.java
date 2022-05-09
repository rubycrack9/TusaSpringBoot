package com.tfg.clientix.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.clientix.models.entity.Clientes;

public interface IClienteDao extends CrudRepository<Clientes, Integer> {

}
