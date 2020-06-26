package VLC;

//https://www.geeksforgeeks.org/play-audio-file-using-java/

//Programme java pour lire un audio
//Le fichier utilise l'objet Clip

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//Le sampled me fournie une interface pour les lectures audios et leurs traitements
//AudioInputStream : format et longeur audio spécifés. Grace à son lecteur d'échantillons de trame pas en octets,
//on pourra choisir d'avancer la lecture et choisir d'arriver à une position ultérieur pour lire l'audio
import javax.sound.sampled.AudioInputStream;

//Me servira de point d'entrée aux sources de system d'audio échantillonné
import javax.sound.sampled.AudioSystem;

//Les données audios sont chargées avant la lecture. Etant donné kil sont préchargées et que l'on connait leur longeur,
//on peut définir le démarrage du clip à n'importe qu'elle position dans ses données audio.
//Possibilité de créer une loop
import javax.sound.sampled.Clip;

//Soulève une exception du fait que la ligne ne peut etre ouverte car elle n'est pas disponible.
//Situation qui survient lorsque les lignes sont déjà utilisées par d'autres appli
import javax.sound.sampled.LineUnavailableException;

//Exception levée parce que le fichier ne contient des données valides d'un type de fichier
// ou d'un format format reconnu
import javax.sound.sampled.UnsupportedAudioFileException;

public class LecteurMusique {
	
	//Pour stocker la position actuelle
	//L'objet Long contient des types long et nous permettra de faire des
	//conversions en String facilement.
	long positionActuelleDuClip;
	
	//Cette proprieté représentera notre média en lui meme
	//Aussi, il est de type Clip car il préchargera les données audios
	Clip clip;
	
	//Le Statut actuelle du médias (En terme d'avancement)
	//On le mettra en String pour nous donner une indication d'information (Théorie à vérifier ???°
	String statutActuelleDuClip;
	
	//AudioInputStream a un format de longeur audio spécifique et permet meme de
	//choisir une position de lecture
	AudioInputStream fluxDEntreeAudio;
	
}
