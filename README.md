# CREDIT-CARD-FRAUD-DETECTOR

School Project 2020 (Faculdade Ciências da Universidade de Lisboa) 

## Problema

  Estima-se que no ano de 2018, a fraude com cartões de crédito tenha atingido
20 mil milhões de euros em todo o mundo. Uma grande parte destes casos são
feitos a partir de dados de cartões de crédito extraídos de lojas online que
sofrem ciber-ataques. Serviços como o MBNet e Privacy.com previnem contra
este tipo de ataques, usando cartões de crédito temporários, com limite
temporal e restrições de valor e de vendedor. No entanto, a grande maioria
dos utilizadores de cartões de crédito online não utiliza estes mecanismos.
  Um método de deteção de fraude compara cada nova transação num dado
cartão com um histórico de transações para o mesmo cartão. Neste trabalho
vamos desenvolver um método para construir métricas para um dado cartão a
partir de dados de transações.

## Solução

A nossa solução é responsável por receber uma sequência de transações
(representadas por uma tabela em formato Comma-Separated Values, CSV) e de
gerar novas colunas para essa tabela, colunas estas que representam os
valores de umas dadas métricas para cada transação. Esta capacidade permite
usar o nosso sistema em tempo real para classificar cada transação à medida
que a recebemos: de cada vez que chega uma nova transação geramos logo a
linha correspondente.
Para permitir aos utilizadores do sistema configurar as métricas que melhor
entenderem, vamos implementar uma linguagem de métricas muito simples,
inspirada pelo SQL e que trabalham sobre as colunas de uma tabela. As
colunas são numeradas a partir do zero: 0, 1, . . . .
A nossa solução está dividida em dois componentes: o Agregador e o
Processador. O Agregador é uma ferramenta que permite construir uma
dada métrica sobre uma sequência de transações. O Processador é
responsável por processar uma lista de transações usando diferentes métricas,
criando uma tabela de informação agregada.
