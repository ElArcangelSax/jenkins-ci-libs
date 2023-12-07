#!/bin/bash
#
clear
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
#
if [[ -z ${WORKSPACE+x} ]]; then
  WORKSPACE=$(pwd)
fi
#
while getopts ':a:' option; do
  case $option in
    a)
      ACCION=$OPTARG
      ;;
    *) #No hago nada con parametros que no conozco
      echo "INVALID getopts"
      usage
      exit 1
      ;;
  esac
done
#
#
function prerequisitos() {
  echo $sep_lines
  echo $sep_lines
  echo "Checking prerequisites"
  if [[ -z $ACCION ]]; then
    usage '-a ACCION'
  fi
  if [[ -z $AZURE_TENANT_ID ]]; then
    echo "You need to declare  AZURE_TENANT_ID"
    exit 1
  fi
  if [[ -z $AZURE_CLIENT_ID ]]; then
    echo "You need to declare AZURE_CLIENT_ID"
    exit 1
  fi
  if [[ -z $AZURE_CLIENT_SECRET ]]; then
    echo "You need to declare AZURE_CLIENT_SECRET"
    exit 1
  fi
  if [[ -z $AZURE_REGION ]]; then
    echo "You need to declare AZURE_REGION"
    exit 1
  fi
  #
}
#
function terraform-init() {
  cd $WORKSPACE
  terraform init -input=false
}
#
function terraform-validate() {
  cd $WORKSPACE
  terraform-init
  terraform validate
}
#
function terraform-fmt() {
  cd $WORKSPACE
  terraform-validate
  terraform fmt -check
}
#
function terraform-plan() {
  cd $WORKSPACE
  terraform-fmt
  terraform plan -out=tfplan -input=false
}
function terraform-apply() {
  cd $WORKSPACE
  terraform-plan
  terraform apply -input=false tfplan -auto-approve
}
#
function main() {
  #
  if [[ $ACCION == 'init' ]]; then
    terraform-init
  elif [[ $ACCION == 'validate' ]]; then
    terraform-validate
  elif [[ $ACCION == 'fmt' ]]; then
    terraform-fmt
  elif [[ $ACCION == 'plan' ]]; then
    terraform-plan
  elif [[ $ACCION == 'apply' ]]; then
    terraform-apply
  fi
  #
}

#############################
prerequisitos
main
