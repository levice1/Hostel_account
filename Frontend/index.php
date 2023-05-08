<button style="width: 200px; height: 50px; margin-top: 5px; margin-bottom: 12px;" onclick="window.location.href = 'https://matur.cz/hostel_app/accounting.php';">Перейти в бухгалтерію</button>

<?php
include_once 'loginData.php';

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT guestName, roomNumber, liveFrom, liveTo, usPeople, addInfo FROM hostel_peoples ORDER BY roomNumber ASC";
$result = $conn->query($sql);
$count = 0; // обнуляем счетчик

?>
<!DOCTYPE html>
<html>
<head>
  <title>Проживаючі</title>
<style>
  table {
    border-collapse: collapse;
    width: 100%;
    max-width: 800px; /* ограничиваем ширину таблицы для удобства чтения */
  }

  th, td {
    padding: 8px;
    text-align: center;
    border: 1px solid #ddd;
    font-size: 14px; /* увеличиваем размер шрифта для удобства чтения */
  }

  th {
    background-color: #f2f2f2;
    font-weight: bold;
  }
      /* добавляем стили для закрашивания цветом */
      .white {
      background-color: #FFFFFF;
    }

    .grey {
      background-color: #e8fce5;
    }
</style>

<table>
  <thead>
    <tr>
      <th>#</th>
      <th style="width: 25%;">Імʼя</th>
      <th style="width: 5%;">Номер кімнати</th>
      <th style="width: 12%;">Проживає від:</th>
      <th style="width: 12%;">Заплачено до:</th>
      <th style="width: 8%;">Наш працівник</th>
      <th style="width: 30%;">Додаткова інформація:</th>
    </tr>
  </thead>
  <tbody>
    <?php
    if ($result->num_rows > 0) {
      // output data of each row
      while($row = $result->fetch_assoc()) {
            if($row["usPeople"] == 1){
                $usMan = "Так";
                $class = 'white';
            } else {
                $usMan = "Hi";
                $class = 'grey';
            }
        $count++; // увеличиваем счетчик
        echo "<tr class='" . $class . "'><td>" . $count . "</td><td>" . $row["guestName"]. "</td><td>" . $row["roomNumber"]. "</td><td>" . $row["liveFrom"]. "</td><td>" . $row["liveTo"]. "</td><td>" . $usMan. "</td><td>" . $row["addInfo"]. "</td></tr>";
      }
    } else {
      echo "<tr><td colspan='7'>0 results</td></tr>";
    }
    ?>
  </tbody>
</table>

<?php
$conn->close();
?>
