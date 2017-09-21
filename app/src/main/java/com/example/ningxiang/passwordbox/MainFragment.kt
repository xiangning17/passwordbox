package com.example.ningxiang.passwordbox

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import component.Item
import component.PasswordManager

class MainFragment : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.fragment_item_list, container, false)
    }

    override fun onResume() {
        super.onResume()

        val content = view
        if (content is RecyclerView) {
            content.adapter = MyItemRecyclerViewAdapter(PasswordManager.getAllItems(),
                    object : EditItemFragment.OnListFragmentInteractionListener {
                        override fun onListFragmentInteraction(item: Item) {
                            (activity as PasswordUiContainer).startFragment(EditItemFragment.newInstance(item.id.toInt()))
                        }
                    })
        }
    }

    companion object {

        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            return fragment
        }
    }
}// Required empty public constructor
