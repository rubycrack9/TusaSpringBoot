package com.tfg.clientix.services;

import java.sql.SQLException;
import java.util.List;

import com.tfg.clientix.models.entity.Envios;

public interface IEnviosServices {
	
	public List<Envios> getEnvios();
	
	public Envios insertEnvios (Envios c);

	//public Envios getEnvioId(Integer id);
	
	public  void  deletebyId(Integer id);

	public boolean consultarNIFExistenteparaesecliente(String dninif, int idcliente);

	public List<Envios> getEstadoEnvioPorId(Integer id);
	
	public List<Envios> getEnviosCliente(Integer id) throws SQLException;
	
	public List<Envios> getEnviosADestinatario(Integer id) throws SQLException;

}
