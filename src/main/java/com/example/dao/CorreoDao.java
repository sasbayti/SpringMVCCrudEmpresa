package com.example.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Empleado;

public interface CorreoDao extends JpaRepository<Empleado, Integer> {
    
}
