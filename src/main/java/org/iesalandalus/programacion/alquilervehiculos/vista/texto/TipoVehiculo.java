package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import javax.naming.OperationNotSupportedException;

public enum TipoVehiculo {

	TURISMO("Turismo"), AUTOBUS("Autobus"), FURGONETA("Furgoneta");
	
	private String nombre;
	
	private TipoVehiculo(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if (nombre.isBlank()) {
			throw new IllegalArgumentException("ERROR: El nombre no puede estar en blanco.");
		}
		this.nombre = nombre;
	}
	private static boolean esOrdinalValido(int ordinal) {
		return ordinal >= 0 && TipoVehiculo.values().length > ordinal;
	}
	 
	public static TipoVehiculo get(int ordinal) throws OperationNotSupportedException {
		if (!(esOrdinalValido(ordinal))) {
			throw new OperationNotSupportedException("ERROR: El ordinal no es valido.");
		}
		return TipoVehiculo.values()[ordinal];
	}
	@Override
	public String toString() {
		return String.format("%d. %s", ordinal(),  nombre);
	}
}
