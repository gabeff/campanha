# Campanha

Expor o serviço de Campanha, seguindo as regras de CRUD (Create, Read, Update e Delete). 

## Premissas

Eu, como usuário, quero administrar os dados da Campanha e fornecer mecanismos (APIs) para INCLUIR, CONSULTAR, ATUALIZAR, DELETAR as campanhas. Para tanto, os critérios de aceite dessa história são:

*	As campanhas deverão ser cadastradas de forma que o serviço retorne essas campanhas seguindo a estrutura abaixo:
**	Nome Da Campanha;
**	ID do Time do Coração;
**	Data de Vigência;
*	O Sistema não deverá retornar campanhas que estão com a data de vigência vencidas;
*	No cadastramento de uma nova campanha, deve-se verificar se já existe uma campanha ativa para aquele período (vigência), caso exista uma campanha ou N campanhas associadas naquele período, o sistema deverá somar um dia no término da vigência de cada campanha já existente. Caso a data final da vigência seja igual a outra campanha, deverá ser acrescido um dia a mais de forma que as campanhas não tenham a mesma data de término de vigência. Por fim, efetuar o cadastramento da nova campanha:
**	Exemplo:
*** Campanha 1 : inicio dia 01/10/2017 a 03/10/2017;
***	Campanha 2: inicio dia 01/10/2017 a 02/10/2017;
***	Cadastrando Campanha 3: inicio 01/10/2017 a 03/10/2017;
***	-> Sistema:
****	Campanha 2 : 01/10/2017 a 03/10/2017 (porém a data bate com a campanha 1 e a 3, somando mais 1 dia)
****	Campanha 2 : 01/10/2017 a 04/10/2017
****	Campanha 1: 01/10/2017 a 04/10/2017 (bate com a data da campanha 2, somando mais 1 dia)
****	Campanha 1: 01/10/2017 a 05/10/2017
****	Incluindo campanha 3 : 01/10/2017 a 03/10/2017
*	As campanhas deveram ser controladas por um ID único;
*	No caso de uma nas campanhas já existentes, o sistema deverá ser capaz de fornecer recursos para avisar outros sistemas que houve alteração nas campanhas existentes;
*	Neste exercício, deve-se contemplar a API que faz a associação entre o Cliente e as Campanhas. Essa API é utilizada pelo próximo exercício. O Candidato deve analisar a melhor forma e quais os tipos de atributos que devem ser utilizados nessa associação.


### Prerequisites

What things you need to install the software and how to install them

```
Give examples
```

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc

