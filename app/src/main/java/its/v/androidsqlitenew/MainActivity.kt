package its.v.androidsqlitenew

import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edtName:TextView
    private lateinit var edtEmail:TextView
    private lateinit var btnAdd:Button
    private lateinit var btnView:Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var sqliteHelper:DataBaseHandler
    private lateinit var btnUpdate:Button
    private var std:StudentModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()

        sqliteHelper=DataBaseHandler(this)

        btnAdd.setOnClickListener(){
            addStudent()
        }
        btnView.setOnClickListener(){
            showStudents()
        }
        studentAdapter.setOnClickItem {
            Toast.makeText(this,it.name,Toast.LENGTH_SHORT).show()
        }
        btnUpdate.setOnClickListener(){
            updateStudent()
        }

        studentAdapter.setOnClickDeleteItem  {
            deleteStudent(it.id)
        }

        studentAdapter.setOnClickEditItem {
            edtName.text=it.name
            edtEmail.text=it.email
            std=it
            Toast.makeText(this,"Edit It!",Toast.LENGTH_SHORT).show()
        }

    }

    private fun deleteStudent(id:Int){

        val builder=AlertDialog.Builder(this)
        builder.setMessage("Are you sure to delete this item")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog,_ ->
            sqliteHelper.deleteStudentById(id)
            showStudents()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){dialog,_ ->
            dialog.dismiss()
        }
        val alert =builder.create()
        alert.show()
    }
    private fun updateStudent() {
        val name=edtName.text.toString()
        val email=edtEmail.text.toString()

        if(std==null) return
        if(name==std?.name && email==std?.name){
            Toast.makeText(this,"Record Not Changed",Toast.LENGTH_SHORT).show()
            return
        }
        val std=StudentModel(id= std!!.id,name=name,email=email)
        val status=sqliteHelper.updateStudent(std)
        if(status> -1){
            Toast.makeText(this,"Updated Successfully",Toast.LENGTH_SHORT).show()
            clearEditTexts()
            showStudents()
        }
        else
            Toast.makeText(this,"Updating Failed",Toast.LENGTH_SHORT).show()

    }


    private fun initRecyclerView(){
        recyclerView.layoutManager=LinearLayoutManager(this)
        studentAdapter=StudentAdapter(this)
        recyclerView.adapter=studentAdapter
    }
    private fun showStudents() {
       val stdList=sqliteHelper.getAllStudent()
        Log.e("poppy","${stdList.size}")
        studentAdapter.addItem(stdList)
    }
    private fun addStudent() {
        val name=edtName.text.toString()
        val email=edtEmail.text.toString()

        if(name.isEmpty() || email.isEmpty())
            Toast.makeText(this,"Please Enter Required field",Toast.LENGTH_SHORT).show()
        else{
            val std=StudentModel(name=name,email=email)
            val status=sqliteHelper.insertStudent(std)
            if(status > -1){
                Toast.makeText(this,"Student Added",Toast.LENGTH_SHORT).show()
                showStudents()
                clearEditTexts()
            }
            else{
                Toast.makeText(this,"Record Not Saved",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun clearEditTexts() {
        edtEmail.text=""
        edtName.text=""
        edtName.requestFocus()
    }
    private fun initView() {
       edtName=findViewById(R.id.edtName)
        edtEmail=findViewById(R.id.edtEmail)
        btnAdd=findViewById(R.id.btnAdd)
        btnView=findViewById(R.id.btnView)
        recyclerView=findViewById(R.id.rvShowData)

        btnUpdate=findViewById(R.id.btnUpdate)
    }
}

