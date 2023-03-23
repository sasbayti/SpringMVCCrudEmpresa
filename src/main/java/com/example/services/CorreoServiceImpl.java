package com.example.services;

import java.util.List;

import com.example.dao.CorreoDao;
import com.example.entities.Correo;
import com.example.entities.Empleado;

public class CorreoServiceImpl implements CorreoService {

    private CorreoDao correoDao;
    @Override
    public List<Correo> findAll() {
        return correoDao.findAll();
    }

    @Override
    public Correo findById(int idCorreo) {
        return correoDao.findById(idCorreo);
    }

    @Override
    public void save(Correo correo) {
        correoDao.save(correo);
    }

    @Override
    public void deleteById(int idCorreo) {
        correoDao.deleteById(idCorreo);
    }

    @Override
    public void deleteByEmpleado(Empleado empleado) {
        correoDao.deleteByEmpleado(empleado);
    }

    @Override
    public List<Correo> findByEmpleado(Empleado empleado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByEstudiante'");
    }
    
}
