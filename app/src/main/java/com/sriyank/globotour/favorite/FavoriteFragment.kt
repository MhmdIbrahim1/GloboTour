package com.sriyank.globotour.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sriyank.globotour.R
import com.sriyank.globotour.city.City
import com.sriyank.globotour.city.VacationSpots
import java.util.*
import kotlin.collections.ArrayList


class FavoriteFragment : Fragment() {

    private lateinit var favoriteCityList : ArrayList<City>
    private lateinit var favoriteAdapter  : FavoriteAdapter
    private lateinit var recyclerView      : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        setupRecyclerView(view)

        return view
    }

    private fun setupRecyclerView(view: View) {

        val context = requireContext()

         favoriteCityList = VacationSpots.favoriteCityList as ArrayList<City>
         favoriteAdapter = FavoriteAdapter(context, favoriteCityList)

        recyclerView = view.findViewById<RecyclerView>(R.id.favorite_recycler_view)
        recyclerView.adapter = favoriteAdapter
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
        // the first when want to implement the drag (السحب)
        // the sec for the swipe feature


        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, targetViewHolder: RecyclerView.ViewHolder): Boolean {
           // called when the item is dragged
            val fromPosition = viewHolder.adapterPosition
            val toPosition = targetViewHolder.adapterPosition

            Collections.swap(favoriteCityList, fromPosition, toPosition)


            recyclerView.adapter?.notifyItemMoved(fromPosition,toPosition) // Notify the adapter that item is moved

            return true
        }


        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            val position = viewHolder.adapterPosition
            val deleteCity: City = favoriteCityList[position]

            deleteItem(position) // we call this to delete an item from recycle view
            updateCityList(deleteCity,false) // update our list here

            Snackbar.make(recyclerView, "deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO"){
                    undoDelete(position, deleteCity)
                    updateCityList(deleteCity, true)
                }
                .show()
        }
    })
    private fun deleteItem(position: Int){
        favoriteCityList.removeAt(position)
        favoriteAdapter.notifyItemRemoved(position)
        favoriteAdapter.notifyItemRangeChanged(position,favoriteCityList.size)

    }

   // update the city list after delete a city from  fav by swapping
    private fun updateCityList(deleteCity: City, isFavorite: Boolean){
        val cityList = VacationSpots.cityList!!      // get the city list
        val position = cityList.indexOf(deleteCity)
        cityList[position].isFavorite = isFavorite  // after delete the item, update the property of the delete city object to false
    }

    private fun undoDelete(position: Int,deleteCity: City){
        favoriteCityList.add(position,deleteCity) // add the item was deleted again
        favoriteAdapter.notifyItemInserted(position)
        favoriteAdapter.notifyItemRangeChanged(position, favoriteCityList.size)
    }
}
