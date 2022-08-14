package rs.raf.projekat2milica_bakic_rn342018.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        if (sharedPreferences.getString("username", "") == "") {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        finish()


    }
}
