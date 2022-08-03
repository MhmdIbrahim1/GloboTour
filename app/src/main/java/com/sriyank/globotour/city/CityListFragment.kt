package com.sriyank.globotour.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.globotour.R


class CityListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_city_list, container, false)

        setupRecycleView(view)
        return view
    }

    private fun setupRecycleView(view: View?) {

        val content = requireContext()

        val cityAdapter = CityAdapter(content, VacationSpots.cityList!!)

        val recycleView = view?.findViewById<RecyclerView>(R.id.city_recycler_view)

        recycleView?.adapter = cityAdapter

        recycleView?.setHasFixedSize(true) // if recycle view never change width or height in the runtime  then should set it true
                                           // this improve its performance
        //val layoutManager = GridLayoutManager(content,2) // first parameter retain the content
                                        // second one the number of columns i want in the grid so,
                                                            // we will see 2 columns in the grid


            val layoutManager = LinearLayoutManager(content)
            layoutManager.orientation =RecyclerView.VERTICAL // make our list in Vertical direction
            recycleView?.layoutManager = layoutManager

    }
}