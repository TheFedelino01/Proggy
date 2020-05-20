<?php
 if (session_status() == PHP_SESSION_NONE) {
	session_start();
}
if (!isset($_SESSION["username"])) {
	header("Location: index.php?err=Devi effettuare il login!");
	die();
}
$giorni = array(
    "0" => "lunedi",
    "1" => "martedi",
	"2" => "mercoledi",
    "3" => "giovedi",
	"4" => "venerdi",
    "5" => "sabato",
	"6" => "domenica",
);
$err="";

include 'connection.php';
$ID=$_GET['idOrario'];
if(isset($_GET['idOrario'])&& $_SERVER['REQUEST_METHOD'] == 'GET'){
	$sql="SELECT oraInizio, oraFine, giorno, postiDisponibili, ID FROM orari WHERE id='$ID'";
	$result = $conn->query($sql);
	$row=$result->fetch_assoc();
}else{
	if(isset($_POST['inizio'])&&isset($_POST['fine'])&&isset($_POST['posti'])&&isset($_POST['giorno'])){
		$inizio=$_POST['inizio'];
		$fine=$_POST['fine'];
		$posti=$_POST['posti'];
		if(in_array($_POST['giorno'],$giorni)){//è da fare lo schifo in js
		$giorno=$_POST['giorno'];
		}else{
			$err="Inserisci un giorno valido";
			header("location: editOrario.php?idOrario=$ID&&err=$err");
		}	
		$sql="UPDATE orari SET oraInizio = '$inizio',oraFine = '$fine',giorno = '$giorno', postiDisponibili = '$posti' WHERE ID='$ID';";
		$result = $conn->query($sql);
		header("location: visualizzaOrari.php");
	}
}
?>

<html>
<body>
	<div align="center">
		<h1> Modifica orario </h1>
		</div>
	<form action="" method="post">
<label for="inizio">Orario di inizio</label>
<input type="time" name="inizio" value="<?php echo($row['oraInizio']); ?>">
<label for="fine">Orario di fine</label>
<input type="time" name="fine" value="<?php echo($row['oraFine']); ?>">
<label for="posti">Posti disponibili</label>
<input type="number" name="posti" value="<?php echo($row['postiDisponibili']); ?>">
<label for="giorno">Giorno</label>
<input type="text" name="giorno" value="<?php echo($row['giorno']); ?>">
<br><br>
<input type="submit" value="Invia">
<input type="reset" value="Cancella">
</form>
</body>
		
</html>
