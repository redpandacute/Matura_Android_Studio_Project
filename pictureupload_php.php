<?php

	//https://www.w3schools.com/Php/php_file_upload.asp
	function isAcceptedFile($filetype) {
		
		if($filetype != "jpeg" && $filetype != "png" && $filetype != "jpg") {
			echo ":: INVALID FILE TYPE ::";
			return false;
		}
		
		if (getimagesize($_FILES["imageFile"]["tmp_name"]) === false) {
			echo ":: INVALID IMAGE/FILE ::";
			return false;
		}
		
		if ($_FILES["imageFile"]["size"] > 5000000) {
			echo ":: INVALID FILE SIZE ::";
			return false;
		}
		return true;
	}
	
	function uploadFile($imagepath, $filename) {
		if (move_uploaded_file($_FILES["imageFile"]["tmp_name"], $imagepath . $filename)) {
			echo "FILE: ". basename( $_FILES["imageFile"]["name"]). ": UPLOAD SUCCESSFUL";
		} else {
			echo ":: UPLOAD UNSUCCESSFUL ::";
		}
	}

	$db_serverhost = "localhost";
	$db_username = "id3512496_iadmin";
	$db_password = "admin";
	$db_name = "id3512496_imatura";

	$conn = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);
	
	if(!$conn) { echo "mysql-error: " . mysqli_connect_error() . "<br>\n";  } 
	
	$user_username = $_POST["user_username"];
	$user_password = $_POST["user_password"];
	
	$statement = mysqli_prepare($conn, "SELECT user_archive.*, user_hashes.* FROM user_archive INNER JOIN user_hashes ON user_archive.user_id = user_hashes.user_id WHERE user_username = ? AND user_password = ?"); //Checking for Username
	
	mysqli_stmt_bind_param($statement, "ss", $user_username, $user_password);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	
	if(mysqli_stmt_num_rows($statement) > 0) {
		
		mysqli_stmt_bind_result($statement, $id, $username, $name, $firstname, $school, $yearofbirth, $email, $password, $description, $id, $passwordhash, $passwordsalt, $profilepicturehash);
		
		while ($field = mysqli_stmt_fetch($statement)) {
			$user_id = $id;
			$user_username = $username;
		}
		
		$filename = hash("md5", $username);
		//$filename = "GumBA";
		$imagepath = "profile_pictures/";
		$response = array();
		$success = true;
	
		if($_SERVER['REQUEST_METHOD'] == 'POST') { //Check if post has been attached
			if(isAcceptedFile(pathinfo($_FILES['imageFile']['name'])['extension'])) {
				$type = explode(".", $_FILES["imageFile"]["name"]); //Splitting the name
				$filename = $filename . '.' . end($type); //end gets the Type of the File
				uploadFile($imagepath, $filename);
			}
		} else {
			$success = false;
			echo ":: Submission failure ::";
		}
		
		mysqli_stmt_close($statement);
		$statement = mysqli_prepare($conn, "UPDATE user_hashes SET hash_profilepicture = ? WHERE user_id = ?");
		mysqli_stmt_bind_param($statement, "si", $filename, $user_id);
		mysqli_stmt_execute($statement);
		mysqli_stmt_close($statement);
		
		$statement = mysqli_prepare($conn ,"SELECT user_archive.*, user_subjects.*, user_hashes.* FROM 
		(user_archive INNER JOIN user_subjects ON user_archive.user_id = user_subjects.user_id) 
		INNER JOIN user_hashes ON user_hashes.user_id = user_archive.user_id 
		WHERE user_archive.user_id = ?");
		mysqli_stmt_bind_param($statement, "i", $id);
		mysqli_stmt_execute($statement);
		mysqli_stmt_store_result($statement);
		mysqli_stmt_bind_result($statement, $id, $username, $name, $firstname, $school, $yearofbirth, $email, $password, $description, $id, $german, $spanish, $english, $french, $biology, $chemistry, $music, $maths, $physics, $id, $passwordhash, $salt, $profilepicturehash);
		
		while ($field = mysqli_stmt_fetch($statement)) {
			$response['user_username'] = $username;
			$response['user_name'] = $name;
			$response['user_firstname'] = $firstname;
			$response['user_school'] = $school;
			$response['user_yearofbirth'] = $yearofbirth;
			$response['user_email'] = $email;
			$response['user_description'] = $description;
			$response['user_id'] = $id;
			
			$response['subj_german'] = $german;
			$response['subj_spanish'] = $spanish;
			$response['subj_english'] = $english;
			$response['subj_french'] = $french;
			$response['subj_biology'] = $biology;
			$response['subj_chemistry'] = $chemistry;
			$response['subj_music'] = $music;
			$response['subj_maths'] = $maths;
			$response['subj_physics'] = $physics;
			
			$response['hash_profilepicture'] = $profilepicturehash;
		}
		
		mysqli_stmt_close($statement);
		$response['success'] = $success;
		print_r(json_encode($response));
	} else {
		$response['success'] = false;
		print_r(json_encode($response));
	}
?>