package com.tfg.clientix.services;

import java.util.List;

import com.tfg.clientix.models.entity.Envios;

public interface IEnviosService {
	
	public List<Envios> getEnvios();
	
	public Envios insert (Envios c);

	public Envios getEnvioId(Integer id);
	
	public  void  deletebyId(Integer id);

}
