<?php


	//conn Details

	$db_serverhost = "sql530.your-server.de";
	$db_username = "efxinf_7";
	$db_password = "LxkLYb23hC7nKp4X";
	$db_name = "efxinf_db7";
 
	//Connecting to db
	
	$conn = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);
 
	if(!$conn) { echo "mysql-error: " . mysqli_connect_error() . "<br>\n";  }
 
	//POST for user_archive
  
  $user_id = $_POST["user_id"];
  $user_firstname = $_POST["user_firstname"];
  $user_name = $_POST["user_name"];
  $user_email = $_POST["user_email"];
  $user_school = $_POST["user_school"];
  $user_description = $_POST["user_description"];
  
  $subj_german = $_POST["subj_german"];
  $subj_spanish = $_POST["subj_spanish"];
  $subj_english = $_POST["subj_english"];
  $subj_french = $_POST["subj_french"];
  $subj_biology = $_POST["subj_biology"];
  $subj_chemistry = $_POST["subj_chemistry"];
  $subj_music = $_POST["subj_music"];
  $subj_maths = $_POST["subj_maths"];
  $subj_physics = $_POST["subj_physics"];
  
	//UPDATE in user_archive
	$update_stmt = mysqli_prepare($conn, "UPDATE user_archive SET user_firstname = ?, user_name = ?, user_email = ?,
										  user_school = ?, user_description = ? WHERE user_id = ?");
					
	if(!$update_stmt) { echo "mysql-error: " . mysqli_connect_error() . "<br>\n";  }
					
	mysqli_stmt_bind_param($update_stmt, "sssssi", $user_firstname, $user_name, $user_email, $user_school, $user_description, $user_id);
	mysqli_stmt_execute($update_stmt);
  
	mysqli_stmt_close($update_stmt);
	
	//getting updated user
	$select_stmt = mysqli_prepare($conn, "SELECT * FROM user_archive WHERE user_id = ?");
	mysqli_stmt_bind_param($select_stmt, "i", $user_id);
	mysqli_stmt_execute($select_stmt);
  
	mysqli_stmt_store_result($select_stmt);
	mysqli_stmt_bind_result($select_stmt, $id, $username, $name, $firstname, $school, $yearofbirth, $email, $password, $description);
	
		
		$response = array();
		$response['success_user'] = true;
		
		while ($field = mysqli_stmt_fetch($select_stmt)) {
			$response['user_username'] = $username;
			$response['user_name'] = $name;
			$response['user_firstname'] = $firstname;
			$response['user_school'] = $school;
			$response['user_yearofbirth'] = $yearofbirth;
			$response['user_email'] = $email;
			$response['user_description'] = $description;
			$response['user_password'] = $password;
			$response['user_id'] = $id;
		}
	
	mysqli_stmt_close($select_stmt);
	
	//UPDATE in user_subjects
	$update_stmt = mysqli_prepare($conn, "UPDATE user_subjects SET subj_german = ?, subj_spanish = ?,
											subj_english = ?, subj_french = ?, subj_biology = ?, subj_chemistry = ?, 
											subj_music = ?, subj_maths = ?, subj_physics = ? WHERE user_id = ?");
											
	if(!$conn) { echo "mysql-error: " . mysqli_connect_error() . "<br>\n";  }
					
	mysqli_stmt_bind_param($update_stmt, "iiiiiiiiii", $subj_german, $subj_spanish, $subj_english, $subj_french, 
						   $subj_biology, $subj_chemistry, $subj_music, $subj_maths, $subj_physics, $id);
	mysqli_stmt_execute($update_stmt);
	
	mysqli_stmt_close($update_stmt);
	
	$select_stmt = mysqli_prepare($conn, "SELECT * FROM user_subjects WHERE user_id = ?");
	mysqli_stmt_bind_param($select_stmt, "i", $id);
	mysqli_stmt_execute($select_stmt);
	
	mysqli_stmt_store_result($select_stmt);
	mysqli_stmt_bind_result($select_stmt, $id, $german, $spanish, $english, $french, $biology, $chemistry, $music, $maths, $physics);
	
		$response['success_subjects'] = true;
	
		while ($field = mysqli_stmt_fetch($select_stmt)) {
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
	
	mysqli_stmt_close($select_stmt);
	
	print_r(json_encode($response));
?>