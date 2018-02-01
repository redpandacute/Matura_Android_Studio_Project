<?php

	//Connection
	$db_serverhost = "localhost";
	$db_username = "id3512496_iadmin";
	$db_password = "redbullsf";
	$db_name = "id3512496_imatura";
	
	$conn = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);
	
	$user_username = $_POST["user_username"];
	$user_password = $_POST["user_password"];
	
	$statement = mysqli_prepare($conn, "SELECT * FROM user_archive WHERE user_username = ? AND user_password = ?"); //Checking for Username
	mysqli_stmt_bind_param($statement, "ss", $user_username, $user_password);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);

	
	if(mysqli_stmt_num_rows($statement) > 0) {
		
		
		mysqli_stmt_bind_result($statement, $id, $username, $name, $firstname, $school, $yearofbirth, $email, $password, $description);
		
		$response = array();
		$response['success'] = true;
		
		while ($field = mysqli_stmt_fetch($statement)) {
			$response['user_username'] = $username;
			$response['user_name'] = $name;
			$response['user_firstname'] = $firstname;
			$response['user_school'] = $school;
			$response['user_yearofbirth'] = $yearofbirth;
			$response['user_email'] = $email;
			$response['user_description'] = $description;
			$response['user_id'] = $id;
		}
			
		mysqli_stmt_close($statement);
		$subj_statement = mysqli_prepare($conn, "SELECT * FROM user_subjects WHERE user_id = ?");
		mysqli_stmt_bind_param($subj_statement, "i", $id);
		mysqli_stmt_execute($subj_statement);
		
		mysqli_stmt_store_result($subj_statement);
		mysqli_stmt_bind_result($subj_statement, $id, $german, $spanish, $english, $french, $biology, $chemistry, $music, $maths, $physics);
		
		while ($field = mysqli_stmt_fetch($subj_statement)) {
				$response['subj_german'] = $german;
				$response['subj_spanish'] = $spanish;
				$response['subj_english'] = $english;
				$response['subj_french'] = $french;
				$response['subj_biology'] = $biology;
				$response['subj_chemistry'] = $chemistry;
				$response['subj_music'] = $music;
				$response['subj_maths'] = $maths;
				$response['subj_physics'] = $physics;
			}
			
		mysqli_stmt_close($subj_statement);
			
		print_r(json_encode($response));
		
	} else {
		//Username or password is incorrect
		$response['success'] = false;
		
		print_r(json_encode($response));
	}
?>	