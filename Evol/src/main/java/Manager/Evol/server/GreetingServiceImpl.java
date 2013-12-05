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
    	
		/*SYNTAXE DE RECH: problème|erreur|bug|migration "jUnit 4"|"jUnit4" site:fr.openclassrooms.com" */
		/* SCRAP DE FORUM */
		/* OpenClassrooms, developpez, ...*/
            try {

                    String titrePosts="oups";
                    String rech="Junit 4";
                    int page =0; // page =0 : c'est la page 1, page=10 c'est la page 2
                    /*Traitement des recherches*/
                    rech=rech.replace(" ","+");
        			rech=rech.replace(" ", "+");
        			String rech_col=rech.replace("+","");
                    /*Forum*/
                    String forum="fr.openclassrooms.com";
                    /*Mots-clés Anglais & Francais */
                    String motCles="probleme|erreur|bug|migration"; //piocher dans la base motcles
                    String keywords="bug|migration|error|problem";
        			String motCles_tuto="tutoriel";
        			String keywords_tuto="tutorial";
        			
                    int nbResultats=0,nbTuto=0,nbResolu=0;
                    /*Tutos & Problèmes résolus*/
                    StringBuffer resolu = new StringBuffer("[Résolu]");
                    StringBuffer tuto = new StringBuffer("Tutoriel");
                    StringBuffer tuto_eng = new StringBuffer("Tutorial");
                    Element test = null ;
                    boolean ok=true;

                    while(ok){
                            page+=10;
            				Document doc = Jsoup.connect("https://www.google.fr/search?client=ubuntu&channel=fs&q="+motCles+"+%22"+rech+"%22|%22"+rech_col+"%22+site:"+forum+"&start="+page).userAgent("Chrome").ignoreHttpErrors(true).timeout(0).get();		
                            Elements listPosts=doc.select("h3.r");
                            int c=0;
                            for(Element ele:listPosts){
                                    if(c==0){
                                            test=ele;
                                            if(ele.text().contains(resolu)) nbResolu++;
                                            if(ele.text().contains(tuto)) nbTuto++;
                                            nbResultats++;
                                    }
                                    c++;
                                    
                                    if(ele==test & c>1) {ok=false;}
                                    
                                    else{
                                            if(ele.text().contains(resolu)) nbResolu++;
                                            if(ele.text().contains(tuto)) nbTuto++;
                                            nbResultats++;
                                    }
                                            
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