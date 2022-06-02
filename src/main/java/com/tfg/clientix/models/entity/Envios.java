package com.tfg.clientix.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity(name = "envios")
@Table(name = "envios")
public class Envios implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idEnvio;
	private int idCliente;
	private int idDestinatario;
	private String idEstadoEnvio;
	private String numIntentosEntrega;
	private String peso;
	
	public Integer getIdEnvio() {
		return idEnvio;
	}
	public void setIdEnvio(Integer idEnvio) {
		this.idEnvio = idEnvio;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public int getIdDestinatario() {
		return idDestinatario;
	}
	public void setIdDestinatario(int idDestinatario) {
		this.idDestinatario = idDestinatario;
	}
	public String getIdEstadoEnvio() {
		return idEstadoEnvio;
	}
	public void setIdEstadoEnvio(String idEstadoEnvio) {
		this.idEstadoEnvio = idEstadoEnvio;
	}
	public String getNumIntentosEntrega() {
		return numIntentosEntrega;
	}
	public void setNumIntentosEntrega(String numIntentosEntrega) {
		this.numIntentosEntrega = numIntentosEntrega;
	}
	public String getPeso() {
		return peso;
	}
	public void setPeso(String peso) {
		this.peso = peso;
	}
	public Envios(Integer idEnvio, int idCliente, int idDestinatario, String idEstadoEnvio, String numIntentosEntrega,
			String peso) {
		this.idEnvio = idEnvio;
		this.idCliente = idCliente;
		this.idDestinatario = idDestinatario;
		this.idEstadoEnvio = idEstadoEnvio;
		this.numIntentosEntrega = numIntentosEntrega;
		this.peso = peso;
	}
	
	public Envios() {}
	
	
}
