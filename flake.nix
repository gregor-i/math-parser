{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-25.05";
    utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, utils }:
    utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs { inherit system; };
        sbt = pkgs.sbt;

      in {
        devShells.default = pkgs.mkShell {
          packages = [ sbt ];
        };

        packages = {
        };

        formatter = pkgs.nixfmt;
      });
}
