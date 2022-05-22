package com.tfg.clientix.validations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.util.StringUtils;

import com.tfg.clientix.errorCharger.CodigosErrorRest;
import com.tfg.clientix.errorCharger.ErrorRest;
import com.tfg.clientix.models.entity.Clientes;
import com.tfg.clientix.models.entity.Destinatarios;
import com.tfg.clientix.models.entity.Envios;
import com.tfg.clientix.services.IClienteServices;
import com.tfg.clientix.services.IDestinatariosServices;
import com.tfg.clientix.services.IEnviosServices;

public class Validaciones {

	private static final String dniChars = "TRWAGMYFPDXBNJZSQVHLCKE";
	private final static String EAD = "ENTREGADO AL DESTINATARIO";
	private final static String EO = "EN OFICINA";
	private final static String OE = "EN LA OFICINA DE ENTREGA";
	private static EntityManager entityManager;

	public static ErrorRest validarCliente(Clientes c, IClienteServices clienteServices) {
		ErrorRest error = new ErrorRest();
		// Si la validacion va bien devolverá true, codigo de error 0 y literal success
		error.setValidado(true);
		error.setCodError(CodigosErrorRest.COD_ERROR_CERO);
		error.setLitError(CodigosErrorRest.LIT_ERROR_SUCCESS);

		int longitudMaximaNombre = 50;
		int longitudMaximaDireccion = 100;
		int longitud_maxima_dni = 9;

		// Validar CIFNIF obligatorio, ni vacio ni null
		if (StringUtils.isEmpty(c.getCIFNIF()) || c.getCIFNIF() == null) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CIFNIF_OBLIGATORIO);
		}
		// Validar tipo de dato CIFNIF
		if (isNumeric(c.getCIFNIF())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CIFNIF_TIPO_DE_DATO_INCORRECTO);
			return error;
		}
		if(c.getCIFNIF().length() > longitud_maxima_dni) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_TAMANO_MAXIMO);
			return error;
		}
		// Validar letra correcta CIFNIF
		error = validarLetraCIFNIF(c.getCIFNIF());
		if(error.getCodError().equals(CodigosErrorRest.COD_ERROR_UNO)) {
			return error;
		}
		// Validar nif existente en la base de datos
		error = comprobarNifExistente(c.getCIFNIF(), clienteServices);
		if(error.getCodError().equals(CodigosErrorRest.COD_ERROR_UNO)) {
			return error;
		}
		// Validar NombreCliente obligatorio, ni vacio ni null
		if (StringUtils.isEmpty(c.getNombreCliente()) || c.getNombreCliente() == null) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_CLIENTE_OBLIGATORIO);
		}
		// Validar tipo de dato NombreCliente
		if (isNumeric(c.getNombreCliente())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_CLIENTE_TIPO_DE_DATO_INCORRECTO);
		}
		// Validar longitud máxima NombreCliente
		if (c.getNombreCliente().length() > longitudMaximaNombre) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_CLIENTE_TAMANO_MAXIMO);
		}
		// Validar direccion obligatorio, ni vacio ni null
		if (StringUtils.isEmpty(c.getDireccionFacturacion()) || c.getDireccionFacturacion() == null) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_FACTURACION_OBLIGATORIO);
		}
		// Validar tipo de dato direccion
		if (isNumeric(c.getDireccionFacturacion())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_FACTURACION_TIPO_DE_DATO_INCORRECTO);
		}
		// Validar longitud máxima direccion
		if (c.getNombreCliente().length() > longitudMaximaDireccion) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_FACTURACION_TAMANO_MAXIMO);
		}

		return error;
	}

	public static ErrorRest validarClienteActualizar(Clientes c, IClienteServices clienteServices) {
		ErrorRest error = new ErrorRest();
		// Si la validacion va bien devolverá true, codigo de error 0 y literal success
		error.setValidado(true);
		error.setCodError(CodigosErrorRest.COD_ERROR_CERO);
		error.setLitError(CodigosErrorRest.LIT_ERROR_SUCCESS);

		int longitudMaximaNombre = 50;
		int longitudMaximaDireccion = 100;
		int longitud_maxima_dni = 9;
		
		// Validar NombreCliente obligatorio, ni vacio ni null
		if (StringUtils.isEmpty(c.getNombreCliente()) || c.getNombreCliente() == null) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_CLIENTE_OBLIGATORIO);
		}
		// Validar tipo de dato NombreCliente
		if (isNumeric(c.getNombreCliente())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_CLIENTE_TIPO_DE_DATO_INCORRECTO);
		}
		// Validar longitud máxima NombreCliente
		if (c.getNombreCliente().length() > longitudMaximaNombre) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_CLIENTE_TAMANO_MAXIMO);
		}
		// Validar direccion obligatorio, ni vacio ni null
		if (StringUtils.isEmpty(c.getDireccionFacturacion()) || c.getDireccionFacturacion() == null) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_FACTURACION_OBLIGATORIO);
		}
		// Validar tipo de dato direccion
		if (isNumeric(c.getDireccionFacturacion())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_FACTURACION_TIPO_DE_DATO_INCORRECTO);
		}
		// Validar longitud máxima direccion
		if (c.getNombreCliente().length() > longitudMaximaDireccion) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_FACTURACION_TAMANO_MAXIMO);
		}

		return error;
	}

	
	
	
	
	
	public static ErrorRest validarDestinatario(Destinatarios d, IDestinatariosServices destinatariosService) throws SQLException {
		ErrorRest error = new ErrorRest();
		// Si la validacion va bien devolverá true, codigo de error 0 y literal success
		error.setValidado(true);
		error.setCodError(CodigosErrorRest.COD_ERROR_CERO);
		error.setLitError(CodigosErrorRest.LIT_ERROR_SUCCESS);

		int longitudMaxNombreDestinatario = 50;
		int longitudMaxDireccionCompleta = 100;
		int longitudMaxCodigoPostal = 5;
		int longitud_maxima_dni = 9;
		
		
		//Validar si existe el cliente
		error = validarSiClienteExisteCuandoCreaDestinatario(d.getCliente().getIdCliente());
		
		
		// Validar DNINIF obligatorio, ni vacio ni null
		if (StringUtils.isEmpty(d.getDNINIF()) || d.getDNINIF() == null) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CIFNIF_OBLIGATORIO);
			return error;
		}
		// Validar tipo de dato DNINIF
		if (isNumeric(d.getDNINIF())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CIFNIF_TIPO_DE_DATO_INCORRECTO);
			return error;
		}
		if(d.getDNINIF().length() > longitud_maxima_dni) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_TAMANO_MAXIMO);
			return error;
		}
		// Validar letra correcta DNINIF
		error = validarLetraCIFNIF(d.getDNINIF());
		// Validar nif existente en la base de datos
		error = comprobarNifExistenteDestinatarios(d.getDNINIF(), destinatariosService, d.getCliente().getIdCliente());
		// Validar NombreDestinatario obligatorio, ni vacio ni null
		if (StringUtils.isEmpty(d.getNombreDestinatario()) || d.getNombreDestinatario() == null) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_DESTINATARIO_OBLIGATORIO);
		}
		// Validar tipo de dato NombreDestinatario
		if (isNumeric(d.getNombreDestinatario())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_DESTINATARIO_TIPO_DE_DATO_INCORRECTO);
		}
		// Validar longitud máxima NombreDestinatario
		if (d.getNombreDestinatario().length() > longitudMaxNombreDestinatario) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_DESTINATARIO_TAMANO_MAXIMO);
		}
		// Validar direccion obligatorio, ni vacio ni null
		if (StringUtils.isEmpty(d.getDireccionCompleta()) || d.getDireccionCompleta() == null) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_COMPLETA_OBLIGATORIO);
		}
		// Validar tipo de dato direccion
		if (isNumeric(d.getDireccionCompleta())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_COMPLETA_TIPO_DE_DATO_INCORRECTO);
		}
		// Validar longitud máxima direccion
		if (d.getDireccionCompleta().length() > longitudMaxDireccionCompleta) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_COMPLETA_TAMANO_MAXIMO);
		}

		// Validar CODIGO POSTAL obligatorio, ni vacio ni null
		if (StringUtils.isEmpty(d.getCodigoPostal()) || d.getCodigoPostal() == null) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CODIGO_POSTAL_OBLIGATORIO);
		}
		/*
		 * // Validar tipo de dato CODIGO POSTAL if (isNumeric(d.getCodigoPostal())) {
		 * error.setValidado(false); error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
		 * error.setLitError(CodigosErrorRest.
		 * ERROR_CODIGO_POSTAL_TIPO_DE_DATO_INCORRECTO); }
		 */
		// Validar longitud máxima CODIGO POSTAL
		if (d.getCodigoPostal().length() > longitudMaxCodigoPostal) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CODIGO_POSTAL_TAMANO_MAXIMO);
		}

		return error;

	}
	
	
	public static ErrorRest validarSiClienteExisteCuandoCreaDestinatario(int id) throws SQLException
	{
		ErrorRest error = new ErrorRest();
			 Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/clientix","root","");
			 String SQL = "select idCliente from clientes where idCliente = " +id;
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(SQL);
			 if(rs.next() == false)
			 {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CLIENTE_CREAR_DESTINATARIO);
			
			 }
		return error;
		
	}
	
	
	public static ErrorRest validarSiClienteTieneDestinatarios(int id) throws SQLException
	{
		ErrorRest error = new ErrorRest();
			 Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/clientix","root","");
			 String SQL = "select * from destinatarios where idcliente = " +id;
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(SQL);
			 if(rs.next() == false)
			 {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CLIENTE_COMPROBAR_DESTINATARIO);
			
			 }
			 else {
			 		 error.setCodError(CodigosErrorRest.COD_ERROR_CERO);
					error.setLitError(CodigosErrorRest.LIT_ERROR_SUCCESS);
			 }
		return error;
		
	}
	

	public static ErrorRest validarLetraCIFNIF(String cifnif) {

		ErrorRest error = new ErrorRest();

		String intPartDNI = cifnif.trim().replaceAll(" ", "").substring(0, 8);
		char ltrDNI = cifnif.charAt(8);
		int valNumDni = Integer.parseInt(intPartDNI) % 23;
		if (cifnif.length() != 9 || isNumeric(intPartDNI) == false || dniChars.charAt(valNumDni) != ltrDNI) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CIFNIF_ERRONEO);
		} else {
			error.setValidado(true);
			error.setCodError(CodigosErrorRest.COD_ERROR_CERO);
			error.setLitError(CodigosErrorRest.LIT_ERROR_SUCCESS);
		}
		return error;
	}

	private static boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	private static ErrorRest comprobarNifExistente(String cifnif, IClienteServices clienteServices) {
		ErrorRest error = new ErrorRest();
		if (clienteServices.consultarNIFExistente(cifnif)) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CIFNIF_EXISTENTE);

		} else {
			error.setValidado(true);
			error.setCodError(CodigosErrorRest.COD_ERROR_CERO);
			error.setLitError(CodigosErrorRest.LIT_ERROR_SUCCESS);
		}

		return error;
	}

	private static ErrorRest comprobarNifExistenteDestinatarios(String dninif,
			IDestinatariosServices destinatariosServices, int idcliente) {
		ErrorRest error = new ErrorRest();
		if (destinatariosServices.consultarNIFExistente(dninif, idcliente)) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CIFNIF_EXISTENTE);
		} else {
			error.setValidado(true);
			error.setCodError(CodigosErrorRest.COD_ERROR_CERO);
			error.setLitError(CodigosErrorRest.LIT_ERROR_SUCCESS);
		}

		return error;
	}

	private static ErrorRest consultarNIFExistenteparaesecliente(String dninif, IEnviosServices envioServices,
			int idcliente) {
		ErrorRest error = new ErrorRest();
		if (envioServices.consultarNIFExistenteparaesecliente(dninif, idcliente)) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CIFNIF_EXISTENTE);
		} else {
			error.setValidado(true);
			error.setCodError(CodigosErrorRest.COD_ERROR_CERO);
			error.setLitError(CodigosErrorRest.LIT_ERROR_SUCCESS);
		}

		return error;
	}

	public static ErrorRest validarEnvio(Envios e, IEnviosServices envioServices) {
		ErrorRest error = new ErrorRest();
		// Si la validacion va bien devolverá true, codigo de error 0 y literal success
		error.setValidado(true);
		error.setCodError(CodigosErrorRest.COD_ERROR_CERO);
		error.setLitError(CodigosErrorRest.LIT_ERROR_SUCCESS);

		int longitudMaximaNombre = 50;
		int longitudMaximaDireccion = 100;
		int longitud_codigo_postal = 5;
		int longitud_maxima_dni = 9;

		// Validar CIFNIF obligatorio, ni vacio ni null
		if (StringUtils.isEmpty(e.getDNINIF()) || e.getDNINIF() == null) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CIFNIF_OBLIGATORIO);
		}
		// Validar tipo de dato CIFNIF
		if (isNumeric(e.getDNINIF())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CIFNIF_TIPO_DE_DATO_INCORRECTO);
			return error;
		}
		if(e.getDNINIF().length() > longitud_maxima_dni) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_TAMANO_MAXIMO);
			return error;
		}
		// Validar letra correcta CIFNIF
		error = validarLetraCIFNIF(e.getDNINIF());
		// Validar nif existente en la base de datos
		error = consultarNIFExistenteparaesecliente(e.getDNINIF(), envioServices, e.getIdCliente());
		// Validar NombreCliente obligatorio, ni vacio ni null
		if (StringUtils.isEmpty(e.getNombreDestinatario()) || e.getNombreDestinatario() == null) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_CLIENTE_OBLIGATORIO);
		}
		// Validar tipo de dato NombreDestinatario
		if (isNumeric(e.getNombreDestinatario())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_CLIENTE_TIPO_DE_DATO_INCORRECTO);
		}
		// Validar longitud máxima NombreDestinatario
		if (e.getNombreDestinatario().length() > longitudMaximaNombre) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_CLIENTE_TAMANO_MAXIMO);
		}
		// Validar direccion obligatorio, ni vacio ni null
		if (StringUtils.isEmpty(e.getDireccionCompleta()) || e.getDireccionCompleta() == null) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_FACTURACION_OBLIGATORIO);
		}
		// Validar tipo de dato direccion
		if (isNumeric(e.getDireccionCompleta())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_FACTURACION_TIPO_DE_DATO_INCORRECTO);
		}
		// Validar longitud máxima direccion
		if (e.getDireccionCompleta().length() > longitudMaximaDireccion) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_FACTURACION_TAMANO_MAXIMO);
		}

		// Validar codigo postal
		if (e.getCodigoPostal().length() > longitud_codigo_postal) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CODIGO_POSTAL_TAMANO_MAXIMO);
		}
		if (isNumeric(e.getIdEstadoEnvio())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_ESTADO_ENVIO_TIPO);
		}
		if(e.getIdEstadoEnvio().equals(EAD)
				|| e.getIdEstadoEnvio().equals(EO)
				|| e.getIdEstadoEnvio().equals(OE)) {
		}else {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_ESTADO_ENVIO_TEXTO);
		}

		// Validar idEstadoEnvio
		// en la bd esta INT PERO AL NO TRATAR EL DATO PUES STRING?
		if (!isNumeric(e.getNumIntentosEntrega().toString())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_ESTADO_ENVIO_TIPO);
		}

		return error;
	}

}
