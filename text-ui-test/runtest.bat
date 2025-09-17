@echo off
setlocal enabledelayedexpansion

rem --- paths ---
set "ROOT_DIR=%~dp0"
set "SRC_DIR=%ROOT_DIR%..\src\main\java"
set "OUT_DIR=%ROOT_DIR%out"

if exist "%OUT_DIR%" rmdir /S /Q "%OUT_DIR%"
mkdir "%OUT_DIR%"

rem --- JavaFX detection ---
set "FX_LIB=%JAVA_HOME%\lib"
set "FX_MODULES=javafx.controls,javafx.fxml"

dir /b "%FX_LIB%\javafx*.jar" >nul 2>&1
if %errorlevel%==0 (
  echo [runtest] JavaFX detected at %FX_LIB% ^> compiling ALL sources with modules
  dir /s /b "%SRC_DIR%\*.java" > "%ROOT_DIR%.sources.txt"
  javac --module-path "%FX_LIB%" --add-modules %FX_MODULES% -Xlint:none -encoding UTF-8 -d "%OUT_DIR%" @"%ROOT_DIR%.sources.txt"
) else (
  echo [runtest] JavaFX NOT found ^> compiling HEADLESS core only (exclude GUI classes)
  > "%ROOT_DIR%.sources.txt" (
    for /f "delims=" %%f in ('dir /s /b "%SRC_DIR%\*.java"') do (
      echo %%~nxf | findstr /i /x "Main.java Launcher.java MainWindow.java DialogBox.java GuiUi.java PlannerResponder.java" >nul || echo %%f
    )
  )
  javac -Xlint:none -encoding UTF-8 -d "%OUT_DIR%" @"%ROOT_DIR%.sources.txt"
)

echo [runtest] Compile step done.

rem --- optional: run single scenario we added ---
set "ACTUAL=%ROOT_DIR%ACTUAL-between-empty.txt"
type "%ROOT_DIR%between-empty-input.txt" | ^
java -classpath "%OUT_DIR%" planner.ESTJ > "%ACTUAL%"
echo [runtest] comparing ACTUAL vs EXPECTED (between-empty)
fc "%ROOT_DIR%between-empty-expected.txt" "%ACTUAL%"
if errorlevel 1 exit /b 1
echo [runtest] between-empty OK

endlocal
