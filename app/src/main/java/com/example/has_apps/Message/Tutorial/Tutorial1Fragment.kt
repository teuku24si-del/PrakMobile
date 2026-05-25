package com.example.has_apps.Message.Tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.has_apps.R



/**
 * A simple [Fragment] subclass.
 * Use the [Tutorial1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tutorial1Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutorial1, container, false)
    }


}