package com.example.user.foodhandbook.activity.ultil;

/**
 * Created by User on 22/01/2018.
 */

public class Server {
   public static String localhost = "192.168.1.68:8080";
   // public static String localhost = "127.0.0.1:8080";
   // public static String localhost = "prohibitionary-jaw.000webhostapp.com/";
    public static String Duongdanloaimon = "http://" + localhost + "/server/getloaimon.php";
    public static String Duongdandanhsachmontheoloai = "http://" + localhost + "/server/getmonantheoloai.php?page=";
    public static String Duongdanmon = "http://" + localhost + "/server/getmonan.php";
    public static String Duongdantienich = "http://" + localhost + "/server/gettienichtheomonan.php";
}