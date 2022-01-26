package com.inov8.bop.currencyconversion.base

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


open class ActivityBase : AppCompatActivity() {

    /**
     * @return the lifecycleState
     */
    /**
     * @param lifecycleState the lifecycleState to set
     */
    var lifecycleState: State? = null

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */


    /**
     * It saves the current state of the application.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

    /**
     * Restores the saved application state, in case of be needed, and does default settings for the action bar.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
    }

    fun callFragmentWithAddBackStack(containerId: Int, fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(containerId, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    fun callFragmentWithReplace(containerId: Int, fragment: Fragment, tag: String?) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(containerId, fragment, tag)

        if (tag != null)
            transaction.addToBackStack(tag)
                .commit()
        else
            transaction
                .commit()
    }


    /**
     * if you will pass tag as title of fragment it will add that
     * fragment to stack otherwise will not add on stack.
     *
     * @param containerId
     * @param fragment
     * @param tag
     */
    fun callFragment(containerId: Int, fragment: Fragment, tag: String?) {
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(containerId, fragment, tag)

        if (tag != null)
            transaction.addToBackStack(tag)
                .commitAllowingStateLoss()
        else
            transaction
                .commit()
    }

    fun callFragmentLossState(containerId: Int, fragment: Fragment, tag: String?) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(containerId, fragment, tag)

        if (tag != null)
            transaction.addToBackStack(tag)
                .commitAllowingStateLoss()
        else
            transaction
                .commitAllowingStateLoss()
    }

    /**
     * if you will pass tag as title of fragment it will add that
     * fragment to stack otherwise will not add on stack.
     *
     * @param containerId
     * @param fragment
     * @param tag
     */
    fun addFragment(containerId: Int, fragment: Fragment, tag: String?) {

        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
        val transaction = supportFragmentManager
            .beginTransaction()
            .add(containerId, fragment, tag)

        if (tag != null)
            transaction.addToBackStack(tag)
                .commit()
        else
            transaction
                .commit()
    }

    /**
     * if you will pass tag as title of fragment it will add that
     * fragment to stack otherwise will not add on stack.
     *
     * @param fragment
     */
    fun removeFragment(fragment: Fragment) {
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
        val transaction = supportFragmentManager
            .beginTransaction()
            .remove(fragment)

        transaction
            .commit()
    }


    override fun onStart() {
        super.onStart()
        lifecycleState = State.STARTED
    }

    override fun onResume() {
        super.onResume()
        lifecycleState = State.RESUMED
    }

    override fun onPause() {
        super.onPause()
        lifecycleState = State.PAUSED
    }


    override fun onStop() {
        super.onStop()
        lifecycleState = State.STOPPED
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleState = State.DESTROYED
    }

    /**
     * The possibles states of an activity lifecycle.
     */
    enum class State {
        CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTROYED
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("ServiceCast")
    fun showKeyboard() {
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }

    fun hideKeyboard() {
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }


    fun noTrailingwhiteLines(text: CharSequence): CharSequence {
        var text = text

        while (text[text.length - 1] == '\n') {
            text = text.subSequence(0, text.length - 1)
        }

        while (text[text.length - 2] == '\n') {
            text = text.subSequence(0, text.length - 2)
        }
        return text
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarColor(color: Int, flag: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
            activity.window.decorView.systemUiVisibility = flag

        }
    }

    companion object {
        lateinit var activity: AppCompatActivity

    }

    override fun onBackPressed() {

        super.onBackPressed()
    }

}