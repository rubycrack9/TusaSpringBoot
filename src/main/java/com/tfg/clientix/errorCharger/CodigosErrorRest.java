package com.tfg.clientix.errorCharger;

public class CodigosErrorRest 
{
	
	
	public static final String COD_ERROR_UNO = "1";
	public static final String COD_ERROR_CERO = "0";
	public static final String COD_ERROR_DEFECTO = "800";
	public static final String COD_ERROR_FORMATO_CLIENTE = "801";
	public static final String COD_ERROR_CLIENTE_NO_ENCONTRADO = "802";
	public static final String LIT_ERROR_SUCCESS = "Success";
	
	public static final String ERROR_CIFNIF_OBLIGATORIO = "El campo NIF no puede enviarse vacio";
	public static final String ERROR_CIFNIF_TIPO_DE_DATO_INCORRECTO = "Asegurese que el dato NIF es un texto";
	public static final String ERROR_CIFNIF_ERRONEO = "La letra del NIF es erronea";
	public static final String ERROR_CIFNIF_EXISTENTE = "El NIF introducido ya existe en la base de datos, por favor introduzca otro";
	public static final String ERROR_CIFNIF_EXISTENTE_CLIENTE = "El NIF introducido ya existe para ese cliente en la base de datos, por favor introduzca otro";
	
	public static final String ERROR_TAMANO_MAXIMO = "El NIF introducido ha superado los carácteres máximos";
	
	public static final String ERROR_NOMBRE_CLIENTE_OBLIGATORIO = "El campo NombreCliente no puede enviarse vacio";
	public static final String ERROR_NOMBRE_CLIENTE_TIPO_DE_DATO_INCORRECTO = "Asegurese que el dato en nombreCliente es un texto";
	public static final String ERROR_NOMBRE_CLIENTE_TAMANO_MAXIMO = "El campo NombreCliente ha superado su tamaño máximo de carácteres";
	
	public static final String ERROR_DIRECCION_FACTURACION_OBLIGATORIO = "El campo DireccionFacturacion no puede enviarse vacio";
	public static final String ERROR_DIRECCION_FACTURACION_TIPO_DE_DATO_INCORRECTO = "Asegurese que el dato en DireccionFacturacion es un texto";
	public static final String ERROR_DIRECCION_FACTURACION_TAMANO_MAXIMO = "El campo DireccionFacturacion ha superado su tamaño máximo de carácteres";
	
	
	public static final String ERROR_NOMBRE_DESTINATARIO_OBLIGATORIO = "El campo NombreDestinatario no puede enviarse vacio";
	public static final String ERROR_NOMBRE_DESTINATARIO_TIPO_DE_DATO_INCORRECTO = "Asegurese que el dato en NombreDestinatario es un texto";
	public static final String ERROR_NOMBRE_DESTINATARIO_TAMANO_MAXIMO = "El campo NombreDestinatario ha superado su tamaño máximo de carácteres";
	public static final String ERROR_CLIENTE_CREAR_DESTINATARIO = "El id del Cliente al que hace referencia no existe en la base de datos, revíselo de nuevo";
	public static final String ERROR_CLIENTE_COMPROBAR_DESTINATARIO = "El id del Cliente al que hace referencia no tiene destinatarios asociados, revíselo de nuevo";
	
	
	public static final String ERROR_DIRECCION_COMPLETA_OBLIGATORIO = "El campo DireccionCompleta no puede enviarse vacio";
	public static final String ERROR_DIRECCION_COMPLETA_TIPO_DE_DATO_INCORRECTO = "Asegurese que el dato en DireccionCompleta es un texto";
	public static final String ERROR_DIRECCION_COMPLETA_TAMANO_MAXIMO = "El campo DireccionCompleta ha superado su tamaño máximo de carácteres";
	
	public static final String ERROR_CODIGO_POSTAL_OBLIGATORIO = "El campo Código Postal no puede enviarse vacio";
	public static final String ERROR_CODIGO_POSTAL_TIPO_DE_DATO_INCORRECTO = "Asegurese que el dato en Código Postal es numérico";
	public static final String ERROR_CODIGO_POSTAL_TAMANO_MAXIMO = "El campo Código Postal debe tener únicamente 5 dígitos";
	
	public static final String ERROR_ESTADO_ENVIO_TIPO = "Este campo tiene que ser numérico, por favor revisa el formato enviado";
	public static final String ERROR_ESTADO_ENVIO_TEXTO = "El valor del campo idEstadoEnvio es erroneo, por favor introduzca un valor valido";
	public static final String ERROR_PESO_VACIO = "El valor del campo peso no es aceptado, por favor revise los datos introducidos";	
	public static final String ERROR_PESO_NUMERICO = "El valor del campo peso debe ser numérico, por favor revise los datos introducidos";	
	public static final String ERROR_PESO_LONGITUD_MAXIMA = "El valor del campo peso no debe ser mayor a 4 dígitos , por favor revise los datos introducidos";	
	public static final String ERROR_ENTEGA_NUMERICO = "El valor del campo intentos de entrega no debe ser mayor a 2 dígitos , por favor revise los datos introducidos";	
	public static final String ERROR_CLIENTE_COMPROBAR_DESTINATARIO_UNICO = "El id del Cliente al que hace referencia no tiene ese id de destinatario asociado, revíselo de nuevo";
	public static final String ERROR_ESTADO_PEDIDO = "El estado que indica no se encuentra entre los valores permitidos, por favor íntentelo de nuevo";
}
