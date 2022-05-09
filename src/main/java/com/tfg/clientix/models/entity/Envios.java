package com.tfg.clientix.models.entity;

import javax.persistence.Table;

@Table(name = "envios")
public class Envios {

	Integer idEnvio;
	Integer idCliente;
	String nombreDestinatario;
	String DNINIF;
	String codigoPostal;
	String direccionCompleta;
	String idEstadoEnvio;
	Integer numIntentosEntrega;

	public Envios() {

	}

	public Envios(Integer idEnvio, Integer idCliente, String nombreDestinatario, String DNINIF,
			String codigoPostal, String direccionCompleta, String idEstadoEnvio, Integer numIntentosEntrega) {

		this.idEnvio = idEnvio;
		this.idCliente = idCliente;
		this.nombreDestinatario = nombreDestinatario;
		this.DNINIF = DNINIF;
		this.codigoPostal = codigoPostal;
		this.direccionCompleta = direccionCompleta;
		this.idEstadoEnvio = idEstadoEnvio;
		this.numIntentosEntrega = numIntentosEntrega;

	}

	public Integer getIdEnvio() {
		return idEnvio;
	}

	public void setIdEnvio(Integer idEnvio) {
		this.idEnvio = idEnvio;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombreDestinatario() {
		return nombreDestinatario;
	}

	public void setNombreDestinatario(String nombreDestinatario) {
		this.nombreDestinatario = nombreDestinatario;
	}

	public String getDNINIF() {
		return DNINIF;
	}

	public void setDNINIF(String dNINIF) {
		DNINIF = dNINIF;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDireccionCompleta() {
		return direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}

	public String getIdEstadoEnvio() {
		return idEstadoEnvio;
	}

	public void setIdEstadoEnvio(String idEstadoEnvio) {
		this.idEstadoEnvio = idEstadoEnvio;
	}

	public Integer getNumIntentosEntrega() {
		return numIntentosEntrega;
	}

	public void setNumIntentosEntrega(Integer numIntentosEntrega) {
		this.numIntentosEntrega = numIntentosEntrega;
	}

}
