import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE transacoes (" +
                    "_id INTEGER PRIMARY KEY," +
                    "nome TEXT," +
                    "valor REAL," +
                    "data TEXT," +
                    "tipo TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS transacoes")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "transacoes.db"
        const val DATABASE_VERSION = 1
    }
}
