#!/bin/bash

# Répertoires principaux
MAIN_DIR="src/main/java"
TEST_DIR="src/test/java"

# Trouver les fichiers Java dans les deux répertoires
main_files=$(find "$MAIN_DIR" -type f -name "*.java" ! -name "package-info.java" | sed "s|$MAIN_DIR/||" | sed "s|.java$||")
test_files=$(find "$TEST_DIR" -type f -name "*.java" | sed "s|$TEST_DIR/||" | sed "s|.java$||")

# Initialiser une variable pour suivre les fichiers sans test
missing_tests=()

# Vérifier chaque fichier dans le dossier main
for main_file in $main_files; do
  test_file="${main_file}Test"
  if ! echo "$test_files" | grep -q "^$test_file$"; then
    missing_tests+=("$main_file")
  fi
done

# Afficher les résultats
if [ ${#missing_tests[@]} -eq 0 ]; then
  echo "Toutes les classes dans $MAIN_DIR ont des tests correspondants dans $TEST_DIR."
else
  echo "Les classes suivantes n'ont pas de tests correspondants dans $TEST_DIR :"
  for missing in "${missing_tests[@]}"; do
    echo "- $missing"
  done
  exit 1
fi

exit 0