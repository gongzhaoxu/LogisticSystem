package com.example.logisticsystem
import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    //数据库中获取的运单信息
    var logisticList = ArrayList<LogisticView.LogisticItem>()

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

    //创建数据库
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createLogistic)
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
//        Toast.makeText(this, "Create succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    //插入数据
    //录入不录入id
    fun insertData(db: SQLiteDatabase,  src: String,  dest: String,  senderName: String,
                    senderTel: String,  accepterName: String,  accepterTel: String,
                    itemName: String,  itemNum: String,  payAlready: String,  payDest: String) {
        db.execSQL(
            "insert into Logistic ( src , dest , senderName , senderTel , accepterName ," +
                    " accepterTel , itemName , itemNum , payAlready , payDest ) values( ?, ?, ?, ?, ?, ?,?, ?, ?, ?)",
            arrayOf( src, dest, senderName,senderTel,accepterName,
                accepterTel,itemName,itemNum,payAlready,payDest)
        )
        Toast.makeText(context, "录入成功", Toast.LENGTH_SHORT).show()
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

                val logisticItem: LogisticView.LogisticItem = LogisticView.LogisticItem(id , src , dest ,
                    senderName , senderTel , accepterName ,accepterTel , itemName ,
                    itemNum , payAlready , payDest)

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
    fun delete(db: SQLiteDatabase){
        var sql = "delete from Logistic "
        db.execSQL(sql)
        Toast.makeText(context, "delete succeeded", Toast.LENGTH_SHORT).show()

    }
}