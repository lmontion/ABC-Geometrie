<?php

/** 
* Cette fonction recursive permet de recuperer une arborescence de fichiers et dossier en prenant en parametre
* @param $dir_path (un chemin d'acces) et $level = 0 permettant avec incrementation d'etablir une hirarchie des dossiers et fichiers
*/

function exploreDirectory ($dir_path, $level = 0) {

	global $limit; // Notre variable $limit permet de limiter la profondeur de recuperation des dossiers et fichiers et sa valeur est definit dans l'entete Accueil.php
	
	$list = array(); //Creation de notre tableau
	
	if ($limit == -1 || $level <= $limit){ // Si la limite est de -1 ou que la variable level est inferieur ou égale à la variable limite

		if ($pointer = opendir($dir_path)) { // Si on peut ouvrir le dossier
			
			while (($file = readdir($pointer)) !== false) { // Si on peut le lire
	
				/*if("." == $file) {continue;}
			   // converts the filename to utf8
			   $file_utf8 = iconv( "iso-8859-1", "utf-8", $file );
			   // encode the path ('path' part: already utf8; 'filename' part: still iso-8859-1)
			   $file = str_replace( "%2F", "/", rawurlencode( "{$relativepath}/" )) . rawurlencode( utf8_decode( "{$file_utf8}" ));*/

			   //$file = utf8_encode($file);

				$new_path = $dir_path.'/'.$file; // Notre chemin d'acces
				
				if (is_dir($new_path) && $file != '.' && $file != '..') { // Dans le cas d'un dossier (autre que ./ et ../)
					
					$file = utf8_encode($file);

					$dir['nom'] = $file; // On recupere le nom du dossier.
					//echo "NOM DU FICHIER ----> ".$file;
					$dir['arborescence'] = exploreDirectory($new_path, $level + 1);	// On recupere l'arborescence grace à la recursive			
					$list['dossiers'][] = $dir; // On enregistre les infos du dossier dans la catégorie 'dossiers' du tableau.
					
				} else if (is_file($new_path)) { // Dans le cas d'un fichier
					
					$part = explode('.', $file); // On explose le chemin d'acce en part
					$ext = $part[count($part)-1]; // On recupere la derniere partie
					
					if ($ext == 'png') { // Si c'est un fichier php ou php3 ou php5
						$list['fichier_png'][] = $file; //On cre une categorie 'fichier_png' dans notre tableau et on le met dedans
					}
					if ($ext == 'txt') { // Si c'est un fichier php ou php3 ou php5
						$list['fichier_txt'][] = $file; //On cre une categorie 'fichier_png' dans notre tableau et on le met dedans
					}	/* else { // Sinon si c'est un autre fichier
						$list['fichiers'][] = $file; // On cre une categorie 'fichiers' dans notre tableaue et on le met dedans
					}*/
				} else if (is_link($new_path)) { // Dans le cas d'un lien symbolique
				// Def lien symbolique : permet d'attribuer un autre chemin d'accès à un fichier en pointant sur un nom de fichier
					$list['symboliques'][] = $file; // On cre une categorie 'symbolique' dans notre tableau et on le met dedans
				}
			}
		} else { // Sinon on ne peut pas ouvrir le dossier
			echo 'Impossible'; // On affiche un message d'erreur
		}
	}	
	return $list; // On recupere notre tableau de tableau
}

/**
 * Genere une liste de listes imbriquees representant l'arborescence du tableau passe en parametre
 * @param array $liste (liste generee avec la fonction exploreDirectory())
 */


function afficherArborescence ($arbo, $parent_path, $theme = "", $question = "", $folder = "", $level = 0) {
	global $limit; // Pour chaque niveau, la liste doit afficher dans l'ordre les dossiers, les fichiers php, les fichiers, puis les liens symboliques	
	global $tabInsert;
	global $idQ;
	$tab = array();
	$imgOK = "";
	$tabImg = null;
	$tempImg = null;
	if (!empty($arbo)) { // Si notre tableau n'est pas vide
		
		?><ul class="tree" id="tree"><?php 
		
		if (isset($arbo['dossiers'])) { // Pour chaque elements de 'dossiers' 
			
			foreach ($arbo['dossiers'] as $dossier) {
				
				if ($level==0){
					//$temp = explode(" ", $dossier['nom']);
					//$theme = $temp[3];
					$theme = $dossier['nom'];
				}
				if ($level==1){
					$folder = $dossier['nom'];
				}
				if ($level==2){
					$question =  $dossier['nom'];
				}
	
				$new_parent_path = $parent_path.$dossier['nom'].'/';
				$new_arbo = $dossier['arborescence']; 
				
				?><li class ="folder" title="<?php echo $new_parent_path; ?>"><img src="img/icofold.png" class="fold"/><span class="selectable"><?php echo $dossier['nom'];			
				
				if ($level >= $limit && $limit != -1) echo " [...]"; // Si la limite bloque l'acce a la lecture des fichiers et sous-dossier inferieur
				
				?></span><?php 	

				if ($limit == -1 || $level < $limit) { // Si nous n'avons pas atteint la limite de profondeur fixee on continue
					afficherArborescence($new_arbo, $new_parent_path,$theme, $question, $folder, $level + 1);
				}
				
				?></li><?php
				//$i++;
			}
		}
		
		if (isset($arbo['fichier_png'])) { // Pour chaque element de 'fichier_png'
			foreach ($arbo['fichier_png'] as $fichier) {
				$tempImg[] = $fichier;
				$file_path = $parent_path.$fichier;
				
				?><li class = "file" title="<?php echo $file_path; ?>" ><img src="img/icofile.png" class="fil"/><span class="selectable"><?php echo $fichier; ?></span></li><?php 
			}
		}
		if (isset($arbo['fichier_txt'])) { // Pour chaque element de 'fichier_png'
			foreach ($arbo['fichier_txt'] as $fichier) {
				
				$file_path = $parent_path.$fichier;
				
				?><li class = "file" title="<?php echo $file_path; ?>" ><img src="img/icofile.png" class="fil"/><span class="selectable"><?php echo $fichier; ?></span></li><?php 
			}
		}


		$test = $_SERVER['SCRIPT_FILENAME']."Niveau 1 Maternelle/".$theme."/".$folder."/".$question."/text.txt";
		$pathFileLang = str_replace("Index.php","",$test);

		if ($tempImg != null){
			foreach ($tempImg as $img) {
				if (strpos($img,'ok') !== false) {
					$temp = explode("-", $img);
				    $imgOK = $temp[0];
				}else{
					$tabImg[] = $img;
				}
			}
		}
		
		$anglais = "EN";
		$espagnol = "ES";
		
		if ($level == 3) { 

			//echo $pathFileLang;
			$myfile = fopen(utf8_decode($pathFileLang), "r") or die("Unable to open file!");
			//$myfile = fopen("text.txt", "r") or die("Unable to open file!");

			/*if (is_readable("lang.txt")) {
			    //$content = fread($fhandle,filesize($fname));
			    echo "----------------------------------> OK <-----------------------------------";
			} else {
			    echo 'The file is not readable.';
			}*/

			$toto = fread($myfile,1000);//filesize("text.txt"));
			$titi = explode("\n", $toto);
			//print_r($titi);
			$anglais = utf8_encode($titi[0]);
			$espagnol = utf8_encode($titi[1]);
			fclose($myfile);
		}

		/*$c_1_10_cou = 0;
		$c_1_20_cou = 1;
		$c_1_40_cou = 2;
		$c_1_10_for = 3;
		$c_1_20_for = 4;
		$c_1_40_for = 5;
		$c_1_10_coufor = 6;
		$c_1_20_coufor = 7;
		$c_1_40_coufor = 8;*/
		
		$insertQ = ",\r\n(".$idQ.",'".$question."','".$anglais."','".$espagnol."','".$imgOK.".png','".$tabImg[0]."','".$tabImg[1]."','".$tabImg[2]."')";
		
		if ($level == 3) { 
			$tabInsert['question'][] = $insertQ;
			if ($theme == "couleurs"){
				$tabInsert['appartenir'][] = ",\r\n(0,".$idQ.")";
				$tabInsert['appartenir'][] = ",\r\n(1,".$idQ.")";
				$tabInsert['appartenir'][] = ",\r\n(2,".$idQ.")";
			}
			if ($theme == "formes"){
				$tabInsert['appartenir'][] = ",\r\n(3,".$idQ.")";
				$tabInsert['appartenir'][] = ",\r\n(4,".$idQ.")";
				$tabInsert['appartenir'][] = ",\r\n(5,".$idQ.")";
			}
			if ($theme == "couleurs et formes"){
				$tabInsert['appartenir'][] = ",\r\n(6,".$idQ.")";
				$tabInsert['appartenir'][] = ",\r\n(7,".$idQ.")";
				$tabInsert['appartenir'][] = ",\r\n(8,".$idQ.")";
			}
			$idQ++;
		}

		?></ul>


		<?php
		return $tabInsert;
	}
}

