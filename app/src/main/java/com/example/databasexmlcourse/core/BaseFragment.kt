package com.example.databasexmlcourse.core

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.databasexmlcourse.core.models.AlertData

abstract class BaseFragment : Fragment(){

    override fun onResume() {
        super.onResume()
        Log.d("FRAGMENT_LOGGER", javaClass.simpleName)
    }

    /** Переход назад. Если параметр id  задан, то просиходит переход к экрану с этим  id.
     *  Если  id  не задан, происходит переход на предыдущий экран в стеке. **/
    @JvmOverloads
    fun popBackStack(id: Int? = null) {
        if (id == null) {
            findNavController().popBackStack()
        } else {
            findNavController().popBackStack(id, false)
        }
    }


    /** Переход к экрану.
     * Здесь: id - id перехода к экрану или самого экрана
     * bundle- параметры , передаваыемые во фрагмент
     * navBuilder -  builder c соответсвующими свойствами
     * По умолчанию задается базовая анимация перехода между фрагментами, анимацию по умолчанию в xml  задавать больше не надо.
     * Если необхоимо задать параметр popUpTo,  то необходимо передать параметр navBuilder = NavOptions.Builder().setPopUpTo(R.id.fragment_id)**/
    @JvmOverloads
    fun navigateTo(
        id: Int,
        bundle: Bundle? = null,
        navBuilder: NavOptions.Builder? = null
    ) {
        findNavController().navigateWithAnimation(id, bundle, navBuilder)
    }

    fun showAlert(alertData: AlertData) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(alertData.title))
        builder.setMessage(getString(alertData.message))
        builder.setPositiveButton(getString(alertData.positiveButton)) { dialog: DialogInterface, _: Int ->
            alertData.navigate?.invoke() ?: dialog.dismiss()
        }
        if (alertData.isNegativeButtonNeeded) {
            builder.setNegativeButton(getString(alertData.negativeButton)) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

}