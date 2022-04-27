package com.tfg.clientix.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.clientix.errorCharger.ErrorRest;
import com.tfg.clientix.models.entity.Clientes;
import com.tfg.clientix.models.entity.Destinatarios;
import com.tfg.clientix.services.IDestinatariosServices;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("ProyectoFinal/webapi")
public class DestinatariosRestController {
	
	Map<String, Object> response = new HashMap<>();
	
	@Autowired
	private IDestinatariosServices destinatariosService;
	
	//CONSULTAR TODOS LOS DESTINATARIOS
	@GetMapping("/destinatarios")
	public List<Destinatarios> getDestinatarios() {
		return destinatariosService.getAllDestinatarios();

	}
	//INSERTAR UN CLIENTE
	@PostMapping("/insertarDestinatario")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> insert(@RequestBody Destinatarios d) {

		Clientes cliente_nuevo = null;
		ErrorRest error = new ErrorRest();
		Map<String, Object> response = new HashMap<>();

		destinatariosService.insertDestinatarios(d);

		/*
		 * error = ValidarClientes.validarCliente(c,destinatariosService);
		 * 
		 * if (error.getCodError().equals(CodigosErrorRest.COD_ERROR_CERO) &&
		 * error.getLitError().equals(CodigosErrorRest.LIT_ERROR_SUCCESS) &&
		 * error.isValidado()) { cliente_nuevo = clienteServices.insert(c); } else { if
		 * (StringUtils.isEmpty(error.getLitError())) { response.put("Mensaje",
		 * "Por favor revise los valores enviados"); response.put("Código de error:",
		 * CodigosErrorRest.COD_ERROR_DEFECTO); } else { response.put("Mensaje",
		 * error.getLitError()); response.put("Código de error:",
		 * CodigosErrorRest.COD_ERROR_UNO); return new ResponseEntity<Map<String,
		 * Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); } }
		 */

		return new ResponseEntity<Destinatarios>(d, HttpStatus.CREATED);

	}

}
