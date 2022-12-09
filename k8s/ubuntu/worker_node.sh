#!/usr/bin/env bash

# 실행 유저 확인
echo "[INFO] worker_node.sh, current user: $(whoami)"

# config for work_nodes only
kubeadm join --token 123456.1234567890123456 \
             --discovery-token-unsafe-skip-ca-verification 192.168.10.10:6443 \
             --cri-socket=unix:///run/containerd/containerd.sock
