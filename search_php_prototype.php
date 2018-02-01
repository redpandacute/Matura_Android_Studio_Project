<?php

//conn Details

 $db_serverhost = "localhost";
 $db_username = "id3512496_iadmin";
 $db_password = "redbullsf";
 $db_name = "id3512496_imatura";
 
 //Connecting to db

 $conn = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);

 if(!$conn) { echo "mysql-error: " . mysqli_connect_error() . "<br>\n";  } 

 //POST
 
 $user_name = $_POST["user_name"];
 //$user_school = $_POST["user_school"];
 
  $subj_german = $_POST["subj_german"];
  $subj_spanish = $_POST["subj_spanish"];
  $subj_english = $_POST["subj_english"];
  $subj_french = $_POST["subj_french"];
  $subj_biology = $_POST["subj_biology"];
  $subj_chemistry = $_POST["subj_chemistry"];
  $subj_music = $_POST["subj_music"];
  $subj_maths = $_POST["subj_maths"];
  $subj_physics = $_POST["subj_physics"];
  
  $statement = mysqli_prepare($conn, "SELECT user_id FROM user_archive WHERE user_name LIKE '?%' OR user_username LIKE '?%' OR user_firstname LIKE '?%'"); //TODO: Add school with "school = ? AND ..."
  mysqli_stmt_bind_param($statement, "sss", $user_name, $user_name, $user_name);
  mysqli_stmt_execute($statement);

	if ($statement) {
	
		mysqli_stmt_store_result($statement);
		mysqli_stmt_bind_result($statement, "i", $id);
	
		$results = array();
		$count_1 = 0;
		while($field = mysqli_stmt_fetch($statement)) {
			$results[$count_1] = $id;
			$count_1 = $count_1 + 1;
		}
		
		mysqli_stmt_close($statement);
		
		$final_results = array();
		//prototype
		if($subj_) { array_merge($final_results, checkSubj("", $subj_, $results); }
	}
  
function checkSubj($subj_str, $subj, $results) {

	$count_2 = 0;
	$subj_results = array();
			
	while($count_2 <= $count_1) {
		$statement = mysqli_prepare($conn, "SELECT user_id FROM user_subjects WHERE ? = ? AND user_id = ?");
		mysqli_stmt_bind_param($statement, "sii",$subj_str, $subj, $results[$count_2]);
		mysqli_stmt_execute($statement);
				
		if($statement) { $subj_results[$count_2] = $results[$count_2]; }		
		mysqli_stmt_close($statement);
		$count_2 = $count_2 + 1;
	}
	
	return $subj_results;
}
  
?>