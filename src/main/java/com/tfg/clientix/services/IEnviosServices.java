package com.tfg.clientix.services;

import java.util.List;

import com.tfg.clientix.models.entity.Envios;

public interface IEnviosServices {
	
	public List<Envios> getEnvios();
	
	public Envios insertEnvios (Envios c);

	//public Envios getEnvioId(Integer id);
	
	public  void  deletebyId(Integer id);

	public boolean consultarNIFExistenteparaesecliente(String dninif, int idcliente);

	public List<Object> getEstadoEnvioPorId(Integer id);

}
