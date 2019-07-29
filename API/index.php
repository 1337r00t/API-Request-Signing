<?php
header("Content-Type: application/json; charset=UTF-8");
if($_SERVER['REQUEST_METHOD'] == 'GET'){
	$signed_params_from_app = $_SERVER['HTTP_SIGNED']; // 'Signed' Request Header
	$plain_params = $_SERVER['HTTP_PARAMS']; // 'Params' Request Header
	$signed_params_from_api = base64_encode(hash_hmac('sha1',$plain_params,'123456',true));
	if($signed_params_from_api == $signed_params_from_app){
	    echo json_encode(array('output'=>'Correct Request :)'));
	}else{
		echo json_encode(array('output'=>'Incorrect Request :('));
		exit(http_response_code(401));
	}
}else{
	exit(http_response_code(405));
}
?>
