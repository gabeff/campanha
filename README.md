# Campanha

Expor o serviço de Campanha, seguindo as regras de CRUD (Create, Read, Update e Delete). 

## Premissas

Eu, como usuário, quero administrar os dados da Campanha e fornecer mecanismos (APIs) para INCLUIR, CONSULTAR, ATUALIZAR, DELETAR as campanhas. Para tanto, os critérios de aceite dessa história são:

*	As campanhas deverão ser cadastradas de forma que o serviço retorne essas campanhas seguindo a estrutura abaixo:
```
	Nome Da Campanha;
	ID do Time do Coração;
	Data de Vigência;
```
*	O Sistema não deverá retornar campanhas que estão com a data de vigência vencidas;
*	No cadastramento de uma nova campanha, deve-se verificar se já existe uma campanha ativa para aquele período (vigência), caso exista uma campanha ou N campanhas associadas naquele período, o sistema deverá somar um dia no término da vigência de cada campanha já existente. Caso a data final da vigência seja igual a outra campanha, deverá ser acrescido um dia a mais de forma que as campanhas não tenham a mesma data de término de vigência. Por fim, efetuar o cadastramento da nova campanha:
```
Exemplo:
  	Campanha 1 : inicio dia 01/10/2017 a 03/10/2017;
	Campanha 2: inicio dia 01/10/2017 a 02/10/2017;
	Cadastrando Campanha 3: inicio 01/10/2017 a 03/10/2017;
  
Sistema:
	Campanha 2 : 01/10/2017 a 03/10/2017 (porém a data bate com a campanha 1 e a 3, somando mais 1 dia)
	Campanha 2 : 01/10/2017 a 04/10/2017
	Campanha 1: 01/10/2017 a 04/10/2017 (bate com a data da campanha 2, somando mais 1 dia)
	Campanha 1: 01/10/2017 a 05/10/2017
	Incluindo campanha 3 : 01/10/2017 a 03/10/2017
```
*	As campanhas deveram ser controladas por um ID único;
*	No caso de uma nas campanhas já existentes, o sistema deverá ser capaz de fornecer recursos para avisar outros sistemas que houve alteração nas campanhas existentes;
*	Neste exercício, deve-se contemplar a API que faz a associação entre o Cliente e as Campanhas. Essa API é utilizada pelo próximo exercício. O Candidato deve analisar a melhor forma e quais os tipos de atributos que devem ser utilizados nessa associação.


## Métodos

### getCampanhasDisponiveisCliente(Integer idCliente)

Retorna todas as campanhas disponíveis para o cliente, ou seja, todas as campanhas ativas para o time do coração do cliente e que ainda não foram associadas ao mesmo.

* URL: /Campanha/getCampanhasDisponiveis/{idCliente}
* Método: Get
* Parametros de URL: idCliente. Ex.: 1

Exemplo Chamada
```
curl -X GET \
  http://localhost:8080/Campanha/getCampanhasDisponiveis/1 \
```

Exemplo Retorno
```
[
    {
        "id": 814,
        "nome": "Campanha 28",
        "idTime": 19,
        "dataInicioVigencia": "2017-10-01",
        "dataFimVigencia": "2017-11-27"
    },
    {
        "id": 841,
        "nome": "Campanha 55",
        "idTime": 19,
        "dataInicioVigencia": "2017-10-01",
        "dataFimVigencia": "2017-12-21"
    },
    {
        "id": 870,
        "nome": "Campanha 84",
        "idTime": 19,
        "dataInicioVigencia": "2017-10-01",
        "dataFimVigencia": "2018-01-18"
    }
]
```

