package com.example.has_apps

import com.example.has_apps.Pertemuan_4.FourthActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.has_apps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        val btnKirm : Button = findViewById(R.id.btnKirim)
//        val noTujuan : EditText = findViewById(R.id.inputNoTujuan)

        binding.btnToFourth.setOnClickListener {
            val btnToFourth = binding.btnToFourth.text
            Toast.makeText(this,"Pesan berhasil dikirim ke $btnToFourth" , Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FourthActivity::class.java)

            /*tambahkan bagian berikut*/
            intent.putExtra("name", "nama")
            intent.putExtra("from", "asal")
            intent.putExtra("age", 25)


            startActivity(intent)
        }
    }
}