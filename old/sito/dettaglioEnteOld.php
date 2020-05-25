<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
if(!isset($_SESSION["username"])){
	header("Location: index.php?err=Devi effettuare il login!");
	die();
}

include 'connection.php';
	
$sql = "SELECT * FROM ente where cod=".$_GET["cod"];
$result = $conn->query($sql);
$row = $result->fetch_assoc();

$nomeEnte=$row["nome"];
$idEnte=$_GET["cod"];
//$arrayUtilizzoScheda = new Array();//idScheda;dataOraInizio;dataOraFine
$tabellaUtilizzoScheda="<tr><th>Scheda ID</th><th>Data Ora Inizio</th><th>Data Ora Fine</th><th></th></tr>";

$sql ="SELECT * FROM (ente join scheda on ente.cod=scheda.idEnte)join utilizza on scheda.cod = utilizza.idScheda where ente.cod='$idEnte' AND utilizza.idCliente='".$_SESSION["idCliente"]."'";
$result = $conn->query($sql);
while($row = $result->fetch_assoc()){
	//array_push($arrayUtilizzoScheda,$row["idScheda"].";".$row["dataOraInizio"].";".$row["dataOraFine"]);	
	$idScheda=$row["idScheda"];
	$dInizio=$row["dataOraInizio"];
	$dFine=$row["dataOraFine"];
	$tabellaUtilizzoScheda.="<tr><td>$idScheda</td><td>$dInizio</td><td>$dFine</td><td><input type='button' value='Info' onClick=\"info('$idScheda','$dInizio','$dFine')\" ></td></tr>";
}


$conn->close();
?>
<html>

<head>
<script>
function info(idScheda,dataInizio,dataFine){
	var codEnte="<?php echo($idEnte); ?>";

	window.location="infoScheda.php?codEnte="+codEnte+"&idScheda="+idScheda+"&dataInizio="+dataInizio+"&dataFine="+dataFine;
}
</script>
</head>

<body>


<h1>Ente: <?php echo($nomeEnte); ?></h1><br>

<table>
	<?php echo $tabellaUtilizzoScheda;?>
</table>


</body>

</html>