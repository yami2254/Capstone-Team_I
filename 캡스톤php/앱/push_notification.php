<?php
// API access key from Google API's Console
define( 'API_ACCESS_KEY', 'AAAA7fMJ-C4:APA91bEf7YTcNjhl5AbECJB9M87LgZY3xBTaGFR_RvJG6-ZJoh-mjbBCDIH0yvGaGl3erMjBCBBPWi6RDLcLli_8VmUo2BAIMupyKtaH_h6NmpeQSc1DiKa2kelKWv9bX7glpWz-SyRt' );

// prep the bundle
$msg = array
(
    'body'  => "확인해보세요",
    'title'     => "꽁터디룸이 생겼습니다",
    'vibrate'   => 1,
    'sound'     => 1,
);

$fields = array
(
    'to'  => '/topics/fcm-test',
    'notification'          => $msg
);

$headers = array
(
    'Authorization: key=' . API_ACCESS_KEY,
    'Content-Type: application/json'
);

$ch = curl_init();
curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
curl_setopt( $ch,CURLOPT_POST, true );
curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
$result = curl_exec($ch );
curl_close( $ch );
echo $result;
?>


