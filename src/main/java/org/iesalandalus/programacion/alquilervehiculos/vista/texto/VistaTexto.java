package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaTexto extends Vista {

	public void comenzar() {
		Accion accion;
		Accion.setVista(this);
		do {
			Consola.mostrarMenuAcciones();
			accion = Consola.elegirAccion();
			try {
				Consola.mostrarCabezera(accion.toString());
				accion.ejecutar();
			} catch (IllegalArgumentException | OperationNotSupportedException e) {
				System.out.printf("%n%s%n%n", e.getMessage());
			}
		} while (!(accion.equals(Accion.SALIR)));

	}

	public void terminar() {
		System.out.print("Adios");
	}

	public void insertarCliente() throws OperationNotSupportedException {
		Cliente cliente = Consola.leerCliente();
		getControlador().insertar(cliente);
	}

	public void insertarVehiculo() throws OperationNotSupportedException {
		Vehiculo vehiculo = Consola.leerVehiculo();
		getControlador().insertar(vehiculo);

	}

	public void insertarAlquiler() {
		Alquiler alquiler = Consola.leerAlquiler();
		try {
			getControlador().insertar(alquiler);
		} catch (OperationNotSupportedException e) {
			System.out.print(e.getMessage());
		}

	}

	public void buscarCliente() {
		Cliente cliente = Consola.leerClienteDni();
		System.out.printf("%s%n", (getControlador().buscar(cliente) == null) ? "Cliente no encontrado"
				: getControlador().buscar(cliente));
	}

	public void buscarVehiculo() {
		Vehiculo vehiculo = Consola.leerTurismoMatricula();
		System.out.printf("%s%n", (getControlador().buscar(vehiculo) == null) ? "Turismo no encontrado"
				: getControlador().buscar(vehiculo));
	}

	public void buscarAlquiler() {
		Alquiler alquiler = Consola.leerAlquiler();
		System.out.printf("%s%n", (getControlador().buscar(alquiler) == null) ? "Alquiler no encontrado"
				: getControlador().buscar(alquiler));
	}

	public void modificarCliente() throws OperationNotSupportedException {
		Cliente cliente = Consola.leerClienteDni();
		String nombre = Consola.leerNombre();
		String telefono = Consola.leerTelefono();

		getControlador().modificar(cliente, nombre, telefono);
	}

	public void devolverAlquilerCliente() {
		Cliente cliente = Consola.leerClienteDni();
		LocalDate fechaDevolucion = Consola.leerFechaDevolucion();

		try {
			getControlador().devolver(cliente, fechaDevolucion);
		} catch (OperationNotSupportedException e) {
			e.getMessage();
		}
	}

	public void devolverAlquilerVehiculo() {
		Vehiculo vehiculo = Consola.leerTurismoMatricula();
		LocalDate fechaDevolucion = Consola.leerFechaDevolucion();

		try {
			getControlador().devolver(vehiculo, fechaDevolucion);
		} catch (OperationNotSupportedException e) {
			e.getMessage();
		}
	}

	public void borrarCliente() {
		Cliente cliente = Consola.leerClienteDni();
		try {
			getControlador().borrar(cliente);
		} catch (OperationNotSupportedException e) {
			e.getMessage();
		}
	}

	public void borrarVehiculo() {
		Vehiculo vehiculo = Consola.leerTurismoMatricula();
		try {
			getControlador().borrar(vehiculo);
		} catch (OperationNotSupportedException e) {
			e.getMessage();
		}
	}

	public void borrarAlquiler() throws OperationNotSupportedException {
		Alquiler alquiler = Consola.leerAlquiler();
		getControlador().borrar(alquiler);
	}

	public void listarClientes() {
		List<Cliente> clientes = getControlador().getClientes();
		clientes.sort(Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni));
		for (int i = 0; i < clientes.size(); i++) {
			System.out.printf("%d. %s%n", i + 1, clientes.get(i));
		}
	}

	public void listarVehiculos() {
		List<Vehiculo> vehiculos = getControlador().getTurismos();
		vehiculos.sort(Comparator.comparing(Vehiculo::getMarca).thenComparing(Vehiculo::getModelo)
				.thenComparing(Vehiculo::getMatricula));
		for (int i = 0; i < vehiculos.size(); i++) {
			System.out.printf("%d. %s%n", i + 1, vehiculos.get(i));
		}
	}

	public void listarAlquileres() {
		List<Alquiler> alquileres = getControlador().getAlquileres();
		alquileres.sort(Comparator.comparing(Alquiler::getFechaAlquiler));
		for (int i = 0; i < alquileres.size(); i++) {
			System.out.printf("%d. %s%n", i + 1, alquileres.get(i));
		}
	}

	public void listarAlquileresCliente() {
		Cliente cliente = Consola.leerClienteDni();
		List<Alquiler> alquileres = getControlador().getAlquileres(cliente);
		for (int i = 0; i < alquileres.size(); i++) {
			System.out.printf("%d. %s%n", i + 1, alquileres.get(i));
		}
	}

	public void listarAlquileresVehiculo() {
		Vehiculo vehiculo = Consola.leerTurismoMatricula();
		List<Alquiler> alquileres = getControlador().getAlquileres(vehiculo);
		for (int i = 0; i < alquileres.size(); i++) {
			System.out.printf("%d. %s%n", i + 1, alquileres.get(i));
		}
	}

	private Map<TipoVehiculo, Integer> inicializarEstadisticas() {
		Map<TipoVehiculo, Integer> estadisticas = new TreeMap<>();
		LocalDate mes = Consola.leerMes();

		List<Alquiler> alquileres = getControlador().getAlquileres();

		for (Alquiler alquiler : alquileres) {
			if (alquiler.getFechaAlquiler().getMonth().equals(mes.getMonth())) {
				estadisticas.put(TipoVehiculo.get(alquiler.getVehiculo()),
						estadisticas.containsKey(TipoVehiculo.get(alquiler.getVehiculo()))
								? estadisticas.get(TipoVehiculo.get(alquiler.getVehiculo())) + 1
								: 1);

			}
		}

		return estadisticas;
	}

	public void mostrarEstadisticasMensuales() {
		Map<TipoVehiculo, Integer> estadisticas = inicializarEstadisticas();
		for (Map.Entry<TipoVehiculo, Integer> entry : estadisticas.entrySet()) {
		    System.out.printf("%s alquilados: %s%n", entry.getKey(), entry.getValue());
		}
	}
}
