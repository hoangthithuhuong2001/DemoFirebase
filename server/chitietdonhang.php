<?php
	include "connect.php";
	$json = $_POST['json'];
	//$json ='[{"madonhang":"23","masanpham":1,"tensanpham":"Face Cream","giasanpham":350000,"soluongsanpham":1},{"madonhang":"23","masanpham":16,"tensanpham":"Đai Giảm Béo","giasanpham":1800000,"soluongsanpham":1}]';
	$data = json_decode($json, true);
	foreach ($data as $value) {
		$madonhang = $value ['madonhang'];
		$masanpham = $value ['masanpham'];
		$tensanpham = $value ['tensanpham'];
		$giasanpham = $value ['giasanpham'];
		$soluongsanpham = $value ['soluongsanpham'];
		$query = "INSERT INTO chitietdonhang (id, madonhang, masanpham, tensanpham, giasanpham, soluongsanpham) VALUES (null, '$madonhang', '$masanpham', '$tensanpham', '$giasanpham', '$soluongsanpham')";
		$Dta = mysqli_query($conn, $query);
		

	}
	if($Dta){
		echo "1";
	}else{
		echo "0";
	}

?>