<?php
session_start();
if(!isset($_SESSION["username"]) || isset($_SESSION["idAdmin"])){
	header("Location: index.php?err=Devi effettuare il login!");
	die();
}

include 'connection.php';
	
//$codEnte=$_GET["codEnte"];
$idScheda=$_GET["idScheda"];
$dataInizio=$_GET["dataInizio"];
$dataFine=$_GET["dataFine"];
$arrayPosizioni = array(); //lat;long;dataOra

$sql = "SELECT * FROM posizione WHERE idScheda='$idScheda' AND dataOra>='$dataInizio' AND dataOra<='$dataFine' ORDER BY posizione.dataOra ASC";
$result = $conn->query($sql);
if ($result->num_rows === 0)
	die("nessuna posizione disponibile");

while($row = $result->fetch_assoc()){
	//array_push($arrayUtilizzoScheda,$row["idScheda"].";".$row["dataOraInizio"].";".$row["dataOraFine"]);	
	$lat=$row["latitudine"];
	$long=$row["longitudine"];
	$dataOra=$row["dataOra"];
	array_push($arrayPosizioni,$lat.";".$long.";".$dataOra);	
}

//print_r($arrayPosizioni);
$conn->close();



function js_str($s)
{
    return '"' . addcslashes($s, "\0..\37\"\\") . '"';
}
function js_array($array)
{
    $temp = array_map('js_str', $array);
    return '[' . implode(',', $temp) . ']';
}
?>
<html>

<head>
	<!-- Custom fonts for this template-->
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	<!-- Custom styles for this template-->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?"></script>

	<script>
	window.onload = function () {
		initMap();
	};

	var map;
	var marker;

	function addMarker(lat,lon,nome) {
		marker = new google.maps.Marker({
		position: new google.maps.LatLng(lat, lon),
		title: nome,
		map: map
		});
	}

	function initMap() {
		var posizioni = <?php echo (js_array($arrayPosizioni)); ?>;//Prendo tutte le posizioni della scheda
		//Imposto il centro della mappa prendendo la prima posizione registrata
		var centroPosizione = posizioni[0].split(";");
		var myLatLng = {lat: parseFloat(centroPosizione[0]),  lng: parseFloat(centroPosizione[1])};
		
		var arrayLatLng=new Array();
		
		map = new google.maps.Map(document.getElementById('dvMap'), {
		zoom: 18,
		center: myLatLng,
		mapTypeId: 'satellite'
		});

		for(var i=0; i<posizioni.length;i++){
			var pos = posizioni[i].split(";");
			addMarker(pos[0],pos[1],pos[2]);
			
			var coordinata = {lat:parseFloat(pos[0]), lng:parseFloat(pos[1])};
			arrayLatLng.push(coordinata);
		}

		map.setTilt(1); //Posizione telecamera 1=2D

		
		var tripPath = new google.maps.Polyline({
			path: arrayLatLng,
			geodesic: true,
			strokeColor: '#FF0000',
			strokeOpacity: 1.0,
			strokeWeight: 2
	  });
	  tripPath.setMap(map);
	}


	</script>
</head>

<body>



<div id="dvMap" style="width: 100%; height: 600px"></div>

</body>

</html>