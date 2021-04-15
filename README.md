# RogueLikeTERD

<b>BEAUCHET Quentin</b> (Quentin Beauchet) <br>
<b>CARLENS Jean-Philippe</b>  (Coruscant11) <br>
<b>FORNER Yann</b>  (Yann-Forner)  <br>
<b>MASSE Gillian</b>  (GillianMASSE)

<b>Attention : le jdk est en version 15 !</b>

<h1>Presentation du jeu</h1>

Le but du jeu est d'avancer le plus loin dans un labyrinthe composé de plusieurs etages eux même composés de plusieurs pieces aux attributs differents les unes des autres le tout genéré proceduralement. Le joueur va devoir survivre face a de nombreux monstres dont des boss et a d'autres pieges tout aussi mortels, heureusement pour lui il va pouvoir s'equipper d'armes et d'armures de plus en plus puissantes et avoir recours a des objets lui facilitant son aventure.

<h2>1 Generation de la carte</h2>
<h3>1.1 Types d'etages<h3>
La carte se compose en plusieurs types d'etages:
<h4>1.1.1 Circle strategy</h4>
Toutes les pieces de cet etage sont des cercles.
<img src="https://user-images.githubusercontent.com/74865653/114859019-78d40100-9dea-11eb-9866-5ae69eae19a8.png"/>
<h4>1.1.2 Normal strategy</h4>
Toutes les pieces de cet etage sont des rectangles.
<img src="https://user-images.githubusercontent.com/74865653/114860563-75417980-9dec-11eb-81eb-381586e9517f.png"/>
<h4>1.1.3 Dongeon strategy</h4>
Les pieces de cet etage sont un mix de tout les types commun de pieces.
<img src="https://user-images.githubusercontent.com/74865653/114860765-b6398e00-9dec-11eb-9a1e-1cccf06809d1.png"/>
<h4>1.1.4 Piege strategy</h4>
C'est un etage piege qui apparait lorsque l'on marche sur une case piegée.
<img src="https://user-images.githubusercontent.com/74865653/114860960-eda83a80-9dec-11eb-8935-82dfc54d8d7d.png"/>
<h3>1.2 Types salles</h3>
<h4>1.2.1 Normale</h4>
C'est la salle de base, elle est rectangulaire.
<img src="https://user-images.githubusercontent.com/74865653/114864633-85a82300-9df1-11eb-96db-ebb69ec51d9c.png"/>
<h4>1.2.2 Triangle</h4>
C'est un triangle, c'est aussi une des salles de base.
<img src="https://user-images.githubusercontent.com/74865653/114866200-922d7b00-9df3-11eb-9c9e-35d1c7f6dfb4.png"/>
<h4>1.2.3 Cercle</h4>
C'est un cercle, c'est une plus petite version de salle de boss.
<img src="https://user-images.githubusercontent.com/74865653/114865403-7a092c00-9df2-11eb-86db-aae4d6977e0e.png"/>
<h4>1.2.4 Boss</h4>
C'est la salle ou apparaissent les boss, elle est beaucoup plus grande que les autres.
<img src="https://user-images.githubusercontent.com/74865653/114865593-c3f21200-9df2-11eb-97d7-e517fa26ea42.png"/>
<h4>1.2.5 Marchand</h4>
<b><i>Non implementé</b></i>
<h4>1.2.6 Repos</h4>
<b><i>Non implementé</b></i>
<h4>1.2.7 Tresor</h4>
<b><i>Non implementé</b></i>
<h4>1.2.8 Piege</h4>
<b><i>Non implementé</b></i>
  
<h2>2 Entités</h2>
<h3>2.1 Joueur</h3>
Le joueur peut choisir sa classe au debut du jeu ce qui lui permet d'influencer sur ses statistiques au debut de la partie.
Ses statistiques sont:
-Points de vie (PV)
-Puissance d'Attaque(PA)
Le joueur est representé par cet emoji 🤓 sur Linux et <span style="color: green">\uD83E\uDD13</span> sur Windows.
