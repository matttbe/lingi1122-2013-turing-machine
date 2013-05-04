public interface Tape{

/* Spécification :
   -------------
 Une instance de (toute classe implémentant l'interface) Tape représente
 un ruban de machine de Turing moyennant les conventions, précisions et
 restrictions ci-dessous.
 
 ° Un ruban est une suite de cases infinie "dans les deux sens", munie 
   d'une tête de lecture qui désigne une case particulière, appelée la "case
   sous la tête de lecture". 
 
 ° Chaque case du ruban contient un (unique) symbole appartenant à un certain
   alphabet Gamma.
   
 ° Nous imposons l'utilisation d'un alphabet Gamma particulier : Gamma est 
   l'ensemble des caractères imprimables du code ASCII, personnifiés en Java
   par les valeurs de type char comprises entre 32 (code du caractère "espace") 
   et 126 (code du caractère ~), incluses.
   
 ° L'alphabet contient un caractère spécial appelé caractère "blanc" et noté B.
   Nous imposons le caractère "espace" (code 32) comme caractère blanc.
 
 ° Toutes les cases du ruban sont blanches sauf un nombre fini d'entre elles. 
 
 Les opérations disponibles sur un ruban sont spécifiées ci-dessous.
 Outre les opérations "classiques" d'écriture, lecture et déplacement de 
 la tête de lecture, on dispose d'une opération pour vérifier la cohérence
 de l'implémentation du ruban et une autre pour afficher son contenu de façon
 "lisible".
 
 */
 
 public boolean repOk() ;
 // Résultat : true si la structure de données vérifie l'invariant de 
 // représentation ; false, sinon.

 
 public boolean isSymbol(char s) throws Exception ;
 // Résultat : true si s est le symbole sous la tête de lecture,
 //   false si s est un symbole permis différent de celui sous la tête de lecture.
 // Une exception est lancée si s n'est pas un symbole permis.
 // Le ruban n'est pas modifié (en aucun cas).
 
 public void putSymbol(char s) throws Exception ;
 // Une exception est lancée si s n'est pas un symbole permis ;
 // dans ce cas, le ruban n'est pas modifié.
 // Le caractère s est écrit sous la tête de lecture si c'est un symbole permis ;
 // les autres cases du ruban sont inchangées, dans ce cas.
 
 public void leftMove() ;
 // Déplace la tête de lecture d'une case vers la gauche.
 
 public void rightMove() ;
 // Déplace la tête de lecture d'une case vers la droite.

 public String toString() ;
 // résultat : une représentation du ruban sous la forme
 //
 // alpha[s]beta
 //
 // où s est le symbole sous la tête de lecture,
 // alpha est un suffixe de la suite des symboles placés avant la tête de lecture, 
 // beta  est un préfixe de la suite des symboles placés après la tête de lecture,
 // alpha et beta contiennent tous les symboles non blancs du ruban (sauf s bien sûr). 
 // NB. Cette représentation est ambiguë dans certains cas.
 
 /* Constructeur. */
 /* ------------- */
 // Toutes les classes qui implémentent cet interface doivent contenir
 // un unique constructeur vérifiant la spécification ci-dessous :
 //
 // public TapeA_B(String init) throws Exception
 // 
 // Si init ne contient que des caractères qui sont des symboles valides,
 // ce constructeur crée un ruban contenant la suite de caractères init,
 // complétée à gauche et à droite par des caractères blancs.
 // En outre, la tête de lecture est placée sur la case située
 // exactement après le dernier symbole de init.
 //
 // Une exception est lancée si init contient au moins un caractère qui n'est pas
 // un symbole valide (ou si init == null).
 
}