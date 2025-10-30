#!/bin/bash
echo "🔄 Régénération complète du wrapper Gradle..."

# Supprimer l'ancien wrapper si il existe
rm -rf gradle
rm -f gradlew
rm -f gradlew.bat

# Créer la structure de dossiers
mkdir -p gradle/wrapper

# Télécharger le fichier gradle-wrapper.jar
echo "📥 Téléchargement de gradle-wrapper.jar..."
wget https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar -O gradle/wrapper/gradle-wrapper.jar

# Recréer gradlew (Linux/Mac)
cat > gradlew << 'GRADLEW_SCRIPT'
#!/usr/bin/env bash

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: \$0 may be a link
PRG="\$0"
# Need this for relative symlinks.
while [ -h "\$PRG" ] ; do
    ls=\`ls -ld "\$PRG"\`
    link=\`expr "\$ls" : '.*-> \(.*\)\$'\`
    if expr "\$link" : '/.*' > /dev/null; then
        PRG="\$link"
    else
        PRG=\`dirname "\$PRG"\`"/\$link"
    fi
done
SAVED="\`pwd\`"
cd "\`dirname \"\$PRG\"\`/" >/dev/null
APP_HOME="\`pwd -P\`"
cd "\$SAVED" >/dev/null

APP_NAME="Gradle"
APP_BASE_NAME=\`basename "\$0"\`

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "\$*"
}

die () {
    echo
    echo "\$*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "\`uname\`" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MINGW* )
    msys=true
    ;;
  NONSTOP* )
    nonstop=true
    ;;
esac

CLASSPATH=\$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Check if Java 17 is available
if ! command -v java >/dev/null 2>&1; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

java_version=\$(java -version 2>&1 | head -n1 | cut -d'"' -f2)
if [[ ! "\$java_version" =~ "17" ]]; then
    echo "Warning: Java 17 is recommended but found \$java_version"
fi

# For Cygwin or MSYS, switch paths to Windows format before running java
if [ "\$cygwin" = "true" ] || [ "\$msys" = "true" ] ; then
    APP_HOME=\`cygpath --path --mixed "\$APP_HOME"\`
    CLASSPATH=\`cygpath --path --mixed "\$CLASSPATH"\`
    JAVACMD=\`cygpath --unix "\$JAVACMD"\`
fi

# Escape application args
save () {
    for i do printf %s\\\\n "\$i" | sed "s/'/'\\\\\\\\''/g;1s/^/'/;\\\\\$s/\\\\\$/' \\\\\\\\/" ; done
    echo " "
}
APP_ARGS=\`save "\$@"\`

# Collect all arguments for the java command, following the shell quoting and substitution rules
eval set -- \$DEFAULT_JVM_OPTS \$JAVA_OPTS \$GRADLE_OPTS "\"-Dorg.gradle.appname=\$APP_BASE_NAME\"" -classpath "\"\$CLASSPATH\"" org.gradle.wrapper.GradleWrapperMain "\$APP_ARGS"

exec "\$JAVACMD" "\$@"
GRADLEW_SCRIPT

chmod +x gradlew

# Recréer gradlew.bat (Windows)
cat > gradlew.bat << 'GRADLEW_BAT'
@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar


@rem Execute Gradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe_ /c return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%GRADLE_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
GRADLEW_BAT

# Vérifier que gradle-wrapper.properties existe
if [ ! -f "gradle/wrapper/gradle-wrapper.properties" ]; then
    cat > gradle/wrapper/gradle-wrapper.properties << 'PROPERTIES'
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.4-bin.zip
networkTimeout=10000
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
PROPERTIES
fi

echo "✅ Wrapper Gradle régénéré avec succès"
echo "🔄 Test du wrapper..."
./gradlew --version
