<p align="center">
  <a href="#">
    <img alt="Gatsby" src="https://glomacs.com/wp-content/uploads/2018/04/dna-Genetic-Algorithm-300x169.png" width="250"/>
  </a>
</p>
<h1 align="center">
  Algoritmos Genéticos
</h1>
<p align="center">
  <a href="https://github.com/ipdmartins/IA_genetics_algorithms/blob/master/LICENSE">
    <img src="https://img.shields.io/badge/license-MIT-blue.svg" alt="This project is released under the MIT license." />
  </a>
</p>
<p align="left">
	PROBLEMA: Os algoritmos genéticos viabilizam os cruzamentos de características para encontrar soluções para certos problemas e também para aumentar o desempenho de indivíduos, por exemplo. Na presente situação, tem-se o cenário de um ranking de jogadores de futebol FIFA com determinadas habilidades. Há jogadores altamente habilidosos em determinados pontos e não tão habilidosos em outros pontos, porém o ranking é formado pela média de tais habilidades. Com a aplicação de algoritmos genéricos (crossover, mutação, e substituição) a finalidade é formar jogadores mais aptos para jogar um suposto campeonato.
</p>
<p align="left">
	DATASET: Trata-se de uma planilha com dados de jogadores de futebol, com muitas habilidades caracterizadas com um número de 0 a 100. O autor do dataset é Stefano Leone e o arquivo está disponível no link dataset: https://www.kaggle.com/stefanoleone992/fifa-20-ultimate-team-players-dataset
</p>
<p align="left">
	A partir do dataset original, foram criados cinco novos arquivos. O arquivo teste1 que contém uma população de oito indivíduos com cinco atributos. O arquivo teste2 que contém uma população de dezesseis indivíduos com dez atributos. O arquivo teste3 que contém uma população de trinta e dois indivíduos com quinze atributos. O arquivo teste4 que contém uma população de dezesseis indivíduos com quinze atributos. O arquivo teste5 que contém uma população de oito indivíduos com quinze atributos. O arquivo teste6 que contém uma população de dezesseis indivíduos com cinco atributos. Por fim, o arquivo teste7 que contém uma população de trinta e dois indivíduos com cinco atributos.
</p>	
<p align="left">
	Os indivíduos nos sete arquivos descritos são representados por uma coluna que contém o número id e outra com a média de habilidades que ele possui. As demais colunas correspondem aos atributos e podem variar entre cinco e quinze habilidades nomeadamente pace_acceleration, drib_agility, drib_balance, drib_ball_control, shoot_shot_power, shoot_long_shots, shoot_penalties, pass_vision, pass_short, pass_long, defending, def_interceptions, phys_jumping, phys_strength e phys_aggression.
</p>	
<p align="left">
	TÉCNICA: Para a modelagem do problema será utilizado o algoritmo genético. A representação cromossômica utilizará os grupos descritos entre os arquivos teste1 ao teste7. Cada indivíduo dos arquivos é representado por um número id, por uma média das habilidades que possui e por um conjunto de habilidades. A média de habilidades é um número decimal que para fins de cálculo será convertido para binário. O cruzamento entre indivíduos será definido com escolha probabilística com utilização do método de roleta. Além do mais, será utilizado a estratégia de crossover single point para fazer a divisão cromossômica. A mutação se dará no quarto ou no sétimo algarismo do número binário.
</p>
<p align="left">
	O fitness threshold é estabelecido pela média de características dos jogadores. No momento, o dataset original indica que o melhor jogador, com uma média de 95, é o Pelé. Então, o propósito é pegar um certo grupo de jogadores com habilidades mistas e com média abaixo de 95, logo após, através dos cruzamentos, formar novos jogadores visando aperfeiçoá-los geneticamente até atingir ou superar a média de habilidades do Pelé. Dessa forma, se tais jogadores fossem inseridos no jogo Fifa eles elevariam a competitividade dos jogos através do aumento das habilidades e qualidade das jogadas.
</p>
<p align="left">
	Os testes ocorrerão com os sete arquivos descritos. O fitness threshold estabelecido para todos os testes será de 96. Para cada arquivo serão feitas dez simulações com a mutação na posição quatro do valor binário e outras dez simulações com a mutação na posição sete do valor binário. Para cada simulação, será contabilizada a quantidade de gerações necessárias para atingir o ft threshold estabelecido e a quantidade de tempo em nano segundos. Posteriormente, para cada grupo das dez simulações, será extraída a média das gerações e a média de tempo em nano segundos.
</p>
<p align="left">
	A programação foi feita em linguagem Java e utilizando a IDE do Eclipse. Para efetuar a leitura de arquivos em xls, foi utilizada uma biblioteca que está disponível neste link. http://www.java2s.com/Code/Jar/j/Downloadjxl2612jar.htm
</p>
<p align="left">
	RESULTADOS: Foram construídos arquivos com populações e atributos de tamanhos diferentes para averiguar qual característica é mais impactante no atingimento dos resultados. Anexo neste repositório consta o arquivo results.ods, onde há a contabilização de todos os testes realizados. Também neste repositório consta o arquivo tabela 1.png, que refere-se a um exemplo da contabilização dos dados e também o arquivo tabela 2.png, que refere ao resumo e compilação das médias dos resultados.
</p>
<p align="left">
	O segunda melhor média de gerações necessárias para atingir o resultado (cinco) foi obtida pela população de trinta e dois indivíduos, com cinco atributos correspondente ao arquivo teste7.xls. Neste arquivo, a mutação ocorreu no quarto algarismo do valor binário. Todavia, a segunda melhor média de tempo em nano segundos (6839440) foi obtida pela população de dezesseis indivíduos, com cinco atributos correspondente ao arquivo teste6.xls. Neste arquivo, a mutação ocorreu no quarto algarismo do valor binário. Os testes evidenciaram que o grupo com menos indivíduos (oito) obteve um desempenho inferior aos demais. No entanto, se o grupo de indivíduos for aumentado para dezesseis ou trinta e dois ao mesmo tempo que são agregados maior quantidade de atributos para combinação (dez e quinze), o número de gerações demandadas e o tempo necessário para atingir o resultado aumentam. Outra característica percebida foi que a mutação ocorrendo no quarto algarismo binário é mais eficiente do que aquela ocorrendo no sétimo.
</p>
<p align="left">
	Após a geração dos testes e análise dos resultados, se percebeu que a melhor média de gerações (4,2) foi obtida com uma população de dezesseis indivíduos e com cinco atributos correspondente ao arquivo teste6.xls. Neste arquivo, a mutação ocorreu no quarto algarismo do valor binário. Todavia, a menor média de tempo em nano segundos (5780200) foi obtida pela população do mesmo arquivo, porém com a mutação ocorrendo sétimo algarismo do valor binário. Desta forma, o modelo escolhido para ser embarcado no software é aquele com mutação na quarta posição do valor binário, para considerar um arquivo com uma população de 16 indivíduos com cinco atributos. Este arquivo teste6.xls consta anexo neste repositório.
</p>
<p align="left">
	INSTRUÇÕES DE USO DO SOFTWARE: Após ser executado, surgirá uma janela de interação. O usuário deverá carregar o arquivo teste6.xls, logo após poderá digitar o ft threshold num valor entre 0 e 100. Para atingir o melhor resultado segundo a média obtida nos testes, deverá clicar no botão 'mutate position 4' e em seguida em iniciar. Quando o sistema atingir o resultado definido ele exibirá outra janela com o número de gerações necessárias, o tempo gasto, o id do indivíduo e o ft que ele atingiu. Um executável .jar encontra-se anexo neste repositório.
</p>
<p align="left">
	VÍDEO: Nos links que seguem há uma explicação sobre o trabalho e também a apresentação de testes efetuados: https://www.youtube.com/watch?v=ed70inPddfQ&t=47s , https://www.youtube.com/watch?v=AodADA4Wle8
</p>
<p>
	You can get this project on your local dev environment in 2 minutes with these four steps:
	<ol>
		<li>
			**Clone this repository.**</br>
			In your preferred shell type: git clone https://github.com/ipdmartins/IA_genetics_algorithms.git
		</li> 	
		<li>
			**How to Contribute**</br>
			Whether you're helping to fix bugs, improve the docs, or spread the word, I'd appreciate to have you as part of it.
		</li>
	</ol>
</p>
<p>
