Evolution Manager
=================

 
Vous pouvez déployer l'application Evolution Manager de deux façons : via Eclipse ou via un serveur Glassfish. Vous devez posséder une connection internet pour faire fonctionner l'application.
 
----
Via eclipse
------------ 
Vous devez posséder le logiciel eclipse téléchargeable [ici][1]. Choisissez le lien de téléchargement correspondant à votre système d'exploitation. Une fois le fichier téléchargé, dé-zipper le et placer le dossier **eclipse** dans le répertoire de votre choix. Dans ce dernier dossier lancer l'application eclipse.

Une fois ceci fait vous devez posséder le plug-in Google Web Toolkit pour lancer l'application. Dans la barre de menu : Help > Install New Software. Cliquez sur Add. Dans le champ de texte **Location** entrez le lien suivant : https://dl.google.com/eclipse/plugin/4.3, et donnez un nom à ce lien. Cochez toutes les options sauf **NDK plugins**. Ensuite : Next > Agree > Finish > OK. Et pour finir acceptez de redémarrer eclipse lorsqu'il vous le demande.

Vous possédez désormais tous les composants nécéssaire au bon fonctionnement d'Evolution Manager. Pour importer l'application dans eclipse : File > Import > General > Existing Projects into Workspace. Ensuite allez chercher le dossier EvolutionManager préalablement dé-zipper. 

L'application est désormais importée sous eclipse, vous allez pouvoir la lancer. Pour cela : dans la fenêtre **Project Explorer** clique droit sur **ManagerEvolution** > Run as > Web Application. Dans la fenêtre **Development Mode** en bas,  double cliquez sur l'URL une fois que celle-ci est chargée. Votre navigateur web par défaut s'ouvre alors et l'application avec. Pour changer le navigateur, au lieu de double cliquer sur l'URL, faites une clique droit et séléctionnez le navigateur de votre choix.


----------

Sur un serveur Glassfish
---------
Vous pouvez également lancer l'application sans passer par eclipse, mais en la déployant sur un serveur Glassfish. Pour cela il vous faut télécharger Glassfish [ici][2]. Décompresser le fichier **glassfish-4.0.zip** dans le dossier de votre choix. Vous devez ensuite lancez le serveur via l'invite de commande (rechercher "cmd" dans windows). Accédez à votre dossier **glassfish4** (pour voir les fichiers et les documents du répertoire courant, tapez la commande **dir** et pour accédez à un dossier tapez **cd +** *nom du dossier*). Une fois dans ce répertoire, accédez au repertoire **bin** puis tapez la commande suivante **asadmin start-domain**. Votre serveur est désormais lancé, vous pouvez accéder au portail d'administration via un browser à l'adresse **localhost:4848**. Il faut désormais déployer l'application sur le serveur. Cliquez sur **Application** puis sur **Deploy**. Cliquez ensuite sur **Choisissez un fichier** et sélectionnez le fichier *EvolutionManager.war* puis cliquez sur **OK**. L'application est désormais déployée, cliquez sur **Launch** puis sur le premier des deux liens qui vous sont proposés (le deuxième fonctionne également). Vous avez désormais accès à l'application.
Pour eteindre le serveur Glassfish, entrez simplement **asadmin stop-domain** dans la console.


  [1]:http://www.eclipse.org/downloads/packages/eclipse-standard-431/keplersr1
  [2]: https://glassfish.java.net/download.html
