package com.tfg.clientix.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
		Map<String, Object> response = new HashMap<>();
		
		error = Validaciones.validarEnvio(e, enviosService);
	
		if (error.getCodError().equals(CodigosErrorRest.COD_ERROR_CERO)
				&& error.getLitError().equals(CodigosErrorRest.LIT_ERROR_SUCCESS) && error.isValidado()) {
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
	return new ResponseEntity<Envios>(e, HttpStatus.CREATED);

	}

}
