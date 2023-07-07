package com.test.android74_categorymemo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android74_categorymemo.databinding.FragmentAddBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddFragment : Fragment() {
    lateinit var fragmentAddBinding: FragmentAddBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentAddBinding = FragmentAddBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentAddBinding.run {

            toolbarAdd.run{
                title = "메모 추가"
                inflateMenu(R.menu.add_menu)

                // 뒤로가기 버튼 활성화
                setNavigationIcon(R.drawable.baseline_arrow_back_24)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.ADD_FRAGMENT)
                }

                // 저장버튼
                setOnMenuItemClickListener {

                    //오늘 날짜 yyyy-mm-dd 포맷으로
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val now = sdf.format(Date())

                    val title = editTextTitleAdd.text.toString()
                    val content = editTextContentAdd.text.toString()

                    Log.i("position","${MainFragment.categoryList[mainActivity.rowPosition].idx}")

                    // 저장할 정보를 가지고 있는 객체를 생성한다.
                    val memo = MemoClass(0, MainFragment.categoryList[mainActivity.rowPosition].idx,title,content,now)

                    // 메모 정보를 저장한다.
                    MemoDAO.insertData(mainActivity, memo)


                    mainActivity.removeFragment(MainActivity.ADD_FRAGMENT)
                    false
                }
            }

        }

        return fragmentAddBinding.root
    }
}