<h1>INSTRUÇÕES SOBRE O PROJETO</h1>

O projeto é a representação de um sistema de agendamento de transferências financeiras, com um front-end manipulando informações através de uma REST API.
Foi desenvolvido em Vue 3, criado com Vite e Vuetify para a manipulação dos componentes.<br/>
O projeto está utilizando Axios para a comunicação com o back-end, e foi desenvolvido na IDE Visual Studio Code.<br/>
Para a execução do front-end, é necessário ter instalado o Node e todas as suas dependências (recomendável ter a versão LTS).<br/>
Com node instalado, abra o CMD e navegue até a pasta do projeto. (Ex: C:\Projetos\namibank-vue).<br/>
Insira os seguintes comandos respectivamente:<br/>
npm run build<br/>
npm run dev<br/><br/>

O back-end está desenvolvido na linguagem Java, utilizando o framework Spring Boot, a versão do Java SDK utilizada é a 17, e a versão do Spring Boot é a 3.0.2.
O projeto utiliza o Maven como gerenciador de dependências.<br/>
Para a persistência dos dados, apenas em tempo de execução, foi utilizado o banco de dados H2.<br/>
A arquitetura do projeto é composta pelas classes Controller, onde estão definidas as rotas a serem acessadas, Model, onde é definida a entidade manipulável do sistema e Service, onde está contida a regra de negócios. Também foram criadas classes de Exception para realizar o tratamento de erros de forma personalizada.<br/>
Para a execução do back-end, por questões de facilidade, optei por criar uma imagem Docker, por isso, para executar é necessária a ferramenta Docker Desktop instalada.<br/>
Para realizar a subida da imagem no container do Docker, primeiramente acesse a pasta raiz do projeto pelo cmd (Ex: C:\Projetos\transf-financeira-springboot)<br/>
Insira os seguintes comandos:<br/>
docker build -t namibankapi<br/>
docker run -p 8000:8080 namibankapi<br/><br/>

OBS: Caso não queira usar o Docker, pode-se usar qualquer IDE com suporte a Java (NetBeans, IntelliJ), porém neste caso, é necessário alterar a porta de acesso da API no FRONT em 'services/api.js' para 8080, pois está configurada para acessar 8000 como padrão.

<h2>UTILIZAÇÃO DO SISTEMA</h2>


O sistema possui duas telas, uma para realizar transferências e a outra para acessar o histórico de transferências realizadas, podendo ser alternadas pela Side Bar.
Para poder realizar um agendamento de transferência financeira, basta preencher todos os campos na tela principal e clicar em 'Confirmar'.<br/>
Para ver o agendamento realizado, basta se direcionar à tela de histórico e selecionar o horário correspondente a transferência desejada. <br/>
É possível entender a regra aplicada para as taxas no campo 'Entenda nossas taxas', também disponível na Side Bar.

![Transferencia](https://user-images.githubusercontent.com/56372170/217151263-1d6e1407-0622-4d9f-a8e7-72229b79f063.png)
![Historico](https://user-images.githubusercontent.com/56372170/217151272-351fc093-8f9f-4eca-8c16-0a1a0d5a4ab1.png)
![Info](https://user-images.githubusercontent.com/56372170/217151280-36010927-7b75-4a4c-aa9e-14cfb192e24c.png)


