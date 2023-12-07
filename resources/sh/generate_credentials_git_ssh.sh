#!/bin/bash
#
clear
#
if [[ -z ${WORKSPACE+x} ]]; then
  WORKSPACE=$(pwd)
fi
if [[ -z ${cicd+x} ]]; then
  echo "no esta declarado el cicd"
  exit 1
fi
#
#####################################
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
#### input
USER_MAIL=$1
SSH_KEY=$2
#### varaibles definidas
GIT_CREDENTIALS='.git-credentials'
GIT_CONFIG='.gitconfig'
#####################################################################################
function gitconfig() {
  echo '[user]' > ~/$GIT_CONFIG
  echo "email = '$USER_MAIL'" >> $HOME/$GIT_CONFIG
  echo 'name = infra' >> $HOME/$GIT_CONFIG
}
#
function generate_credentials() {
  if [[ -d $HOME/.ssh ]]; then
    rm -fr $HOME/.ssh
  fi
  mkdir -p $HOME/.ssh
  #
  cp $WORKSPACE/$cicd/resources/env/$SSH_KEY $HOME/.ssh/id_dsa
  cp $WORKSPACE/$cicd/resources/env/$SSH_KEY $HOME/.ssh/id_rsa
  chmod -R 400 $HOME/.ssh/*
  #
  if [[ $debug_mode == 'true' ]]; then
    cat $HOME/.ssh/id_dsa
    cat $HOME/.ssh/id_rsa
  fi
  #
  ssh-keyscan -t rsa github.com >> $HOME/.ssh/known_hosts
}
#
function erase-first() {
  if [[ -e $HOME/.gitconfig ]]; then
    rm $HOME/.gitconfig
  fi
  if [[ -e $HOME/.git-credentials ]]; then
    rm $HOME/.git-credentials
  fi
  if [[ -d $HOME/.gitconfig.d ]]; then
    rm -fr $HOME/.gitconfig.d
  fi
  if [[ -e $HOME/.gitignore_global ]]; then
    rm $HOME/.gitignore_global
  fi
}
#
function set-global() {
  git config --global --add safe.directory /root
}
#
########################## main #######################
if [[ $# -eq '2' ]]; then
  erase-first
  gitconfig
  generate_credentials
  set-global
else
  echo "Uso: $0 <USER_MAIL> <SSH_KEY>"
  exit 1
fi
