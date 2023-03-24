package com.example;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.entities.Empleado.Genero;
import com.example.services.CorreoService;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;
import com.example.services.TelefonoService;

@SpringBootApplication
public class SpringMvcCrudEmpresaApplication implements CommandLineRunner{
	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	private EmpleadoService empleadoService;

	@Autowired
	private TelefonoService telefonoService;
	@Autowired
	private CorreoService correoService;

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcCrudEmpresaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/**
		 * Agregar registros de muestra, para Facultad, Estudiante y Telefono
		 */

		departamentoService.save(
			Departamento.builder()
			.nombre("RRHH")
			.build()
		);

		departamentoService.save(
			Departamento.builder()
			.nombre("FINANZAS")
			.build()
		);	
		empleadoService.save(
			Empleado.builder()
			.id(1)
			.nombre("Elisabet")
			.apellidos("Agulló Garcia")
			.fechaAlta(LocalDate.of(2018, Month.SEPTEMBER, 1))
			.genero(Genero.MUJER)
			.departamento(departamentoService.findById(1))
			.build()
		);
		telefonoService.save(
			Telefono.builder()
			.id(1)
			.numero("677654789")
			.empleado(empleadoService.findById(1))
			.build()
		);
		
		correoService.save(
			Correo.builder()
			.id(1)
			.email("salam@jhskfhdsg.com")
			.empleado(empleadoService.findById(1))
			.build()
		);

	}

}
