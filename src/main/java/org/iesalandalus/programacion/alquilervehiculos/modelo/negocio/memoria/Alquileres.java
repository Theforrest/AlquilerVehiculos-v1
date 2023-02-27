package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;

public class Alquileres implements IAlquileres{



	private List<Alquiler> coleccionAlquileres;

	public Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}

	public List<Alquiler> get() {
		List<Alquiler> copiaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			copiaAlquileres.add(alquiler);
		}
		return copiaAlquileres;
	}

	public List<Alquiler> get(Cliente cliente) {
		
		List<Alquiler> copiaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {
				copiaAlquileres.add(alquiler);
			}	
		}
		return copiaAlquileres;
	}
	
	public List<Alquiler> get(Vehiculo vehiculo) {
		
		List<Alquiler> copiaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getVehiculo().equals(vehiculo)) {
				copiaAlquileres.add(alquiler);
			}	
		}
		return copiaAlquileres;
	}
	
	public int getCantidad() {
		return coleccionAlquileres.size();
	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler) throws OperationNotSupportedException {
		
		for (Alquiler alquiler : get(cliente)) {
			if (alquiler.getFechaDevolucion() == null) {
				throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
			}	
			if (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)|| alquiler.getFechaDevolucion().isEqual(fechaAlquiler)) {
				throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
			}	
		}
		for (Alquiler alquiler : get(vehiculo)) {
			if (alquiler.getFechaDevolucion() == null) {
				throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");
			}	
			if (alquiler.getFechaDevolucion().isAfter(fechaAlquiler) || alquiler.getFechaDevolucion().isEqual(fechaAlquiler)) {
				throw new OperationNotSupportedException("ERROR: El turismo tiene un alquiler posterior.");
			}	
		}
	}
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}
		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
		coleccionAlquileres.add(alquiler);
	}
	
	

	public Alquiler buscar(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}
		if (!(coleccionAlquileres.contains(alquiler))) {
			return null;
		}
		return coleccionAlquileres.get(coleccionAlquileres.indexOf(alquiler));
	}

	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}
		if (!(coleccionAlquileres.contains(alquiler))) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
		coleccionAlquileres.remove(buscar(alquiler));
		
	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		Alquiler alquiler = getAlquilerAbierto(cliente);
		if (alquiler == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
		}
		alquiler.devolver(fechaDevolucion);
		
	}
	
	private Alquiler getAlquilerAbierto(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}
		Iterator<Alquiler> iterador = get(cliente).iterator();

		while (iterador.hasNext()) {
			Alquiler alquiler = iterador.next();
			if (alquiler.getFechaDevolucion() == null) {
				return alquiler;
			}
		}
		
		return null;
	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		Alquiler alquiler = getAlquilerAbierto(vehiculo);
		if (alquiler == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
		alquiler.devolver(fechaDevolucion);
		
	}
	
	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}
		Iterator<Alquiler> iterador = get(vehiculo).iterator();

		while (iterador.hasNext()) {
			Alquiler alquiler = iterador.next();
			if (alquiler.getFechaDevolucion() == null) {
				return alquiler;
			}
		}
		
		return null;
	}

	


}
