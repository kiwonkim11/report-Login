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
    var DB: DB? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DB = DB(this)

        binding.btnLogin.setOnClickListener {
            val etId = binding.etLogid.text.toString()
            val etPw = binding.etLogpw.text.toString()

            // 아이디 or 비밀번호를 입력하지 않는 경우
            if (etId.isEmpty() || etPw.isEmpty()) {
                Toast.makeText(this, "아이디/비밀번호를 입력해야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val checkUserpass = DB!!.checkUserpass(etId, etPw)

                // 아이디와 비밀번호가 DB에 있는 id, pw와 일치할 경우
                if (checkUserpass) {
                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()

                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

            }

        }

        binding.btnRes.setOnClickListener {
            intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}