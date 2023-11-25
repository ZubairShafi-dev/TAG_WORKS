package com.devz.tagworks.Ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.devz.tagworks.Models.ProductModel
import com.devz.tagworks.Models.ProductViewModel
import com.devz.tagworks.R
import com.devz.tagworks.SharedPrefManager
import com.devz.tagworks.Utils
import com.devz.tagworks.databinding.FragmentAdminBinding
import kotlinx.coroutines.launch

class AdminFragment : Fragment() {
    private lateinit var binding: FragmentAdminBinding
    private lateinit var utils: Utils
    private val productList = mutableListOf<ProductModel>()
    private val productVieModel : ProductViewModel  by viewModels()
    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var mContext: Context
    private val splashDelayMillis = 2000 // 2 seconds

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminBinding.inflate(inflater, container, false)
        val view = binding.root
        mContext = requireContext()
        utils = Utils(mContext)
        sharedPrefManager = SharedPrefManager(requireContext())

        binding.fabAdd.setOnClickListener {
            showSelectngDialog()
        }
        return view
    }

    private fun showSelectngDialog() {
        val builder = AlertDialog.Builder(requireContext()) // or context
        val dialogView = layoutInflater.inflate(R.layout.selectingmodule, null)
        builder.setView(dialogView)

        val aluminium = dialogView.findViewById<TextView>(R.id.Admin)
        val glass = dialogView.findViewById<TextView>(R.id.customer)

        val dialog = builder.create() // Move this line here

        aluminium.setOnClickListener {
            showAddingDialog()
            dialog.dismiss() // Dismiss the dialog when aluminium is clicked
        }

        glass.setOnClickListener {
            showAddingDialog()
            dialog.dismiss() // Dismiss the dialog when glass is clicked
        }

        dialog.show() // Move this line here
    }
    private fun showAddingDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.adding_dialogbox, null)
        builder.setView(dialogView)
        val saveBtn = dialogView.findViewById<TextView>(R.id.savebtn)

        saveBtn.setOnClickListener {
          saveProduct()
          //  dialog.dismiss() // Dismiss the dialog when saveBtn is clicked
        }

        builder.setNegativeButton("", null)
        val dialog = builder.create()
        dialog.show()
    }
    private fun saveProduct() {
        utils.startLoadingAnimation()

        lifecycleScope.launch {
            for (product in productList) {
                productVieModel.saveProductToFirebase(product).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Product saved successfully", Toast.LENGTH_SHORT).show()

                        // Store the product in SharedPreferences
                        storeProductInSharedPreferences(product)

                        utils.endLoadingAnimation()
                    } else {
                        Toast.makeText(requireContext(), "Failed to save product", Toast.LENGTH_SHORT).show()
                        utils.endLoadingAnimation()
                    }
                }
            }
        }
    }
//    private fun dismissDialog() {
//        // Dismiss the dialog when called
//        // Make sure to replace "dialog" with your actual dialog instance
//        val dialog = builder.create()
//        dialog.dismiss()
//    }

    private fun storeProductInSharedPreferences(product: ProductModel) {
        // Retrieve the existing list from SharedPreferences
        val productList = sharedPrefManager.getProductList()
        // Add the new product to the list
        productList.add(product)
        // Save the updated list back to SharedPreferences
        sharedPrefManager.putProductList(productList)
    }
}
