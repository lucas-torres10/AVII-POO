# CURSO DE JOGOS

http://mariojp.com.br

Mario Jorge Pereira

### Projetos e código relacionado ao curso sobre jogos em java.

- Basico 1:
Criando um aplicativo com uma tela em Java Swing

- Basico 2:
Desenhado e exibindo o desenho na tela.

- Basico 3:
Carregando e exibindo uma imagem na tela.

- Animação 1:
Fazendo a animação utilizando <code>javax.swing.Timer</code> e
<code>java.awt.event.ActionListener</code>

- Animação 2:
Fazendo a animação utilizando <code>java.util.Timer</code> e
<code>java.util.TimerTask</code>

- Animação 3:
Fazendo a animação utilizando <code>Thread</code>.

- Sprites 1:
Colocando a nave e os movimentos.

- Sprites 2:
Disparando mísseis com a nave. 

- Sprites 3:
Detectando colisão entre os mísseis e os inimigos.

Documento de Especificação
Este documento se refere ao processo de refatoração e otimização realizado pelos alunos Alisson
Oliveira da Silva Junior, Jean Carlos Macêdo Silva, Jonathan Guerreiro dos Santos e Lucas Campos
Torres.

Para realização deste processo de refatoração foi utilizado o padrão “Abstract Factory”, fazendo parte
da categoria de Patterns Criacionais, a intenção de utilizarmos esse padrão foi a abertura da
possibilidade da criação de famílias de objetos utilizando uma única interface sem a obrigatoriedade
da especificação de uma classe concreta.

Vamos ao processo em si:

- Começamos com a criação da própria fábrica abstrata, que nomeia o padrão utilizado, neste caso foi
criada a fábrica “EntidadeFactory”:

package br.com.mariojp.game;

public interface EntidadeFactory {
Nave criarNave(int x, int y, int largura);
Inimigo criarInimigo(int x, int y);
}

- Logo após fizemos o “implements” que criou uma extensão concreta da nossa fábrica abstrata
“EntidadeFactory”, este processo foi realizado nas Classes “NaveFactory” e “InimigoFactory”:

1. Classe NaveFactory:
package br.com.mariojp.game;

public class NaveFactory implements EntidadeFactory {
@Override
public Nave criarNave(int x, int y, int largura) {
return new Nave(x, y, largura);
    }
@Override
public Inimigo criarInimigo(int x, int y) {
throw new UnsupportedOperationException("NaveFactory não cria
inimigos.");
    }
}

2. Classe InimigoFactory:

package br.com.mariojp.game;

public class InimigoFactory implements EntidadeFactory {
@Override
public Nave criarNave(int x, int y, int largura) {
throw new UnsupportedOperationException("InimigoFactory não cria
naves.");
}
@Override
public Inimigo criarInimigo(int x, int y) {
return new Inimigo(x, y);
  }
}

- Após isso foi possível fazer as alterações necessárias na Classe jogo, foram criadas duas variáveis,
uma do tipo “NaveFactory” e outra do tipo “InimigoFactory”, esse variáveis tem como objetivo de
criar instâncias da suas próprias classes. Também criamos um construtor para que fosse permitido
instanciar as classes no momento em que um novo objeto “jogo” fosse criado:

public Jogo(NaveFactory naveFactory, InimigoFactory inimigoFactory) {
this.naveFactory = naveFactory;
this.inimigoFactory = inimigoFactory;
initJogo();
}

* “NaveFactory naveFactory” é o primeiro parâmetro do construtor e “InimigoFactory
inimigoFactory” é o segundo parâmetro, ambos parâmetros esperam instâncias das suas respectivas classes no
momento em que um novo objeto Jogo é criado

- Dentro do “Olho do Furacão”(Construtor):
No construtor as instâncias são passadas como argumentos e atribuídas às variáveis dentro da classe
Jogo. A utilidade do construtor nessa parte, é que com ele podemos alterar ou alternar entre os
diferentes tipos de fábricas ao criarmos diferentes instâncias de naves ou inimigos.
- Posteriormente modificamos o método “updateInimigo()” e agora podemos visualizar uma transição
no modo que os inimigos são criados no jogo. Antes dessa modificação os inimigos eram criados
utilizando-se o construtor da classe Inimigo.

1. Antes:
private void updateInimigo() {
while (inimigos.size() < 5) {
inimigos.add(new Inimigo(B_WIDTH, random.nextInt(B_HEIGHT
- 20) + 10));
  }
}

2. Depois:
private void updateInimigo() {
while (inimigos.size() < 5) {
inimigos.add(inimigoFactory.criarInimigo(B_WIDTH,
random.nextInt(B_HEIGHT - 20) + 10));
}

- Outra substituição que fizemos foi a alteração do local onde são criadas as naves, para isso usamos a
fábrica “NaveFactory” para criação de uma instância da classe “Nave” no método “initJogo()”.
Lembrando que ao fazermos isso definiremos os parâmetros no próprio método. Com o controle da
criação de naves sendo feito pela fábrica fica mais fácil criar ou alterar os tipos de nave:

private void initJogo() {
addKeyListener(new TAdapter());
setFocusable(true);
setBackground(Color.BLACK);
setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
setDoubleBuffered(true);
nave = naveFactory.criarNave(40, 60, B_HEIGHT);
timer = new Timer(DELAY, this);
timer.start();
}

- Por fim alteramos na classe principal o metódo “initInimigo”, foram iniciadas novas fases e
inimigos, fizemos isso para adicionar os objetos da fábrica “NaveFactory” e “InimigoFactory”.:

private void initUI() {
NaveFactory naveFactory = new NaveFactory();
InimigoFactory inimigoFactory = new InimigoFactory();
add(new Jogo(naveFactory, inimigoFactory));
setResizable(false);
pack();
setTitle("Game");
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}

Com essas alterações conseguimos obter uma melhora de desempenho, realizando alterações que
diminuíram o espaço utilizado, além de conseguir colocar em prática o padrão que nos foi proposto.
