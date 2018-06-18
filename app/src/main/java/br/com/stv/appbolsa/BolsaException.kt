package br.com.stv.appbolsa

class BolsaException(code: String, override var message:String): Exception(message)