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

$oldGiorno="1";

include 'connection.php';

?>
<html lang="en">

<head>

	<title>I tuoi orari</title>
	<script>
	</script>

	<style>
		table {
			width: 50%;
		}

		.imgGrande {
			margin: auto;
			display: block;
			padding-bottom: 20px;
			width: 60%;
		}

		@media only screen and (max-width: 1000px) {
			table {
				width: 100%;
			}

			.imgGrande {

				width: 100%;
			}
		}


		.zoom {
			animation-name: zoomAnim;
			animation-duration: 2s;
			animation-iteration-count: 1;
		}

		@keyframes zoomAnim {
			0% {
				transform: scale(0);
			}

			70% {
				transform: scale(1.3);
			}

			100% {
				transform: scale(1);
			}
		}
	</style>
</head>

<body>
<h1>I tuoi orari</h1>
	<?php 
	for($i=0;$i<7;$i++)
	{
		$sql="SELECT oraInizio, oraFine, giorno, postiDisponibili, ID FROM orari WHERE idEnte=(SELECT idEnte FROM admin WHERE username='".$_SESSION['username']."') && giorno='$giorni[$i]' ORDER BY giorno";
		?>
<?php 	
		$result = $conn->query($sql);
		if($result->num_rows>0){
			?>
					<table border=1>
<?php 
	  while($row=$result->fetch_assoc()){
	?>
	
	<?php
	//Nome giorno della settimana
	if($row['giorno']==$oldGiorno){
	}else{
		?>	
	<h2><?php echo strtoupper($row['giorno'])?></h2>
	<?php
	}
	?>

	
		<tr>
		<td>
			<?php echo($row['oraInizio']); ?>
		</td>
		 
		<td>
			<?php echo($row['oraFine']); ?>
		</td>
		
		<td>
			<?php echo($row['postiDisponibili']); ?>
		</td>
		<td>
			<?php
				echo("<a href='editOrario.php?idOrario=".$row['ID']."'><input type='button' name='edit' value='Modifica'></a>");
			?>
		</td>


		</tr>

	<?php 
	$oldGiorno=$row['giorno'];
	  }
	 }
	?>
</table>

<?php 
	 }
	?>

	

</body>

</html>
