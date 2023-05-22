package com.fedorov.andrii.andriiovych.alarmclock.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fedorov.andrii.andriiovych.alarmclock.R
import com.fedorov.andrii.andriiovych.alarmclock.databinding.FragmentSetTimeBinding


class SetTimeFragment : Fragment() {
        lateinit var binding: FragmentSetTimeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetTimeBinding.inflate(inflater,container,false)
        return binding.root
    }


}