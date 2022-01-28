//package ge.bootcamp.travel19.ui.fragments.favorites.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import ge.bootcamp.travel19.databinding.ListOfFavoritesItemBinding
//import ge.bootcamp.travel19.model.airports.plans.FavoritePlans
//
//class FavoritesAdapter: RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {
//
//    private val plansList: MutableList<FavoritePlans> = mutableListOf()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoritesViewHolder(
//            ListOfFavoritesItemBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//
//
//    override fun onBindViewHolder(holder: FavoritesAdapter.FavoritesViewHolder, position: Int) {
//        val model = plansList[position]
//        holder.onBind(model)
//    }
//
//
//    inner class FavoritesViewHolder(val binding: ListOfFavoritesItemBinding): RecyclerView.ViewHolder(binding.root) {
//
//        fun onBind(model: FavoritePlans) {
//            binding.tvLocation.text = model.location
//            binding.tvDestination.text = model.destination
//        }
//    }
//
//    override fun getItemCount() = plansList.size
//
//    fun setData(plans: MutableList<FavoritePlans>) {
//        plansList.clear()
//        plansList.addAll(plans)
//        notifyDataSetChanged()
//    }
//}