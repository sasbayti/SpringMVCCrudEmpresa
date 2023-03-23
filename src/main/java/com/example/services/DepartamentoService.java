package com.example.services;

import java.util.List;

import com.example.entities.Departamento;
import com.example.entities.Empleado;

public interface DepartamentoService {
    public List<Departamento> findAll();
    public Empleado findById(int idDepartamento);
    public void save(Departamento departamento);
    public void deleteById(int idDepartamento);
    public void delete(Departamento departamentoo);
}
