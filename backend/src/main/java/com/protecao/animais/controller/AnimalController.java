package com.protecao.animais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.protecao.animais.model.Animal;
import com.protecao.animais.model.Ong;
import com.protecao.animais.repository.AnimalRepository; 
import com.protecao.animais.repository.OngRepository;

@RestController
@RequestMapping("/animais")
@CrossOrigin(origins = "http://localhost:4200")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private OngRepository ongRepository;

    @PostMapping
    public ResponseEntity<Animal> salvar(@RequestBody Animal animal) {
        

        if (animal.getOng() == null) {

            Ong ongPadrao = ongRepository.findById(1L).orElse(null);


            if (ongPadrao == null) {
                ongPadrao = new Ong();

                ongPadrao.setId(1L); 
                ongPadrao.setNome("ONG de Teste");
                ongPadrao.setEmail("teste@ong.com");


                ongPadrao = ongRepository.save(ongPadrao);
            }
            

            animal.setOng(ongPadrao);
        }


        if (animal.getId() != null && animal.getId() == 0) {
            animal.setId(null);
        }

        Animal animalSalvo = animalRepository.save(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(animalSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Animal>> listarTodos() {
        return ResponseEntity.ok(animalRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> buscarPorId(@PathVariable Long id) {
        return animalRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> atualizar(@PathVariable Long id, @RequestBody Animal animal) {
        if (!animalRepository.existsById(id)) return ResponseEntity.notFound().build();
        animal.setId(id);
        return ResponseEntity.ok(animalRepository.save(animal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!animalRepository.existsById(id)) return ResponseEntity.notFound().build();
        animalRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}