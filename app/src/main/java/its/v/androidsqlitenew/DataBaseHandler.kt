package its.v.androidsqlitenew

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import its.v.androidsqlitenew.DataBaseHandler.Companion.TABLE_STUDENT
import java.lang.Exception
import kotlin.system.measureNanoTime

class DataBaseHandler(context:Context): SQLiteOpenHelper(context,DATABASE_NAME,null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION=1
        private const val DATABASE_NAME="Student.db"
        private const val TABLE_STUDENT="table_Student"
        private const val ID="id"
        private const val NAME="name"
        private const val EMAIL="email"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createStudentTable=("CREATE TABLE "+ TABLE_STUDENT+ "("
                +ID+" INTEGER PRIMARY KEY,"
                +NAME+" TEXT,"
                +EMAIL+" TEXT"+")")
        p0?.execSQL(createStudentTable)
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {  //p1 oldVersion ,, p2 newVersion
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENT")
        onCreate(p0)
    }
    fun insertStudent(std: StudentModel): Long {
        val p0 = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(ID, std.id)
        contentValue.put(NAME, std.name)
        contentValue.put(EMAIL, std.email)
        return p0.insert(TABLE_STUDENT, null, contentValue)
    }
    @SuppressLint("Range")
    fun getAllStudent():ArrayList<StudentModel>{
        val stdList:ArrayList<StudentModel> =ArrayList()
        val selectQuery="SELECT * FROM $TABLE_STUDENT"
        val p0=this.readableDatabase
        val cursor:Cursor?
        try {
            cursor=p0.rawQuery(selectQuery,null)
        }
        catch (e:Exception){
            e.printStackTrace()
            p0.execSQL(selectQuery)
            return ArrayList()
        }
        var id:Int
        var name:String
        var email:String
        if(cursor.moveToFirst()){
            do{
                id= cursor.getInt(cursor.getColumnIndex("id"))
                name=cursor.getString( cursor.getColumnIndex("name"))
                email=cursor.getString(cursor.getColumnIndex("email"))
                val std=StudentModel(id=id,name=name,email=email)
                stdList.add(std)
            }while(cursor.moveToNext())
        }
        return stdList
    }

    fun updateStudent(std: StudentModel):Int{
        val p0=this.writableDatabase
        val contentValue=ContentValues()
        contentValue.put(ID,std.id)
        contentValue.put(NAME,std.name)
        contentValue.put(EMAIL,std.email)
        val success=p0.update(TABLE_STUDENT,contentValue,"id="+std.id,null)
        p0.close()
        return success
    }

    fun deleteStudentById(id: Int): Int {
        val p0 = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, id)
        val success=p0.delete(TABLE_STUDENT, "id=$id", null)
        p0.close()
        return success
    }


}


