package com.rahul.clientapp.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.clinic.registration.fragments.*
import com.rahul.clientapp.R
import com.rahul.clientapp.registration.fragments.LoginFragment
import com.rahul.clientapp.registration.fragments.RollNoFragment
import com.rahul.clientapp.registration.fragments.WelcomeFragment
import com.rahul.clientapp.registration.fragments.listeners.LoginInterfaceListener

class LoginActivity : AppCompatActivity(), LoginInterfaceListener {

    companion object {

        var clientId: String = ""
        var password: String = ""
        var name: String = ""
        var dob: String = ""
        var sex: String = ""
        var phNo: String = ""
        var email: String = ""
        var height: Int = 0
        var weight: Int = 0
    }

    private val TAG = LoginActivity::class.java.simpleName

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if(fragment == null) {
            try {
                fragment = RollNoFragment()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.loginContentView, fragment!!).commit()

    }

    override fun switchToFragment(fragmentNo: Int) {



        val nextFragment : Fragment = when(fragmentNo) {
            1 -> LoginFragment()
            2 -> SignUpHandlerFragment()
            else -> WelcomeFragment()
        }

//        val slide = Fade()
////        slide.slideEdge = Gravity.BOTTOM
//        nextFragment.enterTransition = slide

        supportFragmentManager.beginTransaction().
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).
            replace(R.id.loginContentView, nextFragment).commit()
    }


}
