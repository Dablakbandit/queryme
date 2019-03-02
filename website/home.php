<?php
require "php/mysql.php";

$data = getData();

// TIME
$time = $data['time'];
$max = key($time);

$time_data = array();

for($i = 0; $i <= $max; $i++){
	if(array_key_exists($i, $time)){
		$time_data[$i] = array("x"=>"newDateHours(".$i.")", "y"=>"".$time[$i]);
	}else{
		$time_data[$i] = array("x"=>"newDateHours(".$i.")", "y"=>"0");
	}
}

// UNIQUE TIME
$unique_time = $data['unique_time'];
$unique_max = key($time);

$unique_data = array();

for($i = 0; $i <= $unique_max; $i++){
	if(array_key_exists($i, $unique_time)){
		$unique_data[$i] = array("x"=>"newDateHours(".$i.")", "y"=>"".$unique_time[$i]);
	}else{
		$unique_data[$i] = array("x"=>"newDateHours(".$i.")", "y"=>"0");
	}
}

// MINUTES
$minutes = $data['time_minute'];
$minutes_max = key($minutes);

$minute_data = array();
for($i = 0; $i <= $minutes_max; $i++){
	if(array_key_exists($i, $minutes)){
		$minutes_data[$i] = array("x"=>"newDateMinutes(".$i.")", "y"=>"".$minutes[$i]);
	}else{
		$minutes_data[$i] = array("x"=>"newDateMinutes(".$i.")", "y"=>"0");
	}
}

// UNIQUE Minutes
$unique_minutes = $data['unique_minute'];
$unique_minutes_max = key($unique_minutes);

$unique_minute_data = array();
for($i = 0; $i <= $unique_minutes_max; $i++){
	if(array_key_exists($i, $minutes)){
		$unique_minutes_data[$i] = array("x"=>"newDateMinutes(".$i.")", "y"=>"".$unique_minutes[$i]);
	}else{
		$unique_minutes_data[$i] = array("x"=>"newDateMinutes(".$i.")", "y"=>"0");
	}
}


// IPS


// HOSTS
$hosts = $data['host'];
$hosts_data = array();
$hosts_host = array();
$hosts_colors = array();

foreach ($hosts as $key => $value){
	$hosts_data[$key] = $value;
	$hosts_host[$key] = "'".$key."'";
	$hosts_colors[$key] = "'rgb(".rand(0,255).", ".rand(0,255).", ".rand(0,255).")'";
}


// PROTOCOL



?>

<!doctype html>
<html>

<head>
	<title>Line Chart</title>
	<script src="js/moment.min.js"></script>
	<script src="js/Chart.min.js"></script>
	<script src="js/utils.js"></script>
	<style>
		canvas {
			-moz-user-select: none;
			-webkit-user-select: none;
			-ms-user-select: none;
		}
	</style>
</head>

<body>
	<!--- TIME --->
	<div style="width:65%;float:left;">
		<canvas id="canvas"></canvas>
	</div>
	<!--- HOSTS --->
	<div id="canvas-holder" style="width:35%; float:right;">
		<canvas id="chart-area"></canvas>
	</div>
	<div style="float:left;">
		<button id="hour1">1H</button>
		<button id="day1">1D</button>
		<button id="day30">30D</button>
		<button id="dayall">All</button>
	</div>
	
	
	
	<script>
		var timeFormat = 'MM/DD/YYYY HH:mm';
		var now = moment();
		var data_hours =  <?php echo str_replace('"', '', json_encode(array_values($time_data)));?>;
		var unique_hours =  <?php echo str_replace('"', '', json_encode(array_values($unique_data)));?>;
		
		var data_minutes =  <?php echo str_replace('"', '', json_encode(array_values($minutes_data)));?>;
		var unique_minutes =  <?php echo str_replace('"', '', json_encode(array_values($unique_minutes_data)));?>;
		
		function newDateMinutes(minutes) {
			return moment(now).subtract(minutes, 'm').toDate();
		}
		
		function newDateHours(hours) {
			return moment(now).subtract(hours, 'h').toDate();
		}
		
		function updateTitle(config, pos, title){
			var total = 0;
			var mmin = moment(config.options.scales.xAxes[0].time.min);
			config.data.datasets[pos].data.forEach(function(dataObj, j) {
				if(mmin.isSameOrBefore(dataObj.x)){
					total += dataObj.y;
				}
			});
			config.data.datasets[pos].label = title + ": " + total;
		}

		var color = Chart.helpers.color;
		var config = {
			type: 'line',
			data: {
				datasets: [{
					label: 'Queries',
					backgroundColor: color(window.chartColors.green).alpha(0.5).rgbString(),
					borderColor: window.chartColors.green,
					fill: true,
					data: data_hours,
				},
				{
					label: 'Unique Queries',
					backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
					borderColor: window.chartColors.blue,
					fill: true,
					data: unique_hours,
				}
				]
			},
			options: {
				title: {
					text: 'QueryMe'
				},
				scales: {
					xAxes: [{
						type: 'time',
						time: {
							unit: 'hour',
							min: newDateHours(<?php echo $max;?>)
						},
					}],
					yAxes: [{
						scaleLabel: {
							display: true,
							labelString: 'Queries',
						}
					}]
				},
			}
		};
		
		var config1 = {
			type: 'pie',
			data: {
				datasets: [{
					data: <?php echo str_replace('"', '', json_encode(array_values($hosts_data)));?>,
					backgroundColor: <?php echo str_replace('"', '', json_encode(array_values($hosts_colors)));?>,
					label: 'Ips'
				}],
				labels: <?php echo str_replace('"', '', json_encode(array_values($hosts_host)));?>
			},
			options: {
				responsive: true
			}
		};
		
		

		window.onload = function() {
			var ctx = document.getElementById('canvas').getContext('2d');
			updateTitle(config, 0, "Queries");
			updateTitle(config, 1, "Unique Queries");
			window.myLine = new Chart(ctx, config);
			
			var ctx1 = document.getElementById('chart-area').getContext('2d');
			window.myPie = new Chart(ctx1, config1);
		};
		
		document.getElementById('hour1').addEventListener('click', function() {
			config.options.scales.xAxes[0].time.unit = 'minute';
			config.options.scales.xAxes[0].time.min = newDateMinutes(60);
			config.data.datasets[0].data = data_minutes;
			config.data.datasets[1].data = unique_minutes;
			updateTitle(config, 0, "Queries");
			updateTitle(config, 1, "Unique Queries");
			window.myLine.update();
		});
		document.getElementById('day1').addEventListener('click', function() {
			config.options.scales.xAxes[0].time.unit = 'hour';
			config.options.scales.xAxes[0].time.min = newDateHours(24);
			config.data.datasets[0].data = data_hours;
			config.data.datasets[1].data = unique_hours;
			updateTitle(config, 0, "Queries");
			updateTitle(config, 1, "Unique Queries");
			window.myLine.update();
		});
		document.getElementById('day30').addEventListener('click', function() {
			config.options.scales.xAxes[0].time.unit = 'hour';
			config.options.scales.xAxes[0].time.min = newDateHours(24*30);
			config.data.datasets[0].data = data_hours;
			config.data.datasets[1].data = unique_hours;
			updateTitle(config, 0, "Queries");
			updateTitle(config, 1, "Unique Queries");
			window.myLine.update();
		});
		document.getElementById('dayall').addEventListener('click', function() {
			config.options.scales.xAxes[0].time.unit = 'hour';
			config.options.scales.xAxes[0].time.min = newDateHours(<?php echo $max;?>);
			config.data.datasets[0].data = data_hours;
			config.data.datasets[1].data = unique_hours;
			updateTitle(config, 0, "Queries");
			updateTitle(config, 1, "Unique Queries");
			window.myLine.update();
		});
		


		
	</script>

	
	
</body>

</html>