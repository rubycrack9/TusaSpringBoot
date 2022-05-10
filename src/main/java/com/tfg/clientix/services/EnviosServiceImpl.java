package com.tfg.clientix.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
	public Envios getEnvioId(Integer id) {
		// TODO Auto-generated method stub
		return null;
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
}
