package com.tfg.clientix.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.clientix.models.dao.IClienteDao;
import com.tfg.clientix.models.entity.Clientes;

@Service
public class ClienteServiceImpl implements IClienteServices {

	@Autowired
	private IClienteDao clienteDao;
	private Clientes c;
	@Autowired
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
		//Lo vamos a hacer en angular
		return null;
	}
                 
	public boolean consultarNIFExistente(String cifnif) {
		List<Clientes> listaCIFNIF = new ArrayList<Clientes>() ;
		Clientes clienteRecuperado = null;
		boolean existe = false;
		try {
			Query consultaNif = entityManager.createQuery("SELECT u FROM clientes u WHERE u.CIFNIF=: CIFNIF");
			consultaNif.setParameter("CIFNIF", cifnif);
			clienteRecuperado = (Clientes)consultaNif.getSingleResult();
			if(clienteRecuperado == null) {
				return existe = false;
			}else {
				listaCIFNIF.add(clienteRecuperado);
				for (Clientes clientes : listaCIFNIF) {
					if(clientes.getCIFNIF().equals(cifnif)) {
						existe = true;
						break;
					}else {
			        	existe = false;
			        }
				}
			}	
			return existe;
		}catch (Exception e) {
			return existe;
		}
		
	}


}
