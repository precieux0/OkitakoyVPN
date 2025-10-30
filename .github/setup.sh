#!/bin/bash
set -e

echo "🔧 Configuration de l'environnement de build..."

# Donner les permissions à gradlew
chmod +x ./gradlew

# Vérifier Java
java -version

# Vérifier Android SDK
if [ -n "$ANDROID_HOME" ]; then
    echo "✅ ANDROID_HOME: $ANDROID_HOME"
else
    echo "❌ ANDROID_HOME non configuré"
fi

# Vérifier les permissions
echo "📋 Permissions:"
ls -la gradlew
echo ""

echo "✅ Setup terminé"
