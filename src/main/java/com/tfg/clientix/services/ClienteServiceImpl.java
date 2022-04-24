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

	@Override
	@Transactional(readOnly = true)
	public Clientes getClienteid(Integer id) {
			return clienteDao.findById(id).orElse(null);
	}



	@Override
	public void deletebyId(Integer id) {
		clienteDao.deleteById(id);
		
	}

	@Override
	public List<Clientes> getClientesPaginacion() {
		//Esto en un futuro hay que hacerlo
		return null;
	}
	

	
	
	

}
