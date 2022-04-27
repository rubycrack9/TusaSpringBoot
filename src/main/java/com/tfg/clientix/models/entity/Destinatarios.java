package com.tfg.clientix.models.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity(name = "destinatarios")
@Table(name = "destinatarios")
public class Destinatarios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDestinatario;
	private String NombreDestinatario;
	private String DNINIF;
	@ManyToOne(targetEntity = Clientes.class)
	@JoinColumn(name = "idcliente")
	private Clientes cliente;
	private String CodigoPostal;
	private String DireccionCompleta;

	public Destinatarios() {

	}

	public Destinatarios(String NombreDestinatario) {
		this.NombreDestinatario = NombreDestinatario;

	}

	public Destinatarios(int idDestinatario, String NombreDestinatario, String DNINIF, int idcliente,
			String CodigoPostal, String DireccionCompleta) {
		this.idDestinatario = idDestinatario;
		this.NombreDestinatario = NombreDestinatario;
		this.DNINIF = DNINIF;
		this.CodigoPostal = CodigoPostal;
		this.DireccionCompleta = DireccionCompleta;

	}

	public Integer getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(Integer idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public String getNombreDestinatario() {
		return NombreDestinatario;
	}

	public void setNombreDestinatario(String nombreDestinatario) {
		NombreDestinatario = nombreDestinatario;
	}

	public String getDNINIF() {
		return DNINIF;
	}

	public void setDNINIF(String dNINIF) {
		DNINIF = dNINIF;
	}

	public String getCodigoPostal() {
		return CodigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		CodigoPostal = codigoPostal;
	}

	public String getDireccionCompleta() {
		return DireccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		DireccionCompleta = direccionCompleta;
	}
	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}


}
