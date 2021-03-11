package com.example.vk_friends.providers

import android.os.Handler
import com.example.vk_friends.R
import com.example.vk_friends.models.VKFriendsRequest
import com.example.vk_friends.models.VKUser
import com.example.vk_friends.presenters.FriendsPresenter
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException
import com.vk.api.sdk.requests.VKRequest


class FriendsProvider(var presenter: FriendsPresenter) {

    /*fun testLoadFriends(hasFriends: Boolean) {
        Handler().postDelayed({
            val friendsList: MutableList<FriendModel> = mutableListOf()
            if (hasFriends) {
                val friend1 = FriendModel(
                    "Egor",
                    "Jukov",
                    city = null,
                    avatar = "https://sun3-12.userapi.com/impf/c855416/v855416476/153c3d/FRoT2E-VQfM.jpg?size=564x1001&quality=96&sign=526ced3981e266dd9619330f2c2e407f&type=album",
                    true
                )
                val friend2 = FriendModel(
                    "Igor",
                    "Muhin",
                    city = "New-York",
                    avatar = "https://images.wallpaperscraft.ru/image/bmw_mashina_sportkar_164668_1280x720.jpg",
                    true
                )
                val friend3 = FriendModel(
                    "Edgar",
                    "Po",
                    city = "London",
                    avatar = "https://images.wallpaperscraft.ru/image/bmw_mashina_chernyj_167779_1280x720.jpg",
                    true
                )

                friendsList.add(friend1)
                friendsList.add(friend2)
                friendsList.add(friend3)
            }

            presenter.friendsLoaded(friendsList = friendsList)
        }, 2000)
    }*/

    fun loadFriends(){
        VK.execute(VKFriendsRequest(), object: VKApiCallback<List<VKUser>> {
            override fun success(result: List<VKUser>) {
                presenter.friendsLoaded(result)
            }

            override fun fail(error: Exception) {
                presenter.showError(textResource = R.string.friends_loading_error)
            }
        })
    }
}