package modelo

import java.sql.Connection
import java.sql.DriverManager

class claseConexion {

    fun cadenaConexion(): Connection?{
        try {
            val ip = "jdbc:oracle:thin:@192.168.0.13:1521:xe"
            val usuario = "system"
            val contrasena = "monterrosa2007"

            val conexion = DriverManager.getConnection(ip, usuario, contrasena)
            return conexion
        }catch (e: Exception){
            println("Este es el error: $e")
            return null
        }
    }

}