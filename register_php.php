<?php

 //Details
 
	$db_serverhost = "sql530.your-server.de";
	$db_username = "efxinf_7";
	$db_password = "LxkLYb23hC7nKp4X";
	$db_name = "efxinf_db7";
 
 //Connection step 1
 
	$conn = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);

/*	
	if (mysqli_connect_errno()) {
		echo "Establishing connection failed." . mysqli_connect_errno();
	}
	echo "Connection to MySQL Server established";
*/
	
//POST
	
	$user_username = $_POST["user_username"];
	$user_name = $_POST["user_name"];
	$user_firstname = $_POST["user_firstname"];
	$user_school = $_POST["user_school"];
	$user_yearofbirth = $_POST["user_yearofbirth"];
	$user_email = $_POST["user_email"];
	$user_password = $_POST["user_password"];
	
	//Checking for availability of username
	$check = mysqli_prepare($conn, "SELECT * FROM user_archive WHERE user_username = ?");
	mysqli_stmt_bind_param($check, "s", $user_username);
	mysqli_stmt_execute($check);
	mysqli_stmt_store_result($check);
	
	if (mysqli_stmt_num_rows($check) > 0) {
		mysqli_stmt_close($check);
		//TODO username exists already
		$response = array();
		$response['success'] = false;
		
		print_r(json_encode($response));
		
	} else {
	
		mysqli_stmt_close($check);
		//Statement
		$statement = mysqli_prepare($conn, "INSERT INTO user_archive(user_username, user_name, user_firstname,
								   user_school, user_yearofbirth, user_email, user_password) 
								   VALUES (?, ?, ?, ?, ?, ?, ?)");
		
		//Binding POST to Statement
		mysqli_stmt_bind_param($statement, "ssssiss", $user_username, $user_name, $user_firstname, $user_school,
							   $user_yearofbirth, $user_email, $user_password);
		
		mysqli_stmt_execute($statement);
		mysqli_stmt_close($statement);
		
		$id_retrieve_statement = mysqli_prepare($conn, "SELECT user_id FROM user_archive WHERE user_username = ?"); //Checking for Username
		mysqli_stmt_bind_param($id_retrieve_statement, "s", $user_username);
		mysqli_stmt_execute($id_retrieve_statement);
		
		mysqli_stmt_store_result($id_retrieve_statement);
		mysqli_stmt_bind_result($id_retrieve_statement, $arch_id);
		
		while ($field = mysqli_stmt_fetch($id_retrieve_statement)) {
			$user_id = $arch_id;
		}
		
		mysqli_stmt_close($id_retrieve_statement);
		
		$subj_statement = mysqli_prepare($conn,"INSERT INTO user_subjects(user_id, subj_german, subj_spanish,
										 subj_english, subj_french, subj_biology, subj_chemistry, subj_music, 
										 subj_maths, subj_physics) VALUES (?,?,?,?,?,?,?,?,?,?)");
										 
		$placeholder = 0;
										 
		mysqli_stmt_bind_param($subj_statement, "iiiiiiiiii", $user_id, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder);
		mysqli_stmt_execute($subj_statement);
		
		mysqli_stmt_close($subj_statement);
		
		$response = array();
		$response['success'] = true;
		
		print_r(json_encode($response));
	}
	
?>
	