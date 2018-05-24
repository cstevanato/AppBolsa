package br.com.stv.appbolsa.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Tabela de Ações(Stock)
 */
@RealmClass
open class Stock() : RealmObject(){
    @PrimaryKey
     var id: Long = 0
     var stock: String? = null
     var description: String? = null

    constructor(id: Long,
                stock: String,
                description: String) : this() {
        this.id = id
        this.stock = stock
        this.description = description
    }
}