<?php
ini_set('log_errors', 'On');
ini_set('error_log', 'check_err.log');

include_once "func.php";


// Получаем данные из POST-запроса

$data = json_decode(file_get_contents('php://input'), true);


$process = $data['process'];

SendMsgTG("$process");

switch($process){

    case 'insertP':
        $id = $data['id'];

        $roomNumber = $data['roomNumber'];

        $guestName = $data['guestName'];
        $guestName = trim($guestName);

        $liveFrom = $data['liveFrom'];

        $liveTo = $data['liveTo'];

        $usPeople = $data['usPeople'];
        if($usPeople == 'true') {
            $usPeople = 1; }
            else { $usPeople = 0;}

        $addInfo = $data['addInfo'];

        $sql = "INSERT INTO hostel_peoples (id, roomNumber, guestName, liveFrom, liveTo, usPeople, addInfo)
            VALUES ($id, $roomNumber, '$guestName', '$liveFrom', '$liveTo', '$usPeople', '$addInfo')
            ON DUPLICATE KEY UPDATE
            roomNumber = VALUES(roomNumber),
            guestName = VALUES(guestName),
            liveFrom = VALUES(liveFrom),
            liveTo = VALUES(liveTo),
            usPeople = VALUES(usPeople),
            addInfo = VALUES(addInfo)";
        queryToDb($sql);
        break;

    case 'deleteP':
        $id = $data['id'];
        $sql = "DELETE FROM hostel_peoples WHERE id = $id";
        queryToDb($sql);
        break;

    case 'insertA':

        $id = $data['id'];

        $date = $data['date'];

        $reason = $data['reason'];
        $reason = trim($reason);

        $sum = $data['sum'];

        $profit = $data['profit'];
        if($profit == 'true'){
            $profit = 1;
        } else { $profit = 0; }

        $sql = "INSERT INTO hostel_acc (id, date, reason, sum, profit)
            VALUES ($id, '$date', '$reason', '$sum', '$profit')
            ON DUPLICATE KEY UPDATE
            date = VALUES(date),
            reason = VALUES(reason),
            sum = VALUES(sum),
            profit = VALUES(profit)";
        queryToDb($sql);
        break;

    case 'deleteA':
        $id = $data['id'];
        $sql = "DELETE FROM hostel_acc WHERE id = $id";
        queryToDb($sql);
        break;

}
?>