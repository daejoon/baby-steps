#!/usr/bin/env bash

# root로 변경
sudo su - 
echo "[INFO] master_node.sh, changed user: $(whoami)"

# 쿠버네티스 초기화 명령 실행
kubeadm init --token 123456.1234567890123456 --token-ttl 0 \
  --apiserver-advertise-address 192.168.100.100 --pod-network-cidr=172.16.0.0/16

# 환경변수 설정
mkdir -p $HOME/.kube
cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
chown $(id -u):$(id -g) $HOME/.kube/config

# Kubectl 자동완성 기능 설치
apt-get install -y bash-completion
source <(kubectl completion bash)
kubectl completion bash | sudo tee /etc/bash_completion.d/kubectl > /dev/null

# 쿠버네티스 네트워크
curl https://docs.projectcalico.org/manifests/calico.yaml -O
kubectl apply -f calico.yaml

# Dashboard 설치
kubectl apply -f https://kubetm.github.io/yamls/k8s-install/dashboard-2.3.0.yaml
nohup kubectl proxy --port=8001 --address=192.168.100.100 --accept-hosts='^*$' >/dev/null 2>&1 &
