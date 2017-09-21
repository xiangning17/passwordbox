package com.example.ningxiang.passwordbox


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment


/**
 * A simple [Fragment] subclass.
 */
open class BaseFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null

    open fun onBackPressd() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

}
