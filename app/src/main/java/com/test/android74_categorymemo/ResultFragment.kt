package com.test.android74_categorymemo

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.test.android74_categorymemo.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    lateinit var fragmentResultBinding: FragmentResultBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentResultBinding = FragmentResultBinding.inflate(inflater)
        mainActivity = activity as MainActivity


        val memoList = MemoDAO.selectAllData(mainActivity)

        // 선택한 행 번째의 객체에서 데이터를 가져와 출력한다.
        fragmentResultBinding.run{
            toolbarResult.run {
                title = "메모 읽기"
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.result_menu)

                // 뒤로가기 버튼 활성화
                setNavigationIcon(R.drawable.baseline_arrow_back_24)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.RESULT_FRAGMENT)
                }

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuResultEdit -> {
                            mainActivity.replaceFragment(MainActivity.EDIT_FRAGMENT, true, true)
                        }
                        else -> {
                            //삭제 버튼 누를 경우 다이얼로그가 뜨게하기

                            // 다이얼로그 생성을 위한 객체를 생성한다.
                            val builder = AlertDialog.Builder(mainActivity)

                            // 타이틀
                            builder.setTitle("메모 삭제")

                            // 메시지
                            builder.setMessage("메모를 삭제 하겠습니까?")



                            // 버튼 배치
                            builder.setPositiveButton("삭제"){ dialogInterface: DialogInterface, i: Int ->
                                MemoDAO.deleteData(mainActivity, memoList[mainActivity.rowPosition].idx)
                                mainActivity.removeFragment(MainActivity.RESULT_FRAGMENT)
                            }

                            builder.setNegativeButton("취소", null)


                            // 다이얼로그를 띄운다.
                            builder.show()



                        }
                    }

                    false
                }
            }

            textViewMemoTitle.text = memoList[mainActivity.rowPosition].title
            textViewMemoDate.text = memoList[mainActivity.rowPosition].dateData
            textViewMemoContent.text = memoList[mainActivity.rowPosition].content
        }

        return fragmentResultBinding.root
    }
}