package com.example.contactapp.signIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    private lateinit var editTextName: EditText
    private lateinit var editTextLocale: EditText
    private lateinit var buttonLogin: TextView
    private lateinit var buttonSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        /*Glide 배경 움짤 추가 */
        val loginGlide = findViewById<ImageView>(R.id.login_glide)
        Glide.with(this).load(R.raw.login_background).into(loginGlide)
        //

        editTextName = findViewById(R.id.et_login_name)
        editTextLocale = findViewById(R.id.et_login_locale)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonSignUp = findViewById(R.id.buttonSignUp)

        buttonLogin.setOnClickListener {

            val checkName = intent.getStringExtra("userName") ?: "name"
            val checkEmailAddress = intent.getStringExtra("userEmailAddress") ?: "emailaddress"
            val checkTel = intent.getStringExtra("userTel") ?: "tel"
            val checkLocale = intent.getStringExtra("userLocale") ?: "locale"
            val checkAbility = intent.getStringExtra("userAbility") ?: "ability"

            val loginName = editTextName.text.toString()   // 이름, 암호명으로 로그인 변경 작업
            val loginLocale = editTextLocale.text.toString()

            if (loginName.isEmpty() || loginLocale.isEmpty()) {
                Toast.makeText(this, "이름/암호명 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if((loginName == checkName) && (loginLocale == checkLocale)){
                Toast.makeText(this, R.string.successLogin, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("userName", checkName)
                intent.putExtra("userEmailAddress", checkEmailAddress)
                intent.putExtra("userTel", checkTel)
                intent.putExtra("userAbility", checkAbility)
                intent.putExtra("userLocale", checkLocale)
                startActivity(intent)
                finish()
            } else{
                Toast.makeText(this, R.string.checkNameLocale, Toast.LENGTH_SHORT).show()
            }
        }

        buttonSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}