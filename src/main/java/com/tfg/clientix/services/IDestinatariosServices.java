package com.tfg.clientix.services;

import java.util.List;

import com.tfg.clientix.models.entity.Clientes;
import com.tfg.clientix.models.entity.Destinatarios;

public interface IDestinatariosServices {

	public List<Destinatarios> getAllDestinatarios();
	
	public Destinatarios readDestinatarios(Integer id);
	
	public Destinatarios insertDestinatarios (Destinatarios d);
	
	public  void  deletebyId(Integer id);
	
	public boolean consultarNIFExistente(String cifnif, int idcliente);
	
	//
	public List<Destinatarios> consultarDestinatariosIdCliente(Integer idCliente);
	
	public List<Destinatarios> getClienteNombre(String nombre);
	public List<Destinatarios> getDireccion(String direccion);
	public Destinatarios getDNI (String cifnif);

}
