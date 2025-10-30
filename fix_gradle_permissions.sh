#!/bin/bash
echo "🔧 Correction des permissions Gradle..."

# Supprimer et recréer le wrapper
rm -f gradlew
rm -f gradlew.bat

# Télécharger un nouveau gradlew
curl -L https://raw.githubusercontent.com/gradle/gradle/master/gradlew -o gradlew
chmod +x gradlew

# Télécharger gradlew.bat
curl -L https://raw.githubusercontent.com/gradle/gradle/master/gradlew.bat -o gradlew.bat

# Vérifier
echo "📋 Permissions des fichiers:"
ls -la gradlew*
echo ""

# Test
./gradlew --version
