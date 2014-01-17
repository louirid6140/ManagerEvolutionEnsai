package Manager.Evol.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Manager.Evol.client.Forums;
import Manager.Evol.client.GitHubAPI;
import Manager.Evol.client.GoogleHit;
import Manager.Evol.client.GreetingService;
import Manager.Evol.client.MotCles;
import Manager.Evol.client.Recherche;
import Manager.Evol.client.Tags;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
GreetingService {

	/**
	 * Fonction cote serveur.Retourne un nombre de resultats google a partir d'un requete.
	 * 
	 * @param recherche requete google
	 * @return  nombre de resultats google a partir d'un requete (recherche) .

	 */
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

	/**
	 * Fonction cote serveur.Retourne une chaine de caractere contenant les indicateurs trouves sur un forum
	 * (nb tuto, nb resultats, nb resolus).
	 * 
	 * @param recherche requete google
	 * @param nomForum un forum particulier
	 * @return  chaine de caractere contenant les indicateurs trouves sur le forum .

	 */
	@SuppressWarnings("null")
	public String forumScrap(Forums nomForum, String recherche){//element de la classe énumérées forum

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
			Document doc1 = Jsoup.connect("https://www.google.fr/search?q="+recherche+forum+"&start="+page).userAgent("Mozilla").ignoreHttpErrors(true).timeout(0).get();		
			Elements listPosts1=doc1.select("h3.r");
			Element test = listPosts1.first();

			boolean pageOk=true;
			int poi=0;
			while(pageOk && poi<10){
				Thread.sleep(500);
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

	/**
	 * Fonction cote serveur.Retourne les resultats de la recherche sur github.
	 * 
	 * @param fn la recherche initiale
	 * @param fv la version initiale
	 * @param tn la recherche finale
	 * @param tv la version finale
	 * @return  le code html représentant le tableau des résultats.
	 
	 */
	public  String gitHubScrap(String fn,String fv,String tn,String tv) {
		GitHubAPI git = new GitHubAPI(fn, fv, tn, tv);
				String pageFrom=null;
				String pageTo=null;
				ArrayList<int[]> fromGM_commit=new ArrayList<int[]>();
				int fromGM_bug=0;
				ArrayList<int[]> toGM_commit=new ArrayList<int[]>();
				int toGM_bug=0;
				ArrayList<String> reposF=getAllRepo(fn,1);
				ArrayList<String> reposT=getAllRepo(tn,1);
				try{
					for(String repo:reposF){
						Document docF = Jsoup.connect("https://api.github.com/repos/"+repo).userAgent("Mozilla").ignoreContentType(true).get();//.userAgent("Chrome").ignoreHttpErrors(true).timeout(0).get();		
						System.out.println("j'ai mon doc de from");
						pageFrom=docF.text();
						if(pageFrom.length()>100){
							git.addNbProjetF();
							int ind_url_commits= pageFrom.indexOf("\"commits_url\":")+15;
							int ind_url_commitsFin=pageFrom.indexOf("{/sha}\",\"git_commits_url\":");
							String urlCommit= pageFrom.substring(ind_url_commits, ind_url_commitsFin);

							int ind_url_issues= pageFrom.indexOf("\"issues_url\":")+14;
							int ind_url_issuesfin= pageFrom.indexOf("{/number}\",\"pulls_url\":");
							String urlIssues= pageFrom.substring(ind_url_issues, ind_url_issuesfin);

							fromGM_commit.add(getStats(getTableCommit(urlCommit)));
							if(hasIssues(pageFrom))fromGM_bug+=getTableBugs(urlIssues).lastKey();

							Thread.sleep(1000);
						}
					}
					for(String repo:reposT){
						//"https://api.github.com/search/code?q="+to+"+in:file+repo:"+repo
						Document docT = Jsoup.connect("https://api.github.com/repos/"+repo).userAgent("Mozilla").ignoreContentType(true).get();//.userAgent("Chrome").ignoreHttpErrors(true).timeout(0).get();		
						System.out.println("j'ai mon doc de to");
						pageTo=docT.text();
						if(pageTo.length()>100){
							git.addNbProjetT();
							int ind_url_commits= pageTo.indexOf("\"commits_url\":")+15;
							int ind_url_commitsFin=pageTo.indexOf("{/sha}\",\"git_commits_url\":");
							String urlCommit= pageTo.substring(ind_url_commits, ind_url_commitsFin);

							int ind_url_issues= pageTo.indexOf("\"issues_url\":")+14;
							int ind_url_issuesfin= pageTo.indexOf("{/number}\",\"pulls_url\":");
							String urlIssues= pageTo.substring(ind_url_issues, ind_url_issuesfin);

							toGM_commit.add(getStats(getTableCommit(urlCommit)));
							if(hasIssues(pageTo))toGM_bug+=getTableBugs(urlIssues).lastKey();
						}
					}
				}
				catch(IOException e){
					//return("pb connection");
					System.out.println("IOException : "+e);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				return affichage(fromGM_commit,fromGM_bug,toGM_commit,toGM_bug);
	}
	
	/**
	 * Retourne la liste des répertoire github répondant à la recherche github.
	 * @param rech la recherche de selection de repertoire github
	 * @param nbRepo le nombre maximale de répertoires attendus
	 * @return la liste des répertoires github répondant à la recherche.
	 
	 */
	public ArrayList<String> getAllRepo(String rech, int nbRepo){
		ArrayList<String> AllRepo=new ArrayList<String>();
		String page=null;
		try{
			Document doc = Jsoup.connect("https://api.github.com/search/repositories?q="+rech).userAgent("Mozilla").ignoreContentType(true).get();//.userAgent("Chrome").ignoreHttpErrors(true).timeout(0).get();		
			System.out.println("j'ai mes repo");
			page=doc.text();
		}
		catch(IOException e){
			//return("pb connection");
			System.out.println("IOException : "+e);
		}
		String repo;
		int ind_nameRepo=page.indexOf("\"full_name\":\"");
		int ind_end;
		int c=0;
		while(ind_nameRepo!=-1 & c<nbRepo){
			c++;
			repo=page.substring(ind_nameRepo+13);
			ind_end=repo.indexOf("\",");
			repo=repo.substring(0, ind_end);
			page=page.substring(ind_nameRepo+13);
			System.out.println(repo);
			ind_nameRepo=page.indexOf("\"full_name\":\"");
			AllRepo.add(repo);
		}
		return AllRepo;
	}
	
	/**
	 * Retourne le tableau de statistiques globales sur les commits. 
	 * @param tab le dictionnaire des statistiques sur pour tous les commits
	 * @return un tableau de statistiques globales sur les commits du dictionnaire.
	 
	 */
	public int[] getStats(HashMap<String,int[]> tab){
		int totG=0,addG=0,delG=0,totM,addM,delM;
		int[] res=new int[7];
		if(tab!=null){
			Collection<int[]> tabval=tab.values();
			for(int[] val:tabval){
				totG+=val[0];
				addG+=val[1];
				delG+=val[2];
			}
			int nb=tabval.size();
			totM=totG/nb;
			addM=addG/nb;
			delM=delG/nb;
			//alors on restitue dans l'ordre: nb, totG, addG, delG, totM, addM, delM
			res[0]=nb;
			res[1]=totG;
			res[2]=addG;
			res[3]=delG;
			res[4]=totM;
			res[5]=addM;
			res[6]=delM;
		}else{
			res[0]=0;
			res[1]=0;
			res[2]=0;
			res[3]=0;
			res[4]=0;
			res[5]=0;
			res[6]=0;
		}
		return res;
	}
	
	/**
	 * Retourne le code html qui affichera un tableau de résultats globaux cette recherche
	 * @param fgmc la liste des tableaux de résultats des commits pour chaque répertoire initial
	 * @param fgmb le nombre de bugs total pour tous les repertoires initiaux 
	 * @param tgmc la liste des tableaux de résultats des commits pour chaque répertoire final
	 * @param tgmb le nombre de bugs total pour tous les repertoires finaux 
	 * @return le code html en String
	 */
	public String affichage(ArrayList<int[]> fgmc,int fgmb,ArrayList<int[]> tgmc,int tgmb){
		int totalF=0,addF=0,delF=0;
		for(int[] tab:fgmc){
			totalF+=tab[1];
			addF+=tab[2];
			delF+=tab[3];
		}
		int totalG=0,addG=0,delG=0;
		for(int[] tab:tgmc){
			totalG+=tab[1];
			addG+=tab[2];
			delG+=tab[3];
		}

		String code_html = "<TABLE BORDER=\"1\" CELLSPACING=\"0\" CELLPADDING=\"8\">"+
				"<CAPTION><h3>Global Commit<h3></CAPTION>"+
				"<TR><TH></TH><TH>FROM</TH><TH>TO</TH> </TR>"+
				"<TR><TD>Nb Projet</TD><TD>"+ fgmc.get(0)[0]+" </TD><TD> "+tgmc.get(0)[0]+" </TD></TR>"+
				"<TR><TD>Total</TD><TD> "+totalF+" </TD><TD> "+totalG+" </TD></TR>"+
				"<TR><TD>Add</TD><TD> "+addF+" </TD><TD>"+addG+"  </TD></TR>"+
				"<TR><TD>Del</TD><TD> "+delF+" </TD><TD>"+delG+"  </TD></TR>"+
				"<TR><TD>Nb Bug</TD><TD> "+fgmb+" </TD><TD>"+tgmb+"  </TD></TR>"+
				"</TABLE>";
		return code_html;
	}
	
	/**
	 * Retourne les informations statistiques sur des commits d'un répertoire 
	 * @param urlCommit l'url github API des commits
	 * @return un dictionnaire contenant la table stat des commits
	 */
	public HashMap<String,int[]> getTableCommit(String urlCommit){
		HashMap<String,int[]> tableCommit=new HashMap<String,int[]>();
		String page=null;
		System.out.println("before try");
		try{
			Document doc = Jsoup.connect(urlCommit).userAgent("Mozilla").ignoreContentType(true).get();//.userAgent("Chrome").ignoreHttpErrors(true).timeout(0).get();		
			//Elements listPosts=doc.select("h3.r");
			System.out.println("j'ai mon doc");
			page=doc.text();
		}
		catch(IOException e){
			//return("pb connection");
			System.out.println("IOException : "+e);
		}

		if(page==null)return null;
		int end=page.length();
		if(page.length()>200){//au cas où on tombe sur la page error
			int start=9;
			int c=0;
			while(start<end-10 & c<5){
				c++;
				String pagec=null;
				String sha=page.substring(start, page.indexOf(",")-1);
				System.out.println(sha);
				try{
					Document doc = Jsoup.connect(urlCommit+"/"+sha).userAgent("Mozilla").ignoreContentType(true).get();
					pagec=doc.text();
				}
				catch(IOException e){
					//return("pb connection");
					System.out.println("IOException : "+e);
				}
				int ind_date= pagec.indexOf("date");
				int ind_tot= pagec.indexOf("\"total\"");
				int ind_add= pagec.indexOf("\"additions\"");
				int ind_del= pagec.indexOf("\"deletions\"");

				String date=pagec.substring(ind_date+7, ind_date+7+10);
				System.out.println(date);

				String total=pagec.substring(ind_tot+8, ind_add-1);
				System.out.println(total);
				String addition=pagec.substring(ind_add+12, ind_del-1);
				System.out.println(addition);
				String deletion=pagec.substring(ind_del+12, pagec.indexOf("},\"files\":["));
				System.out.println(deletion);

				//Date key = new Date(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(8)));

				int[] value=new int[3];
				value[0]=Integer.parseInt(total);
				value[1]=Integer.parseInt(addition);
				value[2]=Integer.parseInt(deletion);

				tableCommit.put(date+sha, value);

				if(page.indexOf(GitHubAPI.DEBUTCOMMIT)!=-1){
					start=page.indexOf(GitHubAPI.DEBUTCOMMIT)+11;
					page=page.substring(start);
					start=0;
					end=page.length();
				}else{
					start=end;
				}
			}
		}
		return tableCommit;
	}

	/**
	 * Retourne une liste des bugs trouvés à partir de l'url
	 * @param urlIssues l'url github API de la signalisation de bugs ou problèmes
	 * @return la liste ordonnée de ces evenements avec leur date
	 */
	public TreeMap<Integer,Date> getTableBugs(String urlIssues){
		TreeMap<Integer,Date> tableBug=new TreeMap<Integer,Date>();
		String page=null;
		System.out.println("before try");
		try{
			Document doc = Jsoup.connect(urlIssues).userAgent("Mozilla").ignoreContentType(true).get();//.userAgent("Chrome").ignoreHttpErrors(true).timeout(0).get();		
			System.out.println("j'ai mon doc");
			page=doc.text();
		}
		catch(IOException e){
			//return("pb connection");
			System.out.println("IOException : "+e);
		}

		if(page==null)return null;
		int end=page.length();
		if(page.length()>200){//au cas où on tombe sur la page error
			int start=page.indexOf(GitHubAPI.NUMEROBUG);
			while(start<(end-10)){
				page=page.substring(start+3);
				String numero=page.substring(6, page.indexOf(","));
				System.out.println(numero);
				int ind_date= page.indexOf("\"created_at\":");

				String date=page.substring(ind_date+14, ind_date+14+10);
				System.out.println(date);

				Date value = new Date(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(8)));

				Integer key= Integer.parseInt(numero);

				tableBug.put(key, value);

				start=0;
				end=page.length();

				start=page.indexOf(GitHubAPI.NUMEROBUG);
				if(start==-1)start=end;

			}
		}
		return tableBug;
	}

	/**
	 * À partir de la page (String) du répertoire, retourne un boolean indiquant la présence de bugs
	 * @param page  obtenue via github API
	 * @return boolean true si il y a des bugs, false sinon
	 */
	public boolean hasIssues(String page){
		int deb= page.indexOf("\"has_issues\":")+14;
		int fin= deb+4;
		System.out.println(deb+" "+fin+" "+page.length());
		String boo=page.substring(deb, fin);
		if(boo.equals("true")){
			return true;
		}else{
			return false;
		}
	}
}