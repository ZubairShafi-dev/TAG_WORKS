package com.devz.tagworks.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.devz.tagworks.Constants
import com.devz.tagworks.Data.Repo
import com.google.android.gms.tasks.Task

class ProductViewModel(context: Application) : AndroidViewModel(context) {

    private val repo = Repo(context)
    private val constants = Constants()

    fun saveProductToFirebase(product:ProductModel): Task<Void> {
        return repo.saveProduct(product)
    }
}
