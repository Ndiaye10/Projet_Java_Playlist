package VLC;

//https://www.geeksforgeeks.org/play-audio-file-using-java/

//Programme java pour lire un audio
//Le fichier utilise l'objet Clip

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//Le sampled me fournie une interface pour les lectures audios et leurs traitements
//AudioInputStream : format et longeur audio sp�cif�s. Grace � son lecteur d'�chantillons de trame pas en octets,
//on pourra choisir d'avancer la lecture et choisir d'arriver � une position ult�rieur pour lire l'audio
import javax.sound.sampled.AudioInputStream;

//Me servira de point d'entr�e aux sources de system d'audio �chantillonn�
import javax.sound.sampled.AudioSystem;

//Les donn�es audios sont charg�es avant la lecture. Etant donn� kil sont pr�charg�es et que l'on connait leur longeur,
//on peut d�finir le d�marrage du clip � n'importe qu'elle position dans ses donn�es audio.
//Possibilit� de cr�er une loop
import javax.sound.sampled.Clip;

//Soul�ve une exception du fait que la ligne ne peut etre ouverte car elle n'est pas disponible.
//Situation qui survient lorsque les lignes sont d�j� utilis�es par d'autres appli
import javax.sound.sampled.LineUnavailableException;

//Exception lev�e parce que le fichier ne contient des donn�es valides d'un type de fichier
// ou d'un format format reconnu
import javax.sound.sampled.UnsupportedAudioFileException;

public class LecteurMusique {
	
	//Pour stocker la position actuelle
	//L'objet Long contient des types long et nous permettra de faire des
	//conversions en String facilement.
	long positionActuelleDuClip;
	
	//Cette propriet� repr�sentera notre m�dia en lui meme
	//Aussi, il est de type Clip car il pr�chargera les donn�es audios
	Clip clip;
	
	//Le Statut actuelle du m�dias (En terme d'avancement)
	//On le mettra en String pour nous donner une indication d'information (Th�orie � v�rifier ???�
	String statutActuelleDuClip;
	
	//AudioInputStream a un format de longeur audio sp�cifique et permet meme de
	//choisir une position de lecture
	AudioInputStream fluxDEntreeAudio;
	
}
