# EST-02
# inf012-consultorio
<h1 align="center" id="topo">Olá, seja bem vindo(a) à API E-Medical</h1>
Esse projeto consiste em uma API para gerenciar consultas medicas. Efetuamos a aplicação por meio de Spring Boot, usando CRUDs de Pacientes, Medicos, para que possam ser agendadas e canceladas as consultas. Para o banco de dados utilizamos o H2.


<h3 id="RQFuncionais">Requisitos Funcionais</h3>
<hr>
Os requisitos funcionais desta API estão divididos em três entidades: Medico, Paciente e Consulta. este sistema possibilita:
<h3>Medico:</h3>
<ul>
	<li>Cadastro de Medico: </li>
  	O sistema deve possuir uma funcionalidade de cadastro de médicos, na qual as seguintes informações deverão ser preenchidas:
      <ul>
        - Nome <br/>
        - E-mail <br/>
        - Telefone <br/>
        - CRM <br/>
        - Especialidade (Ortopedia, Cardiologia, Ginecologia ou Dermatologia) <br/>
        - Endereço completo (logradouro, número, complemento, bairro, cidade, UF e CEP) <br/>
      </ul>
  Todas as informações são de preenchimento obrigatório, exceto o número e o complemento do endereço.
</ul>
<ul>
<li>Listagem de Medicos: </li>
  O sistema deve possuir uma funcionalidade de listagem de médicos, na qual as seguintes informações, de cada um dos médicos cadastrados, deverão ser exibidas:
      <ul>
        - Nome <br/>
        - E-mail <br/>
        - CRM <br/>
        - Especialidade (Ortopedia, Cardiologia, Ginecologia ou Dermatologia) <br/>
        A listagem deve ser ordenada pelo nome do médico, de maneira crescente, bem como ser paginada, trazendo 10 registros por página. <br/>
      </ul>
</ul>

<ul>
		<li>Editar Medico: </li>
O sistema deve possuir uma funcionalidade de atualização de dados cadastrais de médicos, na qual as seguintes informações poderão ser atualizadas:
    <ul>
        - Nome <br/>
        - Telefone <br/>
        - Endereço <br/>
    </ul>
    <ul>
As seguintes regras de negócio devem ser validadas pelo sistema:
        - Não permitir a alteração do e-mail do médico; <br/>
        - Não permitir a alteração do CRM do médico; <br/>
        - Não permitir a alteração da Especialidade do médico;  <br/>
      </ul>
  </ul>
      

<ul>
		<li>Excluiir Medico: </li>
O sistema deve possuir uma funcionalidade que permita a exclusão de médicos cadastrados.
As seguintes regras de negócio devem ser validadas pelo sistema:      
  <ul>
        - A exclusão não deve apagar os dados do médico, mas torná-lo como "inativo" no sistema. <br/>
  </ul>
</ul>  
</ul>

<h3>Paciente:</h3>
<ul>
<li>Cadastro de Paciente: </li>
  	O sistema deve possuir uma funcionalidade de cadastro de pacientes, na qual as seguintes informações deverão ser preenchidas:
      <ul>
        - Nome <br/>
        - E-mail <br/>
        - Telefone <br/>
        - CPF <br/>
        - Endereço completo (logradouro, número, complemento, bairro, cidade, UF e CEP) <br/>
      Todas as informações são de preenchimento obrigatório, exceto o número e o complemento do endereço.
      </ul>

<li>Listagem de Paciente: </li>
	O sistema deve possuir uma funcionalidade de listagem de pacientes, na qual as seguintes informações, de cada um dos pacientes cadastrados, deverão ser exibidas:
	<ul>
        - Nome <br/>
        - E-mail <br/>
        - CPF <br/>
	A listagem deve ser ordenada pelo nome do paciente, de maneira crescente, bem como ser paginada, trazendo 10 registros por página.
      </ul>

 <li>Editar Paciente: </li>
	O sistema deve possuir uma funcionalidade de atualização de dados cadastrais de pacientes, na qual as seguintes informações poderão ser atualizadas:
	<ul>
        - Nome <br/>
        - Telefone <br/>
        - Endereço <br/>
	As seguintes regras de negócio devem ser validadas pelo sistema:
        - Não permitir a alteração do e-mail do paciente; <br/>
        - Não permitir a alteração do CPF do paciente; <br/>      
	</ul>      

 <li>Excluir Paciente: </li>
	O sistema deve possuir uma funcionalidade que permita a exclusão de pacientes cadastrados.
 	As seguintes regras de negócio devem ser validadas pelo sistema:
	<ul>
        - A exclusão não deve apagar os dados do paciente, mas torná-lo como "inativo" no sistema; <br/>      
	</ul>
</ul>
</ul>

<h3>Consulta:</h3>
<ul>
	<li>Marcação de Medico: </li>
	O sistema deve possuir uma funcionalidade que permita o agendamento de consultas, na qual as seguintes informações deverão ser preenchidas:
	<ul>
        - Paciente <br/>
        - Médico <br/>
        - Data/Hora da consulta <br/>]
	As seguintes regras de negócio devem ser validadas pelo sistema:
        - O horário de funcionamento da clínica é de segunda a sábado, das 07:00 às 19:00; <br/>
        - As consultas tem duração fixa de 1 hora; <br/>
        - As consultas devem ser agendadas com antecedência mínima de 30 minutos; <br/>
        - Não permitir o agendamento de consultas com pacientes inativos no sistema; <br/>
        - Não permitir o agendamento de consultas com médicos inativos no sistema; <br/>
        - Não permitir o agendamento de mais de uma consulta no mesmo dia para um mesmo paciente; <br/>
        - Não permitir o agendamento de uma consulta com um médico que já possui outra consulta agendada na mesma data/hora; <br/>
        - A escolha do médico é opcional, sendo que nesse caso o sistema deve escolher aleatoriamente algum médico disponível na data/hora preenchida; <br/>
      </ul>
</ul>
<hr>

<h3 id="endpoints">Endpoints</h3>
Os <b>endpoints</b>  dessa API podem ser acessada/requisitada por uma URL que executa uma certa função quando chamada. Sendo assim, cada endpoint listado abaixo, executa um determinado trecho de código.

<h4 align="center">Endpoints relacionados a Medico</h4>

***[GET]*** Listar Medicos:
```
http://localhost:8080/api/medicos?pagina=0
```
***[POST]*** Cadastra Medico:
```
http://localhost:8080/api/medicos
```
***[PUT]*** Atualizar Medico:
```
http://localhost:8080/api/medicos/{id}
```
***[DELETE]*** Deletar Medico:
```
http://localhost:8080/api/medicos/{id}
```
#### Exemplo Body Medico:

```
{
  "nome": "Dr. Andreia Lemos",
  "email": "dea@example.com",
  "telefone": "321323287",
  "crm": "13123",
  "especialidade": "DERMATOLOGIA",
  "status": true,
  "endereco": {
    "logradouro": "Rua A",
    "numero": "123",
    "complemento": "",
    "bairro": "Centro",
    "cidade": "São Paulo",
    "uf": "SP",
    "cep": "12345-678"
  }
}
```

<h4 align="center">Endpoints relacionados a Paciente</h4>

***[GET]*** Listar Pacientes:
```
http://localhost:8080/api/pacientes?pagina=0
```
***[POST]*** Cadastra Pacientes:
```
http://localhost:8080/api/pacientes
```
***[PUT]*** Atualizar Pacientes:
```
http://localhost:8080/api/pacientes/{id}
```
***[DELETE]*** Deletar Pacientes:
```
http://localhost:8080/api/pacientes/{id}
```
#### Exemplo Body Paciente:

```
{
	"nome": "Fabio Souza",
	"email": "fabinho@example.com",
	"telefone": "987654321",
	"cpf": "98765432101",
	"status": true,
	"endereco": {
		"logradouro": "Rua Y",
		"numero": "987",
		"complemento": "Casa 2",
		"bairro": "Centro",
		"cidade": "Cidade",
		"uf": "XX",
		"cep": "09876-543"
	}
}
```

<h4 align="center">Endpoints relacionados a Consulta</h4>

***[GET]*** Listar Consultas:
```
http://localhost:8080/api/consultas
```
***[POST]*** Agendar Consultas:
```
http://localhost:8080/api/consultas
```
***[DELETE]*** Deletar Consultas:
```
http://localhost:8080/api/consultas/{id}
```

#### Exemplo Body Medico:

```
{
"paciente": {
	"id": 7,
	"nome": "João Silva",
	"email": "joao@example.com",
	"telefone": "123456789",
	"cpf": "12345678901",
	"status": true,
	"endereco": {
		"logradouro": "Rua Y",
		"numero": "123",
		"complemento": "Apto 4",
		"bairro": "Bairro",
		"cidade": "Cidade",
		"uf": "XX",
		"cep": "12345-678"
		}
	},
	"medico": {
		"id": 1,
		"nome": "JDra. Rafaela",
		"email": "rafaela@example.com",
		"telefone": "654321987",
		"crm": "34567",
		"especialidade": "ORTOPEDIA",
		"status": true,
		"endereco": {
			"logradouro": "Avenida C",
			"numero": "987",
			"complemento": "Bloco 3",
			"bairro": "Centro",
			"cidade": "Cidade",
			"uf": "XX",
			"cep": "34567-890"
		}
	},
	"dataHora": "2023-07-21T14:00:00",
	"status": true,
	"cancelamento": null
}
```

	
