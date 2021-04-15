# RogueLikeTERD

<b>BEAUCHET Quentin</b> (Quentin Beauchet) <br>
<b>CARLENS Jean-Philippe</b>  (Coruscant11) <br>
<b>FORNER Yann</b>  (Yann-Forner)  <br>
<b>MASSE Gillian</b>  (GillianMASSE)

<b>Attention : le jdk est en version 15 !</b>

<h1>Presentation du jeu</h1>

Le but du jeu est d'avancer le plus loin dans un labyrinthe composé de plusieurs etages eux même composés de plusieurs pieces aux attributs differents les unes des autres le tout genéré proceduralement. Le joueur va devoir survivre face a de nombreux monstres dont des boss et a d'autres pieges tout aussi mortels, heureusement pour lui il va pouvoir s'equipper d'armes et d'armures de plus en plus puissantes et avoir recours a des objets lui facilitant son aventure.

<h2>1.1 Generation de la carte</h2>

La carte se compose de plusieurs types d'etages:
<h3>1.1.1 Circle strategy</h3>
<img src="https://user-images.githubusercontent.com/74865653/114859019-78d40100-9dea-11eb-9866-5ae69eae19a8.png"/></br>
Toutes les pieces de cet etage sont des cercles.
<h3>1.1.2 Normal strategy</h3>
<img src="https://user-images.githubusercontent.com/74865653/114860563-75417980-9dec-11eb-81eb-381586e9517f.png"/></br>
Toutes les pieces de cet etage sont des rectangles.
<h3>1.1.3 Dongeon strategy</h3>
<img src="https://user-images.githubusercontent.com/74865653/114860765-b6398e00-9dec-11eb-9a1e-1cccf06809d1.png"/></br>
Les pieces de cet etage sont un mix de tout les types commun de pieces
<h3>1.1.4 Piege strategy</h3>
<img src="https://user-images.githubusercontent.com/74865653/114860960-eda83a80-9dec-11eb-8935-82dfc54d8d7d.png"/></br>
