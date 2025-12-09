export interface Animal {
  id?: number;
  nome: string;
  especie: string;
  raca: string;
  idade: number;
  descricao: string;
  status: string;
  fotoUrl?: string;
  vacinado?: boolean;
  castrado?: boolean;
  urgente?: boolean;
  localizacao: string;
  sexo?: string;
}
