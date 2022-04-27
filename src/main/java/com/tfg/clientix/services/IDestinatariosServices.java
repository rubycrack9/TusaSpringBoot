package com.tfg.clientix.services;

import java.util.List;

import com.tfg.clientix.models.entity.Destinatarios;

public interface IDestinatariosServices {

	public List<Destinatarios> getAllDestinatarios();
	
	public Destinatarios readDestinatarios(Integer id);
	
	public Destinatarios insertDestinatarios (Destinatarios d);
	
	public  void  deletebyId(Integer id);
	

}