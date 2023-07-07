package com.test.android74_categorymemo


import android.content.ContentValues
import android.content.Context

class DAO {

    companion object {
        // Create : 저장
        fun insertData(context: Context, data:MemoClass ){
            // 컬럼이름과 데이터를 설정하는 객체
            val contentValues = ContentValues()
            // 컬럼 이름, 값을 지정한다.
            contentValues.put("title", data.title)
            contentValues.put("content", data.content)
            contentValues.put("dateData", data.dateData)

            val dbHelper = DBHelper(context)
            // 데이터를 저장할 테이블의 이름, null값을 어떻게 처리할 것인가
            // 저장할 데이터를 가지고 있는 객체
            dbHelper.writableDatabase.insert("MemoTable", null, contentValues)
            dbHelper.close()
        }

        // Read Condition : 조건에 맞는 행 하나를 가져온다.
        fun selectData(context: Context, idx:Int):MemoClass{

            val dbHelper = DBHelper(context)
            val selection = "idx = ?"
            val args = arrayOf("$idx")
            val cursor = dbHelper.writableDatabase.query("MemoTable", null, selection, args, null, null, null)

            cursor.moveToNext()

            // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("title")
            val idx3 = cursor.getColumnIndex("content")
            val idx4 = cursor.getColumnIndex("dateData")

            // 데이터를 가져온다.
            val idx = cursor.getInt(idx1)
            val title = cursor.getString(idx2)
            val content = cursor.getString(idx3)
            val dateData = cursor.getString(idx4)


            val memoClass = MemoClass(idx,title,content,dateData)

            dbHelper.close()
            return memoClass
        }

        // Read All : 모든 행을 가져온다
        fun selectAllData(context: Context):MutableList<MemoClass>{

            val dbHelper = DBHelper(context)

            //idx 기준으로 역순 정렬
            val cursor = dbHelper.writableDatabase.query("MemoTable", null, null, null, null, null, "idx DESC")

            val dataList = mutableListOf<MemoClass>()

            while(cursor.moveToNext()){
                val idx1 = cursor.getColumnIndex("idx")
                val idx2 = cursor.getColumnIndex("title")
                val idx3 = cursor.getColumnIndex("content")
                val idx4 = cursor.getColumnIndex("dateData")

                val idx = cursor.getInt(idx1)
                val title = cursor.getString(idx2)
                val content = cursor.getString(idx3)
                val dateData = cursor.getString(idx4)


                val memoClass = MemoClass(idx,title,content,dateData)

                dataList.add(memoClass)
            }


            dbHelper.close()

            return dataList
        }
        // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
        fun updateData(context:Context, obj:MemoClass){
            val cv = ContentValues()
            cv.put("title", obj.title)
            cv.put("content", obj.content)
            cv.put("dateData", obj.dateData)
            val condition = "idx = ?"
            val args = arrayOf("${obj.idx}")
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.update("MemoTable", cv, condition, args)
            dbHelper.close()
        }


        // Delete : 조건 맞는 행을 삭제한다.
        fun deleteData(context:Context, idx:Int){
            val condition = "idx = ?"
            val args = arrayOf("$idx")

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.delete("MemoTable", condition, args)
            dbHelper.close()
        }
    }

}