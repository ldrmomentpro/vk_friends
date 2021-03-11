package com.example.vk_friends.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.vk_friends.models.VKUser

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface FriendView: MvpView {

    fun startLoading()
    fun endLoading()
    fun showError(textResource: Int)
    fun setupEmptyList()
    fun setupFriendsList(friendsList: List<VKUser>)
}