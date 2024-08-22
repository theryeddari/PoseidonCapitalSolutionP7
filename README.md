# Poseidon Capital Solutions - Backend


**Poseidon Capital Solutions** est une société financière qui utilise différents outils du marché pour son fonctionnement. Ce projet se concentre sur l'implémentation des méthodes CRUD pour plusieurs entités financières, ainsi que sur la mise en place de la sécurité et de l'authentification.

## Prérequis

- **Java** : 21
- **Spring Boot** : 3.2.2
- **MySQL** : pour la base de donnée
- **JUnit** : 5
- **Log4j2**
- **Thymeleaf** : pour les vues
- **Jacoco** : pour la couverture de code
- **Surefire** : pour les rapports de tests



## Installation

La branch Main est utilisé comme branch de présentation par défaut.

Pour toute utilisation du code, ajout ou modification veuillez creer une nouvelle branch à partir de la branch Master qui est diponible dans le projet aprés avoir cloner le repro.

[Branch Master](https://github.com/theryeddari/PoseidonCapitalSolutionP7.git) 

Clonez le dépôt de la branch Master:

```
git clone https://github.com/theryeddari/PoseidonCapitalSolutionP7.git
```
Copier le code
```
cd PoseidonCapital
```
Compilez et installez les dépendances :
```
./mvnw clean install
```



##Configurez la base de données :

- Installer Mysql définnisez un compte root et un mot de passe, qu'il faudra indiquer en variable d'environnement
- à l'aide de Mysql vous devez creer une base de donnée appelé " demo ".
 
Lancez l'application :

```
./mvnw spring-boot:run
```
## Fonctionnalités Implémentées
### CRUD pour les Entités

- Implémentation des méthodes Create, Read, Update, Delete pour les 6 entités financières.

## Améliorations et Corrections

- refactorisation complète des méthodes de test existante
- correction des templates 
- correction des routes

### Authentification et Sécurité

- Authentification session-based mise en place avec Spring Security.
- Validation des saisies pour les champs numériques et mots de passe :

     - Les mots de passe doivent contenir au moins une lettre majuscule, 8 caractères, un chiffre et un symbole.
     - Un message apparait pour les champs manquant.
     - Un message apparait pour les champs de valeur numérique ne présentant pas les caractéristiques nécessaires 

### Rendu des Vues

- Thymeleaf : Utilisé pour le rendu des vues côté serveur. Les templates Thymeleaf se trouvent dans le répertoire src/main/resources/templates. Vous pouvez personnaliser les pages HTML pour l'interface utilisateur en utilisant les fichiers de ce répertoire.

### Documentation

- Javadoc : La documentation est fournie pour toutes les méthodes et classe.

### Tests
Pour exécuter les tests unitaires :

``` 
./mvnw test
```
Le rapport de test est disponible dans le dossier target/site/index.html ou présent  ici :

[rapport SureFire](https://github.com/theryeddari/P8-TourGuide/blob/e51bbd3725823a355724c16e5ba6e0512cbd124f/surefire.png)

### Couverture de code

Pour générer un rapport de couverture :

```
./mvnw verify
```

Le rapports de couverture est disponible dans le dossier target/site/jacoco/index.html ou présent  ici

[rapport Jacoco](https://github.com/theryeddari/P8-TourGuide/blob/e51bbd3725823a355724c16e5ba6e0512cbd124f/jacoco.png)


## Structure du Projet
```
/src
  /main
    /java
      com/nnk/springboot
    /resources
  /test
    /java
      com/nnk/springboot
```
## License
Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.

### Contact
Pour toute question, contactez moi.
