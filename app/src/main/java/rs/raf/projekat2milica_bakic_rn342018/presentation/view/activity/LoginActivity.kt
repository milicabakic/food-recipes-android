package rs.raf.projekat2milica_bakic_rn342018.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import rs.raf.projekat2milica_bakic_rn342018.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val password: String = "projekat2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        println("INIT")
        initListeners()
    }

    private fun initListeners(){

       binding.buttonLogin.setOnClickListener {
           val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
           val username: String = binding.inputUsername.getText().toString()
           val password: String = binding.inputPassword.getText().toString()

           //provera validnosti upisanih podataka
           if (username == "" || password == "") {
               Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
           } else if (password.length < 5) {
               Toast.makeText(this, "Password must be at least 5 characters", Toast.LENGTH_SHORT).show()
           } else if (password != this.password) {
               Toast.makeText(this, "Username and password don't match", Toast.LENGTH_SHORT).show()
           } else {

               sharedPreferences.edit().putString("username", username)
                       .putString("password", password)
                       .apply()
               println("username " + sharedPreferences.getString("username", ""))
               println("password " + sharedPreferences.getString("password", ""))

               val intent = Intent(this, MainActivity::class.java)
               startActivity(intent)
               finish()
           }

       }

    }
}