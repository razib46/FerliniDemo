package com.example.ferlinidemo.ui

import androidx.fragment.app.DialogFragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.example.ferlinidemo.R

class FilterDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.filter_dialog, container, false)
        val filterOwner: AppCompatEditText = view.findViewById(R.id.filterTextOwner)
        val filterRepo: AppCompatEditText = view.findViewById(R.id.filterTextRepo)
        val searchButton: AppCompatButton = view.findViewById(R.id.filterButtonSearch)

        searchButton.setOnClickListener {
            (activity as MainActivity?)?.loadStargazers(
                filterOwner.text.toString(),
                filterRepo.text.toString()
            )
            dismiss()
        }
        return view
    }
}