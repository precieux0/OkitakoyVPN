package com.okitakoy.vpn

import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {
    
    private lateinit var connectButton: Button
    private lateinit var statusText: TextView
    private lateinit var statusDescription: TextView
    private lateinit var protocolSpinner: AutoCompleteTextView
    private lateinit var serverAddressEditText: EditText
    private lateinit var serverPortEditText: EditText
    private lateinit var importButton: Button
    private lateinit var exportButton: Button
    
    private var isConnected = false
    private var isConnecting = false
    
    companion object {
        private const val VPN_REQUEST_CODE = 1000
        private const val IMPORT_CONFIG_REQUEST_CODE = 1001
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initializeViews()
        setupProtocolSpinner()
        setupClickListeners()
        setupBottomNavigation()
        loadDefaultConfigurations()
    }
    
    private fun initializeViews() {
        connectButton = findViewById(R.id.connectButton)
        statusText = findViewById(R.id.statusText)
        statusDescription = findViewById(R.id.statusDescription)
        protocolSpinner = findViewById(R.id.protocolSpinner)
        serverAddressEditText = findViewById(R.id.serverAddressEditText)
        serverPortEditText = findViewById(R.id.serverPortEditText)
        importButton = findViewById(R.id.importButton)
        exportButton = findViewById(R.id.exportButton)
    }
    
    private fun setupProtocolSpinner() {
        val protocols = arrayOf(
            getString(R.string.v2ray),
            getString(R.string.ssh),
            getString(R.string.websocket),
            getString(R.string.udp_custom),
            getString(R.string.udp_request)
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, protocols)
        protocolSpinner.setAdapter(adapter)
        protocolSpinner.setText(protocols[0], false)
    }
    
    private fun setupClickListeners() {
        connectButton.setOnClickListener {
            if (isConnected) {
                disconnectVpn()
            } else if (!isConnecting) {
                connectVpn()
            }
        }
        
        importButton.setOnClickListener {
            importConfiguration()
        }
        
        exportButton.setOnClickListener {
            exportConfiguration()
        }
    }
    
    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                R.id.navigation_faq -> {
                    startActivity(Intent(this, FAQActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
    
    private fun loadDefaultConfigurations() {
        // Charger les valeurs par défaut
        serverAddressEditText.setText("server.example.com")
        serverPortEditText.setText("443")
    }
    
    private fun connectVpn() {
        if (!validateInputs()) {
            return
        }
        
        val intent = VpnService.prepare(this)
        if (intent != null) {
            startActivityForResult(intent, VPN_REQUEST_CODE)
        } else {
            startVpnService()
        }
    }
    
    private fun disconnectVpn() {
        lifecycleScope.launch {
            isConnecting = true
            updateUI()
            
            // Simulation de déconnexion
            kotlinx.coroutines.delay(1000)
            
            isConnected = false
            isConnecting = false
            updateUI()
            
            Toast.makeText(this@MainActivity, "VPN déconnecté", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun startVpnService() {
        lifecycleScope.launch {
            isConnecting = true
            updateUI()
            
            // Simulation de connexion
            kotlinx.coroutines.delay(2000)
            
            isConnected = true
            isConnecting = false
            updateUI()
            
            Toast.makeText(this@MainActivity, "VPN connecté", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun validateInputs(): Boolean {
        val serverAddress = serverAddressEditText.text.toString().trim()
        val serverPort = serverPortEditText.text.toString().trim()
        
        if (serverAddress.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer une adresse de serveur", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (serverPort.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer un port", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (serverPort.toIntOrNull() == null) {
            Toast.makeText(this, "Port invalide", Toast.LENGTH_SHORT).show()
            return false
        }
        
        return true
    }
    
    private fun updateUI() {
        when {
            isConnecting -> {
                statusText.text = getString(R.string.status_connecting)
                statusDescription.text = "Établissement de la connexion..."
                connectButton.text = "Connexion..."
                connectButton.isEnabled = false
            }
            isConnected -> {
                statusText.text = getString(R.string.status_connected)
                statusDescription.text = "Connecté via ${protocolSpinner.text}"
                connectButton.text = getString(R.string.disconnect)
                connectButton.isEnabled = true
            }
            else -> {
                statusText.text = getString(R.string.status_disconnected)
                statusDescription.text = "Prêt à se connecter"
                connectButton.text = getString(R.string.connect)
                connectButton.isEnabled = true
            }
        }
    }
    
    private fun importConfiguration() {
        // Pour l'instant, simulation d'importation
        Toast.makeText(this, "Fonction d'importation à implémenter", Toast.LENGTH_SHORT).show()
        
        // Ici vous pouvez ajouter la logique pour lire des fichiers .precieux
        // val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        //     addCategory(Intent.CATEGORY_OPENABLE)
        //     type = "*/*"
        // }
        // startActivityForResult(intent, IMPORT_CONFIG_REQUEST_CODE)
    }
    
    private fun exportConfiguration() {
        if (!validateInputs()) {
            Toast.makeText(this, "Configuration invalide", Toast.LENGTH_SHORT).show()
            return
        }
        
        val config = VPNConfiguration(
            protocol = protocolSpinner.text.toString(),
            serverAddress = serverAddressEditText.text.toString().trim(),
            serverPort = serverPortEditText.text.toString().trim().toIntOrNull() ?: 0
        )
        
        exportConfigurationToFile(config)
    }
    
    private fun exportConfigurationToFile(config: VPNConfiguration) {
        try {
            val timestamp = System.currentTimeMillis()
            val fileName = "okitakoy_config_${timestamp}.precieux"
            val fileContent = """
                # Configuration VPN OkitakoyVpn
                # Créé par Okitakoy Précieux
                # Email: okitakoyprecieux@gmail.com
                # WhatsApp: +243982730192
                # Protocole: ${config.protocol}
                
                protocol=${config.protocol}
                server=${config.serverAddress}
                port=${config.serverPort}
                timestamp=${timestamp}
                version=1.0.3
                
                # Instructions:
                # 1. Sauvegardez ce fichier
                # 2. Importez-le dans OkitakoyVpn
                # 3. Connectez-vous au VPN
                
                # Support: okitakoyprecieux@gmail.com
                # Développeur: Okitakoy Précieux

            """.trimIndent()
            
            val file = File(filesDir, fileName)
            file.writeText(fileContent)
            
            // Afficher un message de succès
            AlertDialog.Builder(this)
                .setTitle("Configuration exportée")
                .setMessage("Fichier créé: $fileName\n\nEmplacement: ${file.absolutePath}")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
                
            Toast.makeText(this, "Configuration exportée avec succès", Toast.LENGTH_LONG).show()
            
        } catch (e: Exception) {
            Toast.makeText(this, "Erreur lors de l'exportation: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        when (requestCode) {
            VPN_REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    startVpnService()
                } else {
                    Toast.makeText(this, "Permission VPN refusée", Toast.LENGTH_SHORT).show()
                }
            }
            IMPORT_CONFIG_REQUEST_CODE -> {
                if (resultCode == RESULT_OK && data != null) {
                    // Traiter le fichier importé
                    data.data?.let { uri ->
                        // Lire le fichier .precieux et parser la configuration
                        Toast.makeText(this, "Importation à implémenter", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        // Mettre à jour l'UI à chaque retour sur l'activité
        updateUI()
    }
}

data class VPNConfiguration(
    val protocol: String,
    val serverAddress: String,
    val serverPort: Int
)