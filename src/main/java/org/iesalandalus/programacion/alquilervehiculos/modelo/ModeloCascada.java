package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;

public class ModeloCascada extends Modelo {

	public ModeloCascada(IFuenteDatos fuenteDatos) {
		setFuenteDatos(fuenteDatos);
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {

		getClientes().insertar(new Cliente(cliente));

	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {

		getVehiculos().insertar(Vehiculo.copiar(vehiculo));

	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede realizar un alquiler nulo.");
		}
		if (getClientes().buscar(alquiler.getCliente()) == null) {
			throw new OperationNotSupportedException("ERROR: No existe el cliente del alquiler.");
		}
		if (getVehiculos().buscar(alquiler.getVehiculo()) == null) {
			throw new OperationNotSupportedException("ERROR: No existe el turismo del alquiler.");
		}

		getAlquileres().insertar(new Alquiler(getClientes().buscar(alquiler.getCliente()),
				getVehiculos().buscar(alquiler.getVehiculo()), alquiler.getFechaAlquiler()));

	}

	@Override
	public Cliente buscar(Cliente cliente) {
		if (getClientes().buscar(cliente) == null) {
			return null;

		}
		return new Cliente(getClientes().buscar(cliente));
	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		if (getVehiculos().buscar(vehiculo) == null) {
			return null;

		}
		return Vehiculo.copiar(getVehiculos().buscar(vehiculo));
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		if (getAlquileres().buscar(alquiler) == null) {
			return null;

		}
		return new Alquiler(getAlquileres().buscar(alquiler));
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {

		getClientes().modificar(cliente, nombre, telefono);

	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {

		getAlquileres().devolver(cliente, fechaDevolucion);

	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {

		getAlquileres().devolver(vehiculo, fechaDevolucion);

	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {

		for (Alquiler alquiler : getAlquileres().get(cliente)) {

			getAlquileres().borrar(alquiler);

		}
		getClientes().borrar(cliente);

	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		for (Alquiler alquiler : getAlquileres().get(vehiculo)) {

			getAlquileres().borrar(alquiler);

		}
		getVehiculos().borrar(vehiculo);

	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {

		getAlquileres().borrar(alquiler);

	}

	@Override
	public List<Cliente> getListaClientes() {
		List<Cliente> copiaClientes = new ArrayList<>();
		for (Cliente cliente : getClientes().get()) {
			copiaClientes.add(new Cliente(cliente));
		}
		return copiaClientes;
	}

	@Override
	public List<Vehiculo> getListaVehiculos() {
		List<Vehiculo> copiaTurismos = new ArrayList<>();
		for (Vehiculo vehiculo : getVehiculos().get()) {
			copiaTurismos.add(Vehiculo.copiar(vehiculo));
		}
		return copiaTurismos;
	}

	@Override
	public List<Alquiler> getListaAlquileres() {
		List<Alquiler> copiaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : getAlquileres().get()) {
			copiaAlquileres.add(new Alquiler(alquiler));
		}
		return copiaAlquileres;
	}

	@Override
	public List<Alquiler> getListaAlquileres(Cliente cliente) {
		List<Alquiler> copiaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : getAlquileres().get(cliente)) {
			copiaAlquileres.add(new Alquiler(alquiler));
		}
		return copiaAlquileres;
	}

	@Override
	public List<Alquiler> getListaAlquileres(Vehiculo vehiculo) {
		List<Alquiler> copiaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : getAlquileres().get(vehiculo)) {
			copiaAlquileres.add(new Alquiler(alquiler));
		}
		return copiaAlquileres;
	}
}
