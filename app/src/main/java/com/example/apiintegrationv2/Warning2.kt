package com.example.apiintegrationv2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class Warning2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_warning2, container, false)

        view.findViewById<Button>(R.id.yesBtn)?.setOnClickListener {
            val fm = activity?.supportFragmentManager
            val transaction = fm?.beginTransaction()
            transaction?.replace(R.id.frameLayout, Warning3())
            transaction?.addToBackStack("warning3")
            transaction?.commit()
        }

        view.findViewById<Button>(R.id.noBtn)?.setOnClickListener {
            val fm = activity?.supportFragmentManager
            fm?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        return view
    }
}