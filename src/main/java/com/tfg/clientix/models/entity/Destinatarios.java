package com.tfg.clientix.models.entity;

import javax.persistence.Table;

@Table(name = "destinatarios")
public class Destinatarios {

	private Integer idDestinatario;
	private String NombreDestinatario;
	private String DNINIF;
	private Integer idcliente;
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
		this.idcliente = idcliente;
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

	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
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

}
