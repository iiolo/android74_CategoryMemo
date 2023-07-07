package com.test.android74_categorymemo

import android.content.ContentValues
import android.content.Context

class CategoryDAO {

    companion object {
        // Create : 저장
        fun insertData(context: Context, data:String ){
            // 컬럼이름과 데이터를 설정하는 객체
            val contentValues = ContentValues()
            // 컬럼 이름, 값을 지정한다.
            contentValues.put("categoryName", data)

            val dbHelper = DBHelper(context)
            // 데이터를 저장할 테이블의 이름, null값을 어떻게 처리할 것인가
            // 저장할 데이터를 가지고 있는 객체
            dbHelper.writableDatabase.insert("CategoryTable", null, contentValues)
            dbHelper.close()
        }

        // Read Condition : 조건에 맞는 행 하나를 가져온다.
        fun selectData(context: Context, idx:Int):CategoryClass{

            val dbHelper = DBHelper(context)
            val selection = "idx = ?"
            val args = arrayOf("$idx")
            val cursor = dbHelper.writableDatabase.query("CategoryTable", null, selection, args, null, null, null)

            cursor.moveToNext()

            // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("categoryName")


            // 데이터를 가져온다.
            val idx = cursor.getInt(idx1)
            val categoryName = cursor.getString(idx2)



            val categoryClass = CategoryClass(idx,categoryName)

            dbHelper.close()
            return categoryClass
        }

        // Read All : 모든 행을 가져온다
        fun selectAllData(context: Context):MutableList<CategoryClass>{

            val dbHelper = DBHelper(context)

            //idx 기준으로 역순 정렬
            val cursor = dbHelper.writableDatabase.query("CategoryTable", null, null, null, null, null, "idx DESC")

            val dataList = mutableListOf<CategoryClass>()

            while(cursor.moveToNext()){

                // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
                val idx1 = cursor.getColumnIndex("idx")
                val idx2 = cursor.getColumnIndex("categoryName")


                // 데이터를 가져온다.
                val idx = cursor.getInt(idx1)
                val categoryName = cursor.getString(idx2)

                val categoryClass = CategoryClass(idx,categoryName)

                dataList.add(categoryClass)
            }


            dbHelper.close()

            return dataList
        }
        // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
        fun updateData(context: Context, obj:CategoryClass){
            val cv = ContentValues()
            cv.put("categoryName", obj.categoryName)
            val condition = "idx = ?"
            val args = arrayOf("${obj.idx}")
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.update("CategoryTable", cv, condition, args)
            dbHelper.close()
        }


        // Delete : 조건 맞는 행을 삭제한다.
        fun deleteData(context: Context, idx:Int){
            val condition = "idx = ?"
            val args = arrayOf("$idx")

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.delete("CategoryTable", condition, args)
            dbHelper.close()
        }
    }
}