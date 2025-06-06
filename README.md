# Clínica de Psicologia

## Descrição
App destinado aos estudantes, orientadores e funcionários vinculados a Clínica de Psicologia - UFMS, buscando a sistematização e informatização de suas atividades, bem como servir à fins de pesquisa com a base de dados;


## Requisitos para rodar o projeto

- [Netbeans](https://netbeans.apache.org/front/main/download/) 
- [JDK 21](https://www.oracle.com/br/java/technologies/downloads/#java21)
- [Xampp](https://www.apachefriends.org/pt_br/download.html)

Obs: Nesse projeto utiliza-se o Netbeans, a fim de utilizar o GUI Designer embutido na IDE. 

## Para clonar o projeto

```
git clone *urlProjeto*
```
---

## Configurações Necessárias

Após instalar as ferramentas mencionadas nos requisitos, siga as etapas abaixo para configurar e executar o projeto:

### 1. Instalação de Dependências  
No NetBeans, localize o diretório **"Dependencies"**, clique com o botão direito e selecione **"Download Declared Dependencies"** para baixar as dependências necessárias.

### 2. Configuração do Banco de Dados  
- Abra o **PhpMyAdmin** pelo XAMPP.
- Crie um banco de dados chamado `clinicapsicologia`.

### 3. Execução do Projeto  
- Navegue até `Visao/App/Application.java` e execute o arquivo para iniciar a aplicação.

> **Nota 1:** Caso ainda faltem dependências, será necessário instalá-las manualmente. Consulte a documentação específica:
> - [Dependências Pendentes](https://drive.google.com/file/d/1U7cPg0x4xEaJHpZVIrMyFvKPd0E6cQMY/view?usp=drive_link)
> **Nota 2:** Para adaptar o projeto as mudanças realizadas no banco de dados, é necessário fazer o import dos arquivos .sql. Faça o download do arquivo, importe ele no banco de dados e siga os passos descritos.
> - [Banco de Dados](https://drive.google.com/file/d/1U7cPg0x4xEaJHpZVIrMyFvKPd0E6cQMY/view?usp=drive_link)

