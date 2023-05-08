<button style="width: 200px; height: 50px; margin-top: 5px; margin-bottom: 12px;" onclick="window.location.href = 'https://matur.cz/hostel_app/';">Перейти до проживаючих</button>
<?php
include_once 'loginData.php';

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT date, reason, sum, profit FROM hostel_acc ORDER BY id DESC";
$result = $conn->query($sql);

?>
<!DOCTYPE html>
<html>
<head>
  <title>Бухгалтерія</title>
  <style>
    table {
      border-collapse: collapse;
      width: 100%;
      max-width: 800px;
    }

    th, td {
      padding: 8px;
      text-align: left;
      border: 1px solid #ddd;
      font-size: 14px;
    }

    th {
      background-color: #f2f2f2;
      font-weight: bold;
    }

    /* добавляем стили для закрашивания цветом */
    .green {
      background-color: #8BC34A;
    }

    .orange {
      background-color: #FF9800;
    }
  </style>
</head>
<body>
  <table>
    <thead>
      <tr>
      <th style="width: 10%;">Дата:</th>
      <th style="width: 40%;">Причина:</th>
      <th style="width: 15%;">Сумма:</th>
      <th style="width: 20%;">Прибуток/витрати:</th>
    </tr>
  </thead>
  <tbody>
    <?php
    if ($result->num_rows > 0) {
      // output data of each row
      while($row = $result->fetch_assoc()) {
        if($row["profit"] == 1){
            $profit = "Прибуток";
            $class = 'green';
        } else {
            $profit = "Витрати";
            $class = 'orange';
        }
        echo "<tr class='" . $class . "'><td>" . $row["date"]. "</td><td>" . $row["reason"]. "</td><td>" . $row["sum"]. "</td><td>" . $profit. "</td></tr>";
      }
    } else {
      echo "<tr><td colspan='5'>0 results</td></tr>";
    }
    ?>
  </tbody>
</table>

<?php
$conn->close();
?>
