#!/data/data/com.termux/files/usr/bin/bash

set -e

PROJECT="/storage/emulated/0/AideProjects/SayIt/src"
BIN="$HOME/bin"
BASHRC="$HOME/.bashrc"

echo "===== Say It Build & Setup ====="

# Enter project directory
cd "$PROJECT"

# 1. Build the JAR
echo "[1/5] Building Say It..."
jar cfm sayit.jar MANIFEST.MF -C out .

# 2. Create bin directory
echo "[2/5] Creating command directory..."
mkdir -p "$BIN"

# 3. Install the sayit command
echo "[3/5] Installing sayit..."
cat > "$BIN/sayit" <<EOF
#!/data/data/com.termux/files/usr/bin/bash

PROJECT="$PROJECT"
JAR="\$PROJECT/sayit.jar"

if [ ! -f "\$JAR" ]; then
    echo "Say It error: JAR not found: \$JAR" >&2
    exit 1
fi

exec java -jar "\$JAR" "\$@"
EOF

chmod +x "$BIN/sayit"

# 4. Install the build command in Termux private storage
echo "[4/5] Installing sayit-build..."
cp "$PROJECT/build.sh" "$BIN/sayit-build"
chmod +x "$BIN/sayit-build"

# 5. Configure PATH
if ! grep -Fq 'export PATH="$HOME/bin:$PATH"' "$BASHRC" 2>/dev/null; then
    echo 'export PATH="$HOME/bin:$PATH"' >> "$BASHRC"
fi

export PATH="$HOME/bin:$PATH"

echo
echo "===== Setup Complete ====="
echo "JAR: $PROJECT/sayit.jar"
echo
echo "Run Say It:"
echo "  sayit compile main.si main.sic"
echo "  sayit run main.sic"
echo
echo "Rebuild Say It:"
echo "  sayit-build"
echo
echo "Note: 'out/' must contain your compiled Java classes."
