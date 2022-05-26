package com.tfg.clientix.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "destinatarios")
@Table(name = "destinatarios")
public class Destinatarios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDestinatario;
	@Column(name = "NombreDestinatario")
	private String nombreDestinatario;
	private String DNINIF;
	@ManyToOne(targetEntity = Clientes.class)
	@JoinColumn(name = "idcliente")
	private Clientes cliente;
	private String CodigoPostal;
	@Column(name = "DireccionCompleta")
	private String direccionCompleta;
	@Column(name= "idcliente", insertable = false, updatable = false)
	@JsonIgnore
	private int idcliente;
	

	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}

	public Destinatarios() {

	}

	public Destinatarios(String NombreDestinatario) {
		this.nombreDestinatario = NombreDestinatario;

	}

	public Destinatarios(int idDestinatario, String NombreDestinatario, String DNINIF, int idcliente,
			String CodigoPostal, String DireccionCompleta) {
		this.idDestinatario = idDestinatario;
		this.nombreDestinatario = NombreDestinatario;
		this.DNINIF = DNINIF;
		this.CodigoPostal = CodigoPostal;
		this.direccionCompleta = DireccionCompleta;

	}

	public Integer getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(Integer idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public String getNombreDestinatario() {
		return nombreDestinatario;
	}

	public void setNombreDestinatario(String nombreDestinatario) {
		nombreDestinatario = nombreDestinatario;
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
		return direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		direccionCompleta = direccionCompleta;
	}
	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}


}
