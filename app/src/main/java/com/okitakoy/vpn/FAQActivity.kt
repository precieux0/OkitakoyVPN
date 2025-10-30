package com.okitakoy.vpn

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class FAQActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        
        setupToolbar()
        displayFAQContent()
    }
    
    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "FAQ - OkitakoyVpn"
    }
    
    private fun displayFAQContent() {
        val faqText = findViewById<TextView>(R.id.faqText)
        
        val faqContent = """
            FAQ - OkitakoyVpn
            
            Développeur: Okitakoy Précieux
            Email: okitakoyprecieux@gmail.com
            WhatsApp: +243982730192
            
            Q: Qu'est-ce que OkitakoyVpn?
            R: Une application VPN avancée supportant multiple protocoles.
            
            Q: Quels protocoles sont supportés?
            R: V2Ray, SSH, WebSocket, UDP Custom, UDP Request.
            
            Q: Comment importer une configuration?
            R: Utilisez le bouton "Importer Configuration".
            
            Q: Comment exporter une configuration?
            R: Utilisez le bouton "Exporter Configuration".
            
            Q: Les fichiers .precieux, c'est quoi?
            R: Ce sont des fichiers de configuration générés par l'application.
            
            Q: L'application est-elle gratuite?
            R: Oui, entièrement gratuite et open source.
            
            Pour tout support supplémentaire, contactez-moi directement!
            
            Merci d'utiliser OkitakoyVpn!
        """.trimIndent()
        
        faqText.text = faqContent
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
