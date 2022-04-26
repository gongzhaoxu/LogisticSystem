package com.example.logisticsystem

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class MyDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    //数据库中获取的运单信息
    var logisticList = ArrayList<LogisticView.LogisticItem>()
    var userList = ArrayList<User>()
    private val createLogistic = "create table Logistic (" +
            "id integer primary key autoincrement," +
            "src text," +
            "dest text," +
            "senderName text," +
            "senderTel text," +
            "accepterName text," +
            "accepterTel text," +
            "itemName text," +
            "itemNum text," +
            "payAlready text," +
            "payDest text)"

    private val createUser = "create table User (" +
            "user_id integer primary key autoincrement," +
            "user_department text," +
            "user_name text," +
            "user_login text," +
            "user_passwd text," +
            "user_tel text)"


    private val createCurrentUser = "create table CurrentUser ( user_login text ) "

    //创建数据库
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createLogistic)
        db.execSQL(createUser)
        db.execSQL(createCurrentUser)
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL(createLogistic)
//        db.execSQL(createUser)
//        onCreate(db)
//        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }

    //插入运单数据
    //录入不录入id
    fun insertData(
        db: SQLiteDatabase, src: String, dest: String, senderName: String,
        senderTel: String, accepterName: String, accepterTel: String,
        itemName: String, itemNum: String, payAlready: String, payDest: String
    ) {
        db.execSQL(
            "insert into Logistic ( src , dest , senderName , senderTel , accepterName ," +
                    " accepterTel , itemName , itemNum , payAlready , payDest ) values( ?, ?, ?, ?, ?, ?,?, ?, ?, ?)",
            arrayOf(
                src, dest, senderName, senderTel, accepterName,
                accepterTel, itemName, itemNum, payAlready, payDest
            )
        )
//        Toast.makeText(context, "录入成功", Toast.LENGTH_SHORT).show()
    }

    /**
     * 插入用户
     * 录入不录入id
     */

    fun insertUser(
        db: SQLiteDatabase, user_department: String,
        user_name: String, user_login: String, user_passwd: String, user_tel: String
    ) {
        db.execSQL(
            "insert into User (   user_department , user_name , user_login , user_passwd , user_tel  ) values( ?,?, ?, ?, ?)",
            arrayOf(user_department, user_name, user_login, user_passwd, user_tel)
        )
        Log.e("插入新用户insertUser:", user_login)

    }

    /**
     * 更新用户信息
     */
    fun completeUserInfo( db: SQLiteDatabase,user_login: String,user_department: String,
                          user_name: String, user_tel: String){
        var sql="update User set user_department= \'$user_department\' , user_name=\'$user_name\',user_tel=\'$user_tel\' where " +
                "user_login=\'$user_login\'"
        db.execSQL(sql)
        Log.e("完善信息完成，完善的部门为：$user_department","完善的姓名为$user_name,完善的电话为$user_tel")
    }

    /**
     * 存入当前用户
     */
    fun insertCurrentUser(db: SQLiteDatabase, user_login: String) {
//        var sql = "delete from CurrentUser "
//        db.execSQL(sql)
        if (user_login == "null") {
            Log.e("user_login", "user_login==null")
            return
        } else if (user_login == "") {
            Log.e("user_login", "user_login==\"\"")
            return
        } else {
            db.execSQL(
                "insert into CurrentUser ( user_login  ) values( ?)",
                arrayOf(user_login)
            )
            Log.e("插入当前用户完成$user_login", "插入当前用户完成$user_login")
            return
        }

//        Toast.makeText(context, "插入当前用户完成$user_login", Toast.LENGTH_SHORT).show()

    }

    /**
     * 删除当前用户表数据
     */
    fun deleteCurrentUser(db: SQLiteDatabase) {
        var sql = "delete from CurrentUser "
        db.execSQL(sql)
        Log.e("删除当前用户完成", "删除当前用户完成$")

    }

    /**
     * 获取当前用户
     */
    @SuppressLint("Range", "LongLogTag")
    fun getCurrentUser(db: SQLiteDatabase): String {
        var sql = "select * from CurrentUser"
        val cursor = db.rawQuery(sql, null)
        var user_login = ""
        if (cursor.moveToFirst()) {
            do {
                user_login = cursor.getString(cursor.getColumnIndex("user_login"))
            } while (cursor.moveToNext())
        }
        return user_login
        Log.e("数据库获取当前用户getCurrentUser完成$user_login", "获取当前用户完成$user_login")
        cursor.close()
    }

    /**
     * 获取用户信息用于在mine显示
     */
    @SuppressLint("Range")
    fun getUser(db: SQLiteDatabase, user_login: String): ArrayList<User> {
        var sql = "select * from User where user_login = \'$user_login\'"
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val user_id = cursor.getString(cursor.getColumnIndex("user_id"))
                val user_department = cursor.getString(cursor.getColumnIndex("user_department"))
                val user_name = cursor.getString(cursor.getColumnIndex("user_name"))
                val user_login = cursor.getString(cursor.getColumnIndex("user_login"))
                val user_passwd = cursor.getString(cursor.getColumnIndex("user_passwd"))
                val user_tel = cursor.getString(cursor.getColumnIndex("user_tel"))
                var user =
                    User(user_id, user_department, user_name, user_login, user_passwd, user_tel)
                userList.add(user)
            } while (cursor.moveToNext())
        }
        if(userList.size==0){
            Log.e("数据库中不存在此用户","!")
        }else{
            Log.e("查找用户",userList[0].user_login.toString())
        }
        return userList
        cursor.close()
    }

    //筛选数据,返回运单list
    @SuppressLint("Range")
    fun selectData(db: SQLiteDatabase): ArrayList<LogisticView.LogisticItem> {
        val cursor = db.rawQuery("select * from Logistic", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex("id"))
                val src = cursor.getString(cursor.getColumnIndex("src"))
                val dest = cursor.getString(cursor.getColumnIndex("dest"))
                val senderName = cursor.getString(cursor.getColumnIndex("senderName"))

                val senderTel = cursor.getString(cursor.getColumnIndex("senderTel"))
                val accepterName = cursor.getString(cursor.getColumnIndex("accepterName"))
                val accepterTel = cursor.getString(cursor.getColumnIndex("accepterTel"))
                val itemName = cursor.getString(cursor.getColumnIndex("itemName"))

                val itemNum = cursor.getString(cursor.getColumnIndex("itemNum"))
                val payAlready = cursor.getString(cursor.getColumnIndex("payAlready"))
                val payDest = cursor.getString(cursor.getColumnIndex("payDest"))

                val logisticItem: LogisticView.LogisticItem = LogisticView.LogisticItem(
                    id, src, dest,
                    senderName, senderTel, accepterName, accepterTel, itemName,
                    itemNum, payAlready, payDest
                )

                logisticList.add(logisticItem)
            } while (cursor.moveToNext())
        }
        return logisticList
        cursor.close()
    }

//    //筛选某一列
//    @SuppressLint("Range")
//            /**
//             * content为输入的筛选信息，type为用户选择的查询属性
//             */
//    fun selectColumn(db: SQLiteDatabase, content: String, type: String): ArrayList<BookInfo.Book> {
//        var sql = "select * from Book where $type = \'$content\'"
//        val cursor = db.rawQuery(sql, null)
//        if (cursor.moveToFirst()) {
//            do {
//                val name = cursor.getString(cursor.getColumnIndex("name"))
//                val author = cursor.getString(cursor.getColumnIndex("author"))
//                val price = cursor.getString(cursor.getColumnIndex("price"))
//                val pages = cursor.getString(cursor.getColumnIndex("pages"))
//                val book: BookInfo.Book = BookInfo.Book(name, author, price, pages)
//                bookList.add(book)
//            } while (cursor.moveToNext())
//        }
//        return bookList
//        cursor.close()
//    }

    /**
     * 删除全表
     */
    fun delete(db: SQLiteDatabase) {
        var sql = "delete from Logistic "
        db.execSQL(sql)
        Toast.makeText(context, "delete succeeded", Toast.LENGTH_SHORT).show()

    }
}