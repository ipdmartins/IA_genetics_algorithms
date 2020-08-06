# IA_genetics_algorithms

Equipe: Igor Paulo Domingues Martins

Problema: Aplicação de algoritmos genéricos (crossover, mutação, e substituição) com a finalidade de formar jogadores mais habilidosos. 

Dataset: Trata-se de uma planilha com dados de jogadores de futebol, com muitas habilidades caracterizadas com um número de 0 a 100. O autor do dataset é Stefano Leone e está disponível no link dataset: https://www.kaggle.com/stefanoleone992/fifa-20-ultimate-team-players-dataset 

Técnica: Para a modelagem do problema será utilizado o algoritmo genético. A representação cromossômica utilizará inicialmente um grupo de 8 cromossomos, os mesmos serão representados por um número decimal que correspondem a média de habilidades de cada jogador. Para cálculo posterior esses números serão convertidos para binário. O fitness será definido com escolha probabilística com utilização do método de roleta. Além do mais será utilizado a estratégia de crossover single point para fazer a divisão cromossômica. A mutação se dará no último algarismo do número binário.

No momento o jogador mais bem rankeado é o Pelé com média de 95, então o propósito é pegar jogadores com habilidades mistas e com média abaixo de 95 e através dos cruzamentos formar novos jogadores visando aperfeiçoá-los geneticamente até atingir a média de habilidades do Pelé. Dessa forma se tais jogadores fossem inseridos no jogo Fifa eles elevariam a competitividade dos jogos através do aumento das habilidades e qualidade das jogadas. 
