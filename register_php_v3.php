<?php

	//Details
 
	$db_serverhost = "sql530.your-server.de";
	$db_username = "efxinf_7";
	$db_password = "LxkLYb23hC7nKp4X";
	$db_name = "efxinf_db7";
 
 //Connection step 1
 
	$conn = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);

	
//POST
	
	$user_username = $_POST["user_username"];
	$user_name = $_POST["user_name"];
	$user_firstname = $_POST["user_firstname"];
	$user_school = $_POST["user_school"];
	$user_grade = $_POST["user_grade"];
	$user_email = $_POST["user_email"];
	
	$hash_password = $_POST["hash_password"];
	$hash_salt = $_POST['hash_salt'];
	
	//Checking for availability of username
	$statement= mysqli_prepare($conn, "SELECT user_archive.* FROM user_archive WHERE user_archive.user_username = ? OR user_archive.user_email = ?");
	mysqli_stmt_bind_param($statement, "ss", $user_username, $user_email);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	
	if (mysqli_stmt_num_rows($statement) > 0) {
		mysqli_stmt_close($statement);
		//TODO username or email exists already
		$response = array();
		$response['success'] = false;
		
		print_r(json_encode($response));
		
	} else {
	
		mysqli_stmt_close($statement);
		//INSERT NEW USER INTO MAIN TABLE
		$statement = mysqli_prepare($conn, "INSERT INTO user_archive(user_username, user_name, user_firstname,
								   user_school, user_grade, user_email) 
								   VALUES (?, ?, ?, ?, ?, ?)");
		
		//Binding POST to Statement
		mysqli_stmt_bind_param($statement, "ssssis", $user_username, $user_name, $user_firstname, $user_school,
							   $user_grade, $user_email);
		
		mysqli_stmt_execute($statement);
		mysqli_stmt_close($statement);
		
		
		//RETREIVE USER_ID THAT HAS BEEN ASIGNED TO THE NEW USER
		$id_retrieve_statement = mysqli_prepare($conn, "SELECT user_id FROM user_archive WHERE user_username = ?"); //Checking for Username
		mysqli_stmt_bind_param($id_retrieve_statement, "s", $user_username);
		mysqli_stmt_execute($id_retrieve_statement);
		
		mysqli_stmt_store_result($id_retrieve_statement);
		mysqli_stmt_bind_result($id_retrieve_statement, $arch_id);
		
		while ($field = mysqli_stmt_fetch($id_retrieve_statement)) {
			$user_id = $arch_id;
		}
		
		mysqli_stmt_close($id_retrieve_statement);
		
		//INSERT NEW USER INTO SUBJECTS TABLE WITH DUMMY VALUES
		$subj_statement = mysqli_prepare($conn,"INSERT INTO user_subjects(user_id, subj_german, subj_spanish,
										 subj_english, subj_french, subj_biology, subj_chemistry, subj_music, 
										 subj_maths, subj_physics) VALUES (?,?,?,?,?,?,?,?,?,?)");
										 
		$placeholder = 0;
										 
		mysqli_stmt_bind_param($subj_statement, "iiiiiiiiii", $user_id, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder);
		mysqli_stmt_execute($subj_statement);
		
		mysqli_stmt_close($subj_statement);
		
		//INSERT NEW USER INTO HASH TABLE
		$statement = mysqli_prepare($conn,"INSERT INTO user_hashes(user_id, hash_password, hash_salt) VALUES (?,?,?)");
										 
		mysqli_stmt_bind_param($statement, "iss", $user_id, $hash_password, $hash_salt);
		mysqli_stmt_execute($statement);
		
		mysqli_stmt_close($statement);
		
		//INSERT NEW USER INTO PB TABLE WITH DUMMY VALUE
		$statement = mysqli_prepare($conn,"INSERT INTO user_profilepictures(user_id, blob_profilepicture_big, blob_profilepicture_small) VALUES (?,?,?)");
		
		$placeholder = 0;
		
		mysqli_stmt_bind_param($statement, "iss", $user_id, $placeholder, $placeholder);
		mysqli_stmt_execute($statement);
		
		mysqli_stmt_close($statement);
		
		$response = array();
		$response['success'] = true;
		
		print_r(json_encode($response));
	}

?>