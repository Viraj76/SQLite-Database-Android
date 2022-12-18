package its.v.androidsqlitenew

import java.util.*

data class StudentModel(
    val id:Int= getAutoID(),
    val name:String="",
val email:String=""
) {
    companion object {

        fun getAutoID(): Int {
        val random = Random()
        return random.nextInt(100)
    }
    }

}