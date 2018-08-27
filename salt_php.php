<?php
	
	$db_serverhost = "sql530.your-server.de";
	$db_username = "efxinf_7";
	$db_password = "LxkLYb23hC7nKp4X";
	$db_name = "efxinf_db7";
	
	$conn = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);
	
	$user_username = $_POST['user_username'];
	
	$statement = mysqli_prepare($conn, "SELECT user_hashes.hash_salt FROM user_archive INNER JOIN user_hashes ON user_archive.user_id = user_hashes.user_id WHERE user_archive.user_username = ?");
	mysqli_stmt_bind_param($statement, "s", $user_username);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	
	if(mysqli_stmt_num_rows($statement) > 0) {
		mysqli_stmt_bind_result($statement, $salt);
		
		$response = array();
		$response['success'] = true;
		
		while ($field = mysqli_stmt_fetch($statement)) {
			$response['hash_salt'] = $salt;
		}
		
		mysqli_stmt_close($statement);
		print_r(json_encode($response));
	} else {
		mysqli_stmt_close($statement);
		$response = array();
		$response['success'] = false;
		print_r(json_encode($response));
	}
?>