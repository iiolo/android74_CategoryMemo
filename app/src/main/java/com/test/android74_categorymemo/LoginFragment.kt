package com.test.android74_categorymemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.test.android74_categorymemo.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    lateinit var fragmentLoginBinding: FragmentLoginBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentLoginBinding.run {
            toolbarLogin.run {
                title = "로그인"
            }

            buttonLogin.setOnClickListener {
                // 등록된 비밀번호 가져오기
                val pwd = PwdDAO.selectAllData(mainActivity)[0].pwd

                // 등록된 비밀번호와 입력한 비밀번호 일치 하지 않을 경우 다이얼로그 띄우기
                if(pwd != editTextLogin.text.toString()){
                    // 다이얼로그 생성을 위한 객체를 생성한다.
                    val builder = AlertDialog.Builder(mainActivity)

                    // 타이틀
                    builder.setTitle("로그인")

                    // 메시지
                    builder.setMessage("비밀번호가 일치하지 않습니다.")

                    // 버튼 배치
                    builder.setPositiveButton("확인", null)

                    // 다이얼로그를 띄운다.
                    builder.show()

                } else {
                    mainActivity.replaceFragment(MainActivity.MAIN_FRAGMENT, true, true)
                }
            }
        }

        return fragmentLoginBinding.root
    }


}