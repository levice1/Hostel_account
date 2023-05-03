<?php

function SendMsgTG($txtMsg)
{ //---------> відправка відповіді боту Телеграм
    include_once 'loginData.php';
    $params = array(
        'chat_id' => $chatId, // id получателя сообщения
        'text' => $txtMsg, // текст сообщения
    );
    $curl = curl_init();
    curl_setopt($curl, CURLOPT_URL, 'https://api.telegram.org/bot'.$bottoken.'/sendMessage'); // адрес api телеграмм
    curl_setopt($curl, CURLOPT_POST, true); // отправка данных методом POST
    curl_setopt($curl, CURLOPT_TIMEOUT, 10); // максимальное время выполнения запроса
    curl_setopt($curl, CURLOPT_POSTFIELDS, $params); // параметры запроса
    $result = curl_exec($curl); // запрос к api
    curl_close($curl);
}


function queryToDb($sql){
// Подключаемся к базе данных

include_once 'loginData.php';
$mysql = new mysqli($servername, $username, $password, $dbname);

$mysql -> query("SET NAMES 'UTF-8'");

// Выполняем запрос к базе данных
$result = $mysql->query($sql);

// Проверяем, есть ли ошибки при выполнении запроса
if ($mysql->errno) {
    // Ошибка выполнения запроса
    $errorMessage = "Ошибка MySQL: (" . $mysql->errno . ") " . $mysql->error;
    SendMsgTG($errorMessage);
} else {
    // Запрос выполнен успешно
    echo "Данные успешно записаны в базу данных!";
}

$mysql -> close();

}




?>
