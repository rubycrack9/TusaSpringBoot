package com.tfg.clientix.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.clientix.errorCharger.CodigosErrorRest;
import com.tfg.clientix.errorCharger.ErrorRest;
import com.tfg.clientix.models.entity.Envios;
import com.tfg.clientix.services.IEnviosServices;
import com.tfg.clientix.validations.Validaciones;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("ProyectoFinal/webapi")
public class EnviosRestController {
	
	Map<String, Object> response = new HashMap<>();
	@Autowired
	private IEnviosServices enviosService;
	
	// CONSULTAR TODOS LOS ENVIOS
	@GetMapping("/envios")
	public List<Envios> getEnvios() {
		return enviosService.getEnvios();
	}
	
	// INSERTAR UN ENVIOS
	@PostMapping("/insertarEnvio")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> insert(@RequestBody Envios e) {
		Envios envioNuevo = null;
		ErrorRest error = new ErrorRest();
		String EAD = "ENTREGADO AL DESTINATARIO";
		String EO = "EN OFICINA";
		String OE = "EN LA OFICINA DE ENTREGA";
		Map<String, Object> response = new HashMap<>();
		
		error = Validaciones.validarEnvio(e, enviosService);
	
		if (error.getCodError().equals(CodigosErrorRest.COD_ERROR_CERO)
				&& error.getLitError().equals(CodigosErrorRest.LIT_ERROR_SUCCESS) && error.isValidado()) {
			if(e.getIdEstadoEnvio().equals(EAD)){
				e.setIdEstadoEnvio("1");
			}
			else if (e.getIdEstadoEnvio().equals(EO)){
				e.setIdEstadoEnvio("2");
			}
			else {
				e.setIdEstadoEnvio("3");
			}
			envioNuevo = enviosService.insertEnvios(e);
		} else {
			if (StringUtils.isEmpty(error.getLitError())) {
				response.put("Mensaje", "Por favor revise los valores enviados");
				response.put("Código de error:", CodigosErrorRest.COD_ERROR_DEFECTO);
			} else {
				response.put("Mensaje", error.getLitError());
				response.put("Código de error:", CodigosErrorRest.COD_ERROR_UNO);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}	
		response.put("Mensaje", "El envío ha sido creado con éxito!");
		response.put("Envio", e);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		//return new ResponseEntity<Envios>(e, HttpStatus.CREATED);

	}
	
	
	//CONSULTAR ESTADO ENVIO
	@GetMapping("estadoenvio/{id}")
	public ResponseEntity<?> getEstadoEnvio(@PathVariable Integer id)
	{
		//Envios e = null;
		Map<String, Object> response = new HashMap<>();
		List<Envios> estadoEnvios = new ArrayList<Envios>();
		try {
			estadoEnvios = enviosService.getEstadoEnvioPorId(id);
		} catch (DataAccessException e) {
			response.put("Mensaje", "Error al realizar la consulta en la base de datos");
			response.put("Código de error:",CodigosErrorRest.COD_ERROR_DEFECTO);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		if (CollectionUtils.isEmpty(estadoEnvios)) {
			response.put("Mensaje",
					"El envío con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		//response.put("Estado del envío: ", estadoEnvios.get(0));
		//response.put("Número de intentos de entrega", estadoEnvios.get(1));
		return new ResponseEntity<>(estadoEnvios,HttpStatus.OK);
	}

	// ACTUALIZAR DESTINATARIO
	@PutMapping("/actualizarEnvio/{id}")
	public ResponseEntity<?> update(@RequestBody Envios e, @PathVariable Integer id) {

		List<Envios> envios= enviosService.getEnvios();
		Envios envio_Actual = null;
		Envios envio_actualizado = null;
		for(Envios envio : envios) {
			if(envio.getIdEnvio() == id) {
				envio_Actual = envio;
			}
		}
		if (envio_Actual == null) {
			response.put("Mensaje", "No se pudo editar, el envío con ID: "
					.concat(id.toString().concat(" no existe en la base de datos")));
			response.put("Código de error:", CodigosErrorRest.COD_ERROR_CLIENTE_NO_ENCONTRADO);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			ErrorRest error = new ErrorRest();

			error = Validaciones.validarEnvio(e, enviosService);

			if (error.getCodError().equals(CodigosErrorRest.COD_ERROR_CERO)
					&& error.getLitError().equals(CodigosErrorRest.LIT_ERROR_SUCCESS) && error.isValidado()) {
				envio_Actual.setDNINIF(e.getDNINIF());
				envio_Actual.setDireccionCompleta(e.getDireccionCompleta());
				envio_Actual.setNombreDestinatario(e.getNombreDestinatario());
				envio_Actual.setIdCliente(e.getIdCliente());
				envio_Actual.setIdEstadoEnvio(e.getIdEstadoEnvio());
				envio_Actual.setNumIntentosEntrega(e.getNumIntentosEntrega());
				envio_Actual.setCodigoPostal(e.getCodigoPostal());
				envio_actualizado = enviosService.insertEnvios(envio_Actual);
			} else {
				if (StringUtils.isEmpty(error.getLitError())) {
					response.put("Mensaje", "Por favor revise los valores enviados");
					response.put("Código de error:", CodigosErrorRest.COD_ERROR_DEFECTO);
				} else {
					response.put("Mensaje", error.getLitError());
					response.put("Código de error:", CodigosErrorRest.COD_ERROR_UNO);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			response.put("Mensaje", "El envío ha sido actualizado con éxito!");
			response.put("Cliente:", envio_actualizado);
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

	}
	
}
