package com.example.restau.Controller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restau.View.Adapter.FoodItemAdapter
import com.example.restau.Model.Struk.Struk
import com.example.restau.R
import com.example.restau.Retrofit.InstanceRetro
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuTypeFragment : Fragment() {

    private var menuTypeId: Long? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var struk: Struk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            menuTypeId = it.getLong(ARG_MENU_TYPE_ID)
            struk = it.getSerializable(ARG_STRUK) as Struk
        }
        println("MenuTypeFragment: Menu type ID: $menuTypeId")

        // Fetch the menu data from the API
        CoroutineScope(Dispatchers.IO).launch {
            val localMenuTypeId = menuTypeId
            if (localMenuTypeId != null) {

                //Get Response from service
                val response = InstanceRetro.menuService.getMenuByJenisMenuId(localMenuTypeId)
                if (response.isSuccessful) {

                    //Get the menu Items Object
                    val menuItems = response.body()?.data ?: listOf()
                    withContext(Dispatchers.Main) {
                        if(struk != null){
                            recyclerView.adapter = FoodItemAdapter(menuItems,struk!!)
                        }
                    }
                    println("MenuTypeFragment: Successfully fetched menu items: $menuItems")
                } else {
                    println("MenuTypeFragment: Failed to fetch menu items: ${response.errorBody()}")
                }
            } else {
                println("MenuTypeFragment: Invalid menu type ID: $menuTypeId")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu_type, container, false)

        //Initiate the recyclerView
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    companion object {
        private const val ARG_MENU_TYPE_ID = "menu_type_id"
        private const val ARG_STRUK = "struk"

        fun newInstance(menuTypeId: Long,struk: Struk) =
            MenuTypeFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_MENU_TYPE_ID, menuTypeId)
                    putSerializable(ARG_STRUK,struk)
                }
            }
    }
}