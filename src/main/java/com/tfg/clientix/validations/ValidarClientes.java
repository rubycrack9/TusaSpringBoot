package com.tfg.clientix.validations;

import org.springframework.util.StringUtils;

import com.tfg.clientix.errorCharger.CodigosErrorRest;
import com.tfg.clientix.errorCharger.ErrorRest;
import com.tfg.clientix.models.entity.Clientes;


public class ValidarClientes {
	
	private static final String dniChars="TRWAGMYFPDXBNJZSQVHLCKE";
	
	public static ErrorRest validarCliente(Clientes c)
	{
		ErrorRest error = new ErrorRest();
		//Si la validacion va bien devolverá true, codigo de error 0 y literal success
		error.setValidado(true);
		error.setCodError(CodigosErrorRest.COD_ERROR_CERO);
		error.setLitError(CodigosErrorRest.LIT_ERROR_SUCCESS);
		
		int longitudMaximaNombre = 25;
		int longitudMaximaDireccion = 30;
		
		//Validar CIFNIF obligatorio, ni vacio ni null
		if(StringUtils.isEmpty(c.getCIFNIF())
				|| c.getCIFNIF() == null)
		{
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CIFNIF_OBLIGATORIO);
		}
		//Validar tipo de dato CIFNIF
		if(!c.getCIFNIF().getClass().getSimpleName().equals("String")) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CIFNIF_TIPO_DE_DATO_INCORRECTO);
		}
		//Validar letra correcta CIFNIF
		error = validarLetraCIFNIF(c.getCIFNIF());
		//Validar NombreCliente obligatorio, ni vacio ni null
		if(StringUtils.isEmpty(c.getNombreCliente())
				|| c.getNombreCliente() == null)
		{
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_CLIENTE_OBLIGATORIO);
		}
		//Validar tipo de dato NombreCliente
		if(isNumeric(c.getNombreCliente())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_CLIENTE_TIPO_DE_DATO_INCORRECTO);
		}
		//Validar longitud máxima NombreCliente
		if(c.getNombreCliente().length() > longitudMaximaNombre ) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_NOMBRE_CLIENTE_TAMANO_MAXIMO);
		}
		//Validar direccion obligatorio, ni vacio ni null
		if(StringUtils.isEmpty(c.getDireccionFacturacion())
				|| c.getDireccionFacturacion() == null)
		{
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_FACTURACION_OBLIGATORIO);
		}
		//Validar tipo de dato direccion
		if(isNumeric(c.getDireccionFacturacion())) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_FACTURACION_TIPO_DE_DATO_INCORRECTO);
		}
		//Validar longitud máxima direccion
		if(c.getNombreCliente().length() > longitudMaximaDireccion ) {
			error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_DIRECCION_FACTURACION_TAMANO_MAXIMO);
		}
		
		return error;	
	}
	public static ErrorRest validarLetraCIFNIF(String cifnif) {
		
		ErrorRest error = new ErrorRest();
		
		String intPartDNI = cifnif.trim().replaceAll(" ", "").substring(0, 8);
        char ltrDNI = cifnif.charAt(8);
        int valNumDni = Integer.parseInt(intPartDNI) % 23;
        if (cifnif.length()!= 9 || isNumeric(intPartDNI) == false || dniChars.charAt(valNumDni)!= ltrDNI) {
        	error.setValidado(false);
			error.setCodError(CodigosErrorRest.COD_ERROR_UNO);
			error.setLitError(CodigosErrorRest.ERROR_CIFNIF_ERRONEO);
        }
		return error;
	}
	private static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	} 
	
}
