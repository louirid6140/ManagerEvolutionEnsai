package Manager.Evol.client;



import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
	private String nom_recherche;

	/**
	 * This is the entry point method.
	 */

	public void onModuleLoad() {

		final TextBox name_old = new TextBox();
		name_old.setText("Junit");

		final TextBox version_old = new TextBox();
		version_old.setText("3.8");

		final TextBox name_new = new TextBox();
		name_new.setText("Junit");

		final TextBox version_new = new TextBox();
		version_new.setText("4.9");

		final Button bouton_recherche = new Button("Lancer la recherche");


		final  CheckBox choix_google = new CheckBox("Google");
		final  CheckBox choix_github = new CheckBox("GitHub");
		final  CheckBox choix_forums = new CheckBox("Forums");
		
		final ListBox liste_deroulante = new ListBox();
		liste_deroulante.addItem("forum 1");
		liste_deroulante.addItem("forum 2");
		liste_deroulante.addItem("forum 3");


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
					liste_deroulante.setEnabled(true);
					liste_deroulante.setVisibleItemCount(2);	
				}
				else{
					liste_deroulante.setEnabled(false);
				}

			}     
		});

		// actions si on clique sur le bouton recherche
		bouton_recherche.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TabPanel panel = new TabPanel();
                if(choix_google.getValue()==true){ 
                        //Crée le nom à  chercher sur google
                        nom_recherche= "%22From%20"+name_old.getValue()+"%20"+version_old.getValue()+"%20to%20"+
                                        name_new.getValue()+"%20"+version_new.getValue()+"%22";
                        //Scrapping avec Google
                        greetingService.googleScrap(nom_recherche,
                                        new AsyncCallback<String>() {
                                        public void onFailure(Throwable caught) {
                                                // Si problème lors du scrapping réponse 
                                                nb="Problème lors de la recherche";
                                        }
                                        public void onSuccess(String result) {
                                                nb=result;
                                        }

                                });
                        panel.add(new HTML("Nombre de résultats google: "+nb), "Google");
                        
                        
                }

				if(choix_github.getValue()==true){                         
					panel.add(new HTML("A implémenter"), "GitHub");

				}

				if(choix_forums.getValue()==true){                         
					panel.add(new HTML("A implémenter"), "Forums");

				}
				// Show the 'bar' tab initially.
				panel.selectTab(0);

				// Add it to the root panel.
				RootPanel.get("panel_container").add(panel);

			}     
		});
	}
}
