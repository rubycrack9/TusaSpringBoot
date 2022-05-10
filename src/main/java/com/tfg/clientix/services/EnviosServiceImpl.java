package com.tfg.clientix.services;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.clientix.models.dao.IEnviosDao;
import com.tfg.clientix.models.entity.Envios;

@Service
public class EnviosServiceImpl implements IEnviosService{
	
	@Autowired
	private IEnviosDao enviosDao;
	private Envios e;
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Envios> getEnvios() {
		// TODO Auto-generated method stub
		return (List<Envios>) enviosDao.findAll();
	}

	@Override
	public Envios insertEnvios(Envios e) {
		// TODO Auto-generated method stub
		return enviosDao.save(e);
	}

	@Override
	public Envios getEnvioId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletebyId(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
