#!/bin/bash
#
clear
#
if [[ -z ${WORKSPACE+x} ]]; then
  WORKSPACE=$(pwd)
fi
#
function set-debug() {
  if [[ -n ${debug_mode+x} ]]; then
    if [[ $debug_mode == 'true' ]]; then
      echo "debug mode on"
      set -x
    fi
  fi
}
set-debug
#
kubectl get pod > /dev/null 2>&1
#
if [ $? -eq 0 ]; then
  echo "Kube Credentials OK.... -- $cluster --"
else
  echo "Kube Credentials ERROR!! --- $cluster --"
  exit 1
fi
