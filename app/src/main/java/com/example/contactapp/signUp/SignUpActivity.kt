package com.example.contactapp.signUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.contactapp.R
import com.example.contactapp.signIn.SignInActivity
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private val emailaddressPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)\$")
    private val pwPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#\$%^&+=]).{8,15}\$")
    private val localePattern = Pattern.compile("^[가-힣a-zA-Z]*\$")
    private val namePattern = Pattern.compile("^[가-힣a-zA-Z]*\$")
    private val telPattern = Pattern.compile("^[0-9]{10,11}\$")
    private var imgSet: Int = R.drawable.logo1 // 기본 값으로 초기화

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val et_name = findViewById<EditText>(R.id.et_name)
        val et_emailaddress = findViewById<EditText>(R.id.et_emailaddress)
        val et_pw = findViewById<EditText>(R.id.et_pw)
        val et_locale = findViewById<EditText>(R.id.et_locale)

        val et_tel = findViewById<EditText>(R.id.et_tel)
        val btn_signUp = findViewById<Button>(R.id.btn_signupOk)
        val btn_signCancel = findViewById<Button>(R.id.btn_signupcancel)




        et_emailaddress.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val emailaddress = s.toString()
                val valid = emailaddressPattern.matcher(emailaddress).matches()
                if (!valid) {
                    et_emailaddress.error = getString(R.string._5_10)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        et_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val pw = s.toString()
                val valid = pwPattern.matcher(pw).matches()
                if (!valid) {
                    et_pw.error = getString(R.string._8_15)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        et_locale.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val locale = s.toString()
                val valid = localePattern.matcher(locale).matches()
                if (!valid) {
                    et_locale.error = getString(R.string.kor)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        et_tel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val tel = s.toString()
                val valid = telPattern.matcher(tel).matches()
                if (!valid) {
                    et_tel.error = getString(R.string._10_11)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        et_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val name = s.toString()
                val valid = namePattern.matcher(name).matches()
                if (!valid) {
                    et_name.error = getString(R.string.kor)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        //이미지 초기화
//        val iv_logo = findViewById<ImageView>(R.id.imageView)
//        iv_logo.setOnClickListener {
//            imgSet = when ((1..6).random()) {
//                1 -> R.drawable.logo1
//                2 -> R.drawable.logo2
//                3 -> R.drawable.logo3
//                4 -> R.drawable.logo4
//                5 -> R.drawable.logo5
//                else -> R.drawable.logo1
//            }
//
//            iv_logo.setImageDrawable(ResourcesCompat.getDrawable(resources, imgSet, null))
//
//        }

        btn_signUp.setOnClickListener {
            val name = et_name.text.toString()
            val emailaddress = et_emailaddress.text.toString()
            val pw = et_pw.text.toString()
            val locale = et_locale.text.toString()

            val tel = et_tel.text.toString()

            if (name.isBlank() || emailaddress.isBlank() || pw.isBlank() || locale.isBlank() || tel.isBlank()) {
                Toast.makeText(this, getString(R.string.info), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val nameValid = namePattern.matcher(name).matches()
            val emailaddressValid = emailaddressPattern.matcher(emailaddress).matches()
            val pwValid = pwPattern.matcher(pw).matches()
            val localeValid = localePattern.matcher(locale).matches()
            val telValid = telPattern.matcher(tel).matches()

            if (!nameValid) {
                et_name.error = getString(R.string.kor)
                return@setOnClickListener
            }

            if (!emailaddressValid) {
                et_emailaddress.error = getString(R.string._5_10)
                return@setOnClickListener
            }

            if (!pwValid) {
                et_pw.error = getString(R.string._8_15)
                return@setOnClickListener
            }

            if (!localeValid) {
                et_locale.error = getString(R.string.kor)
                return@setOnClickListener
            }

            if (!telValid) {
                et_tel.error = getString(R.string._10_11)
                return@setOnClickListener
            }

            val intent = Intent(this, SignInActivity::class.java)

            //수정하겠습니다.
            intent.putExtra("userName", name)
            intent.putExtra("userEmailAddress", emailaddress)
            intent.putExtra("userPw", pw)
            intent.putExtra("userTel", tel)
            intent.putExtra("userLocale", locale)

            intent.putExtra("userImage",imgSet)

            startActivity(intent)
            finish()
        }

        btn_signCancel.setOnClickListener {
            Toast.makeText(this@SignUpActivity, "취소 되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }


    }


}