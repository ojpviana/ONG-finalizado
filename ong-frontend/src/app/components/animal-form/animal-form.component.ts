import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AnimalService } from '../../services/animal.service';
import { Animal } from '../../models/animal';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-animal-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './animal-form.component.html',
  styleUrls: ['./animal-form.component.css']
})
export class AnimalFormComponent {

  animal: Animal = {
    nome: '',
    especie: '',
    raca: '',
    idade: 0,
    descricao: '',
    status: 'Disponível',
    vacinado: false,
    castrado: false,
    fotoUrl: '',
    localizacao: ''
  };

  constructor(
    private animalService: AnimalService,
    private router: Router
  ) {}

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {

        this.animal.fotoUrl = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }


  salvarAnimal() {
    this.animalService.salvar(this.animal).subscribe({
      next: (res) => {
        alert('Animal cadastrado com sucesso!');
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error('Erro ao salvar', err);
        alert('Erro ao salvar animal.');
      }
    });
  }
}
