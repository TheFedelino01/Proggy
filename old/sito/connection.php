<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "id12710500_personalsafety_new";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
//echo "connected";

?>
