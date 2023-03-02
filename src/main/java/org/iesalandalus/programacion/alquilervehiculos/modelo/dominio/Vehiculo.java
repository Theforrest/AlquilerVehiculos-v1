package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.util.Objects;

import org.iesalandalus.programacion.alquilervehiculos.vista.texto.TipoVehiculo;

public abstract class Vehiculo {
	private static final String ER_MARCA = "^([A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)( [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$|^([A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)(\\-[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)$|^([A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)([A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$|^([A-ZÁÉÍÓÚÑ]+)+$";
	private static final String ER_MATRICULA = "\\d{4}[QWRTYPSDFGHJKLZXCVBNM]{3}";

	private String marca;
	private String modelo;
	private String matricula;

	protected Vehiculo(String marca, String modelo, String matricula) {
		setMarca(marca);
		setModelo(modelo);
		setMatricula(matricula);
	}

	protected Vehiculo(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No es posible copiar un vehículo nulo.");
		}
		
			setMarca(vehiculo.getMarca());
		
			setModelo(vehiculo.getModelo());

			setMatricula(vehiculo.getMatricula());

		
	}

	public static Vehiculo copiar(Vehiculo vehiculo) {

		TipoVehiculo tipoVehiculo = TipoVehiculo.get(vehiculo);
		if (tipoVehiculo.equals(TipoVehiculo.TURISMO)) {
			return new Turismo((Turismo)vehiculo);
		} else if (tipoVehiculo.equals(TipoVehiculo.AUTOBUS)) {
			return new Autobus((Autobus)vehiculo);
		} else if (tipoVehiculo.equals(TipoVehiculo.FURGONETA)) {
			return new Furgoneta((Furgoneta)vehiculo);
		} 
		return null;
		
	}

	public static Vehiculo getVehiculoConMatricula(String matricula) {
		return new Vehiculo("A", "A", matricula) {

			@Override
			public int getFactorPrecio() {
				// TODO Auto-generated method stub
				return 0;
			}
		};

	}

	public abstract int getFactorPrecio();

	public String getMarca() {
		return marca;
	}

	private void setMarca(String marca) {
		if (marca == null) {
			throw new NullPointerException("ERROR: La marca no puede ser nula.");
		}
		if (!marca.matches(ER_MARCA)) {
			throw new IllegalArgumentException("ERROR: La marca no tiene un formato válido.");
		}
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	private void setModelo(String modelo) {
		if (modelo == null) {
			throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
		}
		if (modelo.isBlank()) {
			throw new IllegalArgumentException("ERROR: El modelo no puede estar en blanco.");
		}
		this.modelo = modelo;
	}

	public String getMatricula() {
		return matricula;
	}

	private void setMatricula(String matricula) {
		if (matricula == null) {
			throw new NullPointerException("ERROR: La matrícula no puede ser nula.");
		}
		if (!matricula.matches(ER_MATRICULA)) {
			throw new IllegalArgumentException("ERROR: La matrícula no tiene un formato válido.");
		}
		this.matricula = matricula;
	}

	@Override
	public int hashCode() {
		return Objects.hash(matricula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Vehiculo))
			return false;
		Vehiculo other = (Vehiculo) obj;
		return Objects.equals(matricula, other.matricula);
	}

	
	
}
