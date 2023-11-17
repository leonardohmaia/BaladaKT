import android.database.Cursor
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.balada.R

class ResumoActivity : AppCompatActivity() {

    private lateinit var txtSaldoAtual: TextView
    private lateinit var txtTotalGasto: TextView
    private lateinit var txtTotalGanho: TextView

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo)

        txtSaldoAtual = findViewById(R.id.txtSaldoAtual)
        txtTotalGasto = findViewById(R.id.txtTotalGasto)
        txtTotalGanho = findViewById(R.id.txtTotalGanho)

        dbHelper = DBHelper(this)

        exibirResumo()
    }

    private fun exibirResumo() {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            "SUM(CASE WHEN tipo = 'Ganho' THEN valor ELSE 0 END) AS total_ganho",
            "SUM(CASE WHEN tipo = 'Gasto' THEN valor ELSE 0 END) AS total_gasto"
        )

        val cursor: Cursor = db.query(
            "transacoes",
            projection,
            null,
            null,
            null,
            null,
            null
        )

        cursor.moveToFirst()

        val totalGanho = cursor.getDouble(cursor.getColumnIndex("total_ganho"))
        val totalGasto = cursor.getDouble(cursor.getColumnIndex("total_gasto"))

        val saldoAtual = totalGanho - totalGasto

        txtSaldoAtual.text = "Saldo Atual: $saldoAtual"
        txtTotalGanho.text = "Total Ganho: $totalGanho"
        txtTotalGasto.text = "Total Gasto: $totalGasto"
    }
}
