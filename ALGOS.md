# Project package manager

IntelliJ IDEA integrates with the npm, Yarn, Yarn 2, and pnpm, so you can install, locate, update, and remove packages of reusable code from
inside the IDE, in the built-in Terminal.

- `npm`
- pnpm
- Yarn

By default, IntelliJ IDEA suggests `npm`

Al 22.11.22 la versione di npm è `8.19.2`, mentre la versione di pnpm è `4.14.0`

Terminale -> `npm -v`

# Homebrew

The Missing Package Manager for macOS (or Linux)

Homebrew installs the stuff you need that Apple (or your Linux system) didn’t.

Homebrew installs packages to their own directory and then symlinks their files into /usr/local (on macOS Intel).

Homebrew won’t install files outside its prefix and you can place a Homebrew installation wherever you like.

Al 22.11.22 la versione di homebrew è `3.6.11`

Terminale -> `brew -v`

- update [options]
  Fetch the newest version of Homebrew and all formulae from GitHub using git(1) and perform any necessary migrations.
- upgrade [options] [outdated_formula|outdated_cask …]
  Upgrade outdated casks and outdated, unpinned formulae using the same options they were originally installed with, plus any appended brew
  formula options. If cask or formula are specified, upgrade only the given cask or formula kegs (unless they are pinned; see pin, unpin).

[Brew man page](https://docs.brew.sh/Manpage).

# Java.jdk

- Versione 17 (temurin-17.jdk)

# Spring boot

- Versione 2.7.5 (spring-boot-starter-parent)

# Vaadin

- Versione 23.2.8
- vaadin.compatibilityMode=false
- vaadin.pnpm.enable=false
- vaadin.whitelisted-packages=it/algos
- spring.main.allow-circular-references=true
- spring.jpa.open-in-view=true
