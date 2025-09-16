#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
SRC_DIR="$ROOT_DIR/../src/main/java"
OUT_DIR="$ROOT_DIR/out"

rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

FX_LIB="${JAVA_HOME:-}/lib"
FX_MODULES="javafx.controls,javafx.fxml"

if compgen -G "$FX_LIB/javafx*.jar" > /dev/null; then
  echo "[runtest] JavaFX detected at $FX_LIB -> compiling ALL sources with modules"
  find "$SRC_DIR" -name "*.java" > "$ROOT_DIR/.sources.txt"
  javac --module-path "$FX_LIB" --add-modules "$FX_MODULES" \
        -Xlint:none -encoding UTF-8 -d "$OUT_DIR" @"$ROOT_DIR/.sources.txt"
else
  echo "[runtest] JavaFX NOT found -> compiling HEADLESS core only (exclude GUI classes)"
  EXCLUDE_NAMES="Main.java|Launcher.java|MainWindow.java|DialogBox.java|GuiUi.java"
  find "$SRC_DIR" -name "*.java" \
    ! -regex ".*\(${EXCLUDE_NAMES}\)$" -print0 \
  | xargs -0 grep -L -E 'GuiUi|javafx\.' \
  > "$ROOT_DIR/.sources.txt"

  javac -Xlint:none -encoding UTF-8 -d "$OUT_DIR" @"$ROOT_DIR/.sources.txt"
fi

echo "[runtest] Compile step done."
