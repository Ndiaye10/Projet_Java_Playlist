package VLC;

//Hola

//https://www.geeksforgeeks.org/play-audio-file-using-java/

//Programme java pour lire un audio
//Le fichier utilise l'objet Clip

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//Le sampled me fournie une "interface" pour les lectures audios et leurs traitements
//AudioInputStream : format et longeur audio spécifés. Grace à son lecteur d'échantillons de trame pas en octets et
//assicier à l'interface sampled, on pourra choisir d'avancer la lecture et choisir d'arriver
//à une position ultérieur pour lire l'audio
import javax.sound.sampled.AudioInputStream;

//Me servira de point d'entrée aux sources de system d'audio échantillonné
//Ca va me faciliter également la mise en place de mon interface "sampled"
import javax.sound.sampled.AudioSystem;

//Les données audios sont chargées avant la lecture. Etant donné kil sont préchargées et que l'on connait leur longeur,
//on peut définir le démarrage du clip à " n'importe qu'elle position dans ses données audio ".
//Possibilité de créer une loop
import javax.sound.sampled.Clip;

//Soulève une exception du fait que la ligne ne peut etre ouverte car elle n'est pas disponible.
//Situation qui survient lorsque les lignes sont déjà utilisées par d'autres appli
import javax.sound.sampled.LineUnavailableException;

//Exception levée parce que le fichier ne contient pas des données valides d'un type de fichier
// ou d'un format reconnu
import javax.sound.sampled.UnsupportedAudioFileException;

import org.omg.CORBA.PRIVATE_MEMBER;

public class LecteurMusique {
	
	//Pour stocker la position actuelle
	//L'objet Long contient des types long et nous permettra de faire des
	//conversions en String facilement. Le long aussi me permet de récupérer
	//cette information à la forme d'une durée qui sera déterminer par la position
	long positionActuelleDuClip;
	
	//Cette proprieté représentera notre média en lui meme
	//Aussi, il est de type Clip car il préchargera les données audios
	//C'est ma plateforme également car c'est sur lui qu'agiront les 
	//méthodes start(), stop() etc.
	Clip clip;
	
	//Le Statut actuelle du médias (En terme d'avancement. ex: en cours de lecture, finit, débute, pause)
	//On le mettra en String pour nous donner une indication d'information sur le statut actuel
	String statutActuelleDuClip;
	
	//AudioInputStream a un format de longeur audio spécifique et permet meme de
	//choisir une position de lecture
	//Il représente le flux émis, le sons en terme de flux.
	AudioInputStream fluxDEntreeAudio;
	
	static String cheminDuFichier;
	
	//Constructeur pour initialiser les flux entrant avec leurs caratéristiques
	//qui sont le fait de pouvoir couper le sons, 
	public LecteurMusique ()
		throws LineUnavailableException,
		IOException, UnsupportedAudioFileException
	{
		//Création de l'objet AudioInputStream (qui représente mon sons en terme de flux entrant)
		//Il va obtenir un flux d'entrée audio à partir du fichier fourni, donc obtenir le fichier de sons.
		//Le fichier doit pointer vers des données de fichier audio valides.
		//On se souvient également que AudioSystem récupère les flux entrant et audioInputStream en est un"
		
		fluxDEntreeAudio = AudioSystem.getAudioInputStream(new File(cheminDuFichier).getAbsoluteFile());
		
		//getAbsoluteFile() renverra le chemin d'accès "abstrait" du fichier récupérer
		
		//Création d'une référence de Clip(renvoie à une plateforme de lecture)
		//Cet objet obtient un clip(ou une disque, une plateforme) qui peut
		//etre utilisé pour lire un fichier audio ou un flux audio à l'image de
		//notre "fluxDEntreeAudio". Le clip retourner doit etre ouvert avec une
		//méthode (AudioFormat) ou (AudioInputStream)
		
		clip = AudioSystem.getClip();
		
		//Ouvrir le fluxDEntreeAudio dans le clip
		clip.open(fluxDEntreeAudio);
		
		//Démarre la lecture en boucle à partir de la position actuelle
		//La lecture continue jusqu'au point de fin de la boucle
		//puis revient en boucle.
		//Le second parametre indiquera que le bouclage doit se 
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
	}
		
		private void faireChoix(int c)
				throws IOException, LineUnavailableException, UnsupportedAudioFileException
		{
			switch (c) 
			{
				case 1:
					pause();
					break;
				case 2:
					infoLectureAudio();
					break;
				case 3:
					restart();
					break;
				case 4:
					stop();
					break;
				case 5:
					System.out.println( "Entrez la durée (" + 0 +
							", " + clip.getMicrosecondLength()//obtien la durée en microsecondes
							+ ")" );
					//Pour saisir notre durée
					Scanner sc = new Scanner(System.in);
					long c1 = sc.nextLong();//Va analyser le choix suivant en tant que long
					jump(c1);
					break;
			}
		}
		
		//Méthode pour jouer le sons
		public void play()
		{
			//démarrer le clip
			clip.start();
			
			statutActuelleDuClip = "play (Lecture)";
		}
		
		//Méthode pour mettre le sons en pause
		public void pause()
		{
			if(statutActuelleDuClip.equals("pause"))
			{
				System.out.println("La lecture du clip est en pause");
				return;//return ce msg
			}
			//Si on met la lecture en pause, getMicrosecondePosition()
			//nous donnera la position de lecture en temps de secondes
			this.positionActuelleDuClip =
					this.clip.getMicrosecondPosition();
			clip.stop();
			statutActuelleDuClip = "En Pause";
		}
		
		//Méthode qui donnera une information sur la lecture du clip
		//(s'il a déjà été joué par exemple)
		public void infoLectureAudio()
			throws UnsupportedAudioFileException,
			IOException, LineUnavailableException
		{
			if(statutActuelleDuClip.equals("Play"))
			{
				System.out.println("L'audio a déjà été joué");
				return;
			}
			clip.close();//On va fermer la ligne de diffusion de ressouces système
			reinitialiserFluxAudio();//Cette méthode reprendra un nouveau flux de lecture
			positionActuelleDuClip = 0L;
			clip.setMicrosecondPosition(0);//Vu que le clip démarrera avec un nouveau flux
			//on a actualisé directement la position au temps 0 donc au début
			this.play();//On appelle la méthode play() pour jouer le clip à nouveau
			
		}
		
		//Méthode pour redemarrer l'audio
		public void restart()
			throws UnsupportedAudioFileException,
			IOException, LineUnavailableException
		{
			//On peut redémarrer à n'importe qu'elle moment
			//donc ça implique premièrement un arrêt brusque
			clip.stop();
			clip.close();//On coupe ensuite la ligne de diffusion
			reinitialiserFluxAudio();//Afin que ça prenne un nouveau flux de diffusion
			positionActuelleDuClip = 0L;
			clip.setMicrosecondPosition(0);
			this.play();//Relecture.
		}
	
}
