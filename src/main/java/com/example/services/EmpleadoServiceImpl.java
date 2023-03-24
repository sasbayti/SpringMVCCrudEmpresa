package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.EmpleadoDao;
import com.example.entities.Empleado;

import jakarta.transaction.Transactional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoDao empleadoDao;
    
    @Override
    public List<Empleado> findAll() {
       return empleadoDao.findAll();
    }

    @Override
    public Empleado findById(int idEmpleado) {
        return empleadoDao.findById(idEmpleado).get();
    }

    @Transactional
    @Override
    public void save(Empleado empleado) {
        empleadoDao.save(empleado);
    }

    @Transactional
    @Override
    public void deleteById(int idEmpleado) {
       empleadoDao.deleteById(idEmpleado);
    }

    @Transactional
    @Override
    public void delete(Empleado empleado) {
        empleadoDao.delete(empleado);
    }

    
    
}
