package com.tfg.clientix.services;
import com.tfg.clientix.models.entity.Clientes;

import java.util.List;
public interface IClienteServices {
	
	public List<Clientes> getClientes();
	
	public Clientes insert (Clientes c);
	
	//public Clientes update (Clientes c, Integer id);

	public Clientes getClienteid(Integer id);
	
}
