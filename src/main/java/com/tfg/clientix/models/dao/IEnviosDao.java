package com.tfg.clientix.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.tfg.clientix.models.entity.Envios;

public interface IEnviosDao extends JpaRepository<Envios, Integer>{
	
	public List<Envios> findbyidCliente(int idCliente);
	public List<Envios> findbyidDestinatario(int idDestinatario);

}
