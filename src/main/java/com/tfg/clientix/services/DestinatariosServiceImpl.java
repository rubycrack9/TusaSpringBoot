package com.tfg.clientix.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.clientix.models.dao.IDestinatariosDao;
import com.tfg.clientix.models.entity.Clientes;
import com.tfg.clientix.models.entity.Destinatarios;

@Service
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

	@Override
	public boolean consultarNIFExistente(String dninif, int idcliente) {
		List<Destinatarios> listaCIFNIF = new ArrayList<Destinatarios>() ;
		Destinatarios destinatarioRecuperado = null;
		boolean existe = false;
		try {
			Query consultaNif = entityManager.createQuery("SELECT d FROM destinatarios d,clientes c WHERE c.idCliente=d.idcliente AND c.idCliente=: idcliente AND d.DNINIF=: DNINIF");
			consultaNif.setParameter("DNINIF", dninif);
			consultaNif.setParameter("idcliente", idcliente);
			destinatarioRecuperado = (Destinatarios)consultaNif.getSingleResult();
			if(destinatarioRecuperado == null) {
				return existe = false;
			}else {
				listaCIFNIF.add(destinatarioRecuperado);
				for (Destinatarios destinatario : listaCIFNIF) {
					if(destinatario.getDNINIF().equals(dninif)) {
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
