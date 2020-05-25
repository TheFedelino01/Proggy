<?php
	session_start();
	include 'connection.php';
	$datiOld="";
	$fotoPresente=false;
	
	chkDatiRichiestiPresenti();
	
	$nome = mysqli_real_escape_string($conn,trim($_POST['nome']));
	$cognome = mysqli_real_escape_string($conn,trim($_POST['cognome']));
	$cf = mysqli_real_escape_string($conn,trim($_POST['cf']));
	$data = mysqli_real_escape_string($conn,trim($_POST['data']));
	$sesso = mysqli_real_escape_string($conn,trim($_POST['sesso']));
	$user = mysqli_real_escape_string($conn,trim($_POST['username']));
	$pass = mysqli_real_escape_string($conn,$_POST['password']);
	$pass2 = mysqli_real_escape_string($conn,$_POST['passwordR']);
	
	$pass = md5($_POST['password']);
	if($fotoPresente==true){
		//Carico la foto
		$uploaddir = './img/';
		$uploadfile = $uploaddir . basename($_FILES['userfile']['name']);
		
		//$_FILES['userfile']['tmp_name']: Il nome del file temporaneo in cui il file caricato è salvato sul server.
		if (move_uploaded_file($_FILES['userfile']['tmp_name'], $uploadfile)) {
			echo "File caricato correttamente!<br>";
		} else {
			echo "Errore upload!<br>";
		}
		
		$nomeFoto=basename($_FILES['userfile']['name']);
		$sql = "INSERT INTO cliente (cf,foto,nome,cognome,dataNascita,sesso,username,password) VALUES('$cf','$nomeFoto','$nome','$cognome','$data','$sesso','$user','$pass')";
		
	}else{
		$sql = "INSERT INTO cliente (cf,nome,cognome,dataNascita,sesso,username,password) VALUES('$cf','$nome','$cognome','$data','$sesso','$user','$pass')";
	}
	$result = $conn->query($sql);
	

	if($result)
		echo "Registrazione effettuata con successo! Click <a href='index.php'>here</a> for login";
	else{
		$errore=mysqli_error($conn);
		header('Location: registratiPage.php?err=Errore registrazione: '.$errore.'&'.$datiOld);
	}
	
	$conn->close();
?>

<?php

function chkDatiRichiestiPresenti(){
	$err = "";
	global $datiOld,$fotoPresente;
	
	if( !isset($_POST['nome']) || trim($_POST['nome'])=="" )
		$err.="Nome non presente<br>";
	else
		$datiOld.="oldNome=".$_POST['nome']."&";
	
	if( !isset($_POST['cognome']) || trim($_POST['cognome'])=="")
		$err.="Cognome non presente<br>";
	else
		$datiOld.="oldCognome=".$_POST['cognome']."&";
	
	if( !isset($_POST['cf']) || trim($_POST['cf'])=="")
		$err.="Codice Fiscale non presente<br>";
	else
		$datiOld.="oldCF=".$_POST['cf']."&";
	
	if( !isset($_POST['data']) || trim($_POST['data'])=="")
		$err.="Data di Nascina non presente<br>";
	else
		$datiOld.="oldData=".$_POST['data']."&";
	
	if( !isset($_POST['sesso']) || trim($_POST['sesso'])=="")
		$err.="Sesso non presente<br>";

	if( !isset($_POST['username']) || trim($_POST['username'])=="")
		$err.="Username non presente<br>";
	else
		$datiOld.="oldUsername=".$_POST['username']."&";
	
	if( basename($_FILES['userfile']['name'])!="")
		$fotoPresente=true;
	
	
	if(strlen($_POST['password'])<4)
		$err.="La password deve avere almeno 4 caratteri<br>";
	
	if($_POST['password'] != $_POST['passwordR']) 
		$err.="Le password non corrispondono<br>";
	
	if($err!=""){
		header('Location: registratiPage.php?err='.$err."&$datiOld");
		die();
	}
}
?>



