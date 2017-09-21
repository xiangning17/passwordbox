package com.example.ningxiang.passwordbox

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.*
import android.widget.EditText
import component.Item
import component.PasswordManager
import kotlinx.android.synthetic.main.fragment_edit_item.*
import kotlinx.android.synthetic.main.fragment_edit_item.view.*

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class EditItemFragment : BaseFragment() {
    // TODO: Customize parameters
    private var mItem: Item? = null
    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mItem = PasswordManager.getItemById(arguments.getInt(ARG_ITEM_ID))
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_edit_item, container, false)

        if (mItem != null) {
            view.name.setText(mItem?.name)
            view.account.setText(mItem?.account)
            view.passwd.setText(mItem?.pwd)
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }


        (activity as PasswordUiContainer).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as PasswordUiContainer).supportActionBar?.setDisplayShowHomeEnabled(false)

        ((activity as PasswordUiContainer).supportActionBar)?.setDisplayUseLogoEnabled(false)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * Initialize the contents of the Fragment host's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called [.setHasOptionsMenu].  See
     * [Activity.onCreateOptionsMenu]
     * for more information.
     *
     * @param menu The options menu in which you place your items.
     *
     * @see .setHasOptionsMenu
     *
     * @see .onPrepareOptionsMenu
     *
     * @see .onOptionsItemSelected
     */
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu!!.clear()
        inflater!!.inflate(R.menu.item_create, menu)

//        (activity as AppCompatActivity).supportActionBar?.setDisplayUseLogoEnabled(false)
//        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
//        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
//        (activity as AppCompatActivity).supportActionBar?.setDisplayShowCustomEnabled(false)
//        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false)
//        (activity as AppCompatActivity).supportActionBar?.setIcon(null)
//        (activity as AppCompatActivity).supportActionBar?.setLogo(null)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
//        (activity as AppCompatActivity).supportActionBar?.customView = null
//        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(null)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_save -> {
                if (save()) {
                    activity.onBackPressed()
                }
            }

            R.id.action_cancel -> {
                activity.onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item)

    }

    private fun save(): Boolean {

        val content = view

        val name = checkInputAndGet(view!!.name)
        val account = checkInputAndGet(view!!.account)
        val pwd = checkInputAndGet(view!!.passwd)

        return listOf(name, account, pwd).all { !it.isNullOrEmpty() }
                && PasswordManager.addItem(name!!, account!!, pwd!!)
    }

    private fun checkInputAndGet(input: EditText) : String? {
        return if (input.text.isNullOrBlank()) {
            input.setError("不能为空")
            null
        } else {
            input.text.toString()
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Item)
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_ITEM_ID = "item_id"

        // TODO: Customize parameter initialization
        fun newInstance(id: Int): EditItemFragment {
            val fragment = EditItemFragment()
            val args = Bundle()
            args.putInt(ARG_ITEM_ID, id)
            fragment.arguments = args
            return fragment
        }
    }
}
