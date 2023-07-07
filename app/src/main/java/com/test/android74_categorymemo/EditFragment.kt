package com.test.android74_categorymemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android74_categorymemo.databinding.FragmentEditBinding


class EditFragment : Fragment() {
    lateinit var fragmentEditBinding: FragmentEditBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentEditBinding = FragmentEditBinding.inflate(inflater)
        mainActivity = activity as MainActivity


        val memoList = MemoDAO.selectAllData(mainActivity)

        fragmentEditBinding.run{
            // 사용자가 클릭한 메모 정보를 editText에 보이게 하기
            editTextTitleEdit.setText(memoList[mainActivity.rowPosition].title)
            editTextContentEdit.setText(memoList[mainActivity.rowPosition].content)

            // 수정을 위한 기존 메모 정보의 idx, 날짜 가져오기
            val idx = memoList[mainActivity.rowPosition].idx
            val cIdx = memoList[mainActivity.rowPosition].categoryIdx
            val dateData = memoList[mainActivity.rowPosition].dateData

            toolbarEdit.run {
                title = "메모 수정"
                inflateMenu(R.menu.edit_menu)

                // 뒤로가기 버튼 활성화
                setNavigationIcon(R.drawable.baseline_arrow_back_24)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.EDIT_FRAGMENT)
                }

                setOnMenuItemClickListener {
                    val editTitle = editTextTitleEdit.text.toString()
                    val editContent = editTextContentEdit.text.toString()

                    val memo = MemoClass(idx,cIdx, editTitle,editContent,dateData)

                    // 수정된 메모 업데이트
                    MemoDAO.updateData(mainActivity,memo)

                    mainActivity.removeFragment(MainActivity.EDIT_FRAGMENT)

                    false
                }
            }



        }

        return fragmentEditBinding.root
    }
}