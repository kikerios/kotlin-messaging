package kikerios.me.kotlinmessaging.feature.views

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kikerios.me.kotlinmessaging.feature.R
import kikerios.me.kotlinmessaging.feature.utils.Message

/**
 * Created by kikerios on 8/25/2018.
 */
var messageTextView: TextView? = null
var messengerTextView: TextView? = null
var messengerImageView: AppCompatImageView? = null

class MessageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Message) {
        messageTextView = itemView.findViewById(R.id.messageTextView) as TextView
        messengerTextView = itemView.findViewById(R.id.messengerTextView) as TextView
        messengerImageView = itemView.findViewById(R.id.messengerImageView) as AppCompatImageView

        messageTextView!!.setText(item.text)
        messengerTextView!!.setText(item.name)
    }
}