package com.test.android74_categorymemo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android74_categorymemo.databinding.FragmentMemoBinding
import com.test.android74_categorymemo.databinding.RowMemoBinding

class MemoFragment : Fragment() {
    lateinit var fragmentMemoBinding: FragmentMemoBinding
    lateinit var mainActivity: MainActivity



    // 메모의 정보를 담을 리스트
    companion object {
        var memoList = mutableListOf<MemoClass>()
        var memoIndex = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMemoBinding = FragmentMemoBinding.inflate(inflater)
        mainActivity = activity as MainActivity


        fragmentMemoBinding.run {
            toolbarMemo.run {
                title = MainFragment.categoryList[mainActivity.rowPosition].categoryName
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.main_menu)

                // 뒤로가기 버튼 활성화
                setNavigationIcon(R.drawable.baseline_arrow_back_24)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MEMO_FRAGMENT)
                }

                setOnMenuItemClickListener {
                    mainActivity.replaceFragment(MainActivity.ADD_FRAGMENT, true, true)
                    false
                }
            }

            recyclerViewMemo.run {
                adapter = MemoRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)

                addItemDecoration(
                    DividerItemDecoration(
                        mainActivity,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }

        }


        // Inflate the layout for this fragment
        return fragmentMemoBinding.root
    }

    inner class MemoRecyclerViewAdapter :
        RecyclerView.Adapter<MemoRecyclerViewAdapter.MemoViewHolderClass>() {

        inner class MemoViewHolderClass(rowMemoBinding: RowMemoBinding) :
            RecyclerView.ViewHolder(rowMemoBinding.root) {
            var textViewRowMemo: TextView

            init {
                textViewRowMemo = rowMemoBinding.textViewRowMemo

                rowMemoBinding.root.setOnClickListener {
                    if(memoList.isNotEmpty()){
                        mainActivity.memoPosition = memoList[adapterPosition].idx
                    }

                    mainActivity.replaceFragment(MainActivity.RESULT_FRAGMENT, true, true)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolderClass {
            val rowMemoBinding = RowMemoBinding.inflate(layoutInflater)
            val mainViewHolderClass = MemoViewHolderClass(rowMemoBinding)

            rowMemoBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return mainViewHolderClass
        }

        override fun getItemCount(): Int {
            return memoList.size
        }

        override fun onBindViewHolder(holder: MemoViewHolderClass, position: Int) {
            holder.textViewRowMemo.text = memoList[position].title
        }
    }

    override fun onResume() {
        super.onResume()
        val cIdx = MainFragment.categoryList[mainActivity.rowPosition].idx
        val tempList = MemoDAO.selectAllData(mainActivity)
        memoList = tempList.filter { memoClass ->  memoClass.categoryIdx == cIdx}.toMutableList()

        // 리사이클러뷰 갱신
        fragmentMemoBinding.recyclerViewMemo.adapter?.notifyDataSetChanged()
    }
}