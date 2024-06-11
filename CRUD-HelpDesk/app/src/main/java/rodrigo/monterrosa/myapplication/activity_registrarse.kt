package rodrigo.monterrosa.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.claseConexion
import java.util.UUID

class activity_registrarse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCorreo = findViewById<EditText>(R.id.txtCorreoRegis)
        val txtClave = findViewById<EditText>(R.id.txtClaveRegis)
        val btnRegistrarme = findViewById<Button>(R.id.btnRegistrarse)
        val btnStartLogin = findViewById<Button>(R.id.btnIngresarLogin)

        btnRegistrarme.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO){

                val objConexion = claseConexion().cadenaConexion()

                val registrarUsuario =
                    objConexion?.prepareStatement("INSERT INTO tbUsuarios(UUID_usuario, correoElectronico, clave) VALUES (?, ?, ?)")!!
                registrarUsuario.setString(1,UUID.randomUUID().toString())
                registrarUsuario.setString(2, txtCorreo.text.toString())
                registrarUsuario.setString(3, txtClave.text.toString())
                registrarUsuario.executeUpdate()
                withContext(Dispatchers.Main){

                    Toast.makeText(this@activity_registrarse, "Usuario registrado", Toast.LENGTH_SHORT).show()
                    txtCorreo.setText("")
                    txtClave.setText("")

                }
            }
        }

        btnStartLogin.setOnClickListener {
            val pantallaLogin = Intent(this, activity_login::class.java)
            startActivity(pantallaLogin)
        }

    }
}