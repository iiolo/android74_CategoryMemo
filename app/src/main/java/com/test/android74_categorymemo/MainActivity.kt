package com.test.android74_categorymemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.test.android74_categorymemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 사용자가 누른 항목 번호
    var rowPosition = 0

    companion object {
        // Activity가 관리할 프래그먼트들의 이름
        val PWD_FRAGMENT = "PwdFragment" // 비밀번호 설정 화면
        val LOGIN_FRAGMENT = "LoginFragment" // 로그인 화면
        val MAIN_FRAGMENT = "MainFragment" // 카테고리 목록 화면(메인)
        val MEMO_FRAGMENT = "MemoFragment" // 메모 목록 화면
        val ADD_FRAGMENT = "AddFragment" // 메모 추가 화면
        val RESULT_FRAGMENT = "ResultFragment" // 메모 보기(상세) 화면
        val EDIT_FRAGMENT = "EditFragment" // 메모 수정 화면



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)


        replaceFragment(MAIN_FRAGMENT, false, false)
    }

    // 지정한 Fragment를 보여주는 메서드
    fun replaceFragment(name:String, addToBackStack:Boolean, animate:Boolean){
        // Fragment 교체 상태로 설정한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        // 새로운 Fragment를 담을 변수
        var newFragment = when(name){
            PWD_FRAGMENT ->{
                PwdFragment()
            }

            LOGIN_FRAGMENT ->{
                LoginFragment()
            }

            MAIN_FRAGMENT -> {
                MainFragment()
            }
            MEMO_FRAGMENT ->{
                MemoFragment()
            }
            ADD_FRAGMENT -> {
                AddFragment()
            }
            RESULT_FRAGMENT -> {
                ResultFragment()
            }
            EDIT_FRAGMENT -> {
                EditFragment()
            }
            else -> {
                Fragment()
            }
        }

        if(newFragment != null) {
            // Fragment를 교채한다.
            fragmentTransaction.replace(R.id.mainContainer, newFragment)

            if (animate == true) {
                // 애니메이션을 설정한다.
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }

            if (addToBackStack == true) {
                // Fragment를 Backstack에 넣어 이전으로 돌아가는 기능이 동작할 수 있도록 한다.
                fragmentTransaction.addToBackStack(name)
            }

            // 교체 명령이 동작하도록 한다.
            fragmentTransaction.commit()
        }
    }

    // Fragment를 BackStack에서 제거한다.
    fun removeFragment(name:String){
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


}
// 정보를 담을 객체
data class MemoClass(var idx :Int, var title:String, var content:String, var dateData:String)
data class CategoryClass(var idx :Int, var categoryName:String)
data class PwdClass(var idx :Int, var pwd:String)
