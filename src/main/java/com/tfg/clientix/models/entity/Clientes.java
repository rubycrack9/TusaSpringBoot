package com.tfg.clientix.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "clientes")
@Table(name = "clientes")
public class Clientes implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCliente;
	private String NombreCliente;
	@Column(name = "CIFNIF", unique = true)
	private String CIFNIF;
	private String DireccionFacturacion;

	public Clientes() {
		// TODO Auto-generated constructor stub
	}
	
	public Clientes(String NombreCliente) {

		this.NombreCliente = NombreCliente;

	}

	public Clientes(int idCliente, String NombreCliente, String CIFNIF, String DireccionFacturacion) {
		this.idCliente = idCliente;
		this.NombreCliente = NombreCliente;
		this.CIFNIF = CIFNIF;
		this.DireccionFacturacion = DireccionFacturacion;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombreCliente() {
		return NombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		NombreCliente = nombreCliente;
	}

	public String getCIFNIF() {
		return CIFNIF;
	}

	public void setCIFNIF(String cIFNIF) {
		CIFNIF = cIFNIF;
	}

	public String getDireccionFacturacion() {
		return DireccionFacturacion;
	}

	public void setDireccionFacturacion(String direccionFacturacion) {
		DireccionFacturacion = direccionFacturacion;
	}
	private static final long serialVersionUID = 1L;
}
