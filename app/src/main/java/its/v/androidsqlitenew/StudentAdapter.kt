package its.v.androidsqlitenew

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class StudentAdapter(private val context: Context):RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var stdList:ArrayList<StudentModel> = ArrayList()
    private var onClickItem:((StudentModel)-> Unit)?=null
    private var onClickDeleteItem:((StudentModel)-> Unit)?=null
    private var onClickEditItem:((StudentModel)-> Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item:ArrayList<StudentModel>){
        this.stdList=item
        notifyDataSetChanged()
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
      val view=LayoutInflater.from(context).inflate(R.layout.card_item_students,parent,false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std=stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{
            onClickItem?.invoke(std)
        }
        holder.imTrash.setOnClickListener(){
            onClickDeleteItem?.invoke(std)
        }
        holder.imEdit.setOnClickListener(){
            onClickEditItem?.invoke(std)
        }

    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    fun setOnClickItem(callBack:(StudentModel)-> Unit){
        this.onClickItem=callBack
    }

    fun setOnClickDeleteItem(callBack:(StudentModel)-> Unit){
        this.onClickDeleteItem=callBack
    }

    fun setOnClickEditItem(callBack: (StudentModel) -> Unit){
        this.onClickEditItem=callBack
    }

    class  StudentViewHolder( view: View):RecyclerView.ViewHolder(view){
        private var id=view.findViewById<TextView>(R.id.tvId)
        private var name=view.findViewById<TextView>(R.id.tvName)
        private var email=view.findViewById<TextView>(R.id.tvEmail)
         var imTrash=view.findViewById<ImageView>(R.id.imTrash)
         var imEdit=view.findViewById<ImageView>(R.id.ivEdit)
        fun bindView(std:StudentModel){
            id.text=std.id.toString()
            name.text=std.name
            email.text=std.email
        }
    }

}