<?php
	session_start();
	include 'connection.php';
	
	$id =  mysqli_real_escape_string($conn,trim($_GET['id']));
	$idEnte =  mysqli_real_escape_string($conn,trim($_GET['idEnte']));
	
	$sql = "UPDATE `prenotazione` SET `cancellato`='1' WHERE id='$id'";
	$result = $conn->query($sql);
	
	if ($result) {
		header('Location: listaPrenotazioni.php?codEnte='.$idEnte.'&ris=Prenotazione cancellata con successo');
	} else {
		header('Location: listaPrenotazioni.php?codEnte='.$idEnte.'&ris=Impossibile eliminare la prenotazione!');
	}
	$conn->close();
	
?>
