<?php
if (session_status() == PHP_SESSION_NONE) {
	session_start();
}
if (!isset($_SESSION["username"])) {
	header("Location: index.php?err=Devi effettuare il login!");
	die();
}

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

<table border=1>
	<?php 
		$sql="SELECT oraInizio, oraFine, giorno, postiDisponibili, ID FROM orari WHERE idEnte=(SELECT idEnte FROM admin WHERE username='".$_SESSION['username']."') ORDER BY giorno";
		
		$result = $conn->query($sql);
	  while($row=$result->fetch_assoc()){
	?>
	
		<tr>
		<td>
			<?php echo($row['oraInizio']); ?>
		</td>
		 
		<td>
			<?php echo($row['oraFine']); ?>
		</td>
		
		<td>
			<?php echo($row['giorno']); ?>
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
	 }
	?>
</table>


	

</body>

</html>