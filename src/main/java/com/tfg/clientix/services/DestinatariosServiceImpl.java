package com.tfg.clientix.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		boolean existe = false;
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clientix", "root", "");
			String SQL = "select d.DNINIF from destinatarios d , clientes c where c.idCliente = d.idcliente and c.idCliente = "
					+ idcliente + " and d.DNINIF = '" + dninif + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			if (rs.next()) {
				String nif = rs.getString("DNINIF");
				existe = false;

				/*if (nif.equals(dninif)) {
					existe = false;
				} else {
					existe = true;
				}*/
			}
			return existe;
		} catch (Exception e) {
			return existe;
		}
	}

	// CONSULTAR
	public List<Destinatarios> consultarDestinatariosIdCliente(Integer idCliente) {
		List<Destinatarios> listaDestinatarios = new ArrayList<Destinatarios>();
		Destinatarios destinatarioRecuperado = null;
		Query getDestinatariosIdCliente = entityManager
				.createQuery("SELECT u FROM destinatarios u WHERE u.idcliente =: idcliente");
		getDestinatariosIdCliente.setParameter("idcliente", idCliente);
		for (Object destinatarioRecuperadoSi : getDestinatariosIdCliente.getResultList()) {
			destinatarioRecuperado = (Destinatarios) destinatarioRecuperadoSi;
			listaDestinatarios.add(destinatarioRecuperado);
		}
		return listaDestinatarios;

	}

	@Override
	public List<Destinatarios> getDestinatarioNombreDestinatario(String nombre) throws SQLException {
		List<Destinatarios> listaDestinatarios = new ArrayList<Destinatarios>();

		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clientix", "root", "");
		String SQL = "select * from destinatarios WHERE NombreDestinatario LIKE " + "'%" + nombre + "%'";
		PreparedStatement ps = con.prepareStatement(SQL);
		// ps.setString(1, "%"+nombre+"%");
		// Statement stmt = con.createStatement();
		ResultSet rs = ps.executeQuery(SQL);
		while (rs.next()) {
			listaDestinatarios.add(new Destinatarios(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6)));
		}
		return listaDestinatarios;

	}

	@Override
	public Clientes getClienteIdDestinatario(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		Clientes c = null;

		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clientix", "root", "");
		String SQL = "SELECT DISTINCT c.idCliente,c.NombreCliente,c.CIFNIF,c.DireccionFacturacion FROM clientes c, destinatarios 	d where d.idcliente = c.idCliente and d.idDestinatario = " + id;
		PreparedStatement ps = con.prepareStatement(SQL);
		// ps.setString(1, "%"+nombre+"%");
		// Statement stmt = con.createStatement();
		ResultSet rs = ps.executeQuery(SQL);
		while (rs.next()) {
			c = (new Clientes(rs.getInt(1), rs.getString(2),  rs.getString(3),  rs.getString(4)));
		}
		return c;
	}

	@Override
	public List<Destinatarios> getDestinatarioNIF(String nif) throws SQLException {
		List<Destinatarios> listaDestinatarios = new ArrayList<Destinatarios>();

		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clientix", "root", "");
		String SQL = "select * from destinatarios WHERE DNINIF LIKE " + "'%" + nif + "%'";
		PreparedStatement ps = con.prepareStatement(SQL);
		// ps.setString(1, "%"+nombre+"%");
		// Statement stmt = con.createStatement();
		ResultSet rs = ps.executeQuery(SQL);
		while (rs.next()) {
			listaDestinatarios.add(new Destinatarios(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6)));
		}
		return listaDestinatarios;
	}

	@Override
	public List<Destinatarios> consultarDestinatariosDireccion(String direccion) throws SQLException {
		List<Destinatarios> listaDestinatarios = new ArrayList<Destinatarios>();

		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clientix", "root", "");
		String SQL = "select * from destinatarios WHERE DireccionCompleta LIKE " + "'%" + direccion + "%'";
		PreparedStatement ps = con.prepareStatement(SQL);
		// ps.setString(1, "%"+nombre+"%");
		// Statement stmt = con.createStatement();
		ResultSet rs = ps.executeQuery(SQL);
		while (rs.next()) {
			listaDestinatarios.add(new Destinatarios(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6)));
		}
		return listaDestinatarios;
	}
	
	


}
