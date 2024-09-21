

# Aplicativo Mobile SEMOC

Este é o aplicativo oficial da Semana de Mobilização Científica (SEMOC) desenvolvido para Android. O objetivo do app é facilitar o acesso às informações do evento, como palestras, minicursos e dados sobre os palestrantes.

## Tecnologias Utilizadas
- **Java**: Linguagem de programação principal.
- **Padrão MVVM**: Arquitetura de software para separar lógica de negócios, dados e interface.
- **Room**: Biblioteca para persistência de dados locais, permitindo o uso offline.
- **API REST (JSON)**: Integração com APIs externas para consumo de dados sobre o evento.

## Funcionalidades
- Exibição de palestras e minicursos da SEMOC.
- Detalhamento de palestrantes e instrutores.
- Filtro por data para facilitar a busca de eventos.
- Armazenamento local de dados com Room para acesso offline.
- Notificações locais para alertar sobre eventos programados.

## Instalação
1. Clone este repositório:
   ```bash
   git clone https://github.com/seuusuario/seu-repositorio.git
   ```
2. Abra o projeto no **Android Studio**.
3. Compile e rode o projeto em um emulador ou dispositivo real.

## API
- **Palestras**: [palestras.json](https://raw.githubusercontent.com/ucsal/semoc/main/api/palestras.json)
- **Minicursos**: [minicursos.json](https://raw.githubusercontent.com/ucsal/semoc/main/api/minicursos.json)
- **Pessoas**: [pessoas.json](https://raw.githubusercontent.com/ucsal/semoc/main/api/pessoas.json)

## Licença
Este projeto está licenciado sob os termos da [Licença MIT](LICENSE).


