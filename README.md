# SAE E Sport

# Gestionnaire de Compétitions Esport en Java
Bienvenue dans le Gestionnaire de Compétitions Esport, une application Java dédiée à la gestion efficace de tournois esportifs. Ce projet utilise derby.jar pour l'exploitation de la base de données JDBC, lequel est inclus dans le répertoire de ce projet sur GitLab. L'IDE recommandé pour ce projet est Eclipse. Pour lancer l'application, suivez les étapes ci-dessous.

# Installation
1. Enregistrez le code source sur GitLab et le dézippez ou clonez le dépôt :

   `git clone https://gitlab.info.iut-tlse3.fr/khc4298a/SAE-e-sport.git `

2. Ouvrez le projet avec Eclipse.

3. Ajoutez Derby.jar au projet via :
   - Properties -> Java Build Path -> Add External JARs

# Exécution de l'Application
1. Ouvrez le package "Application"

2. Lancez le fichier "Donnees.java" pour charger les jeux de données
   -  Cliquer sur "Run Application"

3. Ensuite, lancez le fichier "Application.java" pour exécuter l'application principale

# Fonctionnalités

1. Identification 
   - Deux types d'utilisateurs : administrateur et arbitre
   - L'admin peut gérer les tournois, les matchs, l'historique et les arbitres (sauf choisir le vainqueur d'un match et de la finale)
   - Les arbitres peuvent choisir le vainqueur d'un match et de la finale

2. Gestion des Tournois
   - Création d'un tournoi
   - Affichage de tous les tournois
   - Importation des équipes pour un tournoi
   - Attribution des arbitres pour un tournoi
   - Ouverture et fermeture d'un tournoi
   - Détermination du vainqueur d'un tournoi

3. Gestion des Matchs
   - Choix des vainqueurs
   - Calcul automatique des points pour le classement
   - Création d'une finale

4. Gestion des équipes
   - Affichage de toutes les équipes dans la base de données 
   - Modification de nom et de pays d'une équipe
   - La composition d'une équipe ne peut pas être changer pendant la saison en cours

5. Gestion des Arbitres
   - Ajout et suppression d'arbitres

6. Gestion de l'Historique des Points
   - Suivi des points gagnés par chaque équipe dans tous les tournois joués


# Licence

Ce projet est sous licence MIT. Vous êtes libre de copier, modifier et distribuer ce code. Consultez le fichier LICENSE pour plus de détails.

Merci d'utiliser notre Gestionnaire de Compétitions E-Sport en Java ! Si vous avez des questions ou des suggestions, n'hésitez pas à nous contacter.






