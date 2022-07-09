#!/usr/bin/env bash

#root로 변경
sudo su -
echo "[INFO] worker_node.sh, changed user: $(whoami)"

kubeadm join --token 123456.1234567890123456 \
  --discovery-token-unsafe-skip-ca-verification 192.168.100.100:6443