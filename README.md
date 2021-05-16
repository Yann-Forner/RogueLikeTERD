# RogueLikeTERD

<b>BEAUCHET Quentin</b> (Quentin Beauchet) <br>
<b>CARLENS Jean-Philippe</b>  (Coruscant11) <b>("BuildTools" également pour la plupart des commits)</b> <br>
<b>FORNER Yann</b>  (Yann-Forner) <b>("Yann" également pour les commits du début du projet)</b>  <br>
<b>MASSE Gillian</b>  (GillianMASSE)

<b>Attention : le jdk est en version 15 !</b></br>
<b>Attention : le jeu est pensé pour etre joué sur linux car la console windows n'affiche pas assez vite les characteres !</b>

<h1>Presentation du jeu</h1>

Le but du jeu est d'avancer <b>le plus loin possible</b> dans un labyrinthe composé de <b>plusieurs etages</b> eux même composés de plusieurs pieces aux attributs differents les unes des autres le tout genéré proceduralement. Le joueur va devoir survivre face à de <b>nombreux monstres</b>, boss et divers pièges tout aussi mortels. Heureusement pour lui il va pouvoir s'equipper d'armes et d'armures de plus en plus puissantes et avoir recours a des objets lui facilitant son aventure.

<h2>⚠️Warnings⚠️</h2>

Le jeu est jouable est sur Linux et sur Windows. <br>

Attention toutefois à son utilisation sur <b>Windows</b> :<br>
  - Les smileys seront remplacés par des lettres. <br>
  - Le jeu sera lancé de base en tour par tour, il faudra appuyer sur T pour changer ce mode si désiré.<br>
Ces spécifications seront de même valables sur l'IDE.<br>


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

<h1>Attributs du joueur</h1>
Le joueur est doté de <b>différents attributs</b> lui permettant d'évoluer dans le jeu.</br>

<h2>La vie</h2>

La vie est la donnée la plus importante du jeu. Si celle-ci <b>tombe à 0, le joueur a perdu.</b> <br>
Le joueur peut perdre de la vie par les dégats que peuvent lui infliger les monstres et en regagner grâce à la nourriture ou aux potions de vie  <br>

<h2>L'endurance</h2>
L'endurance permet au joueur <b>d'attaquer</b> les monstres du jeu.</br>
A chaque attaque, celui-ci <b>perd de l'endurance.</b> </br>
Il sera possible de regagner de l'endurance grâce aux <b>potions d'endurance ou au burger</b>.</br></br>
<img src="https://user-images.githubusercontent.com/74865653/118410035-c794e500-b68d-11eb-9491-808a5f945331.png"/></br>

<h2>L'argent</h2>
L'argent permet au joueur <b>d'acheter des objets au marchand.</b> </br></br>
Le joueur peut <b>en gagner</b> en <b>vendant des objets</b> au marchand ou directement en trouvant des <b>stacks d'argent</b> au sein du labyrinthe. </br>
<img src="https://user-images.githubusercontent.com/74865653/118410100-0fb40780-b68e-11eb-91f6-56773c260183.png"/></br>

<h1>Composants du jeu</h1>

<h2>1. Génération de la carte</h2>
<h3>1.1. Types d'etages</h3>
<b>La carte se compose en plusieurs types d'etages:</b>
<h4>1.1.1. Etage cercle</h4>
Toutes les pieces de cet etage sont des cercles.</br></br>
<img src="https://user-images.githubusercontent.com/74865920/118411884-2e6acc00-b697-11eb-9d69-1a9f1c6f384a.png"/>
<h4>1.1.2. Etage normal</h4>
Toutes les pieces de cet etage sont des <b>rectangles</b>.</br></br>
<img src="https://user-images.githubusercontent.com/74865920/118411899-46425000-b697-11eb-9f59-f04a0beeab31.png"
/>
<h4>1.1.3. Etage dongeon</h4>
Les pieces de cet etage sont un <b>mélange</b> de tout les types commun de pieces.</br></br>
<img src="https://user-images.githubusercontent.com/74865920/118411905-53f7d580-b697-11eb-8ad6-1ade5c98e75f.png"/>
<h4>1.1.4. Etage piege</h4>
C'est un etage piege qui apparait lorsque l'on marche sur une <b>case piegée</b>.</br></br>
<img src="https://user-images.githubusercontent.com/74865920/118411961-97eada80-b697-11eb-86da-c2259e8d6676.png"/>
<h4>1.1.5. Etage boss</h4>
C'est un étage où un <b>boss</b> apparaît .</br></br>
<img src="https://user-images.githubusercontent.com/74865920/118412126-94a41e80-b698-11eb-8806-96f54fef143b.png"/>

<h3>1.2. Types salles</h3>
<h4>1.2.1. Salle normale</h4>
C'est la salle de base, elle est <b>rectangulaire</b>.</br></br>
<img src="https://user-images.githubusercontent.com/74865653/114864633-85a82300-9df1-11eb-96db-ebb69ec51d9c.png"/>
<h4>1.2.2. Salle triangle</h4>
C'est un <b>triangle</b>, c'est aussi une des salles de base.</br></br>
<img src="https://user-images.githubusercontent.com/74865653/114866200-922d7b00-9df3-11eb-9c9e-35d1c7f6dfb4.png"/>
<h4>1.2.3. Salle cercle</h4>
C'est un cercle, c'est une <b>plus petite version</b> de salle de boss.</br></br>
<img src="https://user-images.githubusercontent.com/74865653/114865403-7a092c00-9df2-11eb-86db-aae4d6977e0e.png"/>
<h4>1.2.4. Salle marchand</h4>
Salle dans lequel le marchand apparaît<b></b></i>
<img src="https://user-images.githubusercontent.com/74865920/118412097-6d4d5180-b698-11eb-8d81-a79a82e30674.png"/>
<h4>1.2.5. Salle tresor</h4>
<b><i>Non implementé</b></i>

  
<h2>2. Entités</h2>
<h3>2.1. Joueur</h3>
Le joueur peut choisir sa classe au <b>debut du jeu</b> ce qui lui permet d'influencer sur ses statistiques au debut de la partie.</br>
Ses statistiques sont:</br>
-Points de vie (PV)</br>
-Puissance d'Attaque (PA)</br>
Le joueur est representé par cet emoji 🤓 sur Linux et <b>@</b> sur Windows.
<h3>2.2. Monstres</h3>
Les monstres ont les <b>mêmes statistiques</b> que le joueur mais avec une <b>vitesse</b> et un <b>champ de vision</b> en plus ce qui leurs permet de le detecter s'il s'approche trop et de <b>s'arreter</b> s'il s'eloigne trop loin.
<h4>2.2.1. Alien</h4>
Linux: 👽</br>
Windows: <b>&</b></br>
Il se deplace <b>vite</b> et<b> detecte le joueur de très loin</b> de plus des qu'il reçoit un coup il se <b>teleporte</b> a l'autre bout de l'etage.
<h4>2.2.2. Abeille</h4>
Linux: 🐝</br>
Windows: <b>B</b></br>
L'abeille n'est pas si dangereuse en revanche des qu'elle meurt <b>deux nouvelles abeilles</b> deux fois moins puissantes <b>apparaissent</b>.
<h4>2.2.3. Oiseau</h4>
Linux: 🐦</br>
Windows: <b>ù</b></br>
Il suit un <b>chemin periodique</b> en diagonale et n'attaque pas le joueur a moins que celui lui bloque le passage.
<h4>2.2.4. Fantome</h4>
Linux: 👻</br>
Windows: <b>H</b></br>
Il peut <b>traverser les murs</b> pour venir attaquer le joueur.
<h4>2.2.5. Rat</h4>
Linux: 🐀</br>
Windows: <b>M</b></br>
Il est <b>rapide</b> et se deplace en <b>diagonale</b> mais a tres peu de point de vie.
<h4>2.2.6. Squelette</h4>
Linux: 	💀</br>
Windows: <b>S</b></br>
Il peut attaquer le joueur a distance et recule des qu'il s'approche de lui pour garder une certaine distance de securité.
<h4>2.2.7. Esacargot</h4>
Linux: 🐌</br>
Windows: <b>G</b>
Il se deplace en <b>ligne droite</b> et des qu'il est façe a un obstacle fait demi tour. 
<h4>2.2.8. Volcan</h4>
Linux: 🌋</br>
Windows: <b>V</b></br>
Il cause des <b>dégats a toutes les entités</b> s'approchant dans son <b>champs d'activation</b>.
<h4>2.2.9. Zombie</h4>
Linux: 🧟</br>
Windows: <b>Z</b></br>
Il se déplace tres <b>lentement</b> et a <b>beaucoup de points de vie</b>.

<h3>2.2. Le marchand</h3>

Le marchand, même si considéré comme un monstre dans notre implémentation, est <b>bien différent des autres entités</b> du labyrinthe</br>
Il apparaît dans le labyrinthe de manière <b>aléatoire</b>.</br>
Pour engager la conversation avec celui-ci, il suffit de le <b>colisionner</b>. </br></br>
<img src="https://user-images.githubusercontent.com/74865920/118412196-eba9f380-b698-11eb-9f83-f51e67435626.png"/>
<img src="https://user-images.githubusercontent.com/74865920/118412230-21e77300-b699-11eb-9fa4-2f5bfcfd48af.png"/>

<h4>2.2.1. L'achat</h4>
Le joueur peut acheter auprès du marchand des <b>armes et des potions</b>. </br>
Le prix est fixé en fonction de l'équilibrage du jeu.</br></br>
<img src="https://user-images.githubusercontent.com/74865920/118412206-fd8b9680-b698-11eb-9e34-5696458d2842.png"/>

<h4>2.2.2. La vente</h4>

Le joueur peut de même <b>vendre ses objets</b> auprès du marchand.</br></br>
<img src="https://user-images.githubusercontent.com/74865920/118412583-1c8b2800-b69b-11eb-93a6-dd0055e1dba1.png"/>

<h4>2.2.3. Le vol</h4>

Il est possible de <b>rompre la pacificité</b> du marchand en essayant de le voler.</br>
Le marchand <b>devient</b> alors une <b>entité aggressive</b>.</br></br>
<img src="https://user-images.githubusercontent.com/74865920/118412595-27de5380-b69b-11eb-8664-b4dd40f89797.png"/>


<h2>2.3. Boss</h2>
Les boss sont présents dans le labyrinthe dans les salles prévues à cet effet. </br>
Ce sont des monstres bien <b>plus fort</b> que les précédents, dotés de <b>capacités uniques</b> et prenant plusieurs cases d'affichage.</br>
<h4>2.3.1. Big Monster</h4>
Le boss "Big Monster" est un monstre <b>puissant</b> doté de <b>deux bras</b>. Sa particularité est qu'il <b>double sa puissance</b> à chaque perte de bras.</br></br>
<img src="https://user-images.githubusercontent.com/74865920/118412298-80145600-b699-11eb-9816-ef254a68f539.png"/>
<h4>2.3.2. Snake</h4>
Le boss "Snake" est doté d'une <b>longue queue</b> qu'il faudra détruire avant de pouvoir le tuer. </br></br>
<img src="https://user-images.githubusercontent.com/74865920/118412269-6541e180-b699-11eb-980c-ab2486562bec.png"/>
<h4>2.3.3. Invoqueur</h4>
Le boss "Invoqueur" tire de puissant <b>rayons lazer</b>. Il <b>invoque</b> de même des <b>rats</b> lorsque celui-ci se voit infligé des dégats.</br></br>
<img src="https://user-images.githubusercontent.com/74865920/118412282-74c12a80-b699-11eb-9602-397aad331fa5.png"/>

<h2>2.4. Items</h2>
<h2>2.4.1 Armes</h2>
Les armes, peuvent être trouvées tout le long du labyrinthe : </br>
  - En les <b>ramassant</b> directement dans le labyrinthe</br>
  - En les récupérant sur un <b>monstre tué</b></br>
  - En les <b>achetant</b> au marchand</br>
Elles permettent au joueur <b>d'attaquer les différents monstres</b> qu'il va rencontrer durant son aventure. </br>
Le type d'attaque va changer en fonction de l'arme portée par le joueur. </br></br>

<h4>2.4.1.1 Mélée</h4>
Clé à molette :</br>
Linux: 🔧 </br>
Windows: <b>"m"</b></br></br>
Epée :</br>
Linux: 🔪</br>
Windows: <b>"m"</b></br></br>
Hache :</br>
Linux: 🪓</br>
Windows: <b>"m"</b></br></br>
Châine :</br>
Linux: 🔗</br>
Windows: <b>"m"</b></br></br>
Les armes mélées sont les armes <b>corps à corps</b> du jeu. Elles infligent des <b>dégats conséquent</b> à courte portée. </br>
Il existe des clés à molette, des épées, des haches et des chaînes. </br>
Les différentes armes influent sur la portée d'attaque du joueur. </br>
</br>

<h4>2.4.1.2 Armes à distance</h4>
Canne à pêche</br>
Linux: 🎣</br>
Windows: <b>"b"</b></br></br>
Arc</br>
Linux: 🏹</br>
Windows: <b> "b"</b></br></br>
Tridant</br>
Linux: 🔱</br>
Windows: <b> "b"</b></br></br>
Revolver</br>
Linux: 🔫</br>
Windows: <b> "b"</b></br></br>
Il existe dans le jeu des <b>arme à distance</b>. Elles permettent d'attaquer un monstre à partir du moment où il est à portée de l'arme </br>
Si aucun monstre n'est à portée du joueur, ce dernier ne peut pas attaquer.</br>
Il existe des Cannes à pêche, des arcs, des tridents et des revolvers </br>
Les différentes armes influent également sur la portée d'attaque du joueur. </br></br>

<h4>2.4.1.3 Les armes magiques</h4>
Balais</br>
Linux: 🧹</br>
Windows: <b>"w"</b></br></br>
Balais </br>
Linux: 🦴</br>
Windows: <b>"w"</b></br></br>
Balais</br>
Linux: 🥢</br>
Windows: <b>"w"</b></br></br>
Balais</br>
Linux: 🦯</br>
Windows: <b>"w"</b></br></br>
Les armes magiques sont les <b>armes à distance</b> du jeu. Le joueur, en les utilisant, attaquera en <b>ligne droite</b> devant lui. </br>
Contrairement aux armes à distance classiques, peu importe qu'il y ait des monstres sur sa trajectoire, le joueur peut quand même utiliser son arme.</br>
Il existe des balais, des os, des baguettes et des cannes </br>
Les différentes armes influent également sur la portée d'attaque du joueur. </br></br>


<h2>2.4.2 Les potions</h2>
Les potions, tout comme les armes, peuvent être trouvées tout le long du labyrinthe : </br>
  - En les <b>ramassant</b> directement dans le labyrinthe</br>
  - En les <b>récupérant sur un monstre</b> tué</br>
  - En les <b>achetant</b> au marchand</br>
Une fois ramassées, les potions se disposent dans <b>l'inventaire</b> du joueur et peuvent être utilisées à tout moment.</br>
Celles-ci ont des <b>effets qui diffèrent</b> en fonction du type de la potion.</br>
<h4>2.4.1.1 La potion de vie</h4>
Linux: 🍼 </br>
Windows: <b> "h"</b></br>
La potion de vie est une potion tout de ce qu'il y a de plus classique : elle <b>restaure de la vie</b> au joueur à l'utilisation. </br></br>
<h4>2.4.1.2 La potion de force</h4>
Linux: 🍺 </br>
Windows: <b>"s"</b></br>
Une fois utilisée, la potion de force rend le joueur plus fort en <b>augmentant ses dégats d'attaque</b>. </br></br>
<h4>2.4.1.3 La potion d'invincibilité</h4>
Linux: 💉</br>
Windows: <b>"i"</b></br>
Après avoir consommé cette potion, le joueur devient <b>invulnérable</b> pendant un <b>court instant</b></br></br>
<h4>2.4.1.4 La potion d'endurance</h4>
Linux: 🥃 </br>
Windows: <b> "e" </b></br>
L'utilisation de cette potion octroie au joueur une <b>endurance illimitée</b> pendant une <b>courte durée</b>, permettant au joueur d'utiliser ses attaques sans coût.</br>


<h2>2.4.3 La nourriture </h2>
A la différence des potions et des armes, le joueur <b>ne peut pas stocker de nourriture dans son inventaire</b>.</br>
Une fois ramassée, la nourriture est <b>instantanément consomée</b> et <b>rend de la vie et/ou de l'endurance</b> au joueur.

<h4>2.4.3.1 Les fruits</h4>
Les fruits permettent de <b>rendre de la vie</b> au joueur. </br>
Ils sont identifiable dans le jeu par les symboles suivant : </br>

Pomme :</br>
Linux: 🍎</br>
Windows: <b> "p" </b></br></br>

Banane :</br>
Linux: 🍌</br>
Windows: <b>"b"</b></br></br>

Carrote :</br>
Linux: 🥕</br>
Windows: <b>"c"</b></br></br>

Orange :</br>
Linux: 🍊</br>
Windows: <b>"o"</b></br></br>

Pêche :</br>
Linux: 🍑</br>
Windows: <b>"g"</b></br></br>


<h4>2.4.3.2 Le Burger</h4>
Linux: 🍔</br>
Windows: <b>"b"</b></br>
Le burger permet de <b>rendre de la vie et de l'endurance</b> au joueur. </br></br>


<h2>2.4.4 Les items spéciaux</h2
  
<h3>2.4.4.1 Les stacks d'argents</h3>
Les stacks d'argent sont des <b>objets trouvables</b> par le joueur dans le labyrinthe.</br>
<b>Une fois ramassés</b> et comme la nourriture, les stacks d'argent <b>disparaissent</b> et <b>créditent le joueur du montant</b> de celui-ci.</br>
L'affichage va varier en fonction du montant du stack d'argent.</br>
Entre ... et ... : 
Linux: </br>
Windows: <b</b></br></br>
Entre ... et ... : 
Linux: </br>
Windows: <b</b></br></br>
Entre ... et ... : 
Linux: </br>
Windows: <b</b></br></br>
Entre ... et ... : 
Linux: </br>
Windows: <b</b></br></br>


<h2>3. Cases</h2>
Les cases du jeu se divisent en <b>deux categories</b>, les <b>fonctionnelles</b> et les <b>cosmétiques</b>: les premières ont une <b>utilité</b> que cela soit dans le jeu ou la programmation alors que les deuxièmes n'apporteront qu'un <b>intérêt esthétique</b>. 
<h3>3.1. Fonctionelles</h3>
<h4>3.1.1. Cellules Normales</h4>
Ce sont les cellules <b>de base</b>.</br>
<img src="https://user-images.githubusercontent.com/74865653/114877044-3832b280-9dff-11eb-99d6-fc169290f972.png"/></br></br>
<img src="https://user-images.githubusercontent.com/74865653/114878669-c5c2d200-9e00-11eb-9ec1-3fae828f3b9c.png"/>
<h4>3.1.2. Cellules Bordure</h4>
Ce sont les <b>bordures de l'étage</b>, aucune entité <b>ne peut les traverser</b> (sauf exceptions).</br></br>
<img src="https://user-images.githubusercontent.com/74865653/114880411-551cb500-9e02-11eb-954f-7a3ce2ac38cd.png"/>
<h4>3.1.3. Cellules Vide</h4>
Ce sont des cellules <b>vides</b>.</br></br>
<img src="https://user-images.githubusercontent.com/74865653/114881537-66b28c80-9e03-11eb-97ed-f5db9a69c03f.png"/>
<h4>3.1.4. Cellules Monter</h4>
Elle permet de remonter à <b>l'étage precedent</b>.</br>
Linux: 👍</br>
Windows: <b>^</b></br>
<h4>3.1.5. Cellules Descendre</h4>
Elle permet de descendre à <b>l'etage precedent</b>.</br>
Linux: 👎</br>
Windows: <b>v</b></br>
<h4>3.1.6. Cellules Piege</h4>
Ce sont des cellules invisibles qui teleportent le joueur dans un <b>etage piégé<b>.
<h4>3.1.7. Cellules Chest</h4>
Un coffre est placé dans cette cellule.


<h2>4. Inventaire</h2>
L'inventaire du joueur permet de <b>stocker les objets</b> et la <b>monnaie</b> du joueur.</br>
Il affiche tout ceci dans une interface visible sur la droite de l'écran. </br></br>
<img src="https://user-images.githubusercontent.com/74865920/118412636-5fe59680-b69b-11eb-95e2-fd7d81c02104.png"/>

<h2>5. Sauvegarde</h2>
La sauvegarde permet au jouer de sauvegarder la partie pour la reprendre dans le futur. </br></br>  
Exemple quand aucune partie n'est sauvegardée : </br>  </br> 
<img src="https://user-images.githubusercontent.com/74865653/114881537-66b28c80-9e03-11eb-97ed-f5db9a69c03f.png"/></br> </br> 
Exemple de sauvegarde de partie: </br>  </br> 
<img src="https://user-images.githubusercontent.com/74865920/118412916-e9499880-b69c-11eb-9510-4d5d00eaa095.png"/></br> </br> 
Exemple de chargement d'une partie sauvegardée : </br> </br> 
<img src="https://user-images.githubusercontent.com/74865920/118412930-febec280-b69c-11eb-9092-f8695850bbc6.png"/></br> </br> 

