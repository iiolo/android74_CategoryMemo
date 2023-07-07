package com.test.android74_categorymemo

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.test.android74_categorymemo.databinding.FragmentPwdBinding


class PwdFragment : Fragment() {

    lateinit var fragmentPwdBinding: FragmentPwdBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPwdBinding = FragmentPwdBinding.inflate(inflater)
        mainActivity = activity as MainActivity


        fragmentPwdBinding.run {
            toolbarPwd.run {
                title = "비밀번호 설정"
            }
            buttonPwd.setOnClickListener {
                Log.i("pwd", "${editTextPwd.text}" )
                Log.i("pwd", "${editTextPwdCheck.text}" )
                if(editTextPwd.text.toString() != editTextPwdCheck.text.toString()){
                    // 비밀번호가 일치하지 않을 경우 다이얼로그 띄우기

                    // 다이얼로그 생성을 위한 객체를 생성한다.
                    val builder = AlertDialog.Builder(mainActivity)

                    // 타이틀
                    builder.setTitle("비밀번호 설정")

                    // 메시지
                    builder.setMessage("비밀번호가 일치하지 않습니다.")

                    // 버튼 배치
                    builder.setPositiveButton("확인", null)

                    // 다이얼로그를 띄운다.
                    builder.show()
                } else {
                    if(PwdDAO.selectAllData(mainActivity).isNotEmpty()){
                        // 비밀번호가 이미 있을 경우 dialog 띄우기

                        // 다이얼로그 생성을 위한 객체를 생성한다.
                        val builder = AlertDialog.Builder(mainActivity)

                        // 타이틀
                        builder.setTitle("비밀번호 설정")

                        // 메시지
                        builder.setMessage("비밀번호가 일치하지 않습니다.")

                        // 버튼 배치
                        builder.setPositiveButton("확인", null)

                        // 다이얼로그를 띄운다.
                        builder.show()
                    } else {
                        PwdDAO.insertData(mainActivity,editTextPwd.text.toString())
                        mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, true, true)
                    }


                }
            }

        }

        return fragmentPwdBinding.root
    }

}