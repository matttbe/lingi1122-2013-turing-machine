
public class MTA_B {

	/**
	 * Pré
	 * 		La tête de lecture se trouve sur un caractère quelconque du ruban.
	 * Post 
	 * 		La tête de lecture se trouve sur le premier blanc à gauche de l’ancien emplacement
	 * 		de la tête de lecture. Le ruban est inchangé.
	 */
	static void findFirstBlankOnTheLeft(Tape t) throws Exception {
		/**
		 * Init : t.leftMove()
		 * H : t.isSymbol((char) 32)
		 * Iter : t.leftMove()
		 * Clot : /
		 * Inv : toutes les cases se trouvant à droite de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) sont non blanches
		 * Var : le nombre de cases à parcourir avant de sortir de la zone B
		 * 	(ce nombre diminue de 1 unité à chaque itération, puisque la tête de lecture se déplace de 1 cellule à la fois,
		 *  toujours dans la même direction, et on sait que la zone B est de taille finie, selon les conventions de représentation)
		 */
		t.leftMove();
		while(!t.isSymbol((char) 32)) {
			t.leftMove();
		}
	}

	/**
	 * Pré
	 * 		La tête de lecture se trouve sur un caractère quelconque du ruban.
	 * Post
	 * 		La tête de lecture se trouve sur le premier blanc à droite de l’ancien
	 * 		emplacement de la tête de lecture. Le ruban est inchangé.
	 */
	static void findFirstBlankOnTheRight(Tape t) throws Exception {
		/**
		 * Init : t.rightMove()
		 * H : t.isSymbol((char) 32)
		 * Iter : t.rightMove()
		 * Clot : /
		 * Inv : toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) sont non blanches
		 * Var : le nombre de cases à parcourir avant de sortir de la zone B
		 *  (ce nombre diminue de 1 unité à chaque itération, puisque la tête de lecture se déplace de 1 cellule à la fois,
		 *  toujours dans la même direction, et on sait que la zone B est de taille finie, selon les conventions de représentation)
		 */
		t.rightMove();
		while(!t.isSymbol((char) 32)) {
			t.rightMove();
		}
	}

	/**
	 * Pré
	 * 		La tête de lecture se trouve sur un blanc et, à sa gauche, se trouve une suite valide en binaire délimitée par un blanc.
	 * Post
	 * 		La tête de lecture est de retour sur le même blanc. La suite binaire a été convertie en une suite unaire
	 * 		de valeur équivalente délimitée par un blanc et se trouve à gauche de la tête de lecture.
	 * 		La taille de cette suite peut être différente de la précédente, les changements se font vers la gauche,
	 * 		c’est-à-dire que le blanc à gauche peut avoir été décalé. La séquence de caractères à droite de la tête de lecture reste inchangée.
	 */
	static void convertToUnaryLeft(Tape t) throws Exception {
		// on convertit le nombre binaire situé à gauche de la tête de lecture, en nombre décimal
		/**
		 * Init :
		 * 	long decimalResult=0;
		 * 	long exponent=0;
		 * 	t.leftMove();
		 * H : t.isSymbol((char) 32)
		 * Iter :
		 * 	if(t.isSymbol((char) 49)) {
				decimalResult=decimalResult+(long) Math.pow(2, exponent);
				}
			exponent++;
			t.leftMove();
		 * Clot : findFirstBlankOnTheRight(t)
		 * Inv : toutes les cases se trouvant à droite de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) sont non blanches
		 * Var : le nombre de cases à parcourir avant de sortir de la zone B
		 *  (ce nombre diminue de 1 unité à chaque itération, puisque la tête de lecture se déplace de 1 cellule à la fois,
		 *  toujours dans la même direction, et on sait que la zone B est de taille finie, selon les conventions de représentation)
		 */
		long decimalResult=0;
		long exponent=0;
		t.leftMove();
		while(!t.isSymbol((char) 32)) {
			if(t.isSymbol((char) 49)) {
				decimalResult=decimalResult+(long) Math.pow(2, exponent);
			}
			exponent++;
			t.leftMove();
		}
		findFirstBlankOnTheRight(t);

		// on écrit la représentation unaire à la place de la représentation binaire initiale
		/**
		 * Init :
		 *  t.leftMove();
		 * H : decimalResult==0
		 * Iter :
		 * 	decimalResult--;
		 * 	t.putSymbol((char) 49);
		 * 	t.leftMove();
		 * Clot :
		 * 	t.putSymbol((char) 32);
		 * 	findFirstBlankOnTheRight(t);
		 * Inv : toutes les cases se trouvant à droite de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent le caractère 49 de l'alphabet ASCII (le char '1')
		 * Var : decimalResult (est initialement supérieur à 0, diminue de 1 unité à chaque itération et on sort de la boucle lorsqu'il devient égal à 0, ce qui est garantit)
		 */
		t.leftMove();
		while(decimalResult!=0) {
			decimalResult--;
			t.putSymbol((char) 49);
			t.leftMove();
		}
		t.putSymbol((char) 32);
		findFirstBlankOnTheRight(t);
	}

	/**
	 * Pré
	 * 		La tête de lecture se trouve sur un blanc et, à sa droite, se trouve une suite
	 * 		valide en binaire délimitée par un blanc.
	 * Post
	 * 		La tête de lecture est de retour sur le même blanc. La suite binaire a été
	 * 		convertie en une suite unaire de valeur équivalente délimitée par un blanc et se
	 * 		trouve à droite de la tête de lecture. La taille de cette suite peut être différente
	 * 		de la précédente, les changements se font vers la droite, c’est-à-dire que le blanc à
	 * 		droite peut avoir été décalé. La séquence de caractères à gauche de la tête de lecture
	 * 		reste inchangée.
	 */
	static void convertToUnaryRight(Tape t) throws Exception {
		// on détermine tout d'abord l'exposant maximal dans la représentation binaire
		/**
		 * Init :
		 * 	long exponent=-1;
		 * 	t.rightMove();
		 * H : t.isSymbol((char) 32)
		 * Iter :
		 * 	exponent++;
		 *  t.rightMove();
		 * Clot : findFirstBlankOnTheLeft(t)
		 * Inv :
		 * 	- toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) sont non blanches
		 * 	- la variable exponent contient le nombre de fois que la tête de lecture s'est déplacée vers la droite - 1 (après l'instruction d'initialisation)
		 * Var : le nombre de cases à parcourir avant de sortir de la zone B
		 *  (ce nombre diminue de 1 unité à chaque itération, puisque la tête de lecture se déplace de 1 cellule à la fois,
		 *  toujours dans la même direction, et on sait que la zone B est de taille finie, selon les conventions de représentation)
		 */
		long exponent=-1;
		t.rightMove();
		while(!t.isSymbol((char)32)) {
			exponent++;
			t.rightMove();
		}
		findFirstBlankOnTheLeft(t);

		// on convertit le nombre binaire situé à droite de la tête de lecture, en nombre décimal
		/**
		 * Init :
		 * 	long decimalResult=0;
		 * 	t.rightMove();
		 * H : t.isSymbol((char) 32)
		 * Iter :
		 * 	if(t.isSymbol((char)49)) {
				decimalResult=decimalResult+(long) Math.pow(2, exponent);
			}
			exponent--;
			t.rightMove();
		 * Clot : findFirstBlankOnTheLeft(t)
		 * Inv :
		 * 	- toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) sont non blanches
		 * 	- la variable exponent >= 0
		 * Var : le nombre de cases à parcourir avant de sortir de la zone B
		 *  (ce nombre diminue de 1 unité à chaque itération, puisque la tête de lecture se déplace de 1 cellule à la fois,
		 *  toujours dans la même direction, et on sait que la zone B est de taille finie, selon les conventions de représentation)
		 */
		long decimalResult=0;
		t.rightMove();
		while(!t.isSymbol((char)32)) {
			if(t.isSymbol((char)49)) {
				decimalResult=decimalResult+(long) Math.pow(2, exponent);
			}
			exponent--;
			t.rightMove();
		}
		findFirstBlankOnTheLeft(t);

		// on écrit le résultat unaire à la place de la représentation binaire initiale
		/**
		 * Init :
		 * 	t.rightMove();
		 * H : decimalResult==0
		 * Iter :
		 * 	decimalResult--;
		 * 	t.putSymbol((char) 49);
		 * 	t.rightMove();
		 * Clot :
		 * 	t.putSymbol((char) 32);
		 * 	findFirstBlankOnTheLeft(t);
		 * Inv : toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position après l'instruction d'initialisation) contiennent le caractère 49 de l'alphabet ASCII (le char '1')
		 * Var : decimalResult (est initialement supérieur à 0, diminue de 1 unité à chaque itération et on sort de la boucle lorsqu'il devient égal à 0, ce qui est garantit)
		 */
		t.rightMove();
		while(decimalResult!=0) {
			decimalResult--;
			t.putSymbol((char)49);
			t.rightMove();
		}
		t.putSymbol((char)32);
		findFirstBlankOnTheLeft(t);
	}

	/**
	 * Pré
	 * 		La tête de lecture se trouve sur un blanc et, à sa gauche, se trouve une suite valide
	 * 		en unaire délimitée par un blanc.
	 * Post
	 * 		La tête de lecture est de retour sur le même blanc. La suite unaire a été convertie
	 * 		en une suite binaire de valeur équivalente délimitée par un blanc et se trouve à gauche de
	 * 		la tête de lecture. La taille de cette suite peut être différente de la précédente, les
	 * 		changements se font du coté gauche, c’est-à-dire que le blanc à gauche peut avoir été décalé.
	 * 		La séquence de caractères à droite de la tête de lecture reste inchangée.
	 */
	static void convertToBinaryLeft(Tape t) throws Exception {
		// on convertit le nombre binaire situé à gauche de la tête de lecture, en décimal (decimalValue)
		/**
		 * Init :
		 * 	long decimalValue=0;
		 * 	t.leftMove();
		 * H : t.isSymbol((char) 32)
		 * Iter :
		 * 	decimalValue++;
		 * 	t.leftMove();
		 * Clot : /
		 * Inv : -les cases se trouvant à droite de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) sont non blanches
		 * 		 -decimalValue >= 0
		 * Var : le nombre de cases à parcourir avant de sortir de la zone B (diminue de 1 à chaque itération et la
		 * zone B est de taille finie, on sort donc de la boucle avec certitude étant donné la condition d'arrêt H et le fait que la zone B
		 * est bordée par des caractères blancs
		 */
		long decimalValue=0;
		t.leftMove();
		while(!t.isSymbol((char)32)) {
			decimalValue++;
			t.leftMove();
		}

		// on détermine la plus haute puissance de 2 (exponent),
		// pour laquelle 2^exponent est inférieure à decimalValue
		/**
		 * Init :
		 *  long exponent = 0;
		 * H :
		 *  Math.pow(2,exponent) > decimalValue
		 * Iter :
		 *  exponent++;
		 * Clot :
		 *  exponent--;
		 * Inv : 0 <= exponent
		 * Var : decimalValue - 2^exponent
		 */
		long exponent=0;
		while(Math.pow(2, exponent)<=decimalValue) {
			exponent++;
		}
		exponent--;

		// on convertit le nombre décimal en nombre binaire et on écrit le résultat
		/**
		 * Init :
		 *  long c = decimalValue-exponent-1;
		 * H : c==0
		 * Iter :
		 *  t.rightMove();
		 *  t.putSymbol((char)32);
		 *  c--;
		 * Clot : /
		 * Inv :
		 * 	- la case se trouvant sous la tête de lecture et toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) sont blanches
		 * 	- 0 <= c <= decimalValue-exponent-1;
		 * Var : c (c>0 au départ, diminue de 1 unité à chaque itération et il y a sortie de boucle au moment ou c atteint la valeur 0)
		 */
		long c=decimalValue-exponent-1;
		while(c!=0) {
			t.rightMove();
			t.putSymbol((char)32);
			c--;
		}

		/**
		 * Init :
		 *  t.rightMove();
		 * H : exponent < 0
		 * Iter :
         *  if(decimalValue-(long)Math.pow(2, exponent)>=0) {
         *  t.putSymbol((char)49);
         *  decimalValue=decimalValue-(long)Math.pow(2, exponent);
         *  }
         *  else {
         *  t.putSymbol((char)48);
         *  }
         *  exponent--;
         *  t.rightMove();
		 * Clot : /
		 * Inv :
		 * - toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent soit le caractère '1' soit le caractère '0'
		 * - -1<=exponent
		 * Var : exponent+1
		 */
		t.rightMove();
		while(exponent>=0) {
			if(decimalValue-(long)Math.pow(2, exponent)>=0) {
				t.putSymbol((char)49);
				decimalValue=decimalValue-(long)Math.pow(2, exponent);
			} else {
				t.putSymbol((char)48);
			}
			exponent--;
			t.rightMove();
		}
	}

	/**
	 * Pré
	 * 		La tête de lecture se trouve sur un blanc et, à sa droite, se trouve une suite valide en
	 * 		unaire délimitée par un blanc.
	 *Post
	 *		La tête de lecture est de retour sur le même blanc. La suite unaire a été convertie
	 * 		en une suite binaire de valeur équivalente délimitée par un blanc et se trouve à droite de la
	 * 		tête de lecture. La taille de cette suite peut être différente de la précédente, les changements
	 * 		se font du coté droit, c’est-à-dire que le blanc à droite peut avoir été décalé. La séquence de
	 * 		caractères à gauche de la tête de lecture reste inchangée.
	 */
	static void convertToBinaryRight(Tape t) throws Exception {
		// on convertit le nombre unaire situé à droite en décimal (decimalValue)
		/**
		 * Init :
		 * 	long decimalValue = 0;
		 * 	t.rightMove();
		 * H : t.isSymbol((char)32)
		 * Iter :
		 *  decimalValue++;
		 *  t.rightMove();
		 * Clot : findFirstBlankOnTheLeft(t);
		 * Inv :
		 * 	- toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture juste après l'instruction Init) contiennent le caractère '1'
		 * 	- 0<=decimalValue
		 * Var : le nombre de cases à parcourir avant de sortir de la zone B
		 * 	(diminue de 1 unité à chaque itération et la zone B est de taille finie, on sort donc de la boucle avec certitude)
		 */
		long decimalValue=0;
		t.rightMove();
		while(!t.isSymbol((char)32)) {
			decimalValue++;
			t.rightMove();
		}
		findFirstBlankOnTheLeft(t);

		// on détermine la plus haute puissance de 2 (exponent)
		// pour laquelle 2^exponent est inférieure à decimalValue
		/**
		 * Init :
		 *  long exponent=0;
		 * H : Math.pow(2, exponent) > decimalValue
		 * Iter :
		 *  exponent++;
		 * Clot :
		 * 	exponent--;
		 * Inv : 0 <= exponent
		 * Var : decimalValue - 2^exponent
		 */
		long exponent=0;
		while(Math.pow(2, exponent)<=decimalValue) {
			exponent++;
		}
		exponent--;

		// on convertit le nombre décimal en nombre binaire et on écrit le résultat
		/**
		 * Init : t.rightMove();
		 * H : exponent < 0
		 * Iter :
		 *  if(decimalValue-(long)Math.pow(2, exponent) >=0){
		 *  t.putSymbol((char)49);
		 *  decimalValue=decimalValue-(long)Math.pow(2, exponent);
		 *  } else {
		 *  t.putSymbol((char48);
		 *  }
		 *  exponent--;
		 *  t.rightMove();
		 * Clot : /
		 * Inv :
		 * 	- toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent soit le caractère '1' soit le caractère '0'
		 * 	- -1 <= exponent
		 * Var : exponent + 1
		 */
		t.rightMove();
		while(exponent>=0) {
			if(decimalValue-(long)Math.pow(2, exponent)>=0) {
				t.putSymbol((char)49);
				decimalValue=decimalValue-(long)Math.pow(2, exponent);
			} else {
				t.putSymbol((char)48);
			}
			exponent--;
			t.rightMove();
		}
		
		// on nettoie
		/**
		 * Init : long c=0;
		 * H : t.isSymbol((char)32)
		 * Iter :
		 *  t.putSymbol((char)32)
		 *  t.rightMove();
		 *  c++;
		 * Clot : /
		 * Inv :
		 * 	- c >= 0
		 * 	- toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) sont blanches
		 * Var : le nombre de cases à parcourir avant de sortir de la zone B (diminue de 1 à chaque itération et la
		 * 	zone B est de taille finie, on sort donc de la boucle avec certitude)
		 */
		long c=0;
		while(!t.isSymbol((char)32)) {
			t.putSymbol((char)32);
			t.rightMove();
			c++;
		}

		/**
		 * Init : /
		 * H : c==0
		 * Iter :
		 * 	c--;
		 * 	t.leftMove();
		 * Clot : findFirstBlankOnTheLeft(t);
		 * Inv : c >=0
		 * Var : c (est supérieur à 0 initialement et diminue de 1 à chaque itération, on sort donc de la boucle
		 * 	avec certiture étant donné la condition d'arrêt H)
		 */
		while(c!=0) {
			c--;
			t.leftMove();
		}
		findFirstBlankOnTheLeft(t);
	}

	/**
	 * Pré
	 * 		La tête de lecture est placée sur un blanc. À gauche se situe une suite de caractères délimitée
	 * 		par un blanc.
	 * Post
	 * 		La tête de lecture est de retour au même endroit. La suite de caractères à sa gauche est quant à elle
	 * 		inchangée et un blanc a été ajouté après le blanc délimitant cette suite.
	 */
	static void addOneBlankAfterTheFirstBlankOnTheLeft(Tape t) throws Exception {
		findFirstBlankOnTheLeft(t);
		t.leftMove();
		t.putSymbol((char) 32);
		t.rightMove();
		findFirstBlankOnTheRight(t);
	}

	/**
	 * Pré
	 * 		La tête de lecture est placée sur un blanc. À gauche se situe une suite de 1 délimitée par un blanc.
	 * Post
	 * 		La tête de lecture est de retour au même endroit. La suite de 1 à sa gauche est quant à elle inchangée
	 * 		(elle a pu être modifiée entre temps mais on y retrouve toujours le même nombre de 1 l’un à la suite
	 * 		délimité par un blanc). Cette même suite de 1 à été recopiée du 2ème blanc à gauche vers la gauche
	 * 		(en décallant ce deuxième blanc vers la gauche et en le remplaçant par un caractère 1).
	 */
	static void copyLeftOfLeftSequence(Tape t) throws Exception {
		// on compte le nombre de 1 situés directement à gauche
		/**
		 * Init :
		 *  long nbOnes=0;
		 *  t.leftMove();
		 * H : ! t.isSymbol((char)49)
		 * Iter :
		 *  nbOnes++;
		 *  t.leftMove();
		 * Clot : findFirstBlankOnTheLeft(t);
		 * Inv :
		 * 	- nbOnes >= 0
		 * 	- toutes les cases se trouvant à droite de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent le caractère '1'
		 * Var : le nombre de cases à parcourir avant de sortir de la zone B (diminue de 1 à chaque itération et la
		 * 	zone B est de taille finie, on sort donc de la boucle avec certitude)
		 */
		long nbOnes=0;
		t.leftMove();
		while(t.isSymbol((char) 49)) {
			nbOnes++;
			t.leftMove();
		}
		// on se déplace au 2ème blanc à gauche
		findFirstBlankOnTheLeft(t);

		// on recopie la suite de 1
		/**
		 * Init : /
		 * H : nbOnes==0
		 * Iter :
		 *  nbOnes--;
		 *  t.putSymbol((char)49)
		 *  t.leftMove();
		 * Clot :
		 *  t.putSymbol((char) 32);
		 *  findFirstBlankOnTheRight(t);
		 *  findFirstBlankOnTheRight(t);
		 * Inv :
		 * 	- nbOnes >= O
		 * 	- toutes les cases se trouvant à droite de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent le caractère '1'
		 * Var : nbOnes
		 */
		while(nbOnes!=0) {
			nbOnes--;
			t.putSymbol((char) 49);
			t.leftMove();
		}

		// on replace un caractère blanc à la suite de la suite de 1
		t.putSymbol((char) 32);

		// on replace la tête de lecture correctement
		findFirstBlankOnTheRight(t);
		findFirstBlankOnTheRight(t);
	}

	/**
	 * Pré
	 * 		La tête de lecture se trouve sur un blanc, à sa droite se trouve une représentation d’un nombre
	 * 		délimitée par une blanc.
	 * Post
	 * 		La tête de lecture se trouve au même endroit que précédemment.
	 * 		La partie gauche du ruban n’a pas été modifiée.
	 * 		La représentation du nombre à sa droite délimité par un blanc a été effacée
	 * 		(remplacée par des blancs).
	 */
	static void eraseRight(Tape t) throws Exception {
		/**
		 * Init :
		 * 	findFirstBlankOnTheRight(t);		
		 *  t.leftMove();
		 * H : t.isSymbol((char)32)
		 * Iter :
		 *  t.putSymbol((char) 32)
		 *  t.leftMove();
		 * Clot : /
		 * Inv : toutes les cases se trouvant à droite de la tête de lecture jusqu'à la position initiale de départ i_0 
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) sont blanches
		 * Var : Var : le nombre de cases à parcourir avant de sortir de la zone B (diminue de 1 à chaque itération 
		 * 	et la zone B est de taille finie, on sort donc de la boucle avec certitude)
		 */
		// on se place sur le blanc à l'extrémité droite de la suite à supprimer
		findFirstBlankOnTheRight(t);
		t.leftMove();
		while(!t.isSymbol((char) 32)) {
			t.putSymbol((char) 32);
			t.leftMove();
		}
	}

	/**
	 * Pré
	 * 		La tête de lecture est placée sur un blanc, à gauche duquel se situe une suite de 1.
	 * Post
	 * 		La tête de lecture est au même endroit. La suite de 1 quand à elle est inchangée.
	 * 		Cette suite de 1 à été recopiée du 2ème blanc à droite vers la droite.
	 */
	static void copyRightOfLeftSequence(Tape t) throws Exception {
		// on compte le nombre de 1 à gauche du blanc en dessous de la tête de lecture
		/**
		 * Init :
		 *  longe nbOnes = 0;
		 *  t.leftMove();
		 * H : ! t.isSymbol ((char) 49)
		 * Iter :
		 *  nbOnes++;
		 *  t.leftMove();
		 * Clot : findFirstBlankOnTheRight(t);
		 * Inv :
		 * 	-nbOnes >= 0
		 * 	-toutes les cases se trouvant à droite de la tête de lecture jusqu'à la position initiale de départ i_0 
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent le caractère '1'
		 * Var :
		 * 	le nombre de cases à parcourir avant de sortir de la zone B
		 * 	(diminue de 1 à chaque itération et la zone B est de taille finie et bordée par des cases vides 
		 * 	contenant le caractère ' ' qui est différent du caractère 49 ‘1’ de l’alphabet ASCII, on sort donc de la boucle avec certitude)
		 */
		long nbOnes=0;
		t.leftMove();
		while(t.isSymbol((char) 49)) {
			nbOnes++;
			t.leftMove();
		}

		// on revient au blanc de départ
		findFirstBlankOnTheRight(t);

		// on copie la suite de 1 à droite du blanc de départ
		/**
		 * Init :
		 * 	t.rightMove();
		 * H : nbOnes == 0
		 * Iter :
		 *  nbOnes--;
		 *  t.putSymbol((char)49);
		 *  t.rightMove();
		 * Clot :
		 *  findFirstBlankOnTheLeft(t);
		 * Inv :
		 * 	- toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent le caractère '1'
		 * 	- nbOnes >= 0
		 * Var : nbOnes (est positif en entrant dans la boucle et diminue de 1 unité à chaque itération,
		 * 	étant donné la condition d’arrêt H, on sort de la boucle avec certitude)
		 */
		t.rightMove();
		while(nbOnes!=0) {
			nbOnes--;
			t.putSymbol((char) 49);
			t.rightMove();
		}
		// on revient à la position de départ (sur le blanc de départ)
		findFirstBlankOnTheLeft(t);
	}

	/**
	 * Pré
	 * 		La tête de lecture se trouve sur un blanc du ruban. A sa droite se trouve une représentation
	 * 		unaire d’un nombre délimité par un blanc (si il n’y a que des blancs le nombre est 0).
	 * Post
	 * 		La tête de lecture se trouve à la même position. Le nombre unaire a droite a été incrémenté de 1.
	 * 		Dans le cas ou il était nul (blanc), il est mis a 1.
	 */
	static void incrementLeft(Tape t) throws Exception {
		// on se déplace jusqu'à la fin du nombre unaire représenté
		findFirstBlankOnTheLeft(t);

		// on ajoute le 1
		t.putSymbol((char) 49);

		// on s'assure qu'il y a un blanc pour délimiter le nouveau nombre unaire représenté
		t.leftMove();
		t.putSymbol((char) 32);

		// on retourne à la position de départ
		findFirstBlankOnTheRight(t);
	}

	/**
	 * Pré
	 * 		La tête de lecture se trouve sur un blanc séparant deux nombres en représentation unaire.
	 * 		Ces deux nombres sont délimités par un blanc.
	 * Post
	 * 		La tête se trouve sur le blanc entre les 2 représentations unaire. Le premier nombre a été
	 * 		diminué du second et le deuxième conserve sa valeur mais a été décalé de facon a ce qu’il n’y ait toujours
	 * 		qu’un espace qui sépare les nombres unaires.
	 */
	static void shiftSubstract(Tape t) throws Exception {
		// on détermine la valeur du nombre unaire de droite
		/**
		 * Init :
		 *  long valueNbRight=0;
		 *  t.rightMove();
		 * H : !t.isSymbol((char)49)
		 * Iter :
		 *  valueNbRight++;
		 *  t.rightMove();
		 * Clot :
		 *  findFirstBlankOnTheLeft(t);
		 * Inv :
		 * - toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent le caractère '1'
		 * - valueNbRight >=0
		 * Var : le nombre de cases à parcourir avant de sortir de la zone B 
		 * 	(diminue de 1 à chaque itération et la zone B est de taille finie et bordée par des cases vides contenant 
		 * 	le caractère ' ' qui est différent du caractère 49 ‘1’ de l’alphabet ASCII, on sort donc de la boucle avec certitude)
		 */
		long valueNbRight=0;
		t.rightMove();
		while(t.isSymbol((char) 49)) {
			valueNbRight++;
			t.rightMove();
		}

		// on se replace sur le blanc de départ
		findFirstBlankOnTheLeft(t);

		// on se déplace de valueNbRight cases vers la gauche, et on effectue la soustraction
		/**
		 * Init :
		 *  long c = valueNbRight;
		 * H : c == 0
		 * Iter :
		 *  c--;
		 *  t.leftMove();
		 * Clot :
		 *  t.putSymbol((char) 32);
		 * Inv : c>=0
		 * Var : c (diminue de 1 à chaque itération et est positif en rentrant dans la boucle,
		 * 	étant donné la condition d’arrêt H, on sort de la boucle avec certitude)
		 */
		long c=valueNbRight;
		while(c!=0) {
			c--;
			t.leftMove();
		}
		t.putSymbol((char) 32);

        /**
		 * Init :
		 *  t.rightMove();
		 * H : valueNbRight == 0
		 * Iter :
		 *  ValueNbRight --;
		 *  t.putSymbol((char)49);
		 *  t.rightMove();
		 * Clot : /
		 * Inv :
		 * 	- valueNbRight >= 0
		 * 	- toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent le caractère '1'
		 * Var : valueNbRight (est positif en entrant dans la boucle et diminue de 1 à chaque itération, 
		 * 	étant donné la condition d’arrêt H, on sort de la boucle avec certitude)
		 */
		t.rightMove();
		while(valueNbRight!=0) {
			valueNbRight--;
			t.putSymbol((char) 49);
			t.rightMove();
		}

		// on nettoie l'ancien nombre unaire à droite
		/**
		 * Init :
		 *  long nbNewBlancs=0;
		 * H : t.isSymbol((char) 32)
		 * Iter :
		 *  t.putSymbole((char)32);
		 *  t.rightMove();
		 *  nbNewBlancs++;
		 * Clot : /
		 * Inv :
		 * 	-0=<nbNewBlancs
		 * 	- toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) sont blanches
		 * Var : le nombre de cases à parcourir avant de sortir de la zone B
		 * 	(diminue de 1 à chaque itération et la zone B est de taille finie et bordée par des cases vides contenant
		 * 	le caractère caractère 32 ‘ ’ de l’alphabet ASCII), étant donné la condition d’arrêt H, on sort donc de la boucle avec certitude)
		 */
		long nbNewBlancs=0;
		while(!t.isSymbol((char) 32)) {
			t.putSymbol((char)32);
			t.rightMove();
			nbNewBlancs++;
		}

		// on revient sur le blanc entre les deux nombres unaires
		/**
		 * Init : /
		 * H : nbNewBlancs == 0
		 * Iter :
		 *  t.leftMove();
		 *  nbNewBlancs--;
		 * Clot :
		 *  findFirstBlankOnTheLeft(t);
		 * Inv : nbNewBlancs>=0
		 * Var : nbNewBlancs (est positif en entrant dans la boucle et diminue de 1 à chaque itération,
		 * 	étant donné la condition d’arrêt H, on sort de la boucle avec certitude)
		 */
		while(nbNewBlancs!=0) {
			t.leftMove();
			nbNewBlancs--;
		}
		findFirstBlankOnTheLeft(t);
	}

	static void add(Tape t) throws Exception {
		// on place la tête de lecture entre les deux nombres binaires
		findFirstBlankOnTheLeft(t);

		// on convertit le nombre binaire à droite en unaire
		convertToUnaryRight(t);

		// on convertit le nombre binaire à gauche en unaire
		convertToUnaryLeft(t);

		// on retire un 1 à l'extrême droite du nombre de droite
		findFirstBlankOnTheRight(t);
		t.leftMove();
		t.putSymbol((char)32);
		findFirstBlankOnTheLeft(t);

		// on ajout un 1 à la place du blanc séparant initialement les deux nombres
		t.putSymbol((char)49);

		// on reconvertit le nouveau nombre calculé, en binaire
		findFirstBlankOnTheRight(t);
		convertToBinaryLeft(t);
	}

	static void substract(Tape t) throws Exception {
		findFirstBlankOnTheLeft(t);

		convertToUnaryLeft(t);
		convertToUnaryRight(t);

		// on regarde si le membre de droite n'est pas plus élevé que celui de gauche
		/**
		 * Init :
		 *  long right = 0;
		 *  t.rightMove();
		 * H : t.isSymbol((char)32)
		 * Iter :
		 *  right++;
		 *  t.rightMove();
		 * Clot : findFirstBlankOnTheLeft(t);
		 * Inv :
		 *  - right >=0
		 *  - toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) sont non blanches
		 * Var :
		 * 	le nombre de cases à parcourir avant de sortir de la zone B (diminue de 1 à chaque itération et la
		 * 	zone B est de taille finie et bornée par des cases vides contenant le caractère ' ', on sort donc de la boucle avec certitude)
		 */
		long right=0;
		t.rightMove();
		while(!t.isSymbol((char)32)) {
			right++;
			t.rightMove();
		}
		findFirstBlankOnTheLeft(t);

		/**
		 * Init :
		 * 	long left=0;
		 * 	t.leftMove();
		 * H : t.isSymbol((char)32)
		 * Iter :
		 * 	left++;
		 * 	t.leftMove();
		 * Clot : findFirstBlankOnTheRight(t);
		 * Inv :
		 * 	- left >= 0
		 * 	- toutes les cases se trouvant à droite de la tête de lecture jusqu'à la position initiale de départ i_0
		 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) sont non blanches
		 * Var : le nombre de cases à parcourir avant de sortir de la zone B (diminue de 1 à chaque itération et la
		 * zone B est de taille finie et bornée par des cases vides contenant le caractère ' ', on sort donc de la boucle avec certitude)
		 */
		long left=0;
		t.leftMove();
		while(!t.isSymbol((char)32)) {
			left++;
			t.leftMove();
		}
		findFirstBlankOnTheRight(t);
		
		if(right>left) {
			// on écrit une suite de 0
			eraseRight(t);
			findFirstBlankOnTheLeft(t);
			eraseRight(t);
			t.putSymbol((char)48);
			t.rightMove();
		} else {

			/**
			 * Init : boolean substractFinished=false;
			 * H : substractFinished==true
			 * Iter :
			 * findFirstBlankOnTheRight(t);
				t.leftMove();
				if(t.isSymbol((char)49)) {
					t.putSymbol((char)32);
					findFirstBlankOnTheLeft(t);
				} else {
					substractFinished=true;
				}

				if(!substractFinished) {
					findFirstBlankOnTheLeft(t);
					t.rightMove();
					if(t.isSymbol((char)49)) {
						t.putSymbol((char)32);
						findFirstBlankOnTheRight(t);
					} else {
						substractFinished=true;
					}
				}
			 * Clot : /
			 * Inv : toutes les cases se trouvant à gauche (resp. à droite) de la tête de lecture jusqu'à la position initiale de départ i_0 lorsque
			 *  la tête de lecture se trouve à droite de celle-ci (resp. à gauche).
			 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent le caractère 49 de l'alphabet ASCII (le char '1')
			 * Var : le nombre de 1 composant le membre situé à droite de la tête de lecture, à l'initialisation de la boucle.
			 * 	Au départ, ce nombre est >0 que divisor. Il diminue de 1 unité à chaque itération, 
			 *	or il doit rester >= 0 (condition d'arrêt).
			 */
			boolean substractFinished=false;
			while(!substractFinished) {
				findFirstBlankOnTheRight(t);
				t.leftMove();
				if(t.isSymbol((char)49)) {
					t.putSymbol((char)32);
					findFirstBlankOnTheLeft(t);
				} else {
					substractFinished=true;
				}

				if(!substractFinished) {
					findFirstBlankOnTheLeft(t);
					t.rightMove();
					if(t.isSymbol((char)49)) {
						t.putSymbol((char)32);
						findFirstBlankOnTheRight(t);
					} else {
						substractFinished=true;
					}
				}
			}

			convertToBinaryLeft(t);
		}
	}

	static void multiply(Tape t) throws Exception {
		// on se place entre les deux nombre binaire
		findFirstBlankOnTheLeft(t);

		convertToUnaryLeft(t);
		convertToUnaryRight(t);

		// on ajoute un blanc à gauche du premier blanc de gauche
		addOneBlankAfterTheFirstBlankOnTheLeft(t);
		
		/**
		 * Init : t.rightMove();
		 * H : !t.isSymbol((char)49)
		 * Iter :
		 *  findFirstBlankOnTheRight(t);
		 *  t.leftMove();
		 *  t.putSymbol((char)32);
		 *  findFirstBlankOnTheLeft(t);
		 *  copyLeftOfLeftSequence(t);
		 *  t.rightMove();
		 * Clot :
		 * 	t.leftMove();
		 * Inv : toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 *  (i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent le caractère 49 de l'alphabet ASCII (le char '1')
		 * Var :  le nombre de cases contenant le caractères '1' (code 49 de l'alphabet ASCII) se trouvant entre la position de la tête de lecture 
		 * 	et la position initiale de celle-ci (i.e. sa position après l'instruction d'initialisation Init), ce nombre est positif à l'entrée 
		 * 	de la boucle et diminue strictement à chaque itération. Compte tenu de la condition d'arrêt H, cela guarantit que le nombre de bouclage est fini.
		 */
		t.rightMove();
		while(t.isSymbol((char)49)){
			// on décrémente le nombre de droite de 1 unité
			findFirstBlankOnTheRight(t);
			t.leftMove();
			t.putSymbol((char)32);
			findFirstBlankOnTheLeft(t);

			copyLeftOfLeftSequence(t);

			t.rightMove();
		}
		t.leftMove();

		// on place la tête de lecture sur le blanc suivant à gauche
		findFirstBlankOnTheLeft(t);

		// le résultat de la multiplication se trouve à gauche de se blanc.
		// on peut donc nettoyer le reste
		eraseRight(t);

		// on converti le résultat unaire en binaire
		convertToBinaryLeft(t);
	}

	static void divide(Tape t) throws Exception {
		// on se place entre les deux nombres à diviser
		findFirstBlankOnTheLeft(t);
				
		// on convertit le dividende et le diviseur en unaire
		convertToUnaryLeft(t);
		convertToUnaryRight(t);
		
		// on teste si le diviseur est plus grand que le dividende
		/**
		 * Init :
		 *  long divisor = 0;
		 *  t.rightMove();
		 * H : !t.isSymbol((char)49)
		 * Iter :
		 *  divisor++;
		 *  t.rightMove();
		 * Clot :
		 *  findFirstBlankOnTheLeft(t);
		 * Inv :
		 * 	- divisor >= 0
		 * 	- toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
		 *    (i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent le caractère 49 de l'alphabet ASCII (le char '1')
		 * Var : le nombre de cases à parcourir avant de quitter la suite de 1
		 * 	(ce nombre diminue de 1 unité à chaque itération, puisque la tête de lecture se déplace de 1 cellule à la fois,
		 *  toujours dans la même direction, et on sait que la suite est de taille finie, puisqu'elle est la représentation en unaire d'un nombre fini)
		 */
		long divisor=0;
		t.rightMove();
		while(t.isSymbol((char)49)) {
			divisor++;
			t.rightMove();
		}
		findFirstBlankOnTheLeft(t);

		/**
		 * Init :
		 *  long dividend = 0;
		 *  t.leftMove();
		 * H : !t.isSymbol((char49))
		 * Iter :
		 *  dividend++;
		 *  t.leftMove();
		 * Clot :
		 *  findFirstBlankOnTheRight(t);
		 * Inv :
		 * 	- dividend >= 0
		 * 	- toutes les cases se trouvant à droite de la tête de lecture jusqu'à la position initiale de départ i_0
		 *    (i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent le caractère 49 de l'alphabet ASCII (le char '1')
		 * Var : le nombre de cases à parcourir avant de quitter la suite de 1
		 * 	(ce nombre diminue de 1 unité à chaque itération, puisque la tête de lecture se déplace de 1 cellule à la fois,
		 *  toujours dans la même direction, et on sait que la suite est de taille finie, puisqu'elle est la représentation en unaire d'un nombre fini)
		 */
		long dividend=0;
		t.leftMove();
		while(t.isSymbol((char)49)) {
			dividend++;
			t.leftMove();
		}
		findFirstBlankOnTheRight(t);
				
		if(dividend>divisor) {
			/**
			 * Init :
			 *  long rest=0;
			 * 	shiftSubstract(t);
			 * 	rest=restValue(t);
			 * 	findFirstBlankOnTheLeft(t);
			 * 	incrementLeft(t);
			 * 	findFirstBlankOnTheRight(t);
			 * H : rest < divisor
			 * Iter :
			 * 	shiftSubstract(t);
			 * 	rest=restValue(t);
			 * 	findFirstBlankOnTheLeft(t);
			 * 	incrementLeft(t);
			 * 	findFirstBlankOnTheRight(t);
			 * Clot : /
			 * Inv : toutes les cases se trouvant à gauche de la tête de lecture jusqu'à la position initiale de départ i_0
			 * 	(i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent le caractère 49 de l'alphabet ASCII (le char '1')
			 * Var : le nombre de 1 composant le reste. Au départ, ce nombre est > que divisor. Il diminue de 1 unité à chaque itération, 
			 *	or il doit rester >= à divisor (condition d'arrêt), qui lui reste inchangé.
			 */
			long rest=0;
			shiftSubstract(t);
			rest=restValue(t);
			findFirstBlankOnTheLeft(t);
			incrementLeft(t);
			findFirstBlankOnTheRight(t);
			while(rest>=divisor) {
				// on calcule le reste
				shiftSubstract(t);
				rest=restValue(t);
				// on augment le quotient de 1
				findFirstBlankOnTheLeft(t);
				incrementLeft(t);
				// on replace la tête de lecture à la position entre les 2 unaires
				findFirstBlankOnTheRight(t);
			}
						
			eraseRight(t);
			findFirstBlankOnTheLeft(t);
			
			convertToBinaryLeft(t);
			convertToBinaryRight(t);

			findFirstBlankOnTheRight(t);
		} else {
			throw new Exception();
		}
	}

	/**
	 * Pre
	 * 		la tête de lecture se trouve à droite de la représentation en unaire du reste dont on cherche à déterminer la valeur en décimal.
	 * Post
	 * 		la tête de lecture se est de retour à sa position de départ. La valeur décimale du reste est retournée.
	 */
	private static long restValue(Tape t) throws Exception {
		/**
		 * Init :
		 * 	long rest=0;
		 * 	t.leftMove();
		 * H : !t.isSymbol((char)49)
		 * Iter :
		 * 	rest++;
		 * 	t.leftMove();
		 * Clot : findFirstBlankOnTheRight(t);
		 * Inv : toutes les cases se trouvant à droite de la tête de lecture jusqu'à la position initiale de départ i_0
		 *  (i.e. la position de la tête de lecture après l'instruction d'initialisation) contiennent le caractère 49 de l'alphabet ASCII (le char '1')
		 * Var :  le nombre de cases à parcourir avant de quitter la suite de 1
		 * 	(ce nombre diminue de 1 unité à chaque itération, puisque la tête de lecture se déplace de 1 cellule à la fois,
		 *  toujours dans la même direction, et on sait que la suite est de taille finie, puisqu'elle est la représentation en unaire d'un nombre fini)
		 */
		long rest=0;
		t.leftMove();
		while(t.isSymbol((char)49)) {
			rest++;
			t.leftMove();
		}
		findFirstBlankOnTheRight(t);
		return rest;
	}
}
