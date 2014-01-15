Evolution Manager
=================

 
Vous pouvez déployer l'application Evolution Manager de deux façons : via Eclipse ou via un exécutable. Vous devez posséder une connection internet pour faire fonctionner l'application.
 
----
Via eclipse
------------ 
Vous devez posséder le logiciel eclipse téléchargeable [ici][1]. Choisissez le lien de téléchargement correspondant à votre système d'exploitation. Une fois le fichier téléchargé, dé-zipper le et placer le dossier **eclipse** dans le répertoire de votre choix. Dans ce dernier dossier lancer l'application eclipse.

Une fois ceci fait vous devez posséder le plug-in Google Web Toolkit pour lancer l'application. Dans la barre de menu : Help > Install New Software. Cliquez sur Add. Dans le champ de texte **Location** entrez le lien suivant : https://dl.google.com/eclipse/plugin/4.3, et donnez un nom à ce lien. Cochez toutes les options sauf **NDK plugins**. Ensuite : Next > Agree > Finish > OK. Et pour finir acceptez de redémarrer eclipse lorsqu'il vous le demande.

Vous possédez désormais tous les composants nécéssaire au bon fonctionnement d'Evolution Manager. Pour importer l'application dans eclipse : File > Import > General > Existing Projects into Workspace. Ensuite allez chercher le dossier EvolutionManager préalablement dé-zipper. 

L'application est désormais importée sous eclipse, vous allez pouvoir la lancer. Pour cela : dans la fenêtre **Project Explorer** clique droit sur **ManagerEvolution** > Run as > Web Application. Dans la fenêtre **Development Mode** en bas,  double cliquez sur l'URL une fois que celle-ci est chargée. Votre navigateur web par défaut s'ouvre alors et l'application avec. Pour changer le navigateur, au lieu de double cliquer sur l'URL, faites une clique droit et séléctionnez le navigateur de votre choix.


----------

Sur un serveur
----------


 [1]:http://www.eclipse.org/downloads/packages/eclipse-standard-431/keplersr1
