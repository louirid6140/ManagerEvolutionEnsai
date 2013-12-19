package Manager.Evol.server;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Manager.Evol.client.Forums;
import Manager.Evol.client.GreetingService;
import Manager.Evol.client.Tags;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
GreetingService {

	public  String googleScrap(String recherche) {
		try {

			// On se connecte au site et on ch6arge le document html
			Document doc = Jsoup.connect("https://www.google.fr/search?q="+recherche).userAgent("Mozilla").ignoreHttpErrors(true).timeout(0).get();			Element nbResultats=doc.select("div#resultStats").first();
			String nbRes=nbResultats.text().replace("\u00a0","").replaceAll("[^\\d.]", ""); //le nb de rÃ©sultats google, le 2e replace garde que les caractÃ¨res de 0 Ã  9 d'une chaÃ®ne String
			return(nbRes);
		}

		catch(IOException e){
			return("pb connection");
		}
	}

	@SuppressWarnings("null")
	public String ForumScrapOpenClassrooms(Forums nomForum, String recherche){//element de la classe énumérées forum

		/*SYNTAXE DE RECH: problème|erreur|bug|migration "jUnit 4"|"jUnit4" site:fr.openclassrooms.com" */
		/* SCRAP DE FORUM */
		/* OpenClassrooms, developpez, ...*/
		try {

			int page =0; // page =0 : c'est la page 1, page=10 c'est la page 2
			/*Traitement des recherches*/
			/*Forum*/
			String forum=nomForum.toString();
			/*Mots-clés Anglais & Francais */ //piocher dans la base motcles

			int nbResultats=0,nbTuto=0,nbResolu=0;
			/*Tutos & Problèmes résolus*/
			StringBuffer resolu = new StringBuffer("[Résolu]");
			StringBuffer tuto = new StringBuffer("Tutoriel");
			System.out.println("https://www.google.fr/search?q="+recherche+forum+"&start="+page);
			Document doc1 = Jsoup.connect("https://www.google.fr/search?q="+recherche+forum+"&start="+page).userAgent("Mozilla").ignoreHttpErrors(true).timeout(0).get();		
			Elements listPosts1=doc1.select("h3.r");
			Element test = listPosts1.first();
			
			boolean pageOk=true;
			int poi=0;
			while(pageOk && poi<10){
				Thread.sleep(3000);
				int c=0;
				Document doc = Jsoup.connect("https://www.google.fr/search?q="+recherche+forum+"&start="+page).userAgent("Mozilla").ignoreHttpErrors(true).timeout(0).get();		
				Elements listPosts=doc.select("h3.r");
				for(Element ele:listPosts){
					if(poi>0 & (test.text()).equals(ele.text())){
						pageOk=false;
					}else{
						if(c==0){
							test=ele;
						}
						if(pageOk){
						if(ele.text().contains(Tags.resolu.toString())) nbResolu++;
						if(ele.text().contains(Tags.tutoriel.toString())) nbTuto++;
						nbResultats++;
						}
					}
					c++;
				}
				poi++;
				page+=10;
			}

			return("nbRésultats : "+nbResultats+"\n<br>nbResolu : "+nbResolu+"\n<br>nbtuto : "+nbTuto);

		}
		catch(IOException e){
			return("pb connection");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			return(""+e);
		}
	}

}