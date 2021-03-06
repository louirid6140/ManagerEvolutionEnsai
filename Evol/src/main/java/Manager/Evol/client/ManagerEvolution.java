package Manager.Evol.client;



import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ManagerEvolution implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private String nb;
	private String fo;
	private String gi;
	private String nom_recherche;
	private String message_forum="";
	private int nb_forum=0;

	/**
	 * This is the entry point method.
	 */

	public void onModuleLoad() {
		//initialisation par défault
		final TextBox name_old = new TextBox();
		name_old.setText("Junit");
		final TextBox version_old = new TextBox();
		version_old.setText("3");
		final TextBox name_new = new TextBox();
		name_new.setText("Junit");
		final TextBox version_new = new TextBox();
		version_new.setText("4");

		final Button bouton_recherche = new Button("Lancer la recherche");

		//Différents choix de recherche
		final  CheckBox choix_google = new CheckBox("Google");
		final  CheckBox choix_github = new CheckBox("GitHub API");
		final  CheckBox choix_forums = new CheckBox("Forums");

		//Ajout de tous les forums connus
		final ListBox liste_deroulante = new ListBox(true);
		for (Forums forums:Forums.values()) {
			liste_deroulante.addItem(forums.toString());
			nb_forum++;
		}


		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("name_old_container").add(name_old);
		RootPanel.get("name_new_container").add(name_new);
		RootPanel.get("version_old_container").add(version_old);
		RootPanel.get("version_new_container").add(version_new);

		RootPanel.get("bouton_recherche_container").add(bouton_recherche);

		RootPanel.get("choix_google_container").add(choix_google);
		RootPanel.get("choix_github_container").add(choix_github);
		RootPanel.get("choix_forums_container").add(choix_forums);


		// Focus the cursor on the name field when the app loads
		name_old.setFocus(true);
		name_old.selectAll();


		// Action si on clique sur le la checkbox github
		choix_forums.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("liste_deroulante_container").add(liste_deroulante);
				if(choix_forums.getValue()==true){
					liste_deroulante.setVisible(true);
					liste_deroulante.setVisibleItemCount(3);
					Window.alert("Sélectionnez les forums que vous souhaitez utiliser pour la recherche (Utilisez la commande ctrl)");
				}
				else{
					liste_deroulante.setVisible(false);
				}

			}     
		});


		// actions si on clique sur le bouton recherche
		bouton_recherche.addClickHandler(new ClickHandler() {
			TabPanel panel = new TabPanel();
			public void onClick(ClickEvent event) {
				message_forum="";
				panel.clear();
				
				if(choix_google.getValue()==true) googleOnglet(name_old,version_old,name_new,version_new,panel);
				if(choix_github.getValue()==true) gitHubAPIOnglet(name_old, version_old, name_new, version_new, panel);
				if(choix_forums.getValue()==true) forumOnglet(name_old, version_old, name_new, version_new, panel, liste_deroulante);
				
				// Add it to the root panel.
				Window.alert("Veuillez cliquez sur 'ok' pour afficher les résultats");
				RootPanel.get("panel_container").add(panel);

			}     
		});
	}
	
	/**
	 * Implemente l'onglet de la recherche en utilisant exclusivement les résultats google
	 * @param name_old le nom de l'ancien artefact
	 * @param version_old la version de l'ancien artefact
	 * @param name_new le nom du nouvel artefact 
	 * @param version_new la version du nouvel artefact
	 * @param panel le panel dans lequel je mets l'onglet
	 */
	public void googleOnglet(TextBox name_old,TextBox version_old,TextBox name_new,TextBox version_new, final TabPanel  panel){
	
			DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "wait");
			GoogleHit googleHit=new GoogleHit(name_old.getValue(),version_old.getValue(),name_new.getValue(),version_new.getValue());
			//Scrapping avec Google
			greetingService.googleScrap(googleHit.creationRequeteGoogle(),
					new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					// Si problème lors du scrapping réponse 
					nb="Problème lors de la recherche";
				}
				public void onSuccess(String result) {
					//permet d'attendre que le scrap à partir de google soit ok
					Scheduler.get().scheduleEntry(new ScheduledCommand() {
						public void execute() {
							panel.add(new HTML("Nombre de résultats google: "+nb), "Google");
							DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
						}
					});
					nb=result;
				}

			});
	}
	
	/**
	 * Implemente l'onglet de la recherche utilisant gitHUBAPI
	 * @param name_old le nom de l'ancien artefact
	 * @param version_old la version de l'ancien artefact
	 * @param name_new le nom du nouvel artefact 
	 * @param version_new la version du nouvel artefact
	 * @param panel le panel dans lequel je mets l'onglet
	 */
	public void gitHubAPIOnglet(TextBox name_old,TextBox version_old,TextBox name_new,TextBox version_new, final TabPanel  panel){
		
		DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "wait");
		GitHubAPI git=new GitHubAPI(name_old.getValue(),version_old.getValue(),name_new.getValue(),version_new.getValue());
		//Scrapping avec GitHub
		greetingService.gitHubScrap(git.getFromNom(),git.getFromVersion(),git.getToNom(),git.getToVersion(),
				new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// Si problème lors du scrapping réponse 
				gi="Problème lors de la recherche";
			}
			public void onSuccess(String result) {
				//permet d'attendre que le scrap à partir de github soit ok
				Scheduler.get().scheduleEntry(new ScheduledCommand() {
					public void execute() {
						panel.add(new HTML(gi), "GitHub");
						DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
					}
				});
				gi=result;
			}

		});

	}
	
	/**
	 * Implemente l'onglet de la recherche en utilisant les forums selectionnés
	 * @param name_old le nom de l'ancien artefact
	 * @param version_old la version de l'ancien artefact
	 * @param name_new le nom du nouvel artefact 
	 * @param version_new la version du nouvel artefact
	 * @param panel le panel dans lequel je mets l'onglet
	 * @param liste_deroulante la liste des forums
	 */
	public void forumOnglet(TextBox name_old,TextBox version_old,TextBox name_new,TextBox version_new, final TabPanel  panel,final ListBox liste_deroulante){
		DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "wait");
		Forum forum=new Forum(name_old.getValue(),version_old.getValue(),name_new.getValue(),version_new.getValue());
		int i=0;
		for (final Forums foru:Forums.values()) {
			if(liste_deroulante.isItemSelected(i)==true){
				greetingService.forumScrap(foru,forum.creationRequeteForums(),
						new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Si problème lors du scrapping réponse 
						fo="Problème lors de la recherche";
					}
					public void onSuccess(String result) {	
						message_forum+="Pour le forum "+foru.toString()+" : \n ";
						//permet d'attendre que le scrap à partir de google soit ok
						Scheduler.get().scheduleEntry(new ScheduledCommand() {
							public void execute() {
								message_forum+=fo+"\n";
								panel.add(new HTML(message_forum), "Forums");
								message_forum="";
								DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
							}
						});
						fo=result;

					}
				});
			}
			i++;
		}
	}
}