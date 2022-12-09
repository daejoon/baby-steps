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
#kubectl apply -f https://$raw_git/172.16_net_calico_v1.yaml
kubectl apply -f calico.yaml

# install bash-completion for kubectl
rm -rf /var/lib/apt/lists/* -vf
apt-get update
apt-get install bash-completion -y

# kubectl completion on bash-completion dir
kubectl completion bash >/etc/bash_completion.d/kubectl

# alias kubectl to k
echo 'alias k=kubectl' >>~/.bashrc
echo "alias ka='kubectl apply -f'" >>~/.bashrc
echo "alias kd='kubectl delete -f'" >>~/.bashrc
echo 'complete -F __start_kubectl k' >>~/.bashrc

## Dashboard 설치
#kubectl apply -f https://kubetm.github.io/yamls/k8s-install/dashboard-2.3.0.yaml
#nohup kubectl proxy --port=8001 --address=192.168.100.10 --accept-hosts='^*$' >/dev/null 2>&1 &
