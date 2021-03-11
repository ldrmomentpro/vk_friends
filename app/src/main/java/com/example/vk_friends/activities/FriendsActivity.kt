package com.example.vk_friends.activities

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Adapter
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.vk_friends.R
import com.example.vk_friends.adapters.FriendsAdapter
import com.example.vk_friends.models.VKUser
import com.example.vk_friends.presenters.FriendsPresenter
import com.example.vk_friends.views.FriendView
import com.github.rahatarmanahmed.cpv.CircularProgressView

class FriendsActivity : MvpAppCompatActivity(), FriendView {

    @InjectPresenter
    lateinit var friendsPresenter: FriendsPresenter

    private lateinit var rvFriends: RecyclerView
    private lateinit var tvNoItems: TextView
    private lateinit var cpvWait: CircularProgressView
    private lateinit var fAdapter: FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        rvFriends = findViewById(R.id.rv_friends)
        tvNoItems = findViewById(R.id.tv_no_items)
        cpvWait = findViewById(R.id.cpv_friends)

        val etSearch = findViewById<EditText>(R.id.et_friends_search)
        etSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                fAdapter.filter(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        // Загрузка данных
        friendsPresenter.loadFriends()

        fAdapter = FriendsAdapter()
        rvFriends.adapter = fAdapter
        rvFriends.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        rvFriends.setHasFixedSize(true)
    }

    // Реализация Friends View

    override fun startLoading() {
        rvFriends.visibility = View.GONE
        tvNoItems.visibility = View.GONE
        cpvWait.visibility = View.VISIBLE
    }

    override fun endLoading() {
        cpvWait.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        tvNoItems.text = getString(textResource)
    }

    override fun setupEmptyList() {
        cpvWait.visibility = View.GONE
        tvNoItems.visibility = View.VISIBLE
    }

    override fun setupFriendsList(friendsList: List<VKUser>) {
        rvFriends.visibility = View.VISIBLE
        tvNoItems.visibility = View.GONE

        fAdapter.setupFriends(friendList = friendsList)
    }
}