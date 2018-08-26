package kikerios.me.kotlinmessaging.feature.views

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import kikerios.me.kotlinmessaging.feature.R
import kikerios.me.kotlinmessaging.feature.utils.Message

/**
 * Created by kikerios on 8/25/2018.
 */
var messageTextView: TextView? = null
var messengerTextView: TextView? = null
var messengerImageView: AppCompatImageView? = null
var messengerImageViewOutcomming: AppCompatImageView? = null
var container: RelativeLayout? = null

class MessageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Message, mEmail: String) {
        messageTextView = itemView.findViewById(R.id.messageTextView) as TextView
        messengerTextView = itemView.findViewById(R.id.messengerTextView) as TextView
        messengerImageView = itemView.findViewById(R.id.messengerImageView) as AppCompatImageView
        messengerImageViewOutcomming = itemView.findViewById(R.id.messengerImageViewOutcomming) as AppCompatImageView
        container = itemView.findViewById(R.id.container) as RelativeLayout

        messageTextView!!.setText(item.text)
        messengerTextView!!.setText(item.name)

        if(mEmail.equals(item.email)) {
            container!!.setBackgroundResource(R.drawable.shape_bg_outgoing_bubble)
            messengerImageViewOutcomming!!.visibility = View.VISIBLE
            messengerImageView!!.visibility = View.GONE
        } else {
            container!!.setBackgroundResource(R.drawable.shape_bg_incoming_bubble)
            messengerImageViewOutcomming!!.visibility = View.GONE
            messengerImageView!!.visibility = View.VISIBLE
        }
    }
}