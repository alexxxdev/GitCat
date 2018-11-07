package com.github.alexxxdev.gitcat.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.alexxxdev.gitcat.R

class ContainerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_container, container, false)
        return fragmentView
    }
}