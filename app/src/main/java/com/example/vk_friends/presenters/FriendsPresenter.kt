package com.example.vk_friends.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vk_friends.R
import com.example.vk_friends.models.VKUser
import com.example.vk_friends.providers.FriendsProvider
import com.example.vk_friends.views.FriendView

@InjectViewState
class FriendsPresenter: MvpPresenter<FriendView>() {
    fun loadFriends() {
        viewState.startLoading()

        // Загрузка данных
        FriendsProvider(presenter = this).loadFriends()
    }

    fun friendsLoaded(friendsList: List<VKUser>) {
        viewState.endLoading()
        if (friendsList.size == 0) {
            viewState.setupEmptyList()
            viewState.showError(textResource = R.string.friends_no_items)
        }
        else {
            viewState.setupFriendsList(friendsList = friendsList)
        }
    }

    fun showError(textResource: Int){
        viewState.showError(textResource = textResource)
    }
}