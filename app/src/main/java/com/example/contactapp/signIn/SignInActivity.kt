package com.example.contactapp.signIn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.contactapp.R
import com.example.contactapp.main.MainActivity
import com.example.contactapp.signUp.SignUpActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var editTextEmailAddress: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: TextView
    private lateinit var buttonSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        /*Glide 배경 움짤 추가 */
        val loginGlide = findViewById<ImageView>(R.id.login_glide)
        Glide.with(this).load(R.raw.login_background).into(loginGlide)
        //

        editTextEmailAddress = findViewById(R.id.et_login_id)
        editTextPassword = findViewById(R.id.et_login_password)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonSignUp = findViewById(R.id.buttonSignUp)

        buttonLogin.setOnClickListener {

            val login_name = editTextEmailAddress.text.toString()   // 이름, 암호명으로 로그인 변경 작업
            val password = editTextPassword.text.toString()

            val checkName = intent.getStringExtra("userName") ?: "name"
            val checkEmailAddress = intent.getStringExtra("userEmailAddress") ?: "emailaddress"
            val checkPw = intent.getStringExtra("userPw") ?: "pw"
            val checkTel = intent.getStringExtra("userTel") ?: "tel"
            val checkLocale = intent.getStringExtra("userLocale") ?: "locale"
            val checkAbility = intent.getStringExtra("userAbility") ?: "ability"
            val checkImage = intent.getIntExtra("userImage", 0)


            if (login_name.isEmpty() || password.isEmpty()) {


                Toast.makeText(this, "아이디/비밀번호 둘 중 하나가 입력이 비어있습니다.", Toast.LENGTH_SHORT).show()
            } else if((login_name.equals(checkName)) && (password.equals(checkLocale))){

                Toast.makeText(this, R.string.successLogin, Toast.LENGTH_SHORT).show()


                val intent = Intent(this, MainActivity::class.java)

                intent.putExtra("userName", checkName)
                intent.putExtra("userEmailAddress", checkEmailAddress)
                intent.putExtra("userPw", checkPw)
                intent.putExtra("userTel", checkTel)
                intent.putExtra("userAbility", checkAbility)
                intent.putExtra("userLocale", checkLocale)
                intent.putExtra("userImage", checkImage)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, R.string.checkIDPassword, Toast.LENGTH_SHORT).show()
            }
        }

        buttonSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}