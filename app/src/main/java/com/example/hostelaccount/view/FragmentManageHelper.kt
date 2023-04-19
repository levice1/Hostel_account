package com.example.hostelaccount.view

import androidx.fragment.app.Fragment

class FragmentManageHelper(private val manager: androidx.fragment.app.FragmentManager) {

    fun initFragment(layoutId: Int, fragment: Fragment ) {
        manager.beginTransaction().replace( layoutId, fragment).commit()
    }


    fun replaceFragment(layoutId: Int, oldFrag: Fragment, newFrag: Fragment ){
        manager.beginTransaction()
            .replace(layoutId, oldFrag).apply {
            replace(layoutId, newFrag)
            addToBackStack(null)
            commit()
        }
    }
}