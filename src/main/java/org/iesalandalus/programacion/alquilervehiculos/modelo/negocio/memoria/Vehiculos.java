package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

public class Vehiculos implements IVehiculos {

	private List<Vehiculo> coleccionVehiculos;

	public Vehiculos() {
		coleccionVehiculos = new ArrayList<>();
	}

	@Override
	public List<Vehiculo> get() {
		List<Vehiculo> copiaVehiculos = new ArrayList<>();
		for (Vehiculo vehiculo : coleccionVehiculos) {
			copiaVehiculos.add(vehiculo);
		}
		return copiaVehiculos;
	}

	@Override
	public int getCantidad() {
		return coleccionVehiculos.size();
	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede insertar un vehículo nulo.");
		}
		if (coleccionVehiculos.contains(vehiculo)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un vehículo con esa matrícula.");
		}
		coleccionVehiculos.add(vehiculo);
	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede buscar un vehículo nulo.");
		}
		if (!(coleccionVehiculos.contains(vehiculo))) {
			return null;
		}
		return coleccionVehiculos.get(coleccionVehiculos.indexOf(vehiculo));
	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede borrar un vehículo nulo.");
		}
		if (!coleccionVehiculos.remove(buscar(vehiculo))) {
			throw new OperationNotSupportedException("ERROR: No existe ningún vehículo con esa matrícula.");
		}
	}

}
