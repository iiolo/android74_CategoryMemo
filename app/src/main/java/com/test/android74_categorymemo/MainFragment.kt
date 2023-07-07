package com.test.android74_categorymemo

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android74_categorymemo.databinding.FragmentMainBinding
import com.test.android74_categorymemo.databinding.RowMainBinding
import java.util.Collections
import java.util.Locale.Category


class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var mainActivity: MainActivity

    // 카테고리의 정보를 담을 리스트
    companion object{
        var categoryList = mutableListOf<CategoryClass>()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentMainBinding = FragmentMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity


        fragmentMainBinding.run{
            toolbarMain.run{
                title = "카테고리 목록"
                inflateMenu(R.menu.main_menu)
                setOnMenuItemClickListener {
                    // 다이얼로그 생성을 위한 객체를 생성한다.
                    val builder = AlertDialog.Builder(mainActivity)

                    // 타이틀
                    builder.setTitle("카테고리 추가")

                    // 카테고리 추가 입력
                    val categoryAdd = EditText(mainActivity)
                    categoryAdd.hint = "추가할 카테고리 이름을 입력하세요."
                    builder.setView(categoryAdd)


                    // 버튼 배치
                    builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                        CategoryDAO.insertData(mainActivity,categoryAdd.text.toString())
                        categoryList = CategoryDAO.selectAllData(mainActivity)

                        // 리사이클러뷰 갱신
                        fragmentMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()

                    }
                    builder.setNegativeButton("취소",null)

                    // 다이얼로그를 띄운다.
                    builder.show()


                    false
                }
            }

            recyclerViewMain.run{
                adapter = MainRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }

        }


        // Inflate the layout for this fragment
        return fragmentMainBinding.root
    }

    inner class MainRecyclerViewAdapter : RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolderClass>(){

        inner class MainViewHolderClass(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){
            var textViewRowMain:TextView

            init {
                textViewRowMain = rowMainBinding.textViewRowMain

                rowMainBinding.root.setOnClickListener {
                    mainActivity.rowPosition = adapterPosition
                    mainActivity.replaceFragment(MainActivity.MEMO_FRAGMENT, true, true)
                }

                // 카테고리 수정 및 삭제 메뉴 구현
                // 항목 하나의 View에 컨텍스트 메뉴 생성 이벤트를 붙혀준다.
                rowMainBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
                    menu.setHeaderTitle("${categoryList[adapterPosition].categoryName}")
                    mainActivity.menuInflater.inflate(R.menu.row_menu, menu)

                    // 첫 번째 메뉴(카테고리 수정)에 대한 이벤트 처리
                    menu[0].setOnMenuItemClickListener {
                        // 다이얼로그 생성을 위한 객체를 생성한다.
                        val builder = AlertDialog.Builder(mainActivity)

                        // 타이틀
                        builder.setTitle("카테고리 수정")

                        // 카테고리 추가 입력
                        val categoryAdd = EditText(mainActivity)
                        categoryAdd.hint = "수정할 카테고리 이름을 입력하세요."
                        categoryAdd.setText(categoryList[adapterPosition].categoryName)
                        builder.setView(categoryAdd)


                        // 버튼 배치
                        builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                            val cIdx = categoryList[adapterPosition].idx
                            val cName = categoryAdd.text.toString()
                            val cClass = CategoryClass(cIdx,cName)
                            CategoryDAO.updateData(mainActivity,cClass)

                            // 리사이클러뷰 갱신
                            categoryList = CategoryDAO.selectAllData(mainActivity)
                            fragmentMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()

                        }
                        builder.setNegativeButton("취소",null)

                        // 다이얼로그를 띄운다.
                        builder.show()



                        false
                    }


                    // 두 번째 메뉴(카테고리 삭제)에 대한 이벤트 처리
                    menu[1].setOnMenuItemClickListener {
                        // 카테고리 삭제시 해당 카테고리에 속한 메모들도 삭제
                        MemoDAO.deleteData(mainActivity, categoryList[adapterPosition].idx)

                        // 카테고리 삭제
                        CategoryDAO.deleteData(mainActivity,categoryList[adapterPosition].idx)

                        // 리사이클러뷰 갱신
                        categoryList = CategoryDAO.selectAllData(mainActivity)
                        fragmentMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
                        false
                    }
                }
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolderClass {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val mainViewHolderClass = MainViewHolderClass(rowMainBinding)

            rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return mainViewHolderClass
        }

        override fun getItemCount(): Int {
            return categoryList.size
        }

        override fun onBindViewHolder(holder: MainViewHolderClass, position: Int) {
            holder.textViewRowMain.text = categoryList[position].categoryName
        }
    }

    override fun onResume() {
        super.onResume()

        categoryList = CategoryDAO.selectAllData(mainActivity)

        // 리사이클러뷰 갱신
        fragmentMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
    }


}