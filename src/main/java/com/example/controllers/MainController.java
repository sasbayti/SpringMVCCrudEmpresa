package com.example.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.services.CorreoService;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;
import com.example.services.TelefonoService;


    
@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger LOG = Logger.getLogger("MainController");

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private TelefonoService telefonoService;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private CorreoService correoService;

    @GetMapping("/listar")
    public ModelAndView listar() {

        List<Empleado> empleados = empleadoService.findAll();

        ModelAndView mav = new ModelAndView("views/listarEmpleados");
        mav.addObject("empleados", empleados);

        return mav;
    }
    @GetMapping("/frmAltaEmpleado")
    public String formularioAltaEmpleado(Model model) {

        Empleado empleado = new Empleado();

        List<Departamento> departamentos = departamentoService.findAll();

        model.addAttribute("empleado", empleado);
        model.addAttribute("departamentos", departamentos);

        return "views/formularioAltaEmpleado";
    }

    @PostMapping("/altaModificacionEmpleado")
    /** Metodo que recibe los datos procedente de los controles del formulario */
    public String altaEmpleado(@ModelAttribute Empleado empleado,
            @RequestParam(name = "numerosTelefonos") String telefonosRecibidos, 
            @RequestParam(name = "listadoCorreos") String correosRecibidos) { 

        LOG.info("Los telefonos recibidos son: " + telefonosRecibidos);
        
        empleadoService.save(empleado);
        
        List<String> listadoNumerosTelefonos = null; // Como ha sido declarada aqui, ya no necesito hacerlo despues

        if (telefonosRecibidos != null) {
            String[] arrayTelefonos = telefonosRecibidos.split(";"); /* Aqui separo por punto y coma */

            listadoNumerosTelefonos = Arrays.asList(arrayTelefonos); 
        }
        List<String> listadoCorreos = null; 
        if (correosRecibidos != null) {
            String[] arrayCorreos = correosRecibidos.split(";"); 

            listadoCorreos = Arrays.asList(arrayCorreos); 
        }
        // Borrar todos los telefonos que tenga el empleado, si hay que insertar nuevos
        if(telefonosRecibidos != null){
            String[] arrayTelefonos = telefonosRecibidos.split(";");
            listadoNumerosTelefonos = Arrays.asList(arrayTelefonos);
        }
        if (listadoNumerosTelefonos != null) {
           telefonoService.deleteByEmpleado(empleado);
            listadoNumerosTelefonos.stream().forEach(n -> {
                Telefono telefonoObject = Telefono.builder().numero(n).empleado(empleado)
                        .build();

                telefonoService.save(telefonoObject);
            });
        }
        // Borrar todos los correos que tenga el empleado, si hay que insertar nuevos
        if(correosRecibidos != null){
            String[] arrayCorreos = correosRecibidos.split(";");
            listadoCorreos = Arrays.asList(arrayCorreos);
        }
        if (listadoCorreos != null) {
           correoService.deleteByEmpleado(empleado);
            listadoCorreos.stream().forEach(n -> {
                Correo correoObject = Correo.builder().email(n).empleado(empleado)
                        .build();

                correoService.save(correoObject);
            });
        }
        return "redirect:/listar";
    }
    @GetMapping("/frmActualizar/{id}")
    public String frmActualizaEmpleado(@PathVariable(name = "id") int idEmpleado, Model model) {

        Empleado empleado = empleadoService.findById(idEmpleado);
        List<Telefono> todostelefonos = telefonoService.findAll();
        List<Telefono> telefonosDelEmpleado = todostelefonos.stream().filter(t -> t.getEmpleado()
                .getId() == idEmpleado).collect(Collectors.toList());
    
        String numerosDeTelefono = telefonosDelEmpleado.stream().map(t ->t.getNumero()).collect(Collectors.joining(";")); 

        List<Correo> todosCorreos = correoService.findAll();
        List<Correo> correosDelEstudiante = todosCorreos.stream().filter(t -> t.getEmpleado()
                .getId() == idEmpleado).collect(Collectors.toList());
    
        String listadoCorreos = correosDelEstudiante.stream().map(t ->t.getEmail()).collect(Collectors.joining(";")); 

        List<Departamento> departamentos = departamentoService.findAll();

        model.addAttribute("empleado", empleado); 
        model.addAttribute("telefonos", numerosDeTelefono);
        model.addAttribute("correos", listadoCorreos);
        model.addAttribute("departamentos", departamentos);

        return "views/formularioAltaEmpleado";
        
    }
    @GetMapping("/borrar/{id}")
        public String borrarEmpleado(@PathVariable(name = "id") int idEmpleado){
           empleadoService.delete(empleadoService.findById(idEmpleado));
            return "redirect:/listar";
        
        }

    // Metodo que muestra los detalles
    @GetMapping("/detalles/{id}")
        public String detallesEmpleado(@PathVariable(name = "id") int idEmpleado, Model model){
          
            Empleado empleado = empleadoService.findById(idEmpleado);
        List<Telefono> todostelefonos = telefonoService.findByEmpleado(empleado);

        List<String> telefonosDelEmpleado = todostelefonos.stream()
        .map(t -> t.getNumero()).toList();

        List<Correo> correos = correoService.findByEmpleado(empleado);

        List<String> correosDelEmpleado = correos.stream()
        .map(t -> t.getEmail()).toList();
        
            model.addAttribute("empleado", empleado); // para que te traiga el formulario con los datos llenos
            model.addAttribute("telefonos", telefonosDelEmpleado);
            model.addAttribute("correos", correosDelEmpleado);
            // Lo que va dentro de "" es el nombre que tengo dentro del view
         
            return "views/detallesEmpleado";
        }
}
