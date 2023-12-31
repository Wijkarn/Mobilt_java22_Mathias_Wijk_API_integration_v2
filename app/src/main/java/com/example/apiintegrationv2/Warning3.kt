package com.example.apiintegrationv2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class Warning3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_warning3, container, false)

        view.findViewById<Button>(R.id.yesBtn)?.setOnClickListener {
            val fm = activity?.supportFragmentManager
            val transaction = fm?.beginTransaction()
            transaction?.replace(R.id.frameLayout, Warning1())
            transaction?.addToBackStack("warning1")
            transaction?.commit()
        }

        view.findViewById<Button>(R.id.noBtn)?.setOnClickListener {
            val fm = activity?.supportFragmentManager
            fm?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        return view
    }
}