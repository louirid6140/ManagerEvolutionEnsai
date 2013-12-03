package Manager.Evol.server;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Manager.Evol.client.GreetingService;

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
			Document doc = Jsoup.connect("https://www.google.fr/search?q="+recherche).userAgent("Mozilla").ignoreHttpErrors(true).timeout(0).get();
			Element nbResultats=doc.select("div#resultStats").first();
			String nbRes=nbResultats.text().replace("\u00a0","").replaceAll("[^\\d.]", ""); //le nb de rÃ©sultats google, le 2e replace garde que les caractÃ¨res de 0 Ã  9 d'une chaÃ®ne String
			return(nbRes);
		}

		catch(IOException e){
			return("pb connection");
		}
	}

	public String ForumScrapOpenClassrooms(String recherche){//element de la classe énumérées forum
		try {
			String titrePosts="oups";
			String rech="Junit 4";
			int page =0;
			//    		rech=rech.replace(" ","+");
			String forum="fr.openclassrooms.com";
			String motCles="probleme|erreur|bug|migration";

			int nbResultats=0,nbTuto=0,nbResolu=0;
			StringBuffer resolu = new StringBuffer("[Résolu]");
			StringBuffer tuto = new StringBuffer("Tutor");
			Element test = null ;
			boolean ok=true;

			for(page=0;page<30;page+=10){
				page+=10;
				Document doc = Jsoup.connect("https://www.google.fr/search?client=ubuntu&channel=fs&q="+motCles+"+%22jUnit+4%22|%22jUnit4%22+site:"+forum+"&start="+page).userAgent("Chrome").ignoreHttpErrors(true).timeout(0).get();
				Elements listPosts=doc.select("h3.r");
				int c=0;
				for(Element ele:listPosts){
//					if(c==0){
//						test=ele;
//						if(ele.text().contains(resolu)) nbResolu++;
//						if(ele.text().contains(tuto)) nbTuto++;
//						nbResultats++;
//					}
//					c++;
//					if(ele==test & c>1){ ok=false;}else{
						if(ele.text().contains(resolu)) nbResolu++;
						if(ele.text().contains(tuto)) nbTuto++;
						nbResultats++;
//					}
						
				}
				Thread.sleep(3000);
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
