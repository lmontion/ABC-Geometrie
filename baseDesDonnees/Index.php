<?php 

require_once './class.fileexplorer.php'; // Inclu le fichier Php

/*$file_path = $_SERVER['DOCUMENT_ROOT']. '/'; // Racine du serveur 
$file_path .= substr($_SERVER['REQUEST_URI'], 1); // Repere l'adresse du script

// On veut maintenant lancer le script a partir du dossier ou se trouve le script
$part = array(); // Creation du tableau
$adresse = ""; // nNtre futur adresse
$part = explode('/', $file_path); // On coupe l'adresse grace au '/' et on place les morceaux dans le tableau

for ($i=0; $i<count($part)-1; $i++) // On recupere toute l'adresse sauf la fin (nom du script)
{
	$adresse .= $part[$i]. '/'; // On reconstruit l'adresse dans la variable
}*/

$temp = $_SERVER['SCRIPT_FILENAME']."Niveau 1 Maternelle";
$adresse = str_replace("Index.php","",$temp);

$limit = 3; // le niveau limite de profondeur de fichier. Au delà, les dossiers ne sont plus explorés.

?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr">
	<head>
        <meta charset="utf-8" />			
        <link rel="stylesheet" href="includes/stylesheet.css" type="text/css"/>
        <title>AutoDoc</title>
		
	</head>
	
	<body>
		<form action="traitementDoc.php" method="post" >
			<h1>Générer une Documentation Php</h1>
			<h2>Arborescence du fichier "<?php echo $adresse; ?>"</h2> <!--  Affiche le chemin d'acces  -->
			<label for="selectFichier">Choisissez des dossiers ou des fichiers Php pour lesquels vous souhaitez générer une Documentation</label><br/>
			<br/>
				<?php
				// On appel notre arborescence de dossiers / fichiers
				$list = array();
				$idQ = 0;
				$testInsert = array();
				$list = exploreDirectory($adresse);
				$testInsert = afficherArborescence($list, $adresse);

				$fp = fopen('dataQ.txt', 'w');
				fwrite($fp, "INSER INTO question VALUES \r\n");
				for ($i = 0; $i < count($testInsert['question']); $i++) {
					if ($i == 0){
						$testInsert['question'][$i]=substr($testInsert['question'][$i],3);
					}
					fwrite($fp, $testInsert['question'][$i]);
				}
				fclose($fp);

				$fp = fopen('dataAp.txt', 'w');
				fwrite($fp, "INSER INTO appartenir VALUES \r\n");
				for ($i = 0; $i < count($testInsert['appartenir']); $i++) {
					if ($i == 0){
						$testInsert['appartenir'][$i]=substr($testInsert['appartenir'][$i],3);
					}
					fwrite($fp, $testInsert['appartenir'][$i]);
				}
				fclose($fp);

				/*echo "NB QUESTIONS ---> ".count($testInsert['question']);
				echo "</br> NB APPARTENIR ---> ".count($testInsert['appartenir']);*/

				//var_dump($testInsert['question']);




/*
				// Lecture fichier texte => MARCHE !!!
				//$test = $_SERVER['SCRIPT_FILENAME']."lang.txt";
				//$bidon = str_replace("Index.php","",$test);
				$bidon = "C:/Program Files (x86)/EasyPHP-DevServer/data/localweb/projects/PROJET_TUT/Exemple Hierrarchie Dossier/AutoDocNew/Questions/Questions sur les couleurs/Questions sur les losanges en couleurs/Clique sur le losange orange/lang.txt";
				$myfile = fopen($bidon, "r") or die("Unable to open file!");
				$toto = fread($myfile,filesize("lang.txt"));
				$titi = explode("\n", $toto);
				print_r($titi);
				fclose($myfile);
			
*/			
			?>
			<br/><input type="submit" value="Génerer la doc." class="Generer" /><br/><br/>
			<label> Elément(s) selectionné(s) : </label>


		</form>
	</body>
</html>
