package com.example.vk_friends.activities

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.vk_friends.R
import com.example.vk_friends.presenters.LoginPresenter
import com.example.vk_friends.views.LoginView
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.utils.VKUtils.getCertificateFingerprint


class LoginActivity : MvpAppCompatActivity(), LoginView {
    private lateinit var tvHello: TextView
    private lateinit var btnEnter: Button
    private lateinit var cpvWait: CircularProgressView
    private var TAG: String = LoginActivity::class.java.simpleName

    // Вставка презентера
    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvHello = findViewById(R.id.tv_login_hello)
        btnEnter = findViewById(R.id.btn_login)
        cpvWait = findViewById(R.id.cpv_login)

        btnEnter.setOnClickListener{
//            loginPresenter.login(isSuccess = true)
            VK.login(this@LoginActivity, arrayListOf(VKScope.FRIENDS))
        }

        // Получение отпечатка
//        val fingerprints = getCertificateFingerprint(this, this.packageName)
//        Log.e(TAG, "FingerPrint ${fingerprints?.get(0)}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (!loginPresenter.testLogin(requestCode = requestCode, resultCode = resultCode, data = data))
        super.onActivityResult(requestCode, resultCode, data)
    }


    // Реализация Login View
    override fun startLoading() {
        btnEnter.visibility = View.GONE
        cpvWait.visibility = View.VISIBLE
    }

    override fun endLoading() {
        btnEnter.visibility = View.VISIBLE
        cpvWait.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        Toast.makeText(applicationContext, getString(textResource), Toast.LENGTH_SHORT).show()
    }

    override fun openFriends() {
        startActivity(Intent(applicationContext, FriendsActivity::class.java))
    }
}