package com.tfg.clientix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import com.tfg.clientix.models.dao.IClienteDao;
import com.tfg.clientix.models.entity.Clientes;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;

@Service
public class ClienteServiceImpl implements IClienteServices {

	@Autowired
	private IClienteDao clienteDao;
	private Clientes c;

	@Override
	public List<Clientes> getClientes() {
		return (List<Clientes>) clienteDao.findAll();
	}

	@Override
		public Clientes insert(Clientes c) {
		return clienteDao.save(c);
	}
	
	/*@Override
	public Clientes borrarclienteId(Integer id)
	{
		return clienteDao.deleteById(id);
	}*/
	

	
	/*@Override
	public Clientes update(Clientes c, Integer id) {
	Clientes  cliente_actual = clienteDao.findById(id).orElse(null);
	cliente_actual.setCIFNIF(c.getCIFNIF());
	cliente_actual.setDireccionFacturacion(c.getDireccionFacturacion());
	cliente_actual.setNombreCliente(c.getNombreCliente());
	return cliente_actual;
	}*/

	@Override
	@Transactional(readOnly = true)
	public Clientes getClienteid(Integer id) {
			return clienteDao.findById(id).orElse(null);
	}

	
	
	
	

}
