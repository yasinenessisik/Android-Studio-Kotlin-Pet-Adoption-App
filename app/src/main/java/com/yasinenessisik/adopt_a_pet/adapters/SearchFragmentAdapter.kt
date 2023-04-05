import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yasinenessisik.adopt_a_pet.R

class SearchFragmentAdapter(private val items: List<String>) : RecyclerView.Adapter<SearchFragmentAdapter.ViewHolder>() {

    private val checkedItems = BooleanArray(items.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_view_for_search_fragment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemText.text = items[position]
        holder.itemCheckbox.isChecked = checkedItems[position]
        holder.itemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            checkedItems[position] = isChecked
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getCheckedItems(): BooleanArray {
        return checkedItems
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemCheckbox: CheckBox = itemView.findViewById(R.id.cb_sf_list)
        val itemText: TextView = itemView.findViewById(R.id.tv_sf_list)
    }
}
