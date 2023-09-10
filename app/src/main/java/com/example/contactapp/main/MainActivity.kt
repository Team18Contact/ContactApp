package com.example.contactapp.main

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.contactapp.R
import com.example.contactapp.contact.Constants.convertToBitmap
import com.example.contactapp.contact.Constants.convertToUri
import com.example.contactapp.contact.ContactListFragment
import com.example.contactapp.contact.ContactModel
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.databinding.DialogAddContactBinding
import com.example.contactapp.detail.DetailFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dialogBinding: DialogAddContactBinding
    private val viewPager2Adapter by lazy {
        MainViewPagerAdapter(this@MainActivity)
    }
    private val contactListFragment by lazy {
        viewPager2Adapter.getFragment(0) as? ContactListFragment
    }

    private var galleryUri: Uri = convertToUri(R.drawable.ic_empty_user)
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            galleryUri = result.data?.data ?: convertToUri(R.drawable.ic_empty_user)
            dialogBinding.imgProfile.setImageBitmap(convertToBitmap(this, galleryUri))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission()
    }

    private fun initView() = with(binding) {
        val checkName = intent.getStringExtra("userName") ?: "name"
        val checkEmailAddress = intent.getStringExtra("userEmailAddress") ?: "emailaddress"
        val checkTel = intent.getStringExtra("userTel") ?: "tel"
        val checkLocale = intent.getStringExtra("userLocale") ?: "locale"
        val checkAbility = intent.getStringExtra("userAbility") ?: "ability"

        val detailFragment = viewPager2Adapter.getFragment(1) as? DetailFragment
        detailFragment?.setData(ContactModel(convertToUri(R.drawable.ic_empty_user), checkName, checkLocale, checkTel, checkEmailAddress, checkAbility, 0))

        viewPager2.adapter = viewPager2Adapter

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (viewPager2Adapter.getFragment(position) is ContactListFragment) {
                    imgGridView.visibility = View.VISIBLE
                    imgListView.visibility = View.VISIBLE
                } else {
                    imgGridView.visibility = View.INVISIBLE
                    imgListView.visibility = View.INVISIBLE
                }
            }
        })

        TabLayoutMediator(tabLayout, viewPager2) { tab, pos ->
            tab.setText(viewPager2Adapter.getTitle(pos))
        }.attach()

        imgGridView.setOnClickListener {
            contactListFragment?.showGridView()
        }

        imgListView.setOnClickListener {
            contactListFragment?.showRecyclerView(0)
        }

        imgListView.setOnLongClickListener {
            contactListFragment?.showRecyclerView(1)
            true
        }

        btnFab.setOnClickListener { //fab 클릭 리스너
            showCustomDialog()
        }
    }

    private fun showCustomDialog() {
        dialogBinding = DialogAddContactBinding.inflate(layoutInflater)
        val buildDialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        buildDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        buildDialog.show()

        dialogBinding.imgAddProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            galleryLauncher.launch(intent)
        }

        dialogBinding.btnCancel.setOnClickListener {
            buildDialog.dismiss()
        }

        dialogBinding.btnAdd.setOnClickListener {
            val nameEdt = dialogBinding.edtName.text.toString()
            val phoneEdt = dialogBinding.edtPhone.text.toString()
            val emailEdt = dialogBinding.edtEmail.text.toString()
            val localeEdt = dialogBinding.edtLocale.text.toString()
            val abilityEdt = dialogBinding.edtAbility.text.toString()
            if (nameEdt.isBlank() || phoneEdt.isBlank() || emailEdt.isBlank() || localeEdt.isBlank()) {
                Toast.makeText(this, getString(R.string.dialog_no_info_toast), Toast.LENGTH_SHORT)
                    .show()
            } else {
                contactListFragment?.addContactList(ContactModel(galleryUri, nameEdt, localeEdt, phoneEdt, emailEdt, abilityEdt, 0))
                var alarmTime = 0
                when(dialogBinding.chipGroup.checkedChipId) {
                    R.id.chip_off -> alarmTime = 0
                    R.id.chip_5min -> alarmTime = 5 //임시로 5초
                    R.id.chip_10min -> alarmTime = 10 //임시로 10초
                    R.id.chip_30min -> alarmTime = 15 //임시로 15초
                }
                if(alarmTime != 0) {
                    Toast.makeText(this@MainActivity, "${nameEdt}님께 연락할 수 있도록 ${alarmTime}분 이후 알람", Toast.LENGTH_SHORT).show()
                    CoroutineScope(Dispatchers.Default).launch {
                        delay(alarmTime * 1000L)
                        setNotification(nameEdt)
                    }
                }
                buildDialog.dismiss()
                galleryUri = convertToUri(R.drawable.ic_empty_user)
            }
        }
    }

    private fun setNotification(nameEdt: String) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "channel_id"
        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android 8.0 이상
            val channel = NotificationChannel(
                channelId,
                "channel_name",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                // 채널에 다양한 정보 설정
                description = "channel_description"
                setShowBadge(true)
            }
            notificationManager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this, channelId)
        } else { // Android 8.0 이하
            builder = NotificationCompat.Builder(this)
        }

        val fullScreenIntent = Intent(this, MainActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        builder.run {
            setSmallIcon(R.drawable.ic_call)
            setContentTitle(getString(R.string.noti_title))
            setContentText(nameEdt + getString(R.string.noti_message))
            setWhen(System.currentTimeMillis())
            priority = NotificationCompat.PRIORITY_DEFAULT
            setFullScreenIntent(fullScreenPendingIntent, true)
        }
        notificationManager.notify(1, builder.build())
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.READ_CONTACTS",
                    "android.permission.CALL_PHONE"
                ),
                100
            )
        } else {
            initView()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && grantResults[1] == PackageManager.PERMISSION_GRANTED
            && grantResults[2] == PackageManager.PERMISSION_GRANTED
            && grantResults[3] == PackageManager.PERMISSION_GRANTED
        ) {
            initView()
        } else {
            finish()
        }
    }
}
