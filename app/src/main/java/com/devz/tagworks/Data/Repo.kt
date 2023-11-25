package com.devz.tagworks.Data

import android.content.Context
import com.devz.tagworks.Constants
import com.devz.tagworks.Models.ProductModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Repo(val context: Context) {

    private val db = Firebase.firestore
    private val constans = Constants()
    private val Product_COLLECTION =constans.Product_COLLECTION

    fun saveProduct(product: ProductModel): Task<Void> {
        return db.collection(Product_COLLECTION).document(product.pID).set(product)
    }


}
