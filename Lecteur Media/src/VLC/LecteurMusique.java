package VLC;

//https://www.geeksforgeeks.org/play-audio-file-using-java/

//Programme java pour lire un audio
//Le fichier utilise l'objet Clip

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//Le sampled me fournie une "interface" pour les lectures audios et leurs traitements
//AudioInputStream : format et longeur audio sp�cif�s. Grace � son lecteur d'�chantillons de trame pas en octets et
//assicier � l'interface sampled, on pourra choisir d'avancer la lecture et choisir d'arriver
//� une position ult�rieur pour lire l'audio
import javax.sound.sampled.AudioInputStream;

//Me servira de point d'entr�e aux sources de system d'audio �chantillonn�
//Ca va me faciliter �galement la mise en place de mon interface "sampled"
import javax.sound.sampled.AudioSystem;

//Les donn�es audios sont charg�es avant la lecture. Etant donn� kil sont pr�charg�es et que l'on connait leur longeur,
//on peut d�finir le d�marrage du clip � " n'importe qu'elle position dans ses donn�es audio ".
//Possibilit� de cr�er une loop
import javax.sound.sampled.Clip;

//Soul�ve une exception du fait que la ligne ne peut etre ouverte car elle n'est pas disponible.
//Situation qui survient lorsque les lignes sont d�j� utilis�es par d'autres appli
import javax.sound.sampled.LineUnavailableException;

//Exception lev�e parce que le fichier ne contient pas des donn�es valides d'un type de fichier
// ou d'un format reconnu
import javax.sound.sampled.UnsupportedAudioFileException;

import org.omg.CORBA.PRIVATE_MEMBER;

public class LecteurMusique {
	
	//Pour stocker la position actuelle
	//L'objet Long contient des types long et nous permettra de faire des
	//conversions en String facilement. Le long aussi me permet de r�cup�rer
	//cette information � la forme d'une dur�e qui sera d�terminer par la position
	long positionActuelleDuClip;
	
	//Cette propriet� repr�sentera notre m�dia en lui meme
	//Aussi, il est de type Clip car il pr�chargera les donn�es audios
	//C'est ma plateforme �galement car c'est sur lui qu'agiront les 
	//m�thodes start(), stop() etc.
	Clip clip;
	
	//Le Statut actuelle du m�dias (En terme d'avancement. ex: en cours de lecture, finit, d�bute, pause)
	//On le mettra en String pour nous donner une indication d'information sur le statut actuel
	String statutActuelleDuClip;
	
	//AudioInputStream a un format de longeur audio sp�cifique et permet meme de
	//choisir une position de lecture
	//Il repr�sente le flux �mis, le sons en terme de flux.
	AudioInputStream fluxDEntreeAudio;
	
	static String cheminDuFichier;
	
	//Constructeur pour initialiser les flux entrant avec leurs carat�ristiques
	//qui sont le fait de pouvoir couper le sons, 
	public LecteurMusique ()
		throws LineUnavailableException,
		IOException, UnsupportedAudioFileException
	{
		//Cr�ation de l'objet AudioInputStream (qui repr�sente mon sons en terme de flux entrant)
		//Il va obtenir un flux d'entr�e audio � partir du fichier fourni, donc obtenir le fichier de sons.
		//Le fichier doit pointer vers des donn�es de fichier audio valides.
		//On se souvient �galement que AudioSystem r�cup�re les flux entrant et audioInputStream en est un"
		
		fluxDEntreeAudio = AudioSystem.getAudioInputStream(new File(cheminDuFichier).getAbsoluteFile());
		
		//getAbsoluteFile() renverra le chemin d'acc�s "abstrait" du fichier r�cup�rer
		
		//Cr�ation d'une r�f�rence de Clip(renvoie � une plateforme de lecture)
		//Cet objet obtient un clip(ou une disque, une plateforme) qui peut
		//etre utilis� pour lire un fichier audio ou un flux audio � l'image de
		//notre "fluxDEntreeAudio". Le clip retourner doit etre ouvert avec une
		//m�thode (AudioFormat) ou (AudioInputStream)
		
		clip = AudioSystem.getClip();
		
		//Ouvrir le fluxDEntreeAudio dans le clip
		clip.open(fluxDEntreeAudio);
		
		//D�marre la lecture en boucle � partir de la position actuelle
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
					System.out.println( "Entrez la dur�e (" + 0 +
							", " + clip.getMicrosecondLength()//obtien la dur�e en microsecondes
							+ ")" );
					//Pour saisir notre dur�e
					Scanner sc = new Scanner(System.in);
					long c1 = sc.nextLong();//Va analyser le choix suivant en tant que long
					jump(c1);
					break;
			}
		}
		
		//M�thode pour jouer le sons
		public void play()
		{
			//d�marrer le clip
			clip.start();
			
			statutActuelleDuClip = "play (Lecture)";
		}
		
		//M�thode pour mettre le sons en pause
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
		
		//M�thode qui donnera une information sur la lecture du clip
		//(s'il a d�j� �t� jou� par exemple)
		public void infoLectureAudio()
			throws UnsupportedAudioFileException,
			IOException, LineUnavailableException
		{
			if(statutActuelleDuClip.equals("Play"))
			{
				System.out.println("L'audio a d�j� �t� jou�");
				return;
			}
			clip.close();//On va fermer la ligne de diffusion de ressouces syst�me
			reinitialiserFluxAudio();//Cette m�thode reprendra un nouveau flux de lecture
			positionActuelleDuClip = 0L;
			clip.setMicrosecondPosition(0);//Vu que le clip d�marrera avec un nouveau flux
			//on a actualis� directement la position au temps 0 donc au d�but
			this.play();//On appelle la m�thode play() pour jouer le clip � nouveau
			
		}
		
		//M�thode pour redemarrer l'audio
		public void restart()
			throws UnsupportedAudioFileException,
			IOException, LineUnavailableException
		{
			//On peut red�marrer � n'importe qu'elle moment
			//donc �a implique premi�rement un arr�t brusque
			clip.stop();
			clip.close();//On coupe ensuite la ligne de diffusion
			reinitialiserFluxAudio();//Afin que �a prenne un nouveau flux de diffusion
			positionActuelleDuClip = 0L;
			clip.setMicrosecondPosition(0);
			this.play();//Relecture.
		}
		
		//M�thode stop() pour arr�ter la lecture du clip
		public void stop()
			throws IOException, LineUnavailableException, UnsupportedAudioFileException
		{
			positionActuelleDuClip = clip.getMicrosecondPosition();
			clip.stop();
			clip.close();
			statutActuelleDuClip = "Lecture stopp�e";
		}
		
		//M�thode pour pour sauter vers une partie sp�cifique de lecture
		public void jump(long c)//fonctionne grace � la donner qui sera saisie au clavier
		//pour nous indiquer la partie sur laquelle on aimerait faire le saut
			throws IOException, LineUnavailableException, UnsupportedAudioFileException
		{
			//Si le temps voulu est sup�rieur � z�ro et ce meme
			//temps est dans la marge maximale, alors on va vers ce temps.
			if(c > 0 && c < clip.getMicrosecondLength())
			{
				clip.stop();
				clip.close();
				reinitialiserFluxAudio();
				positionActuelleDuClip = c;
				clip.setMicrosecondPosition(c);
				this.play();
			}
		}
		
		//M�thode pour r�initialiser les flux Audio apr�s une interuption
		//suite � une coupure de ligne de diffusion par close()
		public void reinitialiserFluxAudio()
			throws IOException, LineUnavailableException,
			UnsupportedAudioFileException
		{
			//Tout simplement pour dire que �a va repartir chercher le fichier
			//et son flux pour une nouvelle diffusion jusqu'� la fin
			fluxDEntreeAudio = AudioSystem.getAudioInputStream(new File(cheminDuFichier));
			clip.open(fluxDEntreeAudio);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}
	
}
