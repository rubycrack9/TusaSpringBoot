package com.tfg.clientix.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.clientix.models.dao.IEnviosDao;
import com.tfg.clientix.models.entity.Destinatarios;
import com.tfg.clientix.models.entity.Envios;

@Service
public class EnviosServiceImpl implements IEnviosServices{
	
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
	public List<Envios> getEstadoEnvioPorId(Integer id) {
		List<Envios> listaEnvios = new ArrayList<Envios>();
		List<Object> estadoamostrar = new ArrayList<Object>();
		Envios enviorecuperado = null;
		String estadoenvio = null;
		try {
			Query consultaEstado = entityManager.createQuery("SELECT u FROM envios u WHERE u.idEnvio=: idEnvio");
			consultaEstado.setParameter("idEnvio", id);
			enviorecuperado = (Envios)consultaEstado.getSingleResult();
			if (enviorecuperado == null) {
				System.err.println("NO EXISTE ESE ID DE ENVIO");
			}else {
				listaEnvios.add(enviorecuperado);
			}
			if(enviorecuperado.getIdEnvio() == 1)
			{
				estadoenvio = "ENTREGADO AL DESTINATARIO";
			}
			else if (enviorecuperado.getIdEnvio() == 2)
			{estadoenvio = "EN OFICINA";}
			else 
			{
				estadoenvio = "EN LA OFICINA DE ENTREGA";
			}
			for (Envios envio : listaEnvios) {
				envio.setIdEstadoEnvio(estadoenvio);	
				}
			//estadoamostrar.add(enviorecuperado.getNumIntentosEntrega());
			
		} catch (Exception e) {
			e.getMessage();
		}
		return listaEnvios;
	}

	@Override
	public void deletebyId(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean consultarNIFExistenteparaesecliente(String dninif, int idcliente) {
		boolean existe = false;
		try {
			 Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/clientix","root","");
			 String SQL = "select d.DNINIF from destinatarios d , clientes c where c.idCliente = d.idcliente and c.idCliente = "+idcliente+" and d.DNINIF = '"+dninif+"'";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(SQL);
			 if(rs.next()) {
				 String nif = rs.getString("DNINIF");
				 
				 if(nif.equals(dninif)) {
					 existe = true;
				 }else {
					 existe = false;
				 }
			 } 
			return existe;
		}catch (Exception e) {
			return existe;
		}
	}

	@Override
	public List<Envios> getEstadoEnvioPorIdCliente(Integer id) {
		// TODO Auto-generated method stub
		return enviosDao.findbyidCliente(id);
	}

	@Override
	public List<Envios> getEstadoEnvioPorIdDestinatarios(Integer id) {
		// TODO Auto-generated method stub
		return enviosDao.findbyidDestinatario(id);
	}
}
