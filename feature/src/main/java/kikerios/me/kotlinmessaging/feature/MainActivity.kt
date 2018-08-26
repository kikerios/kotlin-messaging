package kikerios.me.kotlinmessaging.feature

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.firebase.ui.database.FirebaseRecyclerAdapter
import kikerios.me.kotlinmessaging.feature.utils.Message
import kikerios.me.kotlinmessaging.feature.views.MessageViewHolder
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.DataSnapshot

/**
 * Created by kikerios on 8/25/2018.
 */
class MainActivity: BaseActivity() {

    private var TAG: String = "MainActivity"
    private val MESSAGES_CHILD = "messages"

    var mRecyclerView: RecyclerView? = null
    var mProgressBar: ProgressBar? = null
    var mLinearLayout: LinearLayout? = null
    var mEditText: EditText? = null
    var mButton: Button? = null
    var mLinearLayoutManager: LinearLayoutManager? = null
    var mFirebaseAdapter: FirebaseRecyclerAdapter<Message, MessageViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mUsername = getUser()!!.getDisplayName()
        val mPhotoUrl = getUser()!!.getPhotoUrl().toString()
        val mEmail = getUser()!!.email

        mLinearLayoutManager = LinearLayoutManager(this)
        mLinearLayoutManager!!.setStackFromEnd(true)
        mRecyclerView = findViewById(R.id.messageRecyclerView) as RecyclerView
        mProgressBar = findViewById(R.id.progressBar) as ProgressBar
        mLinearLayout = findViewById(R.id.linearLayout) as LinearLayout
        mEditText = findViewById(R.id.messageEditText) as EditText
        mButton = findViewById(R.id.sendButton) as Button

        val mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference()
        var messagesRef: DatabaseReference = mFirebaseDatabaseReference.child(MESSAGES_CHILD)

        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messagesRef, object: SnapshotParser<Message> {
                override fun parseSnapshot(snapshot: DataSnapshot): Message {
                    val friendlyMessage = snapshot.getValue<Message>(Message::class.java)
                    if (friendlyMessage != null) {
                        friendlyMessage!!.id = snapshot.key
                    }
                    return friendlyMessage!!
                }
            })
            .build()

        mFirebaseAdapter = object : FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {

            override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
                mProgressBar!!.visibility = View.GONE
                holder!!.bind(model, mEmail!!)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
                return MessageViewHolder(LayoutInflater.from(this@MainActivity).inflate(R.layout.item_message, parent, false))
            }
        }

        mFirebaseAdapter!!.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val friendlyMessageCount = mFirebaseAdapter!!.getItemCount()
                val lastVisiblePosition = mLinearLayoutManager!!.findLastCompletelyVisibleItemPosition()
                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
                // to the bottom of the list to show the newly added message.
                if (lastVisiblePosition == -1 || positionStart >= friendlyMessageCount - 1 && lastVisiblePosition == positionStart - 1) {
                    mRecyclerView!!.scrollToPosition(positionStart)
                }
            }
        })

        mRecyclerView!!.setLayoutManager(mLinearLayoutManager)
        mRecyclerView!!.setAdapter(mFirebaseAdapter)

        mEditText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.toString().trim().length > 0) {
                    mButton!!.setEnabled(true)
                } else {
                    mButton!!.setEnabled(false)
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        mButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val friendlyMessage = Message(mEditText!!.getText().toString(), mUsername!!, mPhotoUrl, mEmail!!)
                mFirebaseDatabaseReference.child(MESSAGES_CHILD).push().setValue(friendlyMessage)
                mEditText!!.setText("")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.sign_out_menu -> {
                logout()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    public override fun onPause() {
        mFirebaseAdapter!!.stopListening()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        mFirebaseAdapter!!.startListening()
    }
}