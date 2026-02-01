{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = [
    pkgs.jdk21
    pkgs.gradle
    pkgs.mesa
    pkgs.libGL
  ];

  shellHook = ''
    export JAVA_HOME=${pkgs.jdk21}
    echo "JAVA_HOME set to $JAVA_HOME"
    echo "Gradle available: $(gradle --version | head -n 1)"
  '';
}

