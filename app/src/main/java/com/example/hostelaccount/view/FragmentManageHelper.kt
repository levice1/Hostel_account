package com.example.hostelaccount.view

import androidx.fragment.app.Fragment

class FragmentManageHelper(private val manager: androidx.fragment.app.FragmentManager) {

    fun initFragment( idFrameLayout: Int , fragment: Fragment ) {
        manager.beginTransaction().replace(idFrameLayout, fragment).commit()
    }


    fun replaceFragment( where: Int, oldFrag: Fragment, newFrag: Fragment ){
        manager.beginTransaction()
            .replace(where, oldFrag).apply {
            replace(where, newFrag)
            addToBackStack(null)
            commit()
        }
    }

}