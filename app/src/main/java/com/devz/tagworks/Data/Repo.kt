package com.devz.tagworks.Data

import android.content.Context
import com.devz.tagworks.Constants
import com.devz.tagworks.Models.ProductModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Repo(val context: Context) {

    private val db = Firebase.firestore
    private val constans = Constants()
    private val Product_COLLECTION =constans.Product_COLLECTION

    fun saveProduct(product: ProductModel): Task<Boolean> {
        val result = db.collection(Product_COLLECTION)
            .document(product.pID)
            .set(product)

        return result.continueWithTask { task ->
            if (task.isSuccessful) {
                // The product was saved successfully
                Tasks.forResult(true)
            } else {
                // There was an error saving the product
                Tasks.forResult(false)
            }
        }
    }


}
