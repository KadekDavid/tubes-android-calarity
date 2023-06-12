package org.d3if0126.myapplication.ui.keranjang
//
//import android.os.Bundle
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import org.d3if0126.myapplication.R
//import org.d3if0126.myapplication.databinding.ActivityKeranjangBinding
//
//class KeranjangFragment : AppCompatActivity() {
//
//    private lateinit var binding: ActivityKeranjangBinding
//    private lateinit var selectItem:TextView
//    private lateinit var dialog: BottomSheetDialog
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//        selectItem = findViewById(R.id.floatingActionButton)
//        showBottomSheet()
//    }
//
//    private fun showBottomSheet() {
//        val dialogView = layoutInflater.inflate(R.layout.fragment_keranjang, null)
//        dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
//        dialog.setContentView(dialogView)
//    }
//
//
//
//}