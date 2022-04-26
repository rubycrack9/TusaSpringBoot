package com.tfg.clientix.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.clientix.errorCharger.CodigosErrorRest;
import com.tfg.clientix.models.dao.IClienteDao;
import com.tfg.clientix.models.entity.Clientes;

@Service
public class ClienteServiceImpl implements IClienteServices {

	@Autowired
	private IClienteDao clienteDao;
	private Clientes c;
	private EntityManager entityManager;

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
                 
	public boolean consultarNIFExistente(String cifnif) {
		List<Clientes> listaCIFNIF = new ArrayList<Clientes>() ;
		boolean existe = true;
		
		TypedQuery<Clientes> consultaNif = entityManager.createNamedQuery("clientes.CIFNIF", Clientes.class);
		listaCIFNIF = consultaNif.setParameter("CIFNIF",cifnif).getResultList();
		
		for (Clientes clientes : listaCIFNIF) {
			if(clientes.getCIFNIF().equals(cifnif)) {
				existe = true;
				break;
			}else {
	        	existe = false;
	        }
		}
		return existe;
	}


}
