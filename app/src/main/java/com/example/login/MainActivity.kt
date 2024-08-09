package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.login.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            var etId = binding.etLogid
            var etPw = binding.etLogpw

            if(etId.text.isEmpty() || etPw.text.isEmpty()) {
                Toast.makeText(this, "아이디/비밀번호를 입력해야 합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRes.setOnClickListener {
            intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}