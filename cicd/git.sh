#!/bin/bash
#
USERNAME=$1
PASSWORD=$2
FOLDER=$3
#
function set-debug() {
  if [[ -n ${debug_mode+x} ]]; then
    if [[ $debug_mode == 'true' ]]; then
      echo "debug mode on"
      set -x
    fi
  fi
}
#
set-debug
#
if [[ -z $USERNAME || -z $PASSWORD || -z $FOLDER ]]; then
  echo "You need to complete  USERNAME or PASSWORD or FOLDER"
  exit 1
fi
#
if [[ -n $(ls -las /$FOLDER | grep .git | grep -v .sh | grep -v .gitattributes | grep -v .gitignore) ]]; then
  #
  echo "Git base exists..It's not getting created."
  #
  cd /$FOLDER
  git branch --set-upstream-to=origin/master master
  git checkout .
  git pull
  chmod -R 777 *.sh
  #
  echo "Desencripting ..."
  git-crypt unlock $WORKSPACE/$gitcryp_ci
  #
else
  #
  echo "Git base does not exist....creating..."
  #
  cd /$FOLDER
  git init
  git config --local init.defaultBranch master
  git remote add origin https://$USERNAME:$PASSWORD@github.com/ElArcangelSax/jenkins-ci-libs.git
  git branch --set-upstream-to=origin/master master
  git pull origin master
  #
  echo "Desencripting ..."
  git-crypt unlock $WORKSPACE/$gitcryp_ci
  #
fi
