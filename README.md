# RogueLikeTERD

<b>BEAUCHET Quentin</b> (Quentin Beauchet) <br>
<b>CARLENS Jean-Philippe</b>  (Coruscant11) <b>("BuildTools" également pour la plupart des commits)</b> <br>
<b>FORNER Yann</b>  (Yann-Forner) <b>("Yann" également pour les commits du début du projet)</b>  <br>
<b>MASSE Gillian</b>  (GillianMASSE)

<b>Attention : le jdk est en version 15 !</b></br>
<b>Attention : le jeu est pensé pour etre joué sur linux car la console windows n'affiche pas assez vite les characteres !</b>

<h1>Presentation du jeu</h1>

Le but du jeu est d'avancer le plus loin dans un labyrinthe composé de plusieurs etages eux même composés de plusieurs pieces aux attributs differents les unes des autres le tout genéré proceduralement. Le joueur va devoir survivre face a de nombreux monstres dont des boss et a d'autres pieges tout aussi mortels, heureusement pour lui il va pouvoir s'equipper d'armes et d'armures de plus en plus puissantes et avoir recours a des objets lui facilitant son aventure.

<h1>Touches</h1>
Déplacement : <b>ZQSD</b></br>
Mode Tour par tour : <b>T</b></br>
Attaque à distance : <b>A</b></br>
Utiliser la potion courante : <b>P</b></br>
Changement d'armes : <b>I</b></br>
Changement de potion : <b>O</b></br>
Lâcher arme : <b>L</b></br>
Lâcher potion : <b>M</b></br>
Sauvegarder : <b>W</b></br>
Quitter : <b>ESC</b></br>



<h1>Composants du jeu</h1>

<h2>1. Génération de la carte</h2>
<h3>1.1. Types d'etages</h3>
<b>La carte se compose en plusieurs types d'etages:</b>
<h4>1.1.1. Etage cercle</h4>
Toutes les pieces de cet etage sont des cercles.
<img src="https://user-images.githubusercontent.com/74865653/114859019-78d40100-9dea-11eb-9866-5ae69eae19a8.png"/>
<h4>1.1.2. Etage normal</h4>
Toutes les pieces de cet etage sont des rectangles.
<img src="https://user-images.githubusercontent.com/74865653/114860563-75417980-9dec-11eb-81eb-381586e9517f.png"/>
<h4>1.1.3. Etage dongeon</h4>
Les pieces de cet etage sont un mix de tout les types commun de pieces.
<img src="https://user-images.githubusercontent.com/74865653/114860765-b6398e00-9dec-11eb-9a1e-1cccf06809d1.png"/>
<h4>1.1.4. Etage piege</h4>
C'est un etage piege qui apparait lorsque l'on marche sur une case piegée.
<img src="https://user-images.githubusercontent.com/74865653/114860960-eda83a80-9dec-11eb-8935-82dfc54d8d7d.png"/>
<h3>1.2. Types salles</h3>
<h4>1.2.1. Salle normale</h4>
C'est la salle de base, elle est rectangulaire.
<img src="https://user-images.githubusercontent.com/74865653/114864633-85a82300-9df1-11eb-96db-ebb69ec51d9c.png"/>
<h4>1.2.2. Salle triangle</h4>
C'est un triangle, c'est aussi une des salles de base.
<img src="https://user-images.githubusercontent.com/74865653/114866200-922d7b00-9df3-11eb-9c9e-35d1c7f6dfb4.png"/>
<h4>1.2.3. Salle cercle</h4>
C'est un cercle, c'est une plus petite version de salle de boss.
<img src="https://user-images.githubusercontent.com/74865653/114865403-7a092c00-9df2-11eb-86db-aae4d6977e0e.png"/>
<h4>1.2.4. Salle boss</h4>
C'est la salle ou apparaissent les boss, elle est beaucoup plus grande que les autres.
<img src="https://user-images.githubusercontent.com/74865653/114865593-c3f21200-9df2-11eb-97d7-e517fa26ea42.png"/>
<h4>1.2.5. Salle marchand</h4>
<b><i>Non implementé</b></i>
<h4>1.2.6. Salle repos</h4>
<b><i>Non implementé</b></i>
<h4>1.2.7. Salle tresor</h4>
<b><i>Non implementé</b></i>
<h4>1.2.8. Salle piege</h4>
<b><i>Non implementé</b></i>
  
<h2>2. Entités</h2>
<h3>2.1. Joueur</h3>
Le joueur peut choisir sa classe au debut du jeu ce qui lui permet d'influencer sur ses statistiques au debut de la partie.</br>
Ses statistiques sont:</br>
-Points de vie (PV)</br>
-Puissance d'Attaque (PA)</br>
Le joueur est representé par cet emoji 🤓 sur Linux et <b>@</b> sur Windows.
<h3>2.2. Monstres</h3>
Les monstres ont les mêmes stats que le joueur mais avec une vitesse et un champ de vision en plus ce qui leurs permet de le detecter s'il s'approche trop et d'arreter s'il s'eloigne trop loin.
<h4>2.2.1. Alien</h4>
Linux: 👽</br>
Windows: <b>&</b></br>
Il se deplace extrement vite et detecte le joueur de tres loin de plus des qu'il reçoit un coup il se teleporte a l'autre bout de l'etage.
<h4>2.2.2. Abeille</h4>
Linux: 🐝</br>
Windows: <b>B</b></br>
L'abeille n'est pas si dangereuse en revanche des qu'elle meurt deux nouvelles abeilles deux fois moins puissantes apparaissent.
<h4>2.2.3. Oiseau</h4>
Linux: 🐦</br>
Windows: <b>ù</b></br>
Il suit un chemin periodique en diagonale et n'attaque pas le joueur a moins que celui lui bloque le passage.
<h4>2.2.4. Fantome</h4>
Linux: 👻</br>
Windows: <b>H</b></br>
Il peut traverser les murs pour venir attaquer le joueur.
<h4>2.2.5. Rat</h4>
Linux: 🐀</br>
Windows: <b>M</b></br>
Il est rapide et se deplace en diagonale mais a tres peu de point de vie.
<h4>2.2.6. Squelette</h4>
Linux: 	💀</br>
Windows: <b>S</b></br>
Il peut attaquer le joueur a distance et recule des qu'il s'approche de lui pour garder une certaine distance de securité.
<h4>2.2.7. Esacargot</h4>
Linux: 🐌</br>
Windows: <b>G</b>
Il se deplace en ligne droite et des qu'il est façe a un obstacle fait demi tour. 
<h4>2.2.8. Volcan</h4>
Linux: 🌋</br>
Windows: <b>V</b></br>
Il cause des degats a toutes les entités s'approchant dans son champs d'activation.
<h4>2.2.9. Zombie</h4>
Linux: 🧟</br>
Windows: <b>Z</b></br>
Il se deplace tres lentement et a beaucoup de points de vie.

<h2>2.3. Boss</h2>
<b><i>Non implementé</b></i>
<h4>2.3.1. Big Monster</h4>
Le boss "Big Monster" est un monstre puissant doté de deux bras. Sa particularité est qu'il double sa puissance à chaque perte de bras.
<img src="![image](https://user-images.githubusercontent.com/74865920/118405570-e63cb100-b678-11eb-85a8-9c028b322a0c.png)"/>
<h4>2.3.2. Snaker</h4>
Le boss "Snake" est doté d'une longue queue qu'il faudra détruire avant de pouvoir le tuer. 
<img src="![image](https://user-images.githubusercontent.com/74865920/118405570-e63cb100-b678-11eb-85a8-9c028b322a0c.png)"/>
<h4>2.3.3. Invoqueur</h4>
Le boss "Invoqueur" tire de puissant rayons lazer. Il invoque de même des rats lorsque celui-ci se voit infligé des dégats.
<img src="![image](https://user-images.githubusercontent.com/74865920/118405570-e63cb100-b678-11eb-85a8-9c028b322a0c.png)"/>
<h2>2.4. Loot</h2>
<b><i>Non implementé</b></i>



<h2>3. Cases</h2>
Les cases du jeu se divisent en deux categories, les fonctionelles et les cosmetiques: les premieres ont une fonction que se soit dans le jeu ou la programmation alors que les deuxiemes ne sont qu'un changement d'affichage des premieres.
<h3>3.1. Fonctionelles</h3>
<h4>3.1.1. Cellules Normales</h4>
C'est les cellules de base.
<img src="https://user-images.githubusercontent.com/74865653/114877044-3832b280-9dff-11eb-99d6-fc169290f972.png"/>
<img src="https://user-images.githubusercontent.com/74865653/114878669-c5c2d200-9e00-11eb-9ec1-3fae828f3b9c.png"/>
<h4>3.1.2. Cellules Bordure</h4>
Ce sont les bordures de l'etage, aucune entité ne peut les traverser (sauf exceptions).
<img src="https://user-images.githubusercontent.com/74865653/114880411-551cb500-9e02-11eb-954f-7a3ce2ac38cd.png"/>
<h4>3.1.3. Cellules Vide</h4>
Ce sont des cellules vide.
<img src="https://user-images.githubusercontent.com/74865653/114881537-66b28c80-9e03-11eb-97ed-f5db9a69c03f.png"/>
<h4>3.1.4. Cellules Monter</h4>
Elle permet de remonter a l'etage precedent.</br>
Linux: 👍</br>
Windows: <b>^</b></br>
<h4>3.1.5. Cellules Descendre</h4>
Elle permet de descendre a l'etage precedent.</br>
Linux: 👎</br>
Windows: <b>v</b></br>
<h4>3.1.6. Cellules Piege</h4>
Ce sont des cellules invisible qui teleporte le joueur dans un etage piégé.
<h4>3.1.7. Cellules Chest</h4>
<b><i>Non implementé</b></i>
<h3>3.2. Cosmetiques</h3>
<b><i>Non implementé</b></i>

<h2>4. Inventaire</h2>
<b><i>Non implementé</b></i>

<h2>5. Sauvegarde</h2>
<b><i>Non implementé</b></i>
