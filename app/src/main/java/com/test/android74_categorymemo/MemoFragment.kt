package com.test.android74_categorymemo

import android.os.Bundle
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
    var memoList = mutableListOf<MemoClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentMemoBinding = FragmentMemoBinding.inflate(inflater)
        mainActivity = activity as MainActivity


        fragmentMemoBinding.run{
            toolbarMemo.run{
                title = MainFragment.categoryList[mainActivity.rowPosition].categoryName
                inflateMenu(R.menu.main_menu)
                setOnMenuItemClickListener {
                    mainActivity.replaceFragment(MainActivity.ADD_FRAGMENT, true, true)
                    false
                }
            }

            recyclerViewMemo.run{
                adapter = MainRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)

                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }

        }


        // Inflate the layout for this fragment
        return fragmentMemoBinding.root
    }

    inner class MainRecyclerViewAdapter : RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolderClass>(){

        inner class MainViewHolderClass(rowMainBinding: RowMemoBinding) : RecyclerView.ViewHolder(rowMainBinding.root){
            var textViewRowMemo: TextView

            init {
                textViewRowMemo = rowMainBinding.textViewRowMemo

                rowMainBinding.root.setOnClickListener {
                    mainActivity.rowPosition = adapterPosition
                    mainActivity.replaceFragment(MainActivity.RESULT_FRAGMENT, true, true)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolderClass {
            val rowMemoBinding = RowMemoBinding.inflate(layoutInflater)
            val mainViewHolderClass = MainViewHolderClass(rowMemoBinding)

            rowMemoBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return mainViewHolderClass
        }

        override fun getItemCount(): Int {
            return memoList.size
        }

        override fun onBindViewHolder(holder: MainViewHolderClass, position: Int) {
            holder.textViewRowMemo.text = memoList[position].title
        }
    }

    override fun onResume() {
        super.onResume()

        memoList = MemoDAO.selectAllData(mainActivity)

        // 리사이클러뷰 갱신
        fragmentMemoBinding.recyclerViewMemo.adapter?.notifyDataSetChanged()
    }
}