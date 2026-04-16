# Sistema de Clínica Médica

Um sistema completo e funcional para gerenciamento de clínica médica, desenvolvido com **Spring Boot** no back-end e ** no front-end *html*, *css*, *js*, e **Docker Compose** para iniciar o serviço MySQL.

## 🎯 Funcionalidades

- ✅ **Gestão de Médicos**: CRUD completo com especialidade, CRM e contato
- ✅ **Gestão de Pacientes**: Cadastro e gerenciamento de pacientes com dados pessoais
- ✅ **Agendamento de Consultas**: Agendar consultas entre médicos e pacientes
- ✅ **Dashboard**: Visualização de estatísticas em tempo real
- ✅ **API RESTful**: Endpoints bem estruturados e documentados
- ✅ **Interface Responsiva**: Front-end moderno e intuitivo


### Instalação

1. **Clone ou extraia o projeto:**
```bash
cd ClinicaMedica
```

2. **Inicie os serviços:**
```bash
docker-compose up -d
```

3. **Aguarde a inicialização (1-2 minutos):**
```bash
docker-compose ps
```

4. **Acesse a aplicação:**
- **Backend API**: http://localhost:8080/index.html
- **Banco de Dados**: localhost:3306

## 📊 Dados de Teste

O banco de dados é inicializado automaticamente com dados de teste:

| Tipo | Quantidade |
|------|-----------|
| Médicos | 3 |
| Pacientes | 3 |
| Consultas | 3 |

## 🔌 Endpoints da API

### Médicos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/medicos` | Listar todos |
| GET | `/api/medicos/{id}` | Obter por ID |
| POST | `/api/medicos` | Criar novo |
| PUT | `/api/medicos/{id}` | Atualizar |
| DELETE | `/api/medicos/{id}` | Deletar |
| GET | `/api/medicos/especialidade/{esp}` | Listar por especialidade |

### Pacientes

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/pacientes` | Listar todos |
| GET | `/api/pacientes/{id}` | Obter por ID |
| POST | `/api/pacientes` | Criar novo |
| PUT | `/api/pacientes/{id}` | Atualizar |
| DELETE | `/api/pacientes/{id}` | Deletar |
| GET | `/api/pacientes/buscar?nome=X` | Buscar por nome |

### Consultas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/consultas` | Listar todas |
| GET | `/api/consultas/{id}` | Obter por ID |
| POST | `/api/consultas` | Criar nova |
| PUT | `/api/consultas/{id}` | Atualizar |
| DELETE | `/api/consultas/{id}` | Deletar |
| GET | `/api/consultas/medico/{id}` | Listar por médico |
| GET | `/api/consultas/paciente/{id}` | Listar por paciente |

## 📝 Exemplo de Requisição

### Criar um Médico

```bash
curl -X POST http://localhost:8080/api/medicos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Dr. Carlos Silva",
    "crm": "123456/SP",
    "especialidade": "Clínica Geral",
    "telefone": "11987654321",
    "email": "carlos@clinica.com"
  }'
```

### Criar uma Consulta

```bash
curl -X POST http://localhost:8080/api/consultas \
  -H "Content-Type: application/json" \
  -d '{
    "medicoId": 1,
    "pacienteId": 1,
    "dataHora": "2026-04-15T14:30:00",
    "descricao": "Consulta de rotina"
  }'
```

## 🛑 Parando os Serviços

```bash
docker-compose down
```

Para remover volumes (limpar dados):
```bash
docker-compose down -v
```

## 📊 Verificar Status

```bash
# Ver status dos serviços
docker-compose ps

# Ver logs
docker-compose logs -f

# Ver logs de um serviço específico
docker-compose logs -f mysql
```

## 🔧 Configuração

### Variáveis de Ambiente (.env)

```env
# Database
DB_HOST=mysql
DB_PORT=3306
DB_NAME=clinica_db
DB_USERNAME=clinica_user
DB_PASSWORD=clinica_password



## 📚 Estrutura de Dados

### Tabelas

**medicos**
- id (PK)
- nome
- crm (UNIQUE)
- especialidade
- telefone
- email
- ativo

**pacientes**
- id (PK)
- nome
- cpf (UNIQUE)
- data_nascimento
- telefone
- email
- endereco
- ativo

**consultas**
- id (PK)
- medico_id (FK)
- paciente_id (FK)
- data_hora
- descricao
- diagnostico
- prescricao
- status (AGENDADA, REALIZADA, CANCELADA)


## 📄 Licença

Este projeto é fornecido como está para fins educacionais.

## 👨‍💻 Autor: Caio Querino S. Marques

Desenvolvido como sistema de exemplo de clínica médica.

---

**Versão**: 1.0.0  
**Data de Criação**: 2026-04-02  
**Status**: ✅ 100% Funcional
