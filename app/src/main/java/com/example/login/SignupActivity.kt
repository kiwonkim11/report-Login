package com.example.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login.databinding.ActivitySignupBinding
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    var DB: DB? = null
    var CheckId: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DB = DB(this)

        // 패턴
        val idPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{6,15}$"
        val phonePattern = "^(\\+[0-9]+)?[0-9]{10,15}$"

        // 아이디 형식 & 중복검사
        binding.btnIdcheck.setOnClickListener {
            val user = binding.etResid.text.toString()

            if (user.isEmpty()) {
                Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                if (Pattern.matches(idPattern, user)) {
                    val checkUsername = DB!!.checkUser(user)

                    // 아이디가 중복이 아닐 때
                    if (!checkUsername) {
                        CheckId = true
                        binding.tvUsableId.visibility = View.VISIBLE
                        binding.tvDupeId.visibility = View.GONE
                    }

                    // 아이디가 중복일 때
                    else {
                        binding.tvDupeId.visibility = View.VISIBLE
                        binding.tvUsableId.visibility = View.GONE
                    }
                }

                // 아이디 형식이 맞지 않을 때
                else {
                    Toast.makeText(this, "아이디 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 비밀번호 패턴 확인
        binding.etRespw.addTextChangedListener(object: TextWatcher {
            // 비밀번호에 문자 입력 전
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            // 비밀번호에 변화가 있을 경우
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            val pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,15}$"

            // 비밀번호 입력이 끝난 후
            override fun afterTextChanged(p0: Editable?) {
                if(Pattern.matches(pwPattern, binding.etRespw.text.toString())){
                    binding.tvPwPattern.visibility = View.GONE
                }
                else
                    binding.tvPwPattern.visibility = View.VISIBLE
            }
        })

        // 비밀번호 일치 여부
        binding.etRespwCheck.addTextChangedListener(object: TextWatcher {
            // 비밀번호 확인에 문자 입력 전
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            // 비밀번호 확인에 변화가 있을 경우
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            // 비밀번호 확인 입력이 끝난 후
            override fun afterTextChanged(p0: Editable?) {
                if(binding.etRespw.getText().toString().equals(binding.etRespwCheck.getText().toString())){
                    binding.tvMismatchPw.visibility = View.GONE
                }
                else
                    binding.tvMismatchPw.visibility = View.VISIBLE
            }
        })


        // 가입완료를 눌렀을 때
        binding.btnSignup.setOnClickListener {
            val user = binding.etResid.text.toString()
            val pass = binding.etRespw.text.toString()
            val passcheck = binding.etRespwCheck.text.toString()
            val phone = binding.etResphone.text.toString()

            if (user.isEmpty() || pass.isEmpty() || passcheck.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "회원정보를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                // 아이디 중복이 확인 되었을 때
                if (CheckId) {
                    // 휴대폰번호 형식이 맞을 때
                    if (Pattern.matches(phonePattern, phone)) {
                        val insert = DB!!.insertData(user, pass, phone)

                        // 가입 성공 시 Toast를 띄우고 메인 화면으로 전환
                        if (insert) {
                            Toast.makeText(this, "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        }

                        // 가입 실패 시
                        else {
                            Toast.makeText(this, "가입 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    // 휴대폰 형식이 맞지 않을 때
                    else {
                        Toast.makeText(this, "전화번호 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                // 아이디 중복확인이 되지 않았을 때
                else {
                    Toast.makeText(this, "아이디 중복확인을 해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}