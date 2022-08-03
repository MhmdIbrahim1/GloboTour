package com.sriyank.globotour.city

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sriyank.globotour.R
import android.util.Log.*

class CityAdapter(val context: Context, var cityList: ArrayList<City> ) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {

        i("CityAdapter", "onCreateViewHolder: ViewHolder Created ")
        val itemView =LayoutInflater.from(context).inflate(R.layout.list_item_city, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(cityViewHolder: CityViewHolder , position: Int) {

        i("CityAdapter", "onBindViewHolder: position $position")

        // make data appear on cityViewHolder
        // will be call in each item in the list and set item in each item in the list
        val city = cityList[position] // retreat the city object at this current position
        cityViewHolder.setData(city, position)

        cityViewHolder.setListeners() // this method is going to set the listeners on the view objects of this cityViewHolder like,
                                        //  cityViewHolder.setData(city, position) but this set the data

    }

    override fun getItemCount(): Int = cityList.size

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        // use inner       //this matching      =>       //super class
        // keyWord  so the ViewHolder class can use the the properties of CityAdapter
        private var currentPosition = -1
        private var currentCity: City? = null

        private val txvCityName =itemView.findViewById<TextView>(R.id.txv_city_name)
        private val imvCityImage =itemView.findViewById<ImageView>(R.id.imv_city)
        private val imvDelete =itemView.findViewById<ImageView>(R.id.imv_delete)      // this ides in list_item_city.xml
        private val imvFavorite =itemView.findViewById<ImageView>(R.id.imv_favorite)

        private val icFavoriteFilledImage = ResourcesCompat.getDrawable(context.resources,
        R.drawable.ic_favorite_filled, null)

        private val icFavoriteBorderedImage = ResourcesCompat.getDrawable(context.resources,
            R.drawable.ic_favorite_bordered, null)


        fun setData(city: City, position: Int){

            txvCityName.text = city.name
            imvCityImage.setImageResource(city.imageId)

            if (city.isFavorite)
                imvFavorite.setImageDrawable(icFavoriteFilledImage)
            else
                imvFavorite.setImageDrawable(icFavoriteBorderedImage)

            this.currentPosition = position
            this.currentCity = city
        }

        fun setListeners(){
            imvDelete.setOnClickListener(this@CityViewHolder)
            imvFavorite.setOnClickListener(this@CityViewHolder)
        }

        override fun onClick(v: View?) {

            when(v!!.id){
                R.id.imv_delete -> deleteItem() // when delete icon is clicked will delete
                R.id.imv_favorite -> addToFavorite() // -- -- - -- -- -- --  will add to fav
            }
        }

        fun deleteItem(){

            cityList.removeAt(currentPosition)
            notifyItemRemoved(currentPosition)
            notifyItemRangeChanged(currentPosition, cityList.size) // passed currentPosition , and size of cityList to update the list
            VacationSpots.favoriteCityList.remove(currentCity!!)

            val deleteCity: City = cityList[currentPosition]
            Snackbar.make(imvDelete, "deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO"){
                    undoDelete(currentPosition, deleteCity)

                }
                .show()

        }

        fun addToFavorite(){

            currentCity?.isFavorite =!(currentCity?.isFavorite!!)  // Toggle the isFavorite Boolean Value

            if (currentCity?.isFavorite!!){      // if it isFavorite - Update icon add  the city object to favorite List
                imvFavorite.setImageDrawable(icFavoriteFilledImage)
                VacationSpots.favoriteCityList.add(currentCity!!)
                Snackbar.make(imvFavorite, "added to favourites", Snackbar.LENGTH_LONG)
                    .show()



            }else{        // else it is not favorite - update  icon and remove the city object from the favorite list
                imvFavorite.setImageDrawable(icFavoriteBorderedImage)
                VacationSpots.favoriteCityList.remove(currentCity!!)

            }

        }
    }

    private fun displaySave() {


    }

    private fun undoDelete(position: Int,deleteCity: City){
        cityList.add(position,deleteCity) // add the item was deleted again
        notifyItemInserted(position)
        notifyItemRangeChanged(position, cityList.size)

    }

}