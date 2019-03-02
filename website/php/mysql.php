<?php
$servername = "127.0.0.1:3306";
$username = "root";
$password = "";
$dbname = "test";

$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$GLOBALS['conn'] = $conn;

function getData(){
	$conn = $GLOBALS['conn'];
	$sql = "SELECT INET_NTOA(`ip`) AS `ip`, `host`, `protocol`, UNIX_TIMESTAMP(`time`) AS `time` FROM `queryme_ip`;";
	$result = $conn->query($sql);
	$data = array();
	$data['time'] = array();
	$data['time_minute'] = array();
	$data['unique_minute'] = array();
	$data['unique_time'] = array();
	$GLOBALS['holder'] = array();
	$GLOBALS['minute_holder'] = array();
	$data['ips'] = array();
	$data['host'] = array();
	$data['protocol'] = array();
	if ($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			$time = $row['time'];
			$minute = (int)((time() - $time) / 60);
			$hour = (int)((time() - $time) / 3600);
			$data['time'] = addToArray($data['time'], $hour);
			if($hour<2){
				$data['time_minute'] = addToArray($data['time_minute'], $minute);
				$data['unique_minute'] = addToUniqueArray($data['unique_minute'], 'minute_holder', $row['ip'], $minute);
			}
			$data['unique_time'] = addToUniqueArray($data['unique_time'], 'holder', $row['ip'], $hour);
			$data['ips'] = addToArray($data['ips'], $row['ip']);
			$data['host'] = addToArray($data['host'], $row['host']);
			$data['protocol'] = addToArray($data['protocol'], $row['protocol']);
		}
	}
	return $data;
}

function addToArray($data, $key){
	if(array_key_exists($key, $data)){
		$data[$key]++;
	}else{
		$data[$key] = 1;
	}
	return $data;
}

function addToUniqueArray($data, $global, $id, $key){
	$holder = $GLOBALS[$global];
	$test = array();
	if(array_key_exists($key, $holder)){
		$test = $holder[$key];
	}
	if(!in_array($id, $test)){
		array_push($test, $id);
		if(array_key_exists($key, $data)){
			$data[$key]++;
		}else{
			$data[$key] = 1;
		}
		$holder[$key] = $test;
		$GLOBALS[$global] = $holder;
	}
	return $data;
}
?>