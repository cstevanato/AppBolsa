package br.com.stv.appbolsa.extension

import br.com.stv.appbolsa.BolsaException
import br.com.stv.appbolsa.ui.activity.buy.BuyData

fun BuyData.NoteValidateRequerid(note: String): BuyData {
    if(note.isNullOrBlank()) {
        throw  BolsaException("NOTE_000001", "Note Required")
    }
    this.note = note
    return this
}