package com.example.vk_friends.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vk_friends.R
import com.example.vk_friends.models.VKUser
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class FriendsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val friendsList: ArrayList<VKUser> = arrayListOf()
    val sourceList: ArrayList<VKUser> = arrayListOf()

    fun setupFriends(friendList: List<VKUser>) {
        sourceList.clear()
        sourceList.addAll(friendList)

        filter(query = "")
    }

    // Реализация поиска
    fun filter(query: String) {
        friendsList.clear()
        sourceList.forEach {
            if (it.firstName.contains(query, ignoreCase = true) || it.lastName.contains(query, ignoreCase = true)) friendsList.add(it)
            else it.city?.let { city -> if (city.contains(query, ignoreCase = true)) friendsList.add(it)}
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.cell_friend, parent, false)
        return FriendsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FriendsViewHolder) {
            holder.bind(friendModel = friendsList[position])
        }
    }

    override fun getItemCount(): Int = friendsList.count()


    class FriendsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var civAvatar = itemView.findViewById<CircleImageView>(R.id.civ_avatar_friend)
        private var tvUsername = itemView.findViewById<TextView>(R.id.tv_friend_username)
        private var tvCity = itemView.findViewById<TextView>(R.id.tv_friend_city)

        private var imgOnline = itemView.findViewById<View>(R.id.img_friend_online)
        @SuppressLint("SetTextI18n")
        fun bind(friendModel: VKUser) {

            // Загрузка изображения
            friendModel.photo?.let { url ->
                Picasso.get().load(url)
                        .into(civAvatar)
            }

            // Привязка данных к View
            tvUsername.text = "${friendModel.firstName} ${friendModel.lastName}"
            tvCity.text = itemView.context.getString(R.string.friends_no_city)
            friendModel.city?.let { city -> tvCity.text = city }

            if (friendModel.isOnline == 1) imgOnline.visibility = View.VISIBLE
            else imgOnline.visibility = View.GONE
        }
    }
}