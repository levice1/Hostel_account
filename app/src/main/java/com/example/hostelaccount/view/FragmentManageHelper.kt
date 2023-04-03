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

    // ПОКА НЕ РАБОТАЕТ, ВОЗМОЖНО УДАЛИТЬ!
//    fun backPressed(context: Context, where: Int, firstFrag: Fragment, secondFrag: Fragment){
//        val currentFragment = manager.findFragmentById(where)
//        if (currentFragment!!::class.java == firstFrag::class.java) {
//            // Показать фрагмент списка
//            FragmentManageHelper(manager)
//                .initFragment(where , secondFrag)
//        } else if (currentFragment!!::class.java == secondFrag::class.java) {
//            // перейти на главное активити (Statistic)
//            InitMenuChoise(context).startMainActivity()
//        }
//    }
}