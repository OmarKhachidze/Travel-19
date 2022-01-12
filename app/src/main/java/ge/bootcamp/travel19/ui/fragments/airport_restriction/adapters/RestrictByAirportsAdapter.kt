package ge.bootcamp.travel19.ui.fragments.airport_restriction.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.InfoModelBinding
import ge.bootcamp.travel19.databinding.RestrictionByAirportsModelBinding
import ge.bootcamp.travel19.model.generalRestrictions.GeneralRestrictions

class RestrictByAirportsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var restrictions: MutableList<GeneralRestrictions> = mutableListOf()

    companion object {
        const val Bool = 1
        const val Info = 4
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if(viewType == Bool) {
            ResViewHolder(RestrictionByAirportsModelBinding.inflate(LayoutInflater.from(parent.context),parent, false))
        } else {
            InfoViewHolder(InfoModelBinding.inflate(LayoutInflater.from(parent.context),parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = restrictions[position]
        if(holder is ResViewHolder){
            holder.onBind(model)
        } else if (holder is InfoViewHolder){
            holder.onBind(model)
        }
    }

    override fun getItemCount(): Int = restrictions.size

    inner class ResViewHolder(private val binding: RestrictionByAirportsModelBinding): RecyclerView.ViewHolder(binding.root) {

        fun onBind(model: GeneralRestrictions) {
            binding.restriction.text = model.name
            if(model.isAllowed != null  && model.isAllowed == true) {
                //binding.restriction.setCheckMarkDrawable(android.R.drawable.checkbox_on_background)
                binding.restriction.setCheckMarkDrawable(R.drawable.ic_check_mark)
            } else {
                binding.restriction.setCheckMarkDrawable(android.R.drawable.checkbox_off_background)
            }

        }
    }

    inner class InfoViewHolder(private val binding: InfoModelBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: GeneralRestrictions) {
            binding.title.text = model.name
            binding.info.text = model.info
        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.i("mess", "viewtype")
        return if(restrictions[position].info == null) {
            Bool
        } else {
            Info
        }
    }

    fun setData(rest: MutableList<GeneralRestrictions>) {
        restrictions.clear()
        restrictions.addAll(rest)
        notifyDataSetChanged()
    }
}