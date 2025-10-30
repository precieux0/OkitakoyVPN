#!/bin/bash

echo "🔧 Vérification et compilation du projet OkitakoyVpn..."

# Vérification de Java
java_version=$(java -version 2>&1 | head -n1 | cut -d'"' -f2)
echo "Java version: $java_version"

# Vérification de la structure des dossiers
echo "📁 Vérification de la structure des dossiers..."
directories=(
    "app/src/main/java/com/okitakoy/vpn"
    "app/src/main/res/layout"
    "app/src/main/res/drawable"
    "app/src/main/res/values"
    "app/src/main/res/xml"
    "app/src/main/res/menu"
    ".github/workflows"
)

for dir in "${directories[@]}"; do
    if [ -d "$dir" ]; then
        echo "✅ $dir existe"
    else
        echo "❌ $dir manquant"
        exit 1
    fi
done

# Vérification des fichiers essentiels
echo "📄 Vérification des fichiers essentiels..."
essential_files=(
    "app/build.gradle"
    "build.gradle"
    "settings.gradle"
    "app/src/main/AndroidManifest.xml"
    "app/src/main/java/com/okitakoy/vpn/MainActivity.kt"
    "app/src/main/res/layout/activity_main.xml"
    ".github/workflows/android.yml"
)

for file in "${essential_files[@]}"; do
    if [ -f "$file" ]; then
        echo "✅ $file existe"
    else
        echo "❌ $file manquant"
        exit 1
    fi
done

# Tentative de compilation
echo "🚀 Tentative de compilation..."
./gradlew build

if [ $? -eq 0 ]; then
    echo "🎉 Compilation réussie! Le projet est prêt."
    echo "📱 APK généré dans: app/build/outputs/apk/"
else
    echo "❌ Erreur lors de la compilation."
    exit 1
fi

echo "✅ Projet OkitakoyVpn créé avec succès!"
echo "📧 Développeur: Okitakoy Précieux"
echo "📧 Email: okitakoyprecieux@gmail.com"
echo "📱 WhatsApp: +243982730192"
