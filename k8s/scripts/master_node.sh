#!/usr/bin/env bash

# 실행 유저 확인
echo "[INFO] master_node.sh, current user: $(whoami)"

# init kubernetes
kubeadm init --token 123456.1234567890123456 --token-ttl 0 \
  --pod-network-cidr=172.16.0.0/16 --apiserver-advertise-address=192.168.10.10 \
  --cri-socket=unix:///run/containerd/containerd.sock

# config for master node only
mkdir -p $HOME/.kube
cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
chown $(id -u):$(id -g) $HOME/.kube/config

# raw_address for gitcontent
#raw_git="raw.githubusercontent.com/sysnet4admin/IaC/master/manifests"
curl https://raw.githubusercontent.com/projectcalico/calico/v3.24.5/manifests/calico.yaml -O

# config for kubernetes's network
kubectl apply -f calico.yaml

# install bash-completion for kubectl
rm -rfv /var/lib/apt/lists/*
apt-get update
apt-get install -y bash-completion

# kubernetes bash completion 시스템 전체에 적용
kubectl completion bash | sudo tee /etc/bash_completion.d/kubectl > /dev/null

# alias kubectl to k
echo 'alias k=kubectl' >>~/.bashrc
echo 'complete -o default -F __start_kubectl k' >>~/.bashrc

