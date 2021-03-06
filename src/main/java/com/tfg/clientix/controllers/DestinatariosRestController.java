package com.tfg.clientix.controllers;

import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.tfg.clientix.models.entity.Clientes;
import com.tfg.clientix.models.entity.Destinatarios;
import com.tfg.clientix.services.IDestinatariosServices;
import com.tfg.clientix.validations.Validaciones;

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
	//INSERTAR UN DESTINATARIO
	@PostMapping("/insertarDestinatario")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> insert(@RequestBody Destinatarios d) throws SQLException {

		Destinatarios destinatarioNuevo = null;
		ErrorRest error = new ErrorRest();
		Map<String, Object> response = new HashMap<>();

		
		error = Validaciones.validarDestinatario(d, destinatariosService);

		if (error.getCodError().equals(CodigosErrorRest.COD_ERROR_CERO)
				&& error.getLitError().equals(CodigosErrorRest.LIT_ERROR_SUCCESS) && error.isValidado()) {
			destinatarioNuevo = destinatariosService.insertDestinatarios(d);
		} else {
			if (StringUtils.isEmpty(error.getLitError())) {
				response.put("Mensaje", "Por favor revise los valores enviados");
				response.put("C??digo de error:", CodigosErrorRest.COD_ERROR_DEFECTO);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				response.put("Mensaje", error.getLitError());
				response.put("C??digo de error:", CodigosErrorRest.COD_ERROR_UNO);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		 

		return new ResponseEntity<Destinatarios>(d, HttpStatus.CREATED);

	}
	//CONSULTAR TODOS LOS DESTINATARIOS POR EL ID DE CLIENTE
	@GetMapping("/destinatariosCliente/{id}")
	public ResponseEntity<?>mostrarDestintarios(@PathVariable Integer id) throws SQLException
	{
		ErrorRest error = new ErrorRest();
		Map<String, Object> response = new HashMap<>();
		
		//COMPROBAR SI ESE CLIENTE TIENE DESTINATARIOS
		
		error = Validaciones.validarSiClienteTieneDestinatarios(id);
		if(error.getCodError().equals(CodigosErrorRest.COD_ERROR_UNO))
		{
			response.put("Mensaje", error.getLitError());
			response.put("C??digo de error:", CodigosErrorRest.COD_ERROR_UNO);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
		
		List<Destinatarios> listaDestinatarios = new ArrayList<Destinatarios>();
		
		try {
			listaDestinatarios = destinatariosService.consultarDestinatariosIdCliente(id);
		} catch (DataAccessException e) {
			response.put("Mensaje", "Error al realizar la consulta en la base de datos");
			response.put("C??digo de error:",CodigosErrorRest.COD_ERROR_DEFECTO);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		if (CollectionUtils.isEmpty(listaDestinatarios)) {
			response.put("Mensaje",
					"El cliente con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(listaDestinatarios,HttpStatus.OK);
		}
	}
	
	//Destinatario por su nombre
	@GetMapping("/destinatarios/nombre/{nombre}")
	public ResponseEntity<?> mostrarporNombre(@PathVariable String nombre) throws SQLException {

		List<Destinatarios> d = null;
		Map<String, Object> response = new HashMap<>();

		try {
			d = destinatariosService.getDestinatarioNombreDestinatario(nombre);
		} catch (DataAccessException e) {
			response.put("Mensaje", "Error al realizar la consulta en la base de datos");
			response.put("C??digo de error:",CodigosErrorRest.COD_ERROR_DEFECTO);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (d.isEmpty()) {
			response.put("Mensaje",
					"No se puede encontrar ning??n destinatario con ese nombre ".concat(nombre).concat(" no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Destinatarios>>(d, HttpStatus.OK);

	}
	
	
		//Consultar cliente por id de destinatario
		@GetMapping("/destinatarios/idDestinatario/{id}")
		public ResponseEntity<?> mostrarClienteId(@PathVariable Integer id) throws SQLException {

			Clientes d = null;
			Map<String, Object> response = new HashMap<>();

			try {
				d = destinatariosService.getClienteIdDestinatario(id);
			} catch (DataAccessException e) {
				response.put("Mensaje", "Error al realizar la consulta en la base de datos");
				response.put("C??digo de error:",CodigosErrorRest.COD_ERROR_DEFECTO);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			}

			if (d == null) {
				response.put("Mensaje",
						"No se puede encontrar ning??n cliente con ese " + id + " de destinatario!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<Clientes>(d, HttpStatus.OK);

		}
	
	
	
	//CONSULTAR UN DESTINATARIO POR ID
	@GetMapping("/destinatarios/{id}")
	public ResponseEntity<?> mostrarporId(@PathVariable Integer id) {

		Destinatarios d = null;
		Map<String, Object> response = new HashMap<>();

		try {
			d = destinatariosService.readDestinatarios(id);
		} catch (DataAccessException e) {
			response.put("Mensaje", "Error al realizar la consulta en la base de datos");
			response.put("C??digo de error:",CodigosErrorRest.COD_ERROR_DEFECTO);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (d == null) {
			response.put("Mensaje",
					"No se puede encontrar ning??n destinatario con ese id: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Destinatarios>(d, HttpStatus.OK);

	}
	
	
	//Destinatario por su NIF
	@GetMapping("/destinatarios/nif/{nif}")
	public ResponseEntity<?> mostrarPorDNI(@PathVariable String nif) throws SQLException {

		List<Destinatarios> d = null;
		Map<String, Object> response = new HashMap<>();

		try {
			d = destinatariosService.getDestinatarioNIF(nif);
		} catch (DataAccessException e) {
			response.put("Mensaje", "Error al realizar la consulta en la base de datos");
			response.put("C??digo de error:",CodigosErrorRest.COD_ERROR_DEFECTO);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (d.isEmpty()) {
			response.put("Mensaje",
					"No se puede encontrar ning??n destinatario con ese NIF " + nif +" en la base de datos!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Destinatarios>>(d, HttpStatus.OK);

	}
	
	
	//Destinatario por su Direccion
	@GetMapping("/destinatarios/direccion/{direccion}")
	public ResponseEntity<?> mostrarPorDireccion(@PathVariable String direccion) throws SQLException {

		List<Destinatarios> d = null;
		Map<String, Object> response = new HashMap<>();

		try {
			d = destinatariosService.consultarDestinatariosDireccion(direccion);
		} catch (DataAccessException e) {
			response.put("Mensaje", "Error al realizar la consulta en la base de datos");
			response.put("C??digo de error:",CodigosErrorRest.COD_ERROR_DEFECTO);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (d.isEmpty()) {
			response.put("Mensaje",
					"No se puede encontrar ning??n destinatario con esa direcci??n " + direccion +" en la base de datos!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Destinatarios>>(d, HttpStatus.OK);

	}

	// ACTUALIZAR DESTINATARIO
	@PutMapping("/actualizarDestinatario/{id}")
	public ResponseEntity<?> update(@RequestBody Destinatarios d, @PathVariable Integer id) throws SQLException {

		Destinatarios destinatario_actual = destinatariosService.readDestinatarios(id);
		Destinatarios destinatario_actualizado = null;

		if (destinatario_actual == null) {
			response.put("Mensaje", "No se pudo editar, el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos")));
			response.put("C??digo de error:", CodigosErrorRest.COD_ERROR_CLIENTE_NO_ENCONTRADO);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			ErrorRest error = new ErrorRest();

			error = Validaciones.validarDestinatarioActualizar(d, destinatariosService);

			if (error.getCodError().equals(CodigosErrorRest.COD_ERROR_CERO)
					&& error.getLitError().equals(CodigosErrorRest.LIT_ERROR_SUCCESS) && error.isValidado()) {
				destinatario_actual.setDNINIF(d.getDNINIF());
				destinatario_actual.setDireccionCompleta(d.getDireccionCompleta());
				destinatario_actual.setNombreDestinatario(d.getNombreDestinatario());
				destinatario_actual.setCliente(d.getCliente());
				destinatario_actual.setCodigoPostal(d.getCodigoPostal());
				destinatario_actualizado = destinatariosService.insertDestinatarios(destinatario_actual);
			} else {
				if (StringUtils.isEmpty(error.getLitError())) {
					response.put("Mensaje", "Por favor revise los valores enviados");
					response.put("C??digo de error:", CodigosErrorRest.COD_ERROR_DEFECTO);
				} else {
					response.put("Mensaje", error.getLitError());
					response.put("C??digo de error:", CodigosErrorRest.COD_ERROR_UNO);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			response.put("Mensaje", "El cliente ha sido actualizado con ??xito!");
			response.put("Cliente:", destinatario_actualizado);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		
	}
	
	


	// METODO BORRAR POR ID
	@DeleteMapping("borrarDestinario/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		destinatariosService.deletebyId(id);
		response.put("Mensaje", "Cliente borrado con ??xito");
		response.put("C??digo de Error", CodigosErrorRest.COD_ERROR_CERO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
