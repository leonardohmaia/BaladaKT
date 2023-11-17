import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.balada.R

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNome: EditText
    private lateinit var editTextValor: EditText
    private lateinit var editTextData: EditText
    private lateinit var checkBoxGanho: CheckBox
    private lateinit var checkBoxGasto: CheckBox
    private lateinit var btnCadastrar: Button
    private lateinit var btnResumo: Button

    private lateinit var dbHelper: DBHelper
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNome = findViewById(R.id.editTextNome)
        editTextValor = findViewById(R.id.editTextValor)
        editTextData = findViewById(R.id.editTextData)
        checkBoxGanho = findViewById(R.id.checkBoxGanho)
        checkBoxGasto = findViewById(R.id.checkBoxGasto)
        btnCadastrar = findViewById(R.id.btnCadastrar)
        btnResumo = findViewById(R.id.btnResumo)

        dbHelper = DBHelper(this)
        db = dbHelper.writableDatabase

        btnCadastrar.setOnClickListener {
            cadastrarTransacao()
        }

        btnResumo.setOnClickListener {
            // Implemente a chamada para a Activity de Resumo aqui
        }
    }

    private fun cadastrarTransacao() {
        val nome = editTextNome.text.toString()
        val valor = editTextValor.text.toString().toDoubleOrNull()
        val data = editTextData.text.toString()
        val isGanho = checkBoxGanho.isChecked
        val isGasto = checkBoxGasto.isChecked

        if (nome.isNotEmpty() && valor != null && (isGanho || isGasto)) {
            val tipo = if (isGanho) "Ganho" else "Gasto"

            val values = ContentValues().apply {
                put("nome", nome)
                put("valor", valor)
                put("data", data)
                put("tipo", tipo)
            }

            val newRowId = db.insert("transacoes", null, values)
            Toast.makeText(this, "Transação cadastrada com ID $newRowId", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
        }
    }
}
