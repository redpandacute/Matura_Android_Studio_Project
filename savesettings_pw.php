<?php


//conn Details

 $db_serverhost = "localhost";
 $db_username = "id3512496_iadmin";
 $db_password = "redbullsf";
 $db_name = "id3512496_imatura";
 
 //Connecting to db

 $conn = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);

 if(!$conn) { echo "mysql-error: " . mysqli_connect_error() . "<br>\n";  } 
 
 //POST for user_archive
  
  $user_id = $_POST["user_id"];
  $user_firstname = $_POST["user_firstname"];
  $user_name = $_POST["user_name"];
  $user_oldpw = $_POST["user_oldpassword"];
  $user_newpw = $_POST["user_newpassword"];
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
  
  //Checking password
  $statement = mysqli_prepare($conn, "SELECT * FROM user_archive WHERE user_id = ? AND user_password = ?");
  
  if(!$statement) { echo "mysql-error: " . mysqli_errno($conn) . "<br>\n";  } 
  
  mysqli_stmt_bind_param($statement, "is", $user_id, $user_oldpw);
  mysqli_stmt_execute($statement);
  
  if($statement) {
		
		mysqli_stmt_close($statement);
		
		//UPDATE in user_archive
		$update_statement = mysqli_prepare($conn, "UPDATE user_archive SET user_firstname = ?, user_name = ?, user_password = ?, user_email = ?, user_school = ?, user_description = ? WHERE user_id = ?");
										
		if(!$update_statement) { echo "mysql-error: " . mysqli_errno($conn) . "<br>\n";  } 
							  
		mysqli_stmt_bind_param($update_statement, "ssssssi", $user_firstname, $user_name, $user_newpw, $user_email, $user_school, $user_description, $user_id);
		mysqli_stmt_execute($update_statement);
		mysqli_stmt_close($update_statement);
  
  
		//getting updated user
		$select_statement = mysqli_prepare($conn, "SELECT * FROM user_archive WHERE user_id = ?");
		
		if(!$select_statement) { echo "mysql-error: " . mysqli_errno($conn) . "<br>\n";  } 
		
		mysqli_stmt_bind_param($select_statement, "s", $user_id);
		mysqli_stmt_execute($select_statement);
  
		mysqli_stmt_store_result($select_statement);
		mysqli_stmt_bind_result($select_statement, $id, $username, $name, $firstname, $school, $yearofbirth, $email, $password, $description);
	    
		 
		$response = array();
		$response['success_user'] = true;
		
		while ($field = mysqli_stmt_fetch($select_statement)) {
			$response['user_username'] = $username;
			$response['user_name'] = $name;
			$response['user_firstname'] = $firstname;
			$response['user_school'] = $school;
			$response['user_yearofbirth'] = $yearofbirth;
			$response['user_email'] = $email;
			$response['user_description'] = $description;
			$response['user_id'] = $id;
		}
		
		mysqli_stmt_close($select_statement);
		
		//UPDATE in user_subjects
		$update_subj_statement = mysqli_prepare($conn, "UPDATE user_subjects SET subj_german = ?, subj_spanish = ?,subj_english = ?, subj_french = ?, subj_biology = ?, subj_chemistry = ?, subj_music = ?, subj_maths = ?, subj_physics = ? WHERE user_id = ?");
											
		if(!$update_subj_statement) { echo "mysql-error: " . mysqli_errno($conn) . "<br>\n";  }
											
		mysqli_stmt_bind_param($update_subj_statement, "iiiiiiiiii", $subj_german, $subj_spanish, $subj_english, $subj_french, $subj_biology, $subj_chemistry, $subj_music, $subj_maths, $subj_physics, $user_id);
		
		mysqli_stmt_execute($update_subj_statement);
		mysqli_stmt_close($update_subj_statement);
	
		$select_subj_statement = mysqli_prepare($conn, "SELECT * FROM user_subjects WHERE user_id = ?");
		
		if(!$select_subj_statement) { echo "mysql-error: " . mysqli_errno($conn) . "<br>\n";  }
		
		mysqli_stmt_bind_param($select_subj_statement, "i", $user_id);
		mysqli_stmt_execute($select_subj_statement);
	
		mysqli_stmt_store_result($select_subj_statement);
		mysqli_stmt_bind_result($select_subj_statement, $id, $german, $spanish, $english, $french, $biology, $chemistry, $music, $maths, $physics);
		
	
			$response['success_subjects'] = true;
	
			while ($field = mysqli_stmt_fetch($select_subj_statement)) {
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
		
		mysqli_stmt_close($select_subj_statement);
		
	//responding		
	print_r(json_encode($response));
	
	} else {
		$response = array();
		$response['success'] = false;
		
	print_r(json_encode($response));
	}	
?>