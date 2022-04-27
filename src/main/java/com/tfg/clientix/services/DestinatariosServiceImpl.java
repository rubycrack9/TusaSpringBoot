package com.tfg.clientix.services;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.clientix.models.dao.IDestinatariosDao;
import com.tfg.clientix.models.entity.Destinatarios;

public class DestinatariosServiceImpl implements IDestinatariosServices {

	@Autowired
	private IDestinatariosDao destinatariosDao;
	private Destinatarios d;
	@Autowired
	private EntityManager entityManager;
	
	
	@Override
	public List<Destinatarios> getAllDestinatarios() {
		// TODO Auto-generated method stub
		return (List<Destinatarios>) destinatariosDao.findAll();
	}

	@Override
	public Destinatarios insertDestinatarios(Destinatarios d) {
		// TODO Auto-generated method stub
		return destinatariosDao.save(d);
	}


	@Override
	@Transactional(readOnly = true)
	public Destinatarios readDestinatarios(Integer id) {
		return destinatariosDao.findById(id).orElse(null);
	}

	
	@Override
	public void deletebyId(Integer id) {
		destinatariosDao.deleteById(id);
		
	}

}
