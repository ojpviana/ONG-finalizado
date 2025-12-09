package com.protecao.animais.repository; 

import org.springframework.data.jpa.repository.JpaRepository;

import com.protecao.animais.model.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    
}