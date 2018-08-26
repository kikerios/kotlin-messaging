package kikerios.me.kotlinmessaging.feature.utils

/**
 * Created by kikerios on 8/25/2018.
 */
class Message {

    var id: String? = null
    var text: String? = null
    var name: String? = null
    var photoUrl: String? = null
    var email: String? = null

    constructor() {}

    constructor(text: String, name: String, photoUrl: String, email: String) {
        this.text = text
        this.name = name
        this.photoUrl = photoUrl
        this.email = email
    }
}